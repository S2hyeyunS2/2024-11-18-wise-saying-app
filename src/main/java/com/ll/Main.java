package com.ll;

public class Main { // Applicaiton이 일하도록 함
    public static void main(String[] args) {
        Container.init();
        new Application().run();
        Container.close();
    }
}
