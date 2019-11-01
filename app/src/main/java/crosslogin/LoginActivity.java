package crosslogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cgg.gov.in.trialall.R;


public class LoginActivity extends AppCompatActivity {
    Button btn_login;
    /*@InjectView(R.id.btn_login) Button btn_login;
    @InjectView(R.id.tv_title) TextView tv_title;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //ButterKnife.inject(this);

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(LoginActivity.this, "Hloo", Toast.LENGTH_LONG).show();
            }

        });

    }
}
