package com.aerofs.takehometest;
import java.util.*;
import java.io.*;
/**
 * Created by gurpreet on 8/28/17.
 */

public class UserBioActivity {
    private String avatar_url;
    private String name;
    private String userName;
    private String location;
    private String email;
    private String blogUrl;
    private String url;

    public UserBioActivity(String avatar_url, String name, String userName, String location, String email, String blogUrl, String url){
        this.avatar_url = avatar_url;
        this.name = name;
        this.userName = userName;
        this.location = location;
        this.email = email;
        this.blogUrl = blogUrl;
        this.url = url;


    }

    //Getters and Setters//

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBlogUrl() {
        return blogUrl;
    }

    public void setBlogUrl(String blogUrl) {
        this.blogUrl = blogUrl;
    }


}
