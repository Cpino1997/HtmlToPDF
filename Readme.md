# HtmlToPDF
HtmlToPDF es una potente herramienta basada en Java 17 y Spring Boot 3 que simplifica la generación de archivos PDF a partir de archivos HTML. Diseñado para ser fácil de usar y altamente personalizable, este proyecto utiliza Thymeleaf para facilitar la conversión de tus documentos HTML en elegantes archivos PDF.

# Características principales
- Integración sin esfuerzo: HtmlToPDF se integra perfectamente con proyectos Spring Boot, lo que facilita la incorporación de funcionalidades de generación de PDF en tus aplicaciones web.

- Potenciado por Thymeleaf: Aprovecha la potencia y flexibilidad de Thymeleaf para definir y estructurar tus documentos HTML, brindando una experiencia de desarrollo más rica.

- Personalización avanzada: HtmlToPDF ofrece opciones de configuración avanzadas para adaptarse a tus necesidades específicas. Personaliza la apariencia, formato y más para obtener resultados precisos.

# Uso
Para utilizar este sistema, sigue estos pasos:

1. Clona o descarga el proyecto desde el repositorio.

2. Añade tu plantilla HTML:
   - Coloca tu archivo HTML en la carpeta resources.
   - Asegúrate de que la plantilla tenga marcadores Thymeleaf como el [archivo de ejemplo 1](./src/main/resources/templates/vendedor.html) o [archivo de ejemplo 2](./src/main/resources/templates/vendedores.html)
3. Configura el Objeto o Lista de Objetos:
   - Define el objeto o lista de objetos que se utilizará para rellenar la plantilla HTML.
   - Asegúrate de que el objeto coincida con las variables utilizadas en la plantilla.
4. Realiza una solicitud POST al endpoint /generarPDF/vendedores:
   - Utiliza tu aplicación web o Postman para enviar una solicitud POST a este endpoint. [ejemplos](/HELP.md)
   - En el cuerpo de la solicitud, envía el objeto o la lista de objetos que deseas utilizar.
5. Descarga el PDF generado:
   - La respuesta contendrá el PDF generado, que se descargará automáticamente con el nombre "vendedores.pdf".

# Contribuciones
¡Las contribuciones son bienvenidas! Si encuentras algún problema o tienes ideas para mejorar HtmlToPDF, no dudes en abrir un problema o enviar una solicitud de extracción.

# Licencia
Este proyecto está bajo la Licencia MIT. Consulta el archivo LICENSE para obtener más detalles. 

