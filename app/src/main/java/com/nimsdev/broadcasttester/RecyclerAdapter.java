package com.nimsdev.broadcasttester;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewholder> {

    private ArrayList<IncomingNumber> arrayList = new ArrayList<>();

    public RecyclerAdapter(ArrayList<IncomingNumber> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {
        holder.ID.setText(Integer.toString(arrayList.get(position).getId()));
        holder.NUMBER.setText(arrayList.get(position).getNumber());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewholder extends RecyclerView.ViewHolder {
        TextView ID, NUMBER;
        public MyViewholder(View itemView) {
            super(itemView);
            ID = (TextView)itemView.findViewById(R.id.txtId);
            NUMBER = (TextView)itemView.findViewById(R.id.txtNumber);
        }
    }
}
