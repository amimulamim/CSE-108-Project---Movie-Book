package allMovieList;

import java.io.Serializable;
import java.util.*;

public class Movielist implements Serializable {

    private  List<Movie> movies=new ArrayList();

  //  private  HashMap<String,Production_Company> known_producer=new HashMap<String,Production_Company>();
    private HashMap<String,List<Movie> > genre_movies=new HashMap<String,List<Movie> >();
    private List<Long> profitvalues=new ArrayList();
    private HashMap<Long,List<Movie>> countingsort=new HashMap<Long, List<Movie>>();
    private HashSet<String> MovieNames=new HashSet<String>();
  /*  public boolean isProductionCompany(String check){
        return known_producer.containsKey(check);
    }
    public void putProducer(String name,Production_Company p){
        known_producer.put(name,p);
    }
    public Production_Company getProducer(String companyName){
        return known_producer.get(companyName);
    }
*/

    public boolean isGenre(String check){
        return genre_movies.containsKey(check);
    }
    public void putGenre(String name,List<Movie> ml){
        genre_movies.put(name,ml);
    }
    public List<Movie> getWithGenre(String genreName){
        return genre_movies.get(genreName);
    }


    public boolean isProfit(long check){
        return countingsort.containsKey(check);
    }
    public void putWithProfit(long profit,List<Movie> ml){
        countingsort.put(profit,ml);
    }
    public List<Movie> getWithProfit(long profit){
        return countingsort.get(profit);
    }


    public void add_movies(Movie movie){
            movies.add(movie);
            MovieNames.add(movie.getTitle().toUpperCase());
    }
    public List<Movie> getAll(){
        return movies;
    }

    public void addToProfit(long Profit){
        profitvalues.add(Profit);
    }

    public  boolean CanhashSetEntry(String s)
    {
        if(MovieNames.contains(s)==true){
            return false;
        }
        else{
            return true;
        }
    }

    public  List<Movie> SearchByTitle(String SearchTitle){
        List<Movie> movieList_local=new ArrayList<>();
        for(Movie m:movies){
            if(SearchTitle.equalsIgnoreCase(m.getTitle())){
                /*System.out.println(i+" .");
                m.showDetails();
                System.out.println();
                found=true;
                i++;*/
                movieList_local.add(m);
            }
        }
        return movieList_local;

    }

    public List<Movie> SearchByReleaseYear(int search_year){
        List<Movie> movieList_local=new ArrayList<>();
        for(Movie m:movies){
            if(m.getYear()==search_year){
                movieList_local.add(m);
            }
        }
        return movieList_local;

    }

    public  List<Movie> searchByGenre(String genresearch){
        String keyUP=genresearch.toUpperCase();

        if((genre_movies.containsKey(keyUP))==false){
           // System.out.println("No such movie with this genre");
            return null;
        }
        else {
            List<Movie> withGenre=genre_movies.get(keyUP);
           // System.out.println("Movies with genre "+genresearch+" :");
            return withGenre;
        }
    }

    public  List<Movie> SearchByTime(int from,int to){
        List<Movie> movieList_local=new ArrayList<>();
        for(Movie m:movies){
            if(m.getRuntime()>=from && m.getRuntime()<=to){
                movieList_local.add(m);
            }
        }
        return movieList_local;

    }


    public  List<Movie> top(int n){
        //used counting sort algorithm for optimization
        Collections.sort(profitvalues,Collections.reverseOrder());
        int i=1;
        List<Movie> locallist=new ArrayList<>();
        while(i<=n){
            for(int j=0;j<profitvalues.size() && i<=n;j++){
                long val=profitvalues.get(j);
                List<Movie> here=countingsort.get(val);
                for(int k=0;k<here.size() && i<=n;k++,i++){
                    Movie m=here.get(k);
                    locallist.add(m);
                }
            }
        }
        return locallist;
    }

    public static void printMovieList(List<Movie> ml){
        if(ml.size()==0){
            System.out.println("No such found!!!");
        }
        else {
            int i=1;
            for (Movie m : ml) {
                System.out.println(i++);
                m.showDetails();
                System.out.println();
            }
            System.out.println();
        }
    }

}
