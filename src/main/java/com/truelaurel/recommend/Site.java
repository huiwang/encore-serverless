package com.truelaurel.recommend;

import software.amazon.awssdk.services.dynamodb.datamodeling.DynamoDbHashKey;
import software.amazon.awssdk.services.dynamodb.datamodeling.DynamoDbTable;

import java.util.List;

@DynamoDbTable(tableName = "encore-dev-site")
public class Site {

    @DynamoDbHashKey
    private String domain;
    private List<Post> posts;

    public Site() {
    }

    public Site(String domain, List<Post> posts) {
        this.domain = domain;
        this.posts = posts;
    }

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
