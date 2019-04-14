package com.rahulrspp.serviceserverside;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import java.util.Random;

public class MyRemoteServerSideService extends Service {

    private int randomNo=0;
    private boolean isNoGenerationOn =false;
    private final int GET_COUNT = 0;

   private class MyRemoteHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case GET_COUNT:
                    Message message = Message.obtain(null, GET_COUNT);
                    message.arg1=getRandomNo();

                    try {
                        msg.replyTo.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;

            }
        }
    }

    Messenger myMessenger=new Messenger(new MyRemoteHandler());

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println(":::: Bind");
        return myMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println(":::: UnBind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        System.out.println(":::: ReBind");

        super.onRebind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println(":::: Service Start");




        new Thread(new Runnable() {
            @Override
            public void run() {
                isNoGenerationOn =true;
                startRandomNoGenerator();
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        System.out.println(":::: Service Stop");
         stopRandomNoGenerator();
    }

    private void startRandomNoGenerator(){
        while(isNoGenerationOn){
            try {
                Thread.sleep(2000);
                Random random=new Random();
                randomNo = random.nextInt(100);
                System.out.println(":::: Generated No: "+randomNo);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void stopRandomNoGenerator(){
        isNoGenerationOn=false;
    }


    public int getRandomNo() {
        return randomNo;
    }


}
