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

    public long create(String content, String authorName) throws IOException {
        long lastId=repository.loadLastId();
        long newId=lastId +1;

        WiseSaying wiseSaying=new WiseSaying(newId,content,authorName);
        return repository.save(wiseSaying);
    }


    public List<WiseSaying> list() throws IOException{
        return repository.load();
    }

    public boolean remove(long id) throws IOException {
        if (!repository.exists(id)) {
            return false;
        }

        repository.remove(id);
        return true;
    }

    public boolean modify(long id, String newContent, String newAuthorName) throws IOException {
        if (!repository.exists(id)) {
            return false;
        }

        repository.modify(id, newContent, newAuthorName);
        return true;
    }

    public void build() throws IOException {
        List<WiseSaying> wiseSayings = repository.load(); // 숫자.json 파일에서 데이터 로드
        repository.build(wiseSayings); // data.json 갱신
    }
}