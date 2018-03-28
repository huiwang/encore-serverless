package com.truelaurel.recommend;

import java.util.List;

public class Site {
    private String domain;
    private List<Post> posts;


    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }


    @Override
    public String toString() {
        return "Site{" +
                "domain='" + domain + '\'' +
                ", posts=" + posts +
                '}';
    }
}
