import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class staffLoginFrame extends JFrame implements ActionListener{

	JPanel northPnl, centerPnl, usernamePnl, passwordPnl, loginButtonPnl;
	JLabel title, usernameLbl, passwordLbl;
	JTextField username;
	JPasswordField password;
	JButton login,back;
	connect con;
	
	public void initial() {
		con = new connect();
		
		//panel
		northPnl = new JPanel();
		centerPnl = new JPanel(new GridLayout(3,2));
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
		login = new JButton("Login");
		login.setPreferredSize(new Dimension(65,25));	
		login.addActionListener(this);				
		back = new JButton("Back");		
		back.setPreferredSize(new Dimension(65,25));
		back.addActionListener(this);

		//login button panel add
		centerPnl.add(usernamePnl);
		centerPnl.add(passwordPnl);
		loginButtonPnl.add(back);
		loginButtonPnl.add(login);
				
		//add panels
		add(northPnl, BorderLayout.NORTH);
		add(centerPnl, BorderLayout.CENTER);
		add(loginButtonPnl, BorderLayout.SOUTH);
		
	}
	
	public staffLoginFrame() {
		initial();
		
		setVisible(true);
		setSize(300,200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Staff Login");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == back) {
			this.dispose();
			new selectionInterface();
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
					if(role == 2) {
						JOptionPane.showMessageDialog(null, "Login Success");
						this.dispose();
						new staffInterface();							
					} else {
						JOptionPane.showMessageDialog(null, "You are not a staff");
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
