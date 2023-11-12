import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;

public class CreateXmlFileDemo {

    public static void main(String argv[]) {

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement("cars");
            doc.appendChild(rootElement);

            //array of attributes value
            String[] attrValue = {"Ferrari", "Geely", "Luxury", "Toyota", "Mazda"};

            //array of attributes type value
            String[] attrTypeValue = {"sports", "luxury", "adventure", "formula one", "simple"};

            //array of text node
            String[] textNode = {"Ferrari 1" , "Ferrari 202", "Hilux", "Raptor", "Avanza"};

            // Create 5 sets of car data
            for (int i = 0; i < 5; i++) {
                Element supercar = doc.createElement("supercars");
                rootElement.appendChild(supercar);

                // setting attribute to element
                Attr attr = doc.createAttribute("company");
                attr.setValue(attrValue[i]);
                supercar.setAttributeNode(attr);

                // carname element
                Element carname = doc.createElement("carname");
                Attr attrType = doc.createAttribute("type");
                attrType.setValue(attrTypeValue[i]);
                carname.setAttributeNode(attrType);
                carname.appendChild(doc.createTextNode(textNode[i]));
                supercar.appendChild(carname);


            }

            // write the content into an XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("xml\\cars.xml"));
            transformer.transform(source, result);

            // Output to console for testing
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
