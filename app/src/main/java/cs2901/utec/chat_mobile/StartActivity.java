package cs2901.utec.chat_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_start);
  }

  public void onBtnClickedSignIn(View view) {
    startActivity(new Intent(this, LoginActivity.class));
  }

  public void onBtnClickedSignUp(View view) {
    startActivity(new Intent(this, RegisterActivity.class));
  }
}
