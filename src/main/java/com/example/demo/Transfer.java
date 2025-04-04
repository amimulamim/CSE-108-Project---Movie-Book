package com.example.demo;

import allMovieList.Movielist;
import allMovieList.Production_Company;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
//import tcpforward.Client;
//import tcpforward.TransferRequest;
import util.SocketWrapper;
import DTO.*;

import java.io.IOException;

public class Transfer {
    private Stage stage;

    @FXML
    TextField movieName,transferTo;
    @FXML
    Label alertLabel;


   private Production_Company pc;
    private HelloApplication main;

    public HelloApplication getMain() {
        return main;
    }

    public void setMain(HelloApplication main) {
        this.main = main;
        main.setTransferWindow(this);
    }

    public void onCancelTransfer(ActionEvent actionEvent) {


        Stage stage1=(Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage1.close();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Production_Company getPc() {
        return main.getMyProductionCompany();
    }

    public void setPc(Production_Company pc) {
        this.pc = pc;
    }


    public void setWarningText(String s){
        alertLabel.setText(s);
    }


    public void onSaveTransfer(ActionEvent actionEvent) {
        String fromCompany=main.getMyProductionCompany().getName().toUpperCase();
        String toCompany=transferTo.getText().toUpperCase();
        String mvName=movieName.getText();
        TransferRequest tr=new TransferRequest(fromCompany,toCompany,mvName);
        SocketWrapper socketWrapper=main.getNetworkUtil();
        try {
            socketWrapper.write(tr);
        } catch (Exception e) {
            System.out.println("Transfer request sending failed : "+e);
        }

        Stage stage1=(Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage1.close();

    }
}
