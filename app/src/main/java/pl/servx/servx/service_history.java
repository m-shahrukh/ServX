package pl.servx.servx;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;

import pl.servx.servx.Model.MyAdaptor;
import pl.servx.servx.Model.SharePref;
import pl.servx.servx.Model.service_item;

public class service_history extends AppCompatActivity {

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<service_item> list;
    MyAdaptor adaptor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_history);
        list = new ArrayList<service_item>();

        recyclerView = (RecyclerView)findViewById(R.id.reqs);

        SharePref sharePref = new SharePref();
        final String UserName = sharePref.getData(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("requests");
        //table_user.child(UserName)

        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds:dataSnapshot.child(UserName).getChildren()){

                    service_item s = ds.getValue(service_item.class);
                    if (!s.status.equals("Pending")){
                        if (s.status.equals("Accept")) {
                            s.status = s.status + "ed";
                        }
                        else{

                            s.status = s.status+"d";
                        }
                    }

                    String compare = "0";
                    compare = '"'+compare+'"';

                    if (!compare.equals(ds.getKey())){

                        String req = ds.getKey();
                        Integer x = Integer.parseInt(req);
                        s.reqid = x;
                        list.add(s);
                    }

                }
                Collections.sort(list, Collections.reverseOrder());

                adaptor = new MyAdaptor(service_history.this, list);
                LinearLayoutManager manager = new LinearLayoutManager(service_history.this);
                recyclerView.setLayoutManager(manager);

                recyclerView.setLayoutManager(new LinearLayoutManager(service_history.this));
                recyclerView.setAdapter(adaptor);
                adaptor.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
