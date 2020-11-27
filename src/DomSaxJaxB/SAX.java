/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DomSaxJaxB;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author mirenordonezdearce
 */
public class SAX {
    
    SAXParser parser;
    ManejadorSAX sh;
    File ficheroXML;
    
    public int abrir_XML_SAX(File fichero) {
        try {
            //Crear un objetos SAXParser para interpretar el documento XML
            SAXParserFactory factory = SAXParserFactory.newInstance();
            parser = factory.newSAXParser();
            
            //Creamos instancia para el manejador de errores
            sh = new ManejadorSAX();
            
            ficheroXML = fichero;
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }
    
    public String recorrerSAX() {
        try {
            sh.cadena_resultado = "";
            parser.parse(ficheroXML, sh);
            return sh.cadena_resultado;
        } catch (SAXException ex) {
            return "Error al parsear con SAX";
        }catch (IOException ex) {
            return "Error al parsear con SAX";
        }
    }

}

class ManejadorSAX extends DefaultHandler {
    String cadena_resultado = "";
    int contador = 1;
    
    //Para las cadenas de caracteres
    @Override
    public void characters(char[] ch, int start, int length)
    throws SAXException{
        for (int i=start; i<length+start; i++) {
            cadena_resultado = cadena_resultado + ch[i];
        }
        cadena_resultado = cadena_resultado.trim() + "\n";
    }
    
    //Para el final de los elementos
    @Override
    public void endElement(String uri, String localName, String qName)
    throws SAXException {
        if (qName.equals("Libro")) {
            cadena_resultado = cadena_resultado + "---------------\n";
        }
    }
    
    //Para el comienzo de los elementos
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
    throws SAXException {
        if (qName.equals("Libros")) {
            cadena_resultado = cadena_resultado + "Se van a mostrar los libros de este documento:\n" + 
                    "******************************\n";
        }
        if (qName.equals("Libro")) {
            cadena_resultado = cadena_resultado + "EL LIBRO " + contador + " ES:\n" + 
                    "Publicado en: " + attributes.getValue(attributes.getQName(0).trim());
            contador++;
        }
        else if (qName.equals("Titulo")) {
            cadena_resultado = cadena_resultado + "El título es: " .trim();
        }
        else if (qName.equals("Autor")) {
            cadena_resultado = cadena_resultado + "El autor es: " .trim();
        }
        
    }
   
}
