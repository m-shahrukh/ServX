package pl.servx.servx;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.regex.Pattern;

import pl.servx.servx.Model.User;

public class SignUp extends Fragment {
    int counter = 0;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstancesState){
        View rootView = inflater.inflate(R.layout.tabbed_signup, container, false);

        final MaterialEditText edtPhone, edtName, edtEmail, edtPassword, edtPasswordconf;
        Button btnSignUp;

            edtName = rootView.findViewById(R.id.edtName);
            edtPhone = rootView.findViewById(R.id.edtPhone);
            edtEmail = rootView.findViewById(R.id.edtEmail);
            edtPassword = rootView.findViewById(R.id.edtPassword);
            edtPasswordconf =  rootView.findViewById(R.id.edtPasswordconf);
            btnSignUp = rootView.findViewById(R.id.btnSignUp);

        final Pattern Pass_Pat = Pattern.compile("^" + "(?=\\S+$)" + ".{6,}" + "$");
        final Pattern Name_Pat = Pattern.compile("^[\\p{L} .'-]+$");
        final Pattern Num_Pat = Pattern.compile("^[0]{1}[3]{1}[0-9]{9}$");



            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference table_user = database.getReference("User");
            final DatabaseReference table_user1 = database.getReference("requests");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(getActivity());

                final String emailInput = edtEmail.getText().toString().trim();
                final String passs = edtPassword.getText().toString().trim();
                final String num = edtPhone.getText().toString().trim();
                final String name = edtName.getText().toString().trim();

                boolean flag = false;

                if (emailInput.isEmpty()) {
                    edtEmail.setError("Field is empty");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                    edtEmail.setError("Email Address not valid");
                    flag = true;
                }

                if (num.isEmpty()) {
                    edtPhone.setError("Field is empty");
                } else if (!Num_Pat.matcher(num).matches()) {
                    edtPhone.setError("Incorrect format (03*********)");
                    flag = true;
                }

                if (name.isEmpty()) {
                    edtName.setError("Field is empty");
                } else if (!Name_Pat.matcher(name).matches()) {
                    edtName.setError("Recheck Name");
                    flag = true;
                }

                if (passs.isEmpty()) {
                    edtPassword.setError("Field is empty");
                } else if (!Pass_Pat.matcher(passs).matches()) {
                    edtPassword.setError("Password Should be At Least 6 character");
                    flag = true;
                }

                if (num.isEmpty() || name.isEmpty() || emailInput.isEmpty() || passs.isEmpty()) {
                    Toast.makeText(getActivity(), "Can Not Sign-up With Empty Field", Toast.LENGTH_LONG).show();
                }
                else {
                    if (!flag){
                        mDialog.setMessage("Please Wait");
                        mDialog.show();

                        if (edtPassword.getText().toString().equals(edtPasswordconf.getText().toString())) {
                            String x = "0";
                            x = '"' + x + '"';
                            final FirebaseAuth mAuth;
                            x = '"' + x + '"';
                            final Activity activity = getActivity();
                            mAuth = FirebaseAuth.getInstance();
                            mAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    mDialog.dismiss();
                                    if (task.isSuccessful()) {
                                        mDialog.dismiss();
                                        Toast.makeText(getActivity(), "SignUp Successful", Toast.LENGTH_SHORT).show();
                                        final User user = new User(edtName.getText().toString(), edtEmail.getText().toString());

                                    table_user.child(edtPhone.getText().toString()).setValue(user);

                                    mAuth.signOut();

                                     //   userr.updateProfile(profileUpdates);*/

//                                    FirebaseUser userr= mAuth.getCurrentUser();
//                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                                            .setDisplayName(edtPhone.getText().toString()).build();
//
//                                    userr.updateProfile(profileUpdates);
//                                    //edtName.setText(userr.getDisplayName());
//                                    //edtEmail.setText(userr.getEmail());
                                    Intent signin = new Intent(getActivity(), Tabbed_Main.class);
                                    signin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                        startActivity(signin);
                                        getActivity().finish();

                                    } else {
                                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                            mDialog.dismiss();
                                            Toast.makeText(getActivity(), "User Already Exists", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            });

                        } else {
                            mDialog.dismiss();
                            Toast.makeText(getActivity(), "Passwords Do Not Match", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        mDialog.dismiss();
                        Toast.makeText(getActivity(), "Cannot Sign-up With Incorrect Field", Toast.LENGTH_LONG).show();
                    }
                }
            }

        });
        return rootView;
    }
}

/*
public class SignUp extends AppCompatActivity {

    int counter =0 ;
    int temp = 1;
    MaterialEditText edtPhone, edtName, edtEmail, edtPassword, edtPasswordconf;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        edtName = (MaterialEditText) findViewById(R.id.edtName);
        edtPhone = (MaterialEditText) findViewById(R.id.edtPhone);
        edtEmail = (MaterialEditText) findViewById(R.id.edtEmail);
        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);
        edtPasswordconf = (MaterialEditText) findViewById(R.id.edtPasswordconf);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        final DatabaseReference table_user1 = database.getReference("requests");


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please wait");
                mDialog.show();


                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot dataSnapshot) {
                        //check if already user phone
                        if (dataSnapshot.child(edtPhone.getText().toString()).exists())
                        {
                            mDialog.dismiss();
                            if(counter == 0 )
                             {

                                Toast.makeText(SignUp.this, "User Already exists", Toast.LENGTH_LONG).show();
                             }
                        }

                        else
                        {
                            if(edtPassword.getText().toString().equals(edtPasswordconf.getText().toString())) {
                                mDialog.dismiss();
                                User user = new User(edtName.getText().toString(), edtPassword.getText().toString());
                                table_user.child(edtPhone.getText().toString()).setValue(user);
                                request req = new request();

                                table_user1.child(edtPhone.getText().toString()).child("0").setValue(req);
                                Toast.makeText(SignUp.this, "SignUp successul", Toast.LENGTH_LONG).show();
                                counter = 1;
                                Intent signin = new Intent( SignUp.this, sign_in.class );
                                startActivity(signin);
                                finish();
                            }
                            else{
                                mDialog.dismiss();
                                Toast.makeText(SignUp.this,"make sure your passwords match", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull  DatabaseError databaseError) {
                        mDialog.dismiss();


                    }
                });

            }
        });
    }
//    @Override
//    public void onBackPressed() {
//        Intent i= new Intent(SignUp.this, MainActivity.class);
//        startActivity(i);
//    }
}
*/
