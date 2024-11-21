package com.ll.wiseSaying.controller;

import com.ll.Container;
import com.ll.Rq;
import com.ll.wiseSaying.entity.WiseSaying;
import com.ll.wiseSaying.repository.WiseSayingRepository;

import java.io.IOException;
import java.util.List;

public class WiseSayingController {

    private final WiseSayingRepository repository;

    public WiseSayingController(WiseSayingRepository repository) {
        this.repository = repository;
    }

    public void write() throws IOException { // 명언 등록
        long id = repository.loadLastId() + 1; // id는 이전 마지막 id+1로 설정
        System.out.print("명언 : ");
        String content = Container.getScanner().nextLine().trim();
        System.out.print("작가 : ");
        String authorName = Container.getScanner().nextLine().trim();

        WiseSaying wiseSaying = new WiseSaying(id, content, authorName);
        repository.add(wiseSaying); // 명언을 저장소에 추가
        System.out.printf("%d번 명언이 등록되었습니다.\n", id);
    }

    public void list() throws IOException { // 명언 목록 출력
        List<WiseSaying> wiseSayings = repository.load(); // 최신 데이터를 가져옴
        if (wiseSayings.isEmpty()) {
            System.out.println("등록된 명언이 없습니다.");
            return;
        }

        System.out.println("번호 / 작가 / 명언");
        System.out.println("-".repeat(30));

        wiseSayings.stream()
                .sorted((a, b) -> Long.compare(b.getId(), a.getId())) // ID 기준 내림차순
                .forEach(wiseSaying ->
                        System.out.printf("%d / %s / %s\n",
                                wiseSaying.getId(),
                                wiseSaying.getAuthorName(),
                                wiseSaying.getContent()));

    }

    public void remove(Rq rq) throws IOException { // 명언 삭제
        int id = rq.getIntParam("id", -1);
        if (id == -1) {
            System.out.println("id(정수)를 입력해주세요.");
            return;
        }

        if (!repository.exists(id)) { // ID가 존재하지 않으면 메시지 출력
            System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
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

        if (!repository.exists(id)) { // ID가 존재하지 않으면 메시지 출력
            System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
            return;
        }

        WiseSaying wiseSaying = repository.findById(id);
        System.out.printf("명언(기존) : %s\n", wiseSaying.getContent());
        System.out.print("새 명언 : ");
        String newContent = Container.getScanner().nextLine().trim();

        System.out.printf("작가(기존) : %s\n", wiseSaying.getAuthorName());
        System.out.print("새 작가 : ");
        String newAuthorName = Container.getScanner().nextLine().trim();

        repository.modify(id, newContent, newAuthorName);
        System.out.printf("%d번 명언이 수정되었습니다.\n", id);
    }
}
