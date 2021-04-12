package com.salmanqureshi.eusa;

import android.annotation.SuppressLint;
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


public class NearbySPAdapter extends RecyclerView.Adapter<NearbySPAdapter.MyViewHolder> {
    List<ServiceProvider> newList;
    Context c;
    private OnItemClickListener mListener;
    public NearbySPAdapter(List<ServiceProvider> newList, Context c) {
        this.c=c;
        this.newList=newList;
    }

    public void filterList(ArrayList<ServiceProvider> newList) {
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
    public NearbySPAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemrow= LayoutInflater.from(c).inflate(R.layout.nearby_sp_row,parent,false);
        return new MyViewHolder(itemrow,mListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NearbySPAdapter.MyViewHolder holder, int position) {
        holder.spname.setText(newList.get(position).getFname()+" "+newList.get(position).getLname());
        holder.spworktype.setText(newList.get(position).getWorktype());
        holder.sprating.setText(newList.get(position).getRating());
        //holder.spimage.setImageBitmap(newList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return newList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView spname;
        public TextView spworktype;
        public TextView sprating;
        //public ImageView spimage;
        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            spname=itemView.findViewById(R.id.spname);
            spworktype=itemView.findViewById(R.id.spworktype);
            sprating=itemView.findViewById(R.id.sprating);
            //spimage=itemView.findViewById(R.id.spimage);
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
