package Classes;

import java.util.ArrayList;

public class Community {
    private String name;
    private String description;
    private ArrayList<Person> members;
    private ArrayList<Post> posts;
    private ArrayList<Person> admins;
    private ArrayList<Person> banned;

    public Community(String name, String description, Person admin) {
        this.name = name;
        this.description = description;
        this.members = new ArrayList<Person>();
        this.posts = new ArrayList<Post>();
        this.admins = new ArrayList<Person>();
        this.admins.add(admin);
        members.add(admin);
        this.banned = new ArrayList<Person>();
        admin.getCommunities().add(this);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Person> getAdmins() {
        return admins;
    }

    public ArrayList<Person> getBanned() {
        return banned;
    }

    public ArrayList<Person> getMembers() {
        return members;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }
}
