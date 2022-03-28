/**
    03.30.2022
    Bil211 Homework-3
    
    Yusuf Aydın 211101019
    yusufaydin@etu.edu.tr
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class KMeansClusterGUI extends JFrame {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private File selectedFile;
    private int numOfIteration;
    private int numOfCenter;
    private JPanel mainPanel;

    public KMeansClusterGUI() {
        super();
        this.setTitle("K-Means Clustering");
        this.setSize(WIDTH,HEIGHT);
        this.getContentPane().setBackground(Color.lightGray);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.mainPanel = new JPanel(); //We will change this panel to avoid adding multiple panels into same frame.

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
                    numOfIteration = 0;
                }
            }
        });

        Integer[] options = {1,2,3,4,5,6,7,8,9,10};
        JComboBox<Integer> menuBar = new JComboBox<>(options);
        menuBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                numOfCenter = menuBar.getItemAt(menuBar.getSelectedIndex());
            }
        });

        JButton openFileButton = new JButton("Dosyadan sec");
        openFileButton.addActionListener(new FileOpener());

        JButton clusteringButton = new JButton("K-Means Clustering");
        clusteringButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    remove(mainPanel); //to avoid adding multiple panels into same frame.
                    mainPanel = new KMeansClusterPanel(selectedFile, numOfCenter, numOfIteration);
                    add(mainPanel, BorderLayout.CENTER);
                    revalidate();

                } catch (NullPointerException e) {
                    JOptionPane.showMessageDialog(null,"Kumelendirme yapilacak veriler icin " +
                            "\"csv\" dosyasi secmelisiniz");
                } catch (IndexOutOfBoundsException e) {
                    JOptionPane.showMessageDialog(null,"Kumelendirme yapilacak veriler icin " +
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