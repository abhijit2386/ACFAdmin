<%@page import="com.rec.CreateReceipt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ACF</title>
</title>
<link rel="stylesheet" href="jquery-ui.css">
<link rel="stylesheet" type="text/css" href="receipt.css" />
<script src="jquery-1.12.4.js"></script>
<script src="jquery-ui.js"></script>
<script>
  $( function() {
    $( "#datepicker" ).datepicker({ dateFormat: 'dd-mm-yy' });
  } );
  
  function validate(){
	  
	  if(jQuery.isNumeric($("#amnt").val()) == false){ alert("Please enter valid amount"); 
	  return false;}
	  else return true;
	  
  }
  </script>
</head>
<body>
	Welcome
	<%= session.getAttribute("user") %>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="logout.jsp">Logout</a>
	<br>
	<form name="fileiploadForm" class="form-style-9"
		action="FileUploadServlet" method="post" enctype="multipart/form-data">
		<ul>
			<li><input type="file" name="file"
				class="field-style field-split align-left" size="50"
				placeholder="Upload File" /></li>
			<li>Download Sample CSV file: <a href="Sample.csv">Sample
					upload file</a>
			</li>
			<li><input type="submit" value="Upload File" /></li>
		</ul>
	</form>

	<form class="form-style-9" action="submitReceipt" method="post">

		<ul>
			<li><input autocomplete="off" type="text" name="name"
				class="field-style field-split align-left" placeholder="Name" /> <input
				autocomplete="off" type="text" name="mobile"
				class="field-style field-split align-right" placeholder="Mobile No" />
			</li>
			<li><input autocomplete="off" type="text" id="amnt"
				name="amount" class="field-style field-split align-left"
				placeholder="Amount Donated" /></li>
			<li><input autocomplete="off" id="datepicker" type="text"
				name="dateofDonation" class="field-style field-split align-left"
				placeholder="date of Donation in DD/MM/YYYY" /> <input
				autocomplete="off" type="text" name="donationReceivedBy"
				class="field-style field-split align-right"
				placeholder="Donation Received By" /></li>
			<li>Mode of Payment <select name="modeofDonation"
				class="dropdwn">
					<option value="Cash">Cash</option>
					<option value="Cheque">Cheque</option>
					<option value="Online Transfer">Online Transfer</option>
					<option value="Other">Other</option>
			</select> <input autocomplete="off" type="text" name="otherFinDetails"
				class="field-style field-split align-right"
				placeholder="Other Payment Details" />
			</li>
			<li>Donation Towards: <select type="select"
				name="donationTowards" class="field-style field-split align-right"
				placeholder="Donation towards">
					<%
for (int i=0; i<CreateReceipt.donationTowards.length; i++) {
    out.println("<option value=\""+CreateReceipt.donationTowards[i]+"\">"+CreateReceipt.donationTowards[i]+"</option>");
}
%>
			</select>
			</li>
			<li><input type="submit" value="Create Receipt"
				onclick="return validate();" /></li>
		</ul>
	</form>
</body>
</html>