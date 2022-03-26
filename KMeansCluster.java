import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class KMeansCluster extends JFrame {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private File selectedFile;
    private int numOfIteration;
    private int numOfCenter;

    public KMeansCluster() {
        super();
        this.setTitle("K-Means Clustering");
        this.setSize(WIDTH,HEIGHT);
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.getContentPane().setBackground(Color.lightGray);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        JLabel iterationLabel = new JLabel("Iterasyon: ");
        JLabel centerLabel = new JLabel("K sayisi (CENTER): ");

        JTextField iterationField = new JTextField(4);
        iterationField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    numOfIteration = Integer.parseInt(iterationField.getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Iterasyon icin yalnizca tam sayi girebilirsiniz," +
                            " aksi taktirde varsayilan iterasyon 0 olarak belirlenir.");
                }
            }
        });

        //MERKEZ SECIM EKRANI ORNEKTEKI GIBI OLUSMUYOR!!!!
        JMenuBar menuBar = new JMenuBar();
        JMenu centerMenu = new JMenu();

        for (int i = 1; i <= 10; i++) {
            JMenuItem item = new JMenuItem(Integer.toString(i));
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    numOfCenter = Integer.parseInt(actionEvent.getActionCommand());
                }
            });
            centerMenu.add(item);
        }
        menuBar.add(centerMenu);

        JButton openFileButton = new JButton("Dosyadan sec");
        openFileButton.addActionListener(new FileOpener());

        JButton clusteringButton = new JButton("K-Means Clustering");
        clusteringButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {

                    ClusterPanel mainPanel = new ClusterPanel(selectedFile, numOfCenter, numOfIteration);
                    add(mainPanel, BorderLayout.CENTER);
                    pack();

                } catch (NullPointerException e) {
                    JOptionPane.showMessageDialog(null,"Kumelendirme yapilacak veriler icin " +
                            "\"csv\" dosyasi secmelisiniz");
                } catch (IndexOutOfBoundsException e) {
                    JOptionPane.showMessageDialog(null,"Kumelendirme yapilacak veriler icin " + "" +
                            "Merkez ve Iterasyon sayisi girmelisiniz.");
                }

            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        bottomPanel.add(iterationLabel);
        bottomPanel.add(iterationField);
        bottomPanel.add(centerLabel);
        bottomPanel.add(menuBar);
        bottomPanel.add(openFileButton);
        bottomPanel.add(clusteringButton);

        this.add(bottomPanel, BorderLayout.SOUTH);

    }

    private class FileOpener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION)
                selectedFile = fileChooser.getSelectedFile();

            String fileName = selectedFile.getName();
            if (!fileName.substring(fileName.indexOf(".") + 1).equals("csv")) {
                JOptionPane.showMessageDialog(null, ".csv disinda bir dosya secemezsiniz!");
                selectedFile = null;
            }
        }
    }
}