package com.aerofs.takehometest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gurpreet on 8/28/17.
 */

public class RepoListAdapter extends ArrayAdapter<RepoListItem> {



    /**
     * custom constructor for array adapter
     * @param context app context
     * @param repos list of public repos for a user
     */
    public RepoListAdapter(Context context, List<RepoListItem> repos) {
        super(context, 0, repos);
    }



    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {


        View listItemView = convertView;

        //if view item exists use it for current repo item
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.repo_list_item, parent,  false);
        }

        //get new repo item from the list
        RepoListItem repo = getItem(position);

        TextView repoName = (TextView) listItemView.findViewById(R.id.repo_name);
        repoName.setText(repo.getRepoName());

        //setting list item attributes for current list item
        TextView lastUpdateView = (TextView) listItemView.findViewById(R.id.last_updated);
        lastUpdateView.setText(repo.getLastUpdate());

        TextView numStarsView = (TextView) listItemView.findViewById(R.id.num_stars);
        numStarsView.setText(repo.getNumStars());

        TextView numForksView = (TextView) listItemView.findViewById(R.id.num_forks);
        numForksView.setText(repo.getNumForks());

        TextView numWatchersView = (TextView) listItemView.findViewById(R.id.num_watchers);
        numWatchersView.setText(repo.getNumWatchers());

        TextView numContributorsView = (TextView) listItemView.findViewById(R.id.num_contributors);
        numContributorsView.setText(repo.getNumContributors());

        TextView languageView = (TextView) listItemView.findViewById(R.id.prog_lang);
        languageView.setText(repo.getLanguage());

        ImageView langColorView = (ImageView) listItemView.findViewById(R.id.lang_color);

        GradientDrawable colorCode = (GradientDrawable) langColorView.getBackground();
        String colorHex = repo.getColorCode();

        //default color code
        int color  = R.color.Java;

        color = setColor(colorHex);

        colorCode.setColor(ContextCompat.getColor(getContext(), color));



        return listItemView;
    }

    /**
     * Helper method to get color index based on programming language
     * @param colorHex
     * @return
     */
    public static int  setColor( String colorHex){
        int color;
        if(colorHex.equals("Java")){
            color = R.color.Java;
        }
        else if(colorHex.equals("Python")){
            color = R.color.Python;
        }
       else if(colorHex.equals("CSS")){
            color = R.color.CSS;
        }else if(colorHex.equals("C")){
            color = R.color.C;
        }else if(colorHex.equals("Swift")){
            color = R.color.Swift;
        }else if(colorHex.equals("HTML")){
            color = R.color.HTML;
        }else if(colorHex.equals("CoffeeScript")){
            color = R.color.CoffeeScript;
        }
        else if(colorHex.equals("PHP")){
            color = R.color.PHP;
        }
        else if(colorHex.equals("Go")){
            color = R.color.Go;
        }
        else if(colorHex.equals("Ruby")){
            color = R.color.Ruby;
        }else if(colorHex.equals("Shell")){
            color = R.color.Shell;
        }
        else if(colorHex.equals("Jupyter")){
            color = R.color.Jupyter;
        }
        else if(colorHex.equals("TypeScript")){
            color = R.color.TypeScript;
        }
        else{
            color = R.color.Jupyter;
        }

        return color;

    }
}
