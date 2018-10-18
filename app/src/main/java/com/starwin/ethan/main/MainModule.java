package com.starwin.ethan.main;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.starwin.ethan.executor.AppExecutors;
import com.starwin.ethan.room.SmsDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @Singleton
    @Provides
    MainPresenter provideMainPresenter() {
        return new MainPresenter();
    }

    @Provides
    @Singleton
    SmsDatabase provideSmsDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), SmsDatabase.class, "sms.db").build();
    }

    @Provides
    @Singleton
    AppExecutors provideAppExecutors() {
        return new AppExecutors();
    }
}
