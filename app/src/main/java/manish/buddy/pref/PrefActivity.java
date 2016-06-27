package manish.buddy.pref;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import manish.buddy.R;

public class PrefActivity extends MvpActivity<PrefView, PrefPresenter>
        implements PrefView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);
        getFragmentManager().beginTransaction().replace(R.id.content, new PrefFragment()).commit();
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    @NonNull @Override public PrefPresenter createPresenter() {
        return new PrefPresenter();
    }
}


