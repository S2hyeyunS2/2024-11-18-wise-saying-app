package com.ll.wiseSaying.service;

import com.ll.wiseSaying.entity.WiseSaying;
import com.ll.wiseSaying.repository.WiseSayingRepository;

import java.io.IOException;
import java.util.List;

public class WiseSayingService {

    private final WiseSayingRepository repository;

    public WiseSayingService(WiseSayingRepository repository) {
        this.repository = repository;
    }

    public void create(String content, String authorName) throws IOException {
        long id = repository.loadLastId() + 1;
        WiseSaying wiseSaying = new WiseSaying(id, content, authorName);
        repository.add(wiseSaying);
        System.out.printf("%d번 명언이 등록되었습니다.\n", id);
    }

    public void list() throws IOException {
        List<WiseSaying> wiseSayings = repository.load();
        if (wiseSayings.isEmpty()) {
            System.out.println("등록된 명언이 없습니다.");
            return;
        }

        System.out.println("번호 / 작가 / 명언");
        System.out.println("-".repeat(30));

        wiseSayings.stream()
                .sorted((a, b) -> Long.compare(b.getId(), a.getId()))
                .forEach(wiseSaying -> System.out.printf("%d / %s / %s\n", wiseSaying.getId(), wiseSaying.getAuthorName(), wiseSaying.getContent()));
    }

    public void remove(long id) throws IOException {
        if (!repository.exists(id)) {
            System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
            return;
        }

        repository.remove(id);
        System.out.printf("%d번 명언이 삭제되었습니다.\n", id);
    }

    public void modify(long id, String newContent, String newAuthorName) throws IOException {
        if (!repository.exists(id)) {
            System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
            return;
        }

        repository.modify(id, newContent, newAuthorName);
        System.out.printf("%d번 명언이 수정되었습니다.\n", id);
    }

    public void build() throws IOException {
        List<WiseSaying> wiseSayings = repository.load();
        repository.save(wiseSayings);
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }
}
