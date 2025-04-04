package DTO;

import allMovieList.Movie;

import java.io.Serializable;
import java.util.List;

public class UpdateProductionCompanyDetails implements Serializable {

    private int movieCount;
    private long profit;
    private List<Movie> myAllMovies;
    private List<Movie> myMostRecent;
    private List<Movie> myMaxRevenue;
    private int latestYear;
    private long maxProfit;
    private long maxRevenue;




    public int getMovieCount() {
        return movieCount;
    }

    public void setMovieCount(int movieCount) {
        this.movieCount = movieCount;
    }

    public long getProfit() {
        return profit;
    }

    public void setProfit(long profit) {
        this.profit = profit;
    }

    public List<Movie> getMyAllMovies() {
        return myAllMovies;
    }

    public void setMyAllMovies(List<Movie> myAllMovies) {
        this.myAllMovies = myAllMovies;
    }

    public List<Movie> getMyMostRecent() {
        return myMostRecent;
    }

    public void setMyMostRecent(List<Movie> myMostRecent) {
        this.myMostRecent = myMostRecent;
    }

    public List<Movie> getMyMaxRevenue() {
        return myMaxRevenue;
    }

    public void setMyMaxRevenue(List<Movie> myMaxRevenue) {
        this.myMaxRevenue = myMaxRevenue;
    }

    public int getLatestYear() {
        return latestYear;
    }

    public void setLatestYear(int latestYear) {
        this.latestYear = latestYear;
    }

    public long getMaxProfit() {
        return maxProfit;
    }

    public void setMaxProfit(long maxProfit) {
        this.maxProfit = maxProfit;
    }

    public long getMaxRevenue() {
        return maxRevenue;
    }

    public void setMaxRevenue(long maxRevenue) {
        this.maxRevenue = maxRevenue;
    }
}
