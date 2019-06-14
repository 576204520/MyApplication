package test.cj.com.myapplication.touch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import test.cj.com.myapplication.R;
import test.cj.com.myapplication.activity.TestActivityB;

/**
 * Created by Administrator on 2019/5/13.
 */

public class TouchActivity extends AppCompatActivity {
    private static final String TAG = "TouchActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_a);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TouchActivity.this, TAG, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
