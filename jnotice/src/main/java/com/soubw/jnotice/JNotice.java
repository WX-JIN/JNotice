package com.soubw.jnotice;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * author：WX_JIN
 * email:wangxiaojin@soubw.com
 */
public class JNotice<T,A extends JNoticeAdapter> implements View.OnTouchListener {

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private Context context;
    private LinearLayout layoutContent;
    private ListView listView;
    private JNoticeAdapter adapter;
    private boolean isOpen = false;

    public JNotice(Context cx) {
        context = cx;
        initView();
    }

    private void initView() {
        windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        layoutContent = (LinearLayout) View.inflate(context, R.layout.jnotice, null);
        listView = (ListView) layoutContent.findViewById(R.id.listView);
        int w = WindowManager.LayoutParams.MATCH_PARENT;
        int h = WindowManager.LayoutParams.WRAP_CONTENT;
        int flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        int type;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            type = WindowManager.LayoutParams.TYPE_TOAST;
        } else {
            type = WindowManager.LayoutParams.TYPE_PHONE;
        }
       layoutParams = new WindowManager.LayoutParams(w, h, type, flags, PixelFormat.TRANSLUCENT);
        layoutParams.gravity = Gravity.TOP;
    }


    public void setAdapter(A a){
        adapter = a;
        listView.setAdapter(a);
        adapter.setOnJNoticeListener(new OnJNoticeListener() {
            @Override
            public void onItemClick(int position) {
            }

            @Override
            public void onDismissDingToast() {
                removeViewAndClear();
            }
        });
    }


    public void addItem(T t){
        if (adapter == null)
            return;
        adapter.addItem(t);
        if (isOpen)
            return;
        show();
    }

    public void addUnReadBean(T t){
        if (adapter == null)
            return;
        adapter.addUnReadBean(t);
        if (isOpen)
            return;
        show();
    }

    public  void show() {
        isOpen = true;
        windowManager.addView(layoutContent, layoutParams);
    }

    private void removeViewAndClear() {
        if (!isOpen) return;
        if (windowManager != null && layoutContent != null) {
            windowManager.removeView(layoutContent);
            isOpen = false;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    public void onPause(){
        removeViewAndClear();
    }

    public void onStart(){
        show();
    }

    public interface OnJNoticeListener {
        void onItemClick(int position);
        void onDismissDingToast();
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
