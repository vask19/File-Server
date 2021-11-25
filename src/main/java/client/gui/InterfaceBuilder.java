package client.gui;


import client.gui.components.ServerTable;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InterfaceBuilder {
    private JFrame jFrame;
    private JPanel jPanel;
    private JTextField fileNameOrIdTextField;
    private JButton buttonToSearchForFile;
    private JRadioButton getButton;
    private JRadioButton putButton;
    private JRadioButton deleteButton;
    private JScrollPane tableWithServerFiles;
    private ButtonGroup buttonGroup;
    private JFileChooser fileChooser;
    private JButton send;


    public InterfaceBuilder(JRadioButton getButton, JRadioButton putButton,
                            JRadioButton deleteButton,
                            JScrollPane tableWithServerFiles,JButton send
                            ,JTextField fileNameOrIdTextField
                            ) {
        this.jFrame = getFrame();
        this.jPanel = new JPanel();
        jFrame.add(jPanel);
        this.fileNameOrIdTextField = fileNameOrIdTextField;
        this.buttonToSearchForFile = new JButton();
        this.buttonGroup = new ButtonGroup();
        this.fileChooser = new JFileChooser();
        initializeFileChooser();
        this.getButton = getButton;
        this.putButton = putButton;
        this.deleteButton = deleteButton;
        this.tableWithServerFiles = tableWithServerFiles;
        this.send = send;
        addComponentsToLayout();



    }

    public void revalidate(Map<Integer,String>  map){
        tableWithServerFiles.setViewportView(new ServerTable().getTable(map));
        tableWithServerFiles.revalidate();

        tableWithServerFiles.repaint();

        jPanel.revalidate();
        jPanel.repaint();
    }



    private void addComponentsToLayout(){
        jPanel.setLayout(new FileServerLayout());
        this.buttonGroup.add(putButton);
        this.buttonGroup.add(getButton);
        this.buttonGroup.add(deleteButton);
        this.jPanel.add(tableWithServerFiles);
        this.jPanel.add(buttonToSearchForFile);
        this.jPanel.add(putButton);
        this.jPanel.add(getButton);
        this.jPanel.add(deleteButton);
        this.jPanel.add(fileNameOrIdTextField);
        this.jPanel.add(send);


    }


















    private void initializeFileChooser(){
        SwingUtilities.invokeLater(() -> {
            buttonToSearchForFile.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                int res = fileChooser.showOpenDialog(jFrame);
                if (res == JFileChooser.APPROVE_OPTION) {
                    fileNameOrIdTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            });
        });

    }

    public  JFrame getFrame(){
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        jFrame.setBounds(dimension.width/2 - 250,dimension.height/2 -150,500,300);

        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jFrame;

    }




    static class FileServerLayout implements LayoutManager {

        @Override
        public void addLayoutComponent(String name, Component comp) {

        }

        @Override
        public void removeLayoutComponent(Component comp) {

        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            return null;
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return null;
        }

        @Override
        public void layoutContainer(Container parent) {
            Component component1 = parent.getComponent(0);
            Component component2 = parent.getComponent(1);
            Component component3 = parent.getComponent(2);
            Component component4 = parent.getComponent(3);
            Component component5 = parent.getComponent(4);
            Component component6 = parent.getComponent(5);
            Component component7 = parent.getComponent(6);

            component1.setBounds(0,0,200,300);
            component1.setBackground(Color.WHITE);
            component2.setBounds(200,0,200,300);
            component3.setBounds(0,300,100,50);
            component4.setBounds(100,300,100,50);
            component5.setBounds(200,300,100,50);

            component6.setBounds(0,350,400,50);
            component7.setBounds(0,400,400,50);






        }

    }
}
