# AndroidPCMtoAAC_RTMP_RTSP
Android将麦克风采集的数据推送到服务器（RTMPorRTSP） 采用AudioRecoder收集音频数据MediaCodeC编码AAC，推送到服务器
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
