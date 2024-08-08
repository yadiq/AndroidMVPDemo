package com.hqumath.demo.utils.safe;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

/**
 * 模拟器检测
 * 是否模拟器 EmulatorCheckUtil.readSysProperty(mContext);
 */
public class EmulatorCheckUtil {
    private static final String TAG = "EmulatorCheck";
    private static final String LABEL_EMULATOR_MAYBE = "*";
    private static final String LABEL_EMULATOR = "**";
    public static final int RESULT_MAYBE_EMULATOR = 0;//可能是模拟器
    public static final int RESULT_EMULATOR = 1;//模拟器
    public static final int RESULT_UNKNOWN = 2;//可能是真机

    private EmulatorCheckUtil() {}

    /**
     * 是否模拟器
     * @param context
     * @return
     */
    public static boolean readSysProperty(@NonNull Context context) {
        int suspectCount = 0;
        StringBuilder sb = new StringBuilder("check safe[emulator]: ").append("\n");

        //检测硬件名称
        CheckResult hardwareResult = checkFeaturesByHardware();
        suspectCount += proResult(hardwareResult, sb, "hardware");

        //检测渠道
        CheckResult flavorResult = checkFeaturesByFlavor();
        suspectCount += proResult(flavorResult, sb, "flavor");

        //检测设备型号
        CheckResult modelResult = checkFeaturesByModel();
        suspectCount += proResult(modelResult, sb, "model");

        //检测硬件制造商
        CheckResult manufacturerResult = checkFeaturesByManufacturer();
        suspectCount += proResult(manufacturerResult, sb, "manufacturer");

        //检测主板名称
        CheckResult boardResult = checkFeaturesByBoard();
        suspectCount += proResult(boardResult, sb, "board");

        //检测主板平台
        CheckResult platformResult = checkFeaturesByPlatform();
        suspectCount += proResult(platformResult, sb, "platform");

        //检测基带信息
        CheckResult baseBandResult = checkFeaturesByBaseBand();
        suspectCount += proResult(baseBandResult, sb, "baseBand", 2);

        //检测传感器数量
        int sensorNumber = getSensorNumber(context);
        suspectCount += proResult(sensorNumber < 8, sb, "sensorNumber", String.valueOf(sensorNumber));

        //检测已安装第三方应用数量
        //int userAppNumber = getUserAppNumber();
        //suspectCount += proResult(userAppNumber < 6, sb, "userAppNumber", String.valueOf(userAppNumber));

        //检测是否支持闪光灯
        boolean supportCameraFlash = supportCameraFlash(context);
        if (!supportCameraFlash) ++suspectCount;
        suspectCount += proResult(!supportCameraFlash, sb, "supportCameraFlash", String.valueOf(supportCameraFlash));

        //检测是否支持相机
        boolean supportCamera = supportCamera(context);
        suspectCount += proResult(!supportCamera, sb, "supportCamera", String.valueOf(supportCamera));

        //检测是否支持蓝牙
        boolean supportBluetooth = supportBluetooth(context);
        suspectCount += proResult(!supportBluetooth, sb, "supportBluetooth", String.valueOf(supportBluetooth));

        //检测光线传感器
        boolean hasLightSensor = hasLightSensor(context);
        if (!hasLightSensor) ++suspectCount;
        suspectCount += proResult(!hasLightSensor, sb, "hasLightSensor", String.valueOf(hasLightSensor));

        //检测进程组信息
        CheckResult cgroupResult = checkFeaturesByCgroup();
        suspectCount += proResult(cgroupResult.result == RESULT_MAYBE_EMULATOR, sb, "cgroupResult", cgroupResult.value);

        Log.i(TAG, sb.toString());
        //嫌疑值大于3，认为是模拟器
        boolean emulator = (suspectCount > 3);
        Log.i(TAG, "check emulator: " + suspectCount);
        return emulator;
    }

    private static int getUserAppNum(String userApps) {
        if (TextUtils.isEmpty(userApps)) return 0;
        String[] result = userApps.split("package:");
        return result.length;
    }

    private static String getProperty(String propName) {
        String property = CommandUtil.getProperty(propName);
        return TextUtils.isEmpty(property) ? null : property;
    }

