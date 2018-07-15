package calculator.database;

public class Database {
	//for db connection
	public static final String url = "jdbc:mariadb://calculatordb.coho3ghuvsx5.eu-west-3.rds.amazonaws.com/momentum_calculatordb";
	public static final String driver = "org.mariadb.jdbc.Driver";
	public static final String username = "ottilia";
	public static final String password = "test1234";
	
	//users
	public static final String fetchUser = "SELECT * FROM users WHERE user_name = ? AND password = ?;";
	public static final String fetchUserByUserName = "SELECT * FROM users WHERE user_name = ?;";
	
	//calculations 
	public static final String saveCalculation = "INSERT INTO calculation (calculation_requested, calculation_answer, user_id, calculation_date)	VALUES (?, ?, ?, NOW());";
	
	//admin 
	public static final String searchCalculations = "SELECT cl.*, us.user_name FROM calculation cl left join users us on cl.user_id = us.id WHERE user_id is not null";
	public static final String searchCalculationsbyUserId = "SELECT * FROM calculation WHERE user_id = ?;";
}
