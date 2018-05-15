package com.lgd.androidpcmtoaac_rtmp_rtsp;

import android.graphics.SurfaceTexture;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lgd.androidpcmtoaac_rtmp_rtsp.utils.AppOperator;
import com.lgd.androidpcmtoaac_rtmp_rtsp.utils.ConstantUtils;
import com.pedro.rtplibrary.rtmp.RtmpCamera2;
import com.pedro.rtplibrary.rtmp.RtmpCamera3;
import com.pedro.rtplibrary.view.AutoFitTextureView;

import net.ossrs.rtmp.ConnectCheckerRtmp;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity implements ConnectCheckerRtmp, View.OnClickListener {
    private RtmpCamera3 rtmpCamera3;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView() {
        button = findViewById(R.id.b_start_stop);
        button.setOnClickListener(this);
        rtmpCamera3 = new RtmpCamera3(this);

    }

    private void startConnect() {
        AppOperator.runOnThread(new Runnable() {
            @Override
            public void run() {
                rtmpCamera3.startStream(ConstantUtils.DEAUFUTRTMPURL);
            }
        });
    }

    @Override
    public void onConnectionSuccessRtmp() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Connection success", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    public void onConnectionFailedRtmp(final String reason) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Connection failed. " + reason,
                        Toast.LENGTH_SHORT).show();
                rtmpCamera3.stopStream();
                button.setText(R.string.start_button);
            }
        });
    }

    @Override
    public void onDisconnectRtmp() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAuthErrorRtmp() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Auth error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAuthSuccessRtmp() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Auth success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_start_stop:
                if (!rtmpCamera3.isStreaming()) {
                    if (rtmpCamera3.prepareAudio()) {
                        button.setText(R.string.stop_button);
                        rtmpCamera3.startStream(ConstantUtils.DEAUFUTRTMPURL);
                    } else {
                        Toast.makeText(this, "Error preparing stream, This device cant do it",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    button.setText(R.string.start_button);
                    rtmpCamera3.stopStream();
                }
                break;
        }
    }


}
