package com.truelaurel.recommend;

import org.junit.Test;

import java.util.Arrays;

public class ValidatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void detectDuplicates() {
        Post post11 = new Post("hello world", "http://hui-wang.info/duplicate.html", Arrays.asList("hello", "world"), "time1");
        Post post12 = new Post("hello france", "http://hui-wang.info/duplicate.html", Arrays.asList("hello", "france"), "time2");
        Site site1 = new Site("hui-wang.info", Arrays.asList(post11, post12));
        Validator.detectDuplicates(site1);
    }
}