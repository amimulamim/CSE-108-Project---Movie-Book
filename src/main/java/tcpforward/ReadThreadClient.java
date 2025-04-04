

package tcpforward;
import DTO.*;
import allMovieList.Movie;
import allMovieList.Movielist;
import allMovieList.Production_Company;
import com.example.demo.HelloApplication;
import javafx.application.Platform;
import util.SocketWrapper;

import java.io.IOException;
import java.util.List;
/*
public class ReadThreadClient implements Runnable {
    private Thread thr;
    private SocketWrapper socketWrapper;

    public ReadThreadClient(SocketWrapper socketWrapper) {
        this.socketWrapper = socketWrapper;
        this.thr = new Thread(this);
        this.thr.start();
    }



    public void run() {
        try {
            while(true) {
                Object o = this.socketWrapper.read();
                if (o instanceof Message) {
                    Message obj = (Message)o;
                   // PrintStream var10000 = System.out;
                    String var10001 = obj.getFrom();
                    System.out.println(var10001 + ", " + obj.getTo() + ", " + obj.getText());
                }
                if(o instanceof  String){
                    System.out.println("Received: "+(String)o);
                }
                if(o instanceof List){
                   // if((((List<?>) o).get(0)) instanceof Movie)
                    List<Movie> ml=(List<Movie>) o;
                    Movielist.printMovieList(ml);

                }
            }
        } catch (Exception var10) {
            System.out.println(var10);
        } finally {
            try {
                this.socketWrapper.closeConnection();
            } catch (IOException var9) {
                var9.printStackTrace();
            }

        }

    }
}
*/

public class ReadThreadClient implements Runnable {
    private Thread thr;
    private SocketWrapper socketWrapper;


    private final HelloApplication main;

    private Production_Company production_company;

    public ReadThreadClient(HelloApplication main) {
        this.main = main;
        this.thr = new Thread(this);
        this.thr.start();
        socketWrapper=main.getNetworkUtil();
        production_company=main.getMyProductionCompany();
    }

