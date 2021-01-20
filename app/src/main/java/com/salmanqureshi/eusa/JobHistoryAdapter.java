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

public class JobHistoryAdapter extends RecyclerView.Adapter<JobHistoryAdapter.MyViewHolder> {
    List<JobHistory> newList;
    Context c;
    private OnItemClickListener mListener;
    public JobHistoryAdapter(List<JobHistory> newList, Context c) {
        this.c=c;
        this.newList=newList;
    }

    public void filterList(ArrayList<JobHistory> newList) {
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
    public JobHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemrow= LayoutInflater.from(c).inflate(R.layout.history_row,parent,false);
        return new MyViewHolder(itemrow,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull JobHistoryAdapter.MyViewHolder holder, int position) {
        holder.dateTime.setText(newList.get(position).getDateTime());
        holder.price.setText(newList.get(position).getPrice());
        holder.jobId.setText(newList.get(position).getJobId());
        holder.status.setText(newList.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return newList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTime;
        public TextView price;
        public TextView jobId;
        public TextView status;
        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            dateTime=itemView.findViewById(R.id.jobbookdate_history);
            price=itemView.findViewById(R.id.totalprice_history);
            jobId=itemView.findViewById(R.id.jobid_history);
            status=itemView.findViewById(R.id.status_history);
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

