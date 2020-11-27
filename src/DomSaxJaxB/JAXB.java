/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DomSaxJaxB;

import java.io.File;
import java.util.List;
import javalibros.Libros;
import javalibros.Libros.Libro;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author mirenordonezdearce
 */
public class JAXB {

    //Creo objeto tipo Libros.
    Libros misLibros;
    
    public int abrir_XML_JAXB(File fichero) {
        try {
            //Creamos la instancia de JAXB
            JAXBContext contexto =  JAXBContext.newInstance(Libros.class);
            //Creamos el objeto unmarshaller
            Unmarshaller u = contexto.createUnmarshaller();
            //Desrealizamos (Convertimos el fichero a objeto)
            misLibros = (Libros) u.unmarshal(fichero);
            return 0;
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al intentar abrir el fichero.");
            return -1;
        } 
    }

    public String recorrerJAXB() {
        String cadena_resultado = "";
        
        //Creo una lista de libros a partir de la clase Libros
        List<Libros.Libro> lLibros = misLibros.getLibro();
        
        for (int i=0; i < lLibros.size(); i++) {
            Libro libro_temporal = lLibros.get(i);
            cadena_resultado = cadena_resultado + "\nPublicado en: " + libro_temporal.getPublicadoEn();
            cadena_resultado = cadena_resultado + "\nTítulo: " + libro_temporal.getTitulo();
            cadena_resultado = cadena_resultado + "\nAutor: " + libro_temporal.getAutor();
            cadena_resultado = cadena_resultado + "\nEditorial: " + libro_temporal.getEditorial();
            cadena_resultado = cadena_resultado + "\n-------------------------------";
        }
        
        return cadena_resultado;
    }
    
}
