import java.util.*;

public class ComputeClosestPair {

    private static final String SMALL = "small.txt";
    private static final String LARGE = "large.txt";

    public static void main(String[] args) {
        System.out.print("\n");
        System.out.println("********************Closest Pair Computation********************");
        Long startSmall = System.currentTimeMillis();
        List<Point> pointsSmall = ReadPointsFile.readPointsFromFile(SMALL);
        ClosestPair closestPairSmall = RandomizedGrid.computeClosestPairRandom(pointsSmall);
        Long endSmall = System.currentTimeMillis();
        Long timeSmall = endSmall-startSmall;
        System.out.println("Using the small list:");
        System.out.println(closestPairSmall);
        System.out.println("Computed in " + timeSmall + "ms\n");

        Long startLarge = System.currentTimeMillis();
        List<Point> pointsLarge = ReadPointsFile.readPointsFromFile(LARGE);
        ClosestPair closestPairLarge = RandomizedGrid.computeClosestPairRandom(pointsLarge);
        Long endLarge = System.currentTimeMillis();
        Long timeLarge = endLarge-startLarge;
        System.out.println("Using the large list:");
        System.out.println(closestPairLarge);
        System.out.println("Computed in " + timeLarge + "ms");
        System.out.println("****************************************************************");
    }

    public static Double computeEuclideanDistance(Point a, Point b) {
        double xDist = a.getX()-b.getX();
        double yDist = a.getY()-b.getY();
        double xPow = Math.pow(xDist,2);
        double yPow = Math.pow(yDist,2);
        return Math.sqrt(xPow + yPow);
    }
}