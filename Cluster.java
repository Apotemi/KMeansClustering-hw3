import java.awt.*;
import java.util.*;
import java.io.*;

public class Cluster {
    private File inputFile;
    private int numOfIteration;
    private int numOfCenter;
    private ArrayList<Point> points;
    private ArrayList<Point> centers;
    private ArrayList<ArrayList<Point>> clusters;

    public Cluster(File inputFile, int numOfCenter, int numOfIteration) {
        this.inputFile = inputFile;
        this.numOfCenter = numOfCenter;
        this.numOfIteration = numOfIteration;
        this.points = getPoints(inputFile);

        this.clusters = new ArrayList<ArrayList<Point>>(numOfCenter);
        for(int i = 0; i < numOfCenter; i++)
            clusters.add(new ArrayList<>(points.size()/numOfCenter));

        centers = new ArrayList<Point>(numOfCenter);
        for(int i = 0; i < numOfCenter; i++) {
            int x = (int)(1280*Math.random());
            int y = (int)(720*Math.random());
            centers.add(new Point(x,y));
        }
        changingCenters();
        clustering();
    }

    private void changingCenters() {
        for(int i = 0; i < clusters.size(); i++)
            clusters.get(i).clear();

        for(int i = 0; i < points.size(); i++) {
            int index = 0;
            double min = points.get(i).distance(centers.get(0));

            for(int j = 1; j < centers.size(); j++) {
                if(min > points.get(i).distance(centers.get(j))) {
                    min = points.get(i).distance(centers.get(j));
                    index = j;
                }
            }
            clusters.get(index).add(points.get(i));
        }
    }

    private void clustering() {
        int x = 0;
        int y= 0;
        int j= 0;

        for(int k = 0; k < numOfIteration; k++) {
            for (int i = 0; i < clusters.size(); i++) {
                for (j = 0; j < clusters.get(i).size(); j++) {
                    x += (int) clusters.get(i).get(j).getX();
                    y += (int) clusters.get(i).get(j).getY();
                }
                centers.get(i).setLocation(x/j,y/j);
                x = 0;
                y = 0;
            }
        changingCenters();
        }
    }

    private ArrayList<Point> getPoints(File inputFile) {
        ArrayList<Point> points = new ArrayList<>(100);

        Scanner inputStream = null;

        try {
            inputStream = new Scanner(inputFile);
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        while(inputStream.hasNext()) {
            String[] temp = inputStream.next().split(",");
            int x = Integer.parseInt(temp[0]);
            int y = Integer.parseInt(temp[1]);

            points.add(new Point(x,y));
        }

        points.trimToSize();
        return points;
    }

    public ArrayList<ArrayList<Point>> getClusters() {
        return clusters;
    }
    public ArrayList<Point> getCenters() {
        return centers;
    }
}
