package Classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Person implements Serializable {
    private String username;
    private String password;
    private String profilePicture = "empty";
    private boolean isLoggedIn = false;
    private boolean darkMode = false;
    private ArrayList<Community> communities;
    private ArrayList<Post> posts;

    public Person(String username, String password) {
        this.username = username;
        this.password = password;
        communities = new ArrayList<>();
    }

    public Person(String username, String password, String profilePicture) {
        this.username = username;
        this.password = password;
        this.profilePicture = profilePicture;
        communities = new ArrayList<>();
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public ArrayList<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(ArrayList<Community> communities) {
        this.communities = communities;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }
}
