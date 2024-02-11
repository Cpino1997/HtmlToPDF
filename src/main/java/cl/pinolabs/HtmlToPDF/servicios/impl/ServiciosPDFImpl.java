package cl.pinolabs.HtmlToPDF.servicios.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import cl.pinolabs.HtmlToPDF.excepciones.PdfException;
import cl.pinolabs.HtmlToPDF.servicios.ServiciosPDF;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Implementación de la interfaz ServiciosPDF para la generación de archivos PDF.
 */
@Service
public class ServiciosPDFImpl implements ServiciosPDF {

    private final static Logger logger = LoggerFactory.getLogger(ServiciosPDFImpl.class);

    @Resource
    private TemplateEngine templateEngine;

    /**
     * Genera un archivo PDF del tipo 1.
     *
     * @param response Objeto HttpServletResponse para configurar la respuesta HTTP.
     * @param objeto   Objeto que contiene los datos para generar el PDF.
     * @param filename Nombre del archivo PDF y del template ubicado en resources/templates.
     */
    @Override
    public void GeneraPDFTipo1(HttpServletResponse response, Object objeto,String filename) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            // Validar que los parámetros no sean nulos
            if (response == null || objeto == null) {
                logger.error("[PdfGenerator] Los parámetros no pueden ser nulos :(");
                throw new IllegalArgumentException("[PdfGenerator] Los parámetros no pueden ser nulos :(");
            }

            // Crear un contexto Thymeleaf y procesar la plantilla
            Context context = new Context();
            context.setVariable("objetos", objeto);
            String contentDisposition = "attachment; filename=" + filename + ".pdf";
            String parsedHtml = templateEngine.process(filename, context);

            // Configurar el renderizador PDF
            renderizarPDF(response, bos, contentDisposition, parsedHtml);

        } catch (Exception e) {
            manejarErrorGeneracionPDF(e);
        }
    }

    /**
     * Genera un archivo PDF del tipo 2.
     * @param response Objeto HttpServletResponse para configurar la respuesta HTTP.
     * @param objeto   Objeto que contiene los datos para generar el PDF.
     * @param filename Nombre del archivo PDF y del template ubicado en resources/templates.
     */
    @Override
    public void GeneraPDFTipo2(HttpServletResponse response, Object objeto,String filename) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            // Validar que los parámetros no sean nulos
            if (response == null || objeto == null) {
                logger.error("[PdfGenerator] Los parámetros no pueden ser nulos :(");
                throw new IllegalArgumentException("[PdfGenerator] Los parámetros no pueden ser nulos :(");
            }

            // Crear un contexto Thymeleaf y procesar la plantilla
            Context context = new Context();
            context.setVariable("objeto", objeto);
            String contentDisposition = "attachment; filename=" + filename + ".pdf";
            String parsedHtml = templateEngine.process(filename, context);

            // Configurar el renderizador PDF
            renderizarPDF(response, bos, contentDisposition, parsedHtml);

        } catch (Exception e) {
            manejarErrorGeneracionPDF(e);
        }
    }

    /**
     * Configura el renderizador PDF y copia el contenido al OutputStream de la respuesta HTTP.
     *
     * @param response           Objeto HttpServletResponse para configurar la respuesta HTTP.
     * @param bos                ByteArrayOutputStream para almacenar el contenido del PDF.
     * @param contentDisposition Encabezado Content-Disposition para la respuesta HTTP.
     * @param parsedHtml         HTML procesado a partir de la plantilla Thymeleaf.
     * @throws Exception Si ocurre un error durante la generación del PDF.
     */
    private void renderizarPDF(HttpServletResponse response, ByteArrayOutputStream bos,
                               String contentDisposition, String parsedHtml) throws Exception {
        // Configurar el renderizador PDF
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(parsedHtml);
        renderer.layout();
        renderer.createPDF(bos);

        // Configurar la respuesta HTTP para el archivo PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", contentDisposition);

        // Copiar el contenido del PDF al OutputStream de la respuesta
        ByteArrayInputStream inStream = new ByteArrayInputStream(bos.toByteArray());
        FileCopyUtils.copy(inStream, response.getOutputStream());
    }

    /**
     * Maneja los errores durante la generación del PDF, registrando el error y lanzando una excepción.
     *
     * @param e Excepción que ocurrió durante la generación del PDF.
     * @throws PdfException Excepción que encapsula el error durante la generación del PDF.
     */
    private void manejarErrorGeneracionPDF(Exception e) {
        logger.error("[PdfGenerator] Error al generar el PDF", e);
        throw new PdfException("[PdfGenerator] Error al generar el PDF", e);
    }
}
