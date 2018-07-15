package calculator_momentum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ejb.Stateless;

@Stateless
public class usersPersistentBean implements userPersistentBeanLocal {
	public usersPersistentBean(){}
	
	public User getUser(User attempting_user)
	{
		 User foundUser = new User();
		 Connection connection = null;
		 String url = "jdbc:mariadb://calculatordb.coho3ghuvsx5.eu-west-3.rds.amazonaws.com/momentum_calculatordb";
		 String driver = "org.mariadb.jdbc.Driver";
		 String username = "ottilia";
		 String password = "test1234";
		 
		 String sql = "select * from users where user_name = '" + attempting_user.getUsername() +
				 	  "' and password = '" + attempting_user.getPassword() + "';";
		 
		 try {
			Class.forName(driver).newInstance();
			connection = DriverManager.getConnection(url , username, password);
			Statement st = connection.createStatement();
	        ResultSet result = st.executeQuery(sql);
	        if (result.next())
	        {
	        	foundUser.setId(result.getLong("id"));
	        	foundUser.setUsername(result.getString("user_name"));
	        	foundUser.setName(result.getString("name"));
	        	foundUser.setSurname(result.getString("surname"));
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	      
		return foundUser;
	}
}
