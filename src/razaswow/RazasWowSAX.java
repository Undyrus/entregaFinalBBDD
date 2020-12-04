package razaswow;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Ramiro Diego González
 */
public class RazasWowSAX {

    public static File fileXML = new File("documentos/razasWoW.xml");
    public static SAXParser parser;
    public static saxHandler sh;
    

    public static boolean openFile(File file) {

        try {
            //Se crea un objeto SAXParser para interpretar el documento XML
            SAXParserFactory factory = SAXParserFactory.newInstance();
            parser = factory.newSAXParser();

            //Se crea una instancia del manejador que será el que recorra
            //el documento XML secuencialmente
            sh = new saxHandler();

            fileXML = file;

            return true;
        } catch (ParserConfigurationException | SAXException e) {
            return false;
        }
    }

    public static String runSAX() {
        try {
            sh.resultChain = "";
            parser.parse(fileXML, sh);

        } catch (SAXException | IOException ex) {
            return "Error al parsear con SAX";
        }
        return sh.resultChain;
    }

    static class saxHandler extends DefaultHandler {

        String resultChain = "";

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {

            for (int i = start; i < length + start; i++) {
                resultChain += ch[i];
            }
            resultChain = resultChain.trim() + "\n";

        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equals("Raza")) {
                resultChain = resultChain + "--------------------------------------------------------------------------------------------\n";
            }
        }

        //Este método se ejecuta cuando se encuentra un elemento de apertura
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {

            if (qName.equals("Raza")) {

                resultChain += "Raza introducida en la expansión: " + attributes.getValue(attributes.getQName(0).trim()) + "\n";
                resultChain += "Raza introducida en el año: " + attributes.getValue(attributes.getQName(1).trim()) + "\n";
                resultChain += "Zona inicial de la raza: " + attributes.getValue(attributes.getQName(2).trim()) + "\n";
            } else if (qName.equals("Nombre")) {
                resultChain += "Nombre de la raza: ";
            } else if (qName.equals("Bando")) {
                resultChain += "Facción de la raza: ";
            } else if (qName.equals("Origen")) {
                resultChain += "Origen de la raza: ";
            } else if (qName.equals("Idioma")) {
                resultChain += "Idioma de la raza: ";
            } else if (qName.equals("Capital")) {
                resultChain += "Capital de la raza: ";
            } else if (qName.equals("Jefe")) {
                resultChain += "Líder de la raza: ";
            } else if (qName.equals("Montura")) {
                resultChain += "Montura de la raza: ";
            }
        }
    }
}
