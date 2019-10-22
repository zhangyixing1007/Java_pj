import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class Editor2 extends JFrame implements Runnable, ActionListener{
	
	Thread compiler;
	Thread runner;
	
	File file_saved;
	
	JPanel pan1 = new JPanel();
	JPanel pan2 = new JPanel();
	
	CardLayout card = new CardLayout();
	
	JButton bt_code = new JButton("CODE");
	JButton bt_compile_result = new JButton("compile RESULT");
	JButton bt_run_result = new JButton("run RESULT");
	JButton bt_compile = new JButton("COMPILE");
	JButton bt_run = new JButton("RUN");
	
	JTextField file_name = new JTextField();
	
	JTextArea code_page = new JTextArea();
	JTextArea compile_page = new JTextArea();
	JTextArea run_page = new JTextArea();
	
	
	public Editor2(){
		super("Java Editor (Version 2.0)");
		compiler = new Thread(this);
		runner = new Thread(this);
		
		pan1.setLayout(new GridLayout(2,3));
		pan1.add(bt_code);
		pan1.add(bt_compile_result);
		pan1.add(bt_run_result);
		pan1.add(file_name);
		pan1.add(bt_compile);
		pan1.add(bt_run);
		add(pan1,"North");
		
		pan2.setLayout(card);
		pan2.add("d_page", code_page);
		pan2.add("c_page", compile_page);
		pan2.add("r_page", run_page);
		add(pan2,"Center");
		pan2.setPreferredSize(new Dimension(1000,1000));
		
		Font f = new Font("Ariel", 0, 32);
		bt_code.setFont(f);
		bt_compile_result.setFont(f);
		bt_run_result.setFont(f);
		bt_compile.setFont(f);
		bt_run.setFont(f);
		file_name.setFont(f);
		code_page.setFont(f);
		compile_page.setFont(f);
		run_page.setFont(f);
		
		compile_page.setBackground(Color.pink);
		run_page.setBackground(Color.orange);
		
		bt_code.addActionListener(this);
		bt_compile_result.addActionListener(this);
		bt_run_result.addActionListener(this);
		bt_compile.addActionListener(this);
		bt_run.addActionListener(this);

		
	}

	public void actionPerformed(ActionEvent e){
		if (e.getSource() == bt_code){
			card.show(pan2,"d_page");
		} else if (e.getSource() == bt_compile_result){
			card.show(pan2,"c_page");
		} else if (e.getSource() == bt_run_result){
			card.show(pan2,"r_page");
		} else if (e.getSource() == bt_compile){
			if (!compiler.isAlive()){
				compiler = new Thread(this);
			}
			compiler.start();
			card.show(pan2,"c_page");
		} else if (e.getSource() == bt_run){
			if (!runner.isAlive()){
				runner = new Thread(this);
			}	
			runner.start();
			card.show(pan2,"r_page");
		}
		
	}
		
	@Override
	public void run(){
		if(Thread.currentThread() == compiler){
			compile_page.setText(null);			
			file_saved = new File(file_name.getText().trim()+".java");
			
			try{
				String tmp = code_page.getText().trim();
				byte[] buffer = tmp.getBytes();
				FileOutputStream writefile = new FileOutputStream(file_saved);
				writefile.write(buffer,0,buffer.length);
				writefile.close();
				
				Runtime runtime = Runtime.getRuntime();
				InputStream in = runtime.exec("javac "+file_name.getText().trim()+".java").getErrorStream();
				BufferedInputStream bfIn = new BufferedInputStream(in);
				
				boolean flag = true;
				@SuppressWarnings("unused")
				int n = 0;
				byte[] b = new byte[100];
				
				while ((n = bfIn.read(b,0,100))!=-1){
					String s = null;
					s = new String(b,0,n);
					if (s!=null) {
						flag = false;
						compile_page.append(s);
					}
				}
				
				if (flag){
					compile_page.append("Compile succeed!");
				}
				
			} catch(Exception e2){
				System.out.println("Error during COMPILE ");
			}			
			
		} else if (Thread.currentThread() == runner){
			run_page.setText(null);
			
			file_saved = new File(file_name.getText().trim()+".java");
			
			try{
				Runtime runtime = Runtime.getRuntime();
				Process process = runtime.exec("java "+file_name.getText().trim());
				InputStream in1 = process.getInputStream();
				InputStream in2 = process.getErrorStream();
				
				BufferedInputStream bfIn1 = new BufferedInputStream(in1);
				BufferedInputStream bfIn2 = new BufferedInputStream(in2);
				
				@SuppressWarnings("unused")
				int m = 0;
				@SuppressWarnings("unused")
				int n = 0;			
				
				byte[] b = new byte[100];
				
				while ((m = bfIn1.read(b,0,100))!=-1){
					String s = null;
					s = new String(b,0,m);
					if (s!=null) {
						run_page.append(s);
					}
				}			
				while ((n = bfIn2.read(b,0,100))!=-1){
					String s = null;
					s = new String(b,0,n);
					if (s!=null) {
						run_page.append(s);
					}
				}
			} catch(Exception e2){
				System.out.println("Error during RUN ");
			}				
		}
	}
	
	public static void main(String[] args){
		Editor2 editor = new Editor2();
		editor.pack();
		editor.setLocationRelativeTo(null);
		editor.setVisible(true);
		editor.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{System.exit(0);}
		});		
	}
	
	
}
