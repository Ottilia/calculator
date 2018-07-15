package calculator_momentum;

import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.jasper.tagplugins.jstl.ForEach;

import com.sun.msv.datatype.xsd.regex.RegExp;

import calculator.calculations.calculations;
import calculator.entities.User;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;

@ManagedBean
@SessionScoped
public class advancedBean {
	private String equation;
	private double result;
	private String message;
	
	public String getEquation() {
		return equation;
	}
	public void setEquation(String equation) {
		this.equation = equation;
	}
	public double getResult() {
		return result;
	}
	public void setResult(double result) {
		this.result = result;
	}	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void calculate()
	{
		//concatenate equation string
		String eq = findSpecialOperators(getEquation());
		if (!eq.equalsIgnoreCase("Error"))
		{
		    try {
		    	//get actual value of string using js 
				ScriptEngineManager manager = new ScriptEngineManager();
			    ScriptEngine se = manager.getEngineByName("JavaScript");
				Object actualvalue = se.eval(eq);
				setResult(Double.parseDouble(actualvalue.toString()));
				
				ELContext elContext = FacesContext.getCurrentInstance().getELContext();
				loginBean lb = (loginBean) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "loginBean");
				
				//save calculation to db
				calculations calc = new calculations();
				User user = new User();
				user.setUserId(lb.getUserId());
				String re = String.valueOf(getResult());
				calc.saveCalculations(user, eq, re);
				setMessage("");
			} catch (Exception e) {
				setMessage("Error");
				e.printStackTrace();
			}
		}
		else
		{
			setMessage("Error");
		}
	}
	
	public String findSpecialOperators(String eq)
	{
		try
		{
			String str = eq;
			
			//check ^
			str = checkExponent(str);
			
			//check root
			str = checkRoot(str);
					
			//check e
			if (str.indexOf("e") >= 0)
			{
				str = str.replaceAll("e", "Math.E");
			}
			
			//check pi
			if (str.indexOf("pi") >= 0)
			{
				str = str.replaceAll("pi", "Math.PI");
			}
		
			return str;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return "Error";
	}
	
	public String checkExponent(String e)
	{
		try
		{
			int indexOfExponent =  e.indexOf("^");
			int lengthOfEquation = e.length();
			int openingBrackets = 0;
			int closingBrackets = 0;
			boolean bracketsFoundMatch = true;
			String newEquation = e;
			
			if (false)
			{}
			
			if (indexOfExponent > 0)
			{
				//get number before ^ 
				String numberBefore = "";
				int indexOfNumberBefore = 0;
				boolean numberExpected = false; //to make sure we search for the proper precedent
				Character indexChar;
				for(int c=indexOfExponent-1; c>=0 ; c--)
				{
					indexChar = e.charAt(c);
					if (Character.isDigit(indexChar))
					{
						numberBefore = indexChar + numberBefore;
						indexOfNumberBefore = c;
						numberExpected = true;
					}
					else
					{	if (numberExpected)
						{
							indexOfNumberBefore = c + 1;
							break;
						}
						else
						{
							if (indexChar == 'e')
							{
								indexOfNumberBefore = c;
								numberBefore = indexChar + numberBefore;
								break;
							}
							else
							{
								for (int anotherc=c; anotherc>=0; anotherc--)
								{
									indexChar = e.charAt(anotherc);
									if (indexChar.toString().matches("[^*%/+-]") || !bracketsFoundMatch) //do not mix numberBefore with prev operator
									{
										if (indexChar == ')')
										{
											closingBrackets ++;
										}
										else
											if (indexChar == '(')
											{
												openingBrackets ++;
											}
										if (closingBrackets == openingBrackets)
										{
											bracketsFoundMatch = true;
										}
										else
										{
											bracketsFoundMatch = false;
										}
										indexOfNumberBefore = anotherc;
										numberBefore =  indexChar + numberBefore;
									}
								}
								break;//loop above already loops till the end so break out of repetition
							}
						}
					}
				}
				
				//get number after ^
				String numberAfter = "";
				int indexOfNumberAfter = 0;
				numberExpected = false;
				for(int c=indexOfExponent+1; c<=lengthOfEquation-1 ; c++)
				{
					indexChar = e.charAt(c);
					if (Character.isDigit(e.charAt(c)))
					{
						numberAfter = numberAfter + e.charAt(c);
						indexOfNumberAfter = c;
						numberExpected = true;
					}
					else
					{
						if (numberExpected)
						{
							indexOfNumberAfter = c-1;
							break;
						}
						else
						{
							if (indexChar == 'e')
							{
								indexOfNumberAfter = c;
								numberAfter = numberAfter + e.charAt(c);
								break;
							}
							else
							{
								for (int anotherc=c; anotherc<=lengthOfEquation-1; anotherc++)
								{
									indexChar = e.charAt(anotherc);
									if (indexChar.toString().matches("[^*%/+-]")) //do not mix numberAfter with next operator
									{
										indexOfNumberAfter = anotherc;
										numberAfter = numberAfter + e.charAt(anotherc);
									}
								}
								break;//loop above already loops till the end so break out of repetition
							}
						}
					}
				}
				
				//replace ^
				String strToReplace = newEquation.substring(indexOfNumberBefore, indexOfNumberAfter+1);
				//handle regex special characters
				strToReplace = escapeSpecialChars(strToReplace);
				String strToReplaceWith = "Math.pow("+ numberBefore + "," + numberAfter +")";
				newEquation = newEquation.replaceAll(strToReplace, strToReplaceWith);	
			}
			
			return newEquation;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return "Error";
	}
	
	public String checkRoot(String e)
	{
		try
		{
			int indexOfRoot =  e.indexOf("root")+4; //includes root length
			int lengthOfEquation = e.length();
			String newEquation = e;
			int openBrackets = 0;
			int closedBrackets = 0;
			String numberAfter = "";
			int indexOfNumberAfter = 0;
			boolean numberExpected = false;
			Character indexChar;
			if (e.indexOf("root") >= 0)
			{
				for(int c=indexOfRoot; c<=lengthOfEquation-1 ; c++)
				{
					indexChar = e.charAt(c);
					if (Character.isDigit(e.charAt(c)))
					{
						numberAfter = numberAfter + e.charAt(c);
						indexOfNumberAfter = c;
						numberExpected = true;
					}
					else
					{
						if (numberExpected)
						{
							indexOfNumberAfter = c-1;
							break;
						}
						else
						{
							if (indexChar == 'e')
							{
								indexOfNumberAfter = c;
								numberAfter = numberAfter + e.charAt(c);
								break;
							}
							else
							{
								for (int anotherc=c; anotherc<=lengthOfEquation-1; anotherc++)
								{
									indexChar = e.charAt(anotherc);
									if (indexChar.toString().matches("[^*%()/+-]")) //do not mix numberAfter with next operator
									{
										indexOfNumberAfter = anotherc;
										numberAfter = numberAfter + e.charAt(anotherc);
									}
								}
								break;//loop above already loops till the end so break out of repetition
							}
						}
					}
				}
				
				//replace root
				String strToReplace = numberAfter;
				String[] numbers = strToReplace.split(","); //gets numbers to calculate
				strToReplace = "root(" + numbers[0] + "," + numbers[1] + ")"; //rebuild original string, escape regex special characters
				strToReplace = escapeSpecialChars(strToReplace);
				String strToReplaceWith = "Math.pow(" + numbers[0] + "," + "(1/" + numbers[1] + "))"; //root formula
				newEquation = newEquation.replaceAll(strToReplace, strToReplaceWith);	
			}
			return newEquation;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return "Error";
	}
	
	public String escapeSpecialChars(String strToReplace)
	{
		strToReplace = strToReplace.replaceAll("\\^", "\\\\^");
		strToReplace = strToReplace.replaceAll("\\(", "\\\\(");
		strToReplace = strToReplace.replaceAll("\\)", "\\\\)");
		strToReplace = strToReplace.replaceAll("\\+", "\\\\+");
		strToReplace = strToReplace.replaceAll("\\-", "\\\\-");
		strToReplace = strToReplace.replaceAll("\\*", "\\\\*");
		strToReplace = strToReplace.replaceAll("\\/", "\\\\/");
		strToReplace = strToReplace.replaceAll("\\%", "\\\\%");
		strToReplace = strToReplace.replaceAll("\\,", "\\\\,");
		
		return strToReplace;
	}
}
