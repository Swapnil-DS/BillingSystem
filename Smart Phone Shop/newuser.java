import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.sql.*;
class newuser extends JFrame implements ActionListener
{
JFrame f;
JButton b1,b2,b3;
JLabel l1,l2,l3,l4;
JTextField t1;
JComboBox cb1;
JPasswordField t2,t3;
Connection cn;
PreparedStatement prstm;
String sql;
Statement stm;
ResultSet rs;
	newuser()
	{
		setTitle("Add New User");
		setLocation(500,250);
		setSize(400,300);
		setLayout(null);

		b1=new JButton("Save");
		b2=new JButton("Clear");
		b3=new JButton("Back");
		l1=new JLabel("New user Name:");
		l2=new JLabel("New password:");
		l3=new JLabel("Questions:");
		l4=new JLabel("Answer:");
		t1=new JTextField(40);
		t2=new JPasswordField(40);
		t3=new JPasswordField(40);
		cb1=new JComboBox();
	
	add(b1);add(b2);add(b3);
	add(l1);add(l2);add(l3);add(l4);add(t1);add(t2);add(t3);add(cb1);
	
cb1.addItem("Select Question");
cb1.addItem("Pet name");
cb1.addItem("First bike");
cb1.addItem("First mobile");
cb1.addItem("First cell no.");
cb1.addItem("Mothers name");
cb1.addItem("Favourite food");
	

	l1.setBounds(20,50,100,20);	l2.setBounds(20,80,100,20);
	l3.setBounds(20,110,100,20);l4.setBounds(20,140,100,20);
	t1.setBounds(130,50,120,20);t2.setBounds(130,80,120,20);		
	cb1.setBounds(130,110,120,20);t3.setBounds(130,140,120,20);		
	b1.setBounds(20,170,80,30);b2.setBounds(120,170,80,30);
	b3.setBounds(220,170,80,30);
	try
	{
	cn=DriverManager.getConnection("jdbc:mysql:///apple1","root","root");	
	stm=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	rs=stm.executeQuery("select * from loguser");
	}
	catch(Exception ex)
	{
	ex.printStackTrace();
	}
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	b1.addActionListener(this);b2.addActionListener(this);b3.addActionListener(this);setVisible(true);
	}
public void actionPerformed(ActionEvent e)
{
try
{
	if(e.getSource()==b1)
	{
	String z=t3.getText();
		if((cb1.getSelectedIndex()!=0)&& (!z.equals("")))
		{
		sql="insert into loguser values('"+t1.getText()+"','"+t2.getText()+"','"+cb1.getSelectedItem().toString()+"','"+t3.getText()+"')";	
		prstm=cn.prepareStatement(sql);
		prstm.execute();
		prstm.close();
		int ans=JOptionPane.showConfirmDialog(null,"Now you are a new user. Do you want to create another user");
		if(ans==0)
		t1.setText("");
		t2.setText("");
		t3.setText("");
		t1.requestFocus(); 
		if(ans==1)
		{
		dispose();
		}
		if(ans==2)
		dispose();
	}
	else
	{
	JOptionPane.showMessageDialog(null,"All fields are required !","WARNING!!!",JOptionPane.WARNING_MESSAGE);	
	}
	}
	if(e.getSource()==b2)
	{
		t1.setText("");
		t2.setText("");
		t3.setText("");
		cb1.setSelectedIndex(0);
		t1.requestFocus(); 
	}
	if(e.getSource()==b3)
	{
	dispose();
	}
	}
	catch(Exception ex)
	{
	System.out.print(ex);
	}

}
public static void main(String args[])
{
	new newuser();
}
}