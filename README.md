# pdf417-cli
Interfaz de linea de comandos para generación de código de barras pdf417.

## Construcción

Se recomienda, para construir y utilizar este programa generar el proceso de construcción de MAVEN. Para ello se recomienda:

`# mvn clean compile assembly:single`


## Ejecución

Desde MS Windows ejecutar desde la carpeta base del programa:

`# pdf417-barcode-gen.bat <entrada> <salida> [formato rows columns errorLevel lenCodewords options color1 color2 encoding]`

Desde Linux/UNIX ejecutar desde la carpeta base del programa:

`# ./pdf417-barcode-gen.sh <entrada> <salida> [formato rows columns errorLevel lenCodewords options color1 color2 encoding]`

Con respecto a los parámetros de entrada.

- **entrada**: es el nombre del archivo de entrada con el contenido a codificar dentro del código de barras.
- **salida**: es el nombre del archivo donde se almacenará la imagen.
- **formato**: el formato de la imagen. Por defecto es png.
- **rows**: es el parametro de filas del código de barras.
- **columns**: es el parametro de columnas del código de barras.
- **errorLevel**: es el nivel de errores. Por defecto es 5.
- **lenCodewords**: por defecto es 999.
- **options**: por defecto es 32 (0x20).
- **color1**: es el color del código de barras.
- **color2**: es el color de fondo.
- **encoding**: es la codificación del texto. Por defecto es ISO-8859-1.
