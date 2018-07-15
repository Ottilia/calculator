package calculator.users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.ejb.LocalBean;

import calculator.database.Database;
import calculator.entities.User;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class UsersBean
 */
@Stateless(mappedName = "UsersBean")
@LocalBean
public class UsersBean implements UsersBeanLocal {
    public UsersBean() {}

	@Override
	public User getUser(User attempting_user) {
		User foundUser = new User(); 
		PreparedStatement ps = null;
		ResultSet result = null;
		try 
		{
			Class.forName(Database.driver).newInstance();
			Connection connection = DriverManager.getConnection(Database.url, Database.username, Database.password);
			ps  = connection.prepareStatement(Database.fetchUser);
			ps.setString(1,attempting_user.getUsername());
			ps.setString(2, attempting_user.getPassword());
			result = ps.executeQuery();
			
			if (result.next())
	        {
				foundUser.setUserId(result.getLong("id"));
	        	foundUser.setUsername(result.getString("user_name"));
	        	foundUser.setName(result.getString("name"));
	        	foundUser.setSurname(result.getString("surname"));
	        	foundUser.setAdmin(result.getBoolean("is_admin"));
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return foundUser;
	}

	@Override
	public User getUserbyUserName(String userName) {
		User foundUser = new User(); 
		PreparedStatement ps = null;
		ResultSet result = null;
		try 
		{
			Class.forName(Database.driver).newInstance();
			Connection connection = DriverManager.getConnection(Database.url, Database.username, Database.password);
			ps  = connection.prepareStatement(Database.fetchUserByUserName);
			ps.setString(1,userName);
			result = ps.executeQuery();
			
			if (result.next())
	        {
				foundUser.setUserId(result.getLong("id"));
	        	foundUser.setUsername(result.getString("user_name"));
	        	foundUser.setName(result.getString("name"));
	        	foundUser.setSurname(result.getString("surname"));
	        	foundUser.setAdmin(result.getBoolean("is_admin"));
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return foundUser;
	}
}
