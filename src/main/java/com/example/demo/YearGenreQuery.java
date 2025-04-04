package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class YearGenreQuery {

    private boolean year=false;
    private boolean genre=false;
    private boolean runningTime=false;

    private HomeView myHome;

    @FXML
    private Label queryLabel;

    @FXML
    private TextField firstTextField;

    @FXML
    private TextField secondTextField;

    @FXML
    private Button cancelQuery,filter;


    public boolean isYear() {
        return year;
    }

    public void setYear(boolean year) {
        this.year = year;
    }

    public boolean isRunningTime() {
        return runningTime;
    }

    public void setRunningTime(boolean runningTime) {
        this.runningTime = runningTime;
    }

    public boolean isGenre() {
        return genre;
    }

    public HomeView getMyHome() {
        return myHome;
    }

    public void setMyHome(HomeView myHome) {
        this.myHome = myHome;
    }




    public void setGenre(boolean genre) {
        this.genre = genre;
    }

    public void init(){
        if(isYear()){
            queryLabel.setText("Filter By Release Year");
            firstTextField.setVisible(true);
            secondTextField.setVisible(false);
            firstTextField.setPromptText("Enter Release Year");

        }
        if(isGenre()){
            queryLabel.setText("Filter By Genre");
            secondTextField.setVisible(false);
            firstTextField.setVisible(true);
            firstTextField.setPromptText("Enter Genre : ");
        }

        if(isRunningTime()){
            queryLabel.setText("Filter By Running Time");
            secondTextField.setVisible(true);
            firstTextField.setVisible(true);
            firstTextField.setPromptText("Enter starting of the Range in minutes");
            secondTextField.setPromptText("Enter Ending of the Range in minutes");
        }

    }


    public void onFilterClick(ActionEvent actionEvent) {
        if(isYear()){
            myHome.searchByYear(firstTextField.getText());
        }
        if(isGenre()){
            myHome.searchByGenre(firstTextField.getText());
        }
        if (isRunningTime()){
            myHome.searchByRunningTime(firstTextField.getText(),secondTextField.getText());
        }

        Stage stage1=(Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage1.close();

    }

    public void onCancelQuery(ActionEvent actionEvent) {
        Stage stage1=(Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage1.close();
    }
}
