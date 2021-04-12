package com.mycompany.main;

import com.mycompany.main.crud.DatosArchivoImpl;
import com.mycompany.main.crud.IDatosArchivo;
import com.mycompany.main.crud.datoArchivo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

@WebServlet("/recibir")
public class DatosDAO extends HttpServlet {

    datoArchivo archivo;
    IDatosArchivo dao = new DatosArchivoImpl();
    private static final long serialVersionUID = 1L;

    public DatosDAO() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        String file = request.getParameter("archivo");
        String ext = "";
        int i = file.lastIndexOf('.');
        System.out.println(i);
        if (i > 0) {
            ext = file.substring(i + 1);
            System.out.println(ext);
        }
        datoArchivo archivo = new datoArchivo();
        File files = new File(file);
        BufferedReader br = new BufferedReader(new FileReader(files));
        String linea;
        String usu = null;
        String cla = null;
        String date = null;
        String nomFile = null;
        if (ext.equalsIgnoreCase("txt")) {
            while ((linea = br.readLine()) != null) {
                if (linea.equalsIgnoreCase("{")) {
                    linea = br.readLine();
                    String[] arreglo_datos = linea.split(":");
                    if (arreglo_datos[0].equalsIgnoreCase("\"nombre\"")) {
                        usu = arreglo_datos[1];
                        linea = br.readLine();
                        arreglo_datos = linea.split(":");
                        if (arreglo_datos[0].equalsIgnoreCase("\"dependencia\"")) {
                            cla = arreglo_datos[1];
                            linea = br.readLine();
                            arreglo_datos = linea.split(":");
                            if (arreglo_datos[0].equalsIgnoreCase("\"fecha\"")) {
                                date = arreglo_datos[1];
                                linea = br.readLine();
                                arreglo_datos = linea.split(":");
                                if (arreglo_datos[0].equalsIgnoreCase("\"archivo\"")) {
                                    nomFile = arreglo_datos[1];
                                    linea = br.readLine();
                                    arreglo_datos = linea.split(":");
                                    archivo = new datoArchivo(usu, cla, date, nomFile, ext);
                                    dao.registrar(archivo);
                                } else {
                                    out.println("<html>");
                                    out.println("<head></head>");
                                    out.println("<body>");
                                    out.println("Archivo invalido");
                                    out.println("<br>");
                                    out.println("<a href=\"/TNCol/\">Volver al inicio</a>");
                                    out.println("</body>");
                                    out.println("</html>");
                                }
                            }
                        }
                    }
                }
            }
        }
        if (ext.equalsIgnoreCase("json")) {
            JSONParser parser = new JSONParser();
            try {
                Object obj = parser.parse(new FileReader(file));
                JSONObject jsonObject = (JSONObject) obj;
                usu = (String) jsonObject.get("nombre");
                cla = (String) jsonObject.get("dependencia");
                date = (String) jsonObject.get("fecha");
                nomFile = (String) jsonObject.get("archivo");
                archivo = new datoArchivo(usu, cla, date, nomFile, ext);
                dao.registrar(archivo);
                try {
                    // Creo una instancia de DocumentBuilderFactory
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    // Creo un documentBuilder
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    // Creo un DOMImplementation
                    DOMImplementation implementation = builder.getDOMImplementation();

                    // Creo un documento con un elemento raiz
                    Document documento = implementation.createDocument(null, "concesionario", null);
                    documento.setXmlVersion("1.0");

                    // Creo los elementos
                    Element coches = documento.createElement("coches");
                    Element coche = documento.createElement("coche");

                    // Matricula
                    Element matricula = documento.createElement("matricula");
                    Text textMatricula = documento.createTextNode("1111AAA");
                    matricula.appendChild(textMatricula);
                    coche.appendChild(matricula);

                    // Marca
                    Element marca = documento.createElement("marca");
                    Text textMarca = documento.createTextNode("AUDI");
                    marca.appendChild(textMarca);
                    coche.appendChild(marca);

                    // Precio
                    Element precio = documento.createElement("precio");
                    Text textPrecio = documento.createTextNode("30000");
                    precio.appendChild(textPrecio);
                    coche.appendChild(precio);

                    // Añado al elemento coches el elemento coche
                    coches.appendChild(coche);

                    // Añado al root el elemento coches
                    documento.getDocumentElement().appendChild(coches);

                    // Asocio el source con el Document
                    Source source = new DOMSource(documento);
                    // Creo el Result, indicado que fichero se va a crear
                    Result result = new StreamResult(new File("concesionario.xml"));

                    // Creo un transformer, se crea el fichero XML
                    Transformer transformer = TransformerFactory.newInstance().newTransformer();
                    transformer.transform(source, result);

                } catch (ParserConfigurationException | TransformerException ex) {
                    System.out.println(ex.getMessage());
                }
            } catch (ParseException ex) {
                Logger.getLogger(DatosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (archivo.getNombre() != null) {
                out.println("<html>");
                out.println("<head></head>");
                out.println("<body>");
                out.println("<br>");
                out.println("<a href=\"/TNCol/\">Volver al inicio</a>");
                out.println("<br>");
                out.println("<br>");
                out.println("<a download=\"TNCol\" href=" + file + ">Descargar</a>");
                out.println("</body>");
                out.println("</html>");

            } else {
                out.println("<html>");
                out.println("<head></head>");
                out.println("<body>");
                out.println("Archivo invalido");
                out.println("<br>");
                out.println("<a href=\"/TNCol/\">Volver al inicio</a>");
                out.println("</body>");
                out.println("</html>");
            }
        }
    }
}
