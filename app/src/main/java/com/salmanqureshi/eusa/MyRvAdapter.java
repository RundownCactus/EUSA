package com.salmanqureshi.eusa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyViewHolder> {
    List<Contact> newList;
    Context c;
    private OnItemClickListener mListener;
    public MyRvAdapter(List<Contact> newList, Context c) {
        this.c=c;
        this.newList=newList;
    }

    public void filterList(ArrayList<Contact> newList) {
        this.newList=newList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    @NonNull
    @Override
    public MyRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemrow= LayoutInflater.from(c).inflate(R.layout.row,parent,false);
        return new MyViewHolder(itemrow,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRvAdapter.MyViewHolder holder, int position) {
        //holder.newimage.setImageBitmap(newList.get(position).getImage());
        holder.name.setText(newList.get(position).getFname());
        holder.phone.setText(newList.get(position).getPhone());
        holder.email.setText(newList.get(position).getEmail());
       // holder.address.setText(newList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return newList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView newimage;
        public TextView name;
        public TextView phone;
        public TextView email;
        public TextView address;
        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            newimage=itemView.findViewById(R.id.newimage);
            name=itemView.findViewById(R.id.name);
            phone=itemView.findViewById(R.id.phone);
            email=itemView.findViewById(R.id.email);
            address=itemView.findViewById(R.id.address);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}