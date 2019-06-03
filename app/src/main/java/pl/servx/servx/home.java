package pl.servx.servx;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pusher.pushnotifications.PushNotifications;

import java.util.ArrayList;

import pl.servx.servx.Model.SharePref;
import pl.servx.servx.Model.Spinner_Adapter;
import pl.servx.servx.Model.cart_data;

public class home extends AppCompatActivity{
    Button btnServices,btnHistory,btnAddCar, btnmaps;
    Spinner sp;
    static String user;
    ArrayList<String> cars;
    int flag=0;
    //TextView stat_text;
    ArrayAdapter<String> adapt;
    String user_name;
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        cars =new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.stat_text);
        toolbarTitle.setText("Home");
        this.setSupportActionBar(toolbar);


        Intent intent = getIntent();
        user_name = intent.getStringExtra("extra");
        //User.Number=user_name;
//        final FirebaseAuth mAuth= FirebaseAuth.getInstance();
//        final FirebaseUser check= mAuth.getCurrentUser();
        //user_name=check.getDisplayName();
        user = user_name;
        SharePref sharePref = new SharePref();
        PushNotifications.start(getApplicationContext(), "ea347389-aee7-4445-9b14-15cd0fe885f4");
        PushNotifications.addDeviceInterest(user_name);
        //PushNotifications.removeDeviceInterest(user_name);



        sharePref.save(this,user_name);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }

        sp = (Spinner) findViewById(R.id.spinner);
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference db= database.getReference("User/"+user_name);
        final ProgressDialog mDialog = new ProgressDialog(this);
        mDialog.setMessage("Please Wait");

        db.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("vehicle").exists()) {
                    for (DataSnapshot ds : dataSnapshot.child("vehicle").getChildren()) {
                        String name = String.valueOf(ds.getKey());
                        if (cars.contains(name)) {
                            continue;
                        }
                        cars.add(name);
                    }

                    if (flag - 1 == 0) {
                        mDialog.dismiss();
                    }
                }
                else{
                    mDialog.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        cars.add("Select Car");
        if (flag==0)
        {mDialog.show();}
        flag++;
        //String addcar="";
//        if (intent.getStringExtra("addcar")!=null){
//            addcar=
//        }

        adapt = new Spinner_Adapter(this,
                R.layout.spinner_row, cars,cars);

        adapt.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        sp.setAdapter(adapt);

        btnAddCar= (Button) findViewById(R.id.btnAddCar);
        btnAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent form = new Intent( home.this, AddCarForm.class );
                //form.putStringArrayListExtra("cars",cars);
                startActivity(form);
            }
        });

/*        btnmaps = (Button)findViewById(R.id.btnmaps);
        btnmaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent maps = new Intent(home.this, select_location.class);
                startActivity(maps);
            }
        });*/

        btnServices= (Button) findViewById(R.id.btnServices);
        btnServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent services = new Intent( home.this, services_tabbed.class );
                String car= sp.getSelectedItem().toString();
                if (car!="Select Car"){
                    cart_data.car=car;
                startActivity(services);}
                else {
                    Toast.makeText(home.this, "Select/Add A Car", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnHistory= (Button) findViewById(R.id.btnHistory);
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent services = new Intent( home.this, service_history.class );

                startActivity(services);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        /*String[] id = getResources().getResourceName(item.getItemId()).split("\\/");

        if(id[1].equals("remove_car")) {
            final String spinner_item = sp.getSelectedItem().toString();
            if (spinner_item.equals("Select Car")) {
                Toast.makeText(home.this, "Select The Car You Want To Remove", Toast.LENGTH_LONG).show();
            }
            else{
                AlertDialog.Builder alert = new AlertDialog.Builder(home.this);
                alert.setMessage("Do You Want To Remove '" + spinner_item + "' From The List?");
                alert.setCancelable(true);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase database= FirebaseDatabase.getInstance();

                        DatabaseReference db= database.getReference("User").child(user).child("vehicle");
//                        try{
//                        db.child(spinner_item).removeValue();}
//                        catch (Exception e) {
//                            e.printStackTrace();
//                        }

                        db.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                dataSnapshot.getRef().child(spinner_item).removeValue();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        cars.clear();
                        cars.add("Select Car");
                        //sp.setAdapter(adapt);
                        sp.setSelection(0);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { }
                });
                alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) { }
                });
                alert.create().show();
            }

        }
        else{*/
            FirebaseAuth.getInstance().signOut();
            PushNotifications.start(getApplicationContext(), "ea347389-aee7-4445-9b14-15cd0fe885f4");
            //PushNotifications.addDeviceInterest(user_name);
            PushNotifications.removeDeviceInterest(user_name);

            finish();
            Intent out = new Intent(home.this, Tabbed_Main.class);
       // out.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(out);
        //}
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
