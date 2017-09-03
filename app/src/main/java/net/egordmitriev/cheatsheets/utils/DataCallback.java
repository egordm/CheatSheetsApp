package net.egordmitriev.cheatsheets.utils;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by EgorDm on 23-Jun-2017.
 */

public abstract class DataCallback<T> implements Callback<T> {
    public interface SuccessInterceptor<T> {
        void success(T data);
    }

    private Call<T> mCall;
    private SuccessInterceptor<T> mInterceptor;

    public DataCallback() {
        init();
    }

    public void init() {

    }

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        if(response.body() == null) {
            onFailure(call, new NullPointerException("Resource has not been found."));
            return;
        }
        if(mInterceptor != null) mInterceptor.success(response.body());
        onData(response.body());
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        mCall = call.clone();
        onError(new LocalizedException(t));
    }

    public abstract void onData(T data);
    public abstract void onError(Throwable t);

    public boolean retry() {
        if(mCall == null) return false;
        if(mCall.isExecuted() || mCall.isCanceled()) mCall = mCall.clone();
        mCall.enqueue(this);
        return true;
    }

    public void setInterceptor(SuccessInterceptor<T> interceptor) {
        mInterceptor = interceptor;
    }
}
