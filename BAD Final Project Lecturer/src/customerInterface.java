import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class customerInterface extends JFrame implements ActionListener{

	private JFrame confirmLogout;
	private JPanel northPnl, centerPnl;
	private JLabel welcomeCustomer;
	private JDesktopPane purchasePane,checkoutPane,logoutPane;
	private JMenuBar menuBar;
	private JButton logout;
	private JMenu purchaseItems, checkout;
	
	public void initial() {
		//frame
		confirmLogout = new JFrame();
				
		//panel
		northPnl = new JPanel();
		centerPnl = new JPanel();

					
		//label
		welcomeCustomer = new JLabel("Welcome, Customer");
		northPnl.add(welcomeCustomer);
				
		//menu bar
		menuBar = new JMenuBar();
		
		//menu
		checkout = new JMenu("Checkout");
		checkout.addMenuListener(new MenuListener() {
					
		@Override
		public void menuSelected(MenuEvent e) {
		checkoutPane = new JDesktopPane();
		setContentPane(checkoutPane);
		checkoutPane.add(new checkoutFrame());	
						
		}
					
		@Override
		public void menuDeselected(MenuEvent e) {				
		}
					
		@Override
		public void menuCanceled(MenuEvent e) {			
		}
		});
				
		purchaseItems = new JMenu("Purchase List");
		purchaseItems.setMnemonic(KeyEvent.VK_M);
		purchaseItems.addMenuListener(new MenuListener() {
					
		@Override
		public void menuSelected(MenuEvent e) {
		purchasePane = new JDesktopPane();
		setContentPane(purchasePane);
		purchasePane.add(new purchaseFrame());				
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
		menuBar.add(purchaseItems);
		menuBar.add(checkout);
		menuBar.add(logout);
		setJMenuBar(menuBar);
				
		//add panel
		add(northPnl, BorderLayout.NORTH);
		add(centerPnl, BorderLayout.CENTER);
			}
			
	public customerInterface() {
		initial();
		
		setVisible(true);
		setSize(800,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Koperasi Customer Interface");
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
