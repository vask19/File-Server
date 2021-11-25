package client.gui.model;

import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerTableModel extends AbstractTableModel {
    private final int columnCount = 2;
    private ArrayList<String []> data;


    public ServerTableModel() {
        data = new ArrayList<>();
        for (int i =0;i<data.size();i++){
            data.add(new String[getColumnCount()]);

        }

    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String[] rows = data.get(rowIndex);
        return rows[columnIndex];


    }

    @Override
    public String getColumnName(int column) {
        return switch (column){
            case 0 -> "Id:";
            case 1 -> "Name:";
            default -> throw new IllegalStateException("Unexpected value: " + columnCount);
        };



    }

    public void addDate(String[] row){
        String[] rowTable = new String[getColumnCount()];
        rowTable = row;
        data.add(rowTable);
    }



}
