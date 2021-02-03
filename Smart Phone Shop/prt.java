import java.awt.Desktop;
import java.io.*;
import jxl.*;
import java.sql.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
public class prt extends JFrame implements ActionListener
{
Connection cn;
ResultSet rs,rs1bn;
PreparedStatement prstm;
String sql,sql1;
Statement stm;
JLabel nm,ad,mo,bd,bd1,email,name,name1,add,add1,bno,bno1,tamount,tamount1,tvat,tvat1,tbamount,tbamount1,custsign,sms,author;
 JButton b1,b2; 
 JPanel p1,p2,p3,p4;
String s1bn,s2name,s3add,s4date,s5amt;
Double vat1,ttl;
int billno;
prt(int bn,Double vat,Double ttl1)
{
billno=bn;
ttl=ttl1;
vat1= vat;
	setTitle("Print");
	setSize(730,750);
	setLocation(330,5);
	setLayout(null);
	setVisible(true);
	
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
	p1=new JPanel();
	p2=new JPanel();
	add(p1);
	add(p2);
	p2.setLayout(null);
	p1.setLayout(null);
	p1.add(p2);
	
	b1=new JButton("Print OK");
	b2=new JButton("Cancel");
	add(b1);
	add(b2);

	nm=new JLabel("Smart-Phone Shop");
	p1.add(nm);
	Font f = new Font("Times New Roman",Font.BOLD,30);
	nm.setFont(f);
	
   ad=new JLabel("shop No.9 Prathmesh Complex Opp. T.C. Collage ,Baramati-413102(pune)");
   p1.add(ad);
   
   email=new JLabel("Email: smart_phone_shop@gmail.com");
	p1.add(email);
	
   mo=new JLabel("Mob. NO.: 9890459347");
   p1.add(mo);
   
   bd=new JLabel("Date:");
   p1.add(bd);
   
   name=new JLabel("Name :");
   p1.add(name);
   
   add=new JLabel("Address :");
   p1.add(add);  
	
  	bno=new JLabel("Bill No:");
   p1.add(bno);
    
   tamount=new JLabel("Total Amount:");
   tvat=new JLabel("12.5% Vat Amount:");
   tbamount=new JLabel("Total Bill Amount:");
   
   p1.add(tamount);
   p1.add(tvat);
   p1.add(tbamount);
   
	p1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
	    
   custsign=new JLabel("Customer Signature");
   p1.add(custsign);
   
   sms=new JLabel("FOR  SMART-PHONE SHOP");
   author=new JLabel("Authorised Signatory");
   p1.add(sms);
   p1.add(author);
   tamount1=new JLabel(""+ttl);
   tvat1=new JLabel(""+vat1);
  p1.add(tamount1);
   p1.add(tvat1);
	try
	{	
	cn=DriverManager.getConnection("jdbc:mysql:///apple1","root","root");	
	stm=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	
	System.out.print(billno);
	rs=stm.executeQuery("select bno,cname,caddrs,billdate,amount from custinfo,mblinfo,billmstr,trans where custinfo.custid=billmstr.cust_id and mblinfo.imei=trans.imei1 and billmstr.bill_id=trans.bno and bill_id="+billno+"");
    rs.next();
	s1bn=rs.getString(1);
	s2name=rs.getString(2);
	s3add=rs.getString(3);
	s4date=rs.getString(4);
	s5amt=rs.getString(5);
					  
	bno1=new JLabel(""+s1bn);
 	name1=new JLabel(""+s2name);
	add1=new JLabel(""+s3add);
  	bd1=new JLabel(""+s4date);
   tbamount1=new JLabel(""+s5amt);
	p1.add(bno1);
	p1.add(add1);  
	p1.add(name1);
    p1.add(bd1);
   p1.add(tbamount1);
   rs.close();
	p1.setBounds(0,0,700,600);   p2.setBounds(5,200,690,200);   nm.setBounds(250,0,300,40);   ad.setBounds(160,50,550,20);
   mo.setBounds(190,70,150,20);   bd.setBounds(560,130,80,20);   bd1.setBounds(630,130,100,20);   email.setBounds(330,70,220,20);
	name.setBounds(130,100,60,20); 	name1.setBounds(200,100,200,20); 	add.setBounds(130,130,60,20); 	add1.setBounds(200,130,200,20); 
	bno.setBounds(560,100,80,20);    bno1.setBounds(630,100,100,20);  tamount.setBounds(50,450,120,20);   	tamount1.setBounds(180,450,100,20);
 	tvat.setBounds(50,480,120,20);	b1.setBounds(250,610,100,20);b2.setBounds(360,610,100,20);	tvat1.setBounds(180,480,100,20);
	tbamount.setBounds(50,510,120,20);tbamount1.setBounds(180,510,100,20);  sms.setBounds(430,500,200,20);	
	author.setBounds(530,550,150,20);	custsign.setBounds(370,550,150,20);
	  }
      catch(Exception exx)
      {
	        exx	.printStackTrace();
      }
   b1.addActionListener(this);
   b2.addActionListener(this);

		String colHeads[] = { "Model Name","Model No.","IMEI No.","Battery No.","Colour","Price"};
        String data[][]={};
        try
		{
          cn = DriverManager.getConnection("jdbc:mysql:///apple1","root", "root");
          stm = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
	      int rows=0,i;	
          rs= stm.executeQuery("select mname,mno,imei,btrn,color,mblinfo.price from custinfo,mblinfo,billmstr,trans "+
				"where custinfo.custid=billmstr.cust_id and mblinfo.imei=trans.imei1 and billmstr.bill_id=trans.bno "+
				"and bill_id="+billno);
          while(rs.next())
          {
              rows++;
          }
		 data = new String[rows][7];
		rs.first();
          for(i=0;i<rows;i++)
          {
              data[i][0]=rs.getString(1);
              data[i][1]=rs.getString(2);
              data[i][2]=rs.getString(3);
              data[i][3]=rs.getString(4);
              data[i][4]=rs.getString(5);
              data[i][5]=rs.getString(6);
              rs.next();
          }
      }
      catch(Exception exx)
      {
          JOptionPane.showMessageDialog(null,"111"+exx);
      }
        JTable table = new JTable(data, colHeads);
		p2.add(table);
		table.setPreferredScrollableViewportSize(new Dimension(500, 200));
		table.setEnabled(false);
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
        int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
        JScrollPane jsp = new JScrollPane(table, v, h);
	    p2.add(jsp);
		jsp.setBounds(5,5,670,190);
}
public void actionPerformed(ActionEvent e)
{
	if(e.getSource()==b1)
	{
	create_pdf();
		write_heading();
		create_tab_heading();
		add_rows();
	close_pdf();
	JOptionPane.showMessageDialog(null,"Take your Bill.");
		}
	if(e.getSource()==b2)
	{
	dispose();
	}
}
    
public static void main(String args[])
{
new prt(0,0.0,0.0);
}
}