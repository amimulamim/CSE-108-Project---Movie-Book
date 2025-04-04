package allMovieList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Movie implements Serializable{
    private String Title,prod_comp;
    private int year,runtime;
    private long revenue,budget;
    private  String[] genre=new String[3];
    private long profit;

    public static boolean isNew=false;

    private String trailerLink;

    public String getTrailerLink() {
        return trailerLink;
    }

    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
    }

    //public Movie(String title,int yr,String genre1,String genre2,String genre3,int rt,int budget,int rev){
    public Movie(String[] data,Movielist movielist1){

        int i=0;
        this.Title=data[i++].trim();
        this.year=Integer.parseInt(data[i++].trim());
        for(int j=0;j<3;j++){
            this.genre[j]=data[i++].trim();
        }

        this.runtime=Integer.parseInt(data[i++].trim());
        this.prod_comp=data[i++].trim();

        this.budget=Long.parseLong(data[i++].trim());
        this.revenue=Long.parseLong(data[i++].trim());

        if(isNew)
        {movielist1.add_movies(this);}
            updateProductionCompany(movielist1);
            updateGenreMapping(movielist1);
            updateprofit(movielist1);
    }

    public void showDetails(){
        System.out.println();
        System.out.println("Title : "+this.Title);
        System.out.println("Year of Release : "+this.year);
        System.out.print("Genres : ");
        for(int i=0;i<3;i++){
            if(genre[i].length()>0 && i<2 && genre[i+1].length()>0){
                System.out.print(genre[i]+ ", ");
            }
            if(genre[i].length()>0 && (i==2 || genre[i+1].length()==0)){
                System.out.print(genre[i]);
            }
        }

        System.out.println("\nRunning time : "+this.runtime);
        System.out.println("Production Company : "+this.prod_comp);
        System.out.println("Budget : "+this.budget);
        System.out.println("Revenue : "+this.revenue+"\n\n");
    }

    private void updateProductionCompany(Movielist movielist1){

        Production_Company newcomp;
        String UP_Key=this.prod_comp.toUpperCase();
        System.out.println(UP_Key);
        if(Production_Company.isProductionCompany(UP_Key)==true) {
            newcomp= Production_Company.getProducer(UP_Key);
        }
        else {
            newcomp = new Production_Company(this.prod_comp);
           // System.out.println("new prod comp : "+this.prod_comp );
            Production_Company.putProducer(UP_Key,newcomp);
        }
        newcomp.produceMovie(this);
    }

    private void updateGenreMapping(Movielist movielist1) {
        for (int j = 0; j < 3; j++) {
            if (this.genre[j].length() > 0) {
                List<Movie> map_movies;
                String UP_key = this.genre[j].toUpperCase();
                if (movielist1.isGenre(UP_key) == true) {
                    map_movies = movielist1.getWithGenre(UP_key);
                } else {
                    map_movies = new ArrayList();
                    movielist1.putGenre(UP_key,map_movies);
                }
                map_movies.add(this);
            }

        }
    }

    public StringBuffer FileFormatting(){
        StringBuffer sb=new StringBuffer(this.Title);
        sb.append(",");
        sb.append(this.year);
        sb.append(",");
        sb.append(this.genre[0]);
        sb.append(",");
        sb.append(this.genre[1]);
        sb.append(",");
        sb.append(this.genre[2]);
        sb.append(",");
        sb.append(this.runtime);
        sb.append(",");
        sb.append(this.prod_comp);
        sb.append(",");
        sb.append(this.budget);
        sb.append(",");
        sb.append(this.revenue);

        return sb;

    }
    public long getProfit() {
        this.profit=this.revenue-this.budget;
        return profit;
    }

    private void updateprofit(Movielist movielist1){
        movielist1.addToProfit(this.getProfit());
        List<Movie> map_movies;
        if (movielist1.isProfit(this.getProfit()) == true) {
            map_movies = movielist1.getWithProfit(this.getProfit());
        } else {
            map_movies = new ArrayList();
            movielist1.putWithProfit(this.getProfit(), map_movies);
        }
        map_movies.add(this);
    }
    public String getTitle() {
        return Title;
    }

    public String getProd_comp() {
        return prod_comp;
    }
    public int getYear() {
        return year;
    }
    public long getBudget() {
        return budget;
    }
    public String[] getGenre() {
        return genre;
    }
    public long getRevenue() {
        return revenue;
    }
    public int getRuntime() {
        return runtime;
    }

    public void setGenre(String[] genre) {
        this.genre = genre;
    }

    public void setProd_comp(Production_Company prod_comp) {
        this.prod_comp = prod_comp.getName();
    }

}

