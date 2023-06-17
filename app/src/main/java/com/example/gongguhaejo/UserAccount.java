package com.example.gongguhaejo;

/**
 * 사용자 계정 정보 모델 클래스
 */
public class UserAccount {
    private String idToken;     // Firebase Uid (고유 토큰 정보)
    private String emailId;     // 이메일 아이디
    private String password;    // 비밀번호
    private String name;
    private String nick;
    private String tel;
    private String acc;
    private String Loca;

    public UserAccount() { }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getLoca() {return Loca;}

    public void setLoca(String loca) {Loca = loca;}
}
