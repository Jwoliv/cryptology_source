package org.example.ciphers.course_4.elliptic_curve;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class EllipticCurve {
    private static final String M = "Lishchuk";
    private static final Long p = 751L;
    private static final Long a = -1L;
    private static final Long b = 1L;
    private static final Point E = new Point(-1L, 1L);
    private static final Point G = new Point(0L, 1L);
    private static final Long kA = 2L;
    private static final Long kB = 11L;
    private static final Long r = 13L;
    private static Point pM;
    private static Point yA;
    private static Point yB;


    public static void proceed() {
        char[] chars = M.toCharArray();
        List<Integer> asciiChars = new ArrayList<>();
        for (var item: chars) {
            asciiChars.add((int) item);
        }
        pM = G.multiplyBy(r);
        System.out.println("Init pm = " + pM);
        yA= G.multiplyBy(kA);
        yB = G.multiplyBy(kB);
    }


    public static PointPair encrypt(Long r) {
        Point rG = G.multiplyBy(r);
        Point rYb = yB.multiplyBy(r);
        Point R = rYb.addPointers(pM);
        return new PointPair(rG, R);
    }

    public static void decrypt(PointPair pair) {
        Point kbrG = new Point(pair.rG.x * kB, pair.rG.y * kB);
        Point pM = pair.R.addPointers(new Point(kbrG.x, kbrG.y * -1));
        System.out.println("Decrypted pM = " + pM);
    }


    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Point {
        private Long x;
        private Long y;

        public Point multiplyBy(Long r) {
            return new Point(x * r, y * r);
        }

        public Point addPointers(Point p) {
            return new Point(x + p.x, y + p.y);
        }
    }

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PointPair {
        private Point rG;
        private Point R;
    }

}
