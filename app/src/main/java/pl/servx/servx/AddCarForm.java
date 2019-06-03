package pl.servx.servx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

import pl.servx.servx.Model.SharePref;
import pl.servx.servx.Model.vehicle;

public class AddCarForm extends AppCompatActivity implements OnItemSelectedListener{
    Spinner spCarModel,spCarMake,spCarYear;
    Button ConfirmCar ;
    MaterialEditText textCarPlate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_form);

        SharePref sharePref = new SharePref();
        final String UserName = sharePref.getData(this);

        final Pattern Regi_Pat = Pattern.compile("^[A-Za-z]{3,4}[0-9]{1,4}$");

        Button back_button= (Button) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home= new Intent(AddCarForm.this, home.class);

                home.putExtra("extra", UserName);
                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                //home.putExtra("extra", UserName);
                startActivity(home);
            }
        });

        //SharePref sharePref = new SharePref();
        //final String UserName = sharePref.getData(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        spCarMake = (Spinner)findViewById(R.id.spCarMake);
        spCarModel = (Spinner)findViewById(R.id.spCarModel);
        spCarYear = (Spinner)findViewById(R.id.spCarYear);
        ConfirmCar = (Button)findViewById(R.id.ConfirmCar);
        textCarPlate = (MaterialEditText)findViewById(R.id.textCarPlate);

        spCarMake.setOnItemSelectedListener(this);

        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        years.add("Select Year");
        for (int i = 1990; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                R.layout.simple_spinner_item, years);
        adaptador.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        spCarYear.setAdapter(adaptador);


        String[] makes = getResources().getStringArray(R.array.Make);
        ArrayAdapter<String> adaptador1 = new ArrayAdapter<String>(this,
                R.layout.simple_spinner_item, makes);
        adaptador1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spCarMake.setAdapter(adaptador1);

        ConfirmCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String plate = textCarPlate.getText().toString().trim();


                boolean flag = false;


                String make = spCarMake.getSelectedItem().toString();
                Object mod = spCarModel.getSelectedItem();
                String year = spCarYear.getSelectedItem().toString();

                if ((make == "Select Make")  || (year == "Select Year") || (mod == null || mod.toString() == "Select Model")) {
                    Toast.makeText(AddCarForm.this, "Add All Fields", Toast.LENGTH_LONG).show();
                    flag = true;

                }

                if (plate.isEmpty()) {
                    textCarPlate.setError("Field is empty");
                    flag = true;
                }else if (!Regi_Pat.matcher(plate).matches()) {
                    textCarPlate.setError("Invalid Registration Plate");
                    flag = true;
                }

                else{
                    if (!flag) {
                        vehicle newcar = new vehicle();
                        newcar.vmake = String.valueOf(spCarMake.getSelectedItem());
                        newcar.vmodel = String.valueOf(spCarModel.getSelectedItem());
                        newcar.vyear = String.valueOf(spCarYear.getSelectedItem());
                        try {

                            table_user.child(UserName).child("vehicle").child(textCarPlate.getText().toString()).setValue(newcar);
                            Intent home = new Intent(AddCarForm.this, home.class);

                            home.putExtra("extra", UserName);
                            //home.putExtra("addcar",textCarPlate.getText().toString());
                            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            finish();
                            startActivity(home);
                        }
                        catch (Exception e) {

                            e.printStackTrace();
                        }

//                        table_user.child(UserName).child("vehicle").child(textCarPlate.getText().toString()).setValue(newcar);

//                        Intent home = new Intent(AddCarForm.this, home.class);
//
//                        home.putExtra("extra", UserName);
//                        //home.putExtra("addcar",textCarPlate.getText().toString());
//                        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//                        finish();
//                        startActivity(home);

                    }
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String make = String.valueOf(spCarMake.getSelectedItem());

        if(!make.equals("Select Make")){

            int resourceId = 0;
            try {
                resourceId = R.array.class.getField(make).getInt(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            String[] model = getResources().getStringArray(resourceId);

            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                    R.layout.simple_spinner_item, model);
            adaptador.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
            adaptador.notifyDataSetChanged();

            spCarModel.setAdapter(adaptador);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
