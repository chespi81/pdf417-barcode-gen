#!/bin/bash

# Parametros de configuracion del programa.
FORMATO=png
ROWS=5
COLUMNS=18
ERROR_LEVEL=5
LEN_CODEWORDS=999
OPTIONS=32
COLOR=0x000000
BG_COLOR=0xFFFFFF
ENCODING=ISO-8859-1

if [ "$#" -eq 2 ]; then
	# Ejecucion del programa.
	java -jar target/pdf17-barcode-gen-1.0-SNAPSHOT-jar-with-dependencies.jar ${1} ${2} ${FORMATO} ${ROWS} ${COLUMNS} ${ERROR_LEVEL} ${LEN_CODEWORDS} ${OPTIONS} ${COLOR} ${BG_COLOR} ${ENCODING}
else
	java -jar target/pdf17-barcode-gen-1.0-SNAPSHOT-jar-with-dependencies.jar
fi
