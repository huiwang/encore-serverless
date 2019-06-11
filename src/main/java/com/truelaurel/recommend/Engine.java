package com.truelaurel.recommend;

import java.util.*;
import java.util.stream.Collectors;

public class Engine {

    private final Site hostSite;
    private Map<Post, List<Post>> internalPostMap;
    private Map<Post, List<Post>> externalPostMap;

    Engine(Site hostSite, List<Site> siteIterator) {
        this.hostSite = hostSite;
        Map<String, List<Post>> internalTagMap = new HashMap<>();
        buildTagMap(hostSite, internalTagMap);
        Map<String, List<Post>> externalTagMap = new HashMap<>();
        siteIterator.forEach(site -> buildTagMap(site, externalTagMap));
        internalPostMap = buildPostMapWithSameTag(hostSite, internalTagMap);
        externalPostMap = buildPostMapWithSameTag(hostSite, externalTagMap);
    }

    private void buildTagMap(Site site, Map<String, List<Post>> map) {
        for (Post post : site.getPosts()) {
            for (String tag : post.getTags()) {
                map.computeIfAbsent(tag, t -> new ArrayList<>()).add(post);
            }
        }
    }

    /**
     * if a post shares multiple tags, it will appear multiple times in the post map
     */
    private Map<Post, List<Post>> buildPostMapWithSameTag(Site site, Map<String, List<Post>> tagMap) {
        Map<Post, List<Post>> postMap = new HashMap<>();
        for (Post post : site.getPosts()) {
            for (String tag : post.getTags()) {
                List<Post> withTagPosts = tagMap.getOrDefault(tag, new ArrayList<>());
                for (Post withTagPost : withTagPosts) {
                    if (!post.equals(withTagPost)) {
                        postMap.computeIfAbsent(post, p -> new ArrayList<>()).add(withTagPost);
                    }
                }
            }
        }
        return postMap;
    }

    public Map<Post, List<Post>> recommend(int internal, int external) {
        Map<Post, List<Post>> result = hostSite.getPosts().stream().collect(Collectors.toMap(p -> p, p -> new ArrayList<>()));
        for (Map.Entry<Post, List<Post>> entry : result.entrySet()) {
            Post post = entry.getKey();
            List<Post> posts = entry.getValue();
            posts.addAll(pickPosts(internalPostMap.getOrDefault(post, new ArrayList<>()), internal));
            posts.addAll(pickPosts(externalPostMap.getOrDefault(post, new ArrayList<>()), external));
        }
        return result;
    }

    private List<Post> pickPosts(List<Post> from, int size) {
        return from
                .stream()
                .sorted(Comparator.comparing(Engine::getUpdated).reversed())
                .limit(size)
                .collect(Collectors.toList());
    }

    private static String getUpdated(Post post) {
        if (post.getUpdated() == null) {
            return "1970";
        } else {
            return post.getUpdated();
        }
    }

}
