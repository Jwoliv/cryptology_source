package org.example.ciphers.course_4.el_gamal;

import java.util.List;
import java.util.Random;

public class ElGamalCipher {
    private static final Long p = 523L;
    private static final Long a = 2L;
    private static final Long x = 16L;
    private static final String M = "2302";

    private static Long b;

    ElGamalCipher() {
        b = (long) (Math.pow(a, x) % p);
        System.out.printf("""
                #######################
                # p = %d
                # a = %d
                # x = %d
                # M = %s
                #######################
                """, p, a, x, M);
        System.out.printf("open key (a = %d, p = %d, b = %d)\n", a, p, b);
    }

    public List<Long> encrypt() {
        Random random = new Random();
        Long qP = p - 1;
        Long y = 1L + (long) (random.nextDouble() * (qP - 1L));
        while (gcd(y, qP) != 1) {
            y = 1L + (long) (random.nextDouble() * (qP - 1L));
        }
        Long e = (long) (Math.pow(a, y) % p);
        Long k = (long) (Math.pow(b, y) * Long.parseLong(M)) % p;
        System.out.printf("y = %d\n", y);
        return List.of(e, k);
    }


    public Long gcd(Long a, Long b) {
        while (b != 0) {
            Long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public Long decrypt(List<Long> items) {
        Long e = items.get(0);
        Long k = items.get(1);
        return (k * (long) Math.pow(e, p - 1 - x)) % p;
    }
}
