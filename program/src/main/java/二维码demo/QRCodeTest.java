package 二维码demo;

import com.google.zxing.Result;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static 二维码demo.QRCode.createQRCode;
import static 二维码demo.QRCode.readQRCode;

/**
 * Created by 972536780 on 2017/6/27.
 */
public class QRCodeTest {
    @Test
    public void createQRCodeTest() throws IOException {
        BitMatrix bitMatrix = createQRCode(300, 300, "https://www.yangshunfeng.cn");
        Path file = new File("e:/123.png").toPath();
        MatrixToImageWriter.writeToPath(bitMatrix, "png", file);
    }

    @Test
    public void readQRCodeTest() {
        Result result = readQRCode("e:/123.png");
        System.out.println(result.toString());
        System.out.println(result.getBarcodeFormat());
        System.out.println(result.getText());
    }
}
