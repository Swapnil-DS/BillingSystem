import javax.swing.table.*;
import java.text.*;
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

class report extends JFrame implements ActionListener
{
int flag=0;
String path;
OutputStream file;
Document document;
Paragraph q,q1,q2,q3,q4,q5,q6;
PdfPTable table1;
PdfPCell c1;
Font big,small,big1,big2;
JButton view,clear,cancel,all,print,print1;
JLabel ltitle,sf,st,rd,ltotal;
JTextField total;
DateButton calb,calb1,calb2;
JTable table;
JPanel p1,p2,p3,p4,p5,p6;
Connection cn;
PreparedStatement prstm;
String sql,sql1;
Statement stm;
ResultSet rs,rs1;
	report()
	{
		setTitle("Generate Bill Report");
		setLocation(300,10);
		setSize(850,700);
		setLayout(null);
		setVisible(true);
		
	Font f=new Font("Georgia",Font.BOLD,25);Font f1=new Font("Georgia",Font.BOLD,20);
	
	    calb = new DateButton();		add(calb);

		calb1 = new DateButton();		add(calb1);

		calb2 = new DateButton();
		add(calb2);

	view=new JButton("View");		clear=new JButton("Clear");cancel=new JButton("Cancel");
	all=new JButton("All Bills");	print=new JButton("Print");	print1=new JButton("Print");
	
	ltitle=new JLabel(">>>Welcome to Report Department<<<");ltitle.setFont(f);
	sf=new JLabel("Select From Date:");	ltotal=new JLabel("Total No.of Records :");
	st=new JLabel("Select To Date:");	rd=new JLabel("Record Details:");

	total=new JTextField(40);

	p1=new JPanel();p1.setBackground(Color.GRAY);p1.setLayout(null);p1.setVisible(false);
	p2=new JPanel();p2.setBackground(Color.GRAY);p2.setLayout(null);p2.setVisible(false);
		
	p1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(Color.BLACK,Color.BLACK),"Customer Details"));
	p2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(Color.BLACK,Color.BLACK),"Customer Details"));
	
	add(ltitle);	add(ltotal);	add(sf);	add(st);
	add(calb);	add(calb1);	
	add(total);	total.setEditable(false);
	add(view);	add(clear);	add(cancel);add(all);	add(print);	add(print1);	add(p1);	add(p2);

	ltotal.setBounds(480,80,150,20);	ltitle.setBounds(120,5,550,30);
	sf.setBounds(130,80,100,20);	st.setBounds(130,120,100,20);
	total.setBounds(490,120,100,20);		p1.setBounds(5,300,830,400);		p2.setBounds(5,300,830,400);	
	calb.setBounds(240,80,100,20);	calb1.setBounds(240,120,100,20);	
	view.setBounds(240,170,100,20);	clear.setBounds(360,170,100,20);
	print.setBounds(240,210,100,20);	print1.setBounds(240,210,100,20);
	cancel.setBounds(360,210,100,20);	all.setBounds(300,250,100,20);	
	
	print.setEnabled(false);	print1.setEnabled(false);
	view.addActionListener(this);	clear.addActionListener(this);	all.addActionListener(this);
	cancel.addActionListener(this);		print.addActionListener(this);		print1.addActionListener(this);
	try
	{
	cn=DriverManager.getConnection("jdbc:mysql:///apple1","root","root");	
	stm=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	}
	catch(Exception ex)
	{
	ex.printStackTrace();
	}
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
}
public void actionPerformed(ActionEvent e)
{
	    try
        {
        if(e.getSource() == all)
		{
		view.setEnabled(false); p1.setVisible(true);p2.setVisible(false);
		calb.setEnabled(false);		calb1.setEnabled(false);
		flag=0;
		rs = stm.executeQuery("select  count(*) from custinfo,mblinfo,billmstr,trans "+
		" where custinfo.custid=billmstr.cust_id and mblinfo.imei=trans.imei1 and billmstr.bill_id=trans.bno");
		rs.first();        
		total.setText(rs.getString(1));
		String colHeads[] = {"Bill no.", "Name", "Address","Model Name","Model No.","Price","Colour","IMEI NO.","Date"};
		String data[][]={};
		try
		 {
          cn = DriverManager.getConnection("jdbc:mysql:///apple1","root", "root");
          stm = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
	      int rows=0,i;	
		  rs.first();
		rs= stm.executeQuery("select  bill_id,imei,caddrs,cname,billdate,mblinfo.price,mno,mname,color from custinfo,mblinfo,billmstr,trans  "+
		" where custinfo.custid=billmstr.cust_id and mblinfo.imei=trans.imei1 and billmstr.bill_id=trans.bno order by bill_id "); 
		while(rs.next())
          {
              rows++;
          }
          data = new String[rows][9];
          rs.first();
          for(i=0;i<rows;i++)
          {
              data[i][0]=rs.getString(1);
              data[i][1]=rs.getString(4);
              data[i][2]=rs.getString(3);
              data[i][3]=rs.getString(8);
              data[i][4]=rs.getString(7);
              data[i][5]=rs.getString(6);
              data[i][6]=rs.getString(9);
              data[i][7]=rs.getString(2);
              data[i][8]=rs.getString(5);
              rs.next();
          }
      }
      catch(Exception exx)
      {
          JOptionPane.showMessageDialog(null,""+exx);
      }
        table = new JTable(data, colHeads);
		p1.add(table);
        DefaultTableColumnModel colModel=(DefaultTableColumnModel)
		table.getColumnModel();
    	TableColumn col=colModel.getColumn(1);
    	col=colModel.getColumn(0);        col.setPreferredWidth(10);
        col=colModel.getColumn(1);        col.setPreferredWidth(60);
    	col=colModel.getColumn(2);        col.setPreferredWidth(60);
    	col=colModel.getColumn(3);        col.setPreferredWidth(40);
    	col=colModel.getColumn(4);        col.setPreferredWidth(20);
    	col=colModel.getColumn(5);        col.setPreferredWidth(20);
    	col=colModel.getColumn(6);        col.setPreferredWidth(20);
    	col=colModel.getColumn(7);        col.setPreferredWidth(70);
    	col=colModel.getColumn(8);        col.setPreferredWidth(40);
    	table.setEnabled(false);
        JScrollPane jsp = new JScrollPane(table);
        p1.add(jsp);
        jsp.setBounds(10,20,810,390);    
		if(total.getText().toString().equals("0")){print1.setEnabled(false);}else{print1.setEnabled(true);}
		print1.setVisible(true);	print.setEnabled(false);		print.setVisible(false);
			}
            if(e.getSource() == clear)
            {
			calb.setEnabled(true);		calb1.setEnabled(true);
		view.setEnabled(true);	all.setEnabled(true);
            total.setText("");		p1.setVisible(false);			p2.setVisible(false);
			print.setEnabled(false);		print1.setEnabled(false);	
			}
			if(e.getSource() == view)
			{
			calb.setEnabled(true);		calb1.setEnabled(true);
			 p2.setVisible(true);	p1.setVisible(false);
			 all.setEnabled(false);
		rs = stm.executeQuery("select count(*) from custinfo,mblinfo,billmstr,trans  "+
	" where custinfo.custid=billmstr.cust_id and mblinfo.imei=trans.imei1 and billmstr.bill_id=trans.bno	and billmstr.billdate>='"+calb.getText().toString()+"' and billmstr.billdate<='"+calb1.getText().toString()+"'");
        rs.first();        
		total.setText(rs.getString(1));
		String colHeads[] = {"Bill no.", "Name", "Address","Model Name","Model No.","Price","Colour","IMEI NO.","Date"};
		String data[][]={};
      try
		{
          cn = DriverManager.getConnection("jdbc:mysql:///apple1","root", "root");
          stm = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
	      int rows=0,i;	
          rs= stm.executeQuery("select  bill_id,imei,caddrs,cname,billdate,trans.price,mno,mname,custid,color from custinfo,mblinfo,billmstr,trans  "+
		" where custinfo.custid=billmstr.cust_id and mblinfo.imei=trans.imei1 and billmstr.bill_id=trans.bno	and billmstr.billdate>='"+calb.getText().toString()+"' and billmstr.billdate<='"+calb1.getText().toString()+"' order by bill_id ");
          while(rs.next())
          {
              rows++;
          }
          data = new String[rows][9];
          rs.first();
          for(i=0;i<rows;i++)
          {
              data[i][0]=rs.getString(1);
              data[i][1]=rs.getString(4);
              data[i][2]=rs.getString(3);
              data[i][3]=rs.getString(8);
              data[i][4]=rs.getString(7);
              data[i][5]=rs.getString(6);
              data[i][6]=rs.getString(10);
              data[i][7]=rs.getString(2);
              data[i][8]=rs.getString(5);
              rs.next();
          }
      }
      catch(Exception exx)
      {
          JOptionPane.showMessageDialog(null,""+exx);
      }
        table = new JTable(data, colHeads);
		p2.add(table);
        DefaultTableColumnModel colModel=(DefaultTableColumnModel)
		table.getColumnModel();
    	TableColumn col=colModel.getColumn(1);
    	col=colModel.getColumn(0);        col.setPreferredWidth(10);
        col=colModel.getColumn(1);        col.setPreferredWidth(60);
    	col=colModel.getColumn(2);        col.setPreferredWidth(60);
    	col=colModel.getColumn(3);        col.setPreferredWidth(40);
    	col=colModel.getColumn(4);        col.setPreferredWidth(20);
    	col=colModel.getColumn(5);        col.setPreferredWidth(20);
    	col=colModel.getColumn(6);        col.setPreferredWidth(20);
    	col=colModel.getColumn(7);        col.setPreferredWidth(70);
    	col=colModel.getColumn(8);        col.setPreferredWidth(40);
		table.setEnabled(false);
        JScrollPane jsp = new JScrollPane(table);
        p2.add(jsp);
        jsp.setBounds(10,20,810,390);    
		if(total.getText().toString().equals("0")){print.setEnabled(false);}else{print.setEnabled(true);}
		print.setVisible(true);	print1.setEnabled(false);		print1.setVisible(false);
		}
		if(e.getSource() == print)
            {
			create_pdf();
			write_heading();
			create_tab_heading();
			add_rows();
			close_pdf();
			}
			if(e.getSource() == print1)
			{
			create_pdf1();
			write_heading1();
			create_tab_heading1();
			add_rows1();
			close_pdf1();
			}
            if(e.getSource() == cancel)
            {
			dispose();
			}
		}
     catch (Exception e1)
     {
	e1.printStackTrace();
    }		
} 
	void create_pdf()
	{
		try
			{
				path="prints/Sales Report on date.pdf";
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
			q1=new Paragraph("Smart Phone-Shop"); 
			q1.setAlignment(Element.ALIGN_CENTER);
			document.add(q1);
			q2=new Paragraph("Sales Report"); 
			q2.setAlignment(Element.ALIGN_CENTER);
			document.add(q2);
			q3=new Paragraph("On Date : "+calb.getText()); 
			q3.setAlignment(Element.ALIGN_RIGHT);
			document.add(q3);
			document.add(new Paragraph(" "));
			q2=new Paragraph("From Date :"+calb.getText()+" to Date : "+calb.getText()); 
			q2.setAlignment(Element.ALIGN_CENTER);
			document.add(q2);
			}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	void create_tab_heading()
	{
		float[] colswidth={30,40,60,30,30,50,50,30,30,30};
		table1=new PdfPTable(colswidth);
		table1.setWidthPercentage(110);	
	c1=new PdfPCell(new Phrase("Bill No."));
	c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    table1.addCell(c1);
	c1=new PdfPCell(new Phrase("Name"));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	table1.addCell(c1);
	c1=new PdfPCell(new Phrase("Address"));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	table1.addCell(c1);
	c1=new PdfPCell(new Phrase("Model Name"));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	table1.addCell(c1);
	c1=new PdfPCell(new Phrase("Model No."));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	table1.addCell(c1);
	c1=new PdfPCell(new Phrase("I.M.E.I."));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	table1.addCell(c1);
	c1=new PdfPCell(new Phrase("Battery"));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	table1.addCell(c1);
	c1=new PdfPCell(new Phrase("Price"));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	table1.addCell(c1);
	c1=new PdfPCell(new Phrase("Colour"));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	table1.addCell(c1);
	c1=new PdfPCell(new Phrase("Date"));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	table1.addCell(c1);
	}	
	void add_rows()
	{
	int cnt=0;
		try
		{
          rs= stm.executeQuery("select  bill_id,imei,caddrs,cname,billdate,trans.price,mno,mname,custid,color,btrn from custinfo,mblinfo,billmstr,trans  "+
		" where custinfo.custid=billmstr.cust_id and mblinfo.imei=trans.imei1 and billmstr.bill_id=trans.bno	and billmstr.billdate>='"+calb.getText().toString()+"' and billmstr.billdate<='"+calb1.getText().toString()+"' order by bill_id ");
			{
			rs.beforeFirst();
			document.add(new Paragraph(" "));
			while(rs.next())
					{
    					c1=new PdfPCell(new Phrase(rs.getString(1)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table1.addCell(c1);
	
						c1=new PdfPCell(new Phrase(rs.getString(4)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table1.addCell(c1);
												
						c1=new PdfPCell(new Phrase(rs.getString(3)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table1.addCell(c1);
						
						c1=new PdfPCell(new Phrase(rs.getString(8)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table1.addCell(c1);
												
       					c1=new PdfPCell(new Phrase(rs.getString(7)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table1.addCell(c1);
						
						c1=new PdfPCell(new Phrase(rs.getString(2)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table1.addCell(c1);
						
						c1=new PdfPCell(new Phrase(rs.getString(11)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table1.addCell(c1);
						
						c1=new PdfPCell(new Phrase(rs.getString(6)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table1.addCell(c1);
						
						c1=new PdfPCell(new Phrase(rs.getString(10)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table1.addCell(c1);
						
						c1=new PdfPCell(new Phrase(rs.getString(5)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table1.addCell(c1);
					cnt++;	
					}
			}
			rs.close();
	
						c1=new PdfPCell(new Phrase("Total :"));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table1.addCell(c1);
						
						c1=new PdfPCell(new Phrase(cnt));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table1.addCell(c1);
						
			document.add(table1);
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
void create_pdf1()
	{
		try
			{
				path="prints/Sales Report.pdf";
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

	void write_heading1()
	{
		try
		{
			big=new Font("TIMES_ROMAN",18,Font.BOLD);
			q1=new Paragraph("Smart Phone-Shop"); 
			q1.setAlignment(Element.ALIGN_CENTER);
			document.add(q1);
			q2=new Paragraph("Sales Report"); 
			q2.setAlignment(Element.ALIGN_CENTER);
			document.add(q2);
			q3=new Paragraph("On Date : "+calb.getText()); 
			q3.setAlignment(Element.ALIGN_RIGHT);
			document.add(q3);
			}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	void create_tab_heading1()
	{
		float[] colswidth={25,30,60,30,30,70,70,20,30};
		table1=new PdfPTable(colswidth);
		table1.setWidthPercentage(110);	
	c1=new PdfPCell(new Phrase("Bill No."));
	c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    table1.addCell(c1);
	c1=new PdfPCell(new Phrase("Name"));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	table1.addCell(c1);
	c1=new PdfPCell(new Phrase("Address"));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	table1.addCell(c1);
	c1=new PdfPCell(new Phrase("Model Name"));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	table1.addCell(c1);
	c1=new PdfPCell(new Phrase("Model No."));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	table1.addCell(c1);
	c1=new PdfPCell(new Phrase("I.M.E.I."));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	table1.addCell(c1);
	c1=new PdfPCell(new Phrase("Battery"));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	table1.addCell(c1);
	c1=new PdfPCell(new Phrase("Price"));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	table1.addCell(c1);
	c1=new PdfPCell(new Phrase("Date"));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	table1.addCell(c1);
	}	
	void add_rows1()
	{
	int cnt=0;
		try
		{
          rs= stm.executeQuery("select  bill_id,imei,caddrs,cname,billdate,trans.price,mno,mname,custid,color,btrn from custinfo,mblinfo,billmstr,trans  "+
	" where custinfo.custid=billmstr.cust_id and mblinfo.imei=trans.imei1 and billmstr.bill_id=trans.bno order by bill_id"); 
			{
			rs.beforeFirst();
			document.add(new Paragraph(" "));
			while(rs.next())
					{
    					c1=new PdfPCell(new Phrase(rs.getString(1)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table1.addCell(c1);
	
						c1=new PdfPCell(new Phrase(rs.getString(4)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table1.addCell(c1);
												
						c1=new PdfPCell(new Phrase(rs.getString(3)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table1.addCell(c1);
						
						c1=new PdfPCell(new Phrase(rs.getString(8)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table1.addCell(c1);
												
       					c1=new PdfPCell(new Phrase(rs.getString(7)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table1.addCell(c1);
						
						c1=new PdfPCell(new Phrase(rs.getString(2)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table1.addCell(c1);
						
						c1=new PdfPCell(new Phrase(rs.getString(11)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table1.addCell(c1);
						
						c1=new PdfPCell(new Phrase(rs.getString(6)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table1.addCell(c1);
						
						c1=new PdfPCell(new Phrase(rs.getString(5)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table1.addCell(c1);
					cnt++;	
					}
			}
			rs.close();
	
						c1=new PdfPCell(new Phrase("Total :"));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table1.addCell(c1);
						
						c1=new PdfPCell(new Phrase(cnt));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table1.addCell(c1);
						
			document.add(table1);
			document.add(new Paragraph(" "));
		}
		catch(Exception e)
		{	
		System.out.println(e);
		}
	}
	void close_pdf1()
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
	new report();
}
}	