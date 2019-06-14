package test.cj.com.myapplication;


import android.os.Handler;

/**
 * Created by Administrator on 2019/3/6.
 */

public class CustomCountDownTimer implements Runnable {

    private int time;
    private int countDownTime;
    private final ICountDownHandler countDownHandler;
    private final Handler handler;
    private boolean isRun;

    public CustomCountDownTimer(int time, ICountDownHandler countDownHandler) {
        handler = new Handler();
        this.time = time;
        this.countDownHandler = countDownHandler;
        countDownTime = time;
    }

    @Override
    public void run() {
        if (isRun) {
            if (countDownHandler != null) {
                countDownHandler.onTicker(countDownTime);
            }
            if (countDownTime == 0) {
                cancel();
                if (countDownHandler != null) {
                    countDownHandler.onFinish();
                }
            } else {
                countDownTime = time--;
                handler.postDelayed(this, 1000);
            }
        }
    }

    public void start() {
        isRun = true;
        handler.post(this);
    }

    public void cancel() {
        isRun = false;
        handler.removeCallbacks(this);
    }

    public interface ICountDownHandler {
        void onTicker(int itme);

        void onFinish();
    }
}
