package common.datetime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SimpleDateEditorTests {

    SimpleDateEditor editor = new SimpleDateEditor();

    @Test
    void testGetAsText() {
        SimpleDate date = new SimpleDate(12, 29, 1977);
        editor.setValue(date);
        assertEquals("December 29, 1977", editor.getAsText());
    }

    @Test
    void testSetAsText() {
        editor.setAsText("December 29, 1977");
        SimpleDate date = (SimpleDate) editor.getValue();
        assertEquals(new SimpleDate(12, 29, 1977), date);
    }

    @Test
    void testSetAsTextBogus() {
        assertThrows(IllegalArgumentException.class, () ->
                editor.setAsText("December 29th, 1977")
        );
    }
}
