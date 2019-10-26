import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Stack;
import javax.swing.*;

public class myCalculator3_1{
	
	JFrame frame = new JFrame ("Calculator v3.1");
	JPanel pan1 = new JPanel();
	JPanel pan2 = new JPanel();
	
	String input = "";//result						
	String operator = "";//operator
	
	int k = 1;
	
	JTextField screen = new JTextField(15);
	JButton bt_clear = new JButton("C");

	public myCalculator3_1(){
		pan1.setLayout(new FlowLayout(FlowLayout.LEFT));
		pan1.add(screen);
		pan1.add(bt_clear);
		
		pan2.setPreferredSize(new Dimension(1000,1000));
		pan2.setLayout(new GridLayout(4,4,10,10));

		class Listener implements ActionListener{
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e){
				int cnt=0;
				String actionCommand = e.getActionCommand();			
				if(actionCommand.equals("+") || actionCommand.equals("-") || actionCommand.equals("*")
						|| actionCommand.equals("/")) {
					input += " " + actionCommand + " ";
				}
				else if(actionCommand.equals("C")) {					
					input = "";
				} 
				else if(actionCommand.equals("=")) {					
					input+= "="+cal(input);
					screen.setText(input);
					input="";
					cnt = 1;
				}
				else
					input += actionCommand;
				
				if(cnt == 0)
					screen.setText(input);							
			}
		}
		
		Listener listener = new Listener();
		Font f = new Font("Ariel",0,36);
		
		String[] name= {"7","8","9","+","4","5","6","-","1","2","3","*",".","0","=","/"};
				
		for(int i=0;i<name.length;i++) {
			JButton button = new JButton(name[i]);
			button.addActionListener(listener);
			button.setFont(f);
			pan2.add(button);
		}
		
		screen.setFont(f);
		screen.setPreferredSize(new Dimension(600,180));
		screen.setEditable(false);
		
		bt_clear.setFont(f);	
		bt_clear.setPreferredSize(new Dimension(450,180));		
		bt_clear.addActionListener(listener);	
		
		frame.add(pan1,"North");
		frame.add(pan2,"Center");
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		

		
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
			    System.exit(0);	
			}		
		});
		
	}
	
	public String cal(String input){
		try{
			String[] comput = input.split(" ");					
			Stack<Double> stack = new Stack<>();
			Double m = Double.parseDouble(comput[0]);
			stack.push(m);										
			//push the first operation digit into the stack
			
			for(int i = 1; i < comput.length; i++) {
				if(i%2==1) {				
					if(comput[i].equals("+"))
						stack.push(Double.parseDouble(comput[i+1]));
					if(comput[i].equals("-"))
						stack.push(-Double.parseDouble(comput[i+1]));
					if(comput[i].equals("*")) {	
					//pop the previous number and do multiplication
					//push the result into the stack
						Double d1 = stack.peek();				
						stack.pop();
						Double d2 = Double.parseDouble(comput[i+1]);
						BigDecimal b1 = new BigDecimal(Double.toString(d1));
						BigDecimal b2 = new BigDecimal(Double.toString(d2));
						Double res = b1.multiply(b2).doubleValue();
						stack.push(res);
						// stack.push(d*Double.parseDouble(comput[i+1]));
					}
					if(comput[i].equals("/")) {	
					//pop the previous number and do division
					//push the result into the stack				
						 double help = Double.parseDouble(comput[i+1]);  
						 if(help == 0)
							 stack.push(help);
							 // throw new MyException("Infinity");					 
						 //if throwing exception
						 //do not continue again
						 double d = stack.peek(); 
						 stack.pop(); 
						 stack.push(d/help);  
					}
				}
				
			}
			
			double d = 0d;
			
			while(!stack.isEmpty()) {			
				d += stack.peek();
				stack.pop();
			}
			
			String result = String.valueOf(d);
			return result;
		} catch (Exception e){
			return "ERROR !";
		}		
	}

	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		myCalculator3_1 cc = new myCalculator3_1();
	}
	
}