package org.nefu.softlab.weiboAPI.core.PO;

/**
 * Created by Jiaxu_Zou on 2018-4-6
 */
public class Log {
    private String logid;

    private Integer count;

    private String ip;

    private String timestamp;

    private String lastlogin;

    public String getLogid() {
        return logid;
    }

    public void setLogid(String logid) {
        this.logid = logid == null ? null : logid.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp == null ? null : timestamp.trim();
    }

    public String getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(String lastlogin) {
        this.lastlogin = lastlogin == null ? null : lastlogin.trim();
    }
}