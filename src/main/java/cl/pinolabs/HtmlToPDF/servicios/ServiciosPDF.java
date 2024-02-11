package cl.pinolabs.HtmlToPDF.servicios;

import jakarta.servlet.http.HttpServletResponse;

public interface ServiciosPDF {
    void GeneraPDFTipo1(HttpServletResponse response,  Object objeto,String filename);
    void GeneraPDFTipo2(HttpServletResponse response,  Object objeto,String filename);
}
