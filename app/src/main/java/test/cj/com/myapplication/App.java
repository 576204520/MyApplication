package test.cj.com.myapplication;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * Created by Administrator on 2017/8/25.
 */
public class App extends Application implements Thread.UncaughtExceptionHandler {
    @Override
    public void onCreate() {
        super.onCreate();
        //异常
        Thread.setDefaultUncaughtExceptionHandler(this);

    }


    @Override
    public void uncaughtException(Thread t, Throwable ex) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time = df.format(new Date());
        sb.append("Hoast time is  ");
        sb.append(time + " //t");
        sb.append("Version code is  ");
        sb.append(Build.VERSION.SDK_INT + "//t");
        sb.append("Model is  ");
        sb.append(Build.MODEL + "//t");
        sb.append(ex.toString() + "//t");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        sb.append(sw.toString());

        Log.e("TAG",sb.toString());
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "com.test" + File.separator + "log.log");

        OutputStream os = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            os = new FileOutputStream(file);
            byte[] bytes = sb.toString().getBytes();
            os.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
