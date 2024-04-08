package com.rec;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Random;

import org.apache.tomcat.util.http.fileupload.IOUtils;

/**
 * Hello world!
 *
 */
public class CreateReceiptBKP 
{
	public static final String IMAGE = System.getProperty("user.dir")+"\\CR\\Blank.jpg";
    public static final String DEST = "C:\\Users\\admin\\Desktop\\ACFReceipts\\background_image.pdf";
    public static Properties p=new Properties();  
    
    static{
    	try {
	    	FileReader reader=new FileReader(System.getProperty("user.dir")+"\\CR\\data.properties");  
			p.load(reader);
		} catch (Exception e) {
			System.out.println("Expected file at this location: "+System.getProperty("user.dir")+"\\CR\\data.properties");
			e.printStackTrace();
		}  
    }
    
    public static void main( String[] args ) throws IOException, DocumentException
    {
    	
    	/*ReceiptDTO dto = new ReceiptDTO();
    	dto.setAmount("5000");
    	dto.setAmountInwords("Five Thousands");
    	dto.setDateOfDonation("05/06/2019");
    	dto.setDonationReceivedBy("Niranjan Aher Dada");
    	dto.setDonerName("Rajnath Singh");
    	dto.setModeOfDonation("Cheque");
    	new CreateReceipt().createPdf(dto);*/
    	
    	
    	
    	System.out.println(p.get("SecretoryName"));
    	File file = new File(DEST);
        file.getParentFile().mkdirs();
        new CreateReceiptBKP().createPdf(DEST);
    }
    
    public String createPdf(ReceiptDTO dto) throws IOException, DocumentException {
    	
    	Random rand = new Random();
    	String receiptNum = p.get("receiptExt")+""+rand.nextInt(1000000)+"";
    	dto.setReceiptNumber(receiptNum);
    	
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(p.getProperty("PDFFilePath")+"\\"+dto.getDonerName()+"_"+receiptNum+".pdf"));
        document.open();
        //document.add(new Paragraph("Berlin!"));
        PdfContentByte canvas = writer.getDirectContentUnder();
        Image image = Image.getInstance(IMAGE);
        image.scaleAbsolute(PageSize.A4.rotate());
        image.setAbsolutePosition(0, 0);
        
        canvas.addImage(image);
        
        
        //System.out.println("ACF-"+rand.nextInt());
        FixText(receiptNum, 110, 333, writer, 14);  // Receipt number
        
        Date d = new Date();
        
        
        FixText(d.getDay()+"", 720, 333, writer, 14);	//Date: day
        FixText(d.getMonth()+"", 743, 333, writer, 14);	// Date: month
        FixText(new GregorianCalendar().get(Calendar.YEAR)+"", 766, 333, writer, 14);	//Date: Year
        dto.setRctGenerationDate(d.getDay()+"/"+d.getMonth()+"/"+new GregorianCalendar().get(Calendar.YEAR));
        
        FixText(dto.getDonerName(), 325, 278, writer, 14); // Name of the doner
        FixText(dto.getAmount()+"/-", 280, 230, writer, 14);  // Amount paid
        
        int n = Integer.parseInt(dto.getAmount());
        String inWord = "";
        inWord += pw((n/1000000000)," Hundred");
        inWord += pw((n/10000000)%100," crore");
        inWord += pw(((n/100000)%100)," lakh");
        inWord += pw(((n/1000)%100)," thousand");
        inWord += pw(((n/100)%10)," hundred");
        inWord += pw((n%100)," ");
        
        FixText(inWord +" Only", 460, 230, writer, 14);  // Amount paid in words
        FixText(dto.getModeOfDonation(), 460, 180, writer, 14); 				// mode of transfer
        
        try {
        	FixText(dto.getDateOfDonation().split("/")[0]+"/", 110, 130, writer, 14);	//Date: day
            FixText(dto.getDateOfDonation().split("/")[1]+"/", 133, 130, writer, 14);	// Date: month
            FixText(dto.getDateOfDonation().split("/")[2], 156, 130, writer, 14);	//Date: Year
        }
        catch(Exception ex) {
        	ex.printStackTrace();
        	System.out.println("Date of donation is incorrect. Please send it in DD/MM/YYYY format");
        	//System.exit(0);
        }
        
