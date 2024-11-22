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

    private <T> T readJsonFile(File file, Class<T> clazz) throws IOException {
        try {
            return objectMapper.readValue(file, clazz);
        } catch (IOException e) {
            throw new IOException("파일을 읽는 도중 문제가 발생했습니다: " + file.getName(), e);
        }
    }

    private void writeJsonFile(File file, Object object) throws IOException {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, object);
        } catch (IOException e) {
            throw new IOException("파일을 저장하는 도중 문제가 발생했습니다: " + file.getName(), e);
        }
    }

    public void saveLastId(long lastId) throws IOException {
        Files.writeString(Paths.get(LAST_ID_FILE), String.valueOf(lastId));
    }

    public long loadLastId() {
        try {
            if (!Files.exists(Paths.get(LAST_ID_FILE))) {
                return 0;
            }
            String content = Files.readString(Paths.get(LAST_ID_FILE)).trim();
            return Long.parseLong(content);
        } catch (IOException | NumberFormatException e){
            return 0;
        }
    }

    public List<WiseSaying> load() throws IOException {
        List<WiseSaying> wiseSayings = new ArrayList<>();
        File[] files = new File(BASE_DIR).listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null) return wiseSayings;

        for (File file : files) {
            wiseSayings.add(readJsonFile(file, WiseSaying.class));
        }

        wiseSayings.sort((a, b) -> Long.compare(a.getId(), b.getId()));
        return wiseSayings;
    }

    public void add(WiseSaying wiseSaying) throws IOException {
        File file = new File(BASE_DIR + "/" + wiseSaying.getId() + ".json");
        writeJsonFile(file, wiseSaying);
        saveLastId(wiseSaying.getId());
    }

    public void modify(long id, String newContent, String newAuthorName) throws IOException {
        File file = new File(BASE_DIR + "/" + id + ".json");
        WiseSaying wiseSaying = readJsonFile(file, WiseSaying.class);
        wiseSaying.setContent(newContent);
        wiseSaying.setAuthorName(newAuthorName);
        writeJsonFile(file, wiseSaying);
    }

    public boolean exists(long id) {
        return new File(BASE_DIR + "/" + id + ".json").exists();
    }

    public WiseSaying findById(long id) throws IOException {
        File file = new File(BASE_DIR + "/" + id + ".json");
        if (!file.exists()) {
            return null;
        }
        return readJsonFile(file,WiseSaying.class);
    }

    public void save(List<WiseSaying> wiseSayings) throws IOException {
        for (WiseSaying wiseSaying : wiseSayings) {
            add(wiseSaying);
        }
    }

    public void remove(long id)  {
        File file = new File(BASE_DIR + "/" + id + ".json");
        if(!file.delete()){
            System.out.printf("파일 삭제 실패: %d.json\n",id);
        }
    }

    public void build(List<WiseSaying> wiseSayings) throws IOException {
        File file = new File(BASE_DIR + "/data.json");
        writeJsonFile(file, wiseSayings);
    }
}
