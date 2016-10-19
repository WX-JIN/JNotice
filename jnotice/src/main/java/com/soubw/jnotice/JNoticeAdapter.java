package com.soubw.jnotice;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author：WX_JIN
 * email:wangxiaojin@soubw.com
 */
public abstract class JNoticeAdapter<T> extends ArrayAdapter<T>{

    protected List<T> beans;
    protected List<T> trueBeans;
    protected JNotice.OnJNoticeListener listener;
    protected T unReadBean = null;
    protected Context context;

    Handler handler = new Handler(Looper.getMainLooper());


    public JNoticeAdapter(Context cx, int textViewResourceId, List<T> bs) {
        super(cx, textViewResourceId);
        context = cx;
        trueBeans = new ArrayList<>();
        if (bs == null){
            beans = new ArrayList<>();
        }else {
            beans = bs;
        }
    }

    public void setOnJNoticeListener(JNotice.OnJNoticeListener l){
        this.listener = l;
    }

    @Override
    public int getCount() {
        return beans == null ? 0 : beans.size();
    }

    @Override
    public T getItem(int position) {
        return beans == null ? null : beans.get(position);
    }

    public void addList(List<T> bs){
        if (beans == null) {
            beans = bs;
        }else {
            beans.addAll(bs);
        }
        notifyDataSetChanged();
    }

    public void refreshList(List<T> bs){
        beans = bs;
        notifyDataSetChanged();
    }

    public void addItem(final T b){
        trueBeans.add(0,b);
        refreshItem();
        if (JNoticeConfig.IS_AUTO_DISMISS){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    trueBeans.remove(b);
                    refreshItem();
                    if (beans.size() < 1){
                        if (listener !=null){
                            listener.onDismissDingToast();
                        }
                    }
                }
            },JNoticeConfig.AUTO_DISMISS_TIME);
        }
        notifyDataSetChanged();
    }

    public void addUnReadBean(final T b){
        unReadBean = b;
        refreshItem();
        if (JNoticeConfig.IS_AUTO_DISMISS){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    beans.remove(b);
                    unReadBean = null;
                    refreshItem();
                    if (beans.size() < 1){
                        if (listener !=null){
                            listener.onDismissDingToast();
                        }
                    }
                }
            },JNoticeConfig.AUTO_DISMISS_TIME);
        }
        notifyDataSetChanged();
    }


    public void refreshItem(){
        beans.clear();
        if (unReadBean !=null){
            beans.add(0,unReadBean);
        }
        if (trueBeans.size() > 3){
            beans.add(trueBeans.get(0));
        }else{
            beans.addAll(trueBeans);
        }
        notifyDataSetChanged();
        
    }

    public void removeItem(int pos){
        beans.remove(pos);
        notifyDataSetChanged();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final T bean = getItem(position);
        return getView(position,convertView,parent, bean);
    }

    protected abstract View getView(int position, View convertView, ViewGroup parent,T bean);

    protected void clickPos(int position,boolean isMove){
        if (listener == null)
            return;
        if (isMove){
            listener.onItemClick(position);
        }
        if (beans.size() == 1){
            beans.clear();
            listener.onDismissDingToast();
        }else{
            if (unReadBean != null && position == 0){
                unReadBean = null;
            }
            removeItem(position);
        }
    }

}

