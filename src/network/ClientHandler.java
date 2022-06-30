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

            StringBuilder request = new StringBuilder();
            int c = dis.read();
            while(c != 0) {
                request.append((char) c);
                c = dis.read();
            }
            Scanner scanner = new Scanner(request.toString());

            String command = scanner.nextLine();
            String data = scanner.nextLine();

            System.out.println("command: " + command);
            System.out.println("data: " + data);

            String response = new Controller().run(command, data);

            dos.writeBytes(response);
            System.out.println("response: " + response);
            dos.flush();

            dos.close();
            dis.close();
            socket.close();
        } catch (Exception e) {}
    }
}
