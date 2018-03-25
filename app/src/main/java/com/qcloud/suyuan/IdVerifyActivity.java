package com.qcloud.suyuan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.ivsign.android.IDCReader.IDCReaderSDK;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.concurrent.TimeUnit;

import IDNR_JAR.IDNRSocket;
import android_serialport_api.SerialPort;
import android_serialport_api.sample.Util;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * 类说明：
 * Author: Kuzan
 * Date: 2018/3/25 9:34.
 */
public class IdVerifyActivity extends AppCompatActivity {
    String ServerIP = "101.201.41.36"; //"47.94.199.36"; //"192.168.1.217"; //"192.168.0.218"; //
    int ServerPort =  9001;  //1010; //12351; //12337; //
    private SoundPool soundPool;
    private int soundId;
    private static final String SDPATH = Environment.getExternalStorageDirectory().getPath();
    private byte[] mBuffer;
    private byte[] mIDCBuffer = new byte[1600];

    private int rec_index_pos = 0;
    private Myttt ttt;
    private boolean isload = false;
    private static final int STEP5001OK = 0x5001;
    private static final int STEP5012OK = 0x5012;
    private static final int STEP5022OK = 0x5022;
    private static final int STEP6013OK = 0x6013;
    private static final int STEP5001ERR = 0x7001;
    private static final int STEP5012ERR = 0x7002;
    private static final int STEP5022ERR = 0x7003;
    private static final int STEP6002ERR = 0x7004;
    private static final int STEP6013ERR = 0x7005;
    private static final int REPEAT5001 = 0x7117;
    private static final int PLEASECHECKNETWORKERR = 0x7118;

    protected SerialPort mRfidPort;
    protected OutputStream mRfidOutputStream;
    private InputStream mRfidInputStream;
    private RfidReadThread mRfidReadThread;

    private class RfidReadThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                int size;
                try {
                    byte[] buffer = new byte[128];	//32
                    if (mRfidInputStream == null)
                        return;
                    size = mRfidInputStream.read(buffer);

                    if (size > 0) {
                        //onRfidDataReceived(buffer, size);
                        final byte[] UartRecv = new byte[1500];
                        int funret;
                        try {
                            funret = IDNRsock.ReadIDC(ServerIP, ServerPort, buffer, size, UartRecv);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            funret = 0x7000;
                        }
                        onRfidDataReceived(UartRecv, funret);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    IDNRSocket IDNRsock = new IDNRSocket();
    IDCReaderSDK mIDCReaderSDK = new IDCReaderSDK();

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);



//		IDNRsock.
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            File file = new File("/dev/" + "ttyS1");
            int baudrate = 115200;
            // int baudrate = 9600;

			/* 检查参数 */
            if (!file.exists()) {
                throw new InvalidParameterException();
            }
            mRfidPort = new SerialPort(file, baudrate, 0);
            mRfidOutputStream = mRfidPort.getOutputStream();
            mRfidInputStream = mRfidPort.getInputStream();
			/* 创建一个接收线程 */
            mRfidReadThread = new RfidReadThread();
            mRfidReadThread.start();

            // 记住信息
            SharedPreferences sf = getSharedPreferences("info", MODE_PRIVATE);
            SharedPreferences.Editor editor = sf.edit();
            editor.putString("CIDSTRING", "ttyS1");
            editor.putInt("BAUDRATT", 115200);
            editor.commit();

        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        new Thread() {
            public void run() {
                try {
                    do_exec("echo -wdir01 1 > /sys/devices/virtual/misc/mtgpio/pin");
                    do_exec("echo -wdout01 1 > /sys/devices/virtual/misc/mtgpio/pin");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                CopyAssets("wltlib", SDPATH + "/wltlib");
            };

        }.start();

        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        // 分别对应声音池数量，AudioManager.STREAM_MUSIC 和 0
        // 3. 使用soundPool加载声音，该操作位异步操作，如果资源很大，需要一定的时间
        soundId = soundPool.load(this, R.raw.bb, 1);
        // 4. 为声音池设定加载完成监听事件
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                // 表示加载完成
                isload = true;
            }
        });

        // mDecodeText = (TextView) findViewById(R.id.TextView02);

        Log.e("TAG", "未检测到设备信息");
