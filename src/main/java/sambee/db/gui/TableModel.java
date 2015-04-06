package sambee.db.gui;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

/**
 * Created by Administrator on 2015/4/4.
 */
public class TableModel extends AbstractTableModel {

    private Vector<DataModel> data = new Vector<DataModel>();
    private static final String ITEMS[] = {"Table Name","Create Table","Export Data", "Status"};

    public TableModel( ){
    }
    @Override
    public String getColumnName(int column) {
        return ITEMS[column];
    }
    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return ITEMS.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < data.size()) {
            return ((DataModel) data.elementAt(rowIndex)).getValueAt(columnIndex);
        } else {
            return null;
        }
    }

    public void setData(Vector<DataModel> data){
       this.data = data;
    }
}
