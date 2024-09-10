package org.example.ciphers.course_4.el_gamal;

import java.util.List;

public class ElGamalCipher {
    private static final Long p = 523L;
    private static final Long a = 2L;
    private static final Long x = 16L;
    private static final Long M = 230L;

    private static Long b;

    ElGamalCipher() {
        b = (long) (Math.pow(a, x) % p);
        System.out.printf("""
                #######################
                # p = %d
                # a = %d
                # x = %s
                # M = %s
                #######################
                """, p, a, x, M);
        System.out.printf("open key (a = %d, p = %d, b = %d)\n", a, p, b);
    }

    public List<Long> encrypt() {
        Long y = 31L;
        Long e = modExp(a, y, p);
        Long k = (M * modExp(b, y, p)) % p;
        System.out.printf("y = %d\n", y);
        return List.of(e, k);
    }

    public static long modExp(long base, long exp, long mod) {
        long result = 1;
        base = base % mod;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = (result * base) % mod;
            }
            exp = exp >> 1;
            base = (base * base) % mod;
        }
        return result;
    }

    public Long decrypt(List<Long> items) {
        Long e = items.get(0);
        Long k = items.get(1);
        return k * modExp(e, p - 1 -x, p) % p;
    }
}
