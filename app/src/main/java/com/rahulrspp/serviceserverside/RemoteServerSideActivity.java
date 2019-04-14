    package com.rahulrspp.serviceserverside;

    import android.content.Intent;
    import android.os.Bundle;
    import android.support.v7.app.AppCompatActivity;
    import android.view.View;
    import android.widget.Button;
    import android.widget.TextView;

    public class RemoteServerSideActivity extends AppCompatActivity {

        TextView tvNo;
        Button btnStartService, btnStopService;
        private Intent intent;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_remote_server_side);
            setViewId();
            setListener();

        }

        private void setViewId() {
            btnStartService=findViewById(R.id.btnStartService);
            btnStopService=findViewById(R.id.btnStopService);

            intent =new Intent(RemoteServerSideActivity.this, MyRemoteServerSideService.class);

        }

        private void setListener() {
            btnStartService.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    startService(intent);
                }
            });

            btnStopService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stopService(intent);
                }
            });
        }

    }
