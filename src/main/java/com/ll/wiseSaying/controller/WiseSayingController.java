package com.ll.wiseSaying.controller;

import com.ll.Container;
import com.ll.Rq;
import com.ll.wiseSaying.entity.WiseSaying;
import com.ll.wiseSaying.repository.WiseSayingRepository;

import java.io.IOException;

public class WiseSayingController {
    private long lastWiseSayingId;
    private final WiseSayingRepository repository;

    public WiseSayingController(WiseSayingRepository repository) {
        this.repository = repository;
        try {
            this.lastWiseSayingId = repository.load().size(); // 이미 존재하는 명언들의 수를 가져옴
        } catch (IOException e) {
            this.lastWiseSayingId = 0;
        }
    }

    public void write() throws IOException { // 명언 등록
        long id = lastWiseSayingId + 1; // id는 이전 마지막 id+1로 설정
        System.out.print("명언 : ");
        String content = Container.getScanner().nextLine().trim();
        System.out.print("작가 : ");
        String authorName = Container.getScanner().nextLine().trim();

        WiseSaying wiseSaying = new WiseSaying(id, content, authorName);
        repository.add(wiseSaying); // 명언을 저장소에 추가
        System.out.printf("%d번 명언이 등록되었습니다.\n", id);
        lastWiseSayingId = id;
    }

    public void list() throws IOException { // 명언 목록 출력
        System.out.println("번호 / 작가 / 명언");
        System.out.println("-".repeat(30));

        for (WiseSaying wiseSaying : repository.load()) {
            System.out.printf("%d / %s / %s\n", wiseSaying.getId(), wiseSaying.getAuthorName(), wiseSaying.getContent());
        }
    }

    public void remove(Rq rq) throws IOException { // 명언 삭제
        int id = rq.getIntParam("id", -1);
        if (id == -1) {
            System.out.println("id(정수)를 입력해주세요.");
            return;
        }

        repository.remove(id); // 명언 삭제
        System.out.printf("%d번 명언이 삭제되었습니다.\n", id);
    }

    public void modify(Rq rq) throws IOException { // 명언 수정
        int id = rq.getIntParam("id", -1);
        if (id == -1) {
            System.out.println("id(정수)를 입력해주세요.");
            return;
        }

        System.out.printf("수정할 명언 %d번을 찾았습니다.\n", id);

        System.out.print("새 명언 : ");
        String newContent = Container.getScanner().nextLine().trim();

        System.out.print("새 작가 : ");
        String newAuthorName = Container.getScanner().nextLine().trim();

        repository.modify(id, newContent, newAuthorName);
        System.out.printf("%d번 명언이 수정되었습니다.\n", id);
    }
}
