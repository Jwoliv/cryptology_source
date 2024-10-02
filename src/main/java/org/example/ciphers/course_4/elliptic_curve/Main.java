package org.example.ciphers.course_4.elliptic_curve;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("#############################################");
        System.out.println("################## START ####################");
        System.out.println("#############################################");
        EllipticCurve ec = new EllipticCurve();
        List<EllipticCurve.PairPoint> pairPointList = ec.encrypt(13);

        System.out.println();
        System.out.println("#############################################");
        System.out.println("################# ENCRYPT ###################");
        System.out.println("#############################################");
        pairPointList.forEach(System.out::println);

        System.out.println();
        System.out.println("#############################################");
        System.out.println("################# DECRYPT ###################");
        System.out.println("#############################################");
        ec.decrypt(pairPointList).forEach(System.out::println);
    }
}
