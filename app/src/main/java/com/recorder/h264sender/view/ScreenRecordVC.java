package com.recorder.h264sender.view;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.laifeng.sopcastsdk.configuration.AudioConfiguration;
import com.laifeng.sopcastsdk.configuration.VideoConfiguration;
import com.laifeng.sopcastsdk.screen.ScreenRecordActivity;
import com.laifeng.sopcastsdk.stream.packer.tcp.TcpPacker;
import com.laifeng.sopcastsdk.stream.sender.OnSenderListener;
import com.laifeng.sopcastsdk.stream.sender.tcp.TcpSender;
import com.recorder.h264sender.Constant;
import com.recorder.h264sender.R;


public class ScreenRecordVC extends ScreenRecordActivity implements OnSenderListener {
    private AppCompatButton btn_start;
    private String ip;
    private VideoConfiguration mVideoConfiguration;
    private TcpSender tcpSender;
    final static String TAG = "ScreenRecordVC";
    private boolean isRecord = false;
    private EditText et_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ip = Constant.ip;
        initialView();
    }

    private void initialView() {
        btn_start = findViewById(R.id.btn_start);
        et_main = findViewById(R.id.et_main);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRecord) {
                    if (!TextUtils.isEmpty(et_main.getText().toString())) {
                        ip = et_main.getText().toString();
                    }
                    requestRecording();
                    Log.e(TAG, "Ip = " + ip);
                    btn_start.setText("开始");
                } else {
                    stopRecording();
                    Log.e("Test", "停止");
                    btn_start.setText("停止");
                }
            }
        });
    }

    @Override
    protected void requestRecordSuccess() {
        super.requestRecordSuccess();
        isRecord = true;
        startRecord();
    }

    @Override
    protected void requestRecordFail() {
        super.requestRecordFail();
    }

    private void startRecord() {
        TcpPacker packer = new TcpPacker();
        packer.setSendAudio(true);
        packer.initAudioParams(AudioConfiguration.DEFAULT_FREQUENCY, 16, false);
        mVideoConfiguration = new VideoConfiguration.Builder().build();
        setVideoConfiguration(mVideoConfiguration);
        setRecordPacker(packer);

        tcpSender = new TcpSender(ip, Constant.port);
        tcpSender.setSenderListener(this);
        tcpSender.setVideoParams(mVideoConfiguration);
        tcpSender.connect();
        setRecordSender(tcpSender);
        startRecording();
    }

    @Override
    public void onConnecting() {
    }

    @Override
    public void onConnected() {
    }

    @Override
    public void onDisConnected() {
    }

    @Override
    public void onPublishFail() {
    }

    @Override
    public void onNetGood() {

    }

    @Override
    public void onNetBad() {

    }


    @Override
    protected void onDestroy() {
        tcpSender.stop();
        super.onDestroy();
    }

}
