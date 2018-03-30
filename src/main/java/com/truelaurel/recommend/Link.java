package com.truelaurel.recommend;

public class Link {
    private String title;
    private String permalink;

    public Link() {
    }

    public Link(String title, String permalink) {
        this.title = title;
        this.permalink = permalink;
    }

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


    @Override
    public String toString() {
        return "Link{" +
                "title='" + title + '\'' +
                ", permalink='" + permalink + '\'' +
                '}';
    }
}
