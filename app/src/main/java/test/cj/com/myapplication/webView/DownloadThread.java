package test.cj.com.myapplication.webView;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2019/6/5.
 */

public class DownloadThread extends Thread {
    private String mUrl;

    public DownloadThread(String url) {
        this.mUrl = url;
    }

    @Override
    public void run() {
        try {
            Log.e("DownloadThread--->>", "开始");
            URL httpUrl = new URL(mUrl);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            InputStream is = conn.getInputStream();
            FileOutputStream fos = null;
            File sdFile;
            File apkFile;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                sdFile = Environment.getExternalStorageDirectory();
                apkFile = new File(sdFile, "myTest.apk");
                if (apkFile.exists()) {
                    apkFile.delete();
                }
                fos = new FileOutputStream(apkFile);
            }

            byte[] b = new byte[6 * 1024];
            int len;
            while ((len = is.read(b)) != -1) {
                if (fos != null) {
                    fos.write(b, 0, len);
                }
            }
            if (is != null) {
                is.close();
            }
            if (fos != null) {
                fos.close();
            }

            Log.e("DownloadThread--->>", "结束");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
