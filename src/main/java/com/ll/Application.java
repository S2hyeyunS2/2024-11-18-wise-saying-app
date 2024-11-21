package com.ll;

import com.ll.system.SystemController;
import com.ll.wiseSaying.controller.WiseSayingController;
import com.ll.wiseSaying.repository.WiseSayingRepository;

import java.io.IOException;

public class Application {
    public void run() {
        System.out.println("== 명언 앱 ==");

        SystemController systemController = new SystemController();
        WiseSayingRepository repository = new WiseSayingRepository();
        WiseSayingController wiseSayingController = new WiseSayingController(repository);

        while (true) {
            System.out.print("명령) ");
            String command = Container.getScanner().nextLine().trim();
            Rq rq = new Rq(command);

            switch (rq.getActionCode()) {
                case "종료":
                    systemController.exit();
                    return;
                case "등록":
                    try {
                        wiseSayingController.write();
                    } catch (IOException e) {
                        System.out.println("등록 오류: " + e.getMessage());
                    }
                    break;
                case "목록":
                    try {
                        wiseSayingController.list();
                    } catch (IOException e) {
                        System.out.println("목록 조회 오류: " + e.getMessage());
                    }
                    break;
                case "삭제":
                    try {
                        wiseSayingController.remove(rq);
                    } catch (IOException e) {
                        System.out.println("삭제 오류: " + e.getMessage());
                    }
                    break;
                case "수정":
                    try {
                        wiseSayingController.modify(rq);
                    } catch (IOException e) {
                        System.out.println("수정 오류: " + e.getMessage());
                    }
                    break;
                case "빌드":
                    try{
                        wiseSayingController.build();
                    } catch (IOException e){
                        System.out.println("빌드 오류: "+ e.getMessage());
                    }
            }
        }
    }
}
