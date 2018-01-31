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
 * Hello world!
 */
public class App {

	private static Logger logger = Logger.getLogger(App.class.getName());

	private static final Color DEFAULT_PDF417_BG_COLOR = Color.WHITE;
	private static final Color DEFAULT_PDF417_COLOR = Color.BLACK;
	private static final String DEFAULT_PDF417_ENCODING = "ISO-8859-1";
	private static final int DEFAULT_PDF417_OPTIONS = BarcodePDF417.PDF417_FORCE_BINARY;
	private static final int DEFAULT_PDF417_LEN_CODEWORDS = 999;
	private static final int DEFAULT_PDF417_ERROR_LEVEL = 5;
	private static final int DEFAULT_PDF417_COLUMNS = 18;
	private static final int DEFAULT_PDF417_ROWS = 5;
	private static final String DEFAULT_IMAGE_FORMAT = "png";

	public static void main(String[] args) throws IOException {
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
