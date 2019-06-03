package pl.servx.servx;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import pl.servx.servx.Model.SharePref;
import pl.servx.servx.Model.cart_data;
import pl.servx.servx.Model.request;

import static java.lang.String.*;

public class Cart extends AppCompatActivity {
    TextView date, time;
    String am_pm;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user1 = database.getReference("requests");
        final DatabaseReference requestID = database.getReference("requestID");

        requestID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cart_data.reqid = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        date = (TextView) findViewById(R.id.date_btn);
        time = (TextView) findViewById(R.id.time_btn);
        if (!cart_data.date.equals("")){
            date.setText(cart_data.date);
        }
        if (!cart_data.time.equals("")){
            time.setText(cart_data.time);
        }


        SharePref sharePref = new SharePref();
        final String UserName = sharePref.getData(this);

/*        Button back_button= (Button) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent services= new Intent(Cart.this, services_tabbed.class);
                startActivity(services);
                finish();
            }
        });*/

        Button gotomap = (Button) findViewById(R.id.map_btn);
        gotomap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent maps = new Intent(Cart.this, select_location.class);
                startActivity(maps);
                finish();
            }
        });

        Button btnConfirm = (Button) findViewById(R.id.btnConfirm);
        final TextView datetext = (TextView) findViewById(R.id.date_btn);
        final TextView timetext = (TextView) findViewById(R.id.time_btn);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!cart_data.date.equals("") && !cart_data.time.equals("")&& !cart_data.location.equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Cart.this);
                    builder.setCancelable(true);
                    builder.setTitle("Confirm Order");
                    //builder.setMessage("Confirm Order");
                    builder.setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                    request req = new request();
                                    req.location = cart_data.location;
                                    req.wash = cart_data.CarWash;
                                    req.oil = cart_data.OilChange;
                                    req.status = "Pending";
                                    req.date = cart_data.date;
                                    req.time = cart_data.time;
                                    req.car = cart_data.car;
                                    Toast.makeText(Cart.this, "Order Successful!", Toast.LENGTH_SHORT).show();
                                    String lol = cart_data.reqid;
                                    table_user1.child(UserName).child(lol).setValue(req);
                                    Integer newreq = Integer.parseInt(cart_data.reqid);
                                    newreq = newreq + 1;
                                    lol = valueOf(newreq);
                                    requestID.setValue(lol);

                                    Intent gohome = new Intent(Cart.this, home.class);
                                    gohome.putExtra("extra", UserName);
                                    //cart_data.dict.clear();
                                    cart_data.clear();
                                    gohome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    finish();
                                    startActivity(gohome);
                            }
                        });


                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                AlertDialog dialog = builder.create();
                dialog.show();

                }
                else{

                    if(cart_data.location.equals("")){
                        Toast.makeText(Cart.this, "Location Not Added", Toast.LENGTH_SHORT).show();
                    }

                    if(cart_data.date.equals("")){
                        //Toast.makeText(Cart.this, "Date Not Added", Toast.LENGTH_SHORT).show();
                        datetext.setError("Date Not Added");
                    }
                    if(cart_data.time.equals("")){
                        //Toast.makeText(Cart.this, "Time Not Added", Toast.LENGTH_SHORT).show();
                        timetext.setError("Time Not Added");
                    }

                }
            }
        });



        // perform click event on edit text
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(Cart.this,
                        android.R.style.Theme_Holo_Dialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                //cart_data.date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                cart_data.date= dayOfMonth+"-"+(monthOfYear + 1)+"-"+year;
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 10000);
                datePickerDialog.show();
            }
        });


        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(Cart.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                                if (selectedHour >= 12) {
                                    am_pm = "pm";
                                }

                                if (selectedHour < 12) {
                                    am_pm = "am";
                                }
                                time.setText(String.format("%02d:%02d", selectedHour, selectedMinute) + am_pm);
                                cart_data.time = selectedHour + ":" + selectedMinute + " " + am_pm;
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        TextView cost1 = (TextView) findViewById(R.id.cost1);
        TextView cost2 = (TextView) findViewById(R.id.cost2);
        TextView total_cost = (TextView) findViewById(R.id.total_cost);
        TextView tc_num = (TextView) findViewById(R.id.tc_num);

        TextView OC = (TextView) findViewById(R.id.pack1);
        TextView CW = (TextView) findViewById(R.id.pack2);
        OC.setText("");
        CW.setText("");
        cost1.setText("");
        cost2.setText("");
        ArrayList<TextView> texts = new ArrayList<>();
        ArrayList<TextView> costs = new ArrayList<>();
        texts.add(OC);
        texts.add(CW);
        costs.add(cost1);
        costs.add(cost2);
        int i = 0;
        for (Map.Entry mapElement : cart_data.dict.entrySet()) {
            String key = (String) mapElement.getKey();

            String value = (String) mapElement.getValue();
            String msg, cost;
            int comma = value.indexOf(",");
            msg = value.substring(0, comma);
            cost = value.substring(comma + 1, value.length());
            if (i==0) {
                msg= "Car: "+cart_data.car+" <br/> <br/>"+msg;
                texts.get(i).setText(Html.fromHtml(msg));
                cost= "<br/><br/>"+cost;
                costs.get(i).setText(Html.fromHtml(cost));
            }

            else { texts.get(i).setText(msg);
                costs.get(i).setText(cost);}
            i++;
        }

        tc_num.setText((cart_data.costWash + cart_data.costOil) + " Rs");

    }
}
