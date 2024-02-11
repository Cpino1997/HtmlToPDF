package cl.pinolabs.HtmlToPDF;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import cl.pinolabs.HtmlToPDF.servicios.ServiciosPDF;
import lombok.Data;

@SpringBootTest
class HtmlToPdfApplicationTests {
    private final static Logger logger = LoggerFactory.getLogger(HtmlToPdfApplicationTests.class);

    @Autowired
    private ServiciosPDF serviciosPDF;

    @Data
    public static class Vendedor {
        private String nombres;
        private String apellidos;
        private String rut;
        private String tipo;
        private String imagen;
    }

    @Test
    void testTipo1() {
        try {
            // Crear objeto de vendedores
            String filename = "vendedores";
            MockHttpServletResponse response = new MockHttpServletResponse();
            List<Vendedor> vendedores = new ArrayList<>();
            vendedores.add(initVendedor1());
            vendedores.add(initVendedor2());

            serviciosPDF.GeneraPDFTipo1(response, vendedores, filename);

            // Validar si se crea el PDF correctamente
            // Ejemplo de validación:
            assert response.getStatus() == 200;
            assert response.getContentAsByteArray().length > 0;
        } catch (Exception e) {
            logger.error("Error en el tipo 1: " ,e);
        }
    }

    @Test
    void testTipo2() {
        try {
            // Crear objeto de vendedor
            String filename = "vendedor";
            MockHttpServletResponse response = new MockHttpServletResponse();
            Vendedor vendedor = initVendedor1();

            serviciosPDF.GeneraPDFTipo2(response, vendedor, filename);

            // Validar si se crea el PDF correctamente
            // Ejemplo de validación:
            assert response.getStatus() == 200;
            assert response.getContentAsByteArray().length > 0;
        } catch (Exception e) {
            logger.error("Error en el tipo 2: " ,e);

        }
    }

    Vendedor initVendedor1() {
        var v = new Vendedor();
        v.setApellidos("Pino");
        v.setNombres("Cristian");
        v.setRut("10.052.023-2");
        v.setTipo("Administrador");
        v.setImagen("https://thumbs.dreamstime.com/b/unknown-male-avatar-profile-image-businessman-vector-unknown-male-avatar-profile-image-businessman-vector-profile-179373829.jpg");
        return v;
    }

    Vendedor initVendedor2() {
        var v = new Vendedor();
        v.setApellidos("Guajardo");
        v.setNombres("Rodrigo");
        v.setRut("10.052.023-5");
        v.setTipo("Administrador");
        v.setImagen("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEljN4nGwrKt0l4-u4d6lVwm2Jzh9goTiwO4ysLlY-N1vxzLAd42XMIzqQ30gSszi53U8&usqp=CAU");
        return v;
    }
}
