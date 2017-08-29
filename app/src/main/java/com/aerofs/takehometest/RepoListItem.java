package com.aerofs.takehometest;
import java.util.*;
import java.io.*;
/**
 * Created by gurpreet on 8/28/17.
 */

public class RepoListItem {
    private String repoName;

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    private String lastUpdate;
    private String numStars;
    private String numForks;
    private String numWatchers;
    private String numContributors;
    private String language;
    private String colorCode;
   // private String repoUrl;


    public RepoListItem(String repoName, String lastUpdate, String numStars, String numForks, String numWatchers, String numContributors, String language, String colorCode){
        this.repoName = repoName;
        this.lastUpdate = lastUpdate;
        this.numStars = numStars;
        this.numForks = numForks;
        this.numWatchers = numWatchers;
        this.numContributors = numContributors;
        this.language = language;
        this.colorCode = colorCode;
        //this.repoUrl = repoUrl;

    }



    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getNumStars() {
        return numStars;
    }

    public void setNumStars(String numStars) {
        this.numStars = numStars;
    }

    public String getNumForks() {
        return numForks;
    }

    public void setNumForks(String numForks) {
        this.numForks = numForks;
    }

    public String getNumWatchers() {
        return numWatchers;
    }

    public void setNumWatchers(String numWatchers) {
        this.numWatchers = numWatchers;
    }

    public String getNumContributors() {
        return numContributors;
    }

    public void setNumContributors(String numContributors) {
        this.numContributors = numContributors;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}
