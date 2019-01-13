package com.example.nir30.newsly2;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

    public class SettingFragment extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
//
//    interface SettingFragmenetListenrer {
//        void onSetSettings(String interval);
//    }
//    private SettingFragmenetListenrer listenrer;
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        try {
//            listenrer = (SettingFragmenetListenrer)context;
//        }catch (ClassCastException ex){
//            throw  new ClassCastException("houston we have a problem");
//        }
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        View root = inflater.inflate(R.layout.notif_settings_fragment, container,false);
//        Spinner intervalSpinner = root.findViewById(R.id.interval_spinner);
//
//        ArrayAdapter<String> intervalAdapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.intervals));
//        intervalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        intervalSpinner.setAdapter(intervalAdapter);
//
//        Button okButtom = root.findViewById(R.id.btn_ok);
//        okButtom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
