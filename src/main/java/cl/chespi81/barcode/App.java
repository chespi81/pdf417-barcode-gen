package cl.chespi81.barcode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.io.FileUtils;

import com.itextpdf.text.pdf.BarcodePDF417;

/**
 * Interfaz de línea de comandos para generación de código de barras pdf417.
 * <p>
 * A continuación se presenta algo de información al respecto de los parámetros
 * de entrada de la clase <b>de acuerdo a su posición</b>.
 * <p>
 * <ol>
 * <li>entrada: es el nombre del archivo de entrada con el contenido en texto
 * plano a codificar dentro del código de barras PDF417. Este archivo debe
 * existir.
 * <li>salida: es el nombre del archivo donde se almacenará la imagen generada
 * del código de barras. Si se especifica un archivo previamente existente, su
 * contenido será sobreescrito.
 * <li>formato: es el formato de la imagen. Por defecto es png. Para mayor
 * información respecto a los formatos de archivo generados, diríjase a la
 * documentación (referenciada al final).
 * <li>rows (1): es el parámetro de filas del código de barras.
 * <li>columns (1): es el parámetro de columnas del código de barras.
 * <li>errorLevel (1): es el nivel de errores. Por defecto es 5.
 * <li>lenCodewords (1): por defecto es 999.
 * <li>options (1): por defecto es 32 (0x20).
 * <li>color1: es el color del código de barras, en formato hexadecimal RGB. Por
 * defecto es negro (0x000000).
 * <li>color2: es el color de fondo de la imagen generada, en formato
 * hexadecimal RGB. Por defecto es blanco (0xFFFFFF).
 * <li>encoding: es la codificación del texto. Por defecto es ISO-8859-1. Para
 * más información acerca de los encodings disponibles diríjase al la
 * documentación de la clase {@link java.nio.charset.Charset}.
 * </ol>
 * 
 * <b><i>NOTA (1)</i></b>: Para más antecedentes respecto de estos argumentos,
 * diríjase a la documentación de la clase java
 * {@link com.itextpdf.text.pdf.BarcodePDF417} para obtener más antecedentes.
 * 
 * @see <a href=
 *      "https://docs.oracle.com/javase/tutorial/2d/images/saveimage.html">https://docs.oracle.com/javase/tutorial/2d/images/saveimage.html</a>
 * @see <a href=
 *      "https://docs.oracle.com/javase/7/docs/api/java/nio/charset/Charset.html">https://docs.oracle.com/javase/7/docs/api/java/nio/charset/Charset.html</a>
 * @see <a href=
 *      "http://itextsupport.com/apidocs/itext5/latest/com/itextpdf/text/pdf/BarcodePDF417.html">http://itextsupport.com/apidocs/itext5/latest/com/itextpdf/text/pdf/BarcodePDF417.html</a>
 */
public class App {

	/**
	 * Variable de acceso al log del sistema.
	 */
	private static Logger logger = Logger.getLogger(App.class.getName());

	/**
	 * Color predeterminado para el fondo de la imagen del código de barras.
	 */
	private static final Color DEFAULT_PDF417_BG_COLOR = Color.WHITE;

	/**
	 * Color predeterminado para el código de barras en si de la imagen
	 * generada.
	 */
	private static final Color DEFAULT_PDF417_COLOR = Color.BLACK;

	/**
	 * Encoding predeterminado para el archivo de entrada.
	 */
	private static final String DEFAULT_PDF417_ENCODING = "ISO-8859-1";

	/**
	 * Opciones por defecto. Su valor predeterminado corresponde a
	 * {@link com.itextpdf.text.pdf.BarcodePDF417#PDF417_FORCE_BINARY}.
	 */
	private static final int DEFAULT_PDF417_OPTIONS = BarcodePDF417.PDF417_FORCE_BINARY;

	/**
	 * Valor predeterminado para lenCodewords del código de barras. Refiérase a
	 * {@link com.itextpdf.text.pdf.BarcodePDF417#setLenCodewords(int)} para
	 * mayores antecedentes.
	 */
	private static final int DEFAULT_PDF417_LEN_CODEWORDS = 999;

	/**
	 * Valor predetermiando para el errorLevel del código de barras. Refiérase a
	 * {@link com.itextpdf.text.pdf.BarcodePDF417#setErrorLevel(int)} para
	 * mayores antecedentes.
	 */
	private static final int DEFAULT_PDF417_ERROR_LEVEL = 5;

	/**
	 * Valor predetermiando para la cantidad de columnas del código de barras.
	 * Refiérase a
	 * {@link com.itextpdf.text.pdf.BarcodePDF417#setCodeColumns(int)} para
	 * mayores antecedentes.
	 */
	private static final int DEFAULT_PDF417_COLUMNS = 18;

	/**
	 * Valor predetermiando para la cantidad de filas del código de barras.
	 * Refiérase a {@link com.itextpdf.text.pdf.BarcodePDF417#setCodeRows(int)}
	 * para mayores antecedentes.
	 */
	private static final int DEFAULT_PDF417_ROWS = 5;

	/**
	 * Formato predeterminado de la imagen generada. Diríjase a
	 * {@link https://docs.oracle.com/javase/tutorial/2d/images/saveimage.html}
	 * para más antecedentes.
	 */
	private static final String DEFAULT_IMAGE_FORMAT = "png";

