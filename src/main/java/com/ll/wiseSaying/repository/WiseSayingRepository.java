package com.ll.wiseSaying.repository;

import com.ll.wiseSaying.entity.WiseSaying;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WiseSayingRepository {
    private static final String FILE_NAME = "wise_sayings.json";
    private ObjectMapper objectMapper;

    public WiseSayingRepository() {
        objectMapper = new ObjectMapper();
    }

    // 파일에서 명언들을 불러오기
    public List<WiseSaying> load() throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>(); // 파일이 없다면 빈 리스트 반환
        }
        return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, WiseSaying.class));
    }

    // 명언을 파일에 저장하기
    public void save(List<WiseSaying> wiseSayings) throws IOException {
        objectMapper.writeValue(new File(FILE_NAME), wiseSayings);
    }

    // 명언을 하나 추가
    public void add(WiseSaying wiseSaying) throws IOException {
        List<WiseSaying> wiseSayings = load();
        wiseSayings.add(wiseSaying);
        save(wiseSayings);
    }

    // 명언 삭제
    public void remove(long id) throws IOException {
        List<WiseSaying> wiseSayings = load();
        wiseSayings.removeIf(wiseSaying -> wiseSaying.getId() == id);
        save(wiseSayings);
    }

    // 명언 수정
    public void modify(long id, String newContent, String newAuthorName) throws IOException {
        List<WiseSaying> wiseSayings = load();
        for (WiseSaying wiseSaying : wiseSayings) {
            if (wiseSaying.getId() == id) {
                wiseSaying.setContent(newContent);
                wiseSaying.setAuthorName(newAuthorName);
                save(wiseSayings);
                break;
            }
        }
    }
}
