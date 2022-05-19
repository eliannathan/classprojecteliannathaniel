import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class customerLoginFrame extends JFrame implements ActionListener{

	JPanel northPnl, centerPnl, southPnl, usernamePnl, passwordPnl, loginButtonPnl;
	JLabel title, usernameLbl, passwordLbl;
	JTextField username;
	JPasswordField password;
	JButton login,back,register;	
	connect con;
	
	public void initial() {		
		con = new connect();
		
		//panel
		northPnl = new JPanel();
		centerPnl = new JPanel(new GridLayout(3,2));
		southPnl = new JPanel();
		usernamePnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    passwordPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    loginButtonPnl = new JPanel(new GridLayout(1, 2,10,0));
		
		//label
		title = new JLabel("Login");
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
		register = new JButton("Register");
		register.setPreferredSize(new Dimension(65,25));
		register.addActionListener(this);
		login = new JButton("Login");
		login.setPreferredSize(new Dimension(65,25));
		login.addActionListener(this);
		back = new JButton("Back");		
		back.addActionListener(this);
		
		//add username and password panel to center
		centerPnl.add(usernamePnl);
		centerPnl.add(passwordPnl);
		
		//login button panel add
		loginButtonPnl.add(register);
		loginButtonPnl.add(login);
		centerPnl.add(loginButtonPnl);
		southPnl.add(back);
		
		//add panels
		add(northPnl, BorderLayout.NORTH);
		add(centerPnl, BorderLayout.CENTER);
		add(southPnl, BorderLayout.SOUTH);
		
	}
	
	public customerLoginFrame() {
		initial();
		
		setVisible(true);
		setSize(300,200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Customer Login");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == back) {
			this.dispose();
			new selectionInterface();
		} else if(e.getSource() == register) {
			this.dispose();
			new registerFrame();
		} else if(e.getSource() == login) {
			ResultSet rs = null;
			String temporaryUsername = username.getText();
			String temporaryPassword = String.valueOf(password.getPassword());
			boolean correctUsername = false;
			boolean correctPassword = false;
									
			try {
				rs = con.runQuery("SELECT username FROM user");
				while(rs.next()){
					String name = rs.getString("username");
					if(temporaryUsername.equals(name)) {
						correctUsername = true;
						break;
					} else {
							
					}
				}
				if(correctUsername == true) {
					rs = con.runQuery("SELECT password FROM user WHERE username = '"+temporaryUsername+"'");
					while(rs.next()) {
						String pass = rs.getString("password");
						if(temporaryPassword.equals(pass)) {
							correctPassword = true;
							break;
						} 
					}
				}
					
				if (correctUsername && correctPassword == true) {
					rs = con.runQuery("SELECT role FROM user WHERE username = '"+temporaryUsername+"'");
					rs.next();
					int role = rs.getInt("role");
					if(role == 1) {
						JOptionPane.showMessageDialog(null, "Login Success");
						this.dispose();
						new customerInterface();							
					} else {
						JOptionPane.showMessageDialog(null, "You are not a customer");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Incorrect information!");
				}
					
			}
			catch (SQLException e1) {
				e1.printStackTrace();
			}
				
		}
	}
			
}			
			
		
			
	
		
	


