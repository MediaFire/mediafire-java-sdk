package com.mediafire.sdk.config;

public interface MFStore<T> {
    public boolean available();
    public T get();
    public void put(T t);
    public void clear();
}
