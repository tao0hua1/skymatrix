package cn.seiua.skymatrix.client.httpclient;

import okhttp3.Call;

public class Task<V> {
    private CallBack<V> callBack;
    private Call call;

    private Class type;

    public CallBack<V> getCallBack() {
        return callBack;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public void setCallBack(CallBack<V> callBack) {
        this.callBack = callBack;
    }

    public Call getCall() {
        return call;
    }

    public void setCall(Call call) {
        this.call = call;
    }

    public Task(CallBack<V> callBack, Call call, Class type) {
        this.callBack = callBack;
        this.call = call;
        this.type = type;
    }
}
