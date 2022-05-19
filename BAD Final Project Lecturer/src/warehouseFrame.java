import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class warehouseFrame extends JInternalFrame implements ActionListener, MouseListener{

	JPanel northPnl, centerPnl, southPnl, inputPnl, buttonsPnl; 
	TableModel dtm;
	JLabel idLbl,nameLbl,typeLbl,priceLbl;
	JTextField idTxt,nameTxt,typeTxt,priceTxt;
	JButton add,update,delete;
	JTable table;
	JLabel title;
	JScrollPane scrollP;		
	connect con;
	
	private void initial() {
		//connect
		con = new connect();
		
		//table
		table = new JTable();
		table.addMouseListener(this);
		
		//panel
		northPnl = new JPanel();
		centerPnl = new JPanel();
		southPnl = new JPanel(new GridLayout(2,1));
		inputPnl = new JPanel(new GridLayout(4,2));
		buttonsPnl = new JPanel(new GridLayout(1,3));

		//label
		title = new JLabel("Add Items to Menu");
		northPnl.add(title);
		
		refreshTable();	
		
		scrollP = new JScrollPane(table);
		scrollP.setPreferredSize(new Dimension(600,300));
		centerPnl.add(scrollP);
		
		//label
		idLbl = new JLabel("ID");
		nameLbl = new JLabel("Name");
		typeLbl = new JLabel("Type");
		priceLbl = new JLabel("Price");
		
		//text fields
		idTxt = new JTextField();
		nameTxt = new JTextField();
		typeTxt = new JTextField();
		priceTxt = new JTextField();
		
		//add label & text fields
		inputPnl.add(idLbl);inputPnl.add(idTxt);
		inputPnl.add(nameLbl);inputPnl.add(nameTxt);
		inputPnl.add(typeLbl);inputPnl.add(typeTxt);
		inputPnl.add(priceLbl);inputPnl.add(priceTxt);
		
		//buttons
		add = new JButton("Add Items");
		add.addActionListener(this);
		update = new JButton("Update Items");
		update.addActionListener(this);
		delete = new JButton("Delete Items");
		delete.addActionListener(this);
		
		buttonsPnl.add(add);
		buttonsPnl.add(update);
		buttonsPnl.add(delete);
		southPnl.add(inputPnl);
		southPnl.add(buttonsPnl);
		
		//add panels
		add(northPnl, BorderLayout.NORTH);
		add(centerPnl, BorderLayout.CENTER);
		add(southPnl, BorderLayout.SOUTH);
	
	}

	public void refreshTable() {
		Object [] columnNames = {"ID","Name", "Type", "Price"};
		dtm = new DefaultTableModel(columnNames,0);
		ResultSet rs = con.runQuery("SELECT * FROM products");		
		try {
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String type = rs.getString("type");
				int price = rs.getInt("price");
				
				Vector<Object> dataRow = new Vector<>();
				dataRow.add(id);
				dataRow.add(name);
				dataRow.add(type);
				dataRow.add(price);
				
				((DefaultTableModel) dtm).addRow(dataRow);
			}
			table.setModel(dtm);
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public warehouseFrame() {
		initial();
		setVisible(true);
		setSize(785,535);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == add) {
			int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to add this product to the list?");
			if (option != JOptionPane.YES_OPTION) {
				return;				
			}			
			String id = idTxt.getText();
			String name = nameTxt.getText();
			String type = typeTxt.getText();
			String price = priceTxt.getText();
					
			String query = String.format(("INSERT INTO products VALUES(%d, '%s', '%s', '%d')"), Integer.parseInt(id), name, type, Integer.parseInt(price));
			
			boolean addSuccess = con.runUpdate(query);
			
			if(addSuccess) {
				JOptionPane.showMessageDialog(null, "Insert Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
				idTxt.setText("");
				nameTxt.setText("");
				typeTxt.setText("");
				priceTxt.setText("");
				refreshTable();
			}else {
				JOptionPane.showMessageDialog(null, "Insert Failed", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if(e.getSource() == update) {
			int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this product to the list?");
			if (option != JOptionPane.YES_OPTION) {
				return;
				
			}
			int updateID = Integer.parseInt(idTxt.getText());
			String updateName = nameTxt.getText();
			String updateType = typeTxt.getText();
			int updatePrice = Integer.parseInt(priceTxt.getText());
			
			String query = "UPDATE products SET id = '"+updateID+"', name = '"+updateName+"', type = '"+updateType+"', price = '"+updatePrice+"' WHERE id = '"+updateID+"';"; 
			
			boolean updateSuccess = con.runUpdate(query);
			
			if(updateSuccess) {
				JOptionPane.showMessageDialog(null, "Update Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
				idTxt.setText("");
				nameTxt.setText("");
				typeTxt.setText("");
				priceTxt.setText("");
				refreshTable();
			}else {
				JOptionPane.showMessageDialog(null, "Update Failed", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if(e.getSource() == delete) {
			int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this product to the list?");
			if (option != JOptionPane.YES_OPTION) {
				return;
				
			}
			int deleteID = Integer.parseInt(idTxt.getText());
			String query = "DELETE FROM products WHERE id = '"+deleteID+"';";
			
			boolean deleteSuccess = con.runUpdate(query);
			
			if(deleteSuccess) {
				JOptionPane.showMessageDialog(null, "Product Deleted", "Success", JOptionPane.INFORMATION_MESSAGE);
				idTxt.setText("");
				nameTxt.setText("");
				typeTxt.setText("");
				priceTxt.setText("");
				refreshTable();
			}else {
				JOptionPane.showMessageDialog(null, "Deletion Failed", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == table) {
			String temporaryID = table.getValueAt(table.getSelectedRow(), 0).toString();
			String temporaryName = table.getValueAt(table.getSelectedRow(), 1).toString();
			String temporaryType = table.getValueAt(table.getSelectedRow(), 2).toString();
			String temporaryPrice = table.getValueAt(table.getSelectedRow(), 3).toString();
			
			idTxt.setText(temporaryID);
			nameTxt.setText(temporaryName);
			typeTxt.setText(temporaryType);
			priceTxt.setText(temporaryPrice);
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
