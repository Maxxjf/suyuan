package com.qcloud.suyuan;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qcloud.suyuan.utils.UsbController;

/**
 * 类说明：
 * Author: Kuzan
 * Date: 2018/3/26 20:13.
 */
public class PrinterActivity extends AppCompatActivity {

    private Button btn_conn = null;
    private Button btnSend = null;
    private Button btn_test = null;
    private Button btnClose = null;
    private EditText txt_content = null;

    private Button btn_print_image = null;

    //private int VID = 0x1CBE;
    //private int PID = 0x0003;
    private int[][] u_infor;
    UsbController usbCtrl = null;
    UsbDevice dev = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer);
        btn_conn = (Button)findViewById(R.id.btn_conn);
        btnSend = (Button)findViewById(R.id.btnSend);
        btn_test = (Button)findViewById(R.id.btn_test);
        btnClose = (Button)findViewById(R.id.btnClose);
        txt_content = (EditText)findViewById(R.id.txt_content);
        btn_print_image = (Button)findViewById(R.id.btn_print_iamge);

        btn_conn.setOnClickListener(new ClickEvent());
        btnSend.setOnClickListener(new ClickEvent());
        btn_test.setOnClickListener(new ClickEvent());
        btnClose.setOnClickListener(new ClickEvent());
        btn_print_image.setOnClickListener(new ClickEvent());

        btnSend.setEnabled(false);
        btn_test.setEnabled(false);
        btnClose.setEnabled(false);
        btn_print_image.setEnabled(false);

        usbCtrl = new UsbController(this);
        // 5 ==> OTHER PLEASE NEED CHANGE TOO , NOTE
        u_infor = new int[5][2];// vid[0] pid[1]
        // heshuo
        u_infor[0][0] = 0x1CBE;
        u_infor[0][1] = 0x0a03;
        u_infor[1][0] = 0x0485;
        u_infor[1][1] = 0x7541;
        u_infor[2][0] = 0x1CB0;
        u_infor[2][1] = 0x0003;
        // 2015-09-19
        u_infor[3][0] = 0x0525;
        u_infor[3][1] = 0xa702;
        // 2016-11-24 DUOGU BLUETOOTH USE PID VID
        u_infor[4][0] = 0x28E9;
        u_infor[4][1] = 0x0289;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        usbCtrl.close();
    }

    //检查是否具有访问usb设备的权限
    public boolean CheckUsbPermission(){
        if( dev != null ){
            if( usbCtrl.isHasPermission(dev)){
                return true;
            }
        }
        btnSend.setEnabled(false);
        btn_test.setEnabled(false);
        btnClose.setEnabled(false);
        btn_conn.setEnabled(true);
        btn_print_image.setEnabled(false);
        Toast.makeText(getApplicationContext(), "无USB设备访问权限,请重新连接USB设备",
                Toast.LENGTH_SHORT).show();
        return false;
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
//                case UsbController.Companion.getUSB_CONNECTED():
//                    Toast.makeText(getApplicationContext(), "已获取USB设备访问权限",
//                            Toast.LENGTH_SHORT).show();
//                    btnSend.setEnabled(true);
//                    btn_test.setEnabled(true);
//                    btnClose.setEnabled(true);
//                    btn_conn.setEnabled(false);
//                    btn_print_image.setEnabled(true);
//                    break;
                default:
                    break;
            }
        }
    };

    //按钮点击事件
    class ClickEvent implements View.OnClickListener {
        public void onClick(View v) {
            byte isHasPaper;
            if( v == btn_conn ){
                usbCtrl.close();
                int  i = 0;
                for( i = 0 ; i < 5 ; i++ ){
                    dev = usbCtrl.getDev(u_infor[i][0],u_infor[i][1]);
                    if(dev != null)
                        break;
                }

                if( dev != null ){
                    if( !(usbCtrl.isHasPermission(dev))){
                        Log.d("usb调试", "请求USB设备权限.");
                        usbCtrl.getPermission(dev);
                    }else{
                        Toast.makeText(getApplicationContext(), "已获取USB设备访问权限",
                                Toast.LENGTH_SHORT).show();
                        btnSend.setEnabled(true);
                        btn_test.setEnabled(true);
                        btnClose.setEnabled(true);
                        btn_conn.setEnabled(false);

                        btn_print_image.setEnabled(true);
                    }
                }
            }else if( v == btnSend ){
                isHasPaper = usbCtrl.revByte(dev);
                if( isHasPaper == 0x38 ){
                    Toast.makeText(getApplicationContext(), "The printer has no paper",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                String txt_msg = txt_content.getText().toString();
                if( CheckUsbPermission() == true ){
                    usbCtrl.sendMsg(txt_msg, "GBK", dev);
                }
            }else if( v == btn_test ){
                Log.d("Thread:btn_test", "thread create...\n");
                Thread thread=new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Log.d("Thread:btn_test", "thread exe...\n" );
                        byte isHasPaper;
                        String msg = "";
                        String lang = "ch";
                        byte[] cmd = new byte[4];
                        isHasPaper = usbCtrl.revByte(dev);
                        if( isHasPaper == 0x38 ){
                            Toast.makeText(getApplicationContext(), "The printer has no paper",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        cmd[0]=0x1B;
                        cmd[1]=0x42;
                        cmd[2]=0x04;
                        cmd[3]=0x01;
                        usbCtrl.sendByte(cmd, dev);       //蜂鸣指令

                        printImage();

                        cmd[0] = 0x1b;
                        cmd[1] = 0x21;
                        if((lang.compareTo("en")) == 0){
                            cmd[2] |= 0x10;
                            usbCtrl.sendByte(cmd, dev);    //倍宽、倍高模式
                            usbCtrl.sendMsg("Congratulations!\n", "GBK", dev);
                            cmd[2] &= 0xEF;
                            usbCtrl.sendByte(cmd, dev);     //取消倍高、倍宽模式
                            msg = "  You have sucessfully created communications between your device and our usb printer.\n\n"
                                    +" Our company is a high-tech enterprise which specializes" +
                                    " in R&D,manufacturing,marketing of thermal printers and barcode scanners.\n\n";

                            usbCtrl.sendMsg(msg, "GBK", dev);
                        }else if((lang.compareTo("ch")) == 0){
                            cmd[2] |= 0x10;
                            usbCtrl.sendByte(cmd, dev);   //倍宽、倍高模式
                            usbCtrl.sendMsg("恭喜您\n", "GBK", dev);
                            //cmd[2] &= 0xEF;
                            cmd[2] &= 0x0;
                            usbCtrl.sendByte(cmd, dev);   //取消倍高、倍宽模式
                            msg = "  您已经成功的连接上了我们的usb打印机！\n\n"
                                    + "  我们公司是一家专业从事研发，生产，销售商用票据打印机和条码扫描设备于一体的高科技企业.\n\n";
                            usbCtrl.sendMsg(msg, "GBK", dev);
                        }
                        cmd[0]=0x1D;
                        cmd[1]=0x56;
                        cmd[2]=0x42;
                        cmd[3]=90;
                        usbCtrl.sendByte(cmd, dev);       //切刀指令
                        printPinTiaoDemo();
                        // TODO Auto-generated method stub
                        Message message=new Message();
                        message.what=3;
                        mHandler.sendMessage(message);
                        Log.d("Thread:btn_test", "thread leave...\n");
                    }
                });
                thread.start();
                Log.d("Thread:btn_test", "thread end...\n");

            }else if( v == btn_print_image ){
                //Log.d("Thread:btn_print_image", "thread create...\n");
                Thread thread=new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        //Log.d("Thread:btn_print_image", "thread exe...\n" );
/*						byte isHasPaper;
						isHasPaper = usbCtrl.revByte(dev);

						if( (isHasPaper&(1<<5)) != 0 ){
							Toast.makeText(getApplicationContext(), "The printer has no paper", Toast.LENGTH_SHORT).show();
							return;
						}*/
/*						Toast.makeText(getApplicationContext(), "The printer has no paper",
								Toast.LENGTH_SHORT).show();*/
                        //printPinTiaoDemo();
                        printImage();
                        //Log.d("Thread:btn_print_image", "printImage end...\n");
                        // do nothing
                        // TODO Auto-generated method stub
                        Message message=new Message();
                        message.what=3;
                        mHandler.sendMessage(message);
                        //Log.d("Thread:btn_print_image", "thread leave...\n");
                    }
                });
                thread.start();
                //Log.d("Thread:btn_print_image", "thread end...\n");
            }else if( v == btnClose ){
                usbCtrl.close();
                btnSend.setEnabled(false);
                btn_test.setEnabled(false);
                btn_print_image.setEnabled(false);
                btnClose.setEnabled(false);
                btn_conn.setEnabled(true);
            }
        }

        private void printPinTiaoDemo(){
            String msg_header = "消团团消费凭条\n";
            String msg_header2 = "卡券消费\n";
            String msg_body = "商品名称：xxxxxxx\n";
            String msg_end = "*********谢谢使用**********\n\n\n\n\n";

            // barcode information and size (byte)
            byte[] msg_barcode_info = "1709191234550001".getBytes();
            byte[] msg_barcode_size_byte = new byte[1];
            int msg_barcode_size = msg_barcode_info.length;
            msg_barcode_size_byte[0] = (byte) msg_barcode_size;

            // ESC @ 初始化打印机
            byte[] cmd_init_printer = new byte[2];
            cmd_init_printer[0]=0x1B;
            cmd_init_printer[1]=0x40;

            // ESC ! n 设置倍高倍宽
            byte[] cmd_double_width_heigh = new byte[3];
            cmd_double_width_heigh[0]=0x1B;
            cmd_double_width_heigh[1]=0x21;
            cmd_double_width_heigh[2]=0x30;
            byte[] cmd_reset_width_heigh = new byte[3];
            cmd_reset_width_heigh[0]=0x1B;
            cmd_reset_width_heigh[1]=0x21;
            cmd_reset_width_heigh[2]=0x00;

            // ESC a n 设置对齐方式
            byte[] cmd_align_left = new byte[3];
            cmd_align_left[0]=0x1B;
            cmd_align_left[1]=0x61;
            cmd_align_left[2]=0x00;
            byte[] cmd_align_center = new byte[3];
            cmd_align_center[0]=0x1B;
            cmd_align_center[1]=0x61;
            cmd_align_center[2]=0x01;
            byte[] cmd_align_right = new byte[3];
            cmd_align_right[0]=0x1B;
            cmd_align_right[1]=0x61;
            cmd_align_right[2]=0x02;

            // GS h n 设置条码高度
            byte[] cmd_set_barcode_height = new byte[3];
            cmd_set_barcode_height[0]=0x1d;
            cmd_set_barcode_height[1]=0x68;
            cmd_set_barcode_height[2]=0x64;	// 100 bit pixel
            // GS H n 设置条码高度
            byte[] cmd_set_barcode_HRI_position = new byte[3];
            cmd_set_barcode_HRI_position[0]=0x1d;
            cmd_set_barcode_HRI_position[1]=0x48;
            cmd_set_barcode_HRI_position[2]=0x02;	// dec 2 or 50: HRI 字符输出在条码下方

            // GS k m n d1...dn 打印条码
            byte[] cmd_barcode_header = new byte[4];
            cmd_barcode_header[0]=0x1d;
            cmd_barcode_header[1]=0x6b;
            cmd_barcode_header[2]= 73;	// code128 0x49
            cmd_barcode_header[3]=msg_barcode_size_byte[0];	// size of barcode MSG info

            // init printer
            usbCtrl.sendByte(cmd_init_printer, dev);

            // set align center and width height double, and print data
            usbCtrl.sendByte(cmd_align_center, dev);
            usbCtrl.sendByte(cmd_double_width_heigh, dev);
            usbCtrl.sendMsg(msg_header, "GBK", dev);
            usbCtrl.sendMsg(msg_header2, "GBK", dev);

            // reset align and font and print data
            usbCtrl.sendByte(cmd_reset_width_heigh, dev);
            usbCtrl.sendByte(cmd_align_left, dev);
            usbCtrl.sendMsg(msg_body, "GBK", dev);

            // set align and barcode print
            usbCtrl.sendByte(cmd_align_center, dev);
            usbCtrl.sendByte(cmd_set_barcode_height, dev);
            usbCtrl.sendByte(cmd_set_barcode_HRI_position, dev);
            usbCtrl.sendByte(cmd_barcode_header, dev);
            usbCtrl.sendByte(msg_barcode_info, dev);

            // reset align and print
            usbCtrl.sendByte(cmd_align_left, dev);
            usbCtrl.sendMsg(msg_end, "GBK", dev);

            // reinit printer
            usbCtrl.sendByte(cmd_init_printer, dev);
        }
    }

    //打印图形
    private void printImage_old() {
//        int i = 0,s = 0,j = 0,index = 0;
//        byte[] temp = new byte[56];
//        byte[] sendData = null;
//        PrintPic pg = new PrintPic();
//        pg.initCanvas(384);
//        pg.initPaint();
//        pg.drawImage(0, 0, "/mnt/sdcard/icon.jpg");
//        sendData = pg.printDraw();
//
//        for( i = 0 ; i < pg.getLength() ; i++ ){  //每隔一行加上包头，发送一次数据
//            s = 0;
//            temp[s++] = 0x1D;
//            temp[s++] = 0x76;
//            temp[s++] = 0x30;
//            temp[s++] = 0x00;
//            temp[s++] = (byte)(pg.getWidth() / 8);
//            temp[s++] = 0x00;
//            temp[s++] = 0x01;
//            temp[s++] = 0x00;
//            for( j = 0 ; j < (pg.getWidth() / 8) ; j++ )
//                temp[s++] = sendData[index++];
//            usbCtrl.sendByte(temp, dev);
//        }
    }
    private void printImage() {

//        //Log.d("Thread:btn_print_image", "printImage ...\n");
//        int i = 0,s = 0,j = 0,index = 0;
//        byte[] sendData = null;
//        PrintPic pg = new PrintPic();
//        Bitmap srcimag = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.icon_sellers);
//
//        pg.initCanvas(srcimag.getWidth(), srcimag.getHeight());
//        pg.drawImage(0, 0, srcimag);
//        sendData = pg.printDraw();
//
//        if(sendData == null){
//            Log.d("Thread:btn_print_image", "sendData == null ...\n");
//        }
//        // usb bulk < 16k bytes
//        if(sendData != null){  //每隔一行加上包头，发送一次数据
//            byte[] temp = new byte[8];
//            temp[s++] = 0x1D;
//            temp[s++] = 0x76;
//            temp[s++] = 0x30;
//            temp[s++] = 0x00;
//            temp[s++] = (byte)((pg.getWidth() / 8)% 256);
//            temp[s++] = (byte)((pg.getWidth() / 8)/ 256);
//            temp[s++] = (byte)(pg.getLength()% 256);
//            temp[s++] = (byte)(pg.getLength()/ 256);
//            if(usbCtrl.sendByte(temp, dev) < 0) {
//                Log.e("Thread:btn_print_image", "esc pos header bulkTranser failed ...\n");
//                return;
//            }
//            if(usbCtrl.sendByte(sendData, dev) < 0){
//                Log.e("Thread:btn_print_image", "sendData bulkTranser failed ...\n");
//                return;
//            }
//
//            // 5 行空白走纸
//            byte[] msg_end = new byte[5];
//            msg_end[0] = 0x0a;
//            msg_end[1] = 0x0a;
//            msg_end[2] = 0x0a;
//            msg_end[3] = 0x0a;
//            msg_end[4] = 0x0a;
//            usbCtrl.sendByte(msg_end, dev);
//
///*			int data_bulk = sendData.length/4;
//			for(int times = 0; times < 4; times++){
//				byte[] sSenddata = new byte[data_bulk];
//				System.arraycopy(sendData, data_bulk * times, sSenddata, 0, data_bulk - 1);
//
//				if(-1 ==usbCtrl.sendByte(sSenddata, dev)){
//					Log.d("Thread:btn_print_image", "image bulkTranser failed ...\n");
//					return;
//				}
//				try{
//					Thread.sleep(500,100);
//					//Thread.sleep(500,1000);// can work
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}*/
//
//        }
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, PrinterActivity.class));
    }
}
