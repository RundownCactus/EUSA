package com.salmanqureshi.eusa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chat extends AppCompatActivity {
    ImageView imageViewBackArrowChat,sendmessageicon;
    EditText sendermessageedittext;
    DatabaseReference mChatDb,orderedService,serviceKey;
    List<ChatObject> mchat;
    ChatAdapter adapter;
    RecyclerView messageRV;
    TextView textViewChatName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        textViewChatName=findViewById(R.id.textViewChatName);
        imageViewBackArrowChat=findViewById(R.id.imageViewBackArrowChat);
        sendermessageedittext=findViewById(R.id.sendermessageedittext);
        sendmessageicon=findViewById(R.id.sendmessageicon);
        mchat = new ArrayList<>();
        //orderedService= FirebaseDatabase.getInstance().getReference("Chat");
        //serviceKey=orderedService.push();
        mChatDb=FirebaseDatabase.getInstance().getReference("Chat").child(getIntent().getStringExtra("myChatKey"));
        textViewChatName.setText(getIntent().getStringExtra("spname"));

        messageRV=findViewById(R.id.messageRV);
        messageRV.setHasFixedSize(true);
        RecyclerView.LayoutManager lm= new LinearLayoutManager(this);
        messageRV.setLayoutManager(lm);
        adapter=new ChatAdapter(mchat,this);
        messageRV.setAdapter(adapter);
    }
    private void getMessages() {
        mChatDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()) {
                    String msg = "";
                    String sndr = "";
                    if (snapshot.child("text").getValue() != null) {
                        msg = snapshot.child("text").getValue().toString();
                    }
                    if (snapshot.child("creator").getValue() != null) {
                        sndr = snapshot.child("creator").getValue().toString();
                    }

                    ChatObject Message = new ChatObject(sndr, snapshot.getKey(), msg);
                    mchat.add(Message);
                    messageRV.getLayoutManager().scrollToPosition(mchat.size()-1);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void SendMessage() {
        sendermessageedittext=findViewById(R.id.sendermessageedittext);
        if(!sendermessageedittext.getText().toString().isEmpty()){
            String mId = mChatDb.push().getKey();
            DatabaseReference mref = mChatDb.child(mId);

            Map mHash = new HashMap<>();
            mHash.put("text",sendermessageedittext.getText().toString());
            mHash.put("creator", FirebaseAuth.getInstance().getUid());
            //new SendNotif(sendermessageedittext.getText().toString(),"New Message",notificationKey);
            //mref.updateChildren(mHash);
            updateDB(mref,mHash);
        }
        sendermessageedittext.setText(null);
    }
    private void updateDB(DatabaseReference mref,Map msg){
        mref.updateChildren(msg);
        sendermessageedittext.setText(null);
        //mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        imageViewBackArrowChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        sendmessageicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=sendermessageedittext.getText().toString();
                if(!msg.equals(""))
                {
                    SendMessage();
                }
                else
                {
                    Toast.makeText(Chat.this,"Can't send empty message.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        getMessages();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}