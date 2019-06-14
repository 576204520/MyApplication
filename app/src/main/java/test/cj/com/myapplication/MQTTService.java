package test.cj.com.myapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.greenrobot.eventbus.EventBus;

public class MQTTService extends Service {
    public static final String TAG = MQTTService.class.getSimpleName();

    private static MqttAndroidClient client;
    private MqttConnectOptions conOpt;

    private String host = "tcp://192.168.11.178:61613";
    private String userName = "admin";
    private String passWord = "password";
    public static String myTopic = "topic";
    private String clientId1 = "test1";
//    private String clientId2 = "test2";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        init();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * �����÷���Ϣ
     */
    public static void publish(String msg) {
        Integer qos = 0;
        Boolean retained = false;
        String topic = myTopic;
        try {
            client.publish(topic, msg.getBytes(), qos.intValue(), retained.booleanValue());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        Log.e(TAG, "init");
        // ��������ַ Э��+��ַ+�˿�
        String uri = host;
        client = new MqttAndroidClient(this, uri, clientId1);
        // ����MQTT�������ҽ�����Ϣ
        client.setCallback(mqttCallback);

        conOpt = new MqttConnectOptions();
        // �����Ƿ����session,�����������Ϊfalse��ʾ�������ᱣ���ͻ��˵����Ӽ�¼����������Ϊtrue��ʾÿ�����ӵ������������µ��������
        // ������֮������Ϊfalseʱ���Կͻ��˿��Խ���������Ϣ
        conOpt.setCleanSession(false);
        // ���ó�ʱʱ�䣬��λ����
        conOpt.setConnectionTimeout(10);
        // ���������ͼ������λ����
        conOpt.setKeepAliveInterval(20);
        // �û���
        conOpt.setUserName(userName);
        // ����
        conOpt.setPassword(passWord.toCharArray());

        // last will message
        boolean doConnect = true;
        String message = "{\"terminal_uid\":\"" + clientId1 + "\"}";
        String topic = myTopic;
        Integer qos = 0;
        Boolean retained = false;
        if ((!message.equals("")) || (!topic.equals(""))) {
            try {
                conOpt.setWill(topic, message.getBytes(), qos.intValue(), retained.booleanValue());
            } catch (Exception e) {
                Log.i(TAG, "Exception Occured", e);
                doConnect = false;
                iMqttActionListener.onFailure(null, e);
            }
        }

        if (doConnect) {
            doClientConnection();
        }

    }

    @Override
    public void onDestroy() {
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    /**
     * ����MQTT������
     */
    private void doClientConnection() {
        if (!client.isConnected() && isConnectIsNomarl()) {
            try {
                client.connect(conOpt, null, iMqttActionListener);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

    }

    // MQTT�Ƿ����ӳɹ�
    private IMqttActionListener iMqttActionListener = new IMqttActionListener() {

        @Override
        public void onSuccess(IMqttToken arg0) {
            Log.i(TAG, "onSuccess ");
            try {
                // ����myTopic����
                //mqtt�ͻ��˶�������
                //��mqtt����QoS����ʶ��������
                //QoS=0ʱ��������෢��һ�Σ��п��ܶ�ʧ
                //QoS=1ʱ���������ٷ���һ�Σ��п����ظ�
                //QoS=2ʱ������ֻ����һ�Σ�����ȷ����Ϣֻ����һ�Ρ�
                client.subscribe(myTopic, 1);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(IMqttToken arg0, Throwable arg1) {
            Log.i(TAG, "onFailure ");
            arg1.printStackTrace();
            // ����ʧ�ܣ�����
        }
    };

    // MQTT�������ҽ�����Ϣ
    private MqttCallback mqttCallback = new MqttCallback() {

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            String str1 = new String(message.getPayload());
            MQTTMessage msg = new MQTTMessage();
            msg.setMessage(str1);
            EventBus.getDefault().post(msg);
            String str2 = topic + ";qos:" + message.getQos() + ";retained:" + message.isRetained();
            Log.e(TAG, "messageArrived:" + str1);
            Log.e(TAG, str2);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken arg0) {
            Log.e(TAG, "deliveryComplete");
        }

        @Override
        public void connectionLost(Throwable arg0) {
            Log.e(TAG, "connectionLost");
            // ʧȥ���ӣ�����
        }
    };

    /**
     * �ж������Ƿ�����
     */
    private boolean isConnectIsNomarl() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            String name = info.getTypeName();
            Log.e(TAG, "MQTT ��ǰ�������ƣ�" + name);
            return true;
        } else {
            Log.e(TAG, "MQTT û�п�������");
            return false;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
