package com.example.nir30.newsly2;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().add(android.R.id.content, new SettingFragment()).commit();
    }
}
