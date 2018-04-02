package com.truelaurel.recommend;

import java.util.HashMap;
import java.util.Map;

public class Validator {
    static void validateRequest(Request request) {
        if (request.getInternal() < 1) {
            throw new IllegalArgumentException("internal link must be greater or equal to 1. internal=" + request.getInternal());
        }
        if (request.getExternal() < 0) {
            throw new IllegalArgumentException("external link must be positive. external=" + request.getExternal());
        }
        detectDuplicates(request.getSite());
    }

    static void detectDuplicates(Site site) {
        Map<String, Post> posts = new HashMap<>();
        for (Post post : site.getPosts()) {
            if (!posts.containsKey(post.getPermalink())) {
                posts.put(post.getPermalink(), post);
            } else {
                throw new IllegalArgumentException(
                        "Duplicated posts detected between " + posts.get(post.getPermalink()) + " and " + post);
            }
        }
    }
}
