/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package razaswow;

/**
 *
 * @author elram
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import razasWowEsquema.Razas;
import razasWowEsquema.Razas.Raza;

public class RazasWowJaxB {

    Razas myRaces;

    public boolean openJaxB(File file) {
        
        try {
            JAXBContext context = JAXBContext.newInstance(Razas.class);
            
            Unmarshaller u = context.createUnmarshaller();

            myRaces = (Razas) u.unmarshal(file);
            
            return true;
        } catch (JAXBException e) {
            return false;
        }
    }
    public String modCombo(){
        String comboString = "";
        List<Razas.Raza> raceList = myRaces.getRaza();
        for (int i = 0; i < raceList.size(); i++) {
            Raza tempRace = raceList.get(i);
            comboString = tempRace.getNombre();
            return comboString;
            
                
         }
        System.out.println(comboString);
        return comboString;
    }
    public Boolean changeJaxB(String raceName, String oldField, String newField) {
        try {
            JAXBContext context = JAXBContext.newInstance(Razas.class);
            Marshaller marshaller = context.createMarshaller();
            List<Razas.Raza> raceList = myRaces.getRaza();
            for (int i = 0; i < raceList.size(); i++) {
                Raza tempRace = raceList.get(i);
                if (tempRace.getNombre().equals(raceName)) {
                    if (tempRace.getBando().equals(oldField) || tempRace.getCapital().equals(oldField) ||
                            tempRace.getJefe().equals(oldField)) {
                        if (tempRace.getBando().equals(oldField)) {
                            tempRace.setBando(newField);
                        }
                        if (tempRace.getCapital().equals(oldField)) {
                            tempRace.setCapital(newField);
                        }
                        if (tempRace.getJefe().equals(oldField)) {
                            tempRace.setJefe(newField);
                        }
                    } else {
                        return false;
                    }
                }
            }
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(myRaces, System.out);
            marshaller.marshal(myRaces, new FileWriter(new File("documentos/razasWoW.xml")));
            
            return true;
        } catch (IOException | JAXBException e) {
            return false;
        }
    }
}
