package com.ll.wiseSaying.entity;

public class WiseSaying { // 등록된 명언과 작가이름을 저장
    private long id; // 등록 번호
    private String content; // 명언
    private String authorName; // 작가 이름

    public WiseSaying(long id, String content, String authorName) {
        this.id = id;
        this.content = content;
        this.authorName = authorName;
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

    public void setContent(String content) {
        this.content = content;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
