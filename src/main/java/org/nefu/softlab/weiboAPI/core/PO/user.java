package org.nefu.softlab.weiboAPI.core.PO;

/**
 * Created by Jiaxu_Zou on 2018-4-6
 */
public class User {
    private String uid;

    private String username;

    private String passwd;

    private String token;

    private String lastlogin;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(String lastlogin) {
        this.lastlogin = lastlogin == null ? null : lastlogin.trim();
    }
}