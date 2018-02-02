@echo off

rem Parametros de configuracion del programa.
set FORMATO=png
set ROWS=5
set COLUMNS=18
set ERROR_LEVEL=5
set LEN_CODEWORDS=999
set OPTIONS=32
set COLOR=0x000000
set BG_COLOR=0xFFFFFF
set ENCODING=ISO-8859-1

set PARAM1=%~1
set PARAM2=%~2

if not defined PARAM1 goto Error
if not defined PARAM2 goto Error

rem Ejecucion del programa.
java -jar target/pdf417-barcode-gen-1.0-SNAPSHOT-jar-with-dependencies.jar %1 %2 %FORMATO% %ROWS% %COLUMNS% %ERROR_LEVEL% %LEN_CODEWORDS% %OPTIONS% %COLOR% %BG_COLOR% %ENCODING%
goto Fin

:Error
java -jar target/pdf417-barcode-gen-1.0-SNAPSHOT-jar-with-dependencies.jar

:Fin
