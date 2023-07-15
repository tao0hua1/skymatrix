package cn.seiua.skymatrix.client.httpclient;

public interface CallBack<V extends Object> {

    void callBack(V value, String data);

}
