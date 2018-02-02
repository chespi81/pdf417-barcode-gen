# pdf417-barcode-gen: Generador de códigos de barra en formato PDF417

Interfaz de línea de comandos para generación de código de barras pdf417.

## Construcción del componente

A continuación se describen las instrucciones necesarias para realizar la construcción del componente.

### Requisitos previos para la construcción

Antes de poder construir el programa, se requiere contar con las siguientes herramientas instaladas.

- [Oracle Java JDK 1.6+](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
- [Apache Maven 3.x](https://maven.apache.org/)

Se recomienda que durante el proceso de compilación se encuentren configuradas las siguientes variables de entorno.

- ***JAVA_HOME***: apuntando a la carpeta base de la instalación del JDK.
- ***PATH***: debe contener, entre otros, la ruta al JDK a utilizar (${JAVA_HOME}/bin en Linux/UNIX o %JAVA_HOME%\bin en MS Windows).

### Instrucciones para la construcción

Con el fin de construir el jar del programa se debe ejecutar el procedimiento de construcción utilizando ***Apache MAVEN***. Esto se logra ejecutando el siguiente comando en la carpeta base del proyecto:

`# mvn clean compile assembly:single`

## Información para ejecución por línea de comandos

Desde la interfaz de línea de comandos de MS Windows, se debe ejecutar desde la carpeta base del proyecto el script `pdf417-barcode-gen.bat`:

`# pdf417-barcode-gen.bat <entrada> <salida> [formato rows columns errorLevel lenCodewords options color1 color2 encoding]`

Desde la interfaz de línea de comandos Linux/UNIX/MAC, se debe ejecutar desde la carpeta base del proyecto el script `pdf417-barcode-gen.sh`:

`# ./pdf417-barcode-gen.sh <entrada> <salida> [formato rows columns errorLevel lenCodewords options color1 color2 encoding]`

A continuación se presenta algo de información al respecto de los parámetros de entrada del script de línea de comandos.

- **entrada**: es el nombre del archivo de entrada con el contenido *en texto plano* a codificar dentro del código de barras PDF417. ***Este archivo debe existir***.
- **salida**: es el nombre del archivo donde se almacenará la imagen generada del código de barras. Si se especifica un archivo previamente existente, ***su contenido será sobreescrito***.
- **formato**: es el formato de la imagen. Por defecto es **png**. Para mayor información respecto a los formatos de archivo generados, diríjase al siguiente [enlace](https://docs.oracle.com/javase/tutorial/2d/images/saveimage.html).
- **rows** (1): es el parámetro de filas del código de barras.
- **columns** (1): es el parámetro de columnas del código de barras.
- **errorLevel** (1): es el nivel de errores. Por defecto es 5.
- **lenCodewords** (1): por defecto es 999.
- **options** (1): por defecto es 32 (0x20).
- **color1**: es el color del código de barras, en formato hexadecimal RGB. Por defecto es negro (0x000000).
- **color2**: es el color de fondo de la imagen generada, en formato hexadecimal RGB. Por defecto es blanco (0xFFFFFF).
- **encoding**: es la codificación del texto. Por defecto es ISO-8859-1. Para más información acerca de los encodings disponibles diríjase al siguiente [enlace](https://docs.oracle.com/javase/7/docs/api/java/nio/charset/Charset.html).

> ***NOTA*** (1): Para más antecedentes respecto de estos argumentos, diríjase a la documentación de la clase java [com.itextpdf.text.pdf.BarcodePDF417](http://itextsupport.com/apidocs/itext5/latest/com/itextpdf/text/pdf/BarcodePDF417.html).