package camera;

import android.content.Context;

import com.hikvision.wifi.configuration.DeviceDiscoveryListener;
import com.videogo.exception.BaseException;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.bean.EZAccessToken;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.openapi.bean.EZDeviceInfo;
import com.videogo.openapi.bean.EZUserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/5.
 */
public class CameraInterfaceUtils {

    //保存token及token超时时间
    public static void baoCunTokenAndTime(EZAccessToken token){

    }

    //授权登录以后给EZOpenSDK设置AccessToken
    public static void setAccessToken(String accessToken){
        EZOpenSDK.getInstance().setAccessToken(accessToken);
    }

    //5.3.5	获取用户信息
    public static EZUserInfo getUserInfo(){
        EZUserInfo info = null;
        try {
            info = EZOpenSDK.getInstance().getUserInfo();
        } catch (BaseException e) {
            e.printStackTrace();
        }
        return info;
    }

    //5.4.2	获取摄像头列表
    //@param pageStart 分页当前页码（从0开始）
    //@param pageSize 分页每页数量 (建议20以内)
    public static List<EZCameraInfo> getCameraList(int pageStart,int pageSize){
        List<EZCameraInfo> list = new ArrayList<>();
        EZCameraInfo info = null;
        try {
            info = (EZCameraInfo) EZOpenSDK.getInstance().getCameraList(pageStart,pageSize);
        } catch (BaseException e) {
            e.printStackTrace();
        }
        list.add(info);
        return list;
    }

    //5.4.3	获取摄像头信息
    //param deviceSerial 设备序列号
    public static EZCameraInfo getCameraInfo(String deviceSerial){
        EZCameraInfo info = null;
        try {
            info = EZOpenSDK.getInstance().getCameraInfo(deviceSerial);
        } catch (BaseException e) {
            e.printStackTrace();
        }
        return info;
    }

    //5.4.4	获取设备信息
    //@param cameraId 设备摄像头id
    public static EZDeviceInfo getDeviceInfo(String cameraId){
        EZDeviceInfo info = null;
        try {
            info = EZOpenSDK.getInstance().getDeviceInfo(cameraId);
        } catch (BaseException e) {
            e.printStackTrace();
        }
        return info;
    }

    //5.4.4.1	删除设备
    public static boolean  deleteDevice(String deviceSerial){
        boolean isDelete = false;
        try {
            isDelete = EZOpenSDK.getInstance().deleteDevice(deviceSerial);
        } catch (BaseException e) {
            e.printStackTrace();
        }
        return isDelete;
    }

    //5.4.5	openSettingDevicePage
    //打开设备设置中间页
    public static void openSettingDevicePage(String deviceSerial){
        EZOpenSDK.getInstance().openSettingDevicePage(deviceSerial);
    }

    //5.4.6	添加设备
    public static boolean addDevice(String deviceSerial, String deviceCode){
        boolean isAddDevice = false;
        try {
            isAddDevice =  EZOpenSDK.getInstance().addDevice(deviceSerial, deviceCode);
        } catch (BaseException e) {
            e.printStackTrace();
        }
        return isAddDevice;
    }

    //5.4.7	startConfigWiFi
    public static boolean startConfigWifi(Context context,String ssid,String password,DeviceDiscoveryListener l){
        boolean isStartConfigWiFi = false;
        try {
            isStartConfigWiFi =  EZOpenSDK.getInstance().startConfigWifi(context, ssid, password, l);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isStartConfigWiFi;
    }

    //5.4.8	stopConfigWiFi
    public static boolean stopConfigWiFi(){
        boolean isStopWiFi = false;
        isStopWiFi = EZOpenSDK.getInstance().stopConfigWiFi();
        return isStopWiFi;
    }
}
