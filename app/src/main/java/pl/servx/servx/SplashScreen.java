package pl.servx.servx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    final FirebaseAuth mAuth= FirebaseAuth.getInstance();
                    final FirebaseUser check= mAuth.getCurrentUser();
                    if (check!=null){
                        sleep(1000);
                        final String number= check.getDisplayName();
                        final String email= check.getEmail();
                        Intent home = new Intent(getApplicationContext(), home.class);
                        home.putExtra("extra", number);
                        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        startActivity(home);
                    }
                    else {
                        sleep(2000);
                        Intent intent = new Intent(getApplicationContext(), Tabbed_Main.class);
                        startActivity(intent);
                        finish();
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}
