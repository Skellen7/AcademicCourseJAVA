package CVPac;

import javax.xml.bind.annotation.XmlElement;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class ParagraphWithList extends Paragraph {
    @XmlElement(name="list")
    private UnorderedList UNList;


    ParagraphWithList(){
        UNList = new UnorderedList();
    };

    public ParagraphWithList addListItem(String item){
        ListItem tmp = new ListItem(item);
        UNList.addListItem(tmp);
        return this;
    }

    @Override
    public ParagraphWithList setContent(String content) {
        content_ = content;
        return this;
    }

    @Override
    public void writeHTML(PrintStream out) {
        out.printf("<p>");
        UNList.writeHTML(out);
        out.printf("</p>\n");
    }
}