//        ttt = new Myttt();
//        ttt.start();

//        RxScheduler.INSTANCE.doOnIOThread(new IOTask<Void>() {
//            @Override
//            public void doOnIOThread() {
//                while (isSend) {
//
//                    if (!new File(SDPATH + "/wltlib").exists()) {
//
//                        CopyAssets("wltlib", SDPATH + "/wltlib");
//                    } else if (new File(SDPATH + "/wltlib").listFiles().length < 4) {
//
//                        CopyAssets("wltlib", SDPATH + "/wltlib");
//                    } else {
////					startRfidSendingThread(mSearchCardCmd, mSearchCardCmd.length);
//                        if(!IDNRsock.UartInUsing){
//                            UartSend02head(2, Util.hexStringToBytes("5000"));
//                            Log.i("TAG", "发送寻卡指令");
//                            IDNRsock.ReadIDInStep = 0;
//                        }
//                    }
//
//                    try {
//                        sleep(500);
//                    } catch (InterruptedException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });

        if (mFindThread != null && !mFindThread.isDisposed()) {
            mFindThread.dispose();
        }

        mFindThread = new DisposableSubscriber<Long>() {
            @Override
            public void onNext(Long aLong) {
                if (!new File(SDPATH + "/wltlib").exists()) {

                    CopyAssets("wltlib", SDPATH + "/wltlib");
                } else if (new File(SDPATH + "/wltlib").listFiles().length < 4) {

                    CopyAssets("wltlib", SDPATH + "/wltlib");
                } else {
//					startRfidSendingThread(mSearchCardCmd, mSearchCardCmd.length);
                    if(!IDNRsock.UartInUsing){
                        UartSend02head(2, Util.hexStringToBytes("5000"));
                        Log.i("TAG", "发送寻卡指令");
                        IDNRsock.ReadIDInStep = 0;
                    }
                }
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };

        // 间隔1秒执行一次
        Flowable.interval(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mFindThread);

    }

    private boolean isSend = true;
    private DisposableSubscriber<Long> mFindThread;

    private DisposableSubscriber<byte[]> mObservable;

    private void onRfidDataReceived(final byte[] UartRecv, final int funret) {
        Log.e("TAG", "onRfidDataReceived = " + funret);

//        mObservable = Observable.just(UartRecv)
//                .observeOn(AndroidSchedulers.mainThread());
        if (mObservable != null && !mObservable.isDisposed()) {
            mObservable.dispose();
        }

        mObservable = new DisposableSubscriber<byte[]>() {
            @Override
            public void onNext(byte[] UartRecv) {
                switch(funret){
                    case STEP5001ERR:
                    case STEP5012ERR:
                    case STEP5022ERR:
                    case STEP6002ERR:
                    case STEP6013ERR:
                        //staus.setText("读证错误:"+ Util.bytesToHexString(Util.intTo2byte(funret)));
                        Log.e("TAG", "读证错误:"+ Util.bytesToHexString(Util.intTo2byte(funret)));
                        break;
                    case PLEASECHECKNETWORKERR:
                        Log.e("TAG","请检查网络连接");
                        //staus.setText("请检查网络连接");
                        break;

                    case STEP5001OK:
                        UartSend02head(12, UartRecv);
                        Log.e("TAG","寻卡成功");
                        //staus.setText("寻卡成功");
                        break;
                    case STEP5012OK:
                        UartSend02head(9, UartRecv);
                        Log.e("TAG","选卡成功");
                        //staus.setText("选卡成功");
                        break;
                    case STEP5022OK:
                        UartSend02head(12, UartRecv);
                        Log.e("TAG","读证..");
                        //staus.setText("读证..");
                        break;
                    case STEP6013OK:
//					case 0x6113:
//						MyLog.e("cmd read ok");
                        //staus.setText("读证成功");
                        Log.e("TAG","读证成功");

                        rec_index_pos = 1295;
                        System.arraycopy(IDNRsock.recData, 0, mIDCBuffer, 0, rec_index_pos);

                        if (isload) {
                            soundPool.play(soundId, 1.0f, 0.5f, 1, 0, 1.5f);
                        }
                        // 解码图片：
                        mIDCReaderSDK.DoDecodeCardInfo(mIDCBuffer);

                        String data1 = mIDCReaderSDK.GetPeopleBirthday();

                        String data2 = mIDCReaderSDK.GetStartDate();
                        data2 = data2.substring(0, 4) + "." + data2.substring(4, 6) + "." + data2.substring(6, 8) + "";

                        String data3 = mIDCReaderSDK.GetEndDate();
                        data3 = data3.substring(0, 4) + "." + data3.substring(4, 6) + "." + data3.substring(6, 8) + "";

                        Log.e("TAG", "name = " + mIDCReaderSDK.GetPeopleName());
                        Log.e("TAG", "sex = " + mIDCReaderSDK.GetPeopleSex());
                        Log.e("TAG", "minzu = " + mIDCReaderSDK.GetPeopleNation());
                        Log.e("TAG", "year = " + data1.substring(0, 4));
                        Log.e("TAG", "month = " + data1.substring(4, 6));
                        Log.e("TAG", "day = " + data1.substring(6, 8));
                        Log.e("TAG", "address = " + mIDCReaderSDK.GetPeopleAddress());
                        Log.e("TAG", "qixiao = " + mIDCReaderSDK.GetDepartment());
                        Log.e("TAG", "jiguan = " + data2 + "-" + data3);

//                        name.setText(mIDCReaderSDK.GetPeopleName());
//                        sex.setText(mIDCReaderSDK.GetPeopleSex());
//                        mingzu.setText(mIDCReaderSDK.GetPeopleNation());
//                        year.setText(data1.substring(0, 4));
//                        month.setText(data1.substring(4, 6));
//                        day.setText(data1.substring(6, 8));
//                        address.setText(mIDCReaderSDK.GetPeopleAddress());
//                        qixiao.setText(mIDCReaderSDK.GetDepartment());
//                        jiguan.setText(data2 + "-" + data3);
//
//                        cid.setText(mIDCReaderSDK.GetPeopleIDCode());
//
//                        Bitmap bitmap = BitmapFactory.decodeFile(SDPATH + "/wltlib/zp.bmp");
//                        mCardImg.setImageBitmap(bitmap);

//						MyLog.e("rec_data ok");
                        break;
                    case REPEAT5001:
                        //staus.setText("Repeat 5001...");
                        Log.e("TAG", "Repeat 5001...");
                        break;
                    default:
                        Log.e("TAG", "default Repeat 5001...");
                        break;
                }
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };

        Flowable.just(UartRecv)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObservable);

//        RxScheduler.INSTANCE.doOnUiThread(new UITask<Void>() {
//            @Override
//            public void doOnUIThread() {
//                switch(funret){
//                    case STEP5001ERR:
//                    case STEP5012ERR:
//                    case STEP5022ERR:
//                    case STEP6002ERR:
//                    case STEP6013ERR:
//                        //staus.setText("读证错误:"+ Util.bytesToHexString(Util.intTo2byte(funret)));
//                        Log.e("TAG", "读证错误:"+ Util.bytesToHexString(Util.intTo2byte(funret)));
//                        break;
//                    case PLEASECHECKNETWORKERR:
//                        Log.e("TAG","请检查网络连接");
//                        //staus.setText("请检查网络连接");
//                        break;
//
//                    case STEP5001OK:
//                        UartSend02head(12, UartRecv);
//                        Log.e("TAG","寻卡成功");
//                        //staus.setText("寻卡成功");
//                        break;
//                    case STEP5012OK:
//                        UartSend02head(9, UartRecv);
//                        Log.e("TAG","选卡成功");
//                        //staus.setText("选卡成功");
//                        break;
//                    case STEP5022OK:
//                        UartSend02head(12, UartRecv);
//                        Log.e("TAG","读证..");
//                        //staus.setText("读证..");
//                        break;
//                    case STEP6013OK:
////					case 0x6113:
////						MyLog.e("cmd read ok");
//                        //staus.setText("读证成功");
//                        Log.e("TAG","读证成功");
//
//                        rec_index_pos = 1295;
//                        System.arraycopy(IDNRsock.recData, 0, mIDCBuffer, 0, rec_index_pos);
//
//                        if (isload) {
//                            soundPool.play(soundId, 1.0f, 0.5f, 1, 0, 1.5f);
//                        }
//                        // 解码图片：
//                        mIDCReaderSDK.DoDecodeCardInfo(mIDCBuffer);
//
//                        String data1 = mIDCReaderSDK.GetPeopleBirthday();
//
//                        String data2 = mIDCReaderSDK.GetStartDate();
//                        data2 = data2.substring(0, 4) + "." + data2.substring(4, 6) + "." + data2.substring(6, 8) + "";
//
//                        String data3 = mIDCReaderSDK.GetEndDate();
//                        data3 = data3.substring(0, 4) + "." + data3.substring(4, 6) + "." + data3.substring(6, 8) + "";
//
//                        Log.e("TAG", "name = " + mIDCReaderSDK.GetPeopleName());
//                        Log.e("TAG", "sex = " + mIDCReaderSDK.GetPeopleSex());
//                        Log.e("TAG", "minzu = " + mIDCReaderSDK.GetPeopleNation());
//                        Log.e("TAG", "year = " + data1.substring(0, 4));
//                        Log.e("TAG", "month = " + data1.substring(4, 6));
//                        Log.e("TAG", "day = " + data1.substring(6, 8));
//                        Log.e("TAG", "address = " + mIDCReaderSDK.GetPeopleAddress());
//                        Log.e("TAG", "qixiao = " + mIDCReaderSDK.GetDepartment());
//                        Log.e("TAG", "jiguan = " + data2 + "-" + data3);
//
////                        name.setText(mIDCReaderSDK.GetPeopleName());
////                        sex.setText(mIDCReaderSDK.GetPeopleSex());
////                        mingzu.setText(mIDCReaderSDK.GetPeopleNation());
////                        year.setText(data1.substring(0, 4));
////                        month.setText(data1.substring(4, 6));
////                        day.setText(data1.substring(6, 8));
////                        address.setText(mIDCReaderSDK.GetPeopleAddress());
////                        qixiao.setText(mIDCReaderSDK.GetDepartment());
////                        jiguan.setText(data2 + "-" + data3);
////
////                        cid.setText(mIDCReaderSDK.GetPeopleIDCode());
////
////                        Bitmap bitmap = BitmapFactory.decodeFile(SDPATH + "/wltlib/zp.bmp");
////                        mCardImg.setImageBitmap(bitmap);
//
////						MyLog.e("rec_data ok");
//                        break;
//                    case REPEAT5001:
//                        //staus.setText("Repeat 5001...");
//                        Log.e("TAG", "Repeat 5001...");
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });


