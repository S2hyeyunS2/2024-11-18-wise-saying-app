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
        long id = repository.loadLastId() + 1;
        WiseSaying wiseSaying = new WiseSaying(id, content, authorName);
        repository.add(wiseSaying);
        return id;
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
        List<WiseSaying> wiseSayings = repository.load();
        repository.save(wiseSayings);
    }
}
