package com.example.demo;

import allMovieList.Movie;
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
//import tcpforward.AddRequest;
//import tcpforward.Client;
//import tcpforward.Message;
import util.SocketWrapper;
import DTO.*;

import java.io.IOException;
public class AddMovie {

    private Stage stage;





    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private HomeView prevScene;
    private  HelloApplication main;

    public HelloApplication getMain() {
        return main;
    }

    public void setMain(HelloApplication main) {
        this.main = main;
        main.setAddWindow(this);
    }

    @FXML
    private Label Warning;
    @FXML
    private Label alertLabel;
    public void setWarningText(String s){
        alertLabel.setText(s);
    }

    Production_Company pc;
    Movielist mvlist;

    public Movielist getMvlist() {
        return mvlist;
    }

    public void setMvlist(Movielist mvlist) {
        this.mvlist = mvlist;
    }

    public Production_Company getPc() {
        return main.getMyProductionCompany();
    }

    public void setPc(Production_Company pc) {
        this.pc = pc;
    }

    @FXML
    TextField title,releaseYear,genre1,genre2,genre3,runtime,budget,revenue,trailerLink;




    public void onSaveClick(ActionEvent actionEvent) {



        boolean alright=true;


        try{
            int yr=Integer.parseInt(releaseYear.getText());
            if(yr<0){
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e){
            alright=false;
            alertLabel.setText("Release Year Must be Positive Integer");
        }

        try{
            int rt=Integer.parseInt(runtime.getText());
            if(rt<0){
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e){
            alright=false;
            alertLabel.setText("Running Time Must be Positive Integer");
        }


        try{
            long budg=Long.parseLong(budget.getText());
            long rev=Long.parseLong(revenue.getText());
            if(budg<0 || rev<0){
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e){
            alright=false;
            alertLabel.setText("Budget and Revenue Must be Positive Integer");
        }




        if(alright) {
            String[] data = {title.getText(), releaseYear.getText(), genre1.getText(), genre2.getText(), genre3.getText(), runtime.getText(), pc.getName(), budget.getText(), revenue.getText()};

            AddRequest addRequest = new AddRequest();
            addRequest.setCompanyName(main.getMyProductionCompany().getName());
            addRequest.setData(data);
            if(trailerLink.getText()!="")
            {addRequest.setTrailerLink(trailerLink.getText());}
            else {
                String yt="https://www.youtube.com/results?search_query=";
                yt=yt+title.getText();
                String path=yt+"+trailer";
                addRequest.setTrailerLink(path);
            }

            SocketWrapper sw = main.getNetworkUtil();
            try {
                sw.write(addRequest);
            } catch (IOException e) {
                System.out.println("add message from client sending error :" + e);
            }
            // prevScene.addToListView(prevScene.getListView(),pc.getOwnMovies());
            Stage stage1 = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage1.close();
        }

    }

    public void onCancelClick(ActionEvent actionEvent) {

        Stage stage1=(Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage1.close();

    }

    public void setProd(Production_Company pc1){
        pc=pc1;
    }

    public HomeView getPrevScene() {
        return prevScene;
    }

    public void setPrevScene(HomeView prevScene) {
        this.prevScene = prevScene;
    }
}
