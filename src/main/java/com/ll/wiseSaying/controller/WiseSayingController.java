package com.ll.wiseSaying.controller;

import com.ll.Container;
import com.ll.Rq;
import com.ll.wiseSaying.entity.WiseSaying;
import com.ll.wiseSaying.service.WiseSayingService;

import java.io.IOException;
import java.util.List;

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

        long id=wiseSayingService.create(content, authorName);
        System.out.printf(id+"번 명언이 등록되었습니다.\n");
    }

    public void list(Rq rq) throws IOException { // 명언 목록 출력
        List<WiseSaying> wiseSayings=wiseSayingService.list();

        String keywordType=rq.getParam("keywordType");
        String keyword=rq.getParam("keyword");

        if (keywordType != null && keyword != null) {
            wiseSayings = wiseSayings.stream()
                    .filter(wiseSaying -> {
                        if (keywordType.equals("content")) {
                            return wiseSaying.getContent().contains(keyword);
                        } else if (keywordType.equals("author")) {
                            return wiseSaying.getAuthorName().contains(keyword);
                        }
                        return false;
                    })
                    .toList();
        }

        if (wiseSayings.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
            return;
        }

        System.out.println("----------------------");
        System.out.printf("검색타입 : %s\n", keywordType != null ? keywordType : "전체");
        System.out.printf("검색어 : %s\n", keyword != null ? keyword : "없음");
        System.out.println("----------------------");
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        wiseSayings.stream()
                .sorted((a, b) -> Long.compare(b.getId(), a.getId()))
                .forEach(wiseSaying -> System.out.printf("%d / %s / %s\n",
                        wiseSaying.getId(), wiseSaying.getAuthorName(), wiseSaying.getContent()));
    }

    public void remove(Rq rq) throws IOException { // 명언 삭제
        int id = rq.getIntParam("id", -1);

        if (!wiseSayingService.remove(id)) { // 삭제 실패
            System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
        } else { // 삭제 성공
            System.out.printf("%d번 명언이 삭제되었습니다.\n", id);
        }
    }

    public void modify(Rq rq) throws IOException { // 명언 수정
        int id = rq.getIntParam("id", -1);
        if (id == -1) {
            System.out.println("수정할 명언의 번호를 입력해주세요.");
            return;
        }

        System.out.print("새 명언 : ");
        String newContent = Container.getScanner().nextLine().trim();
        System.out.print("새 작가 : ");
        String newAuthorName = Container.getScanner().nextLine().trim();

        if (!wiseSayingService.modify(id, newContent, newAuthorName)) { // 수정 실패
            System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
        } else { // 수정 성공
            System.out.printf("%d번 명언이 수정되었습니다.\n", id);
        }
    }

    public void build() throws IOException {
        wiseSayingService.build(); // Service
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");// 호출
    }
}