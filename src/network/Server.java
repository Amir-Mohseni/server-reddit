package network;

import Classes.Comment;
import Classes.Community;
import Classes.Person;
import Classes.Post;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    public static String userPath = "src/Users";
    public static String communityPath = "src/Communities";
    public static String postPath = "src/Posts";
    public static String commentPath = "src/Comments";
    public static int numberOfClients = 0;
    public static ArrayList <Person> users = new ArrayList<Person>();
    public static HashMap<String,Integer> personPosition = new HashMap<>(); //<username,position>
    public static ArrayList<Community> communities = new ArrayList<Community>();
    public static HashMap<String,Integer> communityPosition = new HashMap<>(); //<community name,position>
    public static ArrayList<Post> posts = new ArrayList<Post>();
    public static HashMap<String,Integer> postPosition = new HashMap<>(); //<title + content + author,position>
    public static int numberOfPeople = 0, numberOfPosts = 0, numberOfCommunities = 0;
    public static String message;

    public void start() throws Exception {
        ServerSocket ss = new ServerSocket(8000);
        while(true) {
            Socket socket = ss.accept();
            new ClientHandler(socket).start();
        }
    }

    private static void loadData() throws Exception {
        int numberOfUsers = new File(userPath).listFiles().length;
        int numberOfCommunities = new File(communityPath).listFiles().length;
        int numberOfPosts = new File(postPath).listFiles().length;

        for (int i = 0; i < numberOfUsers; i++) {
            File file = new File(userPath + "/user" + i + ".txt");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Person p = (Person) ois.readObject();
            users.add(p);
            personPosition.put(p.getUsername(), i);
        }

        for (int i = 0; i < numberOfCommunities; i++) {
            File file = new File(communityPath + "/community" + i + ".txt");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Community c = (Community) ois.readObject();
            communities.add(c);
            communityPosition.put(c.getName(), i);
        }

        for (int i = 0; i < numberOfPosts; i++) {
            File file = new File(postPath + "/post" + i + ".txt");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Post p = (Post) ois.readObject();
            posts.add(p);
        }
    }
}
