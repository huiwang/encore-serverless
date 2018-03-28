package com.truelaurel.recommend;

import java.util.List;

public class Post {
    private String title;
    private String permalink;
    private List<String> tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }


    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", permalink='" + permalink + '\'' +
                ", tags=" + tags +
                '}';
    }
}
