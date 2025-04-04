package allMovieList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class Production_Company implements Serializable {
    private List<Movie> produced_movies = new ArrayList();
    private static List<Production_Company> companies = new ArrayList();
    private String name;
    private List<Movie> max_revenue = new ArrayList();
    private List<Movie> latest = new ArrayList();
    private long maxProfit, maxRevenue;
    private long profit;
    private int latestyear, movieCount;

    private String password = "123456";

    public static HashMap<String, Production_Company> getKnown_producer() {
        return known_producer;
    }

    public static void setKnown_producer(HashMap<String, Production_Company> known_producer) {
        Production_Company.known_producer = known_producer;
    }

    private static HashMap<String,Production_Company> known_producer=new HashMap<String,Production_Company>();

    public static boolean isProductionCompany(String check){
        return known_producer.containsKey(check);
    }
    public static void putProducer(String name,Production_Company p){
        known_producer.put(name,p);
    }
    public static Production_Company getProducer(String companyName){
        return known_producer.get(companyName);
    }

    public Production_Company(String name) {
        this.name = name;
        maxProfit = 0;
        maxRevenue = 0;
        latestyear = 0;
        profit = 0;
        movieCount = 0;
        companies.add(this);
    }

    public String getName() {
        return name;
    }
    public void setPassword(String pass){
        password=pass;
    }
    public boolean passwordMatch(String check){
        if(check.equals(password)){
            return true;
        }
        return false;
    }

    public void produceMovie(Movie movie) {

        movieCount++;
        this.profit += (movie.getProfit());
        System.out.println("before adding : "+produced_movies.size());
         this.produced_movies.add(movie);
        System.out.println("after adding : "+produced_movies.size());
        if (movie.getYear() > latestyear) {
            latestyear = movie.getYear();
            latest.clear();
            latest.add(movie);
        } else if (movie.getYear() == latestyear) {
            latest.add(movie);
        }
        if (movie.getRevenue() > maxRevenue) {
            maxRevenue = movie.getRevenue();
            max_revenue.clear();
            max_revenue.add(movie);
        } else if (movie.getRevenue() == maxRevenue) {
            max_revenue.add(movie);
        }

    }


    public long showTotalProfit() {
        return this.profit;
        // System.out.println("Total Profit of "+this.name+" : "+this.profit);
    }

    public List<Movie> showMostRecent() {
        // System.out.println("Latest Movies of "+this.name +" Last Release in "+this.latestyear);
        List<Movie> mvlist = new ArrayList<>();
        for (int i = 0; i < latest.size(); i++) {
            Movie m = latest.get(i);
                /*System.out.println(i+1);
                m.showDetails();
                System.out.println();*/
            mvlist.add(m);
        }
        //System.out.println();
        return mvlist;
    }
    public void updateRevProfRecent() {
        // System.out.println("Latest Movies of "+this.name +" Last Release in "+this.latestyear);

        maxRevenue=0;
        latestyear=0;
        for (Movie movie:produced_movies) {
            if (movie.getYear() > latestyear) {
                latestyear = movie.getYear();
                latest.clear();
                latest.add(movie);
            } else if (movie.getYear() == latestyear) {
                latest.add(movie);
            }
            if (movie.getRevenue() > maxRevenue) {
                maxRevenue = movie.getRevenue();
                max_revenue.clear();
                max_revenue.add(movie);
            } else if (movie.getRevenue() == maxRevenue) {
                max_revenue.add(movie);
            }
        }
    }
    public List<Movie> showMaxRevenue() {
            /*System.out.println("Movies with Maximum Revenue of "+this.name +" : ");
            int i=1;*/
        List<Movie> movieList_local = new ArrayList<>();
        for (Movie m : max_revenue) {
            //Movie m=latest.get(i);
                /*System.out.println(i++);
                m.showDetails();
                System.out.println();*/
            movieList_local.add(m);
        }
        // System.out.println();
        return movieList_local;
    }

    public static HashMap<String, Integer> showallCompanies() {
           // int i=1;
           // System.out.println("Production Companies :");
           // System.out.println();
        HashMap<String, Integer> hm = new HashMap<>();
        for (Production_Company p : companies) {
          //  System.out.println((i++)+". "+p.name+" ,Total movies released: "+ p.movieCount);
            hm.put(p.name, p.movieCount);
        }
        return hm;
    }

    public  List<Movie> searchByCompany(String comp, Movielist movielist1) {
        String upk = comp.toUpperCase();
        List<Movie> ml = new ArrayList<>();
        if (Production_Company.isProductionCompany(upk) == true) {
            Production_Company here = Production_Company.getProducer(upk);

            //System.out.println("Movies from the Production Company "+here.name);
            //  int i=1;
                /*for(Movie m: here.produced_movies){
                   /* System.out.print(i++);
                    m.showDetails();
                    System.out.println();*/
                   /* ml.add(m);

                }

            }
            return ml;*/
            ml = here.produced_movies;

        }
        return ml;
    }

    public void addToOurMovie(Movie m){
        boolean found=false;
        for(Movie mv:produced_movies){
            if(mv==m){
                found=true;
                break;
            }
        }
        if(found==false)
        {  produceMovie(m);}
    }
    public void removeFromOurMovies(Movie m){
        produced_movies.remove(m);
        movieCount--;
        profit-=(m.getProfit());
        if(latest.size()>1 && max_revenue.size()>1){
            latest.remove(m);
            max_revenue.remove(m);
        }
        else {
          /*  Thread thr1=new Thread(this::updateRevProfRecent,"t1");
            thr1.start();
            try {
                thr1.join();
            } catch (InterruptedException e) {
                System.out.println("thread joining error :"+e);
            }*/
            updateRevProfRecent();
        }
    }
   public List<Movie> getOwnMovies(){
        return produced_movies;
    }

    public void transferMovie(Production_Company to,Movie m){
        to.addToOurMovie(m);
        removeFromOurMovies(m);
        m.setProd_comp(to);
    }
    public void transferMovie(Production_Company to,String SearchTitle){
        Movie mv=null;

            for(Movie m:produced_movies){
                if(SearchTitle.equalsIgnoreCase(m.getTitle())){
                    mv=m;
                    break;
                }
            }
        to.addToOurMovie(mv);
        removeFromOurMovies(mv);
        mv.setProd_comp(to);
    }
   // public static void isProductionCompany(String name){


    public int getMovieCount() {
        return movieCount;
    }

    public void setProduced_movies(List<Movie> produced_movies) {

        this.produced_movies = produced_movies;
    }

    public void setMax_revenueList(List<Movie> max_revenue) {
        this.max_revenue = max_revenue;
    }

    public void setLatestList(List<Movie> latest) {
        this.latest = latest;
    }

    public void setMaxProfit(long maxProfit) {

        this.maxProfit = maxProfit;
    }

    public void setMaxRevenue(long maxRevenue) {
        this.maxRevenue = maxRevenue;
    }

    public void setProfit(long profit) {
        this.profit = profit;
    }

    public void setLatestyear(int latestyear) {
        this.latestyear = latestyear;
    }

    public void setMovieCount(int movieCount) {
        this.movieCount = movieCount;
    }

    public long getMaxProfitAmount() {
        return maxProfit;
    }
    public int getLatestYear(){
        return latestyear;
    }

    public long getMaxRevenue(){
        return maxRevenue;
    }

    public String getPassword() {
        return password;
    }
}
