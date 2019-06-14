package test.cj.com.myapplication.hanlder;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import test.cj.com.myapplication.R;

/**
 * Created by Administrator on 2019/5/22.
 */

public class HanlderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hanlder);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.obj = "button OnClickListener";
                handler.sendMessage(message);
            }
        });
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Toast.makeText(HanlderActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
    });
}
