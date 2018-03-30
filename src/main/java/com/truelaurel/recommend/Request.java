package com.truelaurel.recommend;

public class Request {
    private int internal;
    private int external;
    private Site site;

    public int getInternal() {
        return internal;
    }

    public void setInternal(int internal) {
        this.internal = internal;
    }

    public int getExternal() {
        return external;
    }

    public void setExternal(int external) {
        this.external = external;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    @Override
    public String toString() {
        return "Request{" +
                "internal=" + internal +
                ", external=" + external +
                ", site=" + site +
                '}';
    }
}
