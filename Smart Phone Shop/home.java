import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
class home extends JFrame implements ActionListener
{
JFrame f;
ImagePanel_HP p;
JPanel p2;
JButton bshop,bclose;

home()
 {
 f=new JFrame("***** Home Page *****");

 String img_name="Images/a.jpg";
 p=new ImagePanel_HP(img_name);

 p2=new JPanel();
 p2.setLayout(new FlowLayout());
 p2.setBackground(Color.white);

 bshop=new JButton("Phone-Shop");
 bclose=new JButton("Close");
 
 bshop.addActionListener(this);
 bclose.addActionListener(this);

 p2.add(bshop);
 p2.add(bclose);

 f.add(p);
 f.add(p2,BorderLayout.SOUTH);
 f.setSize(800,550);
 f.setVisible(true);
 f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 f.setResizable(false);
 f.setLocation(250,50);
 v(bclose);
 v(bshop);
 }
void v(final JButton tt)
{
	tt.addKeyListener(new KeyAdapter()
	{
		public void keyTyped(KeyEvent e)
		{
 if(e.getSource()==bshop)
  {
  	new loginuser();
  f.dispose();
  }
 if(e.getSource()==bclose)
  {
	f.dispose();
  } 
	}
	});
}
public void actionPerformed(ActionEvent ae)
 {
 if(ae.getSource()==bshop)
  {
  	new loginuser();
  f.dispose();
  }
 if(ae.getSource()==bclose)
  {
	f.dispose();
  } 
 }
public static void main(String args[])
 {
 new home();
 }
}
class ImagePanel_HP extends JPanel
{
Image img=null;
ImagePanel_HP(String name)
 {
 try
  {
  img=ImageIO.read(new File(name));
  }
 catch(IOException e)
  {
  }
 }
public void paintComponent(Graphics g)
 {
 g.drawImage(img,0,0,null);
 }
}
