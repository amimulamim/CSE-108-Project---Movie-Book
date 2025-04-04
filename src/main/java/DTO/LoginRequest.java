package DTO;

import java.io.Serializable;

public class LoginRequest implements Serializable {


    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public LoginRequest(String userName,String password) {
        this.password = password;
        this.userName=userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString(){
        return  "Requesting : Name: "+getUserName()+"Password : "+getPassword();
    }
}
