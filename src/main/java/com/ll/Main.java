package com.ll;

public class Main { // Applicaiton이 일하도록 함
    public static void main(String[] args) {

        Container.init(); // 공통적으로 사용되는 것들을 모아두는 공간(Container) 초기화

        new Application().run(); // 기본적으로 Scanner를 가지고 있는 Applicaiton

        Container.close();
        // 공통적으로 사용되는 것들을 모아두는 공간(Container) 자원해제
    }
}
