package pl.servx.servx;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import pl.servx.servx.Model.cart_data;


public class serv_frag1 extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_serv_frag1, container, false);
        Button btn1= (Button) rootView.findViewById(R.id.btnP1);
        Button btn2= (Button) rootView.findViewById(R.id.btnP2);
        Button btn3= (Button) rootView.findViewById(R.id.btnP3);
        Button btnm1= (Button) rootView.findViewById(R.id.btnM1);
        Button btnm2= (Button) rootView.findViewById(R.id.btnM2);
        Button btnm3= (Button) rootView.findViewById(R.id.btnM3);
        Button btn_cart=(Button) rootView.findViewById(R.id.btn_cart);
        ArrayList<String> orders= new ArrayList<>();
        final int quant1,quant2,quant3;
        quant1=quant2=quant3=0;


        if (getArguments().getString("flag")=="OilChange") {
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cart_data.OilChange = "Gold";
                    cart_data.costOil=cart_data.OilGold;
                    cart_data.dict.put("Oil","Oil Change: Gold Package, "+cart_data.costOil+" Rs" );
                    Toast.makeText(getActivity(), "Added to cart", Toast.LENGTH_SHORT).show();
                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cart_data.OilChange = "Silver";
                    cart_data.costOil=cart_data.OilSilver;
                    cart_data.dict.put("Oil","Oil Change: Silver Package, "+cart_data.costOil+" Rs" );
                    Toast.makeText(getActivity(), "Added to cart", Toast.LENGTH_SHORT).show();
                }
            });
            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cart_data.OilChange = "Bronze";
                    cart_data.costOil=cart_data.OilBronze;
                    cart_data.dict.put("Oil","Oil Change: Bronze Package, "+cart_data.costOil+" Rs" );
                    Toast.makeText(getActivity(), "Added to cart", Toast.LENGTH_SHORT).show();
                }
            });
            btnm1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cart_data.OilChange = "";
                    cart_data.costOil=0;
                    cart_data.dict.remove("Oil");
                    Toast.makeText(getActivity(), "Removed from cart", Toast.LENGTH_SHORT).show();
                }
            });
            btnm2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cart_data.OilChange = "";
                    cart_data.costOil=0;
                    cart_data.dict.remove("Oil");
                    Toast.makeText(getActivity(), "Removed from cart", Toast.LENGTH_SHORT).show();
                }
            });
            btnm3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cart_data.OilChange = "";
                    cart_data.costOil=0;
                    cart_data.dict.remove("Oil");
                    Toast.makeText(getActivity(), "Removed from cart", Toast.LENGTH_SHORT).show();
                }
            });
        }
            else{
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cart_data.CarWash="Gold";
                        cart_data.costWash=cart_data.CarGold;
                        cart_data.dict.put("Wash","Car Wash: Gold Package, "+cart_data.costWash+" Rs" );
                        Toast.makeText(getActivity(), "Added to cart", Toast.LENGTH_SHORT).show();
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cart_data.CarWash="Silver";
                        cart_data.costWash=cart_data.CarSilver;
                        cart_data.dict.put("Wash","Car Wash: Silver Package, "+cart_data.costWash+" Rs" );
                        Toast.makeText(getActivity(), "Added to cart", Toast.LENGTH_SHORT).show();
                    }
                });
                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cart_data.CarWash="Bronze";
                        cart_data.costWash=cart_data.CarBronze;
                        cart_data.dict.put("Wash","Car Wash: Bronze Package, "+cart_data.costWash+" Rs" );
                        Toast.makeText(getActivity(), "Added to cart", Toast.LENGTH_SHORT).show();
                    }
                });
                btnm1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cart_data.CarWash="";
                        cart_data.costWash=0;
                        cart_data.dict.remove("Wash");
                        Toast.makeText(getActivity(), "Removed from cart", Toast.LENGTH_SHORT).show();
                    }
                });
                btnm2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cart_data.CarWash="";
                        cart_data.costWash=0;
                        cart_data.dict.remove("Wash");
                        Toast.makeText(getActivity(), "Removed from cart", Toast.LENGTH_SHORT).show();
                    }
                });
                btnm3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cart_data.CarWash="";
                        cart_data.costWash=0;
                        cart_data.dict.remove("Wash");
                        Toast.makeText(getActivity(), "Removed from cart", Toast.LENGTH_SHORT).show();
                    }
                });

        }

        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Cart= new Intent(getActivity(),Cart.class);
                startActivity(Cart);
            }
        });








        /////////////////////////////////////////////////////////
        final TextView p_info= (TextView) rootView.findViewById(R.id.p_info);
        final TextView p_info2= (TextView) rootView.findViewById(R.id.p_info2);
        final TextView p_info3= (TextView) rootView.findViewById(R.id.p_info3);
        final TextView p_cost= (TextView) rootView.findViewById(R.id.p_cost);
        final TextView p_cost2= (TextView) rootView.findViewById(R.id.p_cost2);
        final TextView p_cost3= (TextView) rootView.findViewById(R.id.p_cost3);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final ArrayList<String> gold_list= new ArrayList<>();
        final ArrayList<String> silver_list= new ArrayList<>();
        final ArrayList<String> bronze_list= new ArrayList<>();
        final DatabaseReference ref= database.getReference("Packages");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (getArguments().getString("flag")=="CarWash") {


                    for (DataSnapshot servSnapshot : dataSnapshot.child("CarWash").child("Gold").child("Services").getChildren()) {
                        String txt = servSnapshot.getValue(String.class);
                        gold_list.add(txt);
                    }
                    //int siz=gold_list.size();
                    String to_disp=prepare_string(gold_list);

//                    for (int i=0; i<siz-1; i++) { to_disp= to_disp+ gold_list.get(i)+" <br/>"; }
//                    if (siz>0){to_disp+=gold_list.get(siz-1);}


                    for (DataSnapshot servSnapshot : dataSnapshot.child("CarWash").child("Silver").child("Services").getChildren()) {
                        String txt = servSnapshot.getValue(String.class);
                        silver_list.add(txt);
                    }
                    //int siz2= silver_list.size();
                    String to_disp2=prepare_string(silver_list);
//                    for (int i=0; i<siz2-1; i++) { to_disp2= to_disp2+ silver_list.get(i)+" <br/>"; }
//                    if (siz2>0){to_disp2+=silver_list.get(siz2-1);}


                    for (DataSnapshot servSnapshot : dataSnapshot.child("CarWash").child("Bronze").child("Services").getChildren()) {
                        String txt = servSnapshot.getValue(String.class);
                        bronze_list.add(txt);
                    }
                    //int siz3= bronze_list.size();
                    String to_disp3=prepare_string(bronze_list);



                    p_info.setText(Html.fromHtml(to_disp));
                    p_info2.setText(Html.fromHtml(to_disp2));
                    p_info3.setText(Html.fromHtml(to_disp3));
                    cart_data.CarGold=Integer.parseInt(dataSnapshot.child("CarWash").child("Gold").child("Cost").getValue(String.class));
                    cart_data.CarSilver=Integer.parseInt(dataSnapshot.child("CarWash").child("Silver").child("Cost").getValue(String.class));
                    cart_data.CarBronze = Integer.parseInt(dataSnapshot.child("CarWash").child("Bronze").child("Cost").getValue(String.class));

                    p_cost.setText(cart_data.CarGold+"");
                    p_cost2.setText(cart_data.CarSilver+"");
                    p_cost3.setText(cart_data.CarBronze+"");

                }
                else {
                    for (DataSnapshot servSnapshot : dataSnapshot.child("OilChange").child("Gold").child("Services").getChildren()) {
                        String txt = servSnapshot.getValue(String.class);
                        gold_list.add(txt);
                    }

                    String to_disp= prepare_string(gold_list);
                    for (DataSnapshot servSnapshot : dataSnapshot.child("OilChange").child("Silver").child("Services").getChildren()) {
                        String txt = servSnapshot.getValue(String.class);
                        silver_list.add(txt);
                    }
                    String to_disp2= prepare_string(silver_list);
                    for (DataSnapshot servSnapshot : dataSnapshot.child("OilChange").child("Bronze").child("Services").getChildren()) {
                        String txt = servSnapshot.getValue(String.class);
                        bronze_list.add(txt);
                    }
                    String to_disp3= prepare_string(bronze_list);

                    p_info.setText(Html.fromHtml(to_disp));
                    p_info2.setText(Html.fromHtml(to_disp2));
                    p_info3.setText(Html.fromHtml(to_disp3));

                    cart_data.OilGold= Integer.parseInt(dataSnapshot.child("OilChange").child("Gold").child("Cost").getValue(String.class));
                    cart_data.OilSilver=Integer.parseInt(dataSnapshot.child("OilChange").child("Silver").child("Cost").getValue(String.class));
                    cart_data.OilBronze= Integer.parseInt(dataSnapshot.child("OilChange").child("Bronze").child("Cost").getValue(String.class));

                    p_cost.setText(cart_data.OilGold+"");
                    p_cost2.setText(cart_data.OilSilver+"");
                    p_cost3.setText(cart_data.OilBronze+"");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"error fetching from DB", Toast.LENGTH_LONG).show();

            }
        });

        return rootView;
        //return inflater.inflate(R.layout.fragment_serv_frag1, container, false);
    }


    private String prepare_string(ArrayList<String> list){
        int siz=list.size();
        String to_disp="";
        for (int i=0; i<siz-1; i++) { to_disp= to_disp+ list.get(i)+" <br/>"; }
        if (siz>0){to_disp+=list.get(siz-1);}

        return to_disp;
    }
    @Override
    public  void onViewCreated(View view , Bundle savedInstanceState) {

//        super.onViewCreated(view, savedInstanceState);
//        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
//        // specify an adapter (see also next example)
//        mAdapter = new MyAdapter(myDataset);
//        recyclerView.setAdapter(mAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
