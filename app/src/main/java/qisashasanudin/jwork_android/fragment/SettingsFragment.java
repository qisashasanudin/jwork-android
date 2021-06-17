package qisashasanudin.jwork_android.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import qisashasanudin.jwork_android.R;
import qisashasanudin.jwork_android.activity.LoginActivity;
import qisashasanudin.jwork_android.activity.MainActivity;
import qisashasanudin.jwork_android.activity.RegisterActivity;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        SwitchPreference themePref = (SwitchPreference) findPreference("theme_pref");
        Preference signOutPref = findPreference("sign_out_pref");

        themePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(((SwitchPreference)preference).isChecked()){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                return true;
            }
        });

        signOutPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Activity currentActivity = getActivity();
                Intent intent = new Intent(currentActivity, LoginActivity.class);
                startActivity(intent);
                currentActivity.finish();
                return false;
            }
        });
    }
}