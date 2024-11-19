package com.ll;

public class Main {
    public static void main(String[] args) {
        App app = new App(); //new 다음에 나오는 것은  Class이다.
        app.run();
    }
}

class App{
    public void run(){
        System.out.println("==명언 앱 ==");
        System.out.print("명령) ");
    }
}