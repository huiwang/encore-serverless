package com.truelaurel.recommend;

import software.amazon.awssdk.services.dynamodb.datamodeling.DynamoDbHashKey;
import software.amazon.awssdk.services.dynamodb.datamodeling.DynamoDbTable;

import java.time.ZonedDateTime;
import java.util.List;

@DynamoDbTable(tableName = "encore-prod-site")
public class Site {

    @DynamoDbHashKey
    private String domain;
    private List<Post> posts;
    private long ttl = ZonedDateTime.now().plusDays(30).toEpochSecond();

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

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    @Override
    public String toString() {
        return "Site{" +
                "domain='" + domain + '\'' +
                ", posts=" + posts +
                ", ttl=" + ttl +
                '}';
    }
}
