package test.cj.com.myapplication.AsyncTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import test.cj.com.myapplication.R;

/**
 * Created by Administrator on 2019/5/22.
 */

public class AsyncTaskActivity extends AppCompatActivity {
    private ImageView image;
    private ProgressBar proressbar;
    private String url = "http://b.zol-img.com.cn/sjbizhi/images/4/320x510/1366618073898.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynstask);
        image = findViewById(R.id.image);
        proressbar = findViewById(R.id.proressbar);
        new MyAsyncTask().execute(url);
    }

    class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            proressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            Bitmap bitmap = null;
            URLConnection urlConnection;
            InputStream is = null;
            try {
                urlConnection = new URL(url).openConnection();
                is = urlConnection.getInputStream();
                BufferedInputStream bfs = new BufferedInputStream(is);
                Thread.sleep(1000);
                bitmap = BitmapFactory.decodeStream(bfs);
                is.close();
                bfs.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            proressbar.setVisibility(View.GONE);
            image.setImageBitmap(RGB_565(bitmap));
        }
    }

    private Bitmap compress(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        Log.i("wechat", "压缩前图片的大小" + (bitmap.getByteCount() / 1024 / 1024) + "/宽度为" + bitmap.getWidth() + "/高度为" + bitmap.getHeight()
                + "/bytes.length=  " + (baos.toByteArray().length / 1024) + "KB");
        int quality = 100;
        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            quality -= 10;
        }
        byte[] bytes = baos.toByteArray();
        Bitmap b = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        Log.i("wechat", "压缩后图片的大小" + (b.getByteCount() / 1024 / 1024) + "/宽度为" + b.getWidth() + "/高度为" + b.getHeight()
                + "/bytes.length=  " + (bytes.length / 1024) + "KB/quality=" + quality);
        return b;
    }

    private Bitmap inSampleSize(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        Log.i("wechat", "压缩前图片的大小" + (bitmap.getByteCount() / 1024 / 1024) + "/宽度为" + bitmap.getWidth() + "/高度为" + bitmap.getHeight()
                + "/bytes.length=  " + (baos.toByteArray().length / 1024) + "KB");
        byte[] bytes = baos.toByteArray();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap b = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        Log.i("wechat", "压缩后图片的大小" + (b.getByteCount() / 1024 / 1024) + "/宽度为" + b.getWidth() + "/高度为" + b.getHeight()
                + "/bytes.length=  " + (bytes.length / 1024) + "KB");
        return b;
    }

    private Bitmap martix(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        Log.i("wechat", "压缩后图片的大小" + (bm.getByteCount() / 1024 / 1024) + "/宽度为" + bm.getWidth() + "/高度为" + bm.getHeight());
        return bm;
    }

    private Bitmap RGB_565(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        Log.i("wechat", "压缩前图片的大小" + (bitmap.getByteCount() / 1024 / 1024) + "/宽度为" + bitmap.getWidth() + "/高度为" + bitmap.getHeight()
                + "/bytes.length=  " + (bytes.length / 1024) + "KB");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap b = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        Log.i("wechat", "压缩后图片的大小" + (b.getByteCount() / 1024 / 1024) + "/宽度为" + b.getWidth() + "/高度为" + b.getHeight()
                + "/bytes.length=  " + (bytes.length / 1024) + "KB");
        return b;
    }
}
