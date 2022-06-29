import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("127.0.0.1", 8000);
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        dos.writeUTF("sign in\nusername:user2,,password:123");
        dos.flush();
        System.out.println(dis.readUTF());
        dos.close();
        dis.close();
        socket.close();
    }
}
