package cs2901.utec.chat_mobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class MessageActivity extends AppCompatActivity {

  RecyclerView mRecyclerView;
  RecyclerView.Adapter mAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_message);

    String username = getIntent().getExtras().get("username").toString();
    TextView tx = (TextView) findViewById(R.id.tv_contact);
    tx.setText(username);
    mRecyclerView = findViewById(R.id.main_recycler_view);
  }

  @Override
  protected void onResume() {
    super.onResume();
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    getChats();
  }

  public void onClickBtnSend(View v) {
    postMessage();
  }

  public Activity getActivity() {
    return this;
  }

  public void showMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }

  public void getChats() {
    final int userToId = getIntent().getExtras().getInt("user_to_id");
    final int userFromId = getIntent().getExtras().getInt("user_from_id");

    String url = "http://192.168.1.19:8080/conversation/" + userToId;

    JsonArrayRequest request =
        new JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            new Response.Listener<JSONArray>() {
              @Override
              public void onResponse(JSONArray response) {
                mAdapter = new MessageAdapter(response, getActivity(), userFromId);
                mRecyclerView.setAdapter(mAdapter);
              }
            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
              }
            });

    RequestQueue queue = Volley.newRequestQueue(this);

    queue.add(request);
  }

  public void postMessage() {
    String url = "http://192.168.1.19:8080/send_message";

    Map<String, String> params = new HashMap();
    final String user_from_id = getIntent().getExtras().get("user_from_id").toString();
    final String user_to_id = getIntent().getExtras().get("user_to_id").toString();
    final String content = ((EditText) findViewById(R.id.txtMessage)).getText().toString();
    params.put("user_from_id", user_from_id);
    params.put("user_to_id", user_to_id);
    params.put("content", content);
    JSONObject parameters = new JSONObject(params);
    JsonObjectRequest request =
        new JsonObjectRequest(
            Request.Method.POST,
            url,
            parameters,
            new Response.Listener<JSONObject>() {
              @Override
              public void onResponse(JSONObject response) {
                showMessage("Message sent successfully!");
              }
            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                showMessage(error.toString());
                error.printStackTrace();
              }
            });
    RequestQueue queue = Volley.newRequestQueue(this);
    queue.add(request);

    finish();
    startActivity(getIntent());
  }
}
