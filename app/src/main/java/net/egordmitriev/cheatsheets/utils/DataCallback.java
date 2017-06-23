package net.egordmitriev.cheatsheets.utils;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by EgorDm on 23-Jun-2017.
 */

public abstract class DataCallback<T> implements Callback<T> {
    private Call<T> mCall;

    public DataCallback() {
        init();
    }

    public void init() {

    }

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        //Logger.json(sGson.toJson(response.body()));
        if(response.body() == null) {
            onFailure(call, new NullPointerException("Resource has not been found."));
            return;
        }
        onData(response.body());
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        mCall = call.clone();
        onError(new LocalizedException(t));
        //t.printStackTrace();

    }

    public abstract void onData(T data);
    public abstract void onError(Throwable t);

    public boolean retry() {
        if(mCall == null) return false;
        mCall.enqueue(this);
        return true;
    }
}
