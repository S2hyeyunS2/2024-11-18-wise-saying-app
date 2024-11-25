import com.ll.Application;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class AppTest {
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream originalOut;
    private InputStream originalIn;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        originalIn = System.in;
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    public String run(String input) {
        System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));
        new Application().run();
        return output.toString().trim();
    }

    public static void clear() {
        // 리소스 초기화 또는 환경 설정 정리
    }
}