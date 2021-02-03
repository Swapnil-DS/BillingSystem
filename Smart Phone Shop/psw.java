import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.sql.*;
class psw extends JFrame implements ActionListener
{
JButton b1,b2,b3,b4;
JLabel l1,l2,l3,l4;
JTextField t1;
JComboBox cb1;
JPasswordField t2,t3,t4;
Connection cn;
PreparedStatement prstm;
String sql;
Statement stm;
ResultSet rs;
	psw()
	{
		setTitle("Change Password");
		setLocation(470,225);
		setSize(350,300);
		setLayout(null);

	b1=new JButton("Change");b4=new JButton("Delete");b2=new JButton("Clear");	b3=new JButton("Back ");
	l1=new JLabel("User Name:");l2=new JLabel("Old-Password:");l3=new JLabel("New Password:");
	l4=new JLabel("Re-enter Password:");t2=new JPasswordField(40);t3=new JPasswordField(40);
	t4=new JPasswordField(40);cb1=new JComboBox();cb1.addItem("<select user>");

		add(b1);add(b2);add(b3);add(l1);add(l2);add(l3);
		add(t2);add(t3);add(t4);add(cb1);add(l4);add(b4);

		l1.setBounds(20,20,100,20);	l2.setBounds(20,60,100,20);l3.setBounds(20,100,100,20);l4.setBounds(20,140,120,20);
		cb1.setBounds(140,20,150,20);t2.setBounds(140,60,150,20);t3.setBounds(140,100,150,20);t4.setBounds(140,140,150,20);
		b1.setBounds(10,180,80,30);b2.setBounds(110,180,80,30);b3.setBounds(210,180,80,30);b4.setBounds(90,220,120,30);

	try
	{
	cn=DriverManager.getConnection("jdbc:mysql:///apple1","root","root");
	stm=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	rs=stm.executeQuery("select * from loguser");
	while(rs.next())
	{
	cb1.addItem(rs.getString(1));
	}
	rs.close();
	}
	catch(Exception ex)
	{
	ex.printStackTrace();
	}
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	b1.addActionListener(this);b2.addActionListener(this);setVisible(true);
	b3.addActionListener(this);b4.addActionListener(this);
}

	public void actionPerformed(ActionEvent e)
	{
	try
	{
	if(e.getSource()==b1)
	{
		String a,b,c,d,s,f,g,r;
		s="select uname,pass,que,ans from loguser where uname='"+cb1.getSelectedItem().toString()+"'";
		rs=stm.executeQuery(s);
		rs.next();
		a=cb1.getSelectedItem().toString();
		b=t2.getText();
		c=t3.getText();
		d=t4.getText();
		f=rs.getString(1);
		g=rs.getString(2);

	 if(a.equals(f)&&b.equals(g)&&!a.equals("")&&!b.equals("")&&!c.equals("")&&!d.equals("")&&c.equals(d))
		{
		sql="update loguser set pass='"+c+"'where uname='"+cb1.getSelectedItem().toString()+"'";
        prstm = cn.prepareStatement(sql);
        prstm.execute() ;
		JOptionPane.showMessageDialog(null," Successfully Changed Your password ");
 	   dispose();
		}
		else
		{
	JOptionPane.showMessageDialog(null," Wrong User Name or Password","Error!!!",JOptionPane.ERROR_MESSAGE);
		cb1.setSelectedIndex(0);
		t2.setText("");
		t3.setText("");
		t4.setText("");
		cb1.requestFocus();
		}
	}
	if(e.getSource()==b2)
	{
		cb1.setSelectedIndex(0);
		t2.setText("");
		t3.setText("");
		t4.setText("");
		cb1.requestFocus();
		}
	if(e.getSource()==b4)
	{		String a;
			a=cb1.getSelectedItem().toString();

		sql="delete from loguser where uname='"+a+"'";
        prstm = cn.prepareStatement(sql);
        prstm.execute() ;
		JOptionPane.showMessageDialog(null," User deleted Successfully");
 	   dispose();

		}

	if(e.getSource()==b3)
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
	new psw();
	}
}
