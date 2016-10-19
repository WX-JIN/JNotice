package com.soubw.jnotice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubw.bean.JNoticeBean;

import java.util.List;

/**
 * author：WX_JIN
 * email:wangxiaojin@soubw.com
 */
public class JDefaultAdapter extends JNoticeAdapter<JNoticeBean> {

    private LayoutInflater mInflater;

    public JDefaultAdapter(Context cx, int textViewResourceId, List<JNoticeBean> bs) {
        super(cx, textViewResourceId ,bs);
        mInflater = LayoutInflater.from(cx);
    }

    @Override
    protected View getView(final int position, View convertView, ViewGroup parent, final JNoticeBean bean) {
        HolderView holder;
        if (convertView != null) {
            holder = (HolderView) convertView.getTag();
        } else {
            holder = new HolderView();
            convertView = mInflater.inflate(R.layout.jnotice_adpter_item, null);
            holder.layoutContent = convertView.findViewById(R.id.layoutContent);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
            holder.ivClose = (ImageView) convertView.findViewById(R.id.ivClose);
            convertView.setTag(holder);
        }
        holder.layoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.getCount() != 0 && trueBeans.size() > 3){

                }else{

                }
                clickPos(position,true);
            }
        });
        holder.tvContent.setVisibility(View.GONE);
        if (bean.getCount() != 0){
            holder.tvTitle.setText("您有"+bean.getCount()+"条事件未处理，点击过去处理吧");
        }else if (trueBeans.size() > 3){
            holder.tvTitle.setText("您当前收到"+trueBeans.size()+"条通知，快去查看吧");
        }else{
            holder.tvTitle.setText("收到一条通知消息");
            holder.tvContent.setVisibility(View.VISIBLE);
            holder.tvContent.setText(bean.getName()+"("+bean.getText()+")"+bean.getTime());
        }

        holder.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPos(position,false);
            }
        });
        return convertView;
    }

    class HolderView {
        View layoutContent;
        TextView tvTitle;
        TextView tvContent;
        ImageView ivClose;

    }

}

