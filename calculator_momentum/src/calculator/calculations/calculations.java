package calculator.calculations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import calculator.database.Database;
import calculator.entities.User;

/**
 * Session Bean implementation class calculations
 */
@Stateless(mappedName = "calculations")
@LocalBean
public class calculations implements calculationsLocal {

	@Override
	public void saveCalculations(User user, String equation, String result) 
	{
		PreparedStatement ps = null;
		int resultRows = 0;
		try 
		{
			Class.forName(Database.driver).newInstance();
			Connection connection = DriverManager.getConnection(Database.url, Database.username, Database.password);
			ps  = connection.prepareStatement(Database.saveCalculation);
			ps.setString(1,equation);
			ps.setString(2,result);
			ps.setLong(3,user.getUserId());
			resultRows = ps.executeUpdate();
			if (resultRows > 0)
			{
				
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
	
}
