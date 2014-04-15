import java.util.List;

public class BruteForce {
    /**
     * This computes the closest pair using brute-force O(n²)
     * This is only implemented for test purposes
     */
    public static ClosestPair computeCP(List<Point> points) {
        ClosestPair closestPair = new ClosestPair(points.get(0),points.get(1),Double.POSITIVE_INFINITY);

        for(int i=0; i<points.size(); i++) {
            for(int j=0; j<points.size(); j++) {
                Point a = points.get(i);
                Point b = points.get(j);
                if(!a.equals(b)) {
                    Double dist = ComputeClosestPair.computeEuclideanDistance(a, b);

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
}
