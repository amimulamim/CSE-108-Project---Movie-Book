package DTO;

import java.io.Serializable;

public class ModifyStatusWarning implements Serializable {

    private boolean add=false;
    private boolean transfer=false;
    private String Message;

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public boolean isTransfer() {
        return transfer;
    }

    public void setTransfer(boolean transfer) {
        this.transfer = transfer;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