    public void run() {
        try {
            while(true) {
                Object o = this.socketWrapper.read();
                if (o instanceof Message) {
                    Message obj = (Message)o;
                    // PrintStream var10000 = System.out;
                    String var10001 = obj.getFrom();
                    //System.out.println(var10001 + ", " + obj.getTo() + ", " + obj.getText());
                }
                if(o instanceof  String){
                    String str=(String)o;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                            // main.setLoginStatusTextofHelloController("Incorrect Password");
                            main.getMyHomePage().setWarningMessage(str);

                        }
                    });




                }
                if(o instanceof List) {
                    // if((((List<?>) o).get(0)) instanceof Movie)
                    List<Movie> ml = (List<Movie>) o;
                    System.out.println("read the list by client : ");
                    Movielist.printMovieList(ml);
                    //System.out.println(ml.get(0).getProd_comp());
                    // String upk=((ml.get(0)).getProd_comp()).toUpperCase();
                    //System.out.println(upk);
                    //  Production_Company pc=Production_Company.getProducer(pcname);

                    // Production_Company pc=null;
                    //if(Production_Company.isProductionCompany(upk)) {
                    Production_Company pc = main.getMyProductionCompany();

                    List<Movie> finalMl = ml;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                            // main.setLoginStatusTextofHelloController("Incorrect Password");
                            main.updateOurMoviesList(finalMl);

                        }
                    });





                    if (pc != null) {
                        System.out.println("Name of Company : ");
                        System.out.println(pc.getName());
                        System.out.println("Total Movies : " + ml.size());
                        System.out.println("Profit : " + pc.showTotalProfit());
                        System.out.println("Most Recent Movie : \n\n");
                        ml = pc.showMostRecent();
                        Movielist.printMovieList(ml);
                        System.out.println("Most Max Revenue Movie : \n\n");
                        ml = pc.showMaxRevenue();
                        Movielist.printMovieList(ml);
                    } else {
                        System.out.println("Trouble finding Production Company");

                    }

                }
                if (o instanceof LoginApproval) {
                    LoginApproval loginApproval = (LoginApproval) o;
                    Production_Company pc = null;
                    if (loginApproval.isFound() && loginApproval.isMatched()) {
                        pc = loginApproval.getPc();
                        // main.setMyProductionCompany(pc);
                        main.setMyProductionCompany(pc);
                        this.production_company=pc;
                        System.out.println("Login Approval Found  : ,assigned :"+pc.getName());
                        Production_Company prod_comp=loginApproval.getPc();

                        System.out.println("written from server :");
                        System.out.println("Name of Company : ");
                        System.out.println(prod_comp.getName());
                        System.out.println("Total Movies : " + prod_comp.getOwnMovies().size());
                        System.out.println("All movies : ");
                       List<Movie> ml2=prod_comp.getOwnMovies();
                        Movielist.printMovieList(ml2);
                        System.out.println("Profit : " + prod_comp.showTotalProfit());
                        System.out.println("Most Recent Movie : \n\n");
                        ml2 = prod_comp.showMostRecent();
                        Movielist.printMovieList(ml2);
                        System.out.println("Most Max Revenue Movie : \n\n");
                        ml2= prod_comp.showMaxRevenue();
                        Movielist.printMovieList(ml2);
                       // main.showHomePage(pc);

                       // Production_Company finalPc = pc;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                             //   if (loginDTO.isStatus()) {
                                    try {
                                        main.showHomePage(production_company);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                               /* } else {
                                    main.showAlert();
                                }*/

                            }
                        });



                    } else if (loginApproval.isFound() == true && loginApproval.isMatched() == false) {



                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                                main.setLoginStatusTextofHelloController("Incorrect Password");
                            }
                        });



                        //LoginStatus.setText("Incorrect Password");
                    } else if (loginApproval.isFound() == false) {
                        //LoginStatus.setText("Invalid Username");
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                                main.setLoginStatusTextofHelloController("Invalid Username");
                            }
                        });
                    }
                }
                if(o instanceof Production_Company){
                    Production_Company prod_comp1=(Production_Company) o;
                    System.out.println("Received : ");

                    System.out.println("Read By Client server :");
                    System.out.println("Name of Company : ");
                    System.out.println(prod_comp1.getName());
                    System.out.println("Total Movies : " + prod_comp1.getOwnMovies().size());
                    System.out.println("All movies it have : ");
                    List<Movie> ml=prod_comp1.getOwnMovies();
                    Movielist.printMovieList(ml);
                    System.out.println("Profit : " + prod_comp1.showTotalProfit());
                    System.out.println("Most Recent Movie : \n\n");
                     ml = prod_comp1.showMostRecent();
                    Movielist.printMovieList(ml);
                    System.out.println("Most Max Revenue Movie : \n\n");
                    ml = prod_comp1.showMaxRevenue();
                    Movielist.printMovieList(ml);


                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                           // main.setLoginStatusTextofHelloController("Incorrect Password");
                            main.setMyProductionCompany(prod_comp1);
                            main.refreshHome(prod_comp1);
                        }
                    });

                }
                if (o instanceof UpdateProductionCompanyDetails){

                    UpdateProductionCompanyDetails upd=(UpdateProductionCompanyDetails) o;
                    System.out.println("updated size read by client : "+upd.getMyAllMovies().size());
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                            // main.setLoginStatusTextofHelloController("Incorrect Password");
                            main.refreshHome(upd);
                        }
                    });



                }
            }
        }



        catch (Exception var10) {
            System.out.println(var10);
        } finally {
            try {
                this.socketWrapper.closeConnection();
            } catch (IOException var9) {
                var9.printStackTrace();
            }

        }

    }

    public Production_Company getProduction_company() {
        return production_company;
    }

    public void setProduction_company(Production_Company production_company) {
        this.production_company = production_company;
    }
}