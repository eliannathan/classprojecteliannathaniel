import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class selectionInterface extends JFrame implements ActionListener{

	private JPanel northPnl, centerPnl, buttonPnl;
	private JLabel welcome;
	private JButton customerLogin, staffLogin;
	
	public void initial() {
		
		//panel
		northPnl = new JPanel();
		centerPnl = new JPanel();
		buttonPnl = new JPanel();
		
		//label
		welcome = new JLabel("Welcome, User! What is your role?");
		northPnl.add(welcome);
		
		//button
		customerLogin = new JButton("Customer");
		customerLogin.setPreferredSize(new Dimension (150, 50));
		customerLogin.addActionListener(this);
		staffLogin = new JButton("Staff");
		staffLogin.setPreferredSize(new Dimension (150, 50));
		staffLogin.addActionListener(this);
		buttonPnl.add(customerLogin);
		buttonPnl.add(staffLogin);
		centerPnl.add(buttonPnl);
		
		//add panels
		add(northPnl, BorderLayout.NORTH);
		add(centerPnl,BorderLayout.CENTER);
	
		}
		
	public selectionInterface() {
		initial();
		
		setVisible(true);
		setSize(400,160);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Koperasi Application");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == customerLogin) {
			this.dispose();
			new customerLoginFrame();
		} else 	if(e.getSource() == staffLogin) {
			this.dispose();
			new staffLoginFrame();
		
	}

}
	
}
