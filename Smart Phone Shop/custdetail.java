import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.lang.*;
import java.sql.*;
class custdetail extends JFrame implements ActionListener
{
 JButton submit,clear,cancel;
JLabel lname,laddrs,lbillno,lcontact,ltitle,ldoc,ldate;
JTextField tname,taddrs,tcontact,tbillno;
DateButton calb;
JCheckBox ch1,ch2,ch3,ch4,ch5;
Connection cn;
PreparedStatement prstm;
String sql,sql1;
Statement stm;
String name="",add="",contact="",vc="",dl="",lb="",ac="",rc="",custid="";
ResultSet rs,rs1;
custdetail()
{
		setTitle(">>>Customer Detail<<<");
		setLocation(400,20);
		setSize(600,650);
		setLayout(null);
		setVisible(true);
		
	Font f=new Font("Georgia",Font.BOLD,28);	Font f1=new Font("Georgia",Font.BOLD,16);
	Font f2=new Font("Georgia",Font.BOLD,20);
	
	submit=new JButton("Submit");	clear=new JButton("Clear");	cancel=new JButton("Cancel");

	calb = new DateButton();	add(calb);calb.setEnabled(false);
	
	lname=new JLabel("Name:");	laddrs=new JLabel("Address:");	lbillno=new JLabel("Cust.ID:");
	lcontact=new JLabel("Contact:");	ltitle=new JLabel(">>Customer Information<<");
	ldoc=new JLabel("@ Identity Proofs :-");	ldate=new JLabel("Date:");
	
	tname=new JTextField();	taddrs=new JTextField();	tbillno=new JTextField(); tcontact	=new JTextField();
	tbillno.setHorizontalAlignment(JTextField.CENTER);
	
	ch1=new JCheckBox("Voting Card");	ch2=new JCheckBox("Driving Licence");	ch3=new JCheckBox("Light Bill");
	ch4=new JCheckBox("Adhar Card");	ch5=new JCheckBox("Ration Card");

	add(lname);	add(laddrs);	add(lbillno);	add(lcontact);	add(ltitle);	add(ldoc); add(ldate);
	add(tname);	add(taddrs);	add(tbillno);	add(tcontact);	
	add(ch1);		add(ch2);		add(ch3);		add(ch4);		add(ch5);
	add(submit);	add(clear);	add(cancel);
	ltitle.setFont(f);	lname.setFont(f1);	laddrs.setFont(f1);	lcontact.setFont(f1);	ldoc.setFont(f2);
	
	tbillno.setEditable(false);

	vd(tcontact);
	
	ltitle.setBounds(80,10,420,20);
	lbillno.setBounds(12,60,50,20);	tbillno.setBounds(65,60,60,20);
	ldate.setBounds(430,60,40,20);	calb.setBounds(470,60,100,20);
	lname.setBounds(80,120,80,20);	tname.setBounds(160,120,300,20);
	laddrs.setBounds(80,160,80,20);	taddrs.setBounds(160,160,300,20);
	lcontact.setBounds(80,200,80,20);	tcontact.setBounds(160,200,300,20);
	ldoc.setBounds(30,250,200,20);	ch1.setBounds(60,310,200,20);		
	ch2.setBounds(60,350,200,20);		ch3.setBounds(60,390,200,20);		
	ch4.setBounds(60,430,200,20);		ch5.setBounds(60,470,200,20);		
	submit.setBounds(100,530,100,30);	clear.setBounds(220,530,100,30);	cancel.setBounds(340,530,100,30);		
	try
	{
	cn=DriverManager.getConnection("jdbc:mysql:///apple1","root","root");	
	stm=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	rs=stm.executeQuery("select max(custid) from custinfo");
	rs.next();
	int bb1=Integer.parseInt(rs.getString(1));
	bb1=bb1+001;
	tbillno.setText(""+bb1);
	rs.close();	
	}
	catch(Exception ex)
	{
	ex.printStackTrace();
	}
submit.addActionListener(this);	clear.addActionListener(this);	cancel.addActionListener(this);
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
}
void vd(final JTextField tt)
{
	tt.addKeyListener(new KeyAdapter()
	{
		public void keyTyped(KeyEvent e)
		{
			if(tcontact.getText().length()<10 && e.getKeyChar()>='0' && e.getKeyChar()<='9')
			super.keyTyped(e);
			else
			{
				e.consume();				
				Toolkit tk=Toolkit.getDefaultToolkit();
				tk.beep();
			}
		}
	});
}
public void actionPerformed(ActionEvent e)
{
if(e.getSource()==cancel)
	{
	new billfrm(name,add,contact,vc,dl,lb,ac,rc,custid);
	dispose();
	}
	if(e.getSource()==submit)
	{
	name=tname.getText().toString();	add=taddrs.getText().toString(); contact=tcontact.getText().toString();custid=tbillno.getText().toString();	
	if(ch1.isSelected()==true)
	vc="y";
	else
	vc="n";
	if(ch2.isSelected()==true)
	dl="y";
	else
	dl="n";
	if(ch3.isSelected()==true)
	lb="y";
	else
	lb="n";
	if(ch4.isSelected()==true)
	ac="y";
	else
	ac="n";
	if(ch5.isSelected()==true)
	rc="y";
	else
	rc="n";
	custid=tbillno.getText();
	if((!name.equals("")) && (!add.equals("")) && (!contact.equals("")) && (ch1.isSelected()!=false)&&(ch4.isSelected()!=false) && (contact.length()==10))
	{
	new billfrm(name,add,contact,vc,dl,lb,ac,rc,custid);
	dispose();
	}
	else
	{
	JOptionPane.showMessageDialog(null,"All fields are required !","WARNING!!!",JOptionPane.WARNING_MESSAGE);		
	}
	}
}
public static void main(String args[])
	{
		new billfrm("","","","","","","","","");
	}
}