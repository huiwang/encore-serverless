package com.truelaurel.recommend;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EngineTest {

    private Post post11 = new Post("hello world", "http://hui-wang.info/hello-world.html", Arrays.asList("hello", "world"), "time1");
    private Post post12 = new Post("hello france", "http://hui-wang.info/hello-france.html", Arrays.asList("hello", "france"), "time2");
    private Site site1 = new Site("hui-wang.info", Arrays.asList(post11, post12));
    private Post post21 = new Post("hello paris", "http://li-ming.info/hello-paris.html", Arrays.asList("hello", "paris", "world"), "time1");
    private Post post22 = new Post("goodbye london", "http://li-ming.info/goodbye-london.html", Arrays.asList("goodbye", "london"), "time2");
    private Site site2 = new Site("ming-li.info", Arrays.asList(post21, post22));

    @Test
    public void testSite1() throws Exception {
        Engine engine = new Engine(site1, Collections.singletonList(site2));
        Map<Post, List<Post>> result = new HashMap<>();
        result.put(post11, Arrays.asList(post12, post21));
        result.put(post12, Arrays.asList(post11, post21));

        Assert.assertEquals(result, engine.recommend(1, 2));
    }

    @Test
    public void testSite2() throws Exception {
        Engine engine = new Engine(site2, Collections.singletonList(site1));
        Map<Post, List<Post>> result = new HashMap<>();
        result.put(post21, Collections.singletonList(post12));
        result.put(post22, Collections.emptyList());

        Map<Post, List<Post>> actual = engine.recommend(1, 1);
        Assert.assertEquals(result, actual);
    }
}