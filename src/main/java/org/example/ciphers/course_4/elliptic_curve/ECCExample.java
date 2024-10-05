package org.example.ciphers.course_4.elliptic_curve;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

record Point(int x, int y) { }
record Pair<K, V>(K first, V second) { }

@Data
class ECCConfig {
    private Integer a = -1;
    private Integer b = 1;
    private Integer p = 751;
    private Integer kb = 11;
    private Integer r = 23;
    private Point G = new Point(0, 1);
}

class ECCMath {
    private final ECCConfig config = new ECCConfig();

    public Point addPoints(Point P, Point Q) {
        if (P == null) return Q;
        if (Q == null) return P;

        var xy1 = new Point(P.x(), P.y());
        var xy2 = new Point(Q.x(), Q.y());
        Integer m = getM(xy1, xy2);
        return definePoint(config.getP(), m, xy1, xy2);
    }

    private Point definePoint(int p, Integer m, Point xy1, Point xy2) {
        if (m == null) return null;
        int x3 = (m * m - xy1.x() - xy2.x()) % p;
        int y3 = (m * (xy1.x() - x3) - xy1.y()) % p;
        return new Point(x3, y3);
    }

    private Integer getM(Point xy1, Point xy2) {
        Integer a = config.getA();
        Integer p = config.getP();
        return xy1.x() == xy2.x()
                ? xy1.y() == xy2.y()
                    ? (3 * xy1.x() * xy1.x() + a) * modInverse(2 * xy1.y(), p) % p
                    : null
                : Integer.valueOf((xy2.y() - xy1.y()) * modInverse(xy2.x() - xy1.x(), p) % p);
    }

    public Point multiply(int k, Point P) {
        if (P == null || k == 0) {
            return null;
        }
        Point result = null;
        Point point = P;
        while (k > 0) {
            if (k % 2 == 0) {
                result = addPoints(result, point);
            }
            point = addPoints(point, point);
            k >>= 1;
        }
        return result;
    }

    public int modInverse(int a, int p) {
        return pow(a, p - 2, p);
    }

    private int pow(int base, int exponent, int mod) {
        int result = 1;
        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = (result * base) % mod;
            }
            base = (base * base) % mod;
            exponent >>= 1;
        }
        return result;
    }
}

class ECCEncryptor {
    private final ECCConfig config = new ECCConfig();
    private final ECCMath eccMath = new ECCMath();

    public List<Pair<Point, Integer>> encryptMessage(String message) {
        List<Integer> asciiMessage = Stream.of(message.split("")).map(x -> (int) x.charAt(0)).toList();
        List<Pair<Point, Integer>> encryptedSymbols = new ArrayList<>();
        for (int n : asciiMessage) {
            Point Y = eccMath.multiply(config.getKb(), config.getG());
            Point rY = eccMath.multiply(config.getR(), Y);
            Point R = eccMath.multiply(config.getR(), config.getG());
            int C = (n * rY.x()) % config.getP();
            encryptedSymbols.add(new Pair<>(R, C));
            System.out.printf("{ %s, %5s, %5s }%n", (char) n, n, C);
        }

        return encryptedSymbols;
    }
}

class ECCDecrypted {
    private final ECCConfig config = new ECCConfig();
    private final ECCMath eccMath = new ECCMath();

    public String decryptMessage(List<Pair<Point, Integer>> encryptedSymbols) {
        StringBuilder decryptedMessage = new StringBuilder();
        encryptedSymbols.forEach(pair -> {
            Point R = pair.first();
            int C = pair.second();
            Point Q = new ECCMath().multiply(config.getKb(), R);
            int x1 = Q.x();
            int decryptedSymbol = (C * eccMath.modInverse(x1, config.getP())) % config.getP();
            decryptedMessage.append((char) decryptedSymbol);
        });
        return decryptedMessage.toString();
    }
}

public class ECCExample {
    public static void main(String[] args) {
        List<Pair<Point, Integer>> encryptedSymbols = new ECCEncryptor().encryptMessage("lishchuk");
        String decryptedText = new ECCDecrypted().decryptMessage(encryptedSymbols);
        System.out.printf("Decrypted - %s%n", decryptedText);
    }
}
