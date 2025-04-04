
package tcpforward;

import allMovieList.Movie;
import allMovieList.Movielist;
import allMovieList.Production_Company;
import com.example.demo.HelloApplication;
import com.example.demo.HomeView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.SocketWrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import DTO.*;

public class ReadThreadServer implements Runnable {
    private final Thread thr;
    private final SocketWrapper socketWrapper;
    public HashMap<String, SocketWrapper> clientMap;
    public HashMap<String,Production_Company> companyMap;

    private Movielist movielist;



    public ReadThreadServer(HashMap<String, SocketWrapper> map,HashMap<String,Production_Company> companyMap, SocketWrapper socketWrapper,Movielist movielist1) {
        this.clientMap = map;
        this.socketWrapper = socketWrapper;
        this.thr = new Thread(this);
        this.companyMap=companyMap;
        this.movielist=movielist1;
        this.thr.start();
    }

    public Movielist getMovielist() {
        return movielist;
    }

    public void setMovielist(Movielist movielist) {
        this.movielist = movielist;
    }

    public void run() {
        try {

            while (true) {
                Object o = null;
                try {
                    o = this.socketWrapper.read();
                } catch (IOException e) {
                    System.out.println("Server Reading error " + e);
                } catch (ClassNotFoundException e) {
                    //throw new RuntimeException(e);
                    System.out.println("Server Reading error " + e);
                }
                List<Movie> now = null;
                if (o instanceof TransferRequest) {
                    TransferRequest obj=(TransferRequest) o;
                   // Message obj = (Message) o;
                    String to = obj.getTo();
                    String upk = to.toUpperCase();
                    String from= obj.getFrom().toUpperCase();
                    String mvName= obj.getMovieName();
                   // String command= obj.getCommand();
                    // String rom=obj.getFrom().toUpperCase();

                    if (Production_Company.isProductionCompany(upk)) {

                        Production_Company pcTo = Production_Company.getProducer(upk);
                        Production_Company pcFrom = Production_Company.getProducer(from);
                       // Movie m=obj.getMovie();

                        Movie toTransfer=null;
                        for (Movie m : pcFrom.getOwnMovies()) {
                            if (m.getTitle().equalsIgnoreCase(mvName)){
                                toTransfer = m;
                                break;
                            }
                        }
                       // System.out.println("Transfer from :" + pcFrom.getName() + " To: " + pcTo.getName() + " Movie: " + toTransfer.getTitle());
                        if (toTransfer == null) {
                            System.out.println("No Such Movie found to transfer");
                            ModifyStatusWarning mdw=new ModifyStatusWarning();
                            mdw.setTransfer(true);
                            mdw.setMessage("No Such Movie found to transfer");

                            socketWrapper.write("No Such Movie found to transfer");


                        } else if(toTransfer!=null) {
                            pcFrom.transferMovie(pcTo, obj.getMovieName());
                           // now = pcFrom.getOwnMovies();
                            try {



                             //   Production_Company prod_comp=pcFrom;
                                UpdateProductionCompanyDetails upd=new UpdateProductionCompanyDetails();
                                upd.setMovieCount(pcFrom.getMovieCount());
                                upd.setProfit(pcFrom.showTotalProfit());
                                upd.setMyAllMovies(pcFrom.getOwnMovies());
                                upd.setMyMostRecent(pcFrom.showMostRecent());
                                upd.setMyMaxRevenue(pcFrom.showMaxRevenue());
                                upd.setLatestYear(pcFrom.getLatestYear());
                                upd.setMaxProfit(pcFrom.getMaxProfitAmount());
                                upd.setMaxRevenue(pcFrom.getMaxRevenue());



                                socketWrapper.write(upd);
                                socketWrapper.write(pcFrom.getOwnMovies());





                             //   socketWrapper.write(pcFrom);
                              //  socketWrapper.write(pcFrom.getOwnMovies());
                            /*
                                System.out.println("written from server :");
                                System.out.println("Name of Company : ");
                                System.out.println(pcFrom.getName());
                                System.out.println("Total Movies : " + pcFrom.getOwnMovies().size());
                                System.out.println("Profit : " + pcFrom.showTotalProfit());
                                System.out.println("Most Recent Movie : \n\n");
                               List<Movie> ml = pcFrom.showMostRecent();
                                Movielist.printMovieList(ml);
                                System.out.println("Most Max Revenue Movie : \n\n");
                                ml = pcFrom.showMaxRevenue();
                                Movielist.printMovieList(ml);*/
                            } catch (IOException e) {
                                System.out.println("Transfer writing error from server to prev owner");
                            }
                           // List<Movie> moviesTo = pcTo.getOwnMovies();
                            SocketWrapper nu = (SocketWrapper) this.clientMap.get(to.toUpperCase());

                            if (nu != null) {
                                try {


                                //    Production_Company prod_comp=pcTo;
                                    UpdateProductionCompanyDetails upd2=new UpdateProductionCompanyDetails();
                                    upd2.setMovieCount(pcTo.getMovieCount());
                                    upd2.setProfit(pcTo.showTotalProfit());
                                    upd2.setMyAllMovies(pcTo.getOwnMovies());
                                    upd2.setMyMostRecent(pcTo.showMostRecent());
                                    upd2.setMyMaxRevenue(pcTo.showMaxRevenue());
                                    upd2.setLatestYear(pcTo.getLatestYear());
                                    upd2.setMaxProfit(pcTo.getMaxProfitAmount());
                                    upd2.setMaxRevenue(pcTo.getMaxRevenue());



                                    nu.write(upd2);
                                    nu.write(pcTo.getOwnMovies());






                                  //  nu.write(pcTo);
                                    //nu.write(pcTo.getOwnMovies());


                                    System.out.println("written from server about to company:");
                                    System.out.println("Name of Company : ");
                                    System.out.println(pcTo.getName());
                                    System.out.println("Total Movies : " + pcTo.getOwnMovies().size());
                                    System.out.println("Profit : " + pcTo.showTotalProfit());
                                    System.out.println("Most Recent Movie : \n\n");
                                    List<Movie> ml = pcTo.showMostRecent();
                                    Movielist.printMovieList(ml);
                                    System.out.println("Most Max Revenue Movie : \n\n");
                                    ml = pcTo.showMaxRevenue();
                                    Movielist.printMovieList(ml);



                                } catch (IOException e) {
                                    System.out.println("Transfer writing error from server to new owner");
                                }
                            }else {
                                System.out.println("\n\nNo such client currently active\n");
                            }
                        }

                    } else {
                        System.out.println("No such prod comp found");
                        ModifyStatusWarning mdw=new ModifyStatusWarning();
                        mdw.setTransfer(true);
                        mdw.setMessage("No Such Production Company found to transfer");

                        socketWrapper.write("No Such Production Company found to transfer");

                    }


                }
                if (o instanceof LoginRequest) {
                    LoginRequest lr = (LoginRequest) o;
                    String userName = lr.getUserName();
                    String password = lr.getPassword();

                    String upk=userName.toUpperCase();
                    clientMap.put(upk,socketWrapper);


                  //  if (Production_Company.isProductionCompany(userName.toUpperCase())) {
                    if (companyMap.containsKey(upk)){
                        Production_Company pc = companyMap.get(userName.toUpperCase());
                        System.out.println("To be assigned Company Name : "+pc.getName());
                        if (pc.passwordMatch(password)) {
                            LoginApproval la = new LoginApproval(true, true, pc);
                            System.out.println("Login approval ready : "+la);
                            try {

                                System.out.println("Before writing from server :");
                                Production_Company pcTo=la.getPc();
                                System.out.println("Name of Company : ");
                                System.out.println(pcTo.getName());
                                System.out.println("Total Movies : " + pcTo.getOwnMovies().size());
                                List<Movie> ml=pcTo.getOwnMovies();
                                System.out.println("all movies : ");
                                Movielist.printMovieList(ml);
                                System.out.println("Profit : " + pcTo.showTotalProfit());
                                System.out.println("Most Recent Movie : \n\n");
                                ml = pcTo.showMostRecent();
                                Movielist.printMovieList(ml);
                                System.out.println("Most Max Revenue Movie : \n\n");
                                ml = pcTo.showMaxRevenue();
                                Movielist.printMovieList(ml);






                                socketWrapper.write(la);
                                System.out.println("Login Approval Written");


                                System.out.println("written from server :");
                                pcTo=la.getPc();
                                System.out.println("Name of Company : ");
                                System.out.println(pcTo.getName());
                                System.out.println("Total Movies : " + pcTo.getOwnMovies().size());
                                ml=pcTo.getOwnMovies();
                                System.out.println("all movies : ");
                                Movielist.printMovieList(ml);
                                System.out.println("Profit : " + pcTo.showTotalProfit());
                                System.out.println("Most Recent Movie : \n\n");
                                 ml = pcTo.showMostRecent();
                                Movielist.printMovieList(ml);
                                System.out.println("Most Max Revenue Movie : \n\n");
                                ml = pcTo.showMaxRevenue();
                                Movielist.printMovieList(ml);



                            } catch (IOException e) {
                                System.out.println("Login Approval writing failed");
                            }

                        } else {
                            LoginApproval la = new LoginApproval(true, false, null);
                            try {
                                socketWrapper.write(la);
                                System.out.println("Login Approval Written as Not matched");
                            } catch (IOException e) {
                                System.out.println("Login Approval writing failed");
                            }
                        }


                    } else {
                        LoginApproval la = new LoginApproval(false, false, null);
                        try {
                            socketWrapper.write(la);
                            System.out.println("login approval written as not found");
                        } catch (IOException e) {
                            System.out.println("Login Approval writing failed");
                        }
                    }
                }
                if (o instanceof AddRequest){
                    AddRequest ar=(AddRequest)o;
                    String[] data=ar.getData();

                    if(movielist.CanhashSetEntry(data[0].toUpperCase())) {
                        Movie m = new Movie(data, movielist);
                        m.setTrailerLink(ar.getTrailerLink());

                        Production_Company prod_comp = companyMap.get(ar.getCompanyName().toUpperCase());

                        // prod_comp.addToOurMovie(m);
                        // System.out.println(prod_comp.);
                        List<Movie> ml = prod_comp.getOwnMovies();
                        //System.out.println(ml);
                        System.out.println("From server reader,before writing ,size= " + prod_comp.getOwnMovies().size());
                        System.out.println("Name of Company : ");
                        System.out.println(prod_comp.getName());
                        System.out.println("Total Movies : " + prod_comp.getOwnMovies().size());
                        System.out.println("All movies : ");
                        List<Movie> ml2 = prod_comp.getOwnMovies();
                        Movielist.printMovieList(ml2);
                        System.out.println("Profit : " + prod_comp.showTotalProfit());
                        System.out.println("Most Recent Movie : \n\n");
                        ml2 = prod_comp.showMostRecent();
                        Movielist.printMovieList(ml2);
                        System.out.println("Most Max Revenue Movie : \n\n");
                        ml2 = prod_comp.showMaxRevenue();
                        Movielist.printMovieList(ml2);


                        // LoginApproval la=new LoginApproval(true,true,prod_comp);
                        UpdateProductionCompanyDetails upd = new UpdateProductionCompanyDetails();
                        upd.setMovieCount(prod_comp.getMovieCount());
                        upd.setProfit(prod_comp.showTotalProfit());
                        upd.setMyAllMovies(prod_comp.getOwnMovies());
                        upd.setMyMostRecent(prod_comp.showMostRecent());
                        upd.setMyMaxRevenue(prod_comp.showMaxRevenue());
                        upd.setLatestYear(prod_comp.getLatestYear());
                        upd.setMaxProfit(prod_comp.getMaxProfitAmount());
                        upd.setMaxRevenue(prod_comp.getMaxRevenue());


                        socketWrapper.write(upd);
                        socketWrapper.write(prod_comp.getOwnMovies());
                        //   prod_comp=la.getPc();

                        System.out.println("written from server :");
                        System.out.println("Name of Company : ");
                        System.out.println(prod_comp.getName());
                        System.out.println("Total Movies : " + prod_comp.getOwnMovies().size());
                        System.out.println("All movies : ");
                        ml2 = prod_comp.getOwnMovies();
                        Movielist.printMovieList(ml2);
                        System.out.println("Profit : " + prod_comp.showTotalProfit());
                        System.out.println("Most Recent Movie : \n\n");
                        ml2 = prod_comp.showMostRecent();
                        Movielist.printMovieList(ml2);
                        System.out.println("Most Max Revenue Movie : \n\n");
                        ml2 = prod_comp.showMaxRevenue();
                        Movielist.printMovieList(ml2);
                    }
                    else {
                        System.out.println("This movie already exists");
                        socketWrapper.write("There already exists a movie in this name.Adding Failed");
                    }
                }
                if(o instanceof PasswordChange){
                    PasswordChange ps=(PasswordChange)o;
                    Production_Company pc=Production_Company.getProducer(ps.getCompanyName().toUpperCase());
                    pc.setPassword(ps.getPassword());
                    System.out.println("Password Changed Successfully");
                }
            }
        }catch (Exception var12) {
            System.out.println(var12);
        } finally {
            try {
                this.socketWrapper.closeConnection();
            } catch (IOException var11) {
                var11.printStackTrace();
            }

        }

    }
}