package com.example.battery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btnt;
    TextView Btrylvl;
    Boolean isTorchon=false;
    CameraManager cameraManager;
    String cameraid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnt=findViewById(R.id.btn);
        try {
            cameraManager=(CameraManager)getSystemService(Context.CAMERA_SERVICE);
            cameraid=cameraManager.getCameraIdList()[0];

        }catch(Exception e){}

        btnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isTorchon){
                    isTorchon=false;
                    btnt.setText("Turn on Flash");


                }
                else{
                    isTorchon=true;
                    btnt.setText("Turn off Flash");
                }
                switchTorch(isTorchon);

            }
        });
        Btrylvl=findViewById(R.id.btrylvl);
        this.registerReceiver(this.myBatteryReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));


    }
    BroadcastReceiver myBatteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int BatteryLevel=intent.getIntExtra("level",0);
            Btrylvl.setText("battery : "+BatteryLevel+"%");
        }
    };
    public void switchTorch(boolean status){
        try {
            cameraManager.setTorchMode(cameraid,status);

        }catch(Exception e){}

    }
}