package ch.bytecrowd.dokupository.tests.utils;

import ch.bytecrowd.dokupository.models.enums.DocumentationModelKlassifizierung;
import ch.bytecrowd.dokupository.models.enums.ApplicationVersionState;
import ch.bytecrowd.dokupository.models.enums.DocumentationType;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RandomUtil {

    private static final SecureRandom RANDOM = new SecureRandom();

    public static String randomString() {

        return new BigInteger(100, RANDOM).toString(32);
    }

    public static byte[] randomBytes(int len) {

        final byte[] bytesAsObjects = new byte[len];

        for (int i = 0; i < len; i++) {
            bytesAsObjects[i] = randomPositiveInteger().byteValue();
        }

        return bytesAsObjects;
    }

    public static boolean randomBoolean() {

        return RANDOM.nextBoolean();
    }

    /**
     * @return positive Integer or 0
     */
    public static Integer randomPositiveInteger() {

        return Math.abs(RANDOM.nextInt());
    }

    public static DocumentationType randomDocumentationType() {

        return randomArrayEntry(DocumentationType.values());
    }

    public static DocumentationModelKlassifizierung randomDocumentationModelKlassifizierung() {

        return randomArrayEntry(DocumentationModelKlassifizierung.values());
    }

    public static ApplicationVersionState randomDocumentationModelState() {

        return randomArrayEntry(ApplicationVersionState.values());
    }

    public static <T> T randomArrayEntry(T[] array) {

        final int randomIndex = randomPositiveInteger() % array.length;
        return array[randomIndex];
    }
}
