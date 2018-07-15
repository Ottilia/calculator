package calculator.admin;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import calculator.entities.Calculation;

@Local
public interface AdminBeanLocal 
{
	List<Calculation> searchCalculationsByCriteria(Long userId, Date dateFrom, Date dateTo);
}
