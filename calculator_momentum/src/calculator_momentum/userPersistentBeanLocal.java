package calculator_momentum;

import javax.ejb.Local;

@Local
public interface userPersistentBeanLocal {
	User getUser(User attempting_user);
}
