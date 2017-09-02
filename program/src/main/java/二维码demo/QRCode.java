package 二维码demo;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 972536780 on 2017/6/27.
 */
public class QRCode {
    public static BitMatrix createQRCode(int width, int height, String content) {
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);
        try {
            return new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Result readQRCode(String path) {
        Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        MultiFormatReader formatReader = new MultiFormatReader();
        File file = new File(path);
        try {
            BufferedImage image = ImageIO.read(file);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
            return formatReader.decode(bitmap, hints);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
