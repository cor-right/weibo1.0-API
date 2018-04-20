package org.nefu.softlab.weiboAPI.core.vo;

/**
 * Created by Jiaxu_Zou on 2018-4-17
 * 微博检索页面检索时交互的VO
 */
public class WeiboRecordsVo {

    private String uid;

    private String nickname;

    private String headurl;

    private String wid;

    private Long time;

    private String content;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "WeiboRecordsVo{" +
                "uid='" + uid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", headurl='" + headurl + '\'' +
                ", wid='" + wid + '\'' +
                ", time=" + time +
                ", content='" + content + '\'' +
                '}';
    }
}
