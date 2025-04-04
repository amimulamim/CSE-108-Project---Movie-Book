
package tcpforward;

import allMovieList.Movie;
import allMovieList.Movielist;
import allMovieList.Production_Company;

import com.example.demo.HelloApplication;
import util.SocketWrapper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import DTO.*;

public class Server {
    private ServerSocket serverSocket;
    public HashMap<String, SocketWrapper> clientMap = new HashMap();
    private Movielist movielist1;

    public HashMap<String,Production_Company> companyMap;



    /*private static League FiveASideLeague;
    private HashMap<String, String> clubPasswordList;
    private HashMap<String, NetworkUtil> clubNetworkUtilMap;
    private ArrayList<Player> transferListedPlayers;
    private ArrayList<Pair<String, String>> countryFlagList;
    private ArrayList<Pair<String, String>> clubLogoList;
    private HashMap<String, String> unaccented_accented;*/



    public Server(Movielist movielist) {
        new Thread(() ->{
            movielist1 = movielist;
            try {
                FileOperations.readfile(movielist1,"movies.txt");

                HashMap<String, Integer> mp=Production_Company.showallCompanies();
                System.out.println("ALL COMPANIES : ");
                System.out.println(mp);
                companyMap=Production_Company.getKnown_producer();
                Movie.isNew=true;
                FileOperations.readTrailers(movielist1,"trailers.txt");
                FileOperations.readPasswords(movielist1,"passwords.txt");

                System.out.println("File reading done,ready to serve");

            } catch (Exception e) {
                System.out.println("File operation error!!Check for correct file! "+e);
                //System.exit(1);
            }

            try {
                this.serverSocket = new ServerSocket(33333);

                while(true) {
                    Socket clientSocket = this.serverSocket.accept();
                    this.serve(clientSocket);
                }
            } catch (Exception var2) {
                System.out.println("Server starts:" + var2);
            }


        }).start();

    }


    //public static HashMap<String, Production_Company> getCompanyMap(){
      //  return
    //}

    public void serve(Socket clientSocket) throws IOException, ClassNotFoundException {
        SocketWrapper socketWrapper = new SocketWrapper(clientSocket);

         ReadThreadServer rts=new ReadThreadServer(this.clientMap, this.companyMap,socketWrapper,movielist1);



    }




    public static void main(String[] args) throws Exception {
      //  int port = 44444;
        Movielist movielist=new Movielist();
        //this.movielist1=movielist;
        Server server = new Server(movielist);
       Thread thrToStop= new Thread(()-> {
            Scanner scanner = new Scanner(System.in);
            String next;
            while (true){
                //System.out.println("Enter Stop to stop server");
                next = scanner.nextLine();
                System.out.println("got in Console : "+next);
                if (next.strip().equalsIgnoreCase("Stop")){
                    System.out.println("Server to be stopped now");
                    try {
                       // FileOperations.writePlayerDataToFile("src/Assets/Data/PlayerDatabase.txt", FiveASideLeague.CentralPlayerDatabase);
                        FileOperations.writefile();


                        BufferedWriter bw = new BufferedWriter(new FileWriter("passwords.txt",false));
                       // List<Movie> ml=movielist.getAll();
                        //Collections getProductionCompanies=this.companyMap.values();
                        HashMap<String,Production_Company> cmpMap=Production_Company.getKnown_producer();
                        for(Production_Company prc:cmpMap.values()) {
                            //Movie toWrite=ml.get(i);
                            //String sb=(toWrite.FileFormatting()).toString();
                            String sb=prc.getName()+","+prc.getPassword();
                            bw.write(sb);
                            bw.write(System.lineSeparator());
                        }
                        bw.close();
                        System.out.println("New Files written successfully");


                    } catch (Exception e) {
                        System.out.println("Trouble closing server");
                        e.printStackTrace();
                    }
                    try {
                        server.serverSocket.close();
                    } catch (IOException e) {
                        System.out.println("Trouble closing server");
                        e.printStackTrace();
                    }
                    System.out.println("Exitting");
                    System.exit(0);
                }
            }
        });
       thrToStop.start();
       thrToStop.join();

        /*
        while (true){
            var cs = server.serverSocket.accept();
            server.serve(cs);
        }
        */

    }




}