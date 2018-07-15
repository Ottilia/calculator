package calculator_momentum;

import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import calculator.calculations.calculations;
import calculator.entities.User;

@ManagedBean
@SessionScoped
public class basicBean {
	private double first_value;
	private double second_value;
	private String operator;
	private double result;
	private String message;
	
	public double getFirst_value() {
		return first_value;
	}
	public void setFirst_value(double first_value) {
		this.first_value = first_value;
	}
	public double getSecond_value() {
		return second_value;
	}
	public void setSecond_value(double second_value) {
		this.second_value = second_value;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public double getResult() {
		return result;
	}
	public void setResult(double result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void calculate()
	{
	    try {
	    	//concatenate equation string
			String equation = getFirst_value() + getOperator() + getSecond_value();		
			//get actual value of string using js
			ScriptEngineManager manager = new ScriptEngineManager();
		    ScriptEngine se = manager.getEngineByName("JavaScript");
			Object actualvalue = se.eval(equation);
			setResult(Double.parseDouble(actualvalue.toString()));
			
			ELContext elContext = FacesContext.getCurrentInstance().getELContext();
			loginBean lb = (loginBean) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "loginBean");
			
			//save calculation to db
			calculations calc = new calculations();
			User user = new User();
			user.setUserId(lb.getUserId());
			calc.saveCalculations(user, equation, actualvalue.toString());
			setMessage("");
		} catch (Exception e) {
			setMessage("Error");
			e.printStackTrace();
		}
	}

}
