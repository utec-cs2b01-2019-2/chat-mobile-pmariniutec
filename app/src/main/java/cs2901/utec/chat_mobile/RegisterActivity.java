package cs2901.utec.chat_mobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rengwuxian.materialedittext.MaterialEditText;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
  }

  public void showMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }

  public void onBtnRegisterClicked(View view) {

    MaterialEditText mName = findViewById(R.id.edtName);
    MaterialEditText mFullname = findViewById(R.id.edtFullName);
    MaterialEditText mUsername = findViewById(R.id.edtUserName);
    MaterialEditText mPassword = findViewById(R.id.edtPassword);

    String name = mName.getText().toString();
    String fullname = mFullname.getText().toString();
    String username = mUsername.getText().toString();
    String password = mPassword.getText().toString();

    Map<String, String> message = new HashMap<>();
    message.put("name", name);
    message.put("fullname", fullname);
    message.put("username", username);
    message.put("password", password);

    JSONObject jsonMessage = new JSONObject(message);

    // Toast.makeText(this,jsonMessage.toString(),Toast.LENGTH_LONG).show();

    System.out.println("Usuario " + username);
    String url = "http://192.168.1.5:8080/registerUser";
    // String url="http://10.100.228.59:8080/authenticate";
    // String url="http://172.17.0.1:8080/authenticate";
    JsonObjectRequest request =
        new JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonMessage,
            new Response.Listener<JSONObject>() {
              @Override
              public void onResponse(JSONObject response) {
                // TODO Qué hacer cuando el server responda
                showMessage("Se registro exitosamente!");
                // Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                // startActivity(intent);
                // finish();
              }
            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError volleyError) {
                // TODO Qué hacer cuando ocurra un error

                // System.out.println("No se logro loguear");
                String message = volleyError.toString();
                if (volleyError instanceof NetworkError) {
                  message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ServerError) {
                  message = "The server could not be found. Please try again after some time!!";
                } else if (volleyError instanceof AuthFailureError) {
                  message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ParseError) {
                  message = "Parsing error! Please try again after some time!!";
                } else if (volleyError instanceof NoConnectionError) {
                  message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof TimeoutError) {
                  message = "Connection TimeOut! Please check your internet connection.";
                }
                showMessage(message);
              }
            });

    RequestQueue queue = Volley.newRequestQueue(this);
    queue.add(request);
  }
}
