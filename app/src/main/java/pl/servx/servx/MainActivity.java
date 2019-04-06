package pl.servx.servx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnSignIn, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn= (Button)findViewById(R.id.btnSignIn);

        btnSignUp= (Button)findViewById(R.id.btnSignUp);



        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent SignUp = new Intent( MainActivity.this, SignUp.class );
                startActivity(SignUp);

            }
        });

    btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sign_in = new Intent( MainActivity.this, sign_in.class );
                startActivity(sign_in);
            }
        });
    }
}
