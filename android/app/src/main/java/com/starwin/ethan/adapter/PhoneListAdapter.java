package com.starwin.ethan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.starwin.ethan.smsservice.R;

import java.util.List;

public class PhoneListAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mPhoneList;

    public PhoneListAdapter(Context context, List<String> messages) {
        mContext = context;
        mPhoneList = messages;
    }

    @Override
    public int getCount() {
        return mPhoneList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPhoneList.get(position);
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
        String phoneStr = mPhoneList.get(position);
        phone.setText(phoneStr);
        return convertView;
    }
}
