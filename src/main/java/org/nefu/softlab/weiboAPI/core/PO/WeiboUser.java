package org.nefu.softlab.weiboAPI.core.PO;

/**
 * Created by Jiaxu_Zou on 2018-4-6
 */
public class WeiboUser {
    private String uid;

    private String nickname;

    private String gender;

    private String birthday;

    private String headurl;

    private String place;

    private Integer weibonum;

    private Integer connum;

    private Integer fansnum;

    private String marriage;

    private String signature;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl == null ? null : headurl.trim();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public Integer getWeibonum() {
        return weibonum;
    }

    public void setWeibonum(Integer weibonum) {
        this.weibonum = weibonum;
    }

    public Integer getConnum() {
        return connum;
    }

    public void setConnum(Integer connum) {
        this.connum = connum;
    }

    public Integer getFansnum() {
        return fansnum;
    }

    public void setFansnum(Integer fansnum) {
        this.fansnum = fansnum;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage == null ? null : marriage.trim();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }
}