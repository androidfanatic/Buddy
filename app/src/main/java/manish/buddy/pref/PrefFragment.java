package manish.buddy.pref;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import manish.buddy.R;

public class PrefFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

}
