package Classes;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Comment implements Serializable {
    private String message;
    private Person creator;
    private Post post;
    private ArrayList<Person> likes;
    private ArrayList<Person> dislikes;

    public Comment(String message, Person creator, Post post) {
        this.message = message;
        this.creator = creator;
        this.post = post;
        this.likes = new ArrayList<Person>();
        this.dislikes = new ArrayList<Person>();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    public Person getCreator() {
        return creator;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Post getPost() {
        return post;
    }
}
