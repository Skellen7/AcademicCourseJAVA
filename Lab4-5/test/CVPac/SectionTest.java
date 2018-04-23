package CVPac;

import org.junit.Test;

import static org.junit.Assert.*;

public class SectionTest {
    @Test
    public void setTitle() throws Exception {
        Section section = new Section();
        section.setTitle("test-title");
        assertEquals("test-title",section.title_);
    }

    @Test
    public void addParagraph() throws Exception {
    }

    @Test
    public void addParagraph1() throws Exception {
    }

    @Test
    public void writeHTML() throws Exception {

    }

    @Test
    public void addListItem() throws Exception {
    }

}