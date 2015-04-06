package sambee.db.gui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.spi.LoggerFactory;
import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.BaseDatabase;
import sam.bee.oa.sql.database.Callback;
import sam.bee.oa.sql.database.DatabaseFactory;
import sam.bee.oa.sql.database.DatabaseService;
import sambee.utils.ConfigUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Administrator on 2015/4/4.
 */
public class App {

    private static final Log log = LogFactory.getLog(App.class);
    private static final String FILE_CONFIG_NAME = "jdbc.properties";
    public static void main(String[] args) throws Exception {
        final MainForm form =  new MainForm();
        form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        form.setSize(800, 600);
        form.onStart();
        form.pack();
        try {
            loadSettings(form);
        } catch (Exception ex) {
            form.dispose();
            return;
        }
        form.setVisible(true);
        form.requestFocus();

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == form.getLoadButton()){
                    loadDatabaseInfo(form);
                }
                if(e.getSource() == form.getSettingsButton()){
                    saveConfig(form);
                }
                if(e.getSource() == form.getExecuteButton()){
                    execute(form);
                }
            }
        };
        form.getLoadButton().addActionListener(actionListener);
        form.getSettingsButton().addActionListener(actionListener);
        form.getExecuteButton().addActionListener(actionListener);

        form.getTable().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int cell = form.getTable().getSelectedColumn();
                int row = form.getTable().getSelectedRow();
                TableModel  tableModel = (TableModel) form.getTable().getModel();
                DataModel dataModel = (DataModel) tableModel.getModelAt(row);
                dataModel.changeValue(cell);
                ((TableModel) form.getTable().getModel()).fireTableCellUpdated(row, cell);
            }
        });


        //table header event
        form.getTable().getTableHeader().addMouseListener(new MouseAdapter()  //表头添加事件
        {
            public void mouseClicked(MouseEvent e) {
                int tableColumn = form.getTable().columnAtPoint(e.getPoint());//获取点击的列
//                System.out.println(tableColumn);
                ((TableModel) form.getTable().getModel()).changeAllValue(tableColumn);
                ((TableModel) form.getTable().getModel()).fireTableCellUpdated(tableColumn,tableColumn);
            }
        });

        form.getTable().getColumnModel().getColumn(0).setPreferredWidth(300);
        form.getExecuteButton().setEnabled(false);
    }


    public static void loadSettings(MainForm form){

        try {
            Map<String,String> map = ConfigUtils.loadConfig(FILE_CONFIG_NAME, "src");

            String driver = value(map.get("driver"), "");
            String jdbc = value(map.get("jdbc"), "");
            String user =  value(map.get("user"), "");
            String password =  value(map.get("password"), "");

            map = ConfigUtils.loadConfig("jdbc.properties", "desc");
            String descDriver =  value(map.get("driver"), "");
            String descJdbc =  value(map.get("jdbc"), "");
            String descUser =  value(map.get("user"), "");
            String descPassword = value(map.get("password"), "");

            form.getSrcDriver().setText(driver);
            form.getSrcJDBC().setText(jdbc);
            form.getSrcUser().setText(user);
            form.getSrcPassword().setText(password);


            form.getDescDriver().setText(descDriver);
           form.getDescJDBC().setText(descJdbc);
            form.getDescUser().setText(descUser);
             form.getDescPassword().setText(descPassword);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public static void saveConfig(final MainForm form){

        Map map = new HashMap(){{
            put("src.driver", form.getSrcDriver().getText());
            put("src.jdbc", form.getSrcJDBC().getText());
            put("src.user", form.getSrcUser().getText());
            put("src.password", form.getSrcPassword().getText());

            put("desc.driver", form.getDescDriver().getText());
            put("desc.jdbc", form.getDescJDBC().getText());
            put("desc.user", form.getDescUser().getText());
            put("desc.password", form.getDescPassword().getText());

        }};
        try {
            ConfigUtils.saveConfig(FILE_CONFIG_NAME, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void  loadDatabaseInfo(MainForm form){
        final  String driver = form.getSrcDriver().getText();
        final  String jdbc = form.getSrcJDBC().getText();
        final   String user = form.getSrcUser().getText();
        final   String password = form.getSrcPassword().getText();
        final  TableModel tableModel = (TableModel)form.getTable().getModel();

        Map<String, String> map = new HashMap<String, String>(){{
            put("driver", driver);
            put("jdbc",jdbc);
            put("user",user);
            put("password", password);

        }};
        DatabaseFactory database = DatabaseFactory.getInstance();
        try {
            database.registerDatabase("src", map);
            BaseDatabase db = database.getDatabase("src");
            DatabaseService service = ServiceFactory.getService("src", DatabaseService.class);
            List<Map<String, String>> tablesInfo = service.getAllTables();

            String tableName;
            Vector<DataModel> dataModels = new Vector<DataModel>();
            DataModel dataModel;
            String[] tables = new String[tablesInfo.size()];
            int i=0;
            for(Map<String,String> table :tablesInfo){
                tableName = table.get("TABLE_NAME");
                tables[i++] = tableName;
            }
            Arrays.sort(tables);

            for(i=0; i < tables.length; i++){
                dataModel =new DataModel(tables[i], false,false);
                dataModels.add(dataModel);
            }


            tableModel.setData(dataModels);
            tableModel.fireTableDataChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(form.getTable().getModel().getRowCount()>0){
            form.getExecuteButton().setEnabled(true);
        }
        else{
            form.getExecuteButton().setEnabled(false);
        }
    }

    private static String mTableName;
    public static void execute(MainForm form){
        final String driver = form.getSrcDriver().getText();
        final String jdbc = form.getSrcJDBC().getText();
        final String user = form.getSrcUser().getText();
        final  String password = form.getSrcPassword().getText();


        final String descDriver = form.getDescDriver().getText();
        final  String descJdbc = form.getDescJDBC().getText();
        final String descUser = form.getDescUser().getText();
        final String descPassword = form.getDescPassword().getText();

        Map<String, String> map = new HashMap<String, String>(){{
            put("driver", driver);
            put("jdbc",jdbc);
            put("user",user);
            put("password", password);

        }};

        Map<String, String> desc = new HashMap<String, String>(){{
            put("driver", descDriver);
            put("jdbc",descJdbc);
            put("user",descUser);
            put("password", descPassword);

        }};

        //copy data to the target database.
        try {
            DatabaseFactory.getInstance().registerDatabase("src", map);
            DatabaseFactory.getInstance().registerDatabase("desc", desc);

            DatabaseService srcService = ServiceFactory.getService("src", DatabaseService.class);
            final DatabaseService descService = ServiceFactory.getService("desc", DatabaseService.class);
            TableModel table =(TableModel) form.getTable().getModel();
            List<DataModel> tableInfo = table.getData();
            DataModel dataModel;
            for(int i=0;i<tableInfo.size();i++){

                dataModel = tableInfo.get(i);
                if(dataModel.isCreateTable()){
                    //table name;
                    log.info("Create SQL:" + dataModel.getTableName());
                    String dropSQL = srcService.dropTableSql(descService.getDatabaseType(), dataModel.getTableName());
                    descService.executeSQL(dropSQL);
                    String createSQL = srcService.createTableSql(descService.getDatabaseType(), dataModel.getTableName());

                    descService.executeSQL(createSQL);

                    descService.getData(dataModel.getTableName(), new Callback() {
                        @Override
                        public boolean execute(Map<String, Object> aData) throws Throwable {
                            return false;
                        }
                    });

                }
                if(dataModel.isExportData()){
                    log.info("Copy Data:" + dataModel.getTableName());
                    mTableName = dataModel.getTableName();
                    srcService.getData(dataModel.getTableName(), new Callback() {
                        @Override
                        public boolean execute(Map<String, Object> aData) throws Throwable {
                            aData.remove("RN");
                            descService.saveData(mTableName, aData);
                            return true;
                        }
                    });
                }

            }

            log.info("==================  DONE ==================");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String value(String v, String v2){
        return v==null?v2 : v;
    }



}
