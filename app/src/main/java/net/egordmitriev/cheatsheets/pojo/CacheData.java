package net.egordmitriev.cheatsheets.pojo;

import java.util.Date;

/**
 * Created by EgorDm on 22-Jun-2017.
 */

public class CacheData<T> extends Model {
    public Date expires;
    public T data;

    public CacheData() {
    }

    public CacheData(Date expires, T data) {
        this.expires = expires;
        this.data = data;
    }
}