	/**
	 * Método principal de la clase de generación de códigos de barras.
	 * <p>
	 * Interpreta los parámetros de entrada, de acuerdo a la definición del
	 * comando, extrae el contenido del archivo de entrada, codifica el
	 * contenido como código de barras PDF417 y finalmente genera el archivo de
	 * salida en el formato especificado.
	 * </p>
	 * 
	 * TODO Eliminar la ecxepción de la firma del método main.
	 * 
	 * @param args
	 *            son los argumentos enviados desde la línea de comandos.
	 * @throws IOException
	 *             en caso de ocurrir una excepción.
	 */
	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			logger.warning("Sintaxis:");
			logger.warning(
					"pdf417-barcode-gen <entrada> <salida> [formato rows columns errorLevel lenCodewords options color1 color2 encoding]");
		} else {
			String salida = args[1];
			String formato = DEFAULT_IMAGE_FORMAT;
			int rows = DEFAULT_PDF417_ROWS;
			int columns = DEFAULT_PDF417_COLUMNS;
			int errorLevel = DEFAULT_PDF417_ERROR_LEVEL;
			int lenCodewords = DEFAULT_PDF417_LEN_CODEWORDS;
			int options = DEFAULT_PDF417_OPTIONS;
			String encoding = DEFAULT_PDF417_ENCODING;
			Color color1 = DEFAULT_PDF417_COLOR;
			Color color2 = DEFAULT_PDF417_BG_COLOR;
			if (args.length > 10) {
				formato = args[2];
				rows = Integer.parseInt(args[3]);
				columns = Integer.parseInt(args[4]);
				errorLevel = Integer.parseInt(args[5]);
				lenCodewords = Integer.parseInt(args[6]);
				options = Integer.parseInt(args[7]);
				color1 = Color.decode(args[8]);
				color2 = Color.decode(args[9]);
				encoding = args[10];
			}
			String text = obtenerTexto(args[0], encoding);
			if (text != null) {
				generarPDF417(text, salida, formato, rows, columns, errorLevel, lenCodewords, options, color1, color2,
						encoding);
			}
		}
	}

	/**
	 * Extrae el texto contenido en el archivo de entrada especificado
	 * utilizando en encoding indicado como argumento.
	 * <p>
	 * En caso de que el contenido no pueda ser recuperado desde el archivo por
	 * cualquier motivo, se retorna <code>null</code> y se registra el motivo en
	 * el log del sistema.
	 * </p>
	 * 
	 * @param entrada
	 *            es el archivo origen del conenido a extraer.
	 * @param encoding
	 *            del texto del archivo de entrada especificado.
	 * @return el contenido como texto, o <code>null</code> en caso de ocurrir
	 *         un problema durante la recuperación.
	 */
	private static String obtenerTexto(String entrada, String encoding) {
		try {
			logger.log(Level.INFO, "Leyendo contenido desde archivo {0}.", entrada);
			return FileUtils.readFileToString(new File(entrada), encoding);
		} catch (IOException ioe) {
			logger.log(Level.WARNING, "Error leyendo archivo {0}. Motivo: {1}.", new Object[] { entrada, ioe });
			logger.log(Level.FINEST, "Error leyendo datos de entrada: " + entrada, ioe);
		}
		return null;
	}

	/**
	 * Transforma el texto especificado en un código de barras PDF417 y lo
	 * almacena en el archivo y formato de arhcivo especificado.
	 * 
	 * @param texto
	 *            que se desea transformar en código de barras PDF417.
	 * @param archivo
	 *            de salida donde se desea almacenar la imagen.
	 * @param formato
	 *            de la imagen que se desea almacenar.
	 * @throws IOException
	 *             en caso de ocurrir un error durante la transformación o
	 *             almacenamiento.
	 */
	private static void generarPDF417(String texto, String archivo, String formato, int rows, int columns,
			int errorLevel, int lenCodewords, int options, Color color1, Color color2, String enconding)
			throws IOException {
		logger.log(Level.INFO, "Generando código de barras PDF417.");
		BarcodePDF417 pdf417 = new BarcodePDF417();
		pdf417.setCodeRows(rows);
		pdf417.setCodeColumns(columns);
		pdf417.setErrorLevel(errorLevel);
		pdf417.setLenCodewords(lenCodewords);
		pdf417.setOptions(options);
		pdf417.setText(texto.getBytes(enconding));
		logger.log(Level.INFO, "Transformando a imagen de código de barras.");
		Image imagen = pdf417.createAwtImage(color1, color2);
		BufferedImage img = generarImagen(imagen);
		logger.log(Level.INFO, "Generando archivo de salida {0}.", archivo);
		ImageIO.write(img, formato, new File(archivo));
		logger.log(Level.INFO, "Generación completada.");
	}

	/**
	 * Se encarga de transformar una imagen AWT a PNG con el fin de poder ser
	 * almacendada como un archivo.
	 * <p>
	 * El código fuente de este método fue obtenido desde el siguiente <a href=
	 * "https://rajeevalochanabr.wordpress.com/2012/01/05/pdf-file-to-image-png-conversion-using-java/">blog</a>
	 * </p>
	 * 
	 * @param image
	 *            es la imagen origen. No debe ser <code>null</code>.
	 * @return una imagen almacenable en un archivo.
	 */
	public static BufferedImage generarImagen(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}

		// This code ensures that all the pixels in the image are loaded
		image = new ImageIcon(image).getImage();

		// Create a buffered image with a format that's compatible with the
		// screen
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			// Determine the type of transparency of the new buffered image
			int transparency = Transparency.OPAQUE;

			// Create the buffered image
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
		} catch (HeadlessException e) {
			System.out.println("The system does not have a screen");
		}

		if (bimage == null) {
			int type = BufferedImage.TYPE_INT_RGB;
			bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
		}

		// Copy image to buffered image
		Graphics g = bimage.createGraphics();

		// Paint the image onto the buffered image
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return bimage;
	}
}
