package com.example.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tom on 15/5/13.
 */
public class StringUtil {

    public static boolean validationPhoneNumber(String phoneNumber) {
        Pattern p = Pattern.compile("\\d{8,16}$");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }


    public static boolean validationPassword(String password) {
        Pattern p = Pattern.compile("\\w{6,18}$");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public static boolean validationVerificationCode(String code) {
        Pattern p = Pattern.compile("\\d{4}$");
        Matcher m = p.matcher(code);
        return m.matches();
    }

    public static boolean isReachableHost(String host) {
        try {
            Process p = Runtime.getRuntime().exec("/system/bin/ping -c " + 1 + " " + host);
            return p.waitFor() == 0;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isReachableHost(String host, int timeout) {
        if (timeout < 1) {
            timeout = 6000;//6s
        }
        try {
            return InetAddress.getByName(host).isReachable(timeout);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isReachableHost(String host, int port, int timeout) {
        if (timeout < 1) {
            timeout = 6000;//6s
        }
        boolean status = false;
        Socket socket = null;
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(host, port), timeout);
            status = socket.isConnected();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return status;
    }

    public static Bitmap stringToBitmap(String str) {

        byte[] b = str.getBytes();
        ByteArrayInputStream bas = new ByteArrayInputStream(b);
        Bitmap bitmap = BitmapFactory.decodeStream(bas);
        return bitmap;
    }

}
