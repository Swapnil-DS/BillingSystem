import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.sql.*;
import javax.swing.table.*;
import java.text.*;
class showstock extends JFrame implements ActionListener,ItemListener
{
JButton b1show,b2clear,b3cancel;
JLabel l1title,l2mdlnm,l3mdlno,l4totalQ;
JTextField t1quantity;
JComboBox cb1mdlnm,cb2ipad,cb3iphn;
JPanel p1,p2,p3,p4;
Connection cn;
JTable table1,table2;
PreparedStatement prstm;
String sql,sql1;
Statement stm;
ResultSet rs,rs1;
	showstock()
	{
		setTitle("Stock Department");
		setLocation(400,5);
		setSize(600,725);
		setLayout(null);
		setVisible(true);
		
	Font f=new Font("Georgia",Font.BOLD,25);Font f1=new Font("Georgia",Font.BOLD,20);

	b1show=new JButton("View");	b2clear=new JButton("Clear");	b3cancel=new JButton("Cancel");
	
	l1title=new JLabel("Stock Department Information");l1title.setFont(f);l2mdlnm=new JLabel("Select Model Name:-");
	l3mdlno=new JLabel("Select Model No.:-");l4totalQ=new JLabel("Total no. of Quantity:-");
	
	t1quantity=new JTextField(40);	
	t1quantity.setEditable(false);	
	
	cb1mdlnm=new JComboBox();	cb2ipad=new JComboBox();		cb3iphn=new JComboBox();
	
	p1=new JPanel();
	p2=new JPanel();
	p3=new JPanel();
	p4=new JPanel();
	
	cb1mdlnm.addItem("Select Model Name");cb1mdlnm.addItem("1.Apple iPad");cb1mdlnm.addItem("2.Apple iPhone");
	cb2ipad.addItem("Select iPad Model");cb3iphn.addItem("Select iPhone Model");
	
	p1.setBackground(Color.GRAY);p1.setLayout(null);p1.setVisible(false);
	p2.setBackground(Color.GRAY);p2.setLayout(null);p2.setVisible(false);
	p3.setBackground(Color.GRAY);p3.setLayout(null);p3.setVisible(false);
	p4.setBackground(Color.GRAY);p4.setLayout(null);p4.setVisible(false);
//	cb2ipad.setVisible(false);
	cb3iphn.setVisible(false);
	table1 = new JTable();
	table2 = new JTable();
	add(table1);
	add(table2);
	add(p1);
	add(p2);
	add(p3);
	add(p4);

	add(b1show);	add(b2clear);	add(b3cancel);
	
	add(t1quantity);
	add(l1title);	 add(l2mdlnm);	add(l3mdlno);	add(l4totalQ);
	
	add(cb1mdlnm);	add(cb2ipad);	add(cb3iphn);
	
	p1.setBounds(5,280,575,400);	
	p2.setBounds(5,280,575,400);
	p3.setBounds(5,280,575,400);
	p4.setBounds(5,280,575,400);

	l1title.setBounds(100,5,400,30);	l2mdlnm.setBounds(60,80,120,20);	l3mdlno.setBounds(60,120,120,20);

	l4totalQ.setBounds(60,160,120,20);

	t1quantity.setBounds(190,160,150,20);

	cb1mdlnm.setBounds(190,80,150,20);	cb2ipad.setBounds(190,120,150,20);	cb3iphn.setBounds(190,120,150,20);

	b1show.setBounds(50,220,100,30);
	b2clear.setBounds(170,220,100,30);
	b3cancel.setBounds(290,220,100,30);
		
	try
	{
	cn=DriverManager.getConnection("jdbc:mysql:///apple1","root","root");	
	stm=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	rs=stm.executeQuery("select distinct mno from ipad");
	while(rs.next())
	{
	cb2ipad.addItem(rs.getString(1));
	}
	rs.close();	
	rs=stm.executeQuery("select distinct mno from iphone");
	while(rs.next())
	{
	cb3iphn.addItem(rs.getString(1));
	}
	rs.close();	
	}
	catch(Exception ex)
	{
	ex.printStackTrace();
	}
	cb1mdlnm.addItemListener(this);	cb2ipad.addItemListener(this);	cb3iphn.addItemListener(this);
	b1show.addActionListener(this);	b2clear.addActionListener(this);	b3cancel.addActionListener(this);
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
}
   void updateTableipad()
    {
       try
        {
        rs = stm.executeQuery("select count(*) from ipad");
        rs.first();
        int rowcnt = rs.getInt(1);
        String colHeads[] = { "Model No.", "IMEI","Battery No.", "Price","Color" };
        String data[][] = new String[rowcnt][5];
        
        rs = stm.executeQuery("select * from ipad order by mno");
        rs.first();

        for(int i=0;i<rowcnt;i++)
        {
            data[i][0] = rs.getString(1);
            data[i][1] = rs.getString(2);
            data[i][2] = rs.getString(3);
            data[i][3] = rs.getString(4);
            data[i][4] = rs.getString(5);
            rs.next();
        }

        JTable table = new JTable(data, colHeads);
        p1.add(table);
		        DefaultTableColumnModel colModel=(DefaultTableColumnModel)
		table.getColumnModel();
    	TableColumn col=colModel.getColumn(1);
    	col=colModel.getColumn(0);        col.setPreferredWidth(50);
        col=colModel.getColumn(1);        col.setPreferredWidth(100);
    	col=colModel.getColumn(2);        col.setPreferredWidth(100);
    	col=colModel.getColumn(3);        col.setPreferredWidth(60);
    	col=colModel.getColumn(4);        col.setPreferredWidth(60);
		table.setEnabled(false);
        JScrollPane jsp = new JScrollPane(table);
       p1.add(jsp);
        jsp.setBounds(5,5,565,390);
	    }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }
void updateTableiphone()
    {
       try
        {
        rs = stm.executeQuery("select count(*) from iphone");
        rs.first();
        int rowcnt = rs.getInt(1);
        String colHeads[] = { "Model No.", "IMEI","Battery No.", "Price","Color" };
        String data[][] = new String[rowcnt][5];
        
        rs = stm.executeQuery("select * from iphone order by mno");
        rs.first();

        for(int i=0;i<rowcnt;i++)
        {
            data[i][0] = rs.getString(1);
            data[i][1] = rs.getString(2);
            data[i][2] = rs.getString(3);
            data[i][3] = rs.getString(4);
            data[i][4] = rs.getString(5);
            rs.next();
        }
        JTable table = new JTable(data, colHeads);
        p2.add(table);
        DefaultTableColumnModel colModel=(DefaultTableColumnModel)
		table.getColumnModel();
    	TableColumn col=colModel.getColumn(1);
    	col=colModel.getColumn(0);        col.setPreferredWidth(50);
        col=colModel.getColumn(1);        col.setPreferredWidth(100);
    	col=colModel.getColumn(2);        col.setPreferredWidth(100);
    	col=colModel.getColumn(3);        col.setPreferredWidth(60);
    	col=colModel.getColumn(4);        col.setPreferredWidth(60);
		table.setEnabled(false);
        JScrollPane jsp = new JScrollPane(table);
        p2.add(jsp);
        jsp.setBounds(5,5,565,390);
	    }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }
public void itemStateChanged(ItemEvent e)
{
updateTableipad();
updateTableiphone();	
	try
    {
     if(cb1mdlnm.getSelectedIndex()==0)
	{
	cb2ipad.setEnabled(false);cb3iphn.setEnabled(false);p1.setVisible(false);
	p2.setVisible(false);p3.setVisible(false);p4.setVisible(false);
	}
    if(cb1mdlnm.getSelectedIndex()==1)
	{
	cb2ipad.setEnabled(true);cb2ipad.setVisible(true);cb3iphn.setVisible(false);
	p1.setVisible(true);p3.setVisible(false);p4.setVisible(false);p2.setVisible(false);updateTableipad();
	}
   if(cb1mdlnm.getSelectedIndex() ==2)
	{
	cb2ipad.setVisible(false);cb3iphn.setEnabled(true);cb3iphn.setVisible(true);
	p2.setVisible(true);p3.setVisible(false);p1.setVisible(false);p4.setVisible(false);updateTableiphone();
	}
if(cb2ipad.getSelectedIndex()!=0)
		{	
		p1.setVisible(true);	p2.setVisible(false);	p3.setVisible(false);	p4.setVisible(false);
        }
if(cb3iphn.getSelectedIndex()!=0)
		{
		p1.setVisible(false);	p2.setVisible(false);p3.setVisible(false);	p4.setVisible(false);
		}
	}
		catch (Exception e1)
		{
		System.out.print(e1);	
		}
}
public void actionPerformed(ActionEvent e)
{
	try
	{
if(e.getSource()==b1show)
	{
	p1.setVisible(false);	p2.setVisible(false);
	if(cb2ipad.getSelectedIndex()!=0)
	{
	p4.setVisible(false);	p3.setVisible(true);
	table1.removeAll();
	table2.removeAll();
       	rs = stm.executeQuery("select count(*) from ipad where mno='"+cb2ipad.getSelectedItem().toString()+"'");
        rs.first();        
		t1quantity.setText(rs.getString(1));  
        String colHeads[] = { "Model No.", "IMEI","Battery No.", "Price","Color" };
		String data[][]={};
      try
      {
          cn = DriverManager.getConnection("jdbc:mysql:///apple1","root", "root");
          stm = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
	      int rows=0,i;	
          rs1= stm.executeQuery("select * from  ipad where mno='"+cb2ipad.getSelectedItem().toString()+"'order by mno");
          while(rs1.next())
          {
              rows++;
          }
          data = new String[rows][5];
          rs1.first();
          for(i=0;i<rows;i++)
          {
              data[i][0]=rs1.getString(1);
              data[i][1]=rs1.getString(2);
              data[i][2]=rs1.getString(3);
              data[i][3]=rs1.getString(4);
              data[i][4]=rs1.getString(5);
              rs1.next();
          }
      }
      catch(Exception exx)
      {
          JOptionPane.showMessageDialog(null,""+exx);
      }
		table1 = new JTable(data, colHeads);
		p3.add(table1);
        DefaultTableColumnModel colModel=(DefaultTableColumnModel)
		table1.getColumnModel();
    	TableColumn col=colModel.getColumn(1);
    	col=colModel.getColumn(0);        col.setPreferredWidth(50);
        col=colModel.getColumn(1);        col.setPreferredWidth(100);
    	col=colModel.getColumn(2);        col.setPreferredWidth(100);
    	col=colModel.getColumn(3);        col.setPreferredWidth(60);
    	col=colModel.getColumn(4);        col.setPreferredWidth(60);
		table1.setEnabled(false);
        JScrollPane jsp = new JScrollPane(table1);
        p3.add(jsp);
        jsp.setBounds(5,5,565,390);
		rs.close();
		rs1.close();
	}
	 else if(cb3iphn.getSelectedIndex()!=0)
	{
	p4.setVisible(true);
	p3.setVisible(false);
    	rs = stm.executeQuery("select count(*) from iphone where mno='"+cb3iphn.getSelectedItem().toString()+"'");
        rs.first();        
		t1quantity.setText(rs.getString(1)); 
        String colHeads[] = { "Model No.", "IMEI","Battery No.", "Price","Color" };
		String data[][]={};
      try
      {
          cn = DriverManager.getConnection("jdbc:mysql:///apple1","root", "root");
          stm = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
	      int rows=0,i;	
          rs1 = stm.executeQuery("select * from  iphone where mno='"+cb3iphn.getSelectedItem().toString()+"' order by mno");
          while(rs1.next())
          {
              rows++;
          }
          data = new String[rows][5];
          rs1.first();
          for(i=0;i<rows;i++)
          {
              data[i][0]=rs1.getString(1);
              data[i][1]=rs1.getString(2);
              data[i][2]=rs1.getString(3);
              data[i][3]=rs1.getString(4);
              data[i][4]=rs1.getString(5);
              rs1.next();
          }
      }
      catch(Exception exx)
      {
          JOptionPane.showMessageDialog(null,""+exx);
      }
        table2 = new JTable(data, colHeads);
		p4.add(table2);
        DefaultTableColumnModel colModel=(DefaultTableColumnModel)
		table2.getColumnModel();
    	TableColumn col=colModel.getColumn(1);
    	col=colModel.getColumn(0);        col.setPreferredWidth(50);
        col=colModel.getColumn(1);        col.setPreferredWidth(100);
    	col=colModel.getColumn(2);        col.setPreferredWidth(100);
    	col=colModel.getColumn(3);        col.setPreferredWidth(60);
    	col=colModel.getColumn(4);        col.setPreferredWidth(60);
		table2.setEnabled(false);
        JScrollPane jsp = new JScrollPane(table2);
        p4.add(jsp);
        jsp.setBounds(5,5,565,390);
		rs.close();
		rs1.close();
	}
	}
if(e.getSource()==b2clear) 
	{
	cb1mdlnm.setSelectedIndex(0);	cb2ipad.setSelectedIndex(0);	cb3iphn.setSelectedIndex(0);	t1quantity.setText("");
	p1.setVisible(false);	p2.setVisible(false);	p3.setVisible(false);	p4.setVisible(false);
	}
if(e.getSource()==b3cancel)
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
	new showstock();
	}
}