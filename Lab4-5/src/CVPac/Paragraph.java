package CVPac;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlValue;
import java.io.PrintStream;

@XmlSeeAlso({ParagraphWithList.class})
public class Paragraph {
    @XmlValue
    String content_;

    Paragraph(){
        content_="";
    }

    Paragraph(String content){
        content_=content;
    }

    Paragraph setContent(String content){
        content_=content;
        return this;
    }
    void writeHTML(PrintStream out){
        out.printf("<p>%s</p>\n",content_);
    }


}
