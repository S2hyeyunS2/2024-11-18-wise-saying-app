import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WiseSayingControllerTest {

    @Test
    void testFindWiseSayingById(){
        WiseSayingController controller=new WiseSayingController("data.json");
        controller.loadData();

        WiseSaying result = controller.findById(6);

        asserNotNull(result);
        assertEquals("현재를 사랑하라", result.getContent());
        assertEquals("혜윤",result.getAuthorName());
    }

   @Test
    void testAddWiseSaying() {
       WiseSayingController controller = new WiseSayingController("data.json");
       controller.loadData();

       WiseSaying newWiseSaying = new WiseSaying(9, "새로운 명언", "테스터");
       controller.addWiseSaying(newWiseSaying);

       WiseSaying result = controller.findById(9);
       assertNotNull(result);
       assertEquals("새로운 명언", result.getContent());
       assertEquals("테스터", result.getAuthorName());

   }
}

