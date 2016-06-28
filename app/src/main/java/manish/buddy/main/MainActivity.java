package manish.buddy.main;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.lang.reflect.Field;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import manish.buddy.R;
import manish.buddy.pref.PrefActivity;
import manish.buddy.service.AdbWirelessService;
import manish.buddy.service.StayAwakeService;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity
        extends MvpActivity<MainView, MainPresenter>
        implements MainView {

    @Bind(R.id.tbtn_adb_wireless) ToggleButton wirelesAdbBtn;
    @Bind(R.id.tbtn_layout_bounds) ToggleButton layoutBoundsBtn;
    @Bind(R.id.tbtn_stay_awake) ToggleButton stayAwakeBtn;
    @Bind(R.id.tv_ipaddr) TextView ipAddrTv;
    @Bind(R.id.tv_apiLevel) TextView apiLevelTv;

    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);
        initState();
    }

    private void initState() {
        getPresenter().initState();
        initOSInfo();
        appBarIcon();
    }

    private void appBarIcon() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
    }

    private void initOSInfo() {

        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            int fieldValue = -1;

            try {
                fieldValue = field.getInt(new Object());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (fieldValue == Build.VERSION.SDK_INT) {
                apiLevelTv.setText(
                        String.format("API: %d, OS: %s v%s",
                                Build.VERSION.SDK_INT,
                                fieldName,
                                Build.VERSION.RELEASE
                        )
                );
            }
        }
    }

    @NonNull @Override public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override public void msg(String msg) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }

    @OnClick(R.id.tbtn_layout_bounds) public void toggleLayoutBounds(View view) {
        getPresenter().toggleLayoutBounds();
    }

    @OnClick(R.id.tbtn_adb_wireless) public void toggleWirelessAdb(View view) {
        getPresenter().toggleWirelessAdb();
    }

    @OnClick(R.id.tbtn_stay_awake) public void toggleStayAwaje(View view) {

        getPresenter().toggleStayAwake(((ToggleButton) findViewById(R.id.tbtn_stay_awake)).isChecked());
    }

    @Override public void setLayoutBoundBtn(boolean mode) {
        layoutBoundsBtn.setChecked(mode);
    }

    @Override public void setWirelessAdbBtn(boolean mode) {
        wirelesAdbBtn.setChecked(mode);
    }

    @Override public void setStayAwakeBtn(boolean mode) {
        stayAwakeBtn.setChecked(mode);
    }

    @Override public void startStayAwakeService() {
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.show_notif_key), true)) {
            startService(new Intent(this, StayAwakeService.class));
        }
    }

    @Override public void startAdbWirelessService() {
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.show_notif_key), true)) {
            startService(new Intent(this, AdbWirelessService.class));
        }
    }

    @Override public void setIPAddr(String[] localIpAddress) {
        ipAddrTv.setText("IP Addresses: " + Arrays.toString(localIpAddress));
    }

    @Override public Context getContext() {
        return getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_pref:
                Timber.d("Start karin");
                startActivity(new Intent(this, PrefActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}

