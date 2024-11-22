package com.ll;

import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

public class Rq {
    private String actionCode;
    private Map<String, String> params;

    public Rq(String command) {
        String[] commandBits = command.split("\\?", 2);
        actionCode = commandBits[0];

        params = new HashMap<>();

        if (commandBits.length == 1) return;

        String[] paramsBits = commandBits[1].split("&");

        // Stream API로 params 맵 초기화
        Arrays.stream(paramsBits)
                .map(paramStr -> paramStr.split("=", 2)) // paramStr를 "=" 기준으로 나눔
                .filter(paramStrBits -> paramStrBits.length == 2) // 길이가 2인 배열만 필터링
                .forEach(paramStrBits -> params.put(paramStrBits[0], paramStrBits[1])); // 키와 값을 맵에 추가
    }

    public String getActionCode() {
        return actionCode;
    }

    public String getParam(String name) {
        return params.get(name);
    }

    public int getIntParam(String name, int defaultValue) {
        try {
            return Integer.parseInt(getParam(name));
        } catch (NumberFormatException e) {
            // 기본값 반환
        }
        return defaultValue;
    }
}
