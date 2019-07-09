package com.jflopezfernandez.rng;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {

    public static final String PREFERENCES = "Preferences";
    public static final String TYPE        = "Type";
    public static final String DIST        = "Distribution";

    public enum Type {
        Integer,
        Real
    };

    public enum Distribution {
        Uniform,
        Normal
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences sharedPreferences = getSharedPreferences(SettingsActivity.PREFERENCES, Context.MODE_PRIVATE);
        int type = sharedPreferences.getInt(SettingsActivity.TYPE, SettingsActivity.Type.Integer.ordinal());
        int dist = sharedPreferences.getInt(SettingsActivity.DIST, SettingsActivity.Distribution.Uniform.ordinal());

        if (type == SettingsActivity.Type.Integer.ordinal()) {
            RadioButton radioButtonInteger = findViewById(R.id.radio_button_integer);

            if (radioButtonInteger != null) {
                radioButtonInteger.setChecked(true);
            }
        } else if (type == SettingsActivity.Type.Real.ordinal()) {
            RadioButton radioButtonReal = findViewById(R.id.radio_button_real);

            if (radioButtonReal != null) {
                radioButtonReal.setChecked(true);
            }
        }

        if (dist == SettingsActivity.Distribution.Uniform.ordinal()) {
            RadioButton radioButtonUniformDistribution = findViewById(R.id.radio_button_uniform);

            if (radioButtonUniformDistribution != null) {
                radioButtonUniformDistribution.setChecked(true);
            }
        } else if (dist == SettingsActivity.Distribution.Normal.ordinal()) {
            RadioButton radioButtonNormalDistribution = findViewById(R.id.radio_button_normal);

            if (radioButtonNormalDistribution != null) {
                radioButtonNormalDistribution.setChecked(true);
            }
        }

        Button buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SavePreferences();

                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void SavePreferences() {
        RadioGroup radioGroupType = findViewById(R.id.radio_group_type);
        RadioGroup radioGroupDist = findViewById(R.id.radio_group_distribution);

        int type = 0;
        int dist = 0;

        if (((RadioButton) findViewById(radioGroupType.getCheckedRadioButtonId())).getText().equals(getResources().getString(R.string.integer))) {
            type = Type.Integer.ordinal();
        } else if (((RadioButton) findViewById(radioGroupType.getCheckedRadioButtonId())).getText().equals(getResources().getString(R.string.real))) {
            type = Type.Real.ordinal();
        }

        if (((RadioButton) findViewById(radioGroupDist.getCheckedRadioButtonId())).getText().equals(getResources().getString(R.string.uniform))) {
            dist = Distribution.Uniform.ordinal();
        } else if (((RadioButton) findViewById(radioGroupDist.getCheckedRadioButtonId())).getText().equals(getResources().getString(R.string.normal))) {
            dist = Distribution.Normal.ordinal();
        }

        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();

        preferencesEditor.putInt(SettingsActivity.TYPE, type);
        preferencesEditor.putInt(SettingsActivity.DIST, dist);

        preferencesEditor.apply();
    }
}
