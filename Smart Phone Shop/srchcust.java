import java.awt.Desktop;
import javax.swing.table.*;
import java.text.*;
import java.io.*;
import jxl.*;
import java.sql.*;
import com.itextpdf.text.*;
import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;
import java.awt.*;
import java.awt.List;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.sql.*;

class srchcust extends JFrame implements ActionListener,ItemListener
{
    JLabel ltitle,ldate,lname,lbill,ladd,lcontact,ls,lid,lmd;
    JTextField tsname,tsbill,tname,tadd,tcontact,tbill,tdate;
    JButton print,clear,cancel;
    JPanel p1,p2,p3;
	String path;
OutputStream file;
Document document;
Paragraph pp,pp1,pp2,pp3,pp4,pp5,pp6;
PdfPTable tablep;
PdfPCell c1;
Font big,small,big1,big2;
int cnt;
	JTable table;
	JCheckBox ch1,ch2,ch3,ch4,ch5;
	ButtonGroup bg;
    JTextField tsrch;
    List l1,l2;
    Connection cn;
	JRadioButton rb1,rb2;
    PreparedStatement prstm;
    Statement stm;
    ResultSet rs;
    String sql;
	DateButton calb;
	srchcust()
    {
        super("Search Customer Information"); 
		setLayout(null);
        setSize(650,700);  
		setVisible(true);
        setLocation(350,5);

		Font f=new Font("Georgia",Font.BOLD,25);
		rb1=new JRadioButton("Customer Name:");		rb2=new JRadioButton("Bill No.");

	    tsname = new JTextField("Enter name for search...");        tsbill = new JTextField("Enter Bill no. for search...");
	    tname = new JTextField("");	    tadd = new JTextField("");	    tcontact = new JTextField("");
	    tdate = new JTextField("");	    tbill = new JTextField("");

		ltitle = new JLabel(">>>Customer Information<<<");		ls = new JLabel("Select your search option :");    
		lname = new JLabel("Name:");        ladd = new JLabel("Address:");        lcontact = new JLabel("Contact:");
        ldate = new JLabel("Date:");        lbill = new JLabel("Bill No.:");			lid = new JLabel("Address Proofs :");
		lmd = new JLabel("Mobile Details :");
	   
        clear = new JButton("Clear");  		print = new JButton("Print");		cancel = new JButton("Cancel");

		calb = new DateButton(); calb.setEnabled(false);

		ch1=new JCheckBox("Voting Card");	ch2=new JCheckBox("Driving Licence");	ch3=new JCheckBox("Light Bill");
		ch4=new JCheckBox("Adhar Card");	ch5=new JCheckBox("Ration Card");

        p1= new JPanel();p1.setLayout(null);p1.setBackground(Color.GRAY);
        p2= new JPanel();p2.setLayout(null);p2.setBackground(Color.GRAY);
        p3= new JPanel();p3.setLayout(null);//p3.setBackground(Color.PINK);
     
        l1 = new List();	l2 = new List();		bg=new ButtonGroup();
		
		add(ltitle);		ltitle.setFont(f);	

		tsname.setEditable(false);		tsbill.setEditable(false);		tsbill.setVisible(false);
		tbill.setEditable(false);		tdate.setEditable(false);		tname.setEditable(false);
		tadd.setEditable(false);		tcontact.setEditable(false);

		tsname.setHorizontalAlignment(JTextField.CENTER);		tsbill.setHorizontalAlignment(JTextField.CENTER);

		add(ls);
		add(rb1);		add(rb2);		bg.add(rb1);		bg.add(rb2);
		add(p1);	p1.add(l1);	p1.add(l2); 
		p1.add(tsbill);	p1.add(tsname);
		add(p2);			add(p3);			//add(p3);	
		p2.add(tdate);		p2.add(tbill);		p2.add(lid);	p2.add(lmd);
		p2.add(ldate);		p2.add(lbill);		p2.add(calb);
		p2.add(lname);		p2.add(ladd);		p2.add(lcontact);
		p2.add(tname);		p2.add(tadd);		p2.add(tcontact);
		p2.add(ch1);		p2.add(ch2);		p2.add(ch3);		p2.add(ch4);		p2.add(ch5);
		p2.add(print);		p2.add(clear);		p2.add(cancel);
		
		ltitle.setBounds(130,10,500,20);  	ls.setBounds(80,50,200,20);  
		rb1.setBounds(230,70,120,20);		rb2.setBounds(360,70,100,20);  
		p1.setBounds(60,90,510,205);  
		tsname.setBounds(25,10,450,30); 	tsbill.setBounds(25,10,450,30);  
		l1.setBounds(25,45,220,150);	l2.setBounds(255,45,220,150);  
		p2.setBounds(60,300,510,355);  
		lbill.setBounds(5,5,50,20);  		ldate.setBounds(370,5,30,20);  
		tbill.setBounds(55,5,80,20);		tdate.setBounds(405,5,100,20);		lid.setBounds(20,135,100,20);
		lname.setBounds(20,45,80,20); 		ladd.setBounds(20,75,80,20); 		lcontact.setBounds(20,105,80,20); 
		tname.setBounds(100,45,350,20); 		tadd.setBounds(100,75,350,20); 		tcontact.setBounds(100,105,350,20); 
		ch1.setBounds(40,160,120,20);		ch2.setBounds(40,185,120,20);		ch3.setBounds(200,160,120,20);		
		ch4.setBounds(200,185,120,20);		ch5.setBounds(360,160,120,20);	lmd.setBounds(20,215,100,20);
    	p3.setBounds(5,542,625,70);   
		print.setBounds(100,320,80,30);    	clear.setBounds(200,320,80,30);  		cancel.setBounds(300,320,80,30);  
	    
        clear.addActionListener(this);       print.addActionListener(this);       cancel.addActionListener(this);
		rb1.addActionListener(this);	rb2.addActionListener(this);        l1.addItemListener(this);
	        l2.addItemListener(this);

        try
        {   cn = DriverManager.getConnection("jdbc:mysql:///apple1","root", "root");
            stm = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE );
        }
        catch(Exception e)
        { e.printStackTrace();
        }
		 tsbill.addKeyListener(new KeyAdapter()
        {   public void keyPressed(KeyEvent e)
            {   if(e.getKeyChar() == KeyEvent.VK_ENTER)
                { try
                    {   l1.clear(); l2.clear();
                        rs = stm.executeQuery("select cname,bill_id from custinfo,mblinfo,billmstr,trans "+
	" where custinfo.custid=billmstr.cust_id and mblinfo.imei=trans.imei1 and billmstr.bill_id=trans.bno and bill_id='"+tsbill.getText() +"'");
                  if(!tsbill.getText().toString().equals(""))
				{
				while(rs.next())
					{ 
					l1.addItem(rs.getString(1));
					l2.addItem(rs.getString(2));
					}
				}
				else
				{
				JOptionPane.showMessageDialog(null,"Please Enter Bill No. !","WARNING!!!",JOptionPane.WARNING_MESSAGE);		
				}
				}
				catch(Exception ee){ee.printStackTrace();}
                }
            }
        });
	    tsname.addKeyListener(new KeyAdapter()
        {   public void keyPressed(KeyEvent e)
            {   if(e.getKeyChar() == KeyEvent.VK_ENTER)
                { try
                    { l1.clear(); l2.clear();
                        rs = stm.executeQuery("select distinct cname,billmstr.bill_id from custinfo,mblinfo,billmstr,trans "+
				" where custinfo.custid=billmstr.cust_id and mblinfo.imei=trans.imei1 and billmstr.bill_id=trans.bno and cname like '%" + tsname.getText() + "%'");
                 if(!tsname.getText().toString().equals(""))
				{
				while(rs.next())
					{ 
					l1.addItem(rs.getString(1));
					l2.addItem(rs.getString(2));
					}
				}
				else
				{
				JOptionPane.showMessageDialog(null,"Please Enter Name...!","WARNING!!!",JOptionPane.WARNING_MESSAGE);		
				}
				}
                    catch(Exception ee){ee.printStackTrace();}
                }
            }
        });
		setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent e)
    {
	    try
        {
            if(e.getSource() == clear)
            {
                tsname.setText("");	tsbill.setText("");	tbill.setText("");	l1.clear();	l2.clear();
                tname.setText("");    tadd.setText("");   tcontact.setText("");
				ch1.setSelected(false);	ch2.setSelected(false);	ch3.setSelected(false);	ch4.setSelected(false);	ch5.setSelected(false);
				tsname.setEditable(false);	tsname.setVisible(true);	tsbill.setVisible(false);	rb1.setSelected(true);
				tdate.setText("");table.setVisible(false);
			}

            if(e.getSource() == print)
            {
			create_pdf();
		write_heading();
		create_tab_heading();
		add_rows();
	close_pdf();
	JOptionPane.showMessageDialog(null,"Take your Bill.");
			}

            if(e.getSource() == cancel)
            {
			dispose();
			}
			if(rb1.isSelected()==true)
			{
			tsname.setVisible(true);
			tsname.setEditable(true);
			tsname.setText("Enter name.....");
			tsbill.setVisible(false);
			tsbill.setEditable(false);
			}
			if(rb2.isSelected()==true)
			{
			tsname.setVisible(false);
			tsname.setEditable(false);
			tsbill.setVisible(true);
			tsbill.setEditable(true);
			tsbill.setText("Enter Bil no.....");
			}			
			
} // End of Try
     catch (Exception e1)
     {    System.out.println(e1);
     }
    } // End of ActionListener

    public void itemStateChanged(ItemEvent e)
		{
       if(e.getSource()==l1)
        {
		    try
            {
                sql = "select  bill_id,cname,caddrs,contact,vtc,drl,lb,ac,rc,billdate from custinfo,mblinfo,billmstr,trans "+
	" where custinfo.custid=billmstr.cust_id and mblinfo.imei=trans.imei1 and billmstr.bill_id=trans.bno	and custinfo.cname='"+l1.getSelectedItem() + "'";
                rs = stm.executeQuery(sql);
                rs.next();
                tname.setText(rs.getString(2));
                tadd.setText(rs.getString(3));
                tcontact.setText(rs.getString(4));
                tdate.setText(rs.getString(10));
                tbill.setText(rs.getString(1));
				
				if(rs.getString(5).equals("y"))	
				{
				ch1.setSelected(true);
				}
				else
				ch1.setSelected(false);
				
				if(rs.getString(6).equals("y"))	
				{
				ch2.setSelected(true);
				}
				else
				ch2.setSelected(false);
				if(rs.getString(7).equals("y"))	
				{
				ch3.setSelected(true);
				}
				else
				ch3.setSelected(false);
				if(rs.getString(8).equals("y"))	
				{
				ch4.setSelected(true);
				}
				else
				ch4.setSelected(false);
				if(rs.getString(9).equals("y"))	
				{
				ch5.setSelected(true);
				}
				else
				ch5.setSelected(false);
				
		rs = stm.executeQuery("select count(*) from custinfo,mblinfo,billmstr,trans "+
		" where custinfo.custid=billmstr.cust_id and mblinfo.imei=trans.imei1 and billmstr.bill_id=trans.bno	and custinfo.cname='"+l1.getSelectedItem().toString()+"'");
        rs.first();        
        int rowcnt = rs.getInt(1);
		String colHeads[] = {"Model No.", "Model Name","IMEI","Battery No.", "Price","Color"};
        String data[][] = new String[rowcnt][6];
      try
      {
          cn = DriverManager.getConnection("jdbc:mysql:///apple1","root", "root");
          stm = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
	      int rows=0,i;	
          rs= stm.executeQuery("select mname,mno,trans.price,imei,color,btrn from custinfo,mblinfo,billmstr,trans "+
		" where custinfo.custid=billmstr.cust_id and mblinfo.imei=trans.imei1 and billmstr.bill_id=trans.bno	and custinfo.cname='"+l1.getSelectedItem().toString()+"'");
          while(rs.next())
          {
              rows++;
          }
          rs.first();
          for(i=0;i<rows;i++)
          {
              data[i][0]=rs.getString(2);
              data[i][1]=rs.getString(1);
              data[i][2]=rs.getString(4);
              data[i][3]=rs.getString(6);
              data[i][4]=rs.getString(3);
              data[i][5]=rs.getString(5);
              rs.next();
          }
      }
      catch(Exception exx)
      {
          JOptionPane.showMessageDialog(null,""+exx);
      }
        table = new JTable(data, colHeads);
		p3.add(table);
            DefaultTableColumnModel colModel=(DefaultTableColumnModel)
		table.getColumnModel();
    	TableColumn col=colModel.getColumn(1);
    	col=colModel.getColumn(0);        col.setPreferredWidth(30);
        col=colModel.getColumn(1);        col.setPreferredWidth(30);
    	col=colModel.getColumn(2);        col.setPreferredWidth(80);
    	col=colModel.getColumn(3);        col.setPreferredWidth(80);
    	col=colModel.getColumn(4);        col.setPreferredWidth(30);
    	col=colModel.getColumn(5);        col.setPreferredWidth(30);
		table.setEnabled(false);
		table.setEnabled(false);
        JScrollPane jsp = new JScrollPane(table);
        p3.add(jsp);
        jsp.setBounds(5,5,615,60);
	        }
            catch (SQLException e1){e1.printStackTrace();}
        }
        
     if(e.getSource()==l2)
        {
		    try
            {
                sql = "select  bill_id,cname,caddrs,contact,vtc,drl,lb,ac,rc,billdate from custinfo,mblinfo,billmstr,trans  "+
	" where custinfo.custid=billmstr.cust_id and mblinfo.imei=trans.imei1 and billmstr.bill_id=trans.bno	and bill_id='"+l2.getSelectedItem() + "'";
                rs = stm.executeQuery(sql);
                rs.next();
                tname.setText(rs.getString(2));
                tadd.setText(rs.getString(3));
                tcontact.setText(rs.getString(4));
                tdate.setText(rs.getString(10));
                tbill.setText(rs.getString(1));
				if(rs.getString(5).equals("y"))	
				{
				ch1.setSelected(true);
				}
				else
				ch1.setSelected(false);
				
				if(rs.getString(6).equals("y"))	
				{
				ch2.setSelected(true);
				}
				else
				ch2.setSelected(false);
				if(rs.getString(7).equals("y"))	
				{
				ch3.setSelected(true);
				}
				else
				ch3.setSelected(false);
				if(rs.getString(8).equals("y"))	
				{
				ch4.setSelected(true);
				}
				else
				ch4.setSelected(false);
				if(rs.getString(9).equals("y"))	
				{
				ch5.setSelected(true);
				}
				else
				ch5.setSelected(false);
				
		rs = stm.executeQuery("select  count(*) from custinfo,mblinfo,billmstr,trans "+
	" where custinfo.custid=billmstr.cust_id and mblinfo.imei=trans.imei1 and billmstr.bill_id=trans.bno	and bill_id='"+l2.getSelectedItem().toString()+"'");
        rs.first();        
        int rowcnt = rs.getInt(1);
		String colHeads[] = {"Model No.", "Model Name","IMEI","Battery No.", "Price","Color"};
        String data[][] = new String[rowcnt][6];
      try
      {
          cn = DriverManager.getConnection("jdbc:mysql:///apple1","root", "root");
          stm = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
	      int rows=0,i;	
          rs= stm.executeQuery("select mno,mname,trans.price,imei,color,btrn from custinfo,mblinfo,billmstr,trans "+
	" where custinfo.custid=billmstr.cust_id and mblinfo.imei=trans.imei1 and billmstr.bill_id=trans.bno	and bill_id='"+l2.getSelectedItem().toString()+"'");
          while(rs.next())
          {
              rows++;
          }
          rs.first();
          for(i=0;i<rows;i++)
          {
              data[i][0]=rs.getString(1);
              data[i][1]=rs.getString(2);
              data[i][2]=rs.getString(4);
              data[i][3]=rs.getString(6);
              data[i][4]=rs.getString(3);
              data[i][5]=rs.getString(5);
              rs.next();
          }
      }
      catch(Exception exx)
      {
          JOptionPane.showMessageDialog(null,""+exx);
      }
       table = new JTable(data, colHeads);
		p3.add(table);
        DefaultTableColumnModel colModel=(DefaultTableColumnModel)
		table.getColumnModel();
    	TableColumn col=colModel.getColumn(1);
    	col=colModel.getColumn(0);        col.setPreferredWidth(30);
        col=colModel.getColumn(1);        col.setPreferredWidth(30);
    	col=colModel.getColumn(2);        col.setPreferredWidth(80);
    	col=colModel.getColumn(3);        col.setPreferredWidth(80);
    	col=colModel.getColumn(4);        col.setPreferredWidth(30);
    	col=colModel.getColumn(5);        col.setPreferredWidth(30);
		table.setEnabled(false);
        JScrollPane jsp = new JScrollPane(table);
        p3.add(jsp);
        jsp.setBounds(5,5,615,60);
	        }
            catch (SQLException e1){e1.printStackTrace();}
        }
    }



	void create_pdf()
	{
		try
			{
				path="prints/Bill No."+tbill.getText()+".pdf";
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
			pp=new Paragraph("APPLE"); 
			pp.setAlignment(Element.ALIGN_CENTER);
			document.add(pp);
			document.add(new Paragraph(" "));
			pp1=new Paragraph("Smart Phone-Shop"); 
			pp1.setAlignment(Element.ALIGN_CENTER);
			document.add(pp1);
			pp2=new Paragraph("Prathmesh Complex Shop No.1,near T.C.Collage,Baramati. Dist:Pune.413102"); 
			pp2.setAlignment(Element.ALIGN_CENTER);
			document.add(pp2);
			pp3=new Paragraph("Contact Mo.9890459347,Email:omkar.909@gmail.com"); 
			pp3.setAlignment(Element.ALIGN_CENTER);
			document.add(pp3);
			pp4=new Paragraph("Name :"+tname.getText().toString());
			pp4.setAlignment(Element.ALIGN_LEFT);
			document.add(pp4);
			pp4=new Paragraph("Bill No. :"+tbill.getText().toString());
			pp4.setAlignment(Element.ALIGN_RIGHT);
			document.add(pp4);
			pp5=new Paragraph("Address :"+tadd.getText().toString());
			pp5.setAlignment(Element.ALIGN_LEFT);
			document.add(pp5);
			pp5=new Paragraph("Date :"+tdate.getText().toString());
			pp5.setAlignment(Element.ALIGN_RIGHT);
			document.add(pp5);
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
		tablep=new PdfPTable(colswidth);
		tablep.setWidthPercentage(100);
	
	c1=new PdfPCell(new Phrase("Model Name"));
	c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    tablep.addCell(c1);
	c1=new PdfPCell(new Phrase("Model No."));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	tablep.addCell(c1);
	c1=new PdfPCell(new Phrase("I.M.E.I. No."));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	tablep.addCell(c1);
	c1=new PdfPCell(new Phrase("Battery No."));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	tablep.addCell(c1);
	c1=new PdfPCell(new Phrase("Colour"));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	tablep.addCell(c1);
	c1=new PdfPCell(new Phrase("Price"));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	tablep.addCell(c1);
	}	
	void add_rows()
	{
		try
		{
          rs= stm.executeQuery("select mname,mno,imei,btrn,color,mblinfo.price,amount from custinfo,mblinfo,billmstr,trans "+
				"where custinfo.custid=billmstr.cust_id and mblinfo.imei=trans.imei1 and billmstr.bill_id=trans.bno "+
				"and bill_id="+tbill.getText()+" ");
			{
				rs.beforeFirst();
			document.add(new Paragraph(" "));
						while(rs.next())
					{
    					c1=new PdfPCell(new Phrase(rs.getString(1)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						tablep.addCell(c1);
	
						c1=new PdfPCell(new Phrase(rs.getString(2)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						tablep.addCell(c1);
												
						c1=new PdfPCell(new Phrase(rs.getString(3)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						tablep.addCell(c1);
						
						c1=new PdfPCell(new Phrase(rs.getString(4)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						tablep.addCell(c1);
												
       					c1=new PdfPCell(new Phrase(rs.getString(5)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						tablep.addCell(c1);
						
						c1=new PdfPCell(new Phrase(rs.getString(6)));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						tablep.addCell(c1);
					}
			}
			rs.close();
			cn.close();


						c1=new PdfPCell(new Phrase(""));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						tablep.addCell(c1);
		
						c1=new PdfPCell(new Phrase(""));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						tablep.addCell(c1);
												
						c1=new PdfPCell(new Phrase(""));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						tablep.addCell(c1);
						
						c1=new PdfPCell(new Phrase(""));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						tablep.addCell(c1);
												
       					c1=new PdfPCell(new Phrase("Total"));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						tablep.addCell(c1);
						
						c1=new PdfPCell(new Phrase(""));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						tablep.addCell(c1);


						c1=new PdfPCell(new Phrase(""));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						tablep.addCell(c1);
	
						c1=new PdfPCell(new Phrase(""));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						tablep.addCell(c1);
												
						c1=new PdfPCell(new Phrase(""));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						tablep.addCell(c1);
						
						c1=new PdfPCell(new Phrase(""));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						tablep.addCell(c1);
												
       					c1=new PdfPCell(new Phrase("VAT(12.5%)"));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						tablep.addCell(c1);
						
						c1=new PdfPCell(new Phrase(("amount")));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						tablep.addCell(c1);

						
						c1=new PdfPCell(new Phrase(""));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						tablep.addCell(c1);
	
						c1=new PdfPCell(new Phrase(""));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						tablep.addCell(c1);
												
						c1=new PdfPCell(new Phrase(""));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						tablep.addCell(c1);
						
						c1=new PdfPCell(new Phrase(""));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						tablep.addCell(c1);
												
       					c1=new PdfPCell(new Phrase("Grand Total :"));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						tablep.addCell(c1);
						
						c1=new PdfPCell(new Phrase("amount"));
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
						tablep.addCell(c1);
						
			document.add(tablep);
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
        new srchcust();
    }
}

