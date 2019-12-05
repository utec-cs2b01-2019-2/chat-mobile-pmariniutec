package cs2901.utec.chat_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class ContactsActivity extends AppCompatActivity {

  RecyclerView mRecyclerView;
  RecyclerView.Adapter mAdapter;

  String url1="http://192.168.1.19:8080/contacts/";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_contacts);

	Toolbar toolbar=findViewById(R.id.toolbar);
	setSupportActionBar(toolbar);
	getSupportActionBar().setTitle("");

	int user_id=getIntent().getExtras().getInt("id");
	String username=getIntent().getExtras().getString("username");
	String fullname=getIntent().getExtras().getString("fullname");

	TextView tx=(TextView)findViewById(R.id.tv_username);
	tx.setText(fullname);
	mRecyclerView = findViewById(R.id.main_recycler_view);
  }

  public void showMessage(String message) {
	Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }

  @Override
  protected void onResume() {
	super.onResume();
	mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
	getUsers();
  }

  public Activity getActivity(){
	return this;
  }

  public void getUsers(){
	final int user_id=getIntent().getExtras().getInt("id");
	String url=url1+String.valueOf(user_id);

	JSONArray jsonMessage=new JSONArray();

	JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, jsonMessage,
		new Response.Listener<JSONArray>() {
		  @Override
		  public void onResponse(JSONArray response) {
			//JSONArray data = response.getJSONArray("data");
			mAdapter = new ChatAdapter(response, getActivity(), user_id);
			mRecyclerView.setAdapter(mAdapter);
		  }
		},
		new Response.ErrorListener() {
		  @Override
		  public void onErrorResponse(VolleyError error) {
			error.printStackTrace();
			String msg=error.toString();
			showMessage(msg);

		  }
		}
		);
	RequestQueue queue= Volley.newRequestQueue(this);
	queue.add(request);


  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.menu,menu);
	return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
	switch (item.getItemId()){
	  case R.id.logout:
		startActivity(new Intent(ContactsActivity.this,LoginActivity.class));
		return true;
	}
	return false;
  }
}
