## Uso

  1. Realice una solicitud POST al endpoint `/generarPDF/{tipo}`.
    - `{tipo}`: Especifique el tipo de PDF a generar (1 o 2).
    
  2. Envíe un objeto o lista de objetos en el cuerpo de la solicitud. El sistema procesará este contenido HTML.

  3. Recibirá como respuesta un archivo PDF generado según el tipo especificado.
## Ejemplo de Solicitud (con CURL)

  ```bash
  # Solicitud tipo 1, genera un pdf a partir de una lista de objetos usando el template
  curl --location 'http://localhost:8087/api/v1/pdf/generarPDF/1' \
  --header 'Content-Type: application/json' \
  --data '[
    {
      "nombres": "Juan Nicolas",
      "apellidos": " Perez Gomez",
      "tipo": "Interno",
      "rut": "10.258.236-3",
      "imagen": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEljN4nGwrKt0l4-u4d6lVwm2Jzh9goTiwO4ysLlY-N1vxzLAd42XMIzqQ30gSszi53U8&usqp=CAU"
    },
    {
      "nombres": "Maria Juana",
      "apellidos": "De Las Flores",
      "tipo": "Externo",
      "rut": "26.359.369-0",
      "imagen": "https://thumbs.dreamstime.com/b/unknown-male-avatar-profile-image-businessman-vector-unknown-male-avatar-profile-image-businessman-vector-profile-179373829.jpg"
    }
  ]
  '

  # Solicitud tipo 2, genera un pdf a partir de un objeto usando el template2
  curl --location 'http://localhost:8087/api/v1/pdf/generarPDF/2' \
  --header 'Content-Type: application/json' \
  --data '{
      "nombres": "Maria Juana",
      "apellidos": "De Las Flores",
      "tipo": "Externo",
      "rut": "26.359.369-0",
      "imagen": "https://thumbs.dreamstime.com/b/unknown-male-avatar-profile-image-businessman-vector-unknown-male-avatar-profile-image-businessman-vector-profile-179373829.jpg"
  }'
  ```

## Tipos de PDF
  - Tipo 1: PDF de vendedores.
  - Tipo 2: PDF de un vendedor individual.
## Ejemplo de Respuesta
  Recibirá un archivo PDF adjunto como respuesta.
## Notas
  - Asegúrese de que el contenido HTML sea válido y esté bien formateado para obtener resultados óptimos.
  - Este servicio está preparado para procesar objetos o listas de objetos según el tipo de PDF especificado.

## Agregar nuevos templates
  Si desea agregar sus propios templates, siga estos pasos:

  - Vaya al servicio ServiciosPDFImpl.
  - Cree una nueva función similar a GeneraPDFTipo1 o GeneraPDFTipo2 para su nuevo tipo:
    ```java
    // Ejemplo de función para generar un nuevo tipo de PDF
    @Override
    public void GeneraPDFTipo3(HttpServletResponse response, Object objeto,String filename) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            // Validar que los parámetros no sean nulos
            if (response == null || objeto == null) {
                logger.error("[PdfGenerator] Los parámetros no pueden ser nulos :(");
                throw new IllegalArgumentException("[PdfGenerator] Los parámetros no pueden ser nulos :(");
            }

            // Crear un contexto Thymeleaf y procesar la plantilla
            Context context = new Context();
            context.setVariable("objeto", objeto);
            String contentDisposition = "attachment; filename="+filename +".pdf";
            String parsedHtml = templateEngine.process(filename, context); 

            // Configurar el renderizador PDF
            renderizarPDF(response, bos, contentDisposition, parsedHtml);

        } catch (Exception e) {
            manejarErrorGeneracionPDF(e);
        }
    }
    ```
  - Agrege el nuevo template en resources/templates
  - Actualice el controlador PDFController para manejar su nuevo tipo:
    ```java
    @PostMapping("/generarPDF/{tipo}")
    public ResponseEntity<byte[]> generarPDF(@PathVariable("tipo") Integer tipo, @RequestBody Object objeto, HttpServletResponse response) {
      switch(tipo){
        case 3:
            filename = "nombre_template";
            serviciosPDF.GeneraPDFTipo3(response, objeto,filename);
            break;
      }
    }
    
    ```
## ¡Disfrute utilizando nuestra herramienta para generar PDF personalizados a partir de su contenido HTML!