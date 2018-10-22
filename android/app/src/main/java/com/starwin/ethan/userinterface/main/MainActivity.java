package com.starwin.ethan.userinterface.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.starwin.ethan.adapter.SmsListAdapter;
import com.starwin.ethan.executor.AppExecutors;
import com.starwin.ethan.mvp_dagger.DaggerMvpActivity;
import com.starwin.ethan.room.SmsDatabase;
import com.starwin.ethan.room.SmsMessage;
import com.starwin.ethan.smsservice.R;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainActivity extends DaggerMvpActivity<MainComponent, MainActivity> implements MainContract.View {

    @Inject
    MainPresenter mMainPresenter;

    @Inject
    AppExecutors mAppExecutors;

    @Inject
    SmsDatabase mSmsDatabase;

    private List<SmsMessage> mMessageList;

    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainPresenter.init(mSmsDatabase, mAppExecutors);
        mListView = findViewById(R.id.list_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainPresenter.takeView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMainPresenter.dropView();
    }

    @Override
    protected void onDestroy() {
        mMainPresenter.destroy();
        super.onDestroy();
    }

    @Override
    protected MainComponent initDaggerComponent() {
        return DaggerMainComponent.builder().application(this.getApplication()).build();
    }

    @Override
    protected MainActivity getComponentInject() {
        return this;
    }


    @Override
    public void notifySmsList(List<SmsMessage> messages) {
        if (mMessageList == null) {
            mMessageList = messages;
            mListView.setAdapter(new SmsListAdapter(this, mMessageList));
        } else {
            mMessageList.clear();
            mMessageList.addAll(messages);
        }
        ((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void showSelfPhoneDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_input_panel, null);
        final EditText editText = view.findViewById(R.id.edit_text);
        AlertDialog dialog = builder.setTitle("设置本机号码")
                .setView(view)
                .create();
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String phone = editText.getText().toString();
                mMainPresenter.saveSelfPhone(phone, MainActivity.this);
            }
        });
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mMainPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.go2setting) {
            mMainPresenter.go2Setting();
            return true;
        }
        if (item.getItemId() == R.id.set_self_phone) {
            mMainPresenter.setSelfPhone();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
