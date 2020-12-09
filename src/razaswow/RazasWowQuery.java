/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package razaswow;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author elram
 */
public class RazasWowQuery {
    Document doc;
    XPath xpath;
    
    public boolean openXPath(File file) {
        doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);

            DocumentBuilder builder = factory.newDocumentBuilder();

            doc = builder.parse(file);
            xpath = XPathFactory.newInstance().newXPath();
            return true;
        } catch (IOException | ParserConfigurationException | SAXException e) {
            return false;
        }
    }
    
        public String executeXPath(String query) {
        String resultChain = "";
        try {
            XPathExpression expression = xpath.compile(query);
            Object result = expression.evaluate(doc, XPathConstants.NODESET);
            NodeList nodeList = (NodeList) result;
            String nodeData[];

            for (int i = 0; i < nodeList.getLength(); i++) {
                ///Libros/Libro/Titulo[.='Oliver Twist']

                if (nodeList.item(i).getNodeName().equals("Raza")) {
                    nodeData = processInfo(nodeList.item(i));
                    resultChain += "\n " + nodeData[0];
                    resultChain += "\n " + nodeData[1];
                    resultChain += "\n " + nodeData[2];

                } else {
                    resultChain += "\n " + nodeList.item(i).getFirstChild().getNodeValue();

                }
            }
            if (resultChain.equals("")) {
                return "Error en la consulta";
            }
            return resultChain;
        } catch (XPathExpressionException | DOMException e) {
            return "Error al leer la consulta";
        }

    }

    private String[] processInfo(Node n) {

        String data[] = new String[200];
        Node ntemp;
        int counter = 1;

        data[0] = n.getAttributes().item(0).getNodeValue();

        NodeList nodeList = n.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {

            ntemp = nodeList.item(i);

            if (ntemp.getNodeType() == Node.ELEMENT_NODE) {

                data[counter] = ntemp.getFirstChild().getNodeValue();
                counter++;
            }
        }
        return data;
    }
}