    /**
     * 特征参数-硬件名称
     */
    private static CheckResult checkFeaturesByHardware() {
        String hardware = getProperty("ro.hardware");
        if (null == hardware) {
            return new CheckResult(RESULT_MAYBE_EMULATOR, null);
        }
        int result;
        String tempValue = hardware.toLowerCase();
        if (tempValue.contains("ttvm") ||
                tempValue.contains("nox") ||
                tempValue.contains("cancro") ||
                tempValue.contains("intel") ||
                tempValue.contains("vbox") ||
                tempValue.contains("vbox86") ||
                tempValue.contains("android_x86") ||
                tempValue.contains("ttvm_x86")) {
            result = RESULT_EMULATOR;
        } else {
            result = RESULT_UNKNOWN;
        }
        return new CheckResult(result, hardware);
    }

    /**
     * 特征参数-渠道
     *
     * @return 0表示可能是模拟器，1表示模拟器，2表示可能是真机
     */
    private static CheckResult checkFeaturesByFlavor() {
        String flavor = getProperty("ro.build.flavor");
        if (null == flavor) {
            return new CheckResult(RESULT_MAYBE_EMULATOR, null);
        }
        int result;
        String tempValue = flavor.toLowerCase();
        if (tempValue.contains("vbox") ||
                tempValue.contains("sdk_gphone") ||
                tempValue.contains("cancro")) {
            result = RESULT_EMULATOR;
        } else {
            result = RESULT_UNKNOWN;
        }
        return new CheckResult(result, flavor);
    }

    /**
     * 特征参数-设备型号
     *
     * @return 0表示可能是模拟器，1表示模拟器，2表示可能是真机
     */
    private static CheckResult checkFeaturesByModel() {
        String model = getProperty("ro.product.model");
        if (null == model) return new CheckResult(RESULT_MAYBE_EMULATOR, null);
        int result;
        String tempValue = model.toLowerCase();
        if (tempValue.contains("google_sdk") ||
                tempValue.contains("emulator") ||
                tempValue.contains("android sdk built for x86") ||
                tempValue.contains("android sdk built for x86_64") ||
                tempValue.contains("droid4x") ||
                tempValue.contains("tiantianvm") ||
                tempValue.contains("andy") ||
                tempValue.contains("cancro")
        ) {
            result = RESULT_EMULATOR;
        } else {
            result = RESULT_UNKNOWN;
        }
        return new CheckResult(result, model);
    }

    private static CheckResult checkFeaturesByBrand() {
        String model = getProperty("ro.product.brand");
        if (null == model) return new CheckResult(RESULT_MAYBE_EMULATOR, null);
        int result;
        String tempValue = model.toLowerCase();
        if (tempValue.contains("google_sdk") ||
                tempValue.contains("emulator") ||
                tempValue.contains("android sdk built for x86") ||
                tempValue.contains("android sdk built for x86_64") ||
                tempValue.contains("droid4x") ||
                tempValue.contains("tiantianvm") ||
                tempValue.contains("andy")
        ) {
            result = RESULT_EMULATOR;
        } else {
            result = RESULT_UNKNOWN;
        }
        return new CheckResult(result, model);
    }

    /**
     * 特征参数-硬件制造商
     *
     * @return 0表示可能是模拟器，1表示模拟器，2表示可能是真机
     */
    private static CheckResult checkFeaturesByManufacturer() {
        String manufacturer = getProperty("ro.product.manufacturer");
        if (null == manufacturer) return new CheckResult(RESULT_MAYBE_EMULATOR, null);
        int result;
        String tempValue = manufacturer.toLowerCase();
        if (tempValue.contains("genymotion") ||
                tempValue.contains("netease") ||
                tempValue.contains("andy") ||
                tempValue.contains("nox") ||
                tempValue.contains("tiantianvm")) {
            result = RESULT_EMULATOR;
        } else {
            result = RESULT_UNKNOWN;
        }
        return new CheckResult(result, manufacturer);
    }

