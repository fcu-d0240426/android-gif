package com.example.user.myapplication;

import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class activity_research_result extends AppCompatActivity {

    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research_result);

        ListView lvTemple = (ListView)findViewById(R.id.lv);

        TempleArrayAdopt adapt = new TempleArrayAdopt(this, new ArrayList<Temple>());
        lvTemple.setAdapter(adapt);

        test = (TextView)findViewById(R.id.test);

        getTempleFromFirebase();
    }

    private void getTempleFromFirebase(){
        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Temple> lsTemple = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                     DataSnapshot dsSName = ds.child("寺 廟 名 稱");
                     DataSnapshot  dsSKine = ds.child("奉祀主神");
                     DataSnapshot dsSAdrress = ds.child("寺 廟 住 址");

                     String name =(String)dsSName.getValue();
                     String kind =(String)dsSKine.getValue();
                     String address =(String)dsSAdrress.getValue();

                    Temple aTemple = new Temple();
                    aTemple.setName(name);
                    aTemple.setKind(kind);
                    aTemple.setAddress(address);
                    lsTemple.add(aTemple);

                    Log.v("Temple",name + ";" + kind +";" +address);


                    Message msg = new Message();
                    msg.what = List_Temple;
                    msg.obj = lsPets;
                    handler.sendMessage(msg);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                   Log.v("Temple",databaseError.getMessage());

            }
        });




    }


}
