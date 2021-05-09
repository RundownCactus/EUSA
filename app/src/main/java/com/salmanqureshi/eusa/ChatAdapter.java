package com.salmanqureshi.eusa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    FirebaseUser fuser;
    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;
    List<ChatObject> newList;
    Context c;
    public ChatAdapter(List<ChatObject> newList, Context c) {
        this.c=c;
        this.newList=newList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==MSG_TYPE_RIGHT) {
            View itemrow = LayoutInflater.from(c).inflate(R.layout.chat_item_right, parent, false);
            return new MyViewHolder(itemrow);
        }
        else {
            View itemrow = LayoutInflater.from(c).inflate(R.layout.chat_item_left, parent, false);
            return new MyViewHolder(itemrow);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChatObject chat=newList.get(position);
        holder.show_message.setText(chat.getMessage());
    }

    @Override
    public int getItemCount() {
        return newList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView show_message;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message=itemView.findViewById(R.id.show_message);
        }
    }

    public int getItemViewType(int position)
    {
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        if(newList.get(position).getSender().equals(fuser.getUid()))
        {
            return MSG_TYPE_RIGHT;
        }
        else
        {
            return MSG_TYPE_LEFT;
        }
    }
}

