package com.rec;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Random;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Hello world!
 *
 */
public class CreateReceipt 
{
	public static final String IMAGE = System.getProperty("user.dir")+"\\CR\\Blank.jpg";
	
    //public static final String DEST = "C:\\Users\\admin\\Desktop\\ACFReceipts\\background_image.pdf";
    public static Properties p=new Properties();  
    public static String FONTFILE = "";
    public static String CSVPATH="";
    public static String PDFFILEPATH="";
    public static String[] donationTowards = null;
    public static int srNo = 0;
    
    static{
    	try {
    		System.out.println("Checking at this location :"+IMAGE);
	    	FileReader reader=new FileReader(System.getProperty("user.dir")+"\\CR\\data.properties");  
	    	p.load(reader);
	    	String dt = p.getProperty("donationTowards");
	    	donationTowards = dt.split(",");
	    	srNo = Integer.parseInt(p.getProperty("serialNumber"));
	    	
	    	FONTFILE= System.getProperty("user.dir")+"\\CR\\"+p.getProperty("FONTFILENAME");
	    	System.out.println("Font file : "+FONTFILE);
	    	CSVPATH= System.getProperty("user.dir")+"\\CR\\data.csv";
	    	System.out.println("Data file : "+CSVPATH);
	    	PDFFILEPATH= System.getProperty("user.dir")+"\\CR\\Receipts";
	    	System.out.println("receipt files path : "+PDFFILEPATH);
	    	PDFFILEPATH= createTodaysDateFolder();
	    	System.out.println("todays date folder created : "+PDFFILEPATH);
	    	
			
		} catch (Exception e) {
			System.out.println("Expected file at this location: "+System.getProperty("user.dir")+"\\CR\\data.properties");
			e.printStackTrace();
		}  
    }
    
    public static void main( String[] args ) throws IOException, DocumentException
    {
    	
    	ReceiptDTO dto = new ReceiptDTO();
    	dto.setAmount("5000");
    	dto.setAmountInwords("Five Thousands");
    	dto.setDateOfDonation("05/06/2019");
    	dto.setDonationReceivedBy("Niranjan Aher");
    	dto.setDonerName("Rajnath Singh");
    	dto.setModeOfDonation("Cheque");
    	new CreateReceipt().createPdf(dto);
    	
    	
    	
    	System.out.println(p.get("SecretoryName"));

    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(new Date());
    	System.out.println(calendar.get(Calendar.DATE));
    	System.out.println(calendar.get(Calendar.MONTH));
    	System.out.println(calendar.get(Calendar.YEAR));
    	
    	
    	//File file = new File(DEST);
        //file.getParentFile().mkdirs();
        //new CreateReceipt().createPdf(DEST);
    }
    
    public static String getMonth(int month){
        String[] monthNames = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        return monthNames[month];
    }
    
    public static String createPdf(ReceiptDTO dto) throws IOException, DocumentException {

    	String doationRefferedFolderPath = getFilePath(dto);
    	
    	Date d = new Date();
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(d);
    	BaseFont bf = BaseFont.createFont(FONTFILE, "CP1251", BaseFont.EMBEDDED);
    	Random rand = new Random();
    	srNo = srNo+1;
    	String receiptNum = p.get("receiptExt")+""+String.format("%05d", srNo)+"";
    	dto.setReceiptNumber(receiptNum);
    	
        Document document = new Document(PageSize.A4.rotate());
        Rectangle one = new Rectangle(2416,1168);
        document.setPageSize(one);
        
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(doationRefferedFolderPath+"\\"+dto.getDonerName()+"_"+receiptNum+".pdf"));
        document.open();
        //document.add(new Paragraph("Berlin!"));
        PdfContentByte canvas = writer.getDirectContentUnder();
        Image image = Image.getInstance(IMAGE);
        image.scaleAbsolute(one);
        image.setAbsolutePosition(0, 0);
        
