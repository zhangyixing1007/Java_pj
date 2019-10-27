import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Stack;
import javax.swing.*;

public class myCalculator3_3{
	
	JFrame frame = new JFrame ("Calculator v3.3");
	JPanel pan1 = new JPanel();
	JPanel pan2 = new JPanel();
	
	String input = "";//result						
	String operator = "";//operator
	boolean afterEq = false;
	
	int k = 1;
	int countBracket = 0;
	
	JTextField screen = new JTextField(25);

	public myCalculator3_3(){

		class Listener implements ActionListener{
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e){
				int cnt=0;
				String actionCommand = e.getActionCommand();			
				if(actionCommand.equals("+") || actionCommand.equals("-") || actionCommand.equals("*")
						|| actionCommand.equals("/")) {							
				    afterEq = false;			
					operator = new String(actionCommand);
				    k = 1;	
					input = input.trim();
					int len = input.length();
					String last = input.substring(len-1,len);
					if (last.equals("+") || last.equals("-") ||
						last.equals("*") || last.equals("/")){
							input = input.substring(0,len-1).trim();
						}
					input += " " + operator + " ";	
				} else if(actionCommand.equals("C")) {					
					input = "";
					k = 1;
				} else if(actionCommand.equals("D")){
					if (afterEq) {
						input = "";
						afterEq = false;
					}
					input = input.trim();
					int len = 0;
					if ((len = input.length())!=0){
						input = input.substring(0,len-1).trim();
					}
				} else if (actionCommand.equals("(")){
					countBracket++;
					if(afterEq){
						input = "";
						afterEq = false;
					}														
					input = input.trim();
					input += " " + actionCommand + " ";
				} else if (actionCommand.equals(")")){
					countBracket--;
					input = input.trim();
					input += " " + actionCommand + " ";					
				}else if(actionCommand.equals("=")) {
					afterEq = true;
					if (countBracket!=0) {
						input = "Brackets not match!";
						screen.setText(input);
						countBracket = 0;
						return;
					}			
					String result = cal(input);
					input+= " = " + result;
					screen.setText(input);
					input=result;
					k = 1;
					cnt = 1;						
				} else if(actionCommand.equals(".")){
					if (k==1){
						input += actionCommand;
						k++;
					}
				} else{			
					if(afterEq){
						input = "";
						afterEq = false;
					}		
					input += actionCommand;
				}
				
				if(cnt == 0)
					screen.setText(input);							
			}
		}
		
		pan1.setLayout(new BorderLayout(800,200));
		pan1.add("Center",screen);
		
		pan2.setPreferredSize(new Dimension(800,1000));
		pan2.setLayout(new GridLayout(5,4,10,10));		
		Listener listener = new Listener();
		Font f = new Font("Ariel",0,36);
		
		String[] name= {"7","8","9","+",
						"4","5","6","-",
						"1","2","3","*",
						".","0","=","/",
						"(",")","D","C"};
				
		for(int i=0;i<name.length;i++) {
			JButton button = new JButton(name[i]);
			button.addActionListener(listener);
			button.setFont(f);
			pan2.add(button);
		}
		
		screen.setFont(f);
		screen.setPreferredSize(new Dimension(600,180));
		screen.setEditable(false);
		
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
			input = input.trim();
			String[] comput = input.split(" ");					
			Stack<String> stack = new Stack<>();
			
			for(int i = 0; i < comput.length; i++) {
				String curr = comput[i];				
				if (curr.equals("(")){
					stack.push(curr);
					countBracket ++;
				} else if (curr.equals(")")){
					countBracket --;
					String tmp = "";
					while (!stack.peek().equals("(")){
						tmp = stack.pop()+" "+tmp;
					}
					tmp = tmp.trim();
					stack.pop();//remove"("
					String result = calculate(tmp);
					stack.push(result);
				} else {//digits
					stack.push(curr);
				}
				
			}
			
			String tmp = "";
			while(!stack.isEmpty()) {	
				tmp = stack.pop()+" "+tmp;
			}
			tmp = tmp.trim();
			
			String result = calculate(tmp);
			return result;
			
		} catch (Exception e){
			return "cal ER !";
		}		
	}
	
	private String calculate(String input){	
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
					}
					if(comput[i].equals("/")) {	
					//pop the previous number and do division
					//push the result into the stack				
						 double help = Double.parseDouble(comput[i+1]);  
						 if(help == 0)
							 throw new MyException("Infinity");			
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
		} catch(Exception e){
			return "calculate ER !";
		}
	}	
	
	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		myCalculator3_3 cc = new myCalculator3_3();
	}
	
}