    /**
     * 特征参数-主板名称
     *
     * @return 0表示可能是模拟器，1表示模拟器，2表示可能是真机
     */
    private static CheckResult checkFeaturesByBoard() {
        String board = getProperty("ro.product.board");
        if (null == board) return new CheckResult(RESULT_MAYBE_EMULATOR, null);
        int result;
        String tempValue = board.toLowerCase();
        if (tempValue.contains("android") ||
                tempValue.contains("goldfish")) {
            result = RESULT_EMULATOR;
        } else {
            result = RESULT_UNKNOWN;
        }
        return new CheckResult(result, board);
    }

    /**
     * 特征参数-主板平台
     *
     * @return 0表示可能是模拟器，1表示模拟器，2表示可能是真机
     */
    private static CheckResult checkFeaturesByPlatform() {
        String platform = getProperty("ro.board.platform");
        if (null == platform) return new CheckResult(RESULT_MAYBE_EMULATOR, null);
        int result;
        String tempValue = platform.toLowerCase();
        if (tempValue.contains("android")) {
            result = RESULT_EMULATOR;
        } else {
            result = RESULT_UNKNOWN;
        }
        return new CheckResult(result, platform);
    }

    /**
     * 特征参数-基带信息
     *
     * @return 0表示可能是模拟器，1表示模拟器，2表示可能是真机
     */
    private static CheckResult checkFeaturesByBaseBand() {
        String baseBandVersion = getProperty("gsm.version.baseband");
        if (null == baseBandVersion) return new CheckResult(RESULT_MAYBE_EMULATOR, null);
        int result;
        if (baseBandVersion.contains("1.0.0.0")) {
            result = RESULT_EMULATOR;
        } else {
            result = RESULT_UNKNOWN;
        }
        return new CheckResult(result, baseBandVersion);
    }

    /**
     * 获取传感器数量
     */
    private static int getSensorNumber(Context context) {
        SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        return sm.getSensorList(Sensor.TYPE_ALL).size();
    }

    /**
     * 获取已安装第三方应用数量
     */
    private static int getUserAppNumber() {
        String userApps = CommandUtil.exec("pm list package -3");
        return getUserAppNum(userApps);
    }

    /**
     * 是否支持相机
     */
    private static boolean supportCamera(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    /**
     * 是否支持闪光灯
     */
    private static boolean supportCameraFlash(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    /**
     * 是否支持蓝牙
     */
    private static boolean supportBluetooth(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH);
    }

    /**
     * 判断是否存在光传感器来判断是否为模拟器
     * 部分真机也不存在温度和压力传感器。其余传感器模拟器也存在。
     *
     * @return false为模拟器
     */
    private static boolean hasLightSensor(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); //光线传感器
        if (null == sensor) return false;
        else return true;
    }

    /**
     * 特征参数-进程组信息
     */
    private static CheckResult checkFeaturesByCgroup() {
        String filter = CommandUtil.exec("cat /proc/self/cgroup");
        if (null == filter) return new CheckResult(RESULT_MAYBE_EMULATOR, null);
        return new CheckResult(RESULT_UNKNOWN, filter);
    }

    private static String addMarkLabel(String raw, String label) {
        return label + raw;
    }

    private static int proResult(boolean hitMaybe, StringBuilder sb, String key, String value) {
        int addCount = 0;
        if (hitMaybe) {
            addCount = 1;
            key = addMarkLabel(key, LABEL_EMULATOR_MAYBE);
        }
        sb.append(key).append(" = ").append(value).append("\n");
        return addCount;
    }

    private static int proResult(CheckResult result, StringBuilder sb, String key) {
        return proResult(result, sb, key, 1);
    }

    private static int proResult(CheckResult result, StringBuilder sb, String key, int hitCount) {
        int addCount = 0;
        switch (result.result) {
            case RESULT_MAYBE_EMULATOR:
                addCount = hitCount;
                key = addMarkLabel(key, LABEL_EMULATOR_MAYBE);
                break;
            case RESULT_EMULATOR:
                addCount = 1000;
                key = addMarkLabel(key, LABEL_EMULATOR);
                break;
        }
        sb.append(key).append(" = ").append(result.value).append("\n");
        return addCount;
    }

    public static class CheckResult {
        public int result;
        public String value;

        public CheckResult(int result, String value) {
            this.result = result;
            this.value = value;
        }
    }
}