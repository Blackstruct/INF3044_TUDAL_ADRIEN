package com.example.kevin.yoapp;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class pokemontrue extends AppCompatActivity {


    public static final String POKE_UPDATE="com.example.kevin.MainActivity.POKE_UPDATE";
    public class PokeUpdate extends BroadcastReceiver{
        public void onReceive(Context context, Intent intent){
            notificatoin_test();
            TextView pokname=(TextView)findViewById(R.id.pokename);
            JSONObject po=getBiersFromFile();
            try {
                pokname.setText(po.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            TextView dl=(TextView)findViewById(R.id.dlc);
            dl.setVisibility(View.VISIBLE);
            pokname.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemontrue);
        IntentFilter intentFilter=new IntentFilter(POKE_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new PokeUpdate(),intentFilter);
        TextView pokname=(TextView)findViewById(R.id.pokename);
        pokname.setText("bulbi");

    }

    public JSONObject getBiersFromFile(){
        try{
            InputStream is=new FileInputStream(getCacheDir()+"/"+"poke.json");
            byte[] buffer=new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONObject(new String(buffer,"UTF-8"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new JSONObject();
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONObject();
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    public void notificatoin_test(){
        NotificationCompat.Builder po= new NotificationCompat.Builder(this);
        po.setSmallIcon(R.mipmap.ic_launcher).setContentTitle("yolo").setContentText("telechargement fini");

        NotificationManager pl=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        pl.notify(153, po.build());
    }

    public void ask_press(View v){


        serpok.startActionFoo(this);
    }

}

