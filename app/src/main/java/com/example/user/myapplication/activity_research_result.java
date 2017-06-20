package com.example.user.myapplication;

import android.os.Handler;
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
    List<Temple> lsTemple;
    private TempleArrayAdopt adapter = null;
    private static final int LIST_TEMP = 1;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case LIST_TEMP:
                    List<Temple> temples = (List<Temple>)msg.obj;
                    refreshTempleList(temples);
                    break;
            }
        }
    };

    private void refreshTempleList(List<Temple> temples){
        adapter.clear();
        adapter.addAll(temples);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research_result);

        ListView lvTemple = (ListView)findViewById(R.id.lv);

        adapter = new TempleArrayAdopt(this, new ArrayList<Temple>());
        lvTemple.setAdapter(adapter);

        getTempleFromFirebase();
    }

    private void getTempleFromFirebase(){
        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {new FirebaseThread(dataSnapshot).start();}

            @Override
            public void onCancelled(DatabaseError databaseError){Log.v("Temple",databaseError.getMessage());}
        });




    }

    class FirebaseThread extends Thread{
        private DataSnapshot dataSnapshot;

        public FirebaseThread(DataSnapshot dataSnapshot){
            this.dataSnapshot = dataSnapshot;
        }

        @Override
        public void run() {
            lsTemple = new ArrayList<>();
            for(DataSnapshot ds : dataSnapshot.getChildren()){
                DataSnapshot dsSName = ds.child("寺 廟 名 稱");
                DataSnapshot  dsSKine = ds.child("奉祀主神");
                DataSnapshot dsSAdrress = ds.child("寺 廟 住 址");

                String name =(String)dsSName.getValue();
                name = name.replaceAll("\\s", "");
                String kind =(String)dsSKine.getValue();
                String address =(String)dsSAdrress.getValue();

                if (name.equals("") || kind.equals("") || address.equals("")){
                    continue;
                }

                if(name.length() > 10){
                    name = name.substring(0, 10) + "\n" + name.substring(10, name.length());
                }

                Temple aTemple = new Temple();
                aTemple.setName(name);
                aTemple.setKind(kind);
                aTemple.setAddress(address);
                lsTemple.add(aTemple);

                Log.v("Temple",name + ";" + kind +";" +address);

                Message msg = new Message();
                msg.what = LIST_TEMP;
                msg.obj = lsTemple;
                handler.sendMessage(msg);
            }
        }
    }
}
