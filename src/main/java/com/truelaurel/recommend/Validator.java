package com.truelaurel.recommend;

public class Validator {
    static void validateRequest(Request request) {
        if (request.getInternal() < 1) {
            throw new IllegalArgumentException("internal link must be greater or equal to 1. internal=" + request.getInternal());
        }
        if (request.getExternal() < 0) {
            throw new IllegalArgumentException("external link must be positive. external=" + request.getExternal());
        }
    }
}
