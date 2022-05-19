import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class cashierFrame extends JInternalFrame implements ActionListener{

	JPanel northPnl, centerPnl,southPnl; 
	JTextField registry;
	TableModel dtm;
	JScrollPane scrollP;
	JTable table;
	JLabel registerLbl;
	JButton countTotal, emptyHistory;
	connect con;
	ResultSet rs;
	int amount = 0, regBalance = 0;
			
	public void initial() {
		//connect
		con = new connect();
				
		//table
		table = new JTable();
		
		//panel
		northPnl = new JPanel();
		centerPnl = new JPanel();
		southPnl = new JPanel();
		
		//label
		registerLbl = new JLabel("Total Funds: ");
		
		//text field
		registry = new JTextField();
		registry.setPreferredSize(new Dimension(150, 20));
		
		historyTable();
	
		//button
		countTotal = new JButton("Calculate Total Funds");
		countTotal.addActionListener(this);
		emptyHistory = new JButton("Empty History");
		emptyHistory.addActionListener(this);
		
		southPnl.setBorder(BorderFactory.createEmptyBorder(0,0,50,0));
		northPnl.add(countTotal);
		northPnl.add(emptyHistory);
		southPnl.add(registerLbl);
		southPnl.add(registry);
				
		add(northPnl, BorderLayout.NORTH);
		add(centerPnl, BorderLayout.CENTER);
		add(southPnl, BorderLayout.SOUTH);
					
	}

	public void historyTable() {
		Object [] columnNames = {"ID","Name", "Type", "Price"};
		dtm = new DefaultTableModel(columnNames,0);
		rs = con.runQuery("SELECT * FROM history");		
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
		//scroll
		scrollP = new JScrollPane(table);
		scrollP.setPreferredSize(new Dimension(600,300));
		centerPnl.add(scrollP);
	}
	
	public cashierFrame() {
		initial();
		setVisible(true);
		setSize(785,535);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == emptyHistory) {
			int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear the history table?");
			if (option != JOptionPane.YES_OPTION) {
				return;
				
			}
			String query = "DELETE FROM history";
			
			boolean deleteSuccess = con.runUpdate(query);
			
			if(deleteSuccess) {
				JOptionPane.showMessageDialog(null, "History Cleared", "Success", JOptionPane.INFORMATION_MESSAGE);
				historyTable();
			}else {
				JOptionPane.showMessageDialog(null, "Deletion Failed", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}else if(e.getSource() == countTotal) {
			int option = JOptionPane.showConfirmDialog(null, "Count total funds?");
			if (option != JOptionPane.YES_OPTION) {
				return;
				
			}
			String query = "SELECT SUM(price) AS total_price FROM history";
			
			rs = con.runQuery(query);
			
			try {
				while(rs.next()) {
					regBalance = rs.getInt("total_price");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String regBalanceString;
			regBalanceString = Integer.toString(regBalance);
			registry.setText(regBalanceString);
				
		}
	}

}
