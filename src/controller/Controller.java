package controller;

import Classes.Comment;
import Classes.Community;
import Classes.Person;
import Classes.Post;
import network.Server;
import utils.Convertor;

import java.io.*;
import java.util.Date;
import java.util.HashMap;

/* List of commands
        ban user
        unban user
     */

public class Controller {
    public String run(String command, String data) throws IOException {
        HashMap<String, String> dataMap = Convertor.StringToMap(data);
        switch (command) {
            case "sign in":
                return signIn(dataMap);
            case "sign up":
                return signUp(dataMap);
            case "create community":
                return createCommunity(dataMap);
            case "join community":
                return joinCommunity(dataMap);
            case "add post":
                return addPost(dataMap);
            case "like post":
                return likePost(dataMap);
            case "dislike post":
                return dislikePost(dataMap);
            case "add admin":
                return addAdmin(dataMap);
            case "add comment":
                return addComment(dataMap);
            case "remove comment":
                return removeComment(dataMap);
            case "set theme":
                return setTheme(dataMap);
            case "change username":
                return changeUsername(dataMap);
            case "change password":
                return changePassword(dataMap);
            case "change profilePicture":
                return changeProfilePicture(dataMap);
            default:
                return "Invalid command";
        }
    }

    public String signIn(HashMap<String, String> dataMap) {
        Person person;
        String username = dataMap.get("username");
        String password = dataMap.get("password");
        if (Server.personPosition.containsKey(username)) {
            person = Server.users.get(Server.personPosition.get(username));
            if(person.getPassword().equals(password)) {
               person.setLoggedIn(true);
               return "signIn:" + username + ":success";
            }
        }
        return "signIn:" + username + ":error";
    }

    public String signUp(HashMap<String, String> dataMap) {
        String username = dataMap.get("username");
        String password = dataMap.get("password");
        if (Server.personPosition.containsKey(username)) {
            return "signUp:" + username + ":error";
        }
        Person person = new Person(username, password);
        Server.users.add(person);
        Server.personPosition.put(username, Server.users.size() - 1);
        userToFile(person);
        return "signUp:" + username + ":success";
    }

