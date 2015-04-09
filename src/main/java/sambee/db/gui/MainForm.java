package sambee.db.gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.TableColumn;

import com.jgoodies.forms.layout.*;
/*
 * Created by JFormDesigner on Sat Apr 04 13:48:28 CST 2015
 */



/**
 * @author samwong
 */
public class MainForm extends JFrame {

    public MainForm(){
        initComponents();
    }
 	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		label1 = new JLabel();
		scrollPane1 = new JScrollPane();
		tblTables = new JTable();
		cmbSrc = new JComboBox();
		label2 = new JLabel();
		txtSrcDriver = new JTextField();
		label3 = new JLabel();
		txtSrcJDBC = new JTextField();
		label4 = new JLabel();
		txtSrcUser = new JTextField();
		label5 = new JLabel();
		txtSrcPassword = new JTextField();
		btnLoad = new JButton();
		btnSettings = new JButton();
		cmbDesc = new JComboBox();
		label10 = new JLabel();
		label6 = new JLabel();
		txtDescDriver = new JTextField();
		label7 = new JLabel();
		txtDescJDBC = new JTextField();
		label8 = new JLabel();
		txtDescUser = new JTextField();
		label9 = new JLabel();
		txtDescPassword = new JTextField();
		btnExecute = new JButton();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new FormLayout(
			"3*(default, $lcgap), 35dlu, 4*($lcgap, default), $lcgap, 17dlu, $lcgap, 94dlu, 6*($lcgap, default)",
			"16*(default, $lgap), 121dlu, 3*($lgap, default)"));

		//---- label1 ----
		label1.setText("Database Source");
		contentPane.add(label1, cc.xywh(7, 3, 21, 1));

		//======== scrollPane1 ========
		{
			scrollPane1.setViewportView(tblTables);
		}
		contentPane.add(scrollPane1, cc.xywh(29, 3, 1, 31));
		contentPane.add(cmbSrc, cc.xywh(7, 5, 19, 1));

		//---- label2 ----
		label2.setText("Driver");
		contentPane.add(label2, cc.xy(7, 7));
		contentPane.add(txtSrcDriver, cc.xywh(11, 7, 15, 1));

		//---- label3 ----
		label3.setText("JDBC");
		contentPane.add(label3, cc.xy(7, 9));
		contentPane.add(txtSrcJDBC, cc.xywh(11, 9, 15, 1));

		//---- label4 ----
		label4.setText("User");
		contentPane.add(label4, cc.xy(7, 11));
		contentPane.add(txtSrcUser, cc.xywh(11, 11, 15, 1));

		//---- label5 ----
		label5.setText("Password");
		contentPane.add(label5, cc.xy(7, 13));
		contentPane.add(txtSrcPassword, cc.xywh(11, 13, 15, 1));

		//---- btnLoad ----
		btnLoad.setText("Load");
		contentPane.add(btnLoad, cc.xywh(7, 15, 11, 1));

		//---- btnSettings ----
		btnSettings.setText("Save Settings");
		contentPane.add(btnSettings, cc.xywh(19, 15, 3, 1));
		contentPane.add(cmbDesc, cc.xywh(7, 19, 19, 1));

		//---- label10 ----
		label10.setText("Database Desc");
		contentPane.add(label10, cc.xywh(7, 21, 13, 1));

		//---- label6 ----
		label6.setText("Driver");
		contentPane.add(label6, cc.xy(7, 23));
		contentPane.add(txtDescDriver, cc.xywh(11, 23, 15, 1));

		//---- label7 ----
		label7.setText("JDBC");
		contentPane.add(label7, cc.xywh(7, 25, 5, 1));
		contentPane.add(txtDescJDBC, cc.xywh(11, 25, 15, 1));

		//---- label8 ----
		label8.setText("User");
		contentPane.add(label8, cc.xy(7, 27));
		contentPane.add(txtDescUser, cc.xywh(11, 27, 15, 1));

		//---- label9 ----
		label9.setText("Password");
		contentPane.add(label9, cc.xy(7, 29));
		contentPane.add(txtDescPassword, cc.xywh(11, 29, 15, 1));

		//---- btnExecute ----
		btnExecute.setText("Execute");
		contentPane.add(btnExecute, cc.xywh(7, 31, 11, 1));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel label1;
	private JScrollPane scrollPane1;
	private JTable tblTables;
	private JComboBox cmbSrc;
	private JLabel label2;
	private JTextField txtSrcDriver;
	private JLabel label3;
	private JTextField txtSrcJDBC;
	private JLabel label4;
	private JTextField txtSrcUser;
	private JLabel label5;
	private JTextField txtSrcPassword;
	private JButton btnLoad;
	private JButton btnSettings;
	private JComboBox cmbDesc;
	private JLabel label10;
	private JLabel label6;
	private JTextField txtDescDriver;
	private JLabel label7;
	private JTextField txtDescJDBC;
	private JLabel label8;
	private JTextField txtDescUser;
	private JLabel label9;
	private JTextField txtDescPassword;
	private JButton btnExecute;
	// JFormDesigner - End of variables declaration  //GEN-END:variables


    public JTable getTable(){
        return tblTables;
    }

    public JButton getLoadButton(){
        return btnLoad;
    }

    public JButton getSettingsButton(){
        return btnSettings;
    }

    public JButton getExecuteButton(){
        return btnExecute;
    }

    public JTextField getSrcDriver(){
        return txtSrcDriver;
    }

    public JTextField getSrcPassword(){
        return txtSrcPassword;
    }

    public JTextField getSrcUser(){
        return txtSrcUser;
    }

    public JTextField getSrcJDBC(){
        return txtSrcJDBC;
    }

    public JTextField getDescPassword(){
        return txtDescPassword;
    }

    public JTextField getDescUser(){
        return txtDescUser;
    }

    public JTextField getDescDriver(){
        return txtDescDriver;
    }
    public JTextField getDescJDBC(){
        return txtDescJDBC;
    }

    public void onStart(){


//        for(int i=0;i<10;i++) {
//            list.add(new DataModel("tablename", true, true));
//        }

		TableModel tableModel = new TableModel();
		tableModel.setData(new Vector<DataModel>());
		tblTables.setModel(tableModel);

		TableColumn aColumn   =   tblTables.getColumnModel().getColumn(1);
		aColumn.setCellEditor(tblTables.getDefaultEditor(Boolean.class));
		aColumn.setCellRenderer(tblTables.getDefaultRenderer(Boolean.class));


		aColumn   =   tblTables.getColumnModel().getColumn(2);
		aColumn.setCellEditor(tblTables.getDefaultEditor(Boolean.class));
		aColumn.setCellRenderer(tblTables.getDefaultRenderer(Boolean.class));

		aColumn   =   tblTables.getColumnModel().getColumn(3);
		aColumn.setCellEditor(tblTables.getDefaultEditor(Boolean.class));
		aColumn.setCellRenderer(tblTables.getDefaultRenderer(Boolean.class));

    }

	public JComboBox getComboDesc() {
		return cmbDesc;
	}


	public JComboBox getComboSrc() {
		return cmbSrc;
	}

	public void clearStatus(){
		TableModel tbModel =  (TableModel)tblTables.getModel();
		Vector<DataModel>  data = tbModel.getData();
		for(int i=0;i<data.size();i++){
			data.get(i).setStatus(false);
		}
		((TableModel) tblTables.getModel()).fireTableDataChanged();
	}
}
