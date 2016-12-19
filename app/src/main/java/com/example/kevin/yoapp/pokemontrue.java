package com.example.kevin.yoapp;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.net.HttpURLConnection;
import java.net.URL;

public class pokemontrue extends AppCompatActivity  {


    public static final String POKE_UPDATE = "com.example.kevin.MainActivity.POKE_UPDATE";
    public String id;
    public class PokeUpdate extends BroadcastReceiver implements LoadImageTask.Listener {
        public void onReceive(Context context, Intent intent) {
            notificatoin_test();
            TextView pokname = (TextView) findViewById(R.id.pokename);
            ImageView imdl = (ImageView) findViewById(R.id.imagepoke);
            JSONObject po = getBiersFromFile();
            try {
                pokname.setText(po.getString("name"));
                new LoadImageTask(this).execute(po.getJSONObject("sprites").getString("front_default"));
                //imdl.setImageBitmap(getBitmapFromURL(po.getJSONObject("sprites").getString("front_default")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            TextView dl = (TextView) findViewById(R.id.dlc);
            dl.setVisibility(View.VISIBLE);
            pokname.setVisibility(View.VISIBLE);
            imdl.setVisibility(View.VISIBLE);
        }

        public void onError() {
        }

        public void onImageLoaded(Bitmap bitmap) {
            ImageView imdl = (ImageView) findViewById(R.id.imagepoke);
            imdl.setImageBitmap(bitmap);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemontrue);
        IntentFilter intentFilter = new IntentFilter(POKE_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new PokeUpdate(), intentFilter);
        TextView pokname = (TextView) findViewById(R.id.pokename);
        pokname.setText("bulbi");

    }

    public JSONObject getBiersFromFile() {
        try {
            InputStream is = new FileInputStream(getCacheDir() + "/" + "poke.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONObject(new String(buffer, "UTF-8"));
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

    public void notificatoin_test() {
        NotificationCompat.Builder po = new NotificationCompat.Builder(this);
        po.setSmallIcon(R.mipmap.ic_launcher).setContentTitle("yolo").setContentText("telechargement fini");

        NotificationManager pl = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        pl.notify(153, po.build());
    }

    public void ask_press(View v) {
        EditText rectext=(EditText) findViewById(R.id.aspokname);
        serpok.ids=rectext.getText().toString();
        Toast.makeText(getApplicationContext(),getString(R.string.whopoke),Toast.LENGTH_LONG).show();
        serpok.startActionFoo(this);
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            InputStream input = conn.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
