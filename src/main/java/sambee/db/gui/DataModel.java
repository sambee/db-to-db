package sambee.db.gui;

/**
 * Created by Administrator on 2015/4/4.
 */
public class DataModel {
    private String tableName;
    private boolean createTable;
    private boolean exportData;

    public DataModel(String tableName, boolean createTable, boolean exportData) {
        this.tableName = tableName;
        this.createTable = createTable;
        this.exportData = exportData;
    }

    public Object getValueAt(int columnIndex){
        switch (columnIndex){
            case 0:
               return tableName;
            case 1:
                return createTable;
            case 2:
                return exportData;
            default:
                return "";
        }
    }
}
