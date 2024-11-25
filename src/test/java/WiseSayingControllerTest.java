import com.ll.wiseSaying.controller.WiseSayingController;
import com.ll.wiseSaying.entity.WiseSaying;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WiseSayingControllerTest {

    @Test
    void testFindWiseSayingById() {
        WiseSayingController controller = new WiseSayingController("data.json");
        controller.loadData();

        WiseSaying result = controller.findById(6);

        assertNotNull(result); // 데이터가 null이 아닌지 확인
        assertEquals("현재를 사랑하라", result.getContent()); // 올바른 데이터 반환 확인
    }
}
