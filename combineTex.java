import java.io.*;
import java.util.HashSet;
 
public class combineTex{
   public static void main(String args[])throws IOException{
       
 	    String folderName = "newTab/";
		String outputFileName = "allTabs.tex";
		
		HashSet<String> set = new HashSet<String>();
        File folder = new File(folderName);
	    Writer out = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(outputFileName,true), "UTF-8"));					
		out.write("\\documentclass{article}\n");
		out.write("\\usepackage[margin=0.1in]{geometry}\n");
		
		String s[] = folder.list();
		for (int i = 0; i < s.length; i++) {
			
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(folderName + "/" + s[i]),"UTF-8"));
			
			System.out.println(folderName + s[i]);
			String line = null;
			while ((line = br.readLine()) != null) {
				String u = line;
				if (u.length()>=11 && 
				u.substring(0,11).equals("\\usepackage") &&
				!set.contains(u)){
				out.write(u + "\n");
				set.add(u);
				System.out.println("Find Package -- "+u);
				}						  
			}
            br.close();
		}
		out.write("\\begin{document}\n");
		
		for (int i = 0; i < s.length; i++) {
			
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(folderName + "/" + s[i]),"UTF-8"));
			
			System.out.println("Copying " +folderName + "/" + s[i]);
			String line = null;
			while ((line = br.readLine()) != null) {
				String u = line;
				if (u.length()>=11 && u.substring(0,11).equals("\\usepackage")){
				//do nothing
				} else if (u.length()>=14 && u.substring(0,14).equals("\\documentclass")){ 
				//do nothing
			    } else if (u.length()>=13 && u.substring(0,13).equals("\\begin{table}")){
					out.write("\\begin{table}[htbp]\\centering\\tiny\n");
			    } else if (		
				!u.equals("\\begin{document}") &&
				!u.equals("\\end{document}")){
					out.write(u + "\n");
				}
			    else if (u.equals("\\end{document}"))
					out.write("\n\n");							  
			}
            br.close();
		}
		out.write("\\end{document}");
		
		out.close();
   }
}
