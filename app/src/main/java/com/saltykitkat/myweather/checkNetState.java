package com.saltykitkat.myweather;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

public class checkNetState {

    public enum NetState {
        NONE, WIFI, MOBILE
    }

    public static NetState getNetState(Context context) {
        if (context == null) {
            return NetState.NONE;
        }
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr == null) {
            return NetState.NONE;
        }
        if (Build.VERSION.SDK_INT < 23) {
            NetworkInfo netInfo = connMgr.getActiveNetworkInfo();
            if (netInfo == null) {
                return NetState.NONE;
            } else if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return NetState.MOBILE;
            } else if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return NetState.WIFI;
            }
        } else {
            Network network = connMgr.getActiveNetwork();
            if (network != null) {
                NetworkCapabilities netCap = connMgr.getNetworkCapabilities(network);
                if (netCap.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return NetState.WIFI;
                } else if (netCap.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return NetState.MOBILE;
                }
            }
        }
        return NetState.NONE;
    }
}
