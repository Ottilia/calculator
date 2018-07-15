package calculator.entities;

import java.util.Date;

public class Calculation {
	private Long calcId;
	private String calcRequested;
	private String calcAnswer;
	private Long userId;
	private String userName;
	private String calcDate;
	
	public Calculation(){}

	public Long getCalcId() {
		return calcId;
	}

	public void setCalcId(Long calcId) {
		this.calcId = calcId;
	}

	public String getCalcRequested() {
		return calcRequested;
	}

	public void setCalcRequested(String calcRequested) {
		this.calcRequested = calcRequested;
	}

	public String getCalcAnswer() {
		return calcAnswer;
	}

	public void setCalcAnswer(String calcAnswer) {
		this.calcAnswer = calcAnswer;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCalcDate() {
		return calcDate;
	}

	public void setCalcDate(String calcDate) {
		this.calcDate = calcDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