        FixText(p.get("donationTowards")+"", 420, 130, writer, 14);	//Donation towards
        FixText(p.get("presidentName")+"", 130, 80, writer, 14);		//President
        FixText(p.get("SecretoryName")+"", 280, 80, writer, 14);		//Secretory
        FixText(p.get("tresures")+"", 460, 80, writer, 14);		//Tresurer
        FixText(dto.getDonationReceivedBy()+"", 620, 80, writer, 14);		//Received by
        
        appendCSV(dto);
        document.close();
        return receiptNum;
    }
    
    public void createPdf(String dest) throws IOException, DocumentException {
    	
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
        FixText("Receipt Num1", 330, 650, writer, 20);  // Receipt number
        FixText("07", 2063, 650, writer, 14);	//Date: day
        FixText("06", 2126, 650, writer, 14);	// Date: month
        FixText("2019", 2193, 650, writer, 14);	//Date: Year
        FixText("Abhijit Kumbhar", 900, 555, writer, 32); // Name of the doner
        FixText("5000"+"/-", 700, 455, writer, 14);  // Amount paid
        FixText("Five Thousands"+" Only", 1300, 455, writer, 14);  // Amount paid in words
        FixText("By Cash", 820, 355, writer, 14); 				// mode of transfer
        FixText("07"+"/", 340, 255, writer, 14);	//Date: day
        FixText("06"+"/", 395, 255, writer, 14);	// Date: month
        FixText("2019", 450, 255, writer, 14);	//Date: Year
        FixText("Alert Citizen Forum", 1200, 255, writer, 14);	//Donation towards
        FixText("Niranjan Aher", 370, 155, writer, 14);		//President
        FixText("Niranjan Aher1", 820, 155, writer, 14);		//Secretory
        FixText("Niranjan Aher2", 1380, 155, writer, 14);		//Tresurer
        FixText("Niranjan Aher3", 1763, 155, writer, 14);		//Received by
        
        
        document.close();
    }
    
    private static void FixText(String text, int x, int y,PdfWriter writer,int size) {
        try {
        	size=38;
            PdfContentByte cb = writer.getDirectContent();

            File file = new File(p.getProperty("FONTFILE"));
            //init array with file length
            byte[] bytesArray = new byte[(int) file.length()]; 
            
            

            BaseFont bf = BaseFont.createFont(p.getProperty("FONTFILE"), "CP1251", BaseFont.EMBEDDED);
            
            
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
    
    public int appendCSV(ReceiptDTO dto) {
    	try {
    		
    		String textToAppend = dto.getReceiptNumber()+"|"+dto.getDonerName()+"|"+dto.getAmount()
	    		+"|"+dto.getDateOfDonation()+"|"+dto.getModeOfDonation()
	    		+"|"+dto.getDonationReceivedBy()+"|"+dto.getRctGenerationDate()+"|"+dto.getMobileNo();
    	     
    	    BufferedWriter writer = new BufferedWriter(
    	                                new FileWriter(p.getProperty("csvPath"), true)  //Set true for append mode
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
    
    public String pw(int n,String ch)
    {
    	String returnStr = "";
      String  one[]={" "," one"," two"," three"," four"," five"," six"," seven"," eight"," nine"," ten"," eleven"," twelve"," thirteen"," fourteen","fifteen"," sixteen"," seventeen"," eighteen"," nineteen"};
   
      String ten[]={" "," "," twenty"," thirty"," forty"," fifty"," sixty","seventy"," eighty"," ninety"};
   
      if(n > 19) { returnStr += ten[n/10]+" "+one[n%10];} 
      	else { returnStr += one[n];}
      if(n > 0)returnStr+=(ch);
      
      return returnStr;
    }
}
