package DTO;

import java.io.Serializable;

public class TransferRequest implements Serializable {
   private String from;
        private   String to;
    private String movieName;

    public TransferRequest(String from,String to,String movie){
        this.from=from;
        this.to=to;
        this.movieName=movie;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
}
