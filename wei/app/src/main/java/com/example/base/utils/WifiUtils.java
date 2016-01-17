package com.example.administrator.base.utils;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/1/9.
 */
public class WifiUtils {

    public static final String CONFIGURED_NETWORKS_CHANGED_ACTION = "android.net.wifi.CONFIGURED_NETWORKS_CHANGE";

    public static final String LINK_CONFIGURATION_CHANGED_ACTION = "android.net.wifi.LINK_CONFIGURATION_CHANGED";

    private static final int SECURITY_NONE = 0;
    private static final int SECURITY_WEP = 1;
    private static final int SECURITY_PSK = 2;
    private static final int SECURITY_EAP = 3;


    public static String  getHost(Context context){
        String ip = null;
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo != null){
            int ipAddress = wifiInfo.getIpAddress();
            if (ipAddress != 0){
                ip = (ipAddress & 0xFF ) + "." +
                        ((ipAddress >> 8 ) & 0xFF) + "." +
                        ((ipAddress >> 16 ) & 0xFF) + "." +
                        ( ipAddress >> 24 & 0xFF);
            }else {
                ip = wifiInfo.getMacAddress();
            }
        }
        return ip;
    }


    public static boolean isCurrentWiFi(Context context,String ssid){
        if (ssid == null) return false;
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo != null && wifiInfo.getSSID() != null){
            if (wifiInfo.getSSID().equals("\""+ssid+"\"") || wifiInfo.getSSID().equals(ssid)){
                return true;
            }
        }
        return false;
    }

    public static boolean isCurrentConnectWiFi(Context context,String ssid){
        if (ssid == null)
            return false;
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo != null){
            String currentSSID = wifiInfo.getSSID();
            if (currentSSID != null && wifiInfo.getSupplicantState() == SupplicantState.COMPLETED){
                if (currentSSID.equalsIgnoreCase("\""+ssid+"\"") || currentSSID.equalsIgnoreCase(ssid)){
                    return true;
                }
            }
        }
        return false;
    }

    public static String getCurrentWiFiSSID(Context context){
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()){
            return null;
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo != null){
            String ssid = wifiInfo.getSSID();
            if (ssid != null){
                if (ssid.startsWith("\"") && ssid.endsWith("\"")){
                    ssid = ssid.substring(1,ssid.length() -1);
                }
                return ssid;
            }
        }
        return  null;
    }


    public static boolean hasWiFi(Context context,String ssid){
        if (ssid == null)
            return false;
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.startScan();
        List<ScanResult> results =  wifiManager.getScanResults();
        for (ScanResult result: results){
            if (ssid.equals(result.SSID)){
                return true;
            }
        }
        return false;
    }


    public static WifiConfiguration getConfig(Context context,String ssid,String password) {

        if (TextUtils.isEmpty(password)){
            password = null;
        }

        boolean hidden = true;
        String capabilities = null;
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.startScan();
        List<ScanResult> results = wifiManager.getScanResults();
        for (ScanResult result : results){
            if (ssid.equals(result.SSID)){
                capabilities = result.capabilities;
                hidden = false;
                break;
            }
        }
        WifiConfiguration config = new WifiConfiguration();
        config.SSID = "\"" + ssid + "\"";
        if (hidden){
            if (password != null) {
                config.preSharedKey = "\"" + password+ "\"";
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            }else{
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            }
            config.hiddenSSID = true;
            config.status = WifiConfiguration.Status.ENABLED;
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.TKIP);
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.CCMP);
            config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            return config;
        }
        config.hiddenSSID = false;
        // If the user adds a network manually, assume that it is hidden.
        switch (getSecurity(capabilities)) {
            case SECURITY_NONE:
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                break;

            case SECURITY_WEP:
                if (password == null) {
                    return null;
                }

                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
                if (password.length() != 0) {
                    int length = password.length();
                    // WEP-40, WEP-104, and 256-bit WEP (WEP-232?)
                    if ((length == 10 || length == 26 || length == 58)
                            && password.matches("[0-9A-Fa-f]*")) {
                        config.wepKeys[0] = password;
                    } else {
                        config.wepKeys[0] = '"' + password + '"';
                    }
                }
                break;

            case SECURITY_PSK:
                if (password == null) {
                    return null;
                }
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                if (password.length() != 0) {
                    if (password.matches("[0-9A-Fa-f]{64}")) {
                        config.preSharedKey = password;
                    } else {
                        config.preSharedKey = '"' + password + '"';
                    }
                }
                config.preSharedKey = '"' + password + '"';
                break;

            case SECURITY_EAP:
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_EAP);
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.IEEE8021X);
                if (password == null) {
                    return null;
                }
                if (password.length() != 0) {
                    if (password.matches("[0-9A-Fa-f]{64}")) {
                        config.preSharedKey = password;
                    } else {
                        config.preSharedKey = '"' + password + '"';
                    }
                }
                break;

            default:
                return null;
        }

        return config;
    }

    public static int getSecurity(String capabilities) {
        if (capabilities.contains("WEP")) {
            return SECURITY_WEP;
        } else if (capabilities.contains("PSK")) {
            return SECURITY_PSK;
        } else if (capabilities.contains("EAP")) {
            return SECURITY_EAP;
        }
        return SECURITY_NONE;
    }
}
