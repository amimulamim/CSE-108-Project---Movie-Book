package tcpforward;

import allMovieList.Movie;
import allMovieList.Movielist;
import allMovieList.Production_Company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileOperations {

    private static final String INPUT_FILE_NAME = "movies.txt";
    private static final String OUTPUT_FILE_NAME = "movies.txt";
   private static List<Movie> newly_added=new ArrayList();
    public static void add_toNewlyAdded(Movie m){
        newly_added.add(m);
    }
    private static Movielist movielist;
    public static void readfile(Movielist movielist2,String name){


        BufferedReader br = null;
        try {

            br = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        }
        catch (Exception e){
            System.out.println("Can not Open Buffered Reader");
        }
        movielist=movielist2;
        while (true) {
            String line=null;
            try {


                line = br.readLine();
               // System.out.println(line);
            }
            catch (Exception e){
                System.out.println("Reading Line error");
            }
            if (line == null) break;
            String[] to_list=line.split(",");
            Movie to_add=new Movie(to_list,movielist);
           // System.out.println("MOVIE: "+to_add.getTitle());
            movielist.add_movies(to_add);
          //  System.out.println(line);
        }
        try {


            br.close();
           // Movie.isNew=true;
        }
        catch (IOException e){
            System.out.println("Reader Closing Error");
        }
    }


    public static void readTrailers(Movielist movielist2,String name){


        BufferedReader br = null;
        try {

            br = new BufferedReader(new FileReader(name));
        }
        catch (Exception e){
            System.out.println("Can not Open Buffered Reader");
        }
        movielist=movielist2;
        while (true) {
            String line=null;
            try {


                line = br.readLine();
                // System.out.println(line);
            }
            catch (Exception e){
                System.out.println("Reading Line error");
            }
            if (line == null) break;
            String[] to_list=line.split(",");
            System.out.println("to list string array= "+to_list);
            List<Movie> ml=movielist.SearchByTitle(to_list[0]);
            Movie m=ml.get(0);
            m.setTrailerLink(to_list[1]);

            System.out.println("Written Movie : "+m.getTitle()+" trailer : "+m.getTrailerLink());
            // System.out.println("MOVIE: "+to_add.getTitle());
           // movielist.add_movies(to_add);
            //  System.out.println(line);
        }
        try {


            br.close();
            // Movie.isNew=true;
        }
        catch (IOException e){
            System.out.println("Reader Closing Error");
        }
    }



    public static void readPasswords(Movielist movielist2,String name){


        BufferedReader br = null;
        try {

            br = new BufferedReader(new FileReader(name));
        }
        catch (Exception e){
            System.out.println("Can not Open Buffered Reader");
        }
        movielist=movielist2;
        while (true) {
            String line=null;
            try {


                line = br.readLine();
                // System.out.println(line);
            }
            catch (Exception e){
                System.out.println("Reading Line error");
            }
            if (line == null) break;
            String[] to_list=line.split(",");
         /*   List<Movie> ml=movielist.SearchByTitle(to_list[0]);
            Movie m=ml.get(0);
            m.setTrailerLink(to_list[1]);*/
            Production_Company pc=Production_Company.getProducer(to_list[0].toUpperCase());
            pc.setPassword(to_list[1]);
            System.out.println(pc.getName()+" password : "+pc.getPassword());

          //  System.out.println("Written Movie : "+m.getTitle()+" trailer : "+m.getTrailerLink());
            // System.out.println("MOVIE: "+to_add.getTitle());
            // movielist.add_movies(to_add);
            //  System.out.println(line);
        }
        try {


            br.close();
            // Movie.isNew=true;
        }
        catch (IOException e){
            System.out.println("Reader Closing Error");
        }
    }



    public static void writefile() throws Exception{
       /* BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME,true));
        for(int i=0;i<newly_added.size();i++) {
            Movie toWrite=newly_added.get(i);
            String sb=(toWrite.FileFormatting()).toString();
            bw.write(sb);
            bw.write(System.lineSeparator());
        }
        bw.close();
        newly_added.clear();*/
        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME,false));
        BufferedWriter bw2 = new BufferedWriter(new FileWriter("trailers.txt",false));

        List<Movie> ml=movielist.getAll();
        for(int i=0;i<ml.size();i++) {
            Movie toWrite=ml.get(i);
            String sb=(toWrite.FileFormatting()).toString();
            String trail=toWrite.getTitle()+","+toWrite.getTrailerLink();
            bw.write(sb);
            bw.write(System.lineSeparator());
            bw2.write(trail);
            bw2.write(System.lineSeparator());
        }
        bw.close();
        bw2.close();
        ml.clear();
    }
}