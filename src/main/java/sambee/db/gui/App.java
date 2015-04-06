package sambee.db.gui;

import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.BaseDatabase;
import sam.bee.oa.sql.database.DatabaseFactory;
import sam.bee.oa.sql.database.DatabaseService;
import sambee.utils.ConfigUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Administrator on 2015/4/4.
 */
public class App {


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
            for(Map<String,String> table :tablesInfo){
                tableName = table.get("TABLE_NAME");
                dataModel =new DataModel(tableName, true,true);
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

    }

    public static void execute(MainForm form){
        String driver = form.getSrcDriver().getText();
        String jdbc = form.getSrcJDBC().getText();
        String user = form.getSrcUser().getText();
        String password = form.getSrcPassword().getText();


        String descDriver = form.getDescDriver().getText();
        String descJdbc = form.getDescJDBC().getText();
        String descUser = form.getDescUser().getText();
        String descPassword = form.getDescPassword().getText();
    }

    public static String value(String v, String v2){
        return v==null?v2 : v;
    }



}
