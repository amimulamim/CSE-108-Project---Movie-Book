package util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketWrapper {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public SocketWrapper(String s, int port) throws IOException {
        this.socket = new Socket(s, port);
        this.oos = new ObjectOutputStream(this.socket.getOutputStream());
        this.ois = new ObjectInputStream(this.socket.getInputStream());
    }

    public SocketWrapper(Socket s) throws IOException {
        this.socket = s;
        this.oos = new ObjectOutputStream(this.socket.getOutputStream());
        this.ois = new ObjectInputStream(this.socket.getInputStream());
    }

    public Object read() throws IOException, ClassNotFoundException {
        return this.ois.readUnshared();
    }

    public void write(Object o) throws IOException {
        this.oos.writeUnshared(o);
    }

    public void closeConnection() throws IOException {
        this.ois.close();
        this.oos.close();
    }
}