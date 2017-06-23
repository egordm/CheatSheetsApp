package net.egordmitriev.cheatsheets.utils;

import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static net.egordmitriev.cheatsheets.api.API.sGson;

/**
 * Created by EgorDm on 23-Jun-2017.
 */

public abstract class DataCallback<T> implements Callback<T> {
    private Call<T> mCall;

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        Logger.json(sGson.toJson(response.body()));
        onData(response.body());
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        Logger.e(t, "DataC");
        onError(t);
        mCall = call;
    }

    public abstract void onData(T data);
    public abstract void onError(Throwable t);

    public void retry() {
        if(mCall == null) return;
        mCall.enqueue(this);
    }
}
