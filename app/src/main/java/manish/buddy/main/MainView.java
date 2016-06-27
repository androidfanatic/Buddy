package manish.buddy.main;

import android.content.Context;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface MainView extends MvpView {
    void recreate();

    void msg(String msg);

    void setLayoutBoundBtn(boolean layoutBound);

    void setWirelessAdbBtn(boolean wirelessAdb);

    void setStayAwakeBtn(boolean b);

    void startStayAwakeService();

    void startAdbWirelessService();

    void setIPAddr(String[] localIpAddress);

    Context getContext();
}
