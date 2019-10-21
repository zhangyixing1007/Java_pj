import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Vector;
import javax.swing.*;

public class myCalculator2{
	
	JFrame frame = new JFrame ("Calculator v2.0");
	JPanel pan1 = new JPanel();
	JPanel pan2 = new JPanel();
	
	String str1 = "0", str2 = "0", signal = "+", result = "0";
	int k1 = 1, k2 = 1, k3 = 1, k4 = 1, k5 = 1;
	
	JTextField screen = new JTextField(result,12);
	JButton bt_clear = new JButton("CL");
	JButton bt_0 = new JButton("0");
	JButton bt_1 = new JButton("1");
	JButton bt_2 = new JButton("2");
	JButton bt_3 = new JButton("3");
	JButton bt_4 = new JButton("4");
	JButton bt_5 = new JButton("5");
	JButton bt_6 = new JButton("6");
	JButton bt_7 = new JButton("7");
	JButton bt_8 = new JButton("8");
	JButton bt_9 = new JButton("9");
	JButton bt_add = new JButton("+");
	JButton bt_sub = new JButton("-");
	JButton bt_mul = new JButton("*");
	JButton bt_div = new JButton("/");
	JButton bt_pt = new JButton(".");
	JButton bt_eq = new JButton("=");
	
	Vector vt = new Vector(20,10);
	JButton store;
	
	public myCalculator2(){
		pan1.setLayout(new FlowLayout(FlowLayout.LEFT));
		pan1.add(screen);
		pan1.add(bt_clear);
		
		screen.setPreferredSize(new Dimension(600,180));
		bt_clear.setPreferredSize(new Dimension(450,180));
		
		pan2.setPreferredSize(new Dimension(1000,1000));
		pan2.setLayout(new GridLayout(4,4,10,10));
		pan2.add(bt_7);pan2.add(bt_8);pan2.add(bt_9);pan2.add(bt_add);
		pan2.add(bt_4);pan2.add(bt_5);pan2.add(bt_6);pan2.add(bt_sub);
		pan2.add(bt_1);pan2.add(bt_2);pan2.add(bt_3);pan2.add(bt_mul);
		pan2.add(bt_pt);pan2.add(bt_0);pan2.add(bt_eq);pan2.add(bt_div);
		
		frame.add(pan1,"North");
		frame.add(pan2,"Center");
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		Font f = new Font("Ariel",0,36);
		
		screen.setFont(f);
		bt_clear.setFont(f);
		bt_0.setFont(f);
		bt_1.setFont(f);
		bt_2.setFont(f);
		bt_3.setFont(f);
		bt_4.setFont(f);
		bt_5.setFont(f);
		bt_6.setFont(f);
		bt_7.setFont(f);
		bt_8.setFont(f);
		bt_9.setFont(f);
		bt_add.setFont(f);	
		bt_sub.setFont(f);
		bt_mul.setFont(f);
		bt_div.setFont(f);
		bt_pt.setFont(f);
		bt_eq.setFont(f);	
		
		class Listener implements ActionListener{
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e){
				store = (JButton) e.getSource();
				vt.add(store);
				String s = store.getText();
				if (k1 == 1){
					if (k3 == 1) {str1 = ""; k5 = 1;}
					str1 = str1 + s; k3++;
					screen.setText(str1);
				}else{
					if (k4 == 1) {str2 = ""; k5 = 1;}
					str2 = str2 + s; k4++;
					screen.setText(str2);					
				}				
			}
		}
		
		class Listener_op implements ActionListener{
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e){
				store =  (JButton) e.getSource();
				vt.add(store);
				String s = store.getText();

				if (k2!=1){
					int sz = vt.size();
					String ss = ((JButton)vt.get(sz-2)).getText();	
					if (!ss.equals("+")&&!ss.equals("-")&&
					!ss.equals("*")&&!ss.equals("/")){
						cal();
						str1 = result;str2 = "0";
						k1 = 2; k2 = 1; k3 = 1; k4 = 1; k5 = 1;
					}
					
				}
				else {k1++;k2++;}
				signal = s;
			}
		}		

		class Listener_pt implements ActionListener{
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e){
				if (k5==1){
					store =  (JButton) e.getSource();
					vt.add(store);
					String s = store.getText();					
					if (k1 == 1){ 
						str1 = str1 + s; screen.setText(str1);	
					}else{
						str2 = str2 + s; screen.setText(str2);	
					}
					k5++;
				}
			}
		}	

		class Listener_eq implements ActionListener{
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e){
				cal();
				str1 = result;str2 = "0";signal = "+";
				k1 = 1; k2 = 1; k3 = 1; k4 = 1; k5 = 1;	
				screen.setText(str1);
			}
		}	

		class Listener_cl implements ActionListener{
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e){
				str1 = "0"; str2 = "0"; signal = "+"; result = "0";
				k1 = 1; k2 = 1; k3 = 1; k4 = 1; k5 = 1;
				vt.clear();
				screen.setText(result);
			}
		}		

		Listener listener = new Listener();
		Listener_cl listener_cl = new Listener_cl();
		Listener_eq listener_eq = new Listener_eq();
		Listener_op listener_op = new Listener_op();
		Listener_pt listener_pt = new Listener_pt();
		
		bt_clear.addActionListener(listener_cl);
		bt_0.addActionListener(listener);
		bt_1.addActionListener(listener);
		bt_2.addActionListener(listener);
		bt_3.addActionListener(listener);
		bt_4.addActionListener(listener);
		bt_5.addActionListener(listener);
		bt_6.addActionListener(listener);
		bt_7.addActionListener(listener);
		bt_8.addActionListener(listener);
		bt_9.addActionListener(listener);	
		bt_add.addActionListener(listener_op);		
		bt_sub.addActionListener(listener_op);	
		bt_mul.addActionListener(listener_op);	
		bt_div.addActionListener(listener_op);
		bt_pt.addActionListener(listener_pt);
		bt_eq.addActionListener(listener_eq);
		
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
			    System.exit(0);	
			}		
		});
		
	}
	
	public void cal(){
		double num1, num2, ans = 0;
		if (str1.equals(".")) str1 = "0.0";
		if (str2.equals(".")) str2 = "0.0";
		
		num1 = Double.valueOf(str1).doubleValue();
		num2 = Double.valueOf(str2).doubleValue();
		
		if (signal.equals("+")){ ans = num1+num2;
		}else if(signal.equals("-")){ ans = num1 - num2;
		}else if(signal.equals("*")){
			BigDecimal b1 = new BigDecimal(Double.toString(num1));
			BigDecimal b2 = new BigDecimal(Double.toString(num2));
			ans = b1.multiply(b2).doubleValue();
		}else if(signal.equals("/")){
			if (num2 == 0) ans = 0;
			else ans = num1/num2;
		}
		result = Double.toString(ans);
	}

	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		myCalculator2 cc = new myCalculator2();
	}
	
	

}
