import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.sql.*;
import javax.swing.table.*;
import java.text.*;
class addstock extends JFrame implements ActionListener,ItemListener
{
JButton b1vw,b2clr,b3bk,b4ad,badd,bclr,bexit,bimg;
JLabel l1wc,l2mdnm,l3mdno,l4imei,l5clr,l6prc,l7mf,l8dsp,l9pssr,l10cmr,l11ram,l12os,l13strg,l14rs,l15spt,mdlnm,mdlno,clr,prc,limei,lbtry,l1btry;
JTextField t1imei,t2prc,t3dsp,t4prssr,t5cmr,t6ram,t7os,t8strg,t9rs,t10spt,tmdlno,tprc,tclr,timei,tbtry,t1btry;
JComboBox cb1mdl,cb2d,cb3clr,cb4nn,cb5mdl2,cb6clr;
JCheckBox ch1,ch2;
JTextField t;
Image img;
File file = null;
JPanel p1,p2,p3,p4,p5,p6;
ButtonGroup bg;
JRadioButton rb1,rb2;
Connection cn;
PreparedStatement prstm;
String sql,sql1;
Statement stm;
ResultSet rs,rs1;
String dsp,prcr,cmr,ram,os,strg,res,sprt,f1,path1,bb;
String ipad,iphn,clrd,clrn,imei,amt,p,no,im,iclr; 
	addstock()
	{
		setTitle("Add New User");
		setLocation(400,10);
		setSize(600,700);
		setLayout(null);
		setVisible(true);
		
	Font f=new Font("Georgia",Font.BOLD,25);Font f1=new Font("Georgia",Font.BOLD,20);
	bg=new ButtonGroup();
	
	b1vw=new JButton("View");b2clr=new JButton("Clear");b3bk=new JButton("Exit");b4ad=new JButton("Add to Stock");
	bexit=new JButton("Exit");bimg=new JButton("Add Image");badd=new JButton("Add to Stock");bclr=new JButton("Clear");

	rb1=new JRadioButton("Add to Stock");rb2=new JRadioButton("Add new Model");

	l1wc=new JLabel("Welcome to Stock Department");l1wc.setFont(f);l2mdnm=new JLabel("Select Model Name:");
	l3mdno=new JLabel("Select Model No.:");l4imei=new JLabel("I.M.E.I. No.:");l5clr=new JLabel("Colour:");l6prc=new JLabel("Price:");
	l7mf=new JLabel("<<Main Features>>");l7mf.setFont(f1);	l8dsp=new JLabel("1.Display:");l9pssr=new JLabel("2.Processor:");
	l10cmr=new JLabel("3.Camera:");	l11ram=new JLabel("4.RAM:");l12os=new JLabel("5.OS:");l13strg=new JLabel("6.Storage:");
	l14rs=new JLabel("7.Resolution:");l15spt=new JLabel("8.Supported:");mdlnm=new JLabel("Select Model Name:");
	mdlno=new JLabel("Enter Model No:");clr=new JLabel("Select Colour:");	prc=new JLabel("Enter price:");
	limei=new JLabel("Enter IMEI No.:");		lbtry=new JLabel("Enter Battery No.:");	l1btry=new JLabel("Enter Battery No.:");

	t1imei=new JTextField(40);t2prc=new JTextField(40);t3dsp=new JTextField(40);t4prssr=new JTextField(40);
	t5cmr=new JTextField(40);t6ram=new JTextField(40);t7os=new JTextField(40);t8strg=new JTextField(40);
	t9rs=new JTextField(40);t10spt=new JTextField(40);tclr=new JTextField(40);tmdlno=new JTextField(40);
	tprc=new JTextField(40);	timei=new JTextField(40);	tbtry=new JTextField(40);	t1btry=new JTextField(40);
	
	t3dsp.setEditable(false);t4prssr.setEditable(false);t5cmr.setEditable(false);t6ram.setEditable(false);
	t7os.setEditable(false);t8strg.setEditable(false);t9rs.setEditable(false);t10spt.setEditable(false);
	
	cb1mdl=new JComboBox();cb2d=new JComboBox();cb3clr=new JComboBox();
	cb4nn=new JComboBox();cb5mdl2=new JComboBox();cb6clr=new JComboBox();

	p1=new JPanel();p2=new JPanel();p3=new JPanel();
	//p4= new PicturePanel();
	p5=new JPanel();p6=new JPanel();

	p1.setBackground(Color.GRAY);p2.setBackground(Color.GRAY);t2prc.setEditable(false);
	p3.setBackground(Color.GREEN);p6.setBackground(Color.PINK);	p5.setBackground(Color.CYAN);
	p1.setLayout(null);p2.setLayout(null);p3.setLayout(null);p6.setLayout(null);p5.setLayout(null);
	p1.setVisible(false);p2.setVisible(false);p3.setVisible(false);
	//p4.setVisible(true);
	p5.setVisible(false);	
	cb2d.setEnabled(false);cb4nn.setEnabled(false);

	add(rb1);add(rb2);add(l1wc);
	p2.add(lbtry);	p2.add(tbtry);
	p1.add(l1btry);	p1.add(t1btry);
	add(p1);add(p2);add(p3);//add(p6);
	//add(p4, BorderLayout.CENTER);
	add(p5);bg.add(rb1);bg.add(rb2);

	p1.add(t1imei);p1.add(t2prc);
	p1.add(l2mdnm);p1.add(l3mdno);p1.add(l4imei);p1.add(l5clr);p1.add(l6prc);
	p1.add(b1vw);p1.add(b2clr);p1.add(b3bk);p1.add(b4ad);
	p1.add(cb1mdl);p1.add(cb2d);p1.add(cb3clr);p1.add(cb4nn);
	p1.add(p3);p1.add(p5);//p2.add(p4);
	
	//p2.add(p4);
	//p2.add(p6);

	p2.add(l7mf);p2.add(l8dsp);p2.add(l9pssr);p2.add(l10cmr);p2.add(l11ram);p2.add(l12os);
	p2.add(l13strg);p2.add(l14rs);p2.add(l15spt);p2.add(mdlno);p2.add(prc);p2.add(clr);p2.add(limei);

	p2.add(cb5mdl2);p2.add(cb6clr);

	p2.add(t3dsp);p2.add(t4prssr);p2.add(t5cmr);p2.add(t6ram);p2.add(t7os);p2.add(t8strg);
	p2.add(t9rs);p2.add(t10spt);p2.add(tclr);p2.add(tmdlno);p2.add(tprc);p2.add(timei);

	p2.add(badd);
	//p2.add(bimg);
	p2.add(mdlnm);p2.add(bclr);p2.add(bexit);
	
	cb1mdl.addItem("Select Model Name");cb1mdl.addItem("1.Apple iPad");cb1mdl.addItem("2.Apple iPhone");
	cb5mdl2.addItem("Select Model Name");cb5mdl2.addItem("1.Apple iPad");cb5mdl2.addItem("2.Apple iPhone");

	cb2d.addItem("Select iPad Model");cb4nn.addItem("Select iPhone Model");

	cb3clr.addItem("Select Model Colour");cb3clr.addItem("Gray");cb3clr.addItem("White");cb3clr.addItem("Black");
	cb6clr.addItem("Select Model Colour");cb6clr.addItem("Gray");cb6clr.addItem("White");cb6clr.addItem("Black");	
	
	p1.setBounds(5,60,575,600);p2.setBounds(5,60,575,600);p3.setBounds(5,280,565,310);
//	p4.setBounds(295,175,275,420);
p5.setBounds(5,280,565,310);p6.setBounds(290,170,280,425);
	
	cb1mdl.setBounds(250,10,150,20);cb2d.setBounds(250,40,150,20);cb3clr.setBounds(250,100,150,20);
	cb4nn.setBounds(250,40,150,20);cb5mdl2.setBounds(220,10,160,20);cb6clr.setBounds(220,70,160,20);
	
	t1imei.setBounds(250,70,150,20);t2prc.setBounds(250,130,150,20);
	t3dsp.setBounds(200,260,180,20);tmdlno.setBounds(220,40,160,20);
	t4prssr.setBounds(200,290,180,20);t5cmr.setBounds(200,320,180,20);t6ram.setBounds(200,350,180,20);tprc.setBounds(220,100,160,20);
	t7os.setBounds(200,380,180,20);t8strg.setBounds(200,410,180,20);t9rs.setBounds(200,440,180,20);t10spt.setBounds(200,470,180,20);
	timei.setBounds(220,130,160,20);
	lbtry.setBounds(100,160,100,20);tbtry.setBounds(220,160,160,20);
		
	rb1.setBounds(150,35,120,20);	rb2.setBounds(280,35,150,20);
	
	mdlnm.setBounds(100,10,120,20);clr.setBounds(100,70,120,20);mdlno.setBounds(100,40,120,20);prc.setBounds(100,100,120,20);
	l1wc.setBounds(90,2,500,30);l2mdnm.setBounds(120,10,120,20);l3mdno.setBounds(120,40,120,20);l4imei.setBounds(120,70,120,20);
	l5clr.setBounds(120,100,120,20);l6prc.setBounds(120,130,120,30);l7mf.setBounds(140,220,250,30);l8dsp.setBounds(100,260,80,20);
	l9pssr.setBounds(100,290,100,20);l10cmr.setBounds(100,320,100,20);l11ram.setBounds(100,350,100,20);l12os.setBounds(100,380,100,20);
	l13strg.setBounds(100,410,100,20);l14rs.setBounds(100,440,100,20);l15spt.setBounds(100,470,100,20);limei.setBounds(100,130,120,20);


	b2clr.setBounds(180,240,80,30);b3bk.setBounds(280,240,80,30);b4ad.setBounds(180,200,180,30);
	badd.setBounds(50,530,110,30);bclr.setBounds(200,530,110,30);bexit.setBounds(350,530,110,30);bimg.setBounds(150,480,110,30);
	l1btry.setBounds(120,165,100,20);t1btry.setBounds(250,165,150,20);

	b2clr.setMnemonic('C');	bclr.setMnemonic('C');	b3bk.setMnemonic('E');	bexit.setMnemonic('E');
	b4ad.setMnemonic('A');	badd.setMnemonic('A');	bimg.setMnemonic('I');

	vd(t1imei);	vd(timei);		v(tbtry);	v(t1btry);

	try
	{
	cn=DriverManager.getConnection("jdbc:mysql:///apple1","root","root");	
	stm=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	rs=stm.executeQuery("select distinct mno from iphone");
	while(rs.next())
	{
	cb4nn.addItem(rs.getString(1));
	}
	rs.close();	
	rs=stm.executeQuery("select distinct mno from ipad");
	while(rs.next())
	{
	cb2d.addItem(rs.getString(1));
	}
	rs.close();	
	}
	catch(Exception ex)
	{
	ex.printStackTrace();
	}
	cb1mdl.addItemListener(this);cb2d.addItemListener(this);cb3clr.addItemListener(this);
	cb4nn.addItemListener(this);cb5mdl2.addItemListener(this);
	b4ad.addActionListener(this);badd.addActionListener(this);
	rb1.addActionListener(this);rb2.addActionListener(this);
	bimg.addActionListener(this);b2clr.addActionListener(this);
	bclr.addActionListener(this);b3bk.addActionListener(this);bexit.addActionListener(this);
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
}
void vd(final JTextField tt)
{
	tt.addKeyListener(new KeyAdapter()
	{
		public void keyTyped(KeyEvent e)
		{
			if(t1imei.getText().length()<15 && e.getKeyChar()>='0' && e.getKeyChar()<='9' && timei.getText().length()<15 && e.getKeyChar()>='0' && e.getKeyChar()<='9')
			super.keyTyped(e);//Optional
			else
			{
				e.consume();				
				Toolkit tk=Toolkit.getDefaultToolkit();
				tk.beep();
			}
		}
	});
}
void v(final JTextField tt)
{
	tt.addKeyListener(new KeyAdapter()
	{
		public void keyTyped(KeyEvent e)
		{
			if((tbtry.getText().length()<16 && t1btry.getText().length()<16 && e.getKeyChar()>='0' && e.getKeyChar()<='9'))
			super.keyTyped(e);//Optional
			else
			{
				e.consume();				
				Toolkit tk=Toolkit.getDefaultToolkit();
				tk.beep();
			}
		}
	});
}
   void updateTableipad()
    {
       try
        {
        rs = stm.executeQuery("select count(*) from ipad");
        rs.first();
        int rowcnt = rs.getInt(1);
		//        JOptionPane.showMessageDialog(null, ""+rowcnt);
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
        p3.add(table);
        DefaultTableColumnModel colModel=(DefaultTableColumnModel)
		table.getColumnModel();
    	TableColumn col=colModel.getColumn(1);
    	col=colModel.getColumn(0);        col.setPreferredWidth(50);
        col=colModel.getColumn(1);        col.setPreferredWidth(100);
    	col=colModel.getColumn(2);        col.setPreferredWidth(100);
    	col=colModel.getColumn(3);        col.setPreferredWidth(60);
    	col=colModel.getColumn(4);        col.setPreferredWidth(60);
		table.setEnabled(false);
		table.setEnabled(false);
        JScrollPane jsp = new JScrollPane(table);
       p3.add(jsp);
        jsp.setBounds(5,5,555,300);
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
		//        JOptionPane.showMessageDialog(null, ""+rowcnt);

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
        p5.add(table);
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
        p5.add(jsp);
        jsp.setBounds(5,5,555,300);
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }
/*	private String getImageFile()
	{
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new ImageFilter());
	    int result = fc.showOpenDialog(null);
	    if (result == JFileChooser.APPROVE_OPTION)
	    {
	        file = fc.getSelectedFile();
	    	return file.getPath();
		}
		else
			return null;
	}
	private class PicturePanel extends JPanel
	{
		public void paint(Graphics g)
		{
			g.drawImage(img,0, 0,p4);
		}
	}
	private class ImageFilter extends javax.swing.filechooser.FileFilter
	{
	    public boolean accept(File f)
	    {
	        if (f.isDirectory())
	            return true;
	        String name = f.getName();
	        if (name.matches(".*((.jpg)|(.gif)|(.png))"))
	           return true;
	        else
	           return false;
	    }
	    public String getDescription()
	    {
	        return "Image files (*.jpg, *.gif, *.png)";
	    }
	}
*/
public void itemStateChanged(ItemEvent e)
{
updateTableipad();updateTableiphone();	
	try
    {
     if(cb1mdl.getSelectedIndex()==0 || cb5mdl2.getSelectedIndex()==0)
	{
	cb4nn.setEnabled(false);cb2d.setEnabled(false);p3.setVisible(false);p5.setVisible(false);
	t3dsp.setEditable(false);t4prssr.setEditable(false);t5cmr.setEditable(false);t6ram.setEditable(false);
	t7os.setEditable(false);t8strg.setEditable(false);t9rs.setEditable(false);t10spt.setEditable(false);
	}
    if(cb1mdl.getSelectedIndex()==1)
	{
	cb2d.setVisible(true);cb2d.setEnabled(true);cb4nn.setVisible(false);p3.setVisible(true);p5.setVisible(false);
	updateTableipad();
	}
   if(cb1mdl.getSelectedIndex() ==2)
	{
	cb2d.setVisible(false);cb4nn.setVisible(true);cb4nn.setEnabled(true);p5.setVisible(true);p3.setVisible(false);
	updateTableiphone();
	}
	if(cb4nn.getSelectedIndex()!=0)
	{
		rs=stm.executeQuery("select distinct price from iphone where	mno='"+cb4nn.getSelectedItem().toString()+"'");
		rs.next();
		t2prc.setText(rs.getString(1));
		rs.close();
	}
	else if(cb2d.getSelectedIndex()!=0)
	{
		rs=stm.executeQuery("select distinct price from ipad where mno='"+cb2d.getSelectedItem().toString()+"'");
		rs.next();
		t2prc.setText(rs.getString(1));
		rs.close();
	}
 	if(cb1mdl.getSelectedItem()=="2.Apple iPhone")
		{
		updateTableiphone();
		}
	if(cb1mdl.getSelectedItem()=="2.Apple iPad")
		{
		updateTableipad();
        }
		if(cb5mdl2.getSelectedIndex()!=0)
		{
		t3dsp.setEditable(true);	t4prssr.setEditable(true);	t5cmr.setEditable(true);	t6ram.setEditable(true);	
		t8strg.setEditable(true);	t9rs.setEditable(true);	t10spt.setEditable(true);	t7os.setEditable(true);
		t3dsp.setText("-inch");	t4prssr.setText("-GHz");	t5cmr.setText("-megapixel");	t6ram.setText("-GB");	t7os.setText("");
		t8strg.setText("-GB");	t9rs.setText("-pixels");	t10spt.setText("");
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
/*if(e.getSource()==bimg)
	{
		f1 = getImageFile();
		if (f1 != null)
		{
			Toolkit kit = Toolkit.getDefaultToolkit();
			img = kit.getImage(f1);
			img = img.getScaledInstance(500, -1, Image.SCALE_SMOOTH);
			this.repaint();
		}
		p4.setVisible(true);
	}*/
if(rb1.isSelected()==true)
	{
	p1.setVisible(true);	
	p2.setVisible(false);
	}
if(rb2.isSelected()==true)
	{
	p2.setVisible(true);
	p1.setVisible(false);
	}
if(e.getSource()==b2clr || e.getSource()==bclr) 
	{
	cb1mdl.setSelectedIndex(0);	cb2d.setSelectedIndex(0);	cb3clr.setSelectedIndex(0);
	cb4nn.setSelectedIndex(0);	cb5mdl2.setSelectedIndex(0);	cb6clr.setSelectedIndex(0);
	tbtry.setText("");	t1btry.setText("");
	//f1=null;
	t3dsp.setText("-inch");	t4prssr.setText("-GHz");	t5cmr.setText("-megapixel");	t6ram.setText("-GB");	t7os.setText("");
	t8strg.setText("-GB");	t9rs.setText("-pixels");	t10spt.setText("");	t1imei.setText("");	t2prc.setText("");
	tmdlno.setText("");	tprc.setText("");	tclr.setText("");	 timei.setText("");// p4.setVisible(false); bimg.setEnabled(true);
	}
if(e.getSource()==b4ad)
	{
	ipad=cb2d.getSelectedItem().toString();iphn=cb4nn.getSelectedItem().toString();clrd=cb3clr.getSelectedItem().toString();
	imei=t1imei.getText();amt=t2prc.getText();
	
   if((cb1mdl.getSelectedIndex()==1) && (cb2d.getSelectedIndex()!=0) && (!imei.equals(""))&&(cb3clr.getSelectedIndex()!=0)&& (!amt.equals("")) && (imei.length()==15)&&(t1btry.getText().length()==16))
	{
		sql="insert into ipad values('"+ipad+"','"+imei+"','"+t1btry.getText().toString()+"','"+amt+"',' "+clrd+"')";	
		prstm=cn.prepareStatement(sql);		prstm.execute();
		prstm.close();
		JOptionPane.showMessageDialog(null,"Record Added");
		updateTableipad();
		}
		else if((cb1mdl.getSelectedIndex()==2) && (cb4nn.getSelectedIndex()!=0) && (!imei.equals(""))&&(cb3clr.getSelectedIndex()!=0)&& (!amt.equals("")) && (imei.length()==15)&&(t1btry.getText().length()==16))
		{
		sql="insert into iphone values('"+iphn+"','"+imei+"','"+t1btry.getText().toString()+"','"+amt+"',' "+clrd+"')";	
		prstm=cn.prepareStatement(sql);		prstm.execute();
		prstm.close();
		JOptionPane.showMessageDialog(null,"Record Added");
		updateTableiphone();
		}
		else
		{
		JOptionPane.showMessageDialog(null,"Please check all Values !","ERROR!!!",JOptionPane.ERROR_MESSAGE);		
		}	
		}
if(e.getSource()==badd)
	{
	ipad=cb5mdl2.getSelectedItem().toString(); dsp=t3dsp.getText().toString();	prcr=t4prssr.getText().toString();
	cmr=t5cmr.getText().toString();	ram=t6ram.getText().toString();	os=t7os.getText().toString();
	strg=t8strg.getText().toString();	res=t9rs.getText().toString();	sprt=t10spt.getText().toString();bb=tbtry.getText().toString();
	iclr=cb6clr.getSelectedItem().toString();	no=tmdlno.getText();	p=tprc.getText();	im=timei.getText();
	 if((cb5mdl2.getSelectedIndex()==1) && (cb6clr.getSelectedIndex()!=0) && (!no.equals(""))&&(!p.equals("")) && (!im.equals(""))&& (!dsp.equals("-inch"))&& (!prcr.equals("-GHz"))&& (!cmr.equals("-megapixel"))&& (!ram.equals("-GB"))&& (!os.equals("-GB"))&& (!strg.equals("-GB"))&& (!res.equals("-pixels"))&& (!sprt.equals(""))&&(im.length()==15)&&(bb.length()==16))
		{
		sql="insert into ipad values('"+no+"','"+im+"','"+bb+"','"+p+"','"+iclr+"')";
//		path1=f1.replace("\\", "/");
		sql1="insert into ftr values('"+no+"','"+dsp+"','"+prcr+"','"+cmr+"','"+ram+"','"+os+"','"+strg+"','"+res+"','"+sprt+"')";	
		prstm=cn.prepareStatement(sql);			prstm.execute();
		prstm=cn.prepareStatement(sql1);		prstm.execute();
		prstm.close();
		JOptionPane.showMessageDialog(null,"Record Added");
		items();
	}
	else if((cb5mdl2.getSelectedIndex()==2) && (cb6clr.getSelectedIndex()!=0) && (!no.equals(""))&&(!p.equals("")) && (!im.equals(""))&& (!dsp.equals("-inch"))&& (!prcr.equals("-GHz"))&& (!cmr.equals("-megapixel"))&& (!ram.equals("-GB"))&& (!os.equals("-GB"))&& (!strg.equals("-GB"))&& (!res.equals("-pixels"))&& (!sprt.equals(""))&& (im.length()==15)&&(bb.length()==16))
	{
		sql="insert into ipad values('"+no+"','"+im+"','"+bb+"','"+p+"','"+iclr+"')";
//		path1=f1.replace("\\", "/");
		sql1="insert into ftr values('"+no+"','"+dsp+"','"+prcr+"','"+cmr+"','"+ram+"','"+os+"','"+strg+"','"+res+"','"+sprt+"')";	
		prstm=cn.prepareStatement(sql);			prstm.execute();
		prstm=cn.prepareStatement(sql1);		prstm.execute();
		prstm.close();
		JOptionPane.showMessageDialog(null,"Record Added");	
		items();
	}
	else
		{
		JOptionPane.showMessageDialog(null,"Please check all Values !","ERROR!!!",JOptionPane.ERROR_MESSAGE);		
		}
		}
if(e.getSource()==bexit || e.getSource()==b3bk)
	{
	dispose();
	}
	}
	catch(Exception ex)
	{
	System.out.print(ex);
	}
} 
    void items()
    {
        cb4nn.removeItemListener(this);
        cb2d.removeItemListener(this);
            cb4nn.removeAllItems() ;
	       cb2d.removeAllItems() ;
        try
        {
			rs=stm.executeQuery("select distinct mno from iphone");
			cb4nn.addItem("Select iPhone Model");
			while(rs.next())
			{
			cb4nn.addItem(rs.getString(1));
			}
			rs.close();	
		rs=stm.executeQuery("select distinct mno from ipad");
    	cb2d.addItem("Select iPad Model");
		while(rs.next())
		{
		cb2d.addItem(rs.getString(1));
		}
	rs.close();	
		}
        catch(Exception e)
        {
            e.printStackTrace();
        }
            cb2d.addItemListener(this);
            cb4nn.addItemListener(this);
}

public static void main(String args[])
{
	new addstock();
}
}	
