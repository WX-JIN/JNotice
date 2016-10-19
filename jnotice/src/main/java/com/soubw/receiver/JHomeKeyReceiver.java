package com.soubw.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * author：WX_JIN
 * email:wangxiaojin@soubw.com
 */
public class JHomeKeyReceiver extends BroadcastReceiver {

    private String SYSTEM_REASON = "reason";
    private String SYSTEM_HOME_KEY = "homekey";
    private String SYSTEM_HOME_KEY_LONG = "recentapps";

    public static boolean isUseHomeKey= false;


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
            String reason = intent.getStringExtra(SYSTEM_REASON);
            if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {//表示按了home键,程序到了后台
                isUseHomeKey= true;
            } else if (TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG)) {//表示长按home键,显示最近使用的程序列表
            }
        }
    }




}
