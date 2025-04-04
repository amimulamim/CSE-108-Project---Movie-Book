package DTO;

import java.io.Serializable;

public class PasswordChange implements Serializable {

    private String companyName;
    private String password;


    public PasswordChange(String companyName,String Pass) {
        this.companyName = companyName;
        this.password=Pass;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
