package com.example.kevin.yoapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }

    public void pressSearchBtn(View v){
        EditText rectext=(EditText) findViewById(R.id.city);
        String in=rectext.getText().toString();
        Toast.makeText(getApplicationContext(),getString(R.string.voyagebyby),Toast.LENGTH_LONG).show();
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+in)));
    }
}
