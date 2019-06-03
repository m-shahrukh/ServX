package pl.servx.servx;

//import android.app.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class sign_in extends Fragment {
    Activity activity;
    ProgressDialog mDialog;

    @Nullable
    @Override



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstancesState){
        View rootView = inflater.inflate(R.layout.tabbed_login, container, false);
         activity= getActivity();
         mDialog=new ProgressDialog(activity);

        final EditText edtphone, edtpass;
        Button btnSignIn;
        edtpass = rootView.findViewById(R.id.edtPassword);
        edtphone = rootView.findViewById(R.id.edtPhone);
        btnSignIn = rootView.findViewById(R.id.btnSignIn);
        final FirebaseAuth mAuth= FirebaseAuth.getInstance();
        final FirebaseUser check= mAuth.getCurrentUser();
        if (check!=null){
//            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                    .setDisplayName("03369696969").build();
//
//            check.updateProfile(profileUpdates);
            final String number= check.getDisplayName();

            //final ArrayList<String> cars= new ArrayList<>();
            //cars.add("Select Car");
            mDialog.setMessage("Please Wait");
            mDialog.show();
            //FirebaseDatabase db= FirebaseDatabase.getInstance();
            final String email= check.getEmail();

            mDialog.dismiss();
                    Intent home = new Intent(getActivity(), home.class);


                    home.putExtra("extra", number);
                    home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    //getActivity().finish();
                    startActivity(home);
            //return rootView;
//            final Activity activity= getActivity();

        }





        //init database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //final ProgressDialog mDialog = new ProgressDialog(activity);
                final String passs = edtpass.getText().toString().trim();
                final String num = edtphone.getText().toString().trim();

                if(num.isEmpty()) {
                    edtphone.setError("Field is empty");
                }

                if(passs.isEmpty()) {
                    edtpass.setError("Field is empty");
                }

                table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override

                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //user info

                        if (num.isEmpty() || passs.isEmpty()) {
                            Toast.makeText(activity, "Can Not Log-in With Empty Field", Toast.LENGTH_LONG).show();
                        }
                        else {
                            mDialog.setMessage("Please Wait");
                            mDialog.show();

                            if (dataSnapshot.child((edtphone.getText().toString())).exists()) {

                                String email= dataSnapshot.child(edtphone.getText().toString()).child("email").getValue(String.class);
//                                final ArrayList<String> cars= new ArrayList<>();
//                                cars.add("Select Car");
//                                for (DataSnapshot ds:dataSnapshot.child(edtphone.getText().toString()).child("vehicle").getChildren())
//                                {
//                                    String name= String.valueOf(ds.getKey());
//                                    cars.add(name);
//                                }

                                //User user = dataSnapshot.child(edtphone.getText().toString()).getValue(User.class);
                                mAuth.signInWithEmailAndPassword(email, edtpass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {

                                            Toast.makeText(activity, "Sign In Successful", Toast.LENGTH_SHORT).show();
                                            FirebaseUser user= mAuth.getCurrentUser();
                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(edtphone.getText().toString()).build();

                                            user.updateProfile(profileUpdates);
                                            //cart_data.cars=cars;
                                            //car_list.cars=cars;
                                            Intent home = new Intent(activity, home.class);
                                            home.putExtra("extra", edtphone.getText().toString());

                                            //home.putStringArrayListExtra("cars",cars);
                                            //activity.finish();
                                            //getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                                            FragmentManager fm = getActivity().getSupportFragmentManager();
//                                            for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
//                                                fm.popBackStack();
//                                            }

                                            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            mDialog.dismiss();
                                            //table_user.removeEventListener(ValueEventListener);
                                            //getActivity().finish();

                                            startActivity(home);
                                            activity.finish();
                                        } else {
                                            Toast.makeText(activity, "Sign-in Failed", Toast.LENGTH_SHORT).show();
                                            mDialog.dismiss();
                                        }
                                    }
                                });


                            } else {
                                mDialog.dismiss();
                                Toast.makeText(activity, "User Does Not Exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        return rootView;
    }

}

/*
public class sign_in extends AppCompatActivity {
    EditText edtphone, edtpass;
    Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtpass = (MaterialEditText)findViewById(R.id.edtPassword);
        edtphone = (MaterialEditText)findViewById(R.id.edtPhone);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        //init database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(sign_in.this);
                mDialog.setMessage("Please Wait");
                        mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override

                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //user info
                        mDialog.dismiss();

                        if (dataSnapshot.child((edtphone.getText().toString())).exists()) {


                            User user = dataSnapshot.child(edtphone.getText().toString()).getValue(User.class);

                            if (user.getPassword().equals(edtpass.getText().toString())) {
                                Toast.makeText(sign_in.this, "sign in successful", Toast.LENGTH_LONG).show();

                                Intent home = new Intent( sign_in.this, home.class );
                                home.putExtra("extra", edtphone.getText().toString());
                                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                startActivity(home);
                            }
                            else {
                                Toast.makeText(sign_in.this, "sign-in failed", Toast.LENGTH_LONG).show();
                            }

                        }
                        else{
                            Toast.makeText(sign_in.this,"user does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
//    @Override
//    public void onBackPressed() {
//        Intent i= new Intent(sign_in.this, MainActivity.class);
//        startActivity(i);
//    }
}
*/
