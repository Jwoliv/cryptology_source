package org.example.ciphers.course_4.rsa;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class RsaCipher {

    private static final Long p =  13L;
    private static final Long q =  17L;
    private static final List<Long> eSuggestions = List.of(68L, 92L, 133L);
    public static final Long M = 12L;

    private final Long n;
    private final Long e;
    private final Long d;


    public RsaCipher() {
        Long qN = (p - 1) * (q - 1);
        n = p * q;
        e = getPrimeNumber();
        d = getD(e, qN);
    }

    public List<Long> encrypt() {
        return Arrays.stream(M.toString().split(""))
                .map(Long::parseLong)
                .map(value -> (long) Math.pow(value, e) % n)
                .toList();
    }

    public String decrypt(List<Long> values) {
        return String.join("", values.stream().map(c -> String.valueOf((long) (Math.pow(c, d) % n))).toList());
    }


    private Long getD(Long e, Long qN) {
        Long d = 0L;
        while ((d * e) % qN != 1) {
            d++;
        }
        return d;
    }

    private Long getPrimeNumber() {
        return eSuggestions.stream()
                .filter(this::isPrime)
                .findFirst()
                .orElse(7L);
    }

    private Boolean isPrime(Long number) {
        return IntStream
                .iterate(2, i -> i < number, i -> i + 1)
                .noneMatch(i -> number % i == 0);
    }
}
