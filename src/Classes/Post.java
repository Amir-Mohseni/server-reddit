package Classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Post implements Serializable {
    private String title;
    private String content;
    private Person creator;
    private Community community;
    private Date createdAt;
    private String image;
    ArrayList<Person> likes;
    ArrayList<Person> dislikes;
    ArrayList<Comment> comments;

    public Post(String title, String content, Person creator, Date createdAt, Community community, String image) {
        this.title = title;
        this.content = content;
        this.creator = creator;
        this.community = community;
        this.createdAt = createdAt;
        this.likes = new ArrayList<Person>();
        this.dislikes = new ArrayList<Person>();
        this.comments = new ArrayList<Comment>();
        this.image = image;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public Community getCommunity() {
        return community;
    }

    public ArrayList<Person> getLikes() {
        return likes;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public ArrayList<Person> getDislikes() {
        return dislikes;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public String getPostKey() {
        return this.title + "/" + this.content + "/" + this.creator.getUsername();
    }
}
