import java.awt.Desktop;
import java.io.*;
import jxl.*;
import java.sql.*;
import com.itextpdf.text.*;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.*;
import java.awt.*;
import java.awt.List;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.sql.*;
class billfrm extends JFrame implements ActionListener,ItemListener
{
int bid,ttl1,vvt;
	String path;
OutputStream file;
Document document;
Paragraph p,p1,p2,p3,p4,p5,p6;
PdfPTable table;
PdfPCell c1;
Font big,small,big1,big2;
int cnt;
String date1;
JRadioButton rb1,rb2;
JTextField tname,tbillno,tbtrynod,tbtrynon,tmdlclrd,tmdlclrn,tpriced,tpricen,twrnty,ttotal,tvat,tgtotal;
TextArea tadd;
JLabel title,name,add,billno,date,mdlnm,mdlno,mdlimei,btryno,mdlclr,mdlno1,amt,total,vat,gtotal,price,wrnty,year,mdlname;
JButton iadd,iclear,clear,save,print,exit,newc,oldc,confirm,remove;
ButtonGroup bg;
JPanel mdlpnl,mdldtl,imgpnl;
List mdllst,mdlamt,lstmno;
JComboBox ipad,iphn,ipadimei,iphnimei,custname;
DateButton calb;
Connection cn;
PreparedStatement prstm;
String sql,sql1,sql2;
Statement stm;
ResultSet rs,rs1;
String name1,add1,contact1,vc1,dl1,lb1,ac1,rc1,custid1,ccid;
int bb1,flag=0,flag1=0,tid,flag3=0;
double total1,vat1,gtotal1;
billfrm(String nm,String ad,String cnt,String vc,String dl,String lb,String ac,String rc,String custid)
{
		setTitle("Generate Bill ");
		setLocation(250,10);
		setSize(800,700);
		setLayout(null);
		setVisible(true);

		name1=nm;	add1=ad;	contact1=cnt;		vc1=vc;		dl1=dl;		lb1=lb;		ac1=ac;		rc1=rc; 		custid1=custid;	

	big=new Font("TIMES_ROMAN",25,Font.BOLD);	big1=new Font("TIMES_ROMAN",20,Font.BOLD);
	big2=new Font("TIMES_ROMAN",10,Font.BOLD);	small=new Font("TIMES_ROMAN",12,Font.BOLD);
	Font f=new Font("Georgia",Font.BOLD,35);	Font f1=new Font("Arial",Font.BOLD,16);	Font f2=new Font("Geograpia",Font.BOLD,11);

		bg=new ButtonGroup();	
	
		rb1=new JRadioButton("iPad");		rb2=new JRadioButton("iPhone");
		
		mdlpnl=new JPanel();	mdldtl=new JPanel();		imgpnl=new JPanel();		
	
		mdlpnl.setLayout(null);	mdldtl.setLayout(null);		imgpnl.setLayout(null);
	
		ipad=new JComboBox();		iphn=new JComboBox();		ipadimei=new JComboBox();	
		iphnimei=new JComboBox();		custname=new JComboBox();		
	
		iadd=new JButton("Add Item");		iclear=new JButton("Clear Item");		clear=new JButton("Clear All");
		save=new JButton("Generate Bill");		print=new JButton("Print");		exit=new JButton("Exit");
		newc=new JButton("New Customer");		oldc=new JButton("Old Customer");	confirm=new JButton("Generate Total");
		remove=new JButton("Remove Item");		calb = new DateButton();
		
	tname=new JTextField(nm);	tbillno =new JTextField();	tbtrynod=new JTextField();	
	tbtrynon=new JTextField();		tmdlclrd =new JTextField();	tmdlclrn =new JTextField();	
	twrnty =new JTextField();	ttotal=new JTextField();	tvat =new JTextField();		tgtotal=new JTextField();
	tadd=new TextArea(ad);	tpriced=new JTextField();tpricen=new JTextField();
	tbtrynod.setEditable(false);	tbtrynon.setEditable(false);
	tmdlclrd.setEditable(false);	tmdlclrn.setEditable(false);
	tpriced.setEditable(false);tpricen.setEditable(false);	tbillno.setEditable(false);
	tname.setEditable(false);	tadd.setEditable(false);
	
	title=new JLabel("SMART-PHONE SHOP");
	mdlname=new JLabel("Model Name");
	name=new JLabel("Name:");	add=new JLabel("Address:");		billno=new JLabel("Bill No.:");
	date=new JLabel("Date:");	mdlnm=new JLabel("Select Model Name:");	mdlno=new JLabel("Select Model No.");
	mdlimei=new JLabel("Select Model IMEI No.:");		btryno=new JLabel("Select Battery No.:");		mdlclr=new JLabel("Select Model Colour :");
	mdlno1=new JLabel("I.M.E.I. No.");	amt=new JLabel("Amount");	total=new JLabel("Total :");		vat=new JLabel("VAT 12.5% :");
	gtotal=new JLabel("Grand Total :");	price=new JLabel("Price :");	wrnty=new JLabel("Warranty");	year=new JLabel("-year");

		mdllst=new List();		mdlamt=new List();		lstmno=new List();
	
	imgpnl.setVisible(false);	mdlamt.setEnabled(false);	tvat.setEditable(false);	ttotal.setEditable(false);	tgtotal.setEditable(false);
	custname.setVisible(false);	custname.setEnabled(false);
	iphn.setVisible(false);	iphnimei.setVisible(false); 
	iphn.setEnabled(false);	iphnimei.setEnabled(false); 
	ipad.setEnabled(false);	ipadimei.setEnabled(false); 
	confirm.setEnabled(false);	 
	save.setEnabled(false);	 
	
	title.setFont(f);
	name.setFont(f1);		add.setFont(f1);
	billno.setFont(f1);	date.setFont(f1);
	
	tbillno.setHorizontalAlignment(JTextField.CENTER);	twrnty.setHorizontalAlignment(JTextField.CENTER);		
	ipad.addItem("Select Your iPad Model");	iphn.addItem("Select Your iPhone Model");
	ipadimei.addItem("Select iPad IMEI No.");	iphnimei.addItem("Select iPhone IMEI No.");
	custname.addItem("Select Customer Name");
	mdlpnl.setBackground(Color.PINK);	mdldtl.setBackground(Color.PINK);	imgpnl.setBackground(Color.RED);

	add(mdlpnl);	 add(mdldtl);	add(imgpnl);		
	add(title);	 add(name);	add(add);		add(billno);	add(date);
	add(tname);	 add(tbillno);	add(calb);
	add(newc);	add(oldc);add(custname);	add(tadd);
	mdlpnl.add(mdlnm);	mdlpnl.add(mdlno);
	mdlpnl.add(mdlimei);	mdlpnl.add(btryno);	mdlpnl.add(mdlclr);	mdldtl.add(remove);	
	mdlpnl.add(wrnty);		mdlpnl.add(year);	mdlpnl.add(rb1);	mdlpnl.add(rb2);	bg.add(rb1);	bg.add(rb2);
	mdlpnl.add(ipad);	mdlpnl.add(iphn);	mdlpnl.add(ipadimei);	mdlpnl.add(iphnimei);
	mdlpnl.add(iadd);	mdlpnl.add(iclear);	mdldtl.add(clear);	mdldtl.add(save);
//	mdldtl.add(print);
	mdlpnl.add(exit);
	mdlpnl.add(tbtrynod);	mdlpnl.add(tbtrynon);
	mdlpnl.add(tmdlclrd);mdlpnl.add(tmdlclrn);	mdlpnl.add(tpriced);mdlpnl.add(tpricen);	 mdlpnl.add(twrnty);	
	mdldtl.add(ttotal);	mdldtl.add(tvat);	mdldtl.add(tgtotal);	mdldtl.add(mdlname);
	mdldtl.add(vat);		mdldtl.add(total);mdldtl.add(lstmno);		mdldtl.add(gtotal);		mdlpnl.add(price);		
	mdldtl.add(amt);	mdldtl.add(mdlno1);	mdldtl.add(mdllst);	mdldtl.add(mdlamt);	mdldtl.add(confirm);

	total.setBounds(140,230,100,20);	ttotal.setBounds(220,230,150,20);
	vat.setBounds(140,260,100,20);		tvat.setBounds(220,260,150,20);remove.setBounds(5,250,120,40);
	gtotal.setBounds(140,290,100,20);	tgtotal.setBounds(220,290,150,20);clear.setBounds(200,450,150,30);
	//confirm.setBounds(200,330,150,30);	
	confirm.setBounds(200,370,150,30);
	save.setBounds(200,410,150,30);
	
	title.setBounds(190,0,500,50);	name.setBounds(20,65,80,20);	tname.setBounds(100,65,350,20);	custname.setBounds(100,65,350,20);
	tbillno.setBounds(530,65,100,20);	add.setBounds(20,90,80,50);		tadd.setBounds(100,100,350,40);	billno.setBounds(460,65,80,20);
	date.setBounds(460,105,80,20);		calb.setBounds(530,105,100,20);	newc.setBounds(650,60,130,30);	oldc.setBounds(650,100,130,30);
	
	mdlpnl.setBounds(5,170,390,500);		mdldtl.setBounds(400,170,380,500);	imgpnl.setBounds(400,170,380,500);

	mdlnm.setBounds(5,30,150,20);	rb1.setBounds(160,30,80,20);	rb2.setBounds(250,30,100,20);
	mdlno.setBounds(5,60,120,20);	ipad.setBounds(160,60,200,20);	iphn.setBounds(160,60,200,20);
	mdlimei.setBounds(5,90,150,20);	ipadimei.setBounds(160,90,200,20);	iphnimei.setBounds(160,90,200,20);
	btryno.setBounds(5,120,130,20);	tbtrynod.setBounds(160,120,200,20);	tbtrynon.setBounds(160,120,200,20);
	mdlclr.setBounds(5,150,130,20);	tmdlclrd.setBounds(160,150,200,20);	tmdlclrn.setBounds(160,150,200,20);
	price.setBounds(80,200,130,20);	tpriced.setBounds(160,200,100,20);tpricen.setBounds(160,200,100,20);
	wrnty.setBounds(80,230,130,20);	twrnty.setBounds(160,230,100,20);	year.setBounds(260,230,100,20);

	iadd.setBounds(20,450,100,30);	iclear.setBounds(140,450,100,30);	exit.setBounds(260,450,100,30);
	mdlno1.setBounds(40,0,100,20);	amt.setBounds(280,0,100,20);
	mdllst.setBounds(5,20,130,200);	mdlamt.setBounds(220,20,155,200);
	lstmno.setBounds(138,20,80,200);	mdlname.setBounds(145,0,100,20);

	try
	{
	cn=DriverManager.getConnection("jdbc:mysql:///apple1","root","root");	
	stm=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	rs=stm.executeQuery("select max(bill_id) from billmstr");
	rs.next();
	bb1=Integer.parseInt(rs.getString(1));
	bb1=bb1+001;
	tbillno.setText(""+bb1);
	rs.close();
	rs=stm.executeQuery("select max(transid) from trans");
	rs.next();
	tid=Integer.parseInt(rs.getString(1));
	tid=tid+1;
	rs.close();
	rs=stm.executeQuery("select distinct custid,cname,caddrs,contact,vtc,drl,lb,ac,rc from custinfo ");
	while(rs.next())
	{
	custname.addItem(rs.getString(1));
	custname.addItem(rs.getString(2));
	}
	rs.close();	
	rs=stm.executeQuery("select distinct mno from iphone order by mno ");
	while(rs.next())
	{
	iphn.addItem(rs.getString(1));
	}
	rs.close();	
	rs=stm.executeQuery("select distinct mno from ipad order by mno ");
	while(rs.next())
	{
	ipad.addItem(rs.getString(1));
	}
	rs.close();		
	}
	catch(Exception ex)
	{
	ex.printStackTrace();
	}
	newc.addActionListener(this);	oldc.addActionListener(this);	
	rb1.addActionListener(this);	rb2.addActionListener(this);
	iclear.addActionListener(this);	iadd.addActionListener(this);
	clear.addActionListener(this);	exit.addActionListener(this);
	confirm.addActionListener(this);	save.addActionListener(this);
	//print.addActionListener(this);
	remove.addActionListener(this);
	custname.addItemListener(this);	ipad.addItemListener(this);	iphn.addItemListener(this);
	ipadimei.addItemListener(this);	iphnimei.addItemListener(this);
	
	if(!name1.equals("")){	rb1.setEnabled(true);rb2.setEnabled(true);}else{rb1.setEnabled(false);rb2.setEnabled(false);}
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
	setVisible(true);
}		
public void itemStateChanged(ItemEvent e)
{
try
{
    if(custname.getSelectedIndex()!=0)
	{
	try
	{
	if(custname.getSelectedIndex()==0){rb1.setEnabled(false);rb2.setEnabled(false);}else{rb1.setEnabled(true);rb2.setEnabled(true);}
	//if(custname.getSelectedItem().equals(" "))
	rs=stm.executeQuery("select distinct cname,caddrs,custid from custinfo where custid='"+custname.getSelectedItem().toString()+"'");
	rs.next();
	custname.setVisible(false);
	tname.setVisible(true);
	tname.setText((rs.getString(1)));
	tadd.setText((rs.getString(2)));
	ccid=rs.getString(3);
	tadd.setVisible(true);
	rs.close();
	}
	catch(Exception ex)
	{
	JOptionPane.showMessageDialog(null,"Please Select CustID Present Before name !","WARNING!!!",JOptionPane.WARNING_MESSAGE);		
	}	
	}
   if(ipad.getSelectedIndex()!=0)
	{	
		rs=stm.executeQuery("select distinct imei from ipad where mno='"+ipad.getSelectedItem().toString()+"'");
		if(flag==0)
		{
		while(rs.next())
		{
		ipadimei.addItem(rs.getString(1));
		}
		rs.close();
		flag=1;
		}
	}
	if(iphn.getSelectedIndex()!=0)
	{	
		rs=stm.executeQuery("select distinct imei from iphone where mno='"+iphn.getSelectedItem().toString()+"'");
		if(flag1==0)
		{
		while(rs.next())
		{
		iphnimei.addItem(rs.getString(1));
		}
		rs.close();	
		flag1=1;
		}
	}
	if(ipadimei.getSelectedIndex()!=0)
	{	
		rs=stm.executeQuery("select distinct btryno,color,price from ipad where mno='"+ipad.getSelectedItem().toString()+"'");
		rs.next();
		{
		tbtrynod.setText(rs.getString(1));
		tmdlclrd.setText(rs.getString(2));
		tpriced.setText(rs.getString(3));
		}rs.close();
	}
	if(iphnimei.getSelectedIndex()!=0)
	{	
		rs=stm.executeQuery("select distinct btryno,color,price from iphone where mno='"+iphn.getSelectedItem().toString()+"'");
		rs.next();
		{
		tbtrynon.setText(rs.getString(1));
		tmdlclrn.setText(rs.getString(2));
		tpricen.setText(rs.getString(3));
		}
		rs.close();
	}
}
catch(Exception ex)
{
ex.printStackTrace();
}	
}
public void actionPerformed(ActionEvent e)
{
try
{
	if(e.getSource()==rb1) 
	{
	ipad.setVisible(true);		ipad.setEnabled(true);	
	ipadimei.setVisible(true);		ipadimei.setEnabled(true);
	tmdlclrd.setVisible(true);	tpriced.setVisible(true);
	iphn.setVisible(false);	iphn.setEnabled(false);
	iphnimei.setVisible(false);	iphnimei.setEnabled(false);
	tmdlclrn.setVisible(false);	tpricen.setVisible(false);
	}
	if(e.getSource()==rb2) 
	{
	iphn.setVisible(true);		iphn.setEnabled(true);	
	iphnimei.setVisible(true);	iphnimei.setEnabled(true);
	tmdlclrn.setVisible(true);	tpricen.setVisible(true);
	ipad.setVisible(false);		ipad.setEnabled(false);
	ipadimei.setVisible(false);	ipadimei.setEnabled(false);
	tmdlclrd.setVisible(false);	tpriced.setVisible(false);
	}
	if(e.getSource()==newc) 
	{
	new custdetail();
	dispose();
	flag3=0;
	}
	if(e.getSource()==exit) 
	{	
	dispose();
	}
	if(e.getSource()==iclear) 
	{
	clear();
	}
	if(e.getSource()==oldc) 
	{
	tadd.setVisible(false);
	tname.setVisible(false);
	custname.setVisible(true);	custname.setEnabled(true);
	flag3=1;
	}
	if(e.getSource()==iadd) 
	{
	String w=twrnty.getText().toString();
	if((ipad.getSelectedIndex()!=0) && !w.equals(""))
	{
	lstmno.addItem(ipad.getSelectedItem().toString());
	mdllst.addItem(ipadimei.getSelectedItem().toString());
	mdlamt.addItem(tpriced.getText().toString());
	sql="insert into mblinfo values('"+ipadimei.getSelectedItem().toString()+"','"+ipad.getSelectedItem().toString()+"','iPad','"+tbtrynod.getText().toString()+"','"+twrnty.getText().toString()+"','"+tmdlclrd.getText().toString()+"','"+tpriced.getText().toString()+"')";	
	prstm=cn.prepareStatement(sql);		prstm.execute();
	sql1="insert into trans values('"+tid+"','"+bb1+"','"+ipadimei.getSelectedItem().toString()+"','"+tpriced.getText().toString()+"')";
	prstm=cn.prepareStatement(sql1);		prstm.execute();
	sql2="delete from ipad where imei='"+ipadimei.getSelectedItem().toString()+"'";
	prstm=cn.prepareStatement(sql2);		prstm.execute();
	prstm.close();
	tid=tid+1;
	confirm.setEnabled(true);	 
	clear();
	}
	else if((iphn.getSelectedIndex()!=0)&& !w.equals(""))
	{
	lstmno.addItem(iphn.getSelectedItem().toString());
	mdllst.addItem(iphnimei.getSelectedItem().toString());
	mdlamt.addItem(tpricen.getText().toString());
	sql="insert into mblinfo values('"+iphnimei.getSelectedItem().toString()+"','"+iphn.getSelectedItem().toString()+"','iPhone','"+tbtrynon.getText().toString()+"','"+twrnty.getText().toString()+"','"+tmdlclrn.getText().toString()+"','"+tpricen.getText().toString()+"')";	
	prstm=cn.prepareStatement(sql);		prstm.execute();
	sql1="insert into trans values('"+tid+"','"+bb1+"','"+iphnimei.getSelectedItem().toString()+"','"+tpricen.getText().toString()+"')";
	prstm=cn.prepareStatement(sql1);		prstm.execute();
	sql2="delete from iphone where imei='"+iphnimei.getSelectedItem().toString()+"'";
	prstm=cn.prepareStatement(sql2);		prstm.execute();
	prstm.close();
	tid=tid+1;
	confirm.setEnabled(true);	 
	clear();
	}	
	else
	{
	JOptionPane.showMessageDialog(null,"Please Fill All Information !","WARNING!!!",JOptionPane.WARNING_MESSAGE);		
	}
	}
	if(e.getSource()==remove) 
	{
		if(mdllst.getSelectedIndex()!=-1)
		{	
		sql="delete from mblinfo where imei='"+mdllst.getSelectedItem().toString()+"'";
		prstm=cn.prepareStatement(sql);		prstm.execute();
		sql1="delete from trans where imei1='"+mdllst.getSelectedItem().toString()+"'";
		prstm=cn.prepareStatement(sql1);		prstm.execute();
		prstm.close();
		int no=mdllst.getSelectedIndex();
		mdllst.remove(mdllst.getSelectedItem());
		lstmno.remove(lstmno.getItem(no));
		mdlamt.remove(mdlamt.getItem(no));
		}
	}
	if(e.getSource()==confirm) 
	{
	String lstitem[]=mdlamt.getItems();
	for(int i=0;i<lstitem.length;i++)
	{
	total1=total1+Double.parseDouble(lstitem[i]);
	}
	vat1=total1;
	vat1=vat1*12.5;
	vat1=vat1/100;
	ttotal.setText(""+total1);
	tvat.setText(""+vat1);
	gtotal1=gtotal1+vat1+total1;
	tgtotal.setText(""+gtotal1);
    vat1=0;
	total1=0;
	gtotal1=0;	
	if(flag3==0)
	{
	sql1="insert into custinfo values('"+custid1+"','"+name1+"','"+add1+"','"+contact1+"','"+vc1+"','"+dl1+"','"+lb1+"','"+ac1+"','"+rc1+"')";	
	prstm=cn.prepareStatement(sql1);prstm.execute();
	sql2="insert into billmstr values('"+bb1+"','"+custid1+"','"+calb.getText().toString()+"','"+tgtotal.getText().toString()+"')";
	prstm=cn.prepareStatement(sql2);prstm.execute();
	prstm.close();
	bb1++;
	}
	else
	{
	sql2="insert into billmstr values('"+bb1+"','"+ccid+"','"+calb.getText().toString()+"','"+tgtotal.getText().toString()+"')";
	prstm=cn.prepareStatement(sql2);prstm.execute();
	prstm.close();
	bb1++;
	}
	JOptionPane.showMessageDialog(null,"Record Saved Successfully.");
	confirm.setEnabled(false);
	save.setEnabled(true);
	remove.setEnabled(false);
	}
	if(e.getSource()==save) 
	{
			create_pdf();
		write_heading();
		create_tab_heading();
		add_rows();
	close_pdf();
	JOptionPane.showMessageDialog(null,"Take your Bill.");
	save.setEnabled(false);
	}
	if(e.getSource()==clear) 
	{
	tbillno =new JTextField();
	tbillno.setText(""+bb1);
	tbillno.setEditable(false);
	tbillno.setHorizontalAlignment(JTextField.CENTER);
	add(tbillno);tbillno.setBounds(530,65,100,20);	
	mdllst.clear();	mdlamt.clear();	lstmno.clear();
	ttotal.setText("");	tvat.setText("");	tgtotal.setText("");
	rb1.setEnabled(false);	rb2.setEnabled(false);
	ipad.setEnabled(false);	iphn.setEnabled(false);
	ipadimei.setEnabled(false);	iphnimei.setEnabled(false);
	clear();save.setEnabled(false);
	tname.setText("");custname.setVisible(false);
	tname.setVisible(true);
	ipad.setSelectedIndex(0);
	custname.setSelectedIndex(0);
	iphn.setSelectedIndex(0);
	tadd.setText("");
	}
}
catch(Exception ex)
	{
	ex.printStackTrace();
	}	
}
void clear()
{
    ipad.removeItemListener(this);    ipadimei.removeItemListener(this);
    iphn.removeItemListener(this); 	iphnimei.removeItemListener(this);
    iphn.setSelectedIndex(0);	flag1=0;
    ipad.setSelectedIndex(0);	flag=0;
	iphnimei.removeAllItems();	ipadimei.removeAllItems();
	ipadimei.addItem("Select iPad IMEI No.");	iphnimei.addItem("Select iPhone IMEI No.");
	tbtrynod.setText("");tbtrynon.setText("");
	tmdlclrd.setText("");tmdlclrn.setText("");
	tpriced.setText("");	tpricen.setText("");
	twrnty.setText("");
    ipad.addItemListener(this);    ipadimei.addItemListener(this);
    iphn.addItemListener(this);    iphnimei.addItemListener(this);
}
	void create_pdf()
	{
		try
			{
				path="prints/Bill No. "+bb1+".pdf";
				file=new FileOutputStream(new File(path));
				document=new Document();
				PdfWriter.getInstance(document,file);
				document.open();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}			
	}

