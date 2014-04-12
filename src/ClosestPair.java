public class ClosestPair {

    private Point a;
    private Point b;
    private Double distance;

    public ClosestPair() {

    }

    public Point getA() {
        return a;
    }

    public void setA(Point a) {
        this.a = a;
    }

    public Point getB() {
        return b;
    }

    public void setB(Point b) {
        this.b = b;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "The closest pair is: "
               + a
               + ","
               + b
               + "\n"
               + "distance: " + distance;
    }
}
