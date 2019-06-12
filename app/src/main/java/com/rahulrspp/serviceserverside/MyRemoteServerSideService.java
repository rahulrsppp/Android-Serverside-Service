package com.rahulrspp.serviceserverside;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

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
        Toast.makeText(getApplicationContext(),"Service Bind", Toast.LENGTH_SHORT).show();
        return myMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(getApplicationContext(),"Service UnBind", Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Toast.makeText(getApplicationContext(),"Service ReBind", Toast.LENGTH_SHORT).show();

        super.onRebind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(),"Service Start", Toast.LENGTH_SHORT).show();


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
