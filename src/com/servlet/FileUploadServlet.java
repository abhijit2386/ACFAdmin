package com.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import com.itextpdf.text.DocumentException;
import com.rec.CreateReceipt;
import com.rec.ReceiptDTO;

/**
 * Servlet implementation class FileUploadServlet
 */
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		if(ServletFileUpload.isMultipartContent(request)) {
			try {
				List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(new ServletRequestContext(request));
				BufferedReader br = null;
		        String line = "";
		        String cvsSplitBy = ",";
		        
				for(FileItem item : multiparts){
                    if(!item.isFormField()){
                    		File file;
                    	// Write the file
                    		System.out.println(System.getProperty("user.dir") +"/CR/csvupload/"+ item.getName());
                           file = new File( System.getProperty("user.dir") +"/CR/csvupload/"+ item.getName()) ;

                           item.write( file ) ;
                       
                           int firstline = 0;
						
						  //File file = new File(item.getName()); 
                           br = new BufferedReader(new FileReader(file)); 
						  List<ReceiptDTO> dtos = new ArrayList<ReceiptDTO>();
						  boolean isErrorExists = false;
						  String outputline = "";
					
						  while ((line = br.readLine()) != null) { 
							  if(firstline==0) {firstline++;}	// ignoring first line
							  else{
								  	firstline++;
								  	// use comma as separator
					                String[] receiptParam = line.split(cvsSplitBy);
					                int size = receiptParam.length;
					               if(size != 8) {
					            	   isErrorExists = true;
					            	   outputline += "Error in the line: >> "+line+"\n";
					               }
					               else {
					            	   	String donerName = receiptParam[0];
						                String amount = receiptParam[2];
						                String modeOfDonation = receiptParam[5];
						                String dateOfDonation = receiptParam[3];
						                String donationTowards = receiptParam[7];
						                String donationReceivedBy = receiptParam[4];
						                String mobileNo = receiptParam[1];
						                String otherFinDetails = receiptParam[6];
						                
						                
						                
						                ReceiptDTO receiptDTO = new ReceiptDTO();
						                receiptDTO.setDonerName(donerName);
						                receiptDTO.setAmount(amount);
						                receiptDTO.setModeOfDonation(modeOfDonation);
						                receiptDTO.setDateOfDonation(dateOfDonation);
						                receiptDTO.setDonationTowards(donationTowards);
						                receiptDTO.setDonationReceivedBy(donationReceivedBy);
						                receiptDTO.setMobileNo(mobileNo);
						                receiptDTO.setOtherFinDetails(otherFinDetails);
						                
						                dtos.add(receiptDTO);
						                
						                
						                
					               }
					                
					                
							  }
							  
						  
						  }
						  
						  if(isErrorExists == true) {
							  out.println(outputline);
						  }else {
							  int i=0;
							  for(ReceiptDTO receiptDTO : dtos){
								  try {
										CreateReceipt.createPdf(receiptDTO);
										i++;
										System.out.println(i+" receipt created "+receiptDTO);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
										System.out.println(i+" receipt not created "+receiptDTO);
									} catch (DocumentException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
										System.out.println(i+" receipt not created "+receiptDTO);
									}
							  }
							  /*dtos.forEach(receiptDTO -> {
								  try {
									CreateReceipt.createPdf(receiptDTO);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (DocumentException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							  });*/
							  out.println("All receipts created successfully");
						  }
						 
                        //System.out.println(name);
                        //item.write( new File(UPLOAD_DIRECTORY + File.separator + name));
                    }
          }
				
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}
