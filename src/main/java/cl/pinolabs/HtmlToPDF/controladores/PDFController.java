package cl.pinolabs.HtmlToPDF.controladores;

import java.io.ByteArrayOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.pinolabs.HtmlToPDF.servicios.ServiciosPDF;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class PDFController {
    private final static Logger logger = LoggerFactory.getLogger(PDFController.class);
    private final ServiciosPDF serviciosPDF;

    public PDFController(ServiciosPDF serviciosPDF) {
        this.serviciosPDF = serviciosPDF;
    }

    @PostMapping("/generarPDF/{tipo}")
    public ResponseEntity<byte[]> generarPDF(@PathVariable("tipo") Integer tipo, @RequestBody Object objeto, HttpServletResponse response) {
        try {
            if (objeto == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            String filename ="archivo.pdf";
            ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
            switch (tipo){
                case 1:
                    filename = "vendedores";
                    serviciosPDF.GeneraPDFTipo1(response, objeto,filename);
                    break;
                case 2:
                    filename = "vendedor";
                    serviciosPDF.GeneraPDFTipo2(response, objeto,filename);
                    break;
                default:
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            // Convertir el PDF a un array de bytes
            byte[] pdfBytes = pdfOutputStream.toByteArray();

            // Devolver el PDF como respuesta
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", filename+".pdf");
            headers.setContentLength(pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error al generar el PDF", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

