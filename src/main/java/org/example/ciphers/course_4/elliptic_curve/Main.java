package org.example.ciphers.course_4.elliptic_curve;

public class Main {
    public static void main(String[] args) {
        EllipticCurve.proceed();
        EllipticCurve.PointPair pointPair = EllipticCurve.encrypt(15L);
        System.out.println(pointPair);
        EllipticCurve.decrypt(pointPair);
    }
}
