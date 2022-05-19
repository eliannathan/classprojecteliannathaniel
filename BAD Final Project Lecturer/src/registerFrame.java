import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.*;

public class registerFrame extends JFrame implements ActionListener{

	JPanel northPnl, centerPnl, usernamePnl, passwordPnl, loginButtonPnl;
	JLabel title, usernameLbl, passwordLbl;
	JTextField username;
	JPasswordField password;
	JButton register,back;
	JScrollPane scrollP;
	
	connect con;
	
	public void initial() {
		//connect
		con = new connect();
		
		//panel
		northPnl = new JPanel();
		centerPnl = new JPanel();
		usernamePnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    passwordPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    loginButtonPnl = new JPanel(new GridLayout(1, 2,10,0));
		
		//label
		title = new JLabel("Register");
		usernameLbl = new JLabel("Username");
		passwordLbl = new JLabel("Password");
		northPnl.add(title);
			
		//text field
		username = new JTextField();
		username.setPreferredSize(new Dimension(200,20));
		usernamePnl.add(usernameLbl);usernamePnl.add(username);
		
		//password
		password = new JPasswordField();
		password.setPreferredSize(new Dimension(200,20));
		passwordPnl.add(passwordLbl);passwordPnl.add(password);
		
		//Button
		register = new JButton("register");
		register.setPreferredSize(new Dimension(65,25));	
		register.addActionListener(this);				
		back = new JButton("Back");		
		back.setPreferredSize(new Dimension(65,25));
		back.addActionListener(this);
		
		//add panels
		centerPnl.add(usernamePnl);
		centerPnl.add(passwordPnl);
		loginButtonPnl.add(back);loginButtonPnl.add(register);
		
		add(northPnl, BorderLayout.NORTH);
		add(centerPnl, BorderLayout.CENTER);
		add(loginButtonPnl, BorderLayout.SOUTH);
		
	}
	
	public registerFrame() {
		initial();
		
		setVisible(true);
		setSize(300,200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Register");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == register) {
			ResultSet rs = con.runQuery("SELECT * FROM user");	
			int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to make a new account?");
			if (option != JOptionPane.YES_OPTION) {
				return;				
			}			
			String name = username.getText();
			String pass = String.valueOf(password.getPassword());
			int role = 1;
								
			String query = String.format(("INSERT INTO user VALUES('%d', '%s', '%s', '%d')"),0, name, pass, role);
			
			boolean regSuccess = con.runUpdate(query);
			
			if(regSuccess) {
				JOptionPane.showMessageDialog(null, "Registered Success", "Success", JOptionPane.INFORMATION_MESSAGE);
				username.setText("");
				password.setText("");

			}else {
				JOptionPane.showMessageDialog(null, "Register Failed", "Error", JOptionPane.ERROR_MESSAGE);
			}
			this.dispose();
			new customerLoginFrame();
		
		}else if (e.getSource() == back) {
			this.dispose();
			new customerLoginFrame();
		}
	}

}
