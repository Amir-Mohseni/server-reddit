package network;

import controller.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread {
    private Socket socket;
    ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            String request = dis.readUTF();
            Scanner scanner = new Scanner(request);

            String command = scanner.nextLine();
            String data = scanner.nextLine();

            String response = new Controller().run(command, data);

            dos.writeUTF(response);
            dos.flush();

            dos.close();
            dis.close();
            socket.close();
        } catch (Exception e) {}
    }
}
