package test.bar;

import java.io.File;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class TestEncode {
	public static void main(String[] args) throws Exception {
		String text = "http://127.0.0.1:8080/eservice/index.jsp?code=";
		int width = 700;
		int height = 700;
		String format = "png";
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
		File outputFile = new File("d://zhangjun.png");
		MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);

	}
}