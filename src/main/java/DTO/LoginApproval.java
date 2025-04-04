package DTO;

import allMovieList.Production_Company;

import java.io.Serializable;

public class LoginApproval implements Serializable {
    private boolean found=false;
    private Production_Company pc=null;
    private boolean matched=false;

    public LoginApproval(boolean found,boolean matched,Production_Company pc){
        this.found=found;
        this.matched=matched;
        this.pc=pc;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public Production_Company getPc() {
        return pc;
    }

    public void setPc(Production_Company pc) {
        this.pc = pc;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }
    @Override
    public String toString(){
        return "Approved :"+isFound()+" Matched : "+isMatched()+"Prodcomp name :"+pc.getName();
    }
}