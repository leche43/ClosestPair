import java.util.ArrayList;
import java.util.List;

public class ComputeClosestPair {

    public static void main(String[] args) {
        Point a = new Point();
        Point b = new Point();
        Point c = new Point();
        a.setX(1035638);
        a.setY(2411038);
        b.setX(1930588);
        b.setY(3969569);
        c.setX(66805);
        c.setY(165280);
        List<Point> points = new ArrayList<Point>();
        points.add(a);
        points.add(b);
        points.add(c);

        ClosestPair closestPair = computeClosestPairBruteForce(points);
        System.out.println(closestPair);
    }

    private static ClosestPair computeClosestPairBruteForce(List<Point> points) {
        ClosestPair closestPair = new ClosestPair();
        closestPair.setDistance(Double.POSITIVE_INFINITY);
        closestPair.setA(points.get(0));
        closestPair.setB(points.get(1));

        for(int i=0; i<points.size(); i++) {
            for(int j=0; j<points.size(); j++) {
                Point a = points.get(i);
                Point b = points.get(j);
                if(!a.equals(b)) {
                    Double dist = computeDistance(a,b);

                    if(dist < closestPair.getDistance()) {
                        closestPair.setDistance(dist);
                        closestPair.setA(a);
                        closestPair.setB(b);
                    }
                }
            }
        }
        return closestPair;
    }

    private static Double computeDistance(Point a, Point b) {
        double xDist = a.getX()-b.getX();
        double yDist = a.getY()-b.getY();
        double xPow = Math.pow(xDist,2);
        double yPow = Math.pow(yDist,2);
        double dist = Math.sqrt(xPow + yPow);
        return dist;
    }

}
