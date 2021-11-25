package client.gui.components;

import client.gui.model.ServerTableModel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Map;

public class ServerTable {
   private ServerTableModel stm = new ServerTableModel();
   private JTable jTable = new JTable(stm);



    public JScrollPane getTable(Map<Integer,String > map){
        JScrollPane jScrollPane = new JScrollPane(jTable);
        jScrollPane.setPreferredSize(new Dimension(200,200));
        for(Map.Entry<Integer,String > entry: map.entrySet()) {
            String [] str = new String[2];
            str[0] = String.valueOf(entry.getKey());
            str[1] = entry.getValue();
            stm.addDate(str);
        }
        return jScrollPane;
    }
}



/* public static void main(String[] args) {
        JFrame jFrame = Frame.getFrame();
        JPanel jPanel = new JPanel();
        jFrame.add(jPanel);
        JScrollPane jScrollPane = getTable(Test.getMap());
        jFrame.add(jScrollPane,
                new GridBagConstraints(0,0,1,1,0,0,
                        GridBagConstraints.NORTH,GridBagConstraints.BOTH,
                        new Insets(1,1,1,1),0,0));

        jPanel.revalidate();
    }*/
