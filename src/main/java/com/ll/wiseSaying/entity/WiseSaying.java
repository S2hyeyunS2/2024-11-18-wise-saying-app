package com.ll.wiseSaying.entity;

public class WiseSaying {
    private long id;
    private String content;
    private String authorName;

    public WiseSaying() {

    }

    public WiseSaying(long id, String content, String authorName){
        this.id=id;
        this.content=content;
        this.authorName=authorName;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setContent(String content){
        this.content=content;
    }
    public void setAuthorName(String authorName){
        this.authorName=authorName;
    }

    public void setId(long id) {
        this.id = id;
    }
}
