package com.example.week9demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText editTextMsg,editTextInterval;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextMsg=findViewById(R.id.editText_message);
        editTextInterval=findViewById(R.id.editText_interval);
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobScheduler jobScheduler=(JobScheduler)getApplicationContext().getSystemService(JOB_SCHEDULER_SERVICE);

                PersistableBundle pb=new PersistableBundle();
                pb.putString("Message",editTextMsg.getText().toString());

                String interval_txt=editTextInterval.getText().toString();
                int interval=Integer.parseInt(interval_txt);

                JobInfo jobInfo=new JobInfo.Builder(0,new ComponentName(getApplicationContext(),MyService.class))
                        .setMinimumLatency(interval *1000)
                        .setExtras(pb).build();
                jobScheduler.schedule(jobInfo);

            }
        });
    }
}