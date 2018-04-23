package CVPac;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;
import java.io.PrintStream;

public class ListItem {
    @XmlValue
    String content_;

    public ListItem(){}

    public ListItem(String content){
        content_=content;
    }

    public void writeHTML(PrintStream out){
        out.printf("<li>%s</li>\n", content_);
    }
}
