package com.mozidev.testopengl.utils;


/**
 * Check device's network connectivity and speed
 *
 * @author emil http://stackoverflow.com/users/220710/emil
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


public class Connectivity {

    private static final String TAG = "Connectivity";

    private static int MAX_SIGNAL_DBM_VALUE = 31;
    private static TelephonyManager tel;
    private static MyPhoneStateListener myPhoneStateListener;
    private static int signal3GLevel;
    private static boolean is3GConnect;


    /**
     * Get the network info
     * @param context
     * @return
     */
    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }


    /**
     * Check if there is any connectivity
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected());
    }


    /**
     * Check if there is any connectivity to a Wifi network
     * @param context
     * @return
     */
    public static boolean isConnectedWifi(Context context) {
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }


    public static String getConnectedWifiName(Context context) {

        String ssid = "";
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null && !TextUtils.isEmpty(connectionInfo.getSSID())) {
                ssid = connectionInfo.getBSSID();
            }
        }
       // Log.d("WIFIReceiver", "ssid = " + ssid);
        return ssid;
    }


    /**
     * Check if there is any connectivity to a mobile network
     * @param context
     * @return
     */
    public static boolean isConnectedMobile(Context context) {
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }


    /**
     * Check if there is fast connectivity
     * @param context
     * @return
     */
    public static boolean isConnectedFast(Context context) {
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected() && Connectivity.isConnectionFast(info.getType(), info.getSubtype()));
    }


    /**
     * Check if the connection is fast
     * @param type
     * @param subType
     * @return
     */
    public static boolean isConnectionFast(int type, int subType) {
        if (type == ConnectivityManager.TYPE_WIFI) {
            return true;
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return false; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return true; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return true; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return false; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return true; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return true; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return true; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return true; // ~ 400-7000 kbps
            /*
             * Above API level 7, make sure to set android:targetSdkVersion
             * to appropriate level to use these
             */
                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                    return true; // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                    return true; // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                    return true; // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                    return false; // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                    return true; // ~ 10+ Mbps
                // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                default:
                    return false;
            }
        } else {
            return false;
        }
    }


    /*     public static int get3GStrength(Context context){
             ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
             if(cm == null) return  0;
             NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
             boolean connected = info.isConnected();
             if(info == null || !connected) return 0;
             TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
             List<CellInfo> allCellInfo = telephonyManager.getAllCellInfo();
             if(allCellInfo == null || allCellInfo.size() == 0) return 0;
             CellInfoGsm cellinfogsm = (CellInfoGsm) allCellInfo.get(0);
             CellSignalStrengthGsm cellSignalStrengthGsm = cellinfogsm.getCellSignalStrength();
             int asuLevel = cellSignalStrengthGsm.getAsuLevel();
             return calculateSignalLevel(asuLevel);
        }*/


    public static int get3GStrength(Context context) {
        return is3GConnect ? signal3GLevel : 0;
    }


    private static int calculateSignalLevel(int asuLevel) {
        int level = 0;
        if (asuLevel < 1) {
            level = 0;
        } else if (asuLevel < 8) {
            level = 1;
        } else if (asuLevel < 16) {
            level = 2;
        } else if (asuLevel < 24) {
            level = 3;
        } else if (asuLevel <= 31) {
            level = 4;
        }
      //  Log.d(TAG, "level = " + level);
        return level;
    }


    public static int getWIFIStrangth(Context context) {
        return PrefUtils.getWIFIStrangth(context);
    }


    public static void initialize3GListener(Context context) {
        myPhoneStateListener = new MyPhoneStateListener();
        tel = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        tel.listen(myPhoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }


    private static class MyPhoneStateListener extends PhoneStateListener {

        private static final int UNKNOW_CODE = 99;


        /* Get the Signal strength from the provider, each tiome there is an update */
        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            is3GConnect = (tel.getDataState() == TelephonyManager.DATA_CONNECTED);

            if (null != signalStrength && signalStrength.getGsmSignalStrength() != UNKNOW_CODE) {
                signal3GLevel = calculateSignalLevel(signalStrength.getGsmSignalStrength());
            }
        }
    }

}
/*For all less than or equal to 100kbps speed, the network is considered to be as 2G.

For all greater than 100kbps & less than 1mbps speed, the network is considered to be as 3G.

And for all greater than 1mbps speed, the network is considered to be as 4G.*/
