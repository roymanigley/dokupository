package ch.bytecrowd.dokupository.utils;

import ch.bytecrowd.dokupository.models.jpa.PrintScreen;
import org.apache.poi.xwpf.usermodel.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;

public class ImageUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ImageUtil.class);

    private ImageUtil() {
    }

    public static BufferedImage toImage(PrintScreen printScreen) throws IOException {
        return bytesToImage(printScreen.getData());
    }

    public static BufferedImage bytesToImage(byte[] bytes) throws IOException {
        try (final ByteArrayInputStream input = new ByteArrayInputStream(bytes);) {
            return ImageIO.read(input);
        } catch (Exception e) {
            throw e;
        }
    }

    public static double getScalingForDocx(BufferedImage image) throws IOException {

        final double width = image.getWidth();
        return (width > 495) ? (495) / width : 1.;
    }

    public static int guessPictureType(PrintScreen printScreen) throws IOException {

        try (final ByteArrayInputStream input = new ByteArrayInputStream(printScreen.getData());) {
            final String contentTypeFromStream = URLConnection.guessContentTypeFromStream(input);
            if (LOG.isDebugEnabled())
                LOG.debug("ContentType to guess: " + contentTypeFromStream);

            switch (contentTypeFromStream) {
                case "image/png" : return Document.PICTURE_TYPE_PNG;
                case "image/jpg": case "image/jpeg" : return Document.PICTURE_TYPE_JPEG;
                case "image/bmp": return Document.PICTURE_TYPE_BMP;
                case "image/gif": return Document.PICTURE_TYPE_GIF;
                default: throw new IllegalStateException(contentTypeFromStream + "not supported, supported mimeTypes: [image/png, image/jpg, image/jpeg, image/bmp, image/gif]");
            }
        } catch (IOException e) {
            throw e;
        }
    }
}
