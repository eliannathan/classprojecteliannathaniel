import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class checkoutFrame extends JInternalFrame implements ActionListener, MouseListener{

	JPanel northPnl, centerPnl, southPnl; 
	TableModel dtm;
	JLabel title, totalPrice;
	JMenuBar menuBar;
	JButton buyBtn, removeBtn;
	JMenu purchaseItems, checkout;
	JTable table;
	JScrollPane scrollP;
	connect con;
	ResultSet rs;
	Object [] columnNames = {"ID","Name", "Type", "Price"};
		
	public void initial() {	
		//connect
		con = new connect();
		
		//panel
		northPnl = new JPanel();
		centerPnl = new JPanel();
		southPnl = new JPanel();
		
		//label
		title = new JLabel("Checkout");
		totalPrice = new JLabel("Total Price is:");
		northPnl.add(title);
		
		//table
		table = new JTable();
		table.addMouseListener(this);
		
		refreshTable();
		
		//button
		buyBtn = new JButton("Buy");
		buyBtn.setPreferredSize(new Dimension(200, 50));
		buyBtn.addActionListener(this);
		removeBtn = new JButton("Remove from cart");
		removeBtn.setPreferredSize(new Dimension (200,50));
		removeBtn.addActionListener(this);
		southPnl.add(removeBtn);
		southPnl.add(buyBtn);
		
		southPnl.setBorder(BorderFactory.createEmptyBorder(0,0,50,0));
		add(northPnl, BorderLayout.NORTH);
		add(centerPnl, BorderLayout.CENTER);
		add(southPnl, BorderLayout.SOUTH);
	}
	
	public checkoutFrame() {
		initial();
		setVisible(true);
		setSize(785,535);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
	}
	
	public void refreshTable() {
		Object [] columnNames = {"ID","Name", "Type", "Price"};
		dtm = new DefaultTableModel(columnNames,0);
		rs = con.runQuery("SELECT * FROM cart");		
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
		scrollP = new JScrollPane(table);
		scrollP.setPreferredSize(new Dimension(600,350));
		centerPnl.add(scrollP);
	}

	int id;
	String name;
	String type;
	int price;
	boolean buySuccess;
	int deleteID;	
	String temporaryID;
	String temporaryName;
	String temporaryType;
	String temporaryPrice;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buyBtn) {
			int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to finish this purchase?");
			if (option != JOptionPane.YES_OPTION) {
				return;				
			}
			rs = con.runQuery("SELECT * FROM cart");
			String query = String.format(("INSERT INTO history (id, name, type, price) SELECT id, name, type, price FROM cart"));
			
			buySuccess = con.runUpdate(query);
			
			if (buySuccess = true) {
				JOptionPane.showMessageDialog(null, "Purchase Complete! :)");
				con.runUpdate("DELETE FROM cart");
				refreshTable();
			} else {
				JOptionPane.showMessageDialog(null, "Purchase Failed!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == removeBtn) {
			int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this product from your cart?");
			if (option != JOptionPane.YES_OPTION) {
				return;
				
			}
			int deleteID = Integer.parseInt(temporaryID);
			String query = "DELETE FROM cart WHERE id = '"+deleteID+"';";
			
			boolean deleteSuccess = con.runUpdate(query);
			
			if(deleteSuccess) {
				JOptionPane.showMessageDialog(null, "Removed from cart", "Success", JOptionPane.INFORMATION_MESSAGE);
				refreshTable();
			}else {
				JOptionPane.showMessageDialog(null, "Failed to remove", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
		
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == table) {
			temporaryID = table.getValueAt(table.getSelectedRow(), 0).toString();
			temporaryName = table.getValueAt(table.getSelectedRow(), 1).toString();
			temporaryType = table.getValueAt(table.getSelectedRow(), 2).toString();
			temporaryPrice = table.getValueAt(table.getSelectedRow(), 3).toString();
			
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



