package com.example.demo;

import DTO.PasswordChange;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Password {

    @FXML
    private TextField companyName,newPassword,confirmPassword;


    @FXML
    private Label warningLabel;

    private HelloApplication main;

    public HelloApplication getMain() {
        return main;
    }

    public void setMain(HelloApplication main) {
        this.main = main;
    }

    public void onCancelRegister(ActionEvent actionEvent) {

        Stage stage1=(Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage1.close();


    }

    public void onSaveChangeResister(ActionEvent actionEvent) {
        String name=companyName.getText();
        String pass1=newPassword.getText();
        String pass2= confirmPassword.getText();

        if(pass1.equals(pass2)){

            PasswordChange ps=new PasswordChange(name.toUpperCase(),pass1);
            try {
                main.getNetworkUtil().write(ps);
            } catch (IOException e) {
                System.out.println("Password change writing error");
            }
            Stage stage1=(Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage1.close();
        }
        else {
            warningLabel.setText("Passwords did not match");
        }




    }
}
