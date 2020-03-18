package com.patent.smartterminal.util.serialPort;

import android.serialport.SerialPort;
import android.util.Log;

import com.patent.smartterminal.util.ByteUtil;
import com.patent.common.DataUtils;
import com.patent.smartterminal.util.LogUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;


/**
 * 串口基类
 */
public abstract class SerialPortUtil {
    private SerialPort serialPort = null;
    InputStream inputStream = null;
    OutputStream outputStream = null;
    /**
     * 打开串口，接收数据
     * 通过串口，接收单片机发送来的数据
     * @param address 串口"/dev/ttyS6" s7 s2
     */
    public void openSerialPort(String address,int baudrate) {
        try {
            serialPort = new SerialPort(new File(address), baudrate, 0);
            //调用对象SerialPort方法，获取串口中"读和写"的数据流
            inputStream = serialPort.getInputStream();
            outputStream = serialPort.getOutputStream();

        } catch (IOException e) {
            LogUtils.d("SerialPortUtils","open error: " + e);
            e.printStackTrace();
        }
        getSerialPort();
    }

    abstract void getSerialPort();

    /**
     * 关闭串口
     * 关闭串口中的输入输出流
     */
    public void closeSerialPort() {
        LogUtils.i("SerialPortUtil", "关闭串口");

        try {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }


            if (serialPort != null) {
                serialPort.close();
                serialPort = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送数据
     * 通过串口，发送数据到单片机
     *
     * @param data 要发送的数据
     */
    public void sendSerialPort(String data) {
        try {
            byte[] sendData = DataUtils.HexToByteArr(data);
            outputStream.write(sendData);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送数据
     * 通过串口，发送数据到单片机
     *
     * @param sendData 要发送的数据
     */
    public void sendSerialPort(byte[] sendData) {
        try {
            LogUtils.d("sendStringData", DataUtils.ByteArrToHex(sendData));
            outputStream.write(sendData);
//            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 发送数据
     * 通过串口，发送数据到单片机
     *
     * @param sendData 要发送的数据
     */
    public void sendSerialPort(byte[] sendData,int offset,int length) {
        try {
            LogUtils.d("sendStringData", Arrays.toString(sendData));
            outputStream.write(sendData,offset,length);
//            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
