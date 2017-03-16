package com.githubapp.model;

import java.io.Serializable;

/**
 * Created by Ravi on 20/03/2016.
 */
public class Repository implements Serializable {

    public String name, full_name, watchers, language, avatar_url, description;


    public Repository(String name, String full_name, String language, String watchers, String avatar_url, String description) {
        this.name = name;
        this.full_name = full_name;
        this.language = language;
        this.watchers = watchers;
        this.avatar_url = avatar_url;
        this.description = description;
    }

}
