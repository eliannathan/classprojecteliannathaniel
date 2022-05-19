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

public class purchaseFrame extends JInternalFrame implements ActionListener, MouseListener{
	
	JPanel northPnl, centerPnl, southPnl; 
	TableModel dtm;
	JLabel title, idLbl, nameLbl, typeLbl, priceLbl;
	JButton addBtn;
	JMenuBar menuBar;
	JMenu purchaseItems, checkout;
	JTable table;
	JScrollPane scrollP;
	connect con;	
	ResultSet rs;

	public void initial() {
		//connect
		con = new connect();
		
		//table
		table = new JTable();
		table.addMouseListener(this);
		
		//panel
		northPnl = new JPanel();
		centerPnl = new JPanel();
		southPnl = new JPanel();
		
		//title
		title = new JLabel("Add Items to Cart");
		northPnl.add(title);
		
		refreshTable();	
				
		//button
		addBtn = new JButton("Add to cart");
		addBtn.setPreferredSize(new Dimension(200, 40));
		addBtn.addActionListener(this);
		southPnl.add(addBtn);
		
		southPnl.setBorder(BorderFactory.createEmptyBorder(0,0,50,0));
		add(northPnl, BorderLayout.NORTH);
		add(centerPnl, BorderLayout.CENTER);
		add(southPnl, BorderLayout.SOUTH);
				
	}
	
	public purchaseFrame() {
		initial();
		setVisible(true);
		setSize(785,535);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
	}
	
	public void refreshTable() {
		Object [] columnNames = {"ID","Name", "Type", "Price"};
		dtm = new DefaultTableModel(columnNames,0);
		rs = con.runQuery("SELECT * FROM products");		
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
	
	String temporaryID;
	String temporaryName;
	String temporaryType;
	String temporaryPrice;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addBtn) {
			int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to add this product to your cart?");
			if (option != JOptionPane.YES_OPTION) {
				return;				
			}
			
			String query = String.format(("INSERT INTO cart VALUES('%d', '%s', '%s', '%d')"), 0, temporaryName, temporaryType, Integer.parseInt(temporaryPrice));
			
			boolean cartAddition = con.runUpdate(query);
			
			if(cartAddition) {
				JOptionPane.showMessageDialog(null, "Added to cart!", "Success", JOptionPane.INFORMATION_MESSAGE);
				refreshTable();
			}else {
				JOptionPane.showMessageDialog(null, "Error, something went wrong! try again later.", "Error", JOptionPane.ERROR_MESSAGE);
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
