import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.print.*;
import java.io.*;
class menu extends JFrame implements ActionListener
{
JMenuBar mbr,mbr1;
String str="a";
JToolBar tbr;
JMenu stock,purches,repair,report,tool;
JMenuItem add,show,bill,addrpr,avlstock,billsts,search,rprsts,chngpass,newuser,logout,exit;
JPanel pbtn,p2,p3,p4,p5,p6;
JButton us,sst,gb,rprt1,srch,rprt2;
	menu()
	{
		setTitle("Welcome to Smart-Phone Shop");
		setLocation(0,0);
		Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
       ss.width -= 00;
        ss.height -= 40;
        setSize(ss);
		setResizable(false);		
		//setLayout(null);
		
		pbtn=new JPanel();p2=new JPanel();p3=new JPanel();p5=new JPanel();p6=new JPanel();
		mbr=new JMenuBar();
		
	us=new JButton("Update Stock");		sst=new JButton("View Stock");
	gb=new JButton("Create Bill");		srch=new JButton("Search Bill");
	rprt1=new JButton("Customer Report");		rprt2=new JButton("Exit");
//	b2clr=new JButton("Clear");
//b3bk=new JButton("Exit");b4ad=new JButton("Add to Stock");
//	bexit=new JButton("Exit");bimg=new JButton("Add Image");badd=new JButton("Add to Stock");bclr=new JButton("Clear");
	JLabel img=new JLabel(new ImageIcon("Images/app.jpg"));
	
	//Menu
		stock=new JMenu("Stock");	purches=new JMenu("Purches"); repair=new JMenu("Repair");
		report=new JMenu("Report");	tool=new JMenu("Tool");
	//MenuItemsavlstock=new JMenuItem("Available Stock");report.add(avlstock);	
		add=new JMenuItem("Add to Stock");	show=new JMenuItem("Show Stock");	bill=new JMenuItem("Create Bill");
		addrpr=new JMenuItem("Add to repair");		billsts=new JMenuItem("Show Bills");		
		search=new JMenuItem("Search");	rprsts=new JMenuItem("Repair Status"); chngpass=new JMenuItem("Change Password");
		newuser=new JMenuItem("New User");	logout=new JMenuItem("Logout");	exit=new JMenuItem("Exit");
		
		stock.add(add);	stock.add(show);
		mbr.add(stock);
		
		purches.add(bill);
		mbr.add(purches);
		
		repair.add(addrpr); repair.add(rprsts);
		mbr.add(repair);

		report.add(billsts);	report.add(search);
		mbr.add(report);

		 tool.add(newuser);tool.add(chngpass);tool.add(logout);tool.add(exit);
		mbr.add(tool);
		pbtn.setLayout(null);
		pbtn.setBackground(Color.GRAY);
		add(mbr);
		add(pbtn);		add(img);	
		pbtn.add(us);		pbtn.add(sst);		pbtn.add(rprt1);		pbtn.add(rprt2);		pbtn.add(gb);		pbtn.add(srch);
		
//		us.setAccelerator(KeyStroke.getKeyStroke(
    //            KeyEvent.VK_F1, ActionEvent.CTRL_MASK));
       
		v(gb);		v(sst);		v(rprt1);v(rprt2);		v(us);		v(srch);
//		v(bill);		v(newuser);
//		v(chngpass);		v(logout);		v(exit);
		//v(add);		v(show);		v(search);		v(billsts);	
		   
		mbr.setBounds(0,0,1366,40);	img.setBounds(200,0,225,225);		pbtn.setBounds(0,40,200,800);
		us.setBounds(10,20,180,40);		sst.setBounds(10,80,180,40);		gb.setBounds(10,140,180,40);
		srch.setBounds(10,200,180,40);		rprt1.setBounds(10,260,180,40);		rprt2.setBounds(10,320,180,40);
		
		gb.addActionListener(this);		sst.addActionListener(this);		rprt1.addActionListener(this);
		rprt2.addActionListener(this);		us.addActionListener(this);		srch.addActionListener(this);
		bill.addActionListener(this);		newuser.addActionListener(this);		chngpass.addActionListener(this);
		logout.addActionListener(this);		exit.addActionListener(this);		add.addActionListener(this);
		show.addActionListener(this);		search.addActionListener(this);		billsts.addActionListener(this);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}		
	
void v(final JButton tt)
{
	tt.addKeyListener(new KeyAdapter()
	{
		public void keyTyped(KeyEvent e)
		{
	if(e.getSource()==add || e.getSource()==us)
	{
	new addstock();
	}
	if(e.getSource()==bill || e.getSource()==gb)
	{
	new billfrm("","","","","","","","","");
	}
	if(e.getSource()==newuser)
	{
	new newuser();
	}
	if(e.getSource()==chngpass) 
	{		
	new psw();
	}
	if(e.getSource()==search|| e.getSource()==srch) 
	{		
	new srchcust();
	}
	if(e.getSource()==show || e.getSource()==sst)
	{
	new showstock();	
	}
	if(e.getSource()==billsts || e.getSource()==rprt1)
	{
	new report();	
	}
	if(e.getSource()==logout)
	{
	new loginuser();	
	dispose();
	}
	if(e.getSource()==exit)
	{
	dispose();
	}
	if(e.getSource()==rprt2)
	{
	dispose();
	}
	}
	});
}
	public void actionPerformed(ActionEvent e)
	{
	if(e.getSource()==add || e.getSource()==us)
	{
	new addstock();
	}
	if(e.getSource()==bill || e.getSource()==gb)
	{
	new billfrm("","","","","","","","","");
	}
	if(e.getSource()==newuser)
	{
	new newuser();
	}
	if(e.getSource()==chngpass) 
	{		
	new psw();
	}
	if(e.getSource()==search|| e.getSource()==srch) 
	{		
	new srchcust();
	}
	if(e.getSource()==show || e.getSource()==sst)
	{
	new showstock();	
	}
	if(e.getSource()==billsts || e.getSource()==rprt1)
	{
	new report();	
	}
	if(e.getSource()==rprt2)
	{
	dispose();
	}
	if(e.getSource()==logout)
	{
	new loginuser();	
	dispose();
	}
	if(e.getSource()==exit)
	{
	dispose();
	}
	}	
	public static void main(String args[])
	{
	new menu();
	}
}
