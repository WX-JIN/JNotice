package com.soubw.wxj;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.soubw.bean.JNoticeBean;
import com.soubw.jnotice.JDefaultAdapter;
import com.soubw.jnotice.JNoticeAgent;
import com.soubw.receiver.JHomeKeyReceiver;

public class MainActivity extends AppCompatActivity {

    private static boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isExit = false;
        JNoticeAgent.setIsAutoDismiss(true);
        JNoticeAgent.setAutoDismissTime(5000);
        JNoticeAgent.setIsUseHomeKey(true);
        JNoticeAgent.register(this);
        JNoticeAgent.getJNoticeAgent().setAdapter(new JDefaultAdapter(MainActivity.this, R.layout.jnotice_adpter_item, null));

        handler.sendMessage(new Message());
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!isExit) {
                if (!JHomeKeyReceiver.isUseHomeKey) {
                    JNoticeAgent.addJNotice(new JNoticeBean(0));
                }
                handler.sendMessageDelayed(new Message(), (long) ((Math.random()*3) *1000));
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        JNoticeAgent.onStart(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JNoticeAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isExit = true;
    }
}
