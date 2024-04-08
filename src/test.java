import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class test {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		//parsing a CSV file into Scanner class constructor  
		Scanner sc = new Scanner(new File("D:\\ACF\\apache-tomcat-8.5.23\\bin\\CR\\data.csv"));  
		sc.useDelimiter(",");   //sets the delimiter pattern  
		while (sc.hasNextLine())  //returns a boolean value  
		{  
			String line = sc.nextLine();
			if(line.toUpperCase().contains("ABHIJIT")|| line.toUpperCase().contains("ABHIJEET")){
				System.out.println(line);  //find and returns the next complete token from this scanner  
				
			}
		}   
		sc.close();  //closes the scanner  
	}

}
