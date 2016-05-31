package manish.buddy.main;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import manish.buddy.service.AdbWirelessService;
import manish.buddy.service.StayAwakeService;
import manish.buddy.utils.NetworkUtil;
import manish.buddy.utils.ShellUtil;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainPresenter extends MvpBasePresenter<MainView> {

    public void toggleLayoutBounds() {
        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override public void call(Subscriber<? super Boolean> subscriber) {
                if (ShellUtil.isLayoutBound()) {
                    ShellUtil.setLayoutBound(false);
                    subscriber.onNext(false);
                } else {
                    subscriber.onNext(true);
                    ShellUtil.setLayoutBound(true);
                }
            }

        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Boolean>() {
                    @Override public void call(Boolean value) {
                        if (value) {
                            getView().msg("Layout bounds enabled.");
                        } else {
                            getView().msg("Layout Bounds disabled.");
                        }
                        getView().recreate();
                    }
                });
    }

    public void toggleWirelessAdb() {

        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override public void call(Subscriber<? super Boolean> subscriber) {
                if (ShellUtil.isWirelessAdb()) {
                    ShellUtil.setWirelessAdb(false);
                    stopAdbWirelessService();
                    subscriber.onNext(false);
                } else {
                    subscriber.onNext(true);
                    ShellUtil.setWirelessAdb(true);
                }
            }

        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Boolean>() {
                    @Override public void call(Boolean value) {
                        if (value) {
                            getView().msg("Wireless ADB enabled.");
                            getView().startAdbWirelessService();
                        } else {
                            getView().msg("Wireless ADB disabled.");
                        }
                    }
                });

    }

    public void toggleStayAwake() {
        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override public void call(Subscriber<? super Boolean> subscriber) {
                if (ShellUtil.isStayAwake()) {
                    ShellUtil.setStayAwake(false);
                    stopStayAwakeService();
                    subscriber.onNext(false);
                } else {
                    subscriber.onNext(true);
                    ShellUtil.setStayAwake(true);
                }
            }

        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Boolean>() {
                    @Override public void call(Boolean value) {
                        if (value) {
                            getView().msg("Stay awake enabled.");
                            getView().startStayAwakeService();
                        } else {
                            getView().msg("Stay awake disabled.");
                        }
                    }
                });


    }

    public void stopAdbWirelessService() {
        if (AdbWirelessService.getInstance() != null) {
            AdbWirelessService.getInstance().stopSelf();
        }
    }

    public void stopStayAwakeService() {
        if (StayAwakeService.getInstance() != null) {
            StayAwakeService.getInstance().stopSelf();
        }
    }

    public void initState() {

        if (ShellUtil.isLayoutBound()) {
            getView().setLayoutBoundBtn(true);
        }

        if (ShellUtil.isWirelessAdb()) {
            getView().setWirelessAdbBtn(true);
            getView().startAdbWirelessService();
        }

        if (StayAwakeService.getInstance() != null) {
            getView().setStayAwakeBtn(true);
        }

        getView().setIPAddr(NetworkUtil.getLocalIpAddress());
    }
}