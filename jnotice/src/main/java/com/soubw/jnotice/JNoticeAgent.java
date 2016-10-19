package com.soubw.jnotice;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.soubw.bean.JNoticeBean;
import com.soubw.receiver.JHomeKeyReceiver;


/**
 * author：WX_JIN
 * email:wangxiaojin@soubw.com
 */
public class JNoticeAgent {

    private final static JNoticeAgent jNoticeAgent = new JNoticeAgent();
    private final static IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);

    public static JNoticeAgent getJNoticeAgent() {
        return jNoticeAgent;
    }

    private static JNotice jNotice;

    private static JHomeKeyReceiver jHomeKeyReceiver = null;

    private JNoticeAgent() {
    }

    public static void setIsUseHomeKey(boolean isUseHomeKey) {
        JNoticeConfig.IS_USE_HOME_KEY = isUseHomeKey;
        registerReceiver();
    }

    public static void setIsAutoDismiss(boolean isAutoDismiss) {
        JNoticeConfig.IS_AUTO_DISMISS = isAutoDismiss;
    }

    public static void setAutoDismissTime(int autoDismissTime) {
        JNoticeConfig.AUTO_DISMISS_TIME = autoDismissTime;
    }

    public static void register(Context cx) {
        if (jNotice == null) {
            synchronized (JNoticeAgent.class) {
                if (jNotice == null) {
                    jNotice = new JNotice(cx.getApplicationContext());
                }
            }
        }
    }

    public static void registerReceiver() {
        if (jHomeKeyReceiver == null) {
            synchronized (JNoticeAgent.class) {
                if (jHomeKeyReceiver == null) {
                    jHomeKeyReceiver = new JHomeKeyReceiver();
                }
            }
        }
    }

    public void setAdapter(JNoticeAdapter a){
        if (jNotice == null)
            return;
        jNotice.setAdapter(a);
    }

    public static void onStart(Context cx) {
        try {
            if (JNoticeConfig.IS_USE_HOME_KEY && jHomeKeyReceiver != null) {
                cx.registerReceiver(jHomeKeyReceiver, homeFilter);
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        if (jNotice == null)
            register(cx);
        if (jNotice.isOpen())
            return;
        jNotice.onStart();
        if (JHomeKeyReceiver.isUseHomeKey){
            //addJNotice(new JNoticeBean(10));
        }
        JHomeKeyReceiver.isUseHomeKey = false;

    }

    public static void onPause(Context cx) {
        try {
            if (JNoticeConfig.IS_USE_HOME_KEY && null != jHomeKeyReceiver) {
                cx.unregisterReceiver(jHomeKeyReceiver);
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        if (jNotice == null)
            register(cx);
        jNotice.onPause();
    }

    public static void addJNotice(JNoticeBean bean) {
        if (jNotice == null)
            return;
        jNotice.addItem(bean);
    }

    public void showUnReadBean(JNoticeBean bean) {
        if (jNotice == null)
            return;
        jNotice.addUnReadBean(bean);
    }

}
