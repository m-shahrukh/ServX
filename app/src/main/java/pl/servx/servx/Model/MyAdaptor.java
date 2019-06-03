package pl.servx.servx.Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import pl.servx.servx.R;

public class MyAdaptor extends RecyclerView.Adapter<MyAdaptor.MyViewHolder> {

    public Context context;
    public ArrayList<service_item> items;

    public MyAdaptor(Context c, ArrayList<service_item> i){
        context = c;
        items = i;
    }

    @NonNull
    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cards_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.car.setText(items.get(position).getCar());
        holder.package_oil.setText(items.get(position).getOil());
        holder.package_car_wash.setText(items.get(position).getWash());
        holder.date.setText(items.get(position).getDate());
        holder.time.setText(items.get(position).getTime());
        holder.status.setText(items.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView package_oil,package_car_wash,date,time,status, car;
        public MyViewHolder(View itemView) {
            super(itemView);
            car = itemView.findViewById(R.id.car);
            package_car_wash = itemView.findViewById(R.id.package_car_wash);
            package_oil = itemView.findViewById(R.id.package_oil);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            status = itemView.findViewById(R.id.status);
        }

    }




}
/*
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cards_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        holder..

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

class MyViewHolder extends RecyclerView.ViewHolder{

    TextView package_oil,package_car_wash,date,time,status;
    public MyViewHolder(View itemView) {
        super(itemView);
        package_car_wash = itemView.findViewById(R.id.package_car_wash);
        package_oil = itemView.findViewById(R.id.package_oil);
        date = itemView.findViewById(R.id.date);
        time = itemView.findViewById(R.id.time);
        status=itemView.findViewById(R.id.status);
    }
}
*/

