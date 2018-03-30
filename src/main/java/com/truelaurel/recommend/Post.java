package com.truelaurel.recommend;

import software.amazon.awssdk.services.dynamodb.datamodeling.DynamoDbDocument;

import java.util.List;
import java.util.Objects;

@DynamoDbDocument
public class Post {
    private String title;
    private String permalink;
    private List<String> tags;

    public Post(String title, String permalink, List<String> tags) {
        this.title = title;
        this.permalink = permalink;
        this.tags = tags;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(permalink, post.permalink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permalink);
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
