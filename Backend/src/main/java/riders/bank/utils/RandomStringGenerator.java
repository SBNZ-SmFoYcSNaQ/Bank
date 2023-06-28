package riders.bank.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

public class RandomStringGenerator {
    public static String generateRandomString(int length) {
        Random random = new Random(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            stringBuilder.append(digit);
        }

        return stringBuilder.toString();
    }
}
