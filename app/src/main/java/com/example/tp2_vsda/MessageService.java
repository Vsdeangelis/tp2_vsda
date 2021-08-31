package com.example.tp2_vsda;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Telephony;
import android.util.Log;

import androidx.annotation.Nullable;

public class MessageService extends Service {
    @SuppressLint("Range")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Uri msj= Uri.parse("content://sms");
        ContentResolver cr= getContentResolver();

        Runnable corneta= new Runnable() {
            @Override
            public void run() {
                while(true){
                    Cursor c= cr.query(msj, null, null, null, null);
                    String num=null;
                    String message= null;
                    Log.d("paso", "aca estoy");
                    if(c!=null && c.getCount()>0){
                        int i =1;
                        while(c.moveToNext() & i<6){
                            num= c.getString(c.getColumnIndex(Telephony.Sms.ADDRESS));
                            message= c.getString(c.getColumnIndex(Telephony.Sms.BODY));
                            Log.d("mensajes", num+ " "+ message);
                            i++;
                        }
                        try {
                            Thread.sleep(9000);
                        } catch (InterruptedException e) {
                            break;
                        }
                        c.close();
                    }
                }
            }
        };
        Thread t= new Thread(corneta);
        t.start();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
