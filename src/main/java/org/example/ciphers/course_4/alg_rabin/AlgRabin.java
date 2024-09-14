package org.example.ciphers.course_4.alg_rabin;

public class AlgRabin {
    public static final Long p = 239L;
    public static Long q = 19L;
    public static final Long E = 2L;
    public static final Long M = 10L;


    public void proceed() {
        Long n = p * q;
        Long oldQ = q;
        q = p % 4;
        Long C = (long) Math.pow(M, 2) % n;
        Long k = (p + 1) / 4;
        Long l = (oldQ + 1) / 4;
        Long x = modExp(C, k, p);
        Long y = modExp(C, l, oldQ);

        Long xm1 = getMod(x, p);
        Long ym1 = getMod(y, oldQ);

        Long xm2 = getMod(x, p);
        Long ym2 = getMod(y * -1, oldQ);

        Long xm3 = getMod(x * -1, p);
        Long ym3 = getMod(y, oldQ);

        Long xm4 = getMod(y * -1, p);
        Long ym4 = getMod(x * -1, oldQ);

        System.out.printf("m = %d\n", M);
        System.out.printf("x = %d, y = %d\n", x, y);
        System.out.printf("m1 = %d m1 = %d\n", xm1, ym1);
        System.out.printf("m2 = %d m2 = %d\n", xm2, ym2);
        System.out.printf("m3 = %d m3 = %d\n", xm3, ym3);
        System.out.printf("m4 = %d m4 = %d\n", xm4, ym4);
    }

    private static long getMod(Long x, Long v) {
        if (x > 0) {
            return x % v;
        }
        return (v - (-1 * x)) % v;
    }

    public static long modExp(Long base, Long exp, Long mod) {
        Long result = 1L;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = (result * base) % mod;
            }
            exp = exp >> 1;
            base = (base * base) % mod;
        }
        return result;
    }
}