//        runOnUiThread(new Runnable() {
//            public void run() {
////				final byte[] UartRecv = new byte[1500];
////				int funret;
////				try {
////					funret = IDNRsock.ReadIDC(ServerIP, ServerPort, buffer, size, UartRecv);
////				} catch (IOException e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////					funret = 0x7000;
////				}
//                switch(funret){
//                    case STEP5001ERR:
//                    case STEP5012ERR:
//                    case STEP5022ERR:
//                    case STEP6002ERR:
//                    case STEP6013ERR:
//                        //staus.setText("读证错误:"+ Util.bytesToHexString(Util.intTo2byte(funret)));
//                        Log.e("TAG", "读证错误:"+ Util.bytesToHexString(Util.intTo2byte(funret)));
//                        break;
//                    case PLEASECHECKNETWORKERR:
//                        Log.e("TAG","请检查网络连接");
//                        //staus.setText("请检查网络连接");
//                        break;
//
//                    case STEP5001OK:
//                        UartSend02head(12, UartRecv);
//                        Log.e("TAG","寻卡成功");
//                        //staus.setText("寻卡成功");
//                        break;
//                    case STEP5012OK:
//                        UartSend02head(9, UartRecv);
//                        Log.e("TAG","选卡成功");
//                        //staus.setText("选卡成功");
//                        break;
//                    case STEP5022OK:
//                        UartSend02head(12, UartRecv);
//                        Log.e("TAG","读证..");
//                        //staus.setText("读证..");
//                        break;
//                    case STEP6013OK:
////					case 0x6113:
////						MyLog.e("cmd read ok");
//                        //staus.setText("读证成功");
//                        Log.e("TAG","读证成功");
//
//                        rec_index_pos = 1295;
//                        System.arraycopy(IDNRsock.recData, 0, mIDCBuffer, 0, rec_index_pos);
//
//                        if (isload) {
//                            soundPool.play(soundId, 1.0f, 0.5f, 1, 0, 1.5f);
//                        }
//                        // 解码图片：
//                        mIDCReaderSDK.DoDecodeCardInfo(mIDCBuffer);
//
//                        String data1 = mIDCReaderSDK.GetPeopleBirthday();
//
//                        String data2 = mIDCReaderSDK.GetStartDate();
//                        data2 = data2.substring(0, 4) + "." + data2.substring(4, 6) + "." + data2.substring(6, 8) + "";
//
//                        String data3 = mIDCReaderSDK.GetEndDate();
//                        data3 = data3.substring(0, 4) + "." + data3.substring(4, 6) + "." + data3.substring(6, 8) + "";
//
//                        Log.e("TAG", "name = " + mIDCReaderSDK.GetPeopleName());
//                        Log.e("TAG", "sex = " + mIDCReaderSDK.GetPeopleSex());
//                        Log.e("TAG", "minzu = " + mIDCReaderSDK.GetPeopleNation());
//                        Log.e("TAG", "year = " + data1.substring(0, 4));
//                        Log.e("TAG", "month = " + data1.substring(4, 6));
//                        Log.e("TAG", "day = " + data1.substring(6, 8));
//                        Log.e("TAG", "address = " + mIDCReaderSDK.GetPeopleAddress());
//                        Log.e("TAG", "qixiao = " + mIDCReaderSDK.GetDepartment());
//                        Log.e("TAG", "jiguan = " + data2 + "-" + data3);
//
////                        name.setText(mIDCReaderSDK.GetPeopleName());
////                        sex.setText(mIDCReaderSDK.GetPeopleSex());
////                        mingzu.setText(mIDCReaderSDK.GetPeopleNation());
////                        year.setText(data1.substring(0, 4));
////                        month.setText(data1.substring(4, 6));
////                        day.setText(data1.substring(6, 8));
////                        address.setText(mIDCReaderSDK.GetPeopleAddress());
////                        qixiao.setText(mIDCReaderSDK.GetDepartment());
////                        jiguan.setText(data2 + "-" + data3);
////
////                        cid.setText(mIDCReaderSDK.GetPeopleIDCode());
////
////                        Bitmap bitmap = BitmapFactory.decodeFile(SDPATH + "/wltlib/zp.bmp");
////                        mCardImg.setImageBitmap(bitmap);
//
////						MyLog.e("rec_data ok");
//                        break;
//                    case REPEAT5001:
//                        //staus.setText("Repeat 5001...");
//                        Log.e("TAG", "Repeat 5001...");
//                        break;
//                    default:
//                        break;
//                }
//            }
//
//        });
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        this.finish();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if (ttt != null) {
            ttt.interrupt();
        }
        mObservable = null;
        if (mFindThread != null && !mFindThread.isDisposed()) {
            mFindThread.dispose();

        }

        if (mObservable != null && !mObservable.isDisposed()) {
            mObservable.dispose();

        }

        new Thread() {
            public void run() {

                try {

                    // SerialPortActivity.do_exec("echo -wdir01 0 >
                    // /sys/devices/virtual/misc/mtgpio/pin");
                    do_exec("echo -wdout01 0 > /sys/devices/virtual/misc/mtgpio/pin");

                } catch (Exception e) {
                    // Toast.makeText(getApplicationContext(), "io operation
                    // failed", 3000).show();
                }
            };
        }.start();
    }

    void startRfidSendingThread(final byte[] buffer, final int len) {
        mBuffer = new byte[len];
        for (int i = 0; i < len; i++) {
            mBuffer[i] = buffer[i];
        }
        if (mRfidPort != null) {
            if (mRfidOutputStream != null) {
                try {
                    mRfidOutputStream.write(mBuffer);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        // printByte("send thread", buffer, len);
    }

    // 循环读卡
    class Myttt extends Thread {
        public void run() {
            while (!isInterrupted()) {

                if (!new File(SDPATH + "/wltlib").exists()) {

                    CopyAssets("wltlib", SDPATH + "/wltlib");
                } else if (new File(SDPATH + "/wltlib").listFiles().length < 4) {

                    CopyAssets("wltlib", SDPATH + "/wltlib");
                } else {
//					startRfidSendingThread(mSearchCardCmd, mSearchCardCmd.length);
                    if(!IDNRsock.UartInUsing){
                        UartSend02head(2, Util.hexStringToBytes("5000"));
                        Log.i("TAG", "发送寻卡指令");
                        IDNRsock.ReadIDInStep = 0;
                    }
                }

                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
    };


    private void CopyAssets(String assetDir, String dir) {
        String[] files;
        try {
            files = this.getResources().getAssets().list(assetDir);
        } catch (IOException e1) {
            return;
        }
        File mWorkingPath = new File(dir);
        // if this directory does not exists, make one.
        if (!mWorkingPath.exists()) {
            if (!mWorkingPath.mkdirs()) {

            }
        }

        for (int i = 0; i < files.length; i++) {
            try {
                String fileName = files[i];
                // we make sure file name not contains '.' to be a folder.
                if (!fileName.contains(".")) {
                    if (0 == assetDir.length()) {
                        CopyAssets(fileName, dir + fileName + "/");
                    } else {
                        CopyAssets(assetDir + "/" + fileName, dir + fileName + "/");
                    }
                    continue;
                }
                File outFile = new File(mWorkingPath, fileName);
                if (outFile.exists())
                    outFile.delete();
                InputStream in = null;
                if (0 != assetDir.length())
                    in = getAssets().open(assetDir + "/" + fileName);
                else
                    in = getAssets().open(fileName);
                OutputStream out = new FileOutputStream(outFile);

                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void UartSend02head(int j, byte[] buff) {
        int i;
        byte verify = 0;
        final byte[] TxBuffer = new byte[50];


        TxBuffer[0] = 0x02;
        byte[] len1 = Util.intTo2byte(j);
        TxBuffer[1] = len1[0];
        TxBuffer[2] = len1[1];
        System.arraycopy(buff, 0, TxBuffer, 3, j);
//		memcpy(&TxBuffer[3], buff, len);
        for (i = 3; i < j + 3; i++)	//校验=长数据（nB）/0x100
        {
            verify ^= TxBuffer[i];
        }
        TxBuffer[j + 3] = verify;
        TxBuffer[j + 4] = 0x03;
        startRfidSendingThread(TxBuffer, j+5);

    }

    @Override
    protected void onDestroy() {
        if (mRfidReadThread != null) {
            mRfidReadThread.interrupt();
        }
        closeSerialPort();
        mRfidPort = null;

        soundPool.release();
        soundPool = null;
        // 关高电频

        try {
            // do_exec("echo -wdout00 0 >
            // /sys/devices/virtual/misc/mtgpio/pin");

        } catch (Exception e) {
            // Toast.makeText(getApplicationContext(), "io operation failed",
            // 0).show();
        }

        super.onDestroy();
    }

    public void closeSerialPort() {
        if (mRfidPort != null) {
            mRfidPort.close();
            mRfidPort = null;
        }
    }

    public static String do_exec(String cmd) {

        Process process = null;
        DataOutputStream os = null;

        String command = cmd;
        Log.d("*** NETLH_E ***", " exec command:" + command);
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
            return cmd;
        } catch (Exception e) {
            Log.d("*** NETLH_E ***", "Unexpected error - Here is what I know: " + e.getMessage());
        }

        return cmd;
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, IdVerifyActivity.class));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
