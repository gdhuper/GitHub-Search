package com.aerofs.takehometest;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void testGetUserBio(){
        UserBioActivity validUser = JsonUtility.getUserBio("aerofs");
        assertEquals("AeroFS", validUser.getName());

        UserBioActivity invalidUser = JsonUtility.getUserBio("dfsdfsd");
        assertEquals("", invalidUser.getName());
    }


    @Test
    public void testGetRepos(){
        //for valid user
        ArrayList<RepoListItem> arr = JsonUtility.getRepos("aerofs");
        assertNotNull(arr);

        //for invalid user
        ArrayList<RepoListItem> invalid = JsonUtility.getRepos("sfsfsfd");
        assertNotNull(invalid);
    }
}