import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.sql.*;
class fpass extends JFrame implements ActionListener
{
JFrame f;
JButton b1,b2,b3;
JLabel l1,l2,l3;
JTextField t1;
JComboBox cb1,cb2;
JPasswordField t3;
Connection cn;
PreparedStatement prstm;
String sql;
Statement stm;
ResultSet rs;
	fpass()
	{
		setTitle("Find Password");
		setLocation(500,250);
		setSize(400,300);
		setLayout(null);

		b1=new JButton("Recover");
		b2=new JButton("Clear");
		b3=new JButton("Back");
		l1=new JLabel("New user Name:");
		l2=new JLabel("Select Question");
		l3=new JLabel("Answer:");
		t1=new JTextField(40);
		t3=new JPasswordField(40);
		cb1=new JComboBox();
		cb2=new JComboBox();
	
	add(b1);add(b2);add(b3);add(cb1);add(cb2);
	add(l1);add(l2);add(l3);add(t3);
	
cb1.addItem("Select Question");
cb1.addItem("Pet name");
cb1.addItem("First bike");
cb1.addItem("First mobile");
cb1.addItem("First cell no.");
cb1.addItem("Mothers name");
cb1.addItem("Favourite food");
cb2.addItem("<select user>");
	

	l1.setBounds(20,50,100,20);	l2.setBounds(20,80,100,20);
	l3.setBounds(20,110,100,20);
	cb2.setBounds(130,50,120,20);cb1.setBounds(130,80,120,20);		
	t3.setBounds(130,110,120,20);		
	b1.setBounds(20,150,90,30);b2.setBounds(130,150,90,30);
	b3.setBounds(230,150,90,30);
	try
	{
	cn=DriverManager.getConnection("jdbc:mysql:///apple1","root","root");	
	stm=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	rs=stm.executeQuery("select * from loguser");
	while(rs.next())
	{
	cb2.addItem(rs.getString(1));
	}
	rs.close();
	}
	catch(Exception ex)
	{
	ex.printStackTrace();
	}
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	b1.addActionListener(this);b2.addActionListener(this);b3.addActionListener(this);setVisible(true);
	}
public void actionPerformed(ActionEvent ep)
{
	try
	{
	if(ep.getSource()==b1)
	{
		String a,b,c,d,s,e,f,g;
		s="select uname,pass,que,ans from loguser where uname='"+cb2.getSelectedItem().toString()+"'";
		a=cb2.getSelectedItem().toString();
		b=cb1.getSelectedItem().toString();
		c=t3.getText();	
		rs=stm.executeQuery(s);
		rs.next();
		d=rs.getString(1);	
		e=rs.getString(3);
		f=rs.getString(4);
	 if(a.equals(d)&&b.equals(e)&&c.equals(f)&&!a.equals("")&&!c.equals("")&&(cb1.getSelectedIndex()!=0))
		{
		JOptionPane.showMessageDialog(null,"Verify SuccessFull. Your password is : '"+rs.getString(2)+"'");
		cb1.setSelectedIndex(0);
		cb2.setSelectedIndex(0);
		t3.setText("");
		cb2.requestFocus();
		dispose();
		new loginuser();
		}
		else
		{
		 if(!a.equals(c)||!b.equals(d)&&!a.equals("")&&!b.equals(""))
		{
		JOptionPane.showMessageDialog(null," Wrong User Name or Password","Error!!!",JOptionPane.ERROR_MESSAGE);
		cb1.setSelectedIndex(0);
		cb2.setSelectedIndex(0);
		t3.setText("");
		cb2.requestFocus();
		}
		}
	}
	if(ep.getSource()==b2)
	{
		cb1.setSelectedIndex(0);
		cb2.setSelectedIndex(0);
		t3.setText("");
		cb2.requestFocus();
	}
	if(ep.getSource()==b3)
	{	
	dispose();

	}
	}	
	catch(Exception ex)
	{
	}	
}
public static void main(String args[])
{
	new fpass();
}
}