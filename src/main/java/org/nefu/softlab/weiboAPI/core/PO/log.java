package org.nefu.softlab.weiboAPI.core.PO;

/**
 * Created by Jiaxu_Zou on 2018-4-6
 */
public class Log {

    private String logid;

    private String uid;

    private Integer count;         // 登录次数

    private String useragent;

    private String ip;                // 客户端IP地址

    private String hostname;    // 客户端主机名

    private String timestamp;   // 时间戳

    private String lastlogin;       // 上一条记录的id

    // getter and setter


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUseragent() {
        return useragent;
    }

    public void setUseragent(String useragent) {
        this.useragent = useragent;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

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