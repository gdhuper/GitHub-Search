package com.aerofs.takehometest;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.StringBuilderPrinter;
import android.widget.ArrayAdapter;

import org.json.*;
import org.w3c.dom.Text;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.io.*;

import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by gurpreet on 8/28/17.
 */

public class JsonUtility {


    //BASE URLS for APIs
    private final static String REPOS_BASE_URL = "https://api.github.com/users/";
    private final static String REPOS_URL_POSTFIX = "/repos";



    private final static String USER_BIO_API = "https://api.github.com/users/";




    /**
     * Private constructor to prevent from instantiating this utility class since this class only holds utility methods and variables
     */
    private JsonUtility(){

    }

    /**
     * Method to make API call for user bio
     * @param userName username to get bio for
     * @return UserBioActivity object with all the user information
     */
    public static UserBioActivity getUserBio(String userName){
        UserBioActivity user = null;
        String completeUrl = USER_BIO_API + userName;

        //validate url
        URL url = generateValidUrl(completeUrl);

        String jsonResponse = null;

        try {
            jsonResponse = callHttpApi(url);
        }catch (IOException e){
            Log.e("JsonUtility", "API request failed for user info");
        }

        return parseUserBioResponse(jsonResponse);
    }


    /**
     * Helper method to parse Json response for user bio API
     * @param jsonResponse
     * @return UserbioActivity object with all the information
     */
    public static UserBioActivity parseUserBioResponse(String jsonResponse){

        if(TextUtils.isEmpty(jsonResponse)) return null;

        UserBioActivity user = null;

        try {
            JSONObject obj = new JSONObject(jsonResponse);

            String avatar = obj.getString("avatar_url");
            String name = obj.getString("name") == null ? "" : obj.getString("name");
            String url = obj.getString("url");

            String userName = obj.getString("login");

            String loc = obj.getString("location") == null || obj.getString("location") == "" ? " " : obj.getString("location");

            String  email = obj.getString("email") != null? obj.getString("email") : " ";

            String blogUrl = obj.getString("blog") == "" || obj.getString("blog") == null ? " " : obj.getString("blog");
            user = new UserBioActivity(avatar, name, userName, loc, email, blogUrl, url);

            }

        catch (JSONException e) {
            Log.e("JsonUtility", "User Bio API Response could not be parsed", e);
        }

        return user;

    }


    /**
     * Method to get all public repositories for a user
     * @param userName
     * @return
     */
    public static ArrayList<RepoListItem> getRepos(String userName){

        String fullRepoUrl =  REPOS_BASE_URL + userName + REPOS_URL_POSTFIX;
        System.out.println("full url: "+ fullRepoUrl);

        //validating API Url
        URL url = generateValidUrl(fullRepoUrl);

        String jsonResponse = null;

        try {
            jsonResponse = callHttpApi(url); //making GET request for the API
        }catch (IOException e){
            Log.e("JsonUtility", "API request Failed");
        }

        ArrayList<RepoListItem> repoList = parseJsonResponse(jsonResponse); //parse response from API into a list

        return repoList;
    }

    /**
     * Helper method to validate a url
     * @param stringUrl url to be validated
     * @return validated url
     */
    public  static  URL generateValidUrl(String stringUrl){
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("JsonUtility", "Malformed API URL!");
        }
        return url;
    }


    /**
     * HTTP client to make GET request to API
     * @param url API url
     * @return Response from API call
     * @throws IOException
     */
    public static String callHttpApi(URL url) throws  IOException{
        String jsonResponse = "";

        if(url == null){
            return jsonResponse;
        }
        HttpURLConnection connection = null;
        InputStream inStream = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000 );
            connection.setConnectTimeout(14000);
            connection.setRequestMethod("GET");
            connection.connect();

            if(connection.getResponseCode() == 200){ //on success
                inStream = connection.getInputStream();
                jsonResponse = processInputStream(inStream);
            }else{
                Log.e("JsonUtility", "Error code " + connection.getResponseCode() + " recieved when calling API");
            }
        }catch (IOException e){
            Log.e("JSonUTility", "Error Calling API");
        }finally {
            if(connection != null)
            {
                connection.disconnect(); //closing connection
            }
            if(inStream != null){
                inStream.close(); //closing inputStream
            }
        }

        return jsonResponse;
    }

    /**
     * Read input stream from http request and convert to String for parsing
     * @param stream
     * @return string object for response
     * @throws IOException
     */
    public static String processInputStream(InputStream stream) throws IOException{
        StringBuilder sb = new StringBuilder();
        if(stream != null) {
            InputStreamReader isr = new InputStreamReader(stream, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }

        }
        return sb.toString();
    }

    /**
     * Helper method to parse JSON response from get repositories API
     * @param jsonResponse
     * @return list of RepolistItem objects
     */
    public static ArrayList<RepoListItem> parseJsonResponse(String jsonResponse){

        if(TextUtils.isEmpty(jsonResponse)) return null;

        ArrayList<RepoListItem> list = new ArrayList<>();
        System.out.println("Reponse" + jsonResponse);

        try {
            JSONArray jsonArr = new JSONArray(jsonResponse);

            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject obj = jsonArr.getJSONObject(i);
                JSONObject useBio = obj.getJSONObject("owner");

                String contributors = obj.getString("contributors_url");
                String numContributors = "1";
                String repoName = obj.getString("name");


                String[] lastUpdated = obj.getString("updated_at").split("T");
                String lastUpdate = "Last Updated: " + lastUpdated[0];

                String numWatcher = obj.getString("watchers_count");

                String lang = obj.getString("language");

                String numForks = obj.getString("forks_count");

                String numStars = obj.getString("stargazers_count");


                RepoListItem item = new RepoListItem(repoName, lastUpdate, numStars, numForks, numWatcher, numContributors, lang, lang);
                list.add(item);

            }
        }
        catch (JSONException e) {
            Log.e("JsonUtility", "API Response could not be parsed", e);
        }

        return list;

    }




}
