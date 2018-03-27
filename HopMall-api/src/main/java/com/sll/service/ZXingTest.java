package com.sll.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Hashtable;

public class ZXingTest {

    public static void main(String[] args) {
        ZXingTest.generalQRcode("https://www.baidu.com");
    }

    public static String generalQRcode(String content){
        Hashtable hint = new Hashtable();
        hint.put(EncodeHintType.CHARACTER_SET,"utf-8");
        String bianary = null;

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE,400,400,hint);

//            File outputFile = new File("/Users/zhaodexu/Desktop/666.jpg");
//            ImageIO.write(toBufferedImage(bitMatrix),"jpg",outputFile);
            BufferedImage image = toBufferedImage(bitMatrix);
            System.out.println("生成成功");

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image,"png",os);

            BASE64Encoder encoder = new BASE64Encoder();
            bianary = new String(encoder.encode(os.toByteArray()));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return bianary;
    }

    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }
}
