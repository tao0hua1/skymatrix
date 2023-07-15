package cn.seiua.skymatrix.client.entity;

import java.io.Serializable;

public class BanInfoEntity implements Serializable {

    private boolean success;

    private RecordEntity record;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public RecordEntity getRecord() {
        return record;
    }

    public void setRecord(RecordEntity record) {
        this.record = record;
    }
}