        canvas.addImage(image);
        
        
        //System.out.println("ACF-"+rand.nextInt());
        FixText(bf, receiptNum, 330, 650, writer, 20);  // Receipt number
        
        
        
        
        FixText(bf, String.format("%02d", calendar.get(Calendar.DATE)), 2063, 650, writer, 14);	//Date: day
        FixText(bf, getMonth(calendar.get(Calendar.MONTH)), 2126, 650, writer, 14);	// Date: month
        FixText(bf, new GregorianCalendar().get(Calendar.YEAR)+"", 2193, 650, writer, 14);	//Date: Year
        dto.setRctGenerationDate(d.getDay()+"/"+d.getMonth()+"/"+new GregorianCalendar().get(Calendar.YEAR));
        
        FixText(bf, dto.getDonerName(), 900, 555, writer, 32); // Name of the doner
        FixText(bf, dto.getAmount()+"/-", 700, 455, writer, 14);  // Amount paid
        
        int n = Integer.parseInt(dto.getAmount());
        String inWord = "";
        inWord += pw((n/1000000000)," Hundred");
        inWord += pw((n/10000000)%100," Crore");
        inWord += pw(((n/100000)%100)," Lakh");
        inWord += pw(((n/1000)%100)," Thousand");
        inWord += pw(((n/100)%10)," Hundred");
        inWord += pw((n%100),"");
        
        FixText(bf, inWord +" Only", 1300, 455, writer, 14); // Amount paid in words
        FixText(bf, dto.getModeOfDonation()+" Details: ["+dto.getOtherFinDetails()+"]", 820, 355, writer, 14); 					// mode of transfer
        
        if(!dto.getDateOfDonation().equals(""))
        try {
        	FixText(bf, dto.getDateOfDonation().split("-")[0]+"/", 340, 255, writer, 14);	//Date: day
            FixText(bf, dto.getDateOfDonation().split("-")[1]+"/", 400, 255, writer, 14);	// Date: month
            FixText(bf, dto.getDateOfDonation().split("-")[2], 460, 255, writer, 14);	//Date: Year
        }
        catch(Exception ex) {
        	ex.printStackTrace();
        	System.out.println(dto.getDateOfDonation()+" Date of donation is incorrect. Please send it in DD-MM-YYYY format");
        	//System.exit(0);
        }
        
        FixText(bf, dto.getDonationTowards()+"", 1200, 255, writer, 14);	//Donation towards
        FixText(bf, p.get("presidentName")+"", 370, 155, writer, 14);			//President
        FixText(bf, p.get("SecretoryName")+"", 820, 155, writer, 14);		//Secretory
        FixText(bf, p.get("tresures")+"", 1330, 155, writer, 14);			//Tresurer
        FixText(bf, dto.getDonationReceivedBy()+"", 1763, 155, writer, 14);		//Received by
        
        appendCSV(dto);
        document.close();
        FileOutputStream output = new FileOutputStream(System.getProperty("user.dir")+"\\CR\\data.properties");
        p.setProperty("serialNumber", srNo+"");
        p.store(output, "This description goes to the header of a file");
        output.close();
        
        return receiptNum;
    }
    
    private static String getFilePath(ReceiptDTO dto) throws IOException {
		
    	String receivedByPeronName = dto.getDonationReceivedBy();
    	if(receivedByPeronName!=null)
    	{
    		receivedByPeronName= receivedByPeronName.trim();
    		String pathStr = PDFFILEPATH+"\\"+receivedByPeronName;
    		Path path=Paths.get(pathStr);
    		if (!Files.exists(path)) {
                
                Files.createDirectory(path);
                System.out.println("Directory created for "+receivedByPeronName);
                return pathStr;
            } else {
                
                //System.out.println("Directory already exists");
                return pathStr;
            }
    	
    	}
		return PDFFILEPATH;
	}
    
