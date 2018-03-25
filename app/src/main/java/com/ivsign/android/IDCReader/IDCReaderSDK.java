package com.ivsign.android.IDCReader;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.UnsupportedEncodingException;

/**
 * 类说明：身份证读取sdk
 * Author: Kuzan
 * Date: 2018/3/25 10:10.
 */
public class IDCReaderSDK {
    private static final boolean __DEBUG__ = true;
    private static final String TAG = "IDCReaderSDK";

    @SuppressLint("SdCardPath")
    private static String path1 = "/sdcard/wltlib";
    @SuppressLint("SdCardPath")
    private static String path2 = "/sdcard/wltlib";

    private String[] decodeInfo = new String[10];

    /**
     * 加载wltdecode库
     * */
    static {
        System.loadLibrary("wltdecode");
        Log.v(TAG, "wltload success");
    }

    public IDCReaderSDK() {
        if (0 == wltInit(path1)) {
            Log.i(TAG, "wltInit success");
        } else if (0 == wltInit(path2)) {
            Log.i(TAG, "wltInit success");
        } else {
            Log.i(TAG, "wltInit fail!");
        }
    }

    public int DoDecodeCardInfo(byte[] idc_data) {
        int ret;
        byte[] persionInfo = new byte[256];
        System.arraycopy(idc_data, 14, persionInfo, 0, 256);
        byte[] wltData = new byte[1024];
        System.arraycopy(idc_data, 14 + 256, wltData, 0, 1024);

        byte[] byLicData = new byte[] { (byte) 0x05, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0xE3, (byte) 0xB4,
                (byte) 0x32, (byte) 0x01, (byte) 0x8B, (byte) 0x21, (byte) 0x0B, (byte) 0x00 };

        // byte[] byLicData = new
        // byte[]{0x05,0x00,0x01,0x00,0x46,(byte)0xb5,0x32,0x01,0x6d,0x13,0x0c,0x00};
        // 0501-20101105-0001207685
        // 0501-20100422-0000791405

        ret = wltGetBMP(wltData, byLicData);
        if (ret == 1) {
            decodePersionInfo(persionInfo);
        }
        return ret;
    }

    public String GetPeopleName() {
        return decodeInfo[0];
    }

    public String GetPeopleSex() {
        return decodeInfo[1];
    }

    public String GetPeopleNation() {
        return decodeInfo[2];
    }

    public String GetPeopleBirthday() {
        return decodeInfo[3];
    }

    public String GetPeopleAddress() {
        return decodeInfo[4];
    }

    public String GetPeopleIDCode() {
        return decodeInfo[5];
    }

    public String GetDepartment() {
        return decodeInfo[6];
    }

    public String GetStartDate() {
        return decodeInfo[7];
    }

    public String GetEndDate() {
        return decodeInfo[8];
    }

    /**
     * 解码身份信息
     * */
    private void decodePersionInfo(byte[] dataBuf) {
        try {
            String TmpStr = new String(dataBuf, "UTF16-LE");
            TmpStr = new String(TmpStr.getBytes("UTF-8"));

            decodeInfo[0] = TmpStr.substring(0, 15); //
            decodeInfo[1] = TmpStr.substring(15, 16); //
            decodeInfo[2] = TmpStr.substring(16, 18); //
            decodeInfo[3] = TmpStr.substring(18, 26); //
            decodeInfo[4] = TmpStr.substring(26, 61); //
            decodeInfo[5] = TmpStr.substring(61, 79); //
            decodeInfo[6] = TmpStr.substring(79, 94); //
            decodeInfo[7] = TmpStr.substring(94, 102); //
            decodeInfo[8] = TmpStr.substring(102, 110); //
            decodeInfo[9] = TmpStr.substring(110, 128); //
            decodeInfo[8] = TmpStr.substring(102, 110);
            if (decodeInfo[1].equals("1"))
                decodeInfo[1] = "男";
            else
                decodeInfo[1] = "女";

            try {
                int code = Integer.parseInt(decodeInfo[2].toString());
                decodeInfo[2] = decodeNation(code);
            } catch (Exception e) {
                decodeInfo[2] = "";
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解码民族
     * */
    private String decodeNation(int code) {
        String nation;
        switch (code) {
            case 1:
                nation = ("汉");
                break;
            case 2:
                nation = ("蒙古");
                break;
            case 3:
                nation = ("回");
                break;
            case 4:
                nation = ("藏");
                break;
            case 5:
                nation = ("维吾尔");
                break;
            case 6:
                nation = ("苗");
                break;
            case 7:
                nation = ("彝");
                break;
            case 8:
                nation = ("壮");
                break;
            case 9:
                nation = ("布依");
                break;
            case 10:
                nation = ("朝鲜");
                break;
            case 11:
                nation = ("满");
                break;
            case 12:
                nation = ("侗");
                break;
            case 13:
                nation = ("瑶");
                break;
            case 14:
                nation = ("白");
                break;
            case 15:
                nation = ("土家");
                break;
            case 16:
                nation = ("哈尼");
                break;
            case 17:
                nation = ("哈萨克");
                break;
            case 18:
                nation = ("傣");
                break;
            case 19:
                nation = ("黎");
                break;
            case 20:
                nation = ("傈僳");
                break;
            case 21:
                nation = ("佤");
                break;
            case 22:
                nation = ("畲");
                break;
            case 23:
                nation = ("高山");
                break;
            case 24:
                nation = ("拉祜");
                break;
            case 25:
                nation = ("水");
                break;
            case 26:
                nation = ("东乡");
                break;
            case 27:
                nation = ("纳西");
                break;
            case 28:
                nation = ("景颇");
                break;
            case 29:
                nation = ("柯尔克孜");
                break;
            case 30:
                nation = ("土");
                break;
            case 31:
                nation = ("达斡尔");
                break;
            case 32:
                nation = ("仫佬");
                break;
            case 33:
                nation = ("羌");
                break;
            case 34:
                nation = ("布朗");
                break;
            case 35:
                nation = ("撒拉");
                break;
            case 36:
                nation = ("毛南");
                break;
            case 37:
                nation = ("仡佬");
                break;
            case 38:
                nation = ("锡伯");
                break;
            case 39:
                nation = ("阿昌");
                break;
            case 40:
                nation = ("普米");
                break;
            case 41:
                nation = ("塔吉克");
                break;
            case 42:
                nation = ("怒");
                break;
            case 43:
                nation = ("乌孜别克");
                break;
            case 44:
                nation = ("俄罗斯");
                break;
            case 45:
                nation = ("鄂温克");
                break;
            case 46:
                nation = ("德昂");
                break;
            case 47:
                nation = ("保安");
                break;
            case 48:
                nation = ("裕固");
                break;
            case 49:
                nation = ("京");
                break;
            case 50:
                nation = ("塔塔尔");
                break;
            case 51:
                nation = ("独龙");
                break;
            case 52:
                nation = ("鄂伦春");
                break;
            case 53:
                nation = ("赫哲");
                break;
            case 54:
                nation = ("门巴");
                break;
            case 55:
                nation = ("珞巴");
                break;
            case 56:
                nation = ("基诺");
                break;
            case 97:
                nation = ("其他");
                break;
            case 98:
                nation = ("外国血统中国籍人士");
                break;
            default:
                nation = ("其他");
        }
        return nation;
    }

    // native 方法定义
    public static native int wltInit(String workPath);

    public static native int wltGetBMP(byte[] wltdata, byte[] licdata);
}
