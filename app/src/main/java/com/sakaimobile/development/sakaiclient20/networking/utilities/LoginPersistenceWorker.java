package com.sakaimobile.development.sakaiclient20.networking.utilities;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sakaimobile.development.sakaiclient20.R;
import com.sakaimobile.development.sakaiclient20.models.sakai.User.UserResponse;
import com.sakaimobile.development.sakaiclient20.networking.services.SessionService;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginPersistenceWorker extends Worker {

    private static final String LOGIN_TASK_NAME = "Login Persistence";

    private SessionService sessionService;

    // This constructor is used above by the PeriodicWorkRequest builder
    @SuppressWarnings("unused")
    public LoginPersistenceWorker(
            Context context,
            WorkerParameters workerParams) {
        super(context, workerParams);

        // There is (currently) no Dagger injection support for Worker
        // classes, and the class is generated by the Work API
        // using just the worker class name. Shoehorning DI just for this class
        // is needlessly complicated, so here we do it the good 'ol way ourselves.
        this.sessionService = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.BASE_URL))
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new HeaderInterceptor(context.getString(R.string.COOKIE_URL_2)))
                        .build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SessionService.class);
    }

    public static void startLoginPersistenceTask() {
        Constraints workConstraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        // We will make requests roughly every ~15 minutes,
        // max window between requests will be 30 minutes,
        // which should suffice for keeping the cookies alive
        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(
                LoginPersistenceWorker.class,
                15,
                TimeUnit.MINUTES)
                .setConstraints(workConstraints)
                .build();

        WorkManager.getInstance().enqueueUniquePeriodicWork(
                LOGIN_TASK_NAME,
                ExistingPeriodicWorkPolicy.REPLACE,
                workRequest);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Work requests already run in background threads, so we will not
        // get a NetworkOnMainThread exception here
        UserResponse user = this.sessionService.getLoggedInUser().blockingGet();
        // If the display ID (i.e. NetID) is non-null, the cookies are still active
        if(user.userId == null)
            return Result.failure();

        // If cookies are still active, refresh the session (keep it active)
        // This is done by requesting the Sakai home page ("https://learn.iqeraschools.com/portal"),
        // which imitates a real user requesting the site from a browser, forcing Sakai's
        // backend to keep the session alive. This is also how Sakai itself keeps sessions alive.
        ResponseBody html = this.sessionService.refreshSession().blockingGet();
        return html != null ? Result.success() : Result.failure();
    }

}
