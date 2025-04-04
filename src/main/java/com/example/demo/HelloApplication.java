package com.example.demo;

import allMovieList.Movie;
import allMovieList.Production_Company;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import tcpdiff.FileOperations;
//import tcpdiff.ReadThread;
import tcpforward.*;
import util.SocketWrapper;
import DTO.*;
import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {


    private Stage stage;
    private SocketWrapper networkUtil;

    public Stage getStage() {
        return stage;
    }

    public SocketWrapper getNetworkUtil() {
        return networkUtil;
    }


    private Production_Company myProductionCompany;
    private HomeView myHomePage;
    private HelloController myHelloController;

    private String clientName;
    private ReadThreadClient rtc;


    public HelloController getMyHelloController() {
        return myHelloController;
    }

    public void setMyHelloController(HelloController myHelloController) {
        this.myHelloController = myHelloController;
    }

    public HomeView getMyHomePage() {
        return myHomePage;
    }

    public void setMyHomePage(HomeView myHomePage) {
        this.myHomePage = myHomePage;
    }

    private void connectToServer() throws IOException {
        String serverAddress = "127.0.0.1";
        int serverPort = 33333;
        networkUtil = new SocketWrapper(serverAddress, serverPort);
       // new ReadThread(this);
        rtc=new ReadThreadClient(this);
    }


    @Override
    public void start(Stage Primarystage) {
        try {
            connectToServer();
        }catch (IOException e){
            System.out.println("Server Connection denied");
        }
        stage=Primarystage;
        showLogInPage(stage);


    }
    public void showLogInPage(Stage stage2){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        stage=stage2;
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            HelloController helloController=fxmlLoader.getController();
            helloController.setMain(this);
            this.setMyHelloController(helloController);
            stage.setTitle("MyIMDB");
            stage.setScene(scene);
            stage.setWidth(600);
            stage.setHeight(400);
            stage.show();

        } catch (IOException e) {
            System.out.println("loading error");
        }




    }
    public void showHomePage(Production_Company pc){

        System.out.println("Gonna show homepage of "+pc.getName());
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Home-view.fxml"));

        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
            HomeView ct = fxmlLoader.getController();
            ct.setMain(this);
            this.setMyHomePage(ct);
            ct.setPc(pc);
            ct.init();

            stage.setScene(scene);
            stage.setWidth(900);
            stage.setHeight(600);
            stage.setTitle(pc.getName().toUpperCase());
            stage.show();
        } catch (IOException e) {
            System.out.println("home page scene loading error "+e);
        }



    }
    public void refreshHome(Production_Company pc){
        //this.setMyProductionCompany(pc);
        myHomePage.setTransferStatus("");
        this.myProductionCompany=pc;
        myHomePage.refreshHomePage(pc,"");

    }

    public void  updateOurMoviesList(List<Movie> movieList2){
        this.myProductionCompany.setProduced_movies(movieList2);
        myHomePage.refreshHomePage(this.myProductionCompany,"");
    }

    public void refreshHome(UpdateProductionCompanyDetails upd){

        this.myProductionCompany.setMovieCount(upd.getMovieCount());
        this.myProductionCompany.setProfit(upd.getProfit());
        this.myProductionCompany.setProduced_movies(upd.getMyAllMovies());
        this.myProductionCompany.setLatestList(upd.getMyMostRecent());
        this.myProductionCompany.setMax_revenueList(upd.getMyMaxRevenue());
        this.myProductionCompany.setLatestyear(upd.getLatestYear());
        this.myProductionCompany.setMaxProfit(upd.getMaxProfit());
        this.myProductionCompany.setMaxRevenue(upd.getMaxRevenue());
        System.out.println(this.myProductionCompany.getOwnMovies().size());
        myHomePage.refreshHomePage(this.myProductionCompany,"");

    }


    Transfer transferWindow;

    public Transfer getTransferWindow() {
        return transferWindow;
    }

    public void setTransferWindow(Transfer transferWindow) {
        this.transferWindow = transferWindow;
    }

    AddMovie addWindow;

    public AddMovie getAddWindow() {
        return addWindow;
    }

    public void setAddWindow(AddMovie addWindow) {
        this.addWindow = addWindow;
    }

    public void dealModifyWarning(ModifyStatusWarning mdw){

        if(mdw.isTransfer()){

            myHomePage.refreshHomePage(getMyProductionCompany(),mdw.getMessage());
        }

    }
    public Production_Company getMyProductionCompany() {
        return myProductionCompany;
    }

    public void setMyProductionCompany(Production_Company myProductionCompany) {
        this.myProductionCompany = myProductionCompany;
    }

    public void onRegisterClick(ActionEvent actionEvent) {


    }

    public void setLoginStatusTextofHelloController(String s){
        myHelloController.setLoginText(s);
    }

    //public void on

    public static void main(String[] args) {
        // This will launch the JavaFX application
        launch(args);
    }


}