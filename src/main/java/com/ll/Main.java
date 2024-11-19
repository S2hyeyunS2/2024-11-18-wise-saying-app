package com.ll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public final Scanner sc;
    public Main(Scanner sc){
        this.sc = sc;
    }

    public void run() {
        System.out.println("== 명언 앱 ==");

        long lastWiseSayingId = 0; // 제일 겉에 있는 곳에 변수를 선언해야지 장기기억!
        List<WiseSaying> wiseSayings = new ArrayList<>();

        while(true){
            System.out.print("명령) ");
            String command = sc.nextLine().trim();
            // trim() : 혹시 있을지 모를 좌우공백 제거된 버전으로 주세요!
            if(command.equals("종료")) { // 종료가 입력되면 프로그램 종료
                break;
            }
            else if (command.equals("등록")){
                long id= lastWiseSayingId+1;
                System.out.print("명언 : "); // #2 명언과 작가 이름을 입력받기
                String content = sc.nextLine().trim();
                System.out.print("작가 : ");
                String author = sc.nextLine().trim();

                WiseSaying wiseSaying=new WiseSaying(id,content,author);
                wiseSayings.add(wiseSaying);

                System.out.printf("%d번 명언이 등록되었습니다.\n",id); // #3 등록 문구 띄우기
                lastWiseSayingId=id; // #4 등록할 때마다 번호 증가
            }
            else if (command.equals("목록")){
                System.out.println("번호 / 작가 / 명언");
                System.out.println("-".repeat(30));

                for(int i = wiseSayings.size()-1; i>=9; i--){
                    WiseSaying wiseSaying=wiseSayings.get(i);

                    System.out.printf("%d / %s / %s \n", wiseSaying.getId(), wiseSaying.getContent(), wiseSaying.getAuthor());
                }
            }

        }
    }
}