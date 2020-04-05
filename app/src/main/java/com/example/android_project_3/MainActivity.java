package com.example.android_project_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //TextView tv;
    JSONArray jsonArray;
    private static final String TAG = "ParseJSON";
    int numberentries = -1;
    //int currententry = -1;
    private SharedPreferences myPreference;
    private SharedPreferences.OnSharedPreferenceChangeListener listener = null;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myPreference=PreferenceManager.getDefaultSharedPreferences(this);

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            //Removed override statement since not part of example
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals("listPref")) {
                    //loadImage();
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            doPreferences();
            return true;
        }

        //all else fails let super handle it
        return super.onOptionsItemSelected(item);
    }

    private void doPreferences() {
        Intent myintent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(myintent);
    }

    public void processJSON(String string) {

        try {
            JSONObject jsonobject = new JSONObject(string);
            jsonArray = jsonobject.getJSONArray("pets");

            //Removed indenting
            Log.d(TAG,jsonArray.toString());
            numberentries = jsonArray.length();

            setupSimpleSpinner();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setupSimpleSpinner() {

        spinner = (Spinner)findViewById(R.id.spinner);

        ArrayList<String> availablePets = new ArrayList<>();

        for (int i = 0; i < numberentries; i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                availablePets.add(jsonObject.getString("name"));
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }

        //Changed CharList from example to string
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, availablePets);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public static final int SELECTED_ITEM = 0;

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long rowid) {
                if (arg0.getChildAt(SELECTED_ITEM) != null) {
                    //TODO create method to set pet image
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

}
