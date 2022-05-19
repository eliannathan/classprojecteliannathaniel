import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class staffInterface extends JFrame implements ActionListener{
	private JFrame confirmLogout;
	private JPanel northPnl, centerPnl;
	private JLabel welcomeStaff;
	private JDesktopPane addItemPane,checkBalPane,logoutPane;
	private JMenuBar menuBar;
	private JButton logout;
	private JMenu addItems, checkBal;
		
	private void initial() {		
		//frame
		confirmLogout = new JFrame();
		
		//panel
		northPnl = new JPanel();
		centerPnl = new JPanel();

			
		//label
		welcomeStaff = new JLabel("Welcome, Staff");
		northPnl.add(welcomeStaff);
		
		//menu bar
		menuBar = new JMenuBar();
		
		//menu
		checkBal = new JMenu("Cashier");
		checkBal.addMenuListener(new MenuListener() {
			
		@Override
		public void menuSelected(MenuEvent e) {
			checkBalPane = new JDesktopPane();
			setContentPane(checkBalPane);
			checkBalPane.add(new cashierFrame());	
				
		}
			
		@Override
		public void menuDeselected(MenuEvent e) {				
		}
			
		@Override
		public void menuCanceled(MenuEvent e) {			
		}
		});
		
			addItems = new JMenu("Warehouse");
			addItems.setMnemonic(KeyEvent.VK_M);
			addItems.addMenuListener(new MenuListener() {
			
		@Override
		public void menuSelected(MenuEvent e) {
			addItemPane = new JDesktopPane();
			setContentPane(addItemPane);
			addItemPane.add(new warehouseFrame());				
		}
			
		@Override
		public void menuDeselected(MenuEvent e) {			
		}
			
		@Override
		public void menuCanceled(MenuEvent e) {		
		}
		});
	
		logout = new JButton("Log Out");
		logout.setMnemonic(KeyEvent.VK_M);
		logout.addActionListener(this);
		
		//menu bar
		menuBar.add(addItems);
		menuBar.add(checkBal);
		menuBar.add(logout);
		setJMenuBar(menuBar);
		
		//add panel
		add(northPnl, BorderLayout.NORTH);
		add(centerPnl, BorderLayout.CENTER);
	}
	
	public staffInterface() {
		initial();
		
		setVisible(true);
		setSize(800,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Koperasi Staff Interface");
				
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int returnLogout = JOptionPane.showConfirmDialog(confirmLogout, "Are you sure?", "", JOptionPane.YES_NO_CANCEL_OPTION);
		if(returnLogout == JOptionPane.YES_OPTION) {
			this.dispose();
			new selectionInterface();
		}					
	};
}