private static String createTodaysDateFolder() throws IOException {
		
	
	Date date = Calendar.getInstance().getTime();
	DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
	String strDate = dateFormat.format(date);
	
	String pathStr = "";
	
    	if(strDate!=null)
    	{
    		pathStr = PDFFILEPATH+"\\"+strDate;
    		Path path=Paths.get(pathStr);
    		if (!Files.exists(path)) {
                
                Files.createDirectory(path);
                System.out.println("Directory created for "+strDate);
                //return pathStr;
            }
    	
    	}
		return pathStr;
	}

	public void createPdf(String dest) throws IOException, DocumentException {
    	
    	BaseFont bf = BaseFont.createFont(p.getProperty("FONTFILE"), "CP1251", BaseFont.EMBEDDED);
        Document document = new Document();
        Rectangle one = new Rectangle(2416,1168);
        document.setPageSize(one);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        //document.add(new Paragraph("Berlin!"));
        PdfContentByte canvas = writer.getDirectContentUnder();
        Image image = Image.getInstance(IMAGE);
        image.scaleAbsolute(one);
        image.setAbsolutePosition(0, 0);
        
       
        canvas.addImage(image);
        FixText(bf, "Receipt Num1", 330, 650, writer, 20);  // Receipt number
        FixText(bf, "07", 2063, 650, writer, 14);	//Date: day
        FixText(bf, "06", 2126, 650, writer, 14);	// Date: month
        FixText(bf, "2019", 2193, 650, writer, 14);	//Date: Year
        FixText(bf, "Abhijit Kumbhar", 900, 555, writer, 32); // Name of the doner
        FixText(bf, "5000"+"/-", 700, 455, writer, 14);  // Amount paid
        FixText(bf, "Five Thousands"+" Only", 1300, 455, writer, 14);  // Amount paid in words
        FixText(bf, "By Cash", 820, 355, writer, 14); 				// mode of transfer
        FixText(bf, "07"+"/", 340, 255, writer, 14);	//Date: day
        FixText(bf, "06"+"/", 395, 255, writer, 14);	// Date: month
        FixText(bf, "2019", 450, 255, writer, 14);	//Date: Year
        FixText(bf, "Alert Citizen Forum", 1200, 255, writer, 14);	//Donation towards
        FixText(bf, "Niranjan Aher", 370, 155, writer, 14);		//President
        FixText(bf, "Niranjan Aher1", 820, 155, writer, 14);		//Secretory
        FixText(bf, "Niranjan Aher2", 1380, 155, writer, 14);		//Tresurer
        FixText(bf, "Niranjan Aher3", 1763, 155, writer, 14);		//Received by
        
        
        document.close();
    }
    
    private static void FixText(BaseFont bf, String text, int x, int y,PdfWriter writer,int size) {
        try {
        	size=38;
            PdfContentByte cb = writer.getDirectContent();
            
            

            
            
            
            //Font m_font = new Font(bf, size, 3);
                    
            //bf.TIMES_ITALIC
            //bf.TIMES_BOLD
            
            //BaseFont bf = BaseFont.createFont(p.getProperty("FONTFILE"),BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, bytesArray, null);
            cb.saveState();
            cb.beginText();
            cb.moveText(x, y);
            cb.setFontAndSize(bf, size);
            cb.showText(text);
            cb.endText();
            cb.restoreState();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static int appendCSV(ReceiptDTO dto) {
    	try {
    		
    		String textToAppend = dto.getReceiptNumber()+"|"+dto.getDonerName()+"|"+dto.getAmount()
	    		+"|"+dto.getDateOfDonation()+"|"+dto.getModeOfDonation()
	    		+"|"+dto.getDonationReceivedBy()+"|"+dto.getRctGenerationDate()+"|"+dto.getMobileNo()+"|"+dto.getDonationTowards()+"|"+dto.getOtherFinDetails();
    	     
    	    BufferedWriter writer = new BufferedWriter(
    	                                new FileWriter(CSVPATH, true)  //Set true for append mode
    	                            ); 
    	    writer.newLine();   //Add new line
    	    writer.write(textToAppend);
    	    writer.close();
    	}
    	catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	
    	return 0;
    }
    
    private static String pw(int n,String ch)
    {
    	String returnStr = "";
      String  one[]={" "," One"," Two"," Three"," Four"," Five"," Six"," Seven"," Eight"," Nine"," Ten"," Eleven"," Twelve"," Thirteen"," Fourteen","Fifteen"," Sixteen"," Seventeen"," Eighteen"," Nineteen"};
   
      String ten[]={" "," "," Twenty"," Thirty"," Forty"," Fifty"," Sixty"," Seventy"," Eighty"," Ninety"};
   
      if(n > 19) { returnStr += ten[n/10]+" "+one[n%10];} 
      	else { returnStr += one[n];}
      if(n > 0)returnStr+=(ch);
      
      return returnStr;
    }
}
