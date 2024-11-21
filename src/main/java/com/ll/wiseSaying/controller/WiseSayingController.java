package com.ll.wiseSaying.controller;

import com.ll.Container;
import com.ll.Rq;
import com.ll.wiseSaying.service.WiseSayingService;

import java.io.IOException;

public class WiseSayingController {

    private final WiseSayingService wiseSayingService;

    public WiseSayingController(WiseSayingService wiseSayingService) {
        this.wiseSayingService = wiseSayingService;
    }

    public void write() throws IOException { // 명언 등록
        System.out.print("명언 : ");
        String content = Container.getScanner().nextLine().trim();
        System.out.print("작가 : ");
        String authorName = Container.getScanner().nextLine().trim();

        wiseSayingService.create(content, authorName); // Service 호출
    }

    public void list() throws IOException { // 명언 목록 출력
        wiseSayingService.list(); // Service 호출
    }

    public void remove(Rq rq) throws IOException { // 명언 삭제
        int id = rq.getIntParam("id", -1);
        wiseSayingService.remove(id); // Service 호출
    }

    public void modify(Rq rq) throws IOException { // 명언 수정
        int id = rq.getIntParam("id", -1);
        String newContent = Container.getScanner().nextLine().trim();
        String newAuthorName = Container.getScanner().nextLine().trim();

        wiseSayingService.modify(id, newContent, newAuthorName); // Service 호출
    }

    public void build() throws IOException {
        wiseSayingService.build(); // Service 호출
    }
}
