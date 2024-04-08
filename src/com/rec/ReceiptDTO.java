package com.rec;

public class ReceiptDTO {
	
    private String receiptNumber;
    private String rctGenerationDate;
    private String donerName;
    private String amount;
    private String amountInwords;
    private String modeOfDonation;
    private String dateOfDonation;
    private String donationTowards;
    private String presidentName;
    private String SecretoryName;
    private String tresures;
    private String donationReceivedBy;
    private String mobileNo;
    private String otherFinDetails;

    
	public String getOtherFinDetails() {
		return otherFinDetails;
	}
	public void setOtherFinDetails(String otherFinDetails) {
		this.otherFinDetails = otherFinDetails;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getReceiptNumber() {
		return receiptNumber;
	}
	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}
	public String getRctGenerationDate() {
		return rctGenerationDate;
	}
	public void setRctGenerationDate(String rctGenerationDate) {
		this.rctGenerationDate = rctGenerationDate;
	}
	public String getDonerName() {
		return donerName;
	}
	public void setDonerName(String donerName) {
		this.donerName = donerName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAmountInwords() {
		return amountInwords;
	}
	public void setAmountInwords(String amountInwords) {
		this.amountInwords = amountInwords;
	}
	public String getModeOfDonation() {
		return modeOfDonation;
	}
	public void setModeOfDonation(String modeOfDonation) {
		this.modeOfDonation = modeOfDonation;
	}
	public String getDateOfDonation() {
		return dateOfDonation;
	}
	public void setDateOfDonation(String dateOfDonation) {
		this.dateOfDonation = dateOfDonation;
	}
	public String getDonationTowards() {
		return donationTowards;
	}
	public void setDonationTowards(String donationTowards) {
		this.donationTowards = donationTowards;
	}
	public String getPresidentName() {
		return presidentName;
	}
	public void setPresidentName(String presidentName) {
		this.presidentName = presidentName;
	}
	public String getSecretoryName() {
		return SecretoryName;
	}
	public void setSecretoryName(String secretoryName) {
		SecretoryName = secretoryName;
	}
	public String getTresures() {
		return tresures;
	}
	public void setTresures(String tresures) {
		this.tresures = tresures;
	}
	public String getDonationReceivedBy() {
		return donationReceivedBy;
	}
	public void setDonationReceivedBy(String donationReceivedBy) {
		this.donationReceivedBy = donationReceivedBy;
	}
	@Override
	public String toString() {
		return "ReceiptDTO [receiptNumber=" + receiptNumber + "]";
	}
    
	
    

}
