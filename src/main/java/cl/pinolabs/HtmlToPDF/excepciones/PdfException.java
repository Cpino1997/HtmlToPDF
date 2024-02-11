package cl.pinolabs.HtmlToPDF.excepciones;


public class PdfException extends RuntimeException {
    public PdfException(String message, Throwable cause) {
        super(message, cause);
    }
}