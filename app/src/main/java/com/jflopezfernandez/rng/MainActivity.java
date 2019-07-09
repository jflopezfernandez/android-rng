package com.jflopezfernandez.rng;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private int type;
    private int dist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(SettingsActivity.PREFERENCES, Context.MODE_PRIVATE);
        type = sharedPreferences.getInt(SettingsActivity.TYPE, SettingsActivity.Type.Integer.ordinal());
        dist = sharedPreferences.getInt(SettingsActivity.DIST, SettingsActivity.Distribution.Uniform.ordinal());

//        if (type == SettingsActivity.Type.Integer.ordinal()) {
//            //
//        } else if (type == SettingsActivity.Type.Real.ordinal()) {
//            //
//        }
//
//        if (dist == SettingsActivity.Distribution.Uniform.ordinal()) {
//            //
//        } else if (dist == SettingsActivity.Distribution.Normal.ordinal()) {
//            //
//        }

        Button buttonGenerateRandomNumber = findViewById(R.id.button_generate);
        buttonGenerateRandomNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateRandomNumber(type, dist);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_settings: {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
            } return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void generateRandomNumber(final int type, final int dist) {
        final Locale currentLocale = getResources().getConfiguration().getLocales().get(0);

        String randomNumber = null;

        if (type == SettingsActivity.Type.Integer.ordinal()) {
            if (dist == SettingsActivity.Distribution.Uniform.ordinal()) {
                randomNumber = String.format(currentLocale, "%d", uniformRandomInt());
            } else if (dist == SettingsActivity.Distribution.Normal.ordinal()) {
                // TODO: Disable normal distribution for integral type
                randomNumber = Integer.toString(uniformRandomInt());
            }
        } else if (type == SettingsActivity.Type.Real.ordinal()) {
            if (dist == SettingsActivity.Distribution.Uniform.ordinal()) {
                randomNumber = String.format(currentLocale, "%.2f", uniformRandomReal());
            } else if (dist == SettingsActivity.Distribution.Normal.ordinal()) {
                randomNumber = String.format(currentLocale, "%.2f", normalRandomReal());
            }
        }

        uniformRandomInt();
        updateOutputTextview(String.format(currentLocale, "%s", randomNumber));
    }

    private void updateOutputTextview(final String output) {
        TextView textViewOutput = findViewById(R.id.textview_result);

        if (output.length() > 2) {
            textViewOutput.setTextSize(getResources().getDimension(R.dimen.text_size_large));
        } else {
            textViewOutput.setTextSize(getResources().getDimension(R.dimen.text_size_large));
        }

        textViewOutput.setText(output);
    }

    public native int uniformRandomInt();

    public native double uniformRandomReal();

    public native double normalRandomReal();
}
