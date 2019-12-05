package cs2901.utec.chat_mobile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
  public JSONArray elements;
  private Context context;
  public int userFromId;

  public ChatAdapter(JSONArray elements, Context context, int userFromId){
	this.elements = elements;
	this.context = context;
	this.userFromId = userFromId;
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
	TextView first_line, second_line;
	RelativeLayout container;

	public ViewHolder(View itemView) {
	  super(itemView);
	  first_line = itemView.findViewById(R.id.element_view_first_line);
	  second_line = itemView.findViewById(R.id.element_view_second_line);
	  container = itemView.findViewById(R.id.element_view_container);
	}
  }


  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
	View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_view,parent, false);
	return new ViewHolder(view);
  }

  public void goToMessageActivity(int user_id, String username){
	Intent goToMessage = new Intent(this.context,MessageActivity.class);
	System.out.println("Esta en chapter\n");
	System.out.println(userFromId);
	System.out.println(user_id);
	goToMessage.putExtra("user_from_id",userFromId);
	goToMessage.putExtra("user_to_id",user_id);
	goToMessage.putExtra("username", username);
	this.context.startActivity(goToMessage);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
	try {
	  JSONObject  element = elements.getJSONObject(position);
	  String name=element.getString("fullname");
	  final String username=element.getString("username");
	  final int id = element.getInt("id");
	  holder.first_line.setText(name);
	  holder.second_line.setText(username);
	  holder.container.setOnClickListener(new View.OnClickListener(){
		@Override
		public void onClick(View v) {
		  goToMessageActivity(id,username);
		}
	  });

	} catch (JSONException e) {
	  e.printStackTrace();
	}

  }

  @Override
  public int getItemCount() {
	return elements.length();
  }
}
