import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomizedGrid {
    /**
     * This computes the closest pair using the randomized algorithm introduces in the lecture E(X)=O(n) 
     */
    public static ClosestPair computeCP(List<Point> points) {
        // 1 Bring list of points in random order
        List<Point> shuffledPoints = shuffle(points);

        // 2 Compute euclidean distance between the first two points
        Double cpDist = ComputeClosestPair.computeEuclideanDistance(shuffledPoints.get(0), shuffledPoints.get(1));
        ClosestPair closestPair = new ClosestPair(shuffledPoints.get(0),shuffledPoints.get(1),cpDist);

        // 3 Construct the grid with grid-size cpDist and the first two points
        List<Point> currentGridPoints = new ArrayList<Point>();
        currentGridPoints.add(shuffledPoints.get(0));
        currentGridPoints.add(shuffledPoints.get(1));
        Map<Cell,List<Point>> grid = computeGrid(currentGridPoints,cpDist);

        // 4 Iterate through the list of points
        for(int i=2; i<shuffledPoints.size();i++) {
            Point currentPoint = shuffledPoints.get(i);
            // Compute cell number of current point
            Cell cell = computeGridCell(currentPoint,cpDist);
            // Inspect the neighbouring cells of the currentPoint and get the adjacent points
            List<Cell> adjacentCells = getAdjacentCells(cell);
            adjacentCells.add(cell);

            List<Point> adjacentPoints = new ArrayList<Point>();
            for(Cell c : adjacentCells) {
                if(grid.containsKey(c)) {
                    adjacentPoints.addAll(grid.get(c));
                }
            }

            // Check if the euclidean distance was updated during the algorithm
            boolean distChanged = false;

            // Check if there is a smaller euclidean distance within the adjacent points
            for(Point adjacentPoint : adjacentPoints) {
                Double currentDist = ComputeClosestPair.computeEuclideanDistance(adjacentPoint, currentPoint);
                if(currentDist < cpDist) {
                    cpDist = currentDist;
                    distChanged = true;
                    closestPair.setA(currentPoint);
                    closestPair.setB(adjacentPoint);
                    closestPair.setDistance(cpDist);
                }
            }

            currentGridPoints.add(currentPoint);

            // recompute grid if cpDist changed
            if(distChanged) {
                grid = computeGrid(currentGridPoints, cpDist);
            } else {
                // Place currentPoint in the grid
                if(grid.containsKey(cell)) {
                    List<Point> list = grid.get(cell);
                    list.add(currentPoint);
                }
                else {
                    List<Point> list = new ArrayList<Point>();
                    list.add(currentPoint);
                    grid.put(cell, list);
                }
            }
        }
        return closestPair;
    }

    /**
     * Returns a list of adjacent cells to a given cell within the grid
     */
    private static List<Cell> getAdjacentCells(Cell cell) {
        Cell cell2 = new Cell(cell.getX()+1,cell.getY());
        Cell cell3 = new Cell(cell.getX()+1,cell.getY()-1);
        Cell cell4 = new Cell(cell.getX(),cell.getY()-1);
        Cell cell5 = new Cell(cell.getX()-1,cell.getY()-1);
        Cell cell6 = new Cell(cell.getX()-1,cell.getY());
        Cell cell7 = new Cell(cell.getX()-1,cell.getY()+1);
        Cell cell8 = new Cell(cell.getX(),cell.getY()+1);
        Cell cell9 = new Cell(cell.getX()+1,cell.getY()+1);

        return new ArrayList<Cell>(Arrays.asList(cell2,cell3,cell4,cell5,cell6,cell7,cell8,cell9));
    }

    /**
     * Constructs the grid given a list of points and the euclidean distance
     */
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

    /**
     * Computes the cell to a given point and euclidean distance
     */
    private static Cell computeGridCell(Point p, Double dist) {
        // x number of the cell
        int xCell = (int) (p.getX()/Math.floor(dist));
        // y number of the cell
        int yCell = (int) (p.getY()/Math.floor(dist));
        return new Cell(xCell,yCell);
    }
    
    /**
     * Fisher–Yates shuffle, modern version from Richard Durstenfeld
     */
    private static List<Point> shuffle(List<Point> points) {
        Random random = new Random();

        for(int i=points.size()-1; i > 0; i--) {
            //TODO i+1?, bei gleicher Zahl neu wuerfeln?
            int j = random.nextInt(i+1);
            Collections.swap(points, i, j);
        }
        return points;
    }
}
