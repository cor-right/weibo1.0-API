package org.nefu.softlab.weiboAPI.core.po;

import java.io.Serializable;

public class DailyRecord implements Serializable{

    private String rid;

    private String socket;

    private Integer recordnumber;

    private Double recordsize;

    private String date;

    private String savetimestamp;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public Integer getRecordnumber() {
        return recordnumber;
    }

    public void setRecordnumber(Integer recordnumber) {
        this.recordnumber = recordnumber;
    }

    public Double getRecordsize() {
        return recordsize;
    }

    public void setRecordsize(Double recordsize) {
        this.recordsize = recordsize;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSavetimestamp() {
        return savetimestamp;
    }

    public void setSavetimestamp(String savetimestamp) {
        this.savetimestamp = savetimestamp;
    }
}