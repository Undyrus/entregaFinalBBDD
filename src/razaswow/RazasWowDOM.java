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
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author elram
 */
public class RazasWowDOM {

    public static Document doc;

    public boolean openDOM(File file) {

        doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //Ignoramos comentarios y espacios en blanco.
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);

            DocumentBuilder builder = factory.newDocumentBuilder();

            doc = builder.parse(file);
            
            return true;
        } catch (IOException | ParserConfigurationException | SAXException e) {
            
            return false;
        }
    }
    
     public String runDOM(){
        String resultChain = "";
        Node node;
        String nodeData[];
         try {
             
         
        Node root = doc.getFirstChild();
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength();i++){
            node = nodeList.item(i);
            
            if(node.getNodeType()==Node.ELEMENT_NODE){
                nodeData = processRace(node);
                
                resultChain += "\n " + "Raza introducida en la expansión: " + nodeData[0];
                
                resultChain += "\n " + "Raza introducida en el año: " + nodeData[1];
                resultChain += "\n " + "Zona inicial de la raza: " + nodeData[2];
                resultChain += "\n " + "Nombre de la raza: " + nodeData[3];
                resultChain += "\n " + "Facción de la raza: " + nodeData[4];
                resultChain += "\n " + "Origen de la raza: " + nodeData[5];
                resultChain += "\n " + "Idioma de la raza: " + nodeData[6];
                resultChain += "\n " + "Capital de la raza: " + nodeData[7];
                resultChain += "\n " + "Líder de la raza: " + nodeData[8];
                resultChain += "\n " + "Montura de la raza: " + nodeData[9];
                resultChain += "\n --------------------";
            }
        }
        return resultChain;
        } catch (Exception e) {
            
            return resultChain;
             
        }
    }
     
    private String[] processRace(Node n) {
        
        String data[] = new String[9];
        Node ntemp;
        int counter = 3;
        System.out.println("erroraco");
        
        data[0] = n.getAttributes().item(0).getNodeValue();
        data[1] = n.getAttributes().item(1).getNodeValue();
        data[2] = n.getAttributes().item(2).getNodeValue();
        
        NodeList nodeList = n.getChildNodes();
        
        for(int i=0; i<nodeList.getLength();i++){
            ntemp = nodeList.item(i);   
            System.out.println(nodeList.item(0).getNodeValue());
            if(ntemp.getNodeType() == Node.ELEMENT_NODE){
                data[counter] = ntemp.getFirstChild().getNodeValue();
                counter++;
            }
        }
        return data;
    }
    
    public boolean addDOM(String name, String faction, String origin, String lenguage, 
            String capital, String leader, String mount, String expansion, String year, String zone) {

        try {
            Node nName = doc.createElement("Nombre");
            Node nNameText = doc.createTextNode(name);
            nName.appendChild(nNameText);
            

            Node nFaction = doc.createElement("Bando");
            Node nFactionText = doc.createTextNode(faction);
            nFaction.appendChild(nFactionText);
            
            Node nOrigin = doc.createElement("Origen");
            Node nOriginText = doc.createTextNode(origin);
            nOrigin.appendChild(nOriginText);
            
            Node nLenguage = doc.createElement("Idioma");
            Node nLenguageText = doc.createTextNode(lenguage);
            nLenguage.appendChild(nLenguageText);

            Node nCapital = doc.createElement("Capital");
            Node nCapitalText = doc.createTextNode(capital);
            nCapital.appendChild(nCapitalText);
                        
            Node nLeader =doc.createElement("Jefe");
            Node nLeaderText = doc.createTextNode(leader);
            nLeader.appendChild(nLeaderText);
            
            Node nMount =doc.createElement("Montura");
            Node nMountText = doc.createTextNode(mount);
            nMount.appendChild(nMountText);
            
            Node nRace = doc.createElement("Raza");
            ((Element) nRace).setAttribute("introducida_en", expansion);
            ((Element) nRace).setAttribute("fecha", year);
            ((Element) nRace).setAttribute("zona_inicial", zone);
            nRace.appendChild(nName);
            nRace.appendChild(nFaction);
            nRace.appendChild(nOrigin);
            nRace.appendChild(nLenguage);
            nRace.appendChild(nCapital);
            nRace.appendChild(nLeader);
            nRace.appendChild(nMount);
            

            Node root = doc.getFirstChild();
            root.appendChild(nRace);

            return true;
        } catch (DOMException e) {
            return false;
        }
    }
}
