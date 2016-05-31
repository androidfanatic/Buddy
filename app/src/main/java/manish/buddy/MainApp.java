package manish.buddy;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainApp extends Application {
    @Override public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("mono.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}
