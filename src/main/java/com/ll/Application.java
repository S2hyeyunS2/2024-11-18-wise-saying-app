package com.ll;

import com.ll.system.SystemController;
import com.ll.wiseSaying.controller.WiseSayingController;

public class Application {
    public void run() {
        System.out.println("== 명언 앱 ==");

        SystemController systemController = new SystemController(); // 객체 생성
        WiseSayingController wiseSayingController = new WiseSayingController();

        while(true){
            System.out.print("명령) "); // 입력 받기
            String command = Container.getScanner().nextLine().trim();
            Rq rq = new Rq(command);

            switch (rq.getActionCode()) {
                case "종료":
                    systemController.exit();
                    return;
                case "등록":
                    wiseSayingController.write();
                    break;
                case "목록":
                    wiseSayingController.list();
                    break;
                case "삭제":
                    wiseSayingController.remove(rq);
                    break;
                case "수정":
                    wiseSayingController.modify(rq);
                    break;
            }
        }
    }
}


