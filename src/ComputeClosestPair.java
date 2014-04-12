import java.util.*;

public class ComputeClosestPair {

    private static final String small = "small.txt";
    private static final String large = "large.txt";

    public static void main(String[] args) {
        List<Point> pointsSmall = ReadPointsFile.readPointsFromFile(small);
        ClosestPair closestPairSmall = computeClosestPairBruteForce(pointsSmall);
        System.out.println(closestPairSmall);

        closestPairSmall = computeClosestPairRandom(pointsSmall);
        System.out.println(closestPairSmall);

        List<Point> pointsLarge = ReadPointsFile.readPointsFromFile(large);
        ClosestPair closestPairLarge = computeClosestPairBruteForce(pointsLarge);
        System.out.println(closestPairLarge);

        closestPairLarge = computeClosestPairRandom(pointsLarge);
        System.out.println(closestPairLarge);

        /**Point a = new Point();
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

        ClosestPair cp = computeClosestPairBruteForce(points);
        System.out.println(cp);

        cp = computeClosestPairRandom(points);
        System.out.println(cp);*/
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

    private static ClosestPair computeClosestPairRandom(List<Point> points) {
        // 1
        List<Point> randomPoints = shuffle(points);
        // 2
        Double cpDist = computeDistance(randomPoints.get(0), randomPoints.get(1));
        ClosestPair closestPair = new ClosestPair();
        closestPair.setA(randomPoints.get(0));
        closestPair.setB(randomPoints.get(1));
        closestPair.setDistance(cpDist);
        // 3
        List<Point> currentGridPoints = new ArrayList<Point>();
        currentGridPoints.add(randomPoints.get(0));
        currentGridPoints.add(randomPoints.get(1));
        Map<Cell,List<Point>> grid = computeGrid(currentGridPoints,cpDist);
        // 4
        for(int i=2; i<randomPoints.size();i++) {
            Point currentPoint = randomPoints.get(i);
            // Platziere Pi in D
            Cell cell1 = computeGridCell(currentPoint,cpDist);
            // Untersuche 9 relevante Zellen
            Cell cell2 = new Cell(cell1.getX()+1,cell1.getY());
            Cell cell3 = new Cell(cell1.getX()+1,cell1.getY()-1);
            Cell cell4 = new Cell(cell1.getX(),cell1.getY()-1);
            Cell cell5 = new Cell(cell1.getX()-1,cell1.getY()-1);
            Cell cell6 = new Cell(cell1.getX()-1,cell1.getY());
            Cell cell7 = new Cell(cell1.getX()-1,cell1.getY()+1);
            Cell cell8 = new Cell(cell1.getX(),cell1.getY()+1);
            Cell cell9 = new Cell(cell1.getX()+1,cell1.getY()+1);
            List<Cell> neighborCells = new ArrayList<Cell>();
            neighborCells.add(cell1);
            neighborCells.add(cell2);
            neighborCells.add(cell3);
            neighborCells.add(cell4);
            neighborCells.add(cell5);
            neighborCells.add(cell6);
            neighborCells.add(cell7);
            neighborCells.add(cell8);
            neighborCells.add(cell9);

            //hole benachbarte Punkte aus den Zellen
            List<Point> neighborPoints = new ArrayList<Point>();
            for(Cell c : neighborCells) {
                if(grid.containsKey(c)) {
                    neighborPoints.addAll(grid.get(c));
                }
            }

            //TODO check if it changes
            boolean distChanged = false;

            //pruefe ob es bei den benachbarten Punkten eine kuerzere Distanz gibt
            for(Point neighborPoint : neighborPoints) {
                Double currentDist = computeDistance(neighborPoint, currentPoint);
                if(currentDist < cpDist) {
                    cpDist = currentDist;
                    distChanged = true;
                    closestPair.setA(currentPoint);
                    closestPair.setB(neighborPoint);
                    closestPair.setDistance(cpDist);
                }
            }

            //fuege den Punkt hinzu
            currentGridPoints.add(currentPoint);

            //neues Gitter aufbauen
            if(distChanged) {
                grid = computeGrid(currentGridPoints, cpDist);
            } else {
                //fuege Punkt ins Grid ein (platzieren)
                if(grid.containsKey(cell1)) {
                    List<Point> list = grid.get(cell1);
                    list.add(currentPoint);
                }
                else {
                    List<Point> list = new ArrayList<Point>();
                    list.add(currentPoint);
                    grid.put(cell1, list);
                }
            }

        }

        return closestPair;
    }

    private static void analyzeAdjacentCells(Map<Cell,List<Point>> grid, Point p, Double cpDist) {
        // Platziere Pi in D
        Cell cell = computeGridCell(p,cpDist);

    }

    private static Map<Cell,List<Point>> computeGrid(List<Point> points, Double dist) {
        Map<Cell,List<Point>> grid = new HashMap<Cell, List<Point>>();

        for(Point p : points) {
            Cell cell = computeGridCell(p,dist);

            if(grid.containsKey(cell)) {
                List<Point> list = grid.get(cell);
                list.add(p);
            }
            else {
                List<Point> list = new ArrayList<Point>();
                list.add(p);
                grid.put(cell, list);
            }
        }
        return grid;
    }

    private static Cell computeGridCell(Point p, Double dist) {
        // x number of the cell
        int xCell = (int) (p.getX()/Math.floor(dist));
        // y number of the cell
        int yCell = (int) (p.getY()/Math.floor(dist));
        return new Cell(xCell,yCell);
    }

    private static Double computeDistance(Point a, Point b) {
        double xDist = a.getX()-b.getX();
        double yDist = a.getY()-b.getY();
        double xPow = Math.pow(xDist,2);
        double yPow = Math.pow(yDist,2);
        double dist = Math.sqrt(xPow + yPow);
        return dist;
    }

    private static List<Point> shuffle(List<Point> points) {
        //Fisher–Yates shuffle, modern version from Richard Durstenfeld
        Random random = new Random();

        for(int i=points.size()-1; i > 0; i--) {
            //TODO i+1?, bei gleicher Zahl neu wuerfeln?
            int j = random.nextInt(i+1);
            Collections.swap(points, i, j);
        }
        return points;
    }

}
