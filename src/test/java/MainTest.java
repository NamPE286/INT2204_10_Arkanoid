import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {
    @Test
    @DisplayName("add test")
    public void add() {
        assertEquals(1 + 2, 3);
    }

    @Test
    @DisplayName("subtract test")
    public void subtract() {
        assertEquals(1 - 2, -1);
    }
}
