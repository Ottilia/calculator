package calculator.calculations;

import javax.ejb.Local;

import calculator.entities.User;

@Local
public interface calculationsLocal {
	void saveCalculations(User user, String equation, String result);
}
