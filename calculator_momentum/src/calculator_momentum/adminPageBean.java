package calculator_momentum;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import calculator.admin.AdminBean;
import calculator.entities.Calculation;
import calculator.entities.User;
import calculator.users.UsersBean;

@ManagedBean
@SessionScoped
public class adminPageBean {
	private String username;
	private Date dateFrom;
	private Date dateTo;
	private List<Calculation> calculations;
	private String searchMessage;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	public List<Calculation> getCalculations() {
		return calculations;
	}
	public void setCalculations(List<Calculation> calculations) {
		this.calculations = calculations;
	}
	
	public String getSearchMessage() {
		return searchMessage;
	}
	public void setSearchMessage(String searchMessage) {
		this.searchMessage = searchMessage;
	}
	
	public void search()
	{
	    try {
	    	Long userId = null;
	    	if (getUsername() != null && !getUsername().equalsIgnoreCase(""))
	    	{
		    	//get user
		    	UsersBean userBean = new UsersBean();
		    	User resultUser = userBean.getUserbyUserName(getUsername());
		    	userId = resultUser.getUserId();
	    	}

    		//at least one of the fields must not be null
    		if (userId != null || getDateFrom() != null || getDateTo() != null)
    		{
		    	//get calculations according to criteria
		    	AdminBean adminBean = new AdminBean();
		    	List<Calculation> resultCalculations = adminBean.searchCalculationsByCriteria(userId, getDateFrom(), getDateTo());
    		
		    	//setList of calculations
		    	setCalculations(resultCalculations);
		    	//clear error message
		    	setSearchMessage("success");
    		}
    		else
    		{
    			setSearchMessage("Failed");
    			List<Calculation> resultCalculations = new ArrayList();
    			setCalculations(resultCalculations);
    		}
	    	
		} catch (Exception e) {
			List<Calculation> resultCalculations = new ArrayList();
			setCalculations(resultCalculations);
			setSearchMessage("Failed");
			e.printStackTrace();
		}
	}	
}
