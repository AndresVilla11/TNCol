package com.mycompany.main;

import com.mycompany.main.crud.DatosArchivoImpl;
import com.mycompany.main.crud.IDatosArchivo;
import com.mycompany.main.crud.datoArchivo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.XML;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
                                    out.println("<html>");
                                    out.println("<head></head>");
                                    out.println("<body>");
                                    out.println("<a href=\"/TNCol/\">Volver al inicio</a>");
                                    out.println("<br>");
                                    out.println("<br>");
                                    out.println("<a download=\"TNCol\" href=" + file + ">Descargar</a>");
                                    out.println("</body>");
                                    out.println("</html>");
                                }
                            }
                        }
                    }
                } else {
                    out.println("<html>");
                    out.println("<head></head>");
                    out.println("<body>");
                    out.println("Archivo invalido");
                    out.println("<br>");
                    out.println("<a href=\"/TNCol/\">Volver al inicio</a>");
                    out.println("</body>");
                    out.println("</html>");
                    break;
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
                try (FileWriter fileWriter = new FileWriter("Intento.xml")) {
                    fileWriter.write(XML.toString(jsonObject));
                    System.out.println("" + XML.toString(jsonObject));
                } catch (JSONException ex) {
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
            } catch (ParseException ex) {
                Logger.getLogger(DatosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
