package trompetin17.com.openglestuto.util;

/**
 * Created by JG on 23/07/17.
 */

public class Geometry {

    public static class Sphere {
        public final Point center;
        public final float radius;

        public Sphere(Point center, float radius) {
            this.center = center;
            this.radius = radius;
        }
    }

    public static boolean intersects(Sphere sphere, Ray ray) {
        return distanceBetween(sphere.center, ray) < sphere.radius;
    }

    private static float distanceBetween(Point point, Ray ray) {
        Vector p1ToPoint = vectorBetween(ray.point, point);
        Vector p2ToPoint = vectorBetween(ray.point.translate(ray.vector), point);
    }

    public static class Point {
        public final float x, y, z;
        public Point(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Point translateY(float distance) {
            return new Point(x, y + distance, z);
        }

        public Point translate(Vector vector) {
            return null;
        }
    }

    public static class Circle {
        public final Point center;
        public final float radious;

        public Circle(Point center, float radious) {
            this.center = center;
            this.radious = radious;
        }

        public Circle scale(float scale) {
            return new Circle(center, radious * scale);
        }
    }

    public static class Cylinder {
        public final Point center;
        public final float radius;
        public final float height;

        public Cylinder(Point center, float radius, float height) {
            this.center = center;
            this.radius = radius;
            this.height = height;
        }
    }

    public static class Vector {
        public final float x, y, z;

        public Vector(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
    public static class Ray {
        public final Point point;
        public final Vector vector;

        public Ray(Point point, Vector vector) {
            this.point = point;
            this.vector = vector;

        }
    }

    public static Vector vectorBetween(Point from, Point to) {
        return new Vector(
                to.x - from.x,
                to.y - from.y,
                to.z - from.z
        );
    }
}
