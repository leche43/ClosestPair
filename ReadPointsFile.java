import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadPointsFile {

    public static List<Point> readPointsFromFile(String fileName) {
        BufferedReader br = null;

        List<Point> points = null;
        try {

            String currentLine;
            br = new BufferedReader(new FileReader(fileName));
            points = new ArrayList<Point>();

            while ((currentLine = br.readLine()) != null) {
                Point point = new Point();
                point.setX(Integer.parseInt(currentLine));
                if ((currentLine = br.readLine()) != null) {
                    point.setY(Integer.parseInt(currentLine));
                }
                points.add(point);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return points;
    }
}
