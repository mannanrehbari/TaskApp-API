package com.backend.rest.payload;

public class BillingLog {
	
	private Long SMS_Parts;
	private Long Credit_Deducted;
	
	public BillingLog() {
	}
	
	public BillingLog(Long sMS_Parts, Long credit_Deducted) {
		SMS_Parts = sMS_Parts;
		Credit_Deducted = credit_Deducted;
	}
	
	public Long getSMS_Parts() {
		return SMS_Parts;
	}
	public void setSMS_Parts(Long sMS_Parts) {
		SMS_Parts = sMS_Parts;
	}
	public Long getCredit_Deducted() {
		return Credit_Deducted;
	}
	public void setCredit_Deducted(Long credit_Deducted) {
		Credit_Deducted = credit_Deducted;
	}

	@Override
	public String toString() {
		return "BillingLog [SMS_Parts=" + SMS_Parts + ", Credit_Deducted=" + Credit_Deducted + "]";
	}
		

}
