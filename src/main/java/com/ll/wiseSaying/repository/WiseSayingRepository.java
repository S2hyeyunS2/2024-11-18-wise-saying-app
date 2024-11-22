package com.ll.wiseSaying.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.wiseSaying.entity.WiseSaying;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WiseSayingRepository {
    private final String BASE_DIR = "db/wiseSaying";
    private final String LAST_ID_FILE = BASE_DIR + "/lastId.txt";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WiseSayingRepository() {
        ensureBaseDirectoryExists();
    }

    private void ensureBaseDirectoryExists() {
        File baseDir = new File(BASE_DIR);
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }
    }

    public void saveLastId(long lastId) throws IOException {
        Files.writeString(Paths.get(LAST_ID_FILE), String.valueOf(lastId));
    }

    public long loadLastId() throws IOException {
        if (!Files.exists(Paths.get(LAST_ID_FILE))) {
            return 0;
        }
        String content = Files.readString(Paths.get(LAST_ID_FILE)).trim();
        return Long.parseLong(content);
    }

    public List<WiseSaying> load() throws IOException {
        List<WiseSaying> wiseSayings = new ArrayList<>();

        File baseDir=new File(BASE_DIR);
        File[] files =baseDir.listFiles((dir,name)-> name.matches("\\d+\\.json"));
        if(files !=null){
            for(File file : files){
                WiseSaying wiseSaying = objectMapper.readValue(file,WiseSaying.class);
                wiseSayings.add(wiseSaying);
            }
        }

        wiseSayings.sort((a,b)-> Long.compare(a.getId(),b.getId()));
        return wiseSayings;
    }

    public void add(WiseSaying wiseSaying) throws IOException {
        File file = new File(BASE_DIR + "/" + wiseSaying.getId() + ".json");
        objectMapper.writeValue(file, wiseSaying);
        saveLastId(wiseSaying.getId());
    }

    public void modify(long id, String newContent, String newAuthorName) throws IOException {
        File file = new File(BASE_DIR + "/" + id + ".json");
        WiseSaying wiseSaying = objectMapper.readValue(file, WiseSaying.class);
        wiseSaying.setContent(newContent);
        wiseSaying.setAuthorName(newAuthorName);
        objectMapper.writeValue(file, wiseSaying);
    }

    public boolean exists(long id) {
        File file = new File(BASE_DIR + "/" + id + ".json");
        return file.exists();
    }

    public WiseSaying findById(long id) throws IOException {
        File file = new File(BASE_DIR + "/" + id + ".json");
        if (!file.exists()) {
            return null;
        }
        return objectMapper.readValue(file, WiseSaying.class);
    }

    public long save(WiseSaying wiseSaying) throws IOException {
        File file = new File(BASE_DIR + "/" + wiseSaying.getId() + ".json");
        objectMapper.writeValue(file, wiseSaying);
        saveLastId(wiseSaying.getId());
        return wiseSaying.getId(); // 저장된 명언의 ID 반환
    }

    public void remove(long id) throws IOException {
        File file = new File(BASE_DIR + "/" + id + ".json");
        file.delete();
    }

    public void build(List<WiseSaying> wiseSayings) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(BASE_DIR + "/data.json"), wiseSayings);
    }

}