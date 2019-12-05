package cs2901.utec.chat_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
  }

  public void showMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }

  public void onBtnLoginClicked(View view) {

    EditText txtUsername = (EditText) findViewById(R.id.txtUsername);
    EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
    String username = txtUsername.getText().toString();
    String password = txtPassword.getText().toString();

    Map<String, String> message = new HashMap<>();
    message.put("username", username);
    message.put("password", password);

    JSONObject jsonMessage = new JSONObject(message);

    String endpoint = "http://192.168.1.19:8080/authenticate";

    JsonObjectRequest request =
        new JsonObjectRequest(
            Request.Method.POST,
            endpoint,
            jsonMessage,
            new Response.Listener<JSONObject>() {
              @Override
              public void onResponse(JSONObject response) {
                showMessage("Successfully authenticated");
                try {
                  String username = response.getString("username");
                  String fullname = response.getString("fullname");
                  int id = response.getInt("user_id");
                  Intent intent = new Intent(LoginActivity.this, ContactsActivity.class);
                  intent.putExtra("username", username);
                  intent.putExtra("fullname", fullname);
                  intent.putExtra("id", id);
                  startActivity(intent);
                } catch (JSONException e) {
                  e.printStackTrace();
                }
              }
            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError volleyError) {
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
