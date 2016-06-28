package manish.buddy.utils;

import java.util.List;

import eu.chainfire.libsuperuser.Shell;
import timber.log.Timber;

public class ShellUtil {

    public static boolean isLayoutBound() {
        List<String> result = Shell.SU.run("getprop debug.layout");
        return result.size() > 0 && result.get(0).equalsIgnoreCase("true");
    }

    public static void setLayoutBound(boolean mode) {
        Shell.SU.run("setprop debug.layout " + (mode ? "true" : "false"));
    }

    public static boolean isWirelessAdb() {
        List<String> result = Shell.SU.run("getprop service.adb.tcp.port");
        return result.size() > 0 && result.get(0).equalsIgnoreCase("5555");
    }

    public static void setWirelessAdb(boolean mode) {
        Shell.SU.run("setprop service.adb.tcp.port " + (mode ? "5555" : "-1"));
        Shell.SU.run(new String[]{"stop adbd", "start adbd"});
    }

    public static void setStayAwake(boolean mode) {
        Timber.d("1svc power stayon " + (mode ? " true " : " false "));
        Shell.SU.run("svc power stayon " + (mode ? "true" : "false"));
    }

}

