package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rec.CreateReceipt;
import com.rec.ReceiptDTO;

/**
 * Servlet implementation class submitReceipt
 */
public class submitReceipt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public submitReceipt() {
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
		//doGet(request, response);
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		try {
		ReceiptDTO dto = new ReceiptDTO();
    	dto.setAmount(request.getParameter("amount"));
    	dto.setAmountInwords(request.getParameter("amountinWords"));
    	dto.setDateOfDonation(request.getParameter("dateofDonation"));
    	dto.setDonationReceivedBy(request.getParameter("donationReceivedBy"));
    	dto.setDonerName(request.getParameter("name"));
    	dto.setModeOfDonation(request.getParameter("modeofDonation"));
    	dto.setMobileNo(request.getParameter("mobile"));
    	dto.setDonationTowards(request.getParameter("donationTowards"));
    	dto.setOtherFinDetails(request.getParameter("otherFinDetails"));
    	String receiptNo = new CreateReceipt().createPdf(dto);
    	if(receiptNo!=null) {
    		out.println("Receipt Created Successfully <br/> receipt Number: "+receiptNo);
    		out.println("<a href='createReceipt.jsp'> Create Receipt Again </a><br/>");
    		out.println("<a href='logout.jsp'> logout </a>");
    	}
    	else {
    		out.println("Receipt Not Created , Please try again <br/>");
    		out.println("<a href='createReceipt.jsp'> Create Receipt Again </a>");
    	}
    		
    	
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
			
		}
	}

}
