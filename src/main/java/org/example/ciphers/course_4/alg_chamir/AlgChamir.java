package org.example.ciphers.course_4.alg_chamir;

public class AlgChamir {
    public static final Long p = 61L;
    public static final Long qA = 7L;
    public static final Long qB = 37L;
    public static final Long M = 10L;

    public Long proceed() {
        Long kA = defineK(qA, p);
        Long kB = defineK(qB, p);
        Long yA = modExp(M, kA, p);
        Long yB = modExp(yA, kB, p);
        Long C = modExp(yB, qA, p);
        Long M = modExp(C, qB, p);
        return M;
    }

    public Long defineK(Long q, Long p) {
        Long k = 1L;
        while ((k * q) % (p - 1) != 1L) {
            k++;
        }
        return k;
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
