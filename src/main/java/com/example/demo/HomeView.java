package com.example.demo;

import allMovieList.Movie;
import allMovieList.Movielist;
import allMovieList.Production_Company;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.SocketWrapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeView {
    private HelloApplication main;
    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private Production_Company pc;
    private Movielist movielist;

    public Movielist getMovielist() {
        return movielist;
    }

    public void setMovielist(Movielist movielist) {
        this.movielist = movielist;
    }

    private HelloController meInWhichHelloController;

    @FXML
    private Label totalMovies;
    @FXML
    private Label CompanyName;
    @FXML
    private ListView listView;

    @FXML
    //private ListView movieList;
    private List <Movie> searchedList;
    @FXML
    private Button backTo;
    @FXML
    private Button logOutButton;
    @FXML
    private Label profitShow;

    @FXML
    private Label notFound;
    @FXML
    private CheckBox profitBox;

    @FXML
    private ImageView imageView;

    @FXML
    private Label transferStatus,warningMessage;

    private SocketWrapper myConnectionWithServer;


    public void setWarningMessage(String warningMessages) {
        this.warningMessage.setText(warningMessages);
        warningMessage.setVisible(true);

    }

    public HomeView() {
    }

    public HelloApplication getMain() {
        return main;
    }

    public void setMain(HelloApplication main) {
        this.main = main;
        //main.setMyHomePage(this);
    }

    public Production_Company getPc() {
        return main.getMyProductionCompany();
    }


    public void setTransferStatus(String s) {

        this.transferStatus.setText(s);
        transferStatus.setVisible(true);


    }

    public HelloController getMeInWhichHelloController() {
        return meInWhichHelloController;
    }

    public void setMeInWhichHelloController(HelloController meInWhichHelloController) {
        this.meInWhichHelloController = meInWhichHelloController;
    }

    public SocketWrapper getMyConnectionWithServer() {
        return myConnectionWithServer;
    }

    public void setMyConnectionWithServer(SocketWrapper myConnectionWithServer) {
        this.myConnectionWithServer = myConnectionWithServer;
    }

    public List<Movie> getSearchedList() {
        return searchedList;
    }

    public void setSearchedList(List<Movie> searchedList) {
        this.searchedList = searchedList;
    }

    public ListView getListView() {
        return listView;
    }



    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public void setPc(Production_Company pc) {
        this.pc = pc;
        CompanyName.setText(pc.getName());
        totalMovies.setText("Total Movies: "+pc.getMovieCount());
       // List<Movie> ml=pc.getOwnMovies();
        searchedList=pc.getOwnMovies();
        addToListView(listView,searchedList);

    }

    public void refreshHomePage(Production_Company pc1,String s){
        this.pc=pc1;
       // main.setMyProductionCompany(pc);
        CompanyName.setText(pc1.getName());
        totalMovies.setText("Total Movies: "+pc1.getMovieCount());
        // List<Movie> ml=pc.getOwnMovies();
        searchedList=pc1.getOwnMovies();
        System.out.println("got list size :"+searchedList.size());
        addToListView(listView,searchedList);
        transferStatus.setText(s);

        main.showHomePage(pc1);

    }

    public Label getCompanyName() {
        return CompanyName;
    }

    public void addToListView(ListView listView,List<Movie> ml){

        List<Button> toShow=new ArrayList<>();
        List<Movie> check=main.getMyProductionCompany().getOwnMovies();
        System.out.println("List to be displayed of size : "+ml.size());
        System.out.println("Total size= "+check.size());
        //List<Button> buttonList=new ArrayList<>();
        for(Movie m:ml) {
            if (check.contains(m)) {
                Button b = new Button();
                b.setText(m.getTitle().toUpperCase());
                b.setPrefWidth(500);
                b.setPrefHeight(15);
                b.setTextFill(Color.DARKMAGENTA);
                b.setOnAction(this::onMovieSee);

                toShow.add(b);
            }
        }
        //listView= (ListView) toShow;*/
        if(toShow.size()>0)
        {listView.getItems().clear();
        System.out.println("button adding size= "+toShow.size());
        notFound.setVisible(false);
            listView.getItems().addAll(toShow);
        }
        else if(toShow.size()==0){
            listView.getItems().clear();
            notFound.setVisible(true);
        }

      //  for(Button str:toShow){
        //    listView.getItems().add(str);
        //}


   //    listView.autosize();



    }

    public void setCompanyName(String Name) {
        CompanyName.setText(Name);
    }

    public Label getTotalMovies() {
        return totalMovies;
    }

    public void setTotalMovies(int movieCount) {

        this.totalMovies.setText("Number of Movies : "+movieCount);
    }


    public void init() {
        List<String> toShow=new ArrayList<>();
        listView.getItems().addAll(toShow);
        warningMessage.setVisible(false);
        try {
            System.out.println("Here");
            File file=new File("src/main/resources/images/defaultProducer.jpg");
            if(file.exists() && !file.isDirectory()){
                System.out.println("file exists");
            }
            else {
                System.out.println("doesnt exist");
            }


            Image img = new Image(file.toURI().toString());
            imageView.setImage(img);
        }
        catch (Exception e){
            System.out.println("company image loading error");
        }


    }

    public void searchMostRecent(ActionEvent actionEvent) {
       List<Movie> ml=main.getMyProductionCompany().showMostRecent();

       addToListView(listView,ml);
        backTo.setVisible(true);
        warningMessage.setVisible(false);
    }

    public void searchByYear(String yearS){
        int y=Integer.parseInt(yearS);
        List<Movie> ml=new ArrayList<>();

        for(Movie m:main.getMyProductionCompany().getOwnMovies()){
            if(m.getYear()==y){
                ml.add(m);
            }
        }
        addToListView(listView,ml);
        backTo.setVisible(true);
        warningMessage.setVisible(false);

    }

    public void searchByGenre(String genre){

        List<Movie> ml=new ArrayList<>();

        for(Movie m:main.getMyProductionCompany().getOwnMovies()){
            String[] included=m.getGenre();
            if((included[0].equalsIgnoreCase(genre))|| (included[1].equalsIgnoreCase(genre)) || (included[2].equalsIgnoreCase(genre))){
                ml.add(m);
            }
        }
        addToListView(listView,ml);
        backTo.setVisible(true);
        warningMessage.setVisible(false);

    }

    public void searchByRunningTime(String start,String ending){
        int x=Integer.parseInt(start);
        int y=Integer.parseInt(ending);
        List<Movie> ml=new ArrayList<>();

        for(Movie m:main.getMyProductionCompany().getOwnMovies()){
            if(m.getRuntime()>=x && m.getRuntime()<=y){
                ml.add(m);
            }
        }
        addToListView(listView,ml);
        backTo.setVisible(true);
        warningMessage.setVisible(false);



    }



    public void searchMaximumRevenue(ActionEvent actionEvent) {
        List<Movie> ml=main.getMyProductionCompany().showMaxRevenue();
        addToListView(listView,ml);
        backTo.setVisible(true);
    }


    public void onProfitShow(ActionEvent actionEvent) {
        boolean flag=profitBox.isSelected();
        profitShow.setText("Total Profit : "+ main.getMyProductionCompany().showTotalProfit());
        profitShow.setVisible(flag);
        warningMessage.setVisible(false);
    }

    public void onLogOutClick(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Stage stage2 = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

    main.showLogInPage(stage2);
        warningMessage.setVisible(false);

    }

    public void onBackToClick(ActionEvent actionEvent) {
        addToListView(listView,main.getMyProductionCompany().getOwnMovies());
        backTo.setVisible(false);
        warningMessage.setVisible(false);
    }

    public void onMovieSee(ActionEvent actionEvent){
        //stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage=new Stage();
        stage.setHeight(400);
        stage.setWidth(650);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("movie-popUp.fxml"));
        Scene scene=null;
        try {


            scene = new Scene(fxmlLoader.load(), 400, 500);
        }
        catch (IOException e){
            System.out.println("Got Error "+e);
        }
        MoviePopUp controller = fxmlLoader.getController();
        Button clicked=(Button) actionEvent.getSource();
        String toSearch=clicked.getText();
        System.out.println("Button Clicked : "+toSearch);
        //controller.setMovie(movie);
        Movie movie = null;
        //for(Movie m:searchedList)
        for (Movie m:main.getMyProductionCompany().getOwnMovies()){
            if(m.getTitle().equalsIgnoreCase(toSearch)){
                movie=m;
                break;
            }
        }
        if(movie!=null)
        {System.out.println("Details of Movie: "+movie.getTitle());}

        else {
            System.out.println("Movie assigning error");
        }
        controller.setMv(movie);
        controller.setMovieDetails(movie);
        controller.setMain(main);


                       //stage=new Stage();
                        stage.setTitle("Movie Details");
                        stage.setScene(scene);
                        stage.initModality(Modality.APPLICATION_MODAL);
        warningMessage.setVisible(false);
                       // stage.initModality(Modality.APPLICATION_MODAL);
                        stage.show();
    }

    public void onChangePasswordClick(ActionEvent actionEvent) {
      //  main.getMyProductionCompany().setPassword("123456");

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Password.fxml"));
        // stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Stage stage1=new Stage();
        stage1.setHeight(250);
        stage1.setWidth(700);

        Scene scene = null;

        try {
            scene=new Scene(fxmlLoader.load(), 500, 500);
            Password ct=fxmlLoader.getController();

            ct.setMain(main);
            //  Movielist ml=HelloApplication.getMovielist1();
            // ct.setMvlist(ml);
            stage1.setScene(scene);
            stage1.setTitle("Changing Password of  "+main.getMyProductionCompany().getName().toUpperCase());
            stage1.initModality(Modality.APPLICATION_MODAL);
            warningMessage.setVisible(false);
            stage1.show();
        } catch (IOException e) {
            System.out.println("scene getting error of PassChangeclick");
        }



    }

    public void onaddClick(ActionEvent actionEvent){

       // Stage
        //
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Add Movie.fxml"));
       // stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Stage stage1=new Stage();
        stage1.setHeight(500);
        stage1.setWidth(600);

        Scene scene = null;

        try {
            scene=new Scene(fxmlLoader.load(), 500, 500);
            AddMovie ct=fxmlLoader.getController();
            ct.setStage(stage1);
            ct.setPc(main.getMyProductionCompany());
            ct.setPrevScene(this);
            ct.setMain(main);
          //  Movielist ml=HelloApplication.getMovielist1();
           // ct.setMvlist(ml);
            stage1.setScene(scene);
            stage1.setTitle("Add New Movies of  "+main.getMyProductionCompany().getName().toUpperCase());
            stage1.initModality(Modality.APPLICATION_MODAL);
            warningMessage.setVisible(false);
            stage1.show();
        } catch (IOException e) {
            System.out.println("scene getting error of addclick");
        }


    }



    public void OnTransferClick(ActionEvent actionEvent) {

        warningMessage.setVisible(false);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Transfer.fxml"));
        //stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Stage stage1=new Stage();
        stage1.setHeight(300);
        stage1.setWidth(600);

        Scene scene = null;

        try {
            scene=new Scene(fxmlLoader.load(), 500, 500);
            Transfer ct=fxmlLoader.getController();
            ct.setStage(stage);
            ct.setPc(main.getMyProductionCompany());
            ct.setMain(main);
          //  Movielist ml=HelloApplication.getMovielist1();
           // ct.setMvlist(ml);
            stage1.setScene(scene);
            stage1.setTitle("Transfer of  "+main.getMyProductionCompany().getName().toUpperCase());
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.show();
        } catch (IOException e) {
            System.out.println("scene getting error of addclick");
        }

    }


    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void onYearFilter(ActionEvent actionEvent) {

        warningMessage.setVisible(false);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("YearGenreQuery.fxml"));
        // stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Stage stage1=new Stage();
        stage1.setHeight(300);
        stage1.setWidth(600);

        Scene scene = null;

        try {
            scene=new Scene(fxmlLoader.load(), 500, 500);
            YearGenreQuery ct=fxmlLoader.getController();
           // ct.setStage(stage1);
            //ct.setPc(main.getMyProductionCompany());
            //ct.setPrevScene(this);
            //ct.setMain(main);
            ct.setMyHome(this);
            ct.setYear(true);
            ct.setGenre(false);
            ct.setRunningTime(false);
            ct.init();
            //  Movielist ml=HelloApplication.getMovielist1();
            // ct.setMvlist(ml);
            stage1.setScene(scene);
            stage1.setTitle("Filter Movies of  "+main.getMyProductionCompany().getName().toUpperCase());
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.show();
        } catch (IOException e) {
            System.out.println("scene getting error of addclick");
        }



    }

    public void onGenreFilter(ActionEvent actionEvent) {
        warningMessage.setVisible(false);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("YearGenreQuery.fxml"));
        // stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Stage stage1=new Stage();
        stage1.setHeight(300);
        stage1.setWidth(600);

        Scene scene = null;

        try {
            scene=new Scene(fxmlLoader.load(), 500, 500);
            YearGenreQuery ct=fxmlLoader.getController();

            ct.setMyHome(this);
            ct.setYear(false);
            ct.setGenre(true);
            ct.setRunningTime(false);
            ct.init();
            //  Movielist ml=HelloApplication.getMovielist1();
            // ct.setMvlist(ml);
            stage1.setScene(scene);
            stage1.setTitle("Filter Movies of  "+main.getMyProductionCompany().getName().toUpperCase());
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.show();
        } catch (IOException e) {
            System.out.println("scene getting error of addclick");
        }



    }

    public void onRunningTimeFilter(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("YearGenreQuery.fxml"));
        // stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        warningMessage.setVisible(false);

        Stage stage1=new Stage();
        stage1.setHeight(300);
        stage1.setWidth(600);

        Scene scene = null;

        try {
            scene=new Scene(fxmlLoader.load(), 500, 500);
            YearGenreQuery ct=fxmlLoader.getController();

            ct.setMyHome(this);
            ct.setYear(false);
            ct.setGenre(false);
            ct.setRunningTime(true);
            ct.init();
            //  Movielist ml=HelloApplication.getMovielist1();
            // ct.setMvlist(ml);
            stage1.setScene(scene);
            stage1.setTitle("Filter Movies of  "+main.getMyProductionCompany().getName().toUpperCase());
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.show();
        } catch (IOException e) {
            System.out.println("scene getting error of addclick");
        }

    }
}
