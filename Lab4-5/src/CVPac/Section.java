package CVPac;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Section {
    @XmlAttribute
    String title_;
    @XmlElements(value= {
            @XmlElement(name = "paragraph", type = Paragraph.class),
            @XmlElement(name = "paragraph-with-list", type = ParagraphWithList.class)
    })
    List<Paragraph> paragraps = new ArrayList<>() ;

    Section(){}

    Section(String title){
        title_=title;
    }

    Section setTitle(String title){
        title_=title;
        return this;
    }
    Section addParagraph(String paragraphText){
        Paragraph paragraph = new Paragraph(paragraphText);
        paragraps.add(paragraph);
        return this;
    }
    Section addParagraph(Paragraph p){
        paragraps.add(p);
        return this;
    }
    void writeHTML(PrintStream out){
        out.printf("<h2>%s</h2>",this.title_);
        for(Paragraph paragraph : paragraps){
            paragraph.writeHTML(out);
        }
    }

    public void addListItem(String c) {
    }
}