    public String createCommunity(HashMap<String, String> dataMap) {
        Person admin = Server.users.get(Server.personPosition.get(dataMap.get("username")));
        String communityName = dataMap.get("communityName");
        String communityDescription = dataMap.get("communityDescription");
        if (Server.communityPosition.containsKey(communityName)) {
            return "createCommunity:" + communityName + ":error";
        }
        String communityPicture = dataMap.get("communityPicture");
        Community community = new Community(communityName, communityDescription, admin, communityPicture);
        Server.communities.add(community);
        Server.communityPosition.put(communityName, Server.communities.size() - 1);
        try {
            communityToFile(community);
            return "createCommunity:" + communityName + ":success";
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "createCommunity:" + communityName + ":success";
    }

    public String joinCommunity(HashMap<String, String> dataMap) throws IOException {
        Person person = Server.users.get(Server.personPosition.get(dataMap.get("username")));
        String communityName = dataMap.get("communityName");
        Community community = Server.communities.get(Server.communityPosition.get(communityName));
        if (community.getMembers().contains(person))
            return "joinCommunity:" + communityName + ":error";
        community.getMembers().add(person);
        person.getCommunities().add(community);
        updateUserFile(person);
        updateCommunityFile(community);
        return "joinCommunity:" + communityName + ":success";
    }

    public String addPost(HashMap<String, String> dataMap) throws IOException {
        Person person = Server.users.get(Server.personPosition.get(dataMap.get("username")));
        Community community = Server.communities.get(Server.communityPosition.get(dataMap.get("communityName")));
        String title = dataMap.get("title");
        String content = dataMap.get("content");
        String image = dataMap.get("image");
        Date createdAt = new Date(dataMap.get("createdAt"));
        Post post = new Post(title, content, person, createdAt, community, image);
        community.getPosts().add(post);
        person.getPosts().add(post);
        Server.postPosition.put(post.getPostKey(), Server.posts.size() - 1);
        postToFile(post);
        updateUserFile(person);
        updateCommunityFile(community);
        return "addPost:success";
    }

    public String likePost(HashMap<String, String> dataMap) throws IOException {
        Person person = Server.users.get(Server.personPosition.get(dataMap.get("username")));
        String title = dataMap.get("title");
        String content = dataMap.get("content");
        String postKey = title + "/" + content + "/" + person.getUsername();
        Post post = Server.posts.get(Server.postPosition.get(postKey));
        if (post.getLikes().contains(person)) {
            post.getLikes().remove(person);
        }
        else if(post.getDislikes().contains(person)) {
            post.getDislikes().remove(person);
            post.getLikes().add(person);
        }
        else {
            post.getLikes().add(person);
        }
        updatePostFile(post);
        return "likePost:success";
    }

    public String dislikePost(HashMap<String, String> dataMap) throws IOException {
        Person person = Server.users.get(Server.personPosition.get(dataMap.get("username")));
        String title = dataMap.get("title");
        String content = dataMap.get("content");
        String postKey = title + "/" + content + "/" + person.getUsername();
        Post post = Server.posts.get(Server.postPosition.get(postKey));
        if (post.getDislikes().contains(person)) {
            post.getDislikes().remove(person);
        }
        else if(post.getLikes().contains(person)) {
            post.getLikes().remove(person);
            post.getDislikes().add(person);
        }
        else {
            post.getDislikes().add(person);
        }
        updatePostFile(post);
        return "dislikePost:success";
    }

    public String addAdmin(HashMap<String, String> dataMap) throws IOException {
        //always used when an admin clicks the button
        Person person = Server.users.get(Server.personPosition.get(dataMap.get("username")));
        String communityName = dataMap.get("communityName");
        Community community = Server.communities.get(Server.communityPosition.get(communityName));
        if (!community.getMembers().contains(person) || community.getAdmins().contains(person))
            return "addAdmin:" + communityName + ":error";
        community.getAdmins().add(person);
        updateUserFile(person);
        updateCommunityFile(community);
        return "addAdmin:" + communityName + ":success";
    }

    public String addComment(HashMap<String, String> dataMap) throws IOException {
        Person person = Server.users.get(Server.personPosition.get(dataMap.get("username")));
        String title = dataMap.get("title");
        String content = dataMap.get("content");
        String postKey = title + "/" + content + "/" + person.getUsername();
        Post post = Server.posts.get(Server.postPosition.get(postKey));
        String commentContent = dataMap.get("commentContent");
        Comment comment = new Comment(commentContent, person, post);
        post.getComments().add(comment);
        updateUserFile(person);
        updatePostFile(post);
        return "addComment:success";
    }

    public String removeComment(HashMap<String, String> dataMap) throws IOException {
        Person person = Server.users.get(Server.personPosition.get(dataMap.get("username")));
        String title = dataMap.get("title");
        String content = dataMap.get("content");
        String postKey = title + "/" + content + "/" + person.getUsername();
        Post post = Server.posts.get(Server.postPosition.get(postKey));
        String commentContent = dataMap.get("commentContent");
        Comment comment = new Comment(commentContent, person, post);
        post.getComments().remove(comment);
        updateUserFile(person);
        updatePostFile(post);
        return "removeComment:success";
    }

    public String setTheme(HashMap<String, String> dataMap) throws IOException {
        Person person = Server.users.get(Server.personPosition.get(dataMap.get("username")));
        String theme = dataMap.get("theme");
        //theme == dark or light
        if(theme.equals("dark")) {
            person.setDarkMode(true);
        }
        else {
            person.setDarkMode(false);
        }
        updateUserFile(person);
        return "setTheme:success";
    }

    public String changeUsername(HashMap<String, String> dataMap) throws IOException {
        Person person = Server.users.get(Server.personPosition.get(dataMap.get("username")));
        String newUsername = dataMap.get("newUsername");
        if(Server.personPosition.containsKey(newUsername))
            return "changeUsername:" + newUsername + ":error";
        person.setUsername(newUsername);
        updateUserFile(person);
        return "changeUsername:" + newUsername + ":success";
    }

    public String changePassword(HashMap<String, String> dataMap) throws IOException {
        Person person = Server.users.get(Server.personPosition.get(dataMap.get("username")));
        String newPassword = dataMap.get("newPassword");
        person.setPassword(newPassword);
        updateUserFile(person);
        return "changePassword:success";
    }

    public String changeProfilePicture(HashMap<String, String> dataMap) throws IOException {
        Person person = Server.users.get(Server.personPosition.get(dataMap.get("username")));
        String newProfilePicture = dataMap.get("newProfilePicture");
        person.setProfilePicture(newProfilePicture);
        updateUserFile(person);
        return "changeProfilePicture:success";
    }



    public static void userToFile(Object object) {
        try {
            File file = new File(Server.userPath + "/user" + Server.numberOfPeople + ".txt");
            if(!file.exists()) {
                file.createNewFile();
                Server.numberOfPeople++;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void communityToFile(Object object) {
        try {
            File file = new File(Server.communityPath + "/community" + Server.numberOfCommunities + ".txt");
            if(!file.exists()) {
                file.createNewFile();
                Server.numberOfCommunities++;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void postToFile(Object object) {
        try {
            File file = new File(Server.postPath + "/post" + Server.numberOfPosts + ".txt");
            if(!file.exists()) {
                file.createNewFile();
                Server.numberOfPosts++;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateUserFile(Object object) throws IOException {
        Person person = (Person) object;
        int index = Server.personPosition.get(person.getUsername());
        File file = new File(Server.userPath + "/user" + index + ".txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(person);
        objectOutputStream.flush();
    }

    public static void updateCommunityFile(Object object) throws IOException {
        Community community = (Community) object;
        int index = Server.communityPosition.get(community.getName());
        File file = new File(Server.communityPath + "/community" + index + ".txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(community);
        objectOutputStream.flush();
    }

    public static void updatePostFile(Object object) throws IOException {
        Post post = (Post) object;
        int index = Server.posts.indexOf(post);
        File file = new File(Server.postPath + "/post" + index + ".txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(post);
        objectOutputStream.flush();
    }

/*

    private String send(HashMap<String, String> data) {
        try {
            Database.getInstance().getTable("messages").insert(data);
            return "message saved successfully";
        }catch (Exception e) {
            return "error saving message";
        }
    }

 */
}
