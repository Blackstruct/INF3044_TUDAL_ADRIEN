package com.example.kevin.yoapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private AutoCompleteTextView complete = null;

    Dialog dial = null;

    private static final String TAG="MainAct";
    private DatePickerDialog dpd=null;
    public   RecyclerView rvi=null;
    public static final String BIERS_UPDATE="com.example.kevin.MainActivity.BIERS_UPDATE";
    public class BierUpdate extends BroadcastReceiver{
        public void onReceive(Context context, Intent intent){
            notifBeerDl();
            BiersAdapter a=(BiersAdapter)rvi.getAdapter();
            a.setNewBiere();
        }
    }
    private class BiersAdapter extends RecyclerView.Adapter<BiersAdapter.BierHolder>{

        private JSONArray biere=null;

        private BiersAdapter(JSONArray beer){
            this.biere=beer;
        }

        private void setNewBiere(){
            this.biere=getBiersFromFile();
            notifyDataSetChanged();
            Log.d(TAG, String.valueOf(getItemCount()));
        }

        @Override
        public BierHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater lf=LayoutInflater.from(parent.getContext());
            View vvvvvvvvvvvvv=lf.inflate(R.layout.rv_biere_element,parent,false);
            return new BierHolder(vvvvvvvvvvvvv);
        }

        @Override
        public void onBindViewHolder(BierHolder holder, int position) {
            try {
                JSONObject js=biere.getJSONObject(position);
                String nom=js.getString("name");
                holder.name.setText(nom);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return biere.length();
        }

        public class BierHolder extends RecyclerView.ViewHolder{
            public  TextView name;
            private BierHolder(View itemView) {
                super(itemView);
                name=(TextView)itemView.findViewById(R.id.rv_biere_element_name);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button btn_test=(Button)findViewById(R.id.button);
        DatePickerDialog.OnDateSetListener odsl=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                btn_test.setText(dayOfMonth+"/"+month+"/"+year);
            }
        };
        dpd=new DatePickerDialog(this, odsl, 2000, 10, 20);
        IntentFilter intentFilter=new IntentFilter(BIERS_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BierUpdate(),intentFilter);
        rvi=(RecyclerView)findViewById(R.id.rv_biere);
        rvi.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvi.setAdapter(new BiersAdapter(getBiersFromFile()));

        /* Dialog couleur */
        dial = new Dialog(this);
        dial.setContentView(R.layout.color_dialog);
        Button okButton = (Button) dial.findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),getString(R.string.quit_dial),Toast.LENGTH_LONG).show();
                dial.dismiss();
            }
        });
    }

    public void pressFavColorBtn(View v)
    {
        Context context = MainActivity.this;
        String[] colorList = new String[]{
                context.getString(R.string.blue),
                context.getString(R.string.green),
                context.getString(R.string.yellow),
                context.getString(R.string.red),
                context.getString(R.string.purple),
                context.getString(R.string.beige),
                context.getString(R.string.black),
                context.getString(R.string.brown),
                context.getString(R.string.golden),
                context.getString(R.string.mauve),
                context.getString(R.string.navy_blue),
                context.getString(R.string.pink),
                context.getString(R.string.silver),
                context.getString(R.string.turquoise),
                context.getString(R.string.white),
                context.getString(R.string.grey),
                context.getString(R.string.orange)
        };

        complete = (AutoCompleteTextView) dial.findViewById(R.id.complete);
        complete.setThreshold(1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, colorList);
        complete.setAdapter(adapter);

        dial.show();
    }

    public void notifBeerDl(){
        NotificationCompat.Builder po= new NotificationCompat.Builder(this);
        po.setSmallIcon(R.mipmap.ic_launcher).setContentTitle("beer.json").setContentText(getString(R.string.end_dl));

        NotificationManager pl=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        pl.notify(153, po.build());
    }

    public void pressBeerBtn(View v){
        if(rvi.getVisibility()==View.INVISIBLE) {
            Yoservice.startActionFoo(this);
            rvi.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),getString(R.string.beer),Toast.LENGTH_LONG).show();
        }else{
            rvi.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),getString(R.string.disbeer),Toast.LENGTH_LONG).show();
        }
    }

    public void pressHitBtn(View v){
        String mot[]=new String[4];
        mot[0]=getString(R.string.frap0);
        mot[1]=getString(R.string.frap1);
        mot[2]=getString(R.string.frap2);
        mot[3]=getString(R.string.frap3);
        Random rand=new Random();
        Toast.makeText(getApplicationContext(),mot[rand.nextInt(4)],Toast.LENGTH_LONG).show();
    }

    public void pressToastBtn(View v){
        Toast.makeText(getApplicationContext(),getString(R.string.msg),Toast.LENGTH_LONG).show();
    }

    public void pressPkmBtn(View v){
        Toast.makeText(getApplicationContext(),getString(R.string.poke),Toast.LENGTH_LONG).show();
        startActivity(new Intent(this,PokemonTrue.class));
    }

    public void pressMapBtn(View v){
        Toast.makeText(getApplicationContext(),getString(R.string.voyage),Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, MapActivity.class));
    }

    public JSONArray getBiersFromFile(){
        try{
            InputStream is=new FileInputStream(getCacheDir()+"/"+"biere.json");
            byte[] buffer=new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONArray(new String(buffer,"UTF-8"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new JSONArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONArray();
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

}
