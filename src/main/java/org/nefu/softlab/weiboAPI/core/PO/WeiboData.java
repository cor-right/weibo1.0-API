package org.nefu.softlab.weiboAPI.core.PO;

import java.io.Serializable;

/**
 * Created by Jiaxu_Zou on 2018-4-6
 */
public class WeiboData implements Serializable {
    private String wid;

    private Integer attu;

    private Integer comment;

    private String device;

    private Long time;

    private String rewid;

    private String uid;

    private String content;

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid == null ? null : wid.trim();
    }

    public Integer getAttu() {
        return attu;
    }

    public void setAttu(Integer attu) {
        this.attu = attu;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device == null ? null : device.trim();
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getRewid() {
        return rewid;
    }

    public void setRewid(String rewid) {
        this.rewid = rewid == null ? null : rewid.trim();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}