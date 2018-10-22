package com.starwin.ethan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.starwin.ethan.room.SmsMessage;
import com.starwin.ethan.smsservice.R;

import java.util.List;

public class SmsListAdapter extends BaseAdapter {

    private Context mContext;
    private List<SmsMessage> mMessages;

    public SmsListAdapter(Context context, List<SmsMessage> messages) {
        mContext = context;
        mMessages = messages;
    }

    @Override
    public int getCount() {
        return mMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_sms_item, parent, false);
        }

        TextView phone = convertView.findViewById(R.id.phone);
        TextView content = convertView.findViewById(R.id.content);
        TextView date = convertView.findViewById(R.id.date);
        SmsMessage message = mMessages.get(position);
        phone.setText(message.getPhone());
        content.setText(message.getContent());
        date.setText(message.getDate());

        return convertView;
    }
}
