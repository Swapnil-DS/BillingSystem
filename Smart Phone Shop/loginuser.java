import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.sql.*;
class loginuser extends JFrame implements ActionListener
{
JFrame f;
JButton b1,b2;
JLabel l1,l2;
JTextField t1;
JPasswordField t2;
JMenuBar mbr;
JMenu menu;
JComboBox cb1;
JMenuItem fpass;
Connection cn;
PreparedStatement prstm;
String sql;
Statement stm;
ResultSet rs;
	loginuser()
	{
		setTitle("Login user");
		setLocation(500,250);
		setSize(450,250);
		setLayout(null);
	
		JLabel img=new JLabel(new ImageIcon("Images/login.jpg"));
		
	mbr=new JMenuBar();
	menu=new JMenu("Menu");
	fpass=new JMenuItem("Forgot Password");

		b1=new JButton("Login ");
		b2=new JButton("Exit ");
		l1=new JLabel("Login Name:");
		l2=new JLabel("Password:");
		t1=new JTextField(40);
		t2=new JPasswordField(40);
		cb1=new JComboBox();

		cb1.addItem("<select user>");

	add(b1);add(b2);add(l1);add(l2);add(t2);
	menu.add(fpass);mbr.add(menu);
	add(mbr);add(cb1);		add(img);
	mbr.setBounds(0,0,210,20);
	
	l1.setBounds(20,50,100,20);	l2.setBounds(20,90,100,20);b1.setBounds(20,140,80,30);	img.setBounds(210,00,225,225);
	b2.setBounds(120,140,80,30);cb1.setBounds(100,50,105,20);t2.setBounds(100,90,105,20);		
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
	v(b1);
	v(b2);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	b1.addActionListener(this);b2.addActionListener(this);setVisible(true);
	fpass.addActionListener(this);
	cb1.requestFocus();
	}
  void v(final JButton tt)
{
	tt.addKeyListener(new KeyAdapter()
	{
	public void keyTyped(KeyEvent e)
	{
	if(e.getSource()==b1)
	{
		if((cb1.getSelectedIndex()==0) && (t2.getText().equals("")))
		{
		JOptionPane.showMessageDialog(null,"Select User Name And Enter Password !","WARNING!!!",JOptionPane.WARNING_MESSAGE);
		cb1.setSelectedIndex(0);
		t2.setText("");
		cb1.requestFocus();
		}
		else if((cb1.getSelectedIndex()==0))
		{
		JOptionPane.showMessageDialog(null,"Select User Name!","WARNING!!!",JOptionPane.WARNING_MESSAGE);
		cb1.setSelectedIndex(0);
		cb1.requestFocus();
		t2.setText("");
		}
		else if((t2.getText().equals("")))
		{
		JOptionPane.showMessageDialog(null,"Enter Password !","WARNING!!!",JOptionPane.WARNING_MESSAGE);
			cb1.setSelectedIndex(0);
			t2.setText("");
		t2.requestFocus();
		}
		else
		{
		try
		{
		String s,c,d;
		s="select uname,pass from loguser where uname='"+cb1.getSelectedItem().toString()+"' and pass='"+t2.getText().toString()+"'";
		String a,b;
		a=cb1.getSelectedItem().toString();
		b=t2.getText();	
		rs=stm.executeQuery(s);
		rs.next();
		c=rs.getString(1);	
		d=rs.getString(2);
	 if(a.equals(c)&&b.equals(d)&&!a.equals("")&&!b.equals(""))
		{
		JOptionPane.showMessageDialog(null,"Login SuccessFull");
		new menu();
		dispose();
		}
		else
		{
		 if(!a.equals(c)||!b.equals(d)&&!a.equals("")&&!b.equals(""))
		{
		JOptionPane.showMessageDialog(null,"Access Denied","Error!!!",JOptionPane.ERROR_MESSAGE);
			cb1.setSelectedIndex(0);
			t2.setText("");
			cb1.requestFocus();
		}
		}
	}	
	catch(Exception ex)
	{
	JOptionPane.showMessageDialog(null," Wrong User Name or Password","Error!!!",JOptionPane.ERROR_MESSAGE);
	cb1.setSelectedIndex(0);
	cb1.requestFocus();
	t2.setText("");
	}	
	}}
	if(e.getSource()==b2)
	{
	System.exit(0);
	}
	if(e.getSource()==fpass)
	{
	new fpass();
	dispose();
	}	
	}
	});
}
	public void actionPerformed(ActionEvent e)
	{
	if(e.getSource()==b1)
	{
		if((cb1.getSelectedIndex()==0) && (t2.getText().equals("")))
		{
		JOptionPane.showMessageDialog(null,"Select User Name And Enter Password !","WARNING!!!",JOptionPane.WARNING_MESSAGE);
		cb1.setSelectedIndex(0);
		t2.setText("");
		cb1.requestFocus();
		}
		else if((cb1.getSelectedIndex()==0))
		{
		JOptionPane.showMessageDialog(null,"Select User Name!","WARNING!!!",JOptionPane.WARNING_MESSAGE);
			cb1.setSelectedIndex(0);
			cb1.requestFocus();
			t2.setText("");
		}
		else if((t2.getText().equals("")))
		{
		JOptionPane.showMessageDialog(null,"Enter Password !","WARNING!!!",JOptionPane.WARNING_MESSAGE);
			cb1.setSelectedIndex(0);
			t2.setText("");
		t2.requestFocus();
		}
		else
		{
		try
		{
		String s,c,d;
		s="select uname,pass from loguser where uname='"+cb1.getSelectedItem().toString()+"' and pass='"+t2.getText().toString()+"'";
		String a,b;
		a=cb1.getSelectedItem().toString();
		b=t2.getText();	
		rs=stm.executeQuery(s);
		rs.next();
		c=rs.getString(1);	
		d=rs.getString(2);
	 if(a.equals(c)&&b.equals(d)&&!a.equals("")&&!b.equals(""))
		{
		JOptionPane.showMessageDialog(null,"Login SuccessFull");
		new menu();
		dispose();
		}
		else
		{
		 if(!a.equals(c)||!b.equals(d)&&!a.equals("")&&!b.equals(""))
		{
		JOptionPane.showMessageDialog(null,"Access Denied","Error!!!",JOptionPane.ERROR_MESSAGE);
			cb1.setSelectedIndex(0);
			t2.setText("");
			cb1.requestFocus();
		}
		}
	}	
	catch(Exception ex)
	{
	JOptionPane.showMessageDialog(null," Wrong User Name or Password","Error!!!",JOptionPane.ERROR_MESSAGE);
		cb1.setSelectedIndex(0);
		cb1.requestFocus();
		t2.setText("");
	}	
	}}
	if(e.getSource()==b2)
	{
	System.exit(0);
	}
	if(e.getSource()==fpass)
	{
	new fpass();
	dispose();
	}
}	
	public static void main(String args[])
	{
	new loginuser();
	}
}
