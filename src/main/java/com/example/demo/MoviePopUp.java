package com.example.demo;

import allMovieList.Movie;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

//import static jdk.jpackage.internal.WixAppImageFragmentBuilder.ShortcutsFolder.Desktop;
//import java.awt.Desktop;

//import static jdk.jpackage.internal.WixAppImageFragmentBuilder.ShortcutsFolder.Desktop;

public class MoviePopUp {
    Stage stage;
    private Movie mv;
    public Movie getMv() {
        return mv;
    }

    public void setMv(Movie mv) {
        this.mv = mv;
    }

    private HelloApplication main;






    @FXML
    private Label Title;
    @FXML
    private Label releaseYear,runtime,prodComp,genres,budget,revenue;
@FXML
private ImageView movieImage;
@FXML
private Hyperlink hyperlink;
private String TrailerLink;
    public Label getTitle() {
        return Title;
    }

    public Label getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
       // this.releaseYear = releaseYear;
        this.releaseYear.setText("Release Year : "+releaseYear);
    }

    public void setTitle(String title) {
        Title.setText(title);
        //Title.setTextFill(Color.DARKRED);
    }

    public Label getRuntime() {
        return runtime;
    }



    public void setRuntime(String runtime) {
      //  this.runtime = runtime;
        this.runtime.setText("Running Time : "+runtime);
    }

    public Label getProdComp() {
        return prodComp;
    }

    public void setProdComp(String prodComp) {
        this.prodComp.setText("Production Company : "+prodComp);
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Label getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres.setText("Genres : "+ genres[0]+"  "+genres[1]+"  "+genres[2]);
    }

    public Label getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget.setText("Budget : "+budget);
    }

    public Label getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue.setText("Revenue : "+revenue );
    }
    public void setMovieDetails(Movie m){
        setTitle(m.getTitle());
        setReleaseYear(String.valueOf(m.getYear()));
        setGenres(m.getGenre());
        setProdComp(m.getProd_comp());
        setBudget(String.valueOf(m.getBudget()));
        setRuntime(String.valueOf(m.getRuntime()));
        setRevenue(String.valueOf(m.getRevenue()));
        System.out.println("set hyperlink Text to : "+m.getTrailerLink());
        if(m.getTrailerLink()!="")

        {
            hyperlink.setText(m.getTrailerLink());
            System.out.println("set hyperlink Text to : "+m.getTrailerLink());
            hyperlink.setUnderline(true);
            hyperlink.setVisible(true);
        }
        else if(m.getTrailerLink().strip()==""){
           // hyperlink.setVisible(false);
           // String path="https://www.youtube.com/results?search_query=aynabaji+trailer";
            String yt="https://www.youtube.com/results?search_query=";
            yt=yt+m.getTitle();
           String path=yt+"+trailer";
            hyperlink.setText(path);
            System.out.println("set hyperlink Text to deafult : "+path);
            hyperlink.setUnderline(true);
            hyperlink.setVisible(true);

        }


        try {
            System.out.println("Here");
            String path="src/main/resources/images/";

            String str=(m.getTitle());
            str = str.replaceAll("[^a-zA-Z0-9]", "");
            String imgName=str+"poster.jpg";
            System.out.println("Image Name : "+imgName);
            path=path+imgName;



            File file=new File(path);
            if(file.exists() && !file.isDirectory()){
                System.out.println("file exists");
                Image img = new Image(file.toURI().toString());
                movieImage.setImage(img);
            }
            else {
                System.out.println("doesnt exist");
                throw new Exception();
            }



        }
        catch (Exception e){
          System.out.println(" image not found");

            try {
                System.out.println("Here");
                File file1=new File("src/main/resources/images/mvDefault.png");
                if(file1.exists() && !file1.isDirectory()){
                    System.out.println("default file exists");
                }
                else {
                    System.out.println("default doesnt exist");
                }


                Image img = new Image(file1.toURI().toString());
                movieImage.setImage(img);
            }
            catch (Exception es){
                System.out.println("company image loading error "+es);
            }

        }



    }


    public HelloApplication getMain() {
        return main;
    }

    public void setMain(HelloApplication main) {
        this.main = main;
    }

    public void onCloseMovieDetails(ActionEvent actionEvent) {
        Stage mystg=(Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        mystg.close();
    }

    public void onClickLink(ActionEvent actionEvent) {
        System.out.println("Link Clicked");

        HostServices hostServices=main.getHostServices();
        hostServices.showDocument(hyperlink.getText());

        hyperlink.setVisited(false);

    }
}
