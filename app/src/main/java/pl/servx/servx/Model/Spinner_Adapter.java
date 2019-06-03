package pl.servx.servx.Model;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import pl.servx.servx.R;

public class Spinner_Adapter extends ArrayAdapter<String> {
    Context context;
    ArrayList<String> iName;
    ArrayList<String> iAbout;

    TextView spnItemName,spnItemDel;
    ImageView spnItemIcon;

    public Spinner_Adapter(Context context, int textViewResourceId, ArrayList<String> objects, ArrayList<String> iName){
        super(context,textViewResourceId,objects);
        this.context = context;
        this.iName = iName;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View rowView =  inflater.inflate(R.layout.spinner_row, parent, false);

        spnItemName = (TextView) rowView.findViewById(R.id.spnItemName);
        spnItemDel = (TextView) rowView.findViewById(R.id.spnItemDel);
        if ((iName.get(position)+"").equals("Select Car")){
            spnItemDel.setText("");
            spnItemName.setText(iName.get(position)+"");

            return  rowView;
        }
        spnItemName.setText(iName.get(position)+"");

        final FirebaseAuth mAuth= FirebaseAuth.getInstance();
        final FirebaseUser check= mAuth.getCurrentUser();
        final String user=check.getDisplayName();

        spnItemDel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String spinner_item= iName.get(position)+"";
                Log.i("log", spinner_item);
//                iName.remove(position);
//                notifyDataSetChanged();
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setMessage("Do You Want To Remove '" + spinner_item + "' From The List?");
                alert.setCancelable(true);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase database= FirebaseDatabase.getInstance();

                        DatabaseReference db= database.getReference("User").child(user).child("vehicle");
 //                       }
                        try{
                        db.child(spinner_item).removeValue();
                            Toast.makeText(context, "Car Deleted", Toast.LENGTH_SHORT).show();
                                iName.remove(position);
                                notifyDataSetChanged();}
                        catch (Exception e) {
                            e.printStackTrace();
                        }
//                        db.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                dataSnapshot.getRef().child(spinner_item).removeValue();
//                                Toast.makeText(context, "Car Deleted", Toast.LENGTH_SHORT).show();
//                                iName.remove(position);
//                                notifyDataSetChanged();
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
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
        });
        return rowView;
    }
}