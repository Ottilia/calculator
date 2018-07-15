package calculator.admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;

import calculator.database.Database;
import calculator.entities.Calculation;
import calculator.entities.User;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class UsersBean
 */
@Stateless(mappedName = "AdminBean")
@LocalBean
public class AdminBean implements AdminBeanLocal {
    public AdminBean() {}

	@Override
	public List<Calculation> searchCalculationsByCriteria(Long userId, Date dateFrom, Date dateTo) {
		List<Calculation> foundCalculations = new ArrayList<Calculation>();
		PreparedStatement ps = null;
		ResultSet result = null;
		try 
		{
			String searchCalculations = Database.searchCalculations;
			//add criteria
			if (userId != null)
			{
				searchCalculations +=  " AND user_id = ? ";
			}
			
			if (dateFrom != null)
			{
				searchCalculations += " AND calculation_date >= ?";
			}
			
			if (dateTo != null)
			{
				searchCalculations += " AND calculation_date <= ?";
			}
			searchCalculations += ";";
			
			int countArg = 0;
			
			Class.forName(Database.driver).newInstance();
			Connection connection = DriverManager.getConnection(Database.url, Database.username, Database.password);
			ps  = connection.prepareStatement(searchCalculations);
			if (userId != null)
			{
				ps.setLong(++countArg, userId);
			}
			
			if (dateFrom != null)
			{
				ps.setDate(++countArg, new java.sql.Date(dateFrom.getTime()));
			}
			
			if (dateTo != null)
			{
				ps.setDate(++countArg, new java.sql.Date(dateTo.getTime()));
			}
			
			result = ps.executeQuery();
			
			while (result.next())
	        {
				Calculation foundCalculation = new Calculation(); 
				foundCalculation.setCalcId(result.getLong("id"));
				foundCalculation.setCalcRequested(result.getString("calculation_requested"));
				foundCalculation.setCalcAnswer(result.getString("calculation_answer"));
				foundCalculation.setUserId(result.getLong("user_id"));
				foundCalculation.setCalcDate(result.getString("calculation_date"));
				foundCalculation.setUserName(result.getString("user_name"));
				foundCalculations.add(foundCalculation);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return foundCalculations;
	}
}
