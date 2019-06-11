package com.truelaurel.recommend;

import software.amazon.awssdk.services.dynamodb.datamodeling.DynamoDbDocument;

import java.util.List;
import java.util.Objects;

@DynamoDbDocument
public class Post {
    private String title;
    private String permalink;
    private List<String> tags;
    private String updated;

    public Post() {
    }

    public Post(String title, String permalink, List<String> tags, String updated) {
        this.title = title;
        this.permalink = permalink;
        this.tags = tags;
        this.updated = updated;
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

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(title, post.title) &&
                Objects.equals(permalink, post.permalink) &&
                Objects.equals(tags, post.tags) &&
                Objects.equals(updated, post.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, permalink, tags, updated);
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", permalink='" + permalink + '\'' +
                ", tags=" + tags +
                ", updated='" + updated + '\'' +
                '}';
    }
}
