package calculator_momentum;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import calculator.entities.User;
import calculator.users.UsersBean;

@SessionScoped
@ManagedBean(name = "loginBean")
public class loginBean {
	private Long userId;
	private String username;
	private String password;
	private boolean isAdmin;
	private int number;
	private String loginMessage;
	
	public loginBean() {
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public String login()
	{
		//InitialContext ctx = null;
		FacesContext context = FacesContext.getCurrentInstance();
		UsersBean userBean = new UsersBean();
		try {
//			ctx = new InitialContext(props);
			
			//userBean = (usersPersistentBean) ctx.lookup("usersPersistentBean");
			User attempting_user = new User();
			attempting_user.setUsername(getUsername());
			attempting_user.setPassword(getPassword());
			User resultUser = userBean.getUser(attempting_user);
			if (resultUser.getUsername() != null)
			{
				setIsAdmin(resultUser.isAdmin());
				setUserId(resultUser.getUserId());
				setLoginMessage("success");
				context.getExternalContext().redirect("basicCalculator.xhtml");
			}
			else
			{
				setLoginMessage("*Incorrect Username and password");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return getLoginMessage();		
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLoginMessage() {
		return loginMessage;
	}

	public void setLoginMessage(String loginMessage) {
		this.loginMessage = loginMessage;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
