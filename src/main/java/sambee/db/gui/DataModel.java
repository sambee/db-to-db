package sambee.db.gui;

/**
 * Created by Administrator on 2015/4/4.
 */
public class DataModel {
    private String tableName;
    private boolean createTable;
    private boolean exportData;
    private boolean status;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean isCreateTable() {
        return createTable;
    }

    public void setCreateTable(boolean createTable) {
        this.createTable = createTable;
    }

    public boolean isExportData() {
        return exportData;
    }

    public void setExportData(boolean exportData) {
        this.exportData = exportData;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

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
            case 3:
                return status;
            default:
                return "";
        }
    }

    public void changeValue(int columnIndex){
        switch (columnIndex){
            case 1:
                createTable=!createTable;
                break;
            case 2:
                exportData=!exportData;
                break;
            case 3:
                status=!status;
        }
    }

    public void changeValue(int columnIndex, boolean status){
        switch (columnIndex){
            case 1:
                this.createTable=status;
                break;
            case 2:
                this.exportData=status;
                break;
            case 3:
                this.status=status;
        }
    }
}
