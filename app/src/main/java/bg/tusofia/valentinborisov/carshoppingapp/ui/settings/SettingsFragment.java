package bg.tusofia.valentinborisov.carshoppingapp.ui.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.DialogPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import bg.tusofia.valentinborisov.carshoppingapp.constants.Constants;
import bg.tusofia.valentinborisov.carshoppingapp.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    private SharedPreferences mSharedPreferences;
    private ListPreference.OnPreferenceChangeListener listener;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.main_preferences, rootKey);


        ListPreference range = findPreference(Constants.RANGE_SETTING);
        range.setEntries(Constants.RANGE_LABELS);
        range.setEntryValues(Constants.RANGE_VALUES);

        ListPreference interval = findPreference(Constants.INTERVAL_SETTING);
        interval.setEntries(Constants.INTERVAL_LABELS);
        interval.setEntryValues(Constants.INTERVAL_VALUES);


        mSharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);


        listener = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                SharedPreferences.Editor editor = mSharedPreferences.edit();

                if(!(mSharedPreferences.getString(preference.getKey(), null) == null)) {
                    editor.remove(preference.getKey());
                }

                editor.putString(preference.getKey(), String.valueOf(newValue));
                editor.apply();
                return true;
            }
        };

        range.setOnPreferenceChangeListener(listener);
        interval.setOnPreferenceChangeListener(listener);
    }

}