	void write_heading()
	{
		try
		{
			big=new Font("TIMES_ROMAN",18,Font.BOLD);
			p=new Paragraph("APPLE"); 
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
			document.add(new Paragraph(" "));
			p1=new Paragraph("Smart Phone-Shop"); 
			p1.setAlignment(Element.ALIGN_CENTER);
			document.add(p1);
			p2=new Paragraph("Prathmesh Complex Shop No.1,near T.C.Collage,Baramati. Dist:Pune.413102"); 
			p2.setAlignment(Element.ALIGN_CENTER);
			document.add(p2);
			p3=new Paragraph("Contact Mo.9890459347,Email:omkar.909@gmail.com"); 
			p3.setAlignment(Element.ALIGN_CENTER);
			document.add(p3);
			p4=new Paragraph("Name :"+tname.getText().toString());
			p4.setAlignment(Element.ALIGN_LEFT);
			document.add(p4);
			p4=new Paragraph("Bill No. :"+tbillno.getText().toString());
			p4.setAlignment(Element.ALIGN_RIGHT);
			document.add(p4);
			p5=new Paragraph("Address :"+tadd.getText().toString());
			p5.setAlignment(Element.ALIGN_LEFT);
			document.add(p5);
			p5=new Paragraph("Date :"+calb.getText().toString());
			p5.setAlignment(Element.ALIGN_RIGHT);
			document.add(p5);
			document.add(new Paragraph(" "));
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	void create_tab_heading()
	{
		float[] colswidth={40,40,80,80,60,60};
		table=new PdfPTable(colswidth);
		table.setWidthPercentage(100);
	
	c1=new PdfPCell(new Phrase("Model Name"));
	c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(c1);
	c1=new PdfPCell(new Phrase("Model No."));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	table.addCell(c1);
	c1=new PdfPCell(new Phrase("I.M.E.I. No."));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	table.addCell(c1);
	c1=new PdfPCell(new Phrase("Battery No."));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	table.addCell(c1);
	c1=new PdfPCell(new Phrase("Colour"));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	table.addCell(c1);
	c1=new PdfPCell(new Phrase("Price"));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	table.addCell(c1);
	}	
	void add_rows()
	{
		try
		{
          rs= stm.executeQuery("select mname,mno,imei,btrn,color,mblinfo.price from custinfo,mblinfo,billmstr,trans "+
				"where custinfo.custid=billmstr.cust_id and mblinfo.imei=trans.imei1 and billmstr.bill_id=trans.bno "+
				"and bill_id="+tbillno.getText()+" ");
			{
				rs.beforeFirst();
			document.add(new Paragraph(" "));
						while(rs.next())
					{
    					c1=new PdfPCell(new Phrase(rs.getString(1)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);
	
						c1=new PdfPCell(new Phrase(rs.getString(2)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);
												
						c1=new PdfPCell(new Phrase(rs.getString(3)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);
						
						c1=new PdfPCell(new Phrase(rs.getString(4)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);
												
       					c1=new PdfPCell(new Phrase(rs.getString(5)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);
						
						c1=new PdfPCell(new Phrase(rs.getString(6)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);
					}
			}
			rs.close();
			cn.close();


						c1=new PdfPCell(new Phrase(""));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);
		
						c1=new PdfPCell(new Phrase(""));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);
												
						c1=new PdfPCell(new Phrase(""));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);
						
						c1=new PdfPCell(new Phrase(""));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);
												
       					c1=new PdfPCell(new Phrase("Total"));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);
						
						c1=new PdfPCell(new Phrase(ttotal.getText()));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);


						c1=new PdfPCell(new Phrase(""));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);
	
						c1=new PdfPCell(new Phrase(""));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);
												
						c1=new PdfPCell(new Phrase(""));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);
						
						c1=new PdfPCell(new Phrase(""));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);
												
       					c1=new PdfPCell(new Phrase("VAT(12.5%)"));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);
						
						c1=new PdfPCell(new Phrase(tvat.getText()));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);

						
						c1=new PdfPCell(new Phrase(""));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);
	
						c1=new PdfPCell(new Phrase(""));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);
												
						c1=new PdfPCell(new Phrase(""));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);
						
						c1=new PdfPCell(new Phrase(""));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);
												
       					c1=new PdfPCell(new Phrase("Grand Total :"));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);
						
						c1=new PdfPCell(new Phrase(tgtotal.getText()));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(c1);
						
			document.add(table);
			document.add(new Paragraph(" "));
		}
		catch(Exception e)
		{	
		System.out.println(e);
		}
	}
	void close_pdf()
	{
		try
		{
			document.close();
			file.close();
			Desktop desktop=Desktop.getDesktop();
			desktop.open(new java.io.File(path));		
		}
		catch(Exception e)
		{	
			e.printStackTrace();
		}
	}
public static void main(String args[])
	{
		new billfrm("","","","","","","","","");
	}
}
