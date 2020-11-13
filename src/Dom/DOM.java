/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dom;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author mirenordonezdearce
 */
public class DOM {
    Document doc;
    //Aquí tengo que meter todos los métodos y llamarlos desde la interfaz
    
    public int abrir_XML_DOM(File fichero) {
        doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(fichero);
            return 0;
        }catch (Exception e) {
            return -1;
        }
    }
    
    public String recorrerDOMyMostrar() {
        String salida = "";
        Node node;
        String datos_nodo[] = null;
        
        //Obtenemos el nodo raiz
        Node raiz = doc.getFirstChild();
        //Y una lista con todos los hijos del nodo raíz
        NodeList nodeList = raiz.getChildNodes();
        
        //Por cada hijo, ejecuta procesarLibro
        for (int i=0; i < nodeList.getLength(); i++) {
            node = nodeList.item(i);
            
            if (node.getNodeType()== Node.ELEMENT_NODE) {
                datos_nodo = procesarLibro(node);
                
                salida = salida + "\n" + "Publicado en: " + datos_nodo[0];
                salida = salida + "\n" + "El título es: " + datos_nodo[1];
                salida = salida + "\n" + "El autor es: " + datos_nodo[2];
                salida = salida + "\n ---------------";
            }
        }
        return salida;
    }

    private String[] procesarLibro(Node node) {
        String datos[] = new String[3];
        Node ntemp = null;
        int contador = 1;
        
        //Para el atributo, como solo hay uno, no está en un bucle.
        datos[0] = node.getAttributes().item(0).getNodeValue();
        
        //Obtengo los nodos hijos de los hijos Libro (título y autor).
        NodeList nodos = node.getChildNodes();
        
        for (int i = 0; i < nodos.getLength(); i++) {
            ntemp = nodos.item(i);
            if (ntemp.getNodeType() == Node.ELEMENT_NODE) {
                datos[contador] = ntemp.getFirstChild().getNodeValue();
                contador++;
            }
        }
        return datos;
    }
    
    public int annadirDOM (String titulo, String autor, String anno) {
        try {
            //Creo nodo tipo Element con nombre título (<Titulo>)
            Node ntitulo = doc.createElement("Titulo");
            //Creo nodo tipo text con el título del libro
            Node ntitulo_text = doc.createTextNode(titulo);
            //Se añade el nodo tipo text como hijo del nodo tipo Element
            ntitulo.appendChild(ntitulo_text);
            
            Node nautor = doc.createElement("Autor");
            Node nautor_text = doc.createTextNode(autor);
            nautor.appendChild(nautor_text);
            
            Node nlibro = doc.createElement("Libro");
            //Añado atributo de nlibro
            ((Element)nlibro).setAttribute("publicado_en", anno);
            
            //Añado a Libro, nodos título y autor
            nlibro.appendChild(ntitulo);
            nlibro.appendChild(nautor);
            
            //Añadir a nodo raíz, el nodo hijo Libro
            Node raiz = doc.getFirstChild();
            raiz.appendChild(nlibro);
            return 0;
        }
        catch (Exception e) {
            return -1;
        }
    }
    
    public int guardarDOMcomoFile() {
        try {
            //Creamos fichero
            File archivo_XML = new File ("salida.xml");
            //Especificamos el formato
            OutputFormat format = new OutputFormat(doc);
            //Indentamos el fichero
            format.setIndenting(true);
            //Escribimos el contenido en el FILE
            XMLSerializer serializer = 
                    new XMLSerializer (new FileOutputStream(archivo_XML), format);
            serializer.serialize(doc);
            return 0;
        } catch (Exception e) {
            return -1;
        }
        
    }
    
    public void modificarDOM (String nombreAntiguo, String nombreNuevo) {
        Node node;
        
        //Obtenemos el nodo raiz
        Node raiz = doc.getFirstChild();
        //Y una lista con todos los hijos del nodo raíz
        NodeList nodeList = raiz.getChildNodes();
        
        //Por cada hijo, ejecuta modifLibro
        for (int i=0; i < nodeList.getLength(); i++) {
            node = nodeList.item(i);
            
            if (node.getNodeType()== Node.ELEMENT_NODE) {
                modificarCampo(node, nombreAntiguo, nombreNuevo);
            }
        }
    }

    private void modificarCampo(Node node, String nombreAntiguo, String nombreNuevo) {
        String[] datos = new String[3];
        Node n;
        int contador = 1;
        
        //Guardo en datos[0] el valor del atributo
        datos[0] = node.getAttributes().item(0).getNodeValue();
        //Si el campo a modificar es "publicado en", al ser atributo es así
        if (nombreAntiguo.equals(datos[0])) {
            node.getAttributes().item(0).setNodeValue(nombreNuevo);
        }
        
        //Obtengo los nodos hijos de los hijos Libro (título y autor).
        NodeList nodos = node.getChildNodes();
        
        for (int i = 0; i < nodos.getLength(); i++) {
            n = nodos.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                //Coge el valor del nodo texto
                datos[contador] = n.getFirstChild().getNodeValue();
                //Si el nombre antiguo se corresponde con el valor del
                if (nombreAntiguo.equals(datos[contador])) {
                    n.getFirstChild().setNodeValue(nombreNuevo);
                }
                contador++;
            }
        }
        
    }

}
