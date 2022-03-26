import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.util.*;

public class ClusterPanel extends JPanel {
    private final Color FIRST_COLOR = new Color(67, 40, 24);
    private final Color SECOND_COLOR = new Color(251, 255, 254);
    private final Color THIRD_COLOR = new Color(196, 173, 108);
    private final Color FOURTH_COLOR = new Color(103, 116, 35);
    private final Color FIFTH_COLOR = new Color(38, 70, 83);
    private final Color SIXTH_COLOR = new Color(231, 111, 81);
    private final Color SEVENTH_COLOR = new Color(183, 110, 120);
    private final Color EIGHTH_COLOR = new Color(196,187,175);
    private final Color NINETH_COLOR = new Color(45, 30, 47);
    private final Color TENTH_COLOR = new Color(166, 141,  114);

    private ArrayList<ArrayList<Point>> clusters;
    private ArrayList<Point> centers;
    private Color[] colors;

    public ClusterPanel(File inputFile, int numOfCenter, int numOfIteration) {
        super();
        this.setBackground(Color.lightGray);
        this.colors = getColorArray();

        Cluster clust = new Cluster(inputFile, numOfCenter, numOfIteration);
        clusters = clust.getClusters();
        centers = clust.getCenters();
    }

    private Color[] getColorArray() {
        Color[] colorArray = new Color[10];
        colorArray[0] = FIRST_COLOR;
        colorArray[1] = SECOND_COLOR;
        colorArray[2] = THIRD_COLOR;
        colorArray[3] = FOURTH_COLOR;
        colorArray[4] = FIFTH_COLOR;
        colorArray[5] = SIXTH_COLOR;
        colorArray[6] = SEVENTH_COLOR;
        colorArray[7] = EIGHTH_COLOR;
        colorArray[8] = NINETH_COLOR;
        colorArray[9] = TENTH_COLOR;
        return colorArray;

    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for(int i = 0; i < clusters.size(); i++) {
            for(int j = 0; j< clusters.get(i).size(); j++) {
                int x = (int) clusters.get(i).get(j).getX();
                int y = (int) clusters.get(i).get(j).getY();
                g2d.setColor(colors[i]);
                g2d.fillOval(x, y, 7, 7);
            }
        }
        for(int i = 0; i < centers.size(); i++) {
                int x = (int)centers.get(i).getX();
                int y = (int)centers.get(i).getY();
                g2d.setColor(Color.black);
                g2d.fillOval(x,y,10,10);
        }
    }
}