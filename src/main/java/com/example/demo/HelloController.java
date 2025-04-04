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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
//import tcpforward.Client;
//import tcpforward.LoginApproval;
//import tcpforward.LoginRequest;
import util.SocketWrapper;
import DTO.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

public class HelloController {
    Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    HelloApplication main;

    public HelloApplication getMain() {
        return main;
    }

    public void setMain(HelloApplication main) {
        this.main = main;
    }

    HomeView myAssignedHomeView;

    public HomeView getMyAssignedHomeView() {
        return myAssignedHomeView;
    }

    public void setMyAssignedHomeView(HomeView myAssignedHomeView) {
        this.myAssignedHomeView = myAssignedHomeView;
    }

    @FXML
    Button resetButton,loginButton;
    @FXML
    PasswordField passField;
    @FXML
    TextField UsernameField;

    @FXML
    private Label welcomeText;
    @FXML
    private Label LoginStatus;

    String username,password;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    private void onLoginButtonClick(ActionEvent actionEvent) throws IOException {

        username=UsernameField.getText();
        System.out.println("Welcome "+username);
        password=passField.getText();
        System.out.println("Password= "+password);


        LoginRequest lr=new LoginRequest(username,password);
        System.out.println("Sending Login req from client: "+lr);


        SocketWrapper myConnection=main.getNetworkUtil();
        // myConnection.write(username);
        myConnection.write(lr);


    }

    public void onResetButtonClick(ActionEvent actionEvent) {
            UsernameField.setText(null);
            passField.setText(null);
    }

    public void onRegisterClick(ActionEvent actionEvent) {


    }

    public void setLoginText(String s){
        LoginStatus.setText(s);
    }

}