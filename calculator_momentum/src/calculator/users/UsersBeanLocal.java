package calculator.users;

import javax.ejb.Local;
import calculator.entities.User;

@Local
public interface UsersBeanLocal 
{
	User getUser(User attempting_user);
	User getUserbyUserName(String userName);
}
