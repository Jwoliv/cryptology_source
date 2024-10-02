package org.example.ciphers.course_4.elliptic_curve;

import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
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
    private static List<Point> pMs = new ArrayList<>();
    private static Point yA;
    private static Point yB;

    public EllipticCurve() {
        List<Integer> asciiChairs = Arrays.stream(M.split("")).map(x -> (int) x.charAt(0)).toList();
        pMs = asciiChairs.stream().map(G::multiply).toList();
        yA = G.multiply(kA);
        yB = G.multiply(kB);
        System.out.println("list of the pm");
        pMs.forEach(System.out::println);
    }


    public List<PairPoint> encrypt(Integer r) {
        Point rG = G.multiply(r);
        List<Point> rs = pMs.stream().map(pm -> yB.multiply(r).add(pm)).toList();
        return rs.stream().map(rso -> PairPoint.builder().first(rG).second(rso).build()).toList();
    }

    public List<Point> decrypt(List<PairPoint> pairPoints) {
        List<Point> result = new ArrayList<>();
        for (PairPoint pairPoint : pairPoints) {
            Point rG = pairPoint.getFirst();
            Point R = pairPoint.getSecond();
            result.add(R.reduce(rG.multiply(kB)));
        }
        return result;
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Point {
        private Long x;
        private Long y;

        public Point multiply(Integer r) {
            return new Point(x * r, y * r);
        }

        public Point multiply(Long r) {
            return new Point(x * r, y * r);
        }

        public Point add(Point pm) {
            return new Point(x + pm.x, y + pm.y);
        }

        public Point reduce(Point pm) {
            return new Point(x - pm.x, y - pm.y);
        }

        @Override
        public String toString() {
            return "{ %d, %d }".formatted(x, y);
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PairPoint {
        private Point first;
        private Point second;

        @Override
        public String toString() {
            return "{ %d, %d } { %d, %d }".formatted(first.getX(), first.getY(), second.getX(), second.getY());
        }
    }
}
