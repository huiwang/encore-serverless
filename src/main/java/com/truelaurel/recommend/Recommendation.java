package com.truelaurel.recommend;

import java.util.List;

public class Recommendation {
    private String permalink;
    private List<Link> links;

    public Recommendation(String permalink, List<Link> links) {
        this.permalink = permalink;
        this.links = links;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }


    @Override
    public String toString() {
        return "Recommendation{" +
                "permalink='" + permalink + '\'' +
                ", links=" + links +
                '}';
    }
}
