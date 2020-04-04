package seniorProject;

//File Name: InfoSheet.java
//Creator: Bailey Schunn
//Date Last Modified: 3/20/2020
//Purpose: Class file, sets all data parameters for PDF
//         based on user selections received from main
//         application file

// Import statements

import java.text.NumberFormat;
import java.util.Locale;
import java.io.*;

public class InfoSheet 
{
	// VARIABLES
	private int twoYearTuition, fourYearTuition, housingCost, totalTwoYearCost, totalFourYearCost;
	private String twoYearTuitionStr, fourYearTuitionStr, housingCostStr, totalTwoYearStr, totalFourYearStr;
	private String stateStr, statusStr, housingStr;
	private boolean fafsaIndicator, scholarshipIndicator, tuitionAssistIndicator, taxCreditIndicator;
	
	// CONSTRUCTOR
	public InfoSheet(int stateIndex, int statusIndex, int housingIndex, String stateChoice, String statusChoice, String housingChoice, boolean fafsaChoice, boolean scholarshipChoice, boolean tuitionAssistChoice, boolean taxCreditChoice) 
	{
		setCosts(stateIndex, statusIndex, housingIndex);
		setState(stateChoice);
		setStatus(statusChoice);
		setHousing(housingChoice);
		setIndicators(fafsaChoice, scholarshipChoice, tuitionAssistChoice, taxCreditChoice);
	}
	
	// SET METHODS
	
	// The setCosts method reads each line of the CollegeCosts.txt file until the stateID variable matches the state variable,
	// then sets the values for tuition and housing costs based on the user's status and housing choices in the application form
	public void setCosts(int state, int status, int housing)
	{
		String[] array = new String[8];
		String fileText = "";
		String delimiter = ",";
		String stateId;
		
		// CollegeCosts.txt file contains all cost data in the following format:
		// [state ID],[state name],[2-year in-state tuition],[2-year out-of-state tuition],[4-year in-state tuition],[4-year out-of-state tuition],[on-campus cost],[off-campus cost]
		
		try
		{
			InputStream input = this.getClass().getResourceAsStream("CollegeCosts.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			System.out.println();
			fileText = reader.readLine();
			while(fileText != null)
			{
				array = fileText.split(delimiter);
				stateId = array[0];
				if(Integer.parseInt(stateId) == state)
				{
					if(status == 1)
					{
						twoYearTuition = Integer.parseInt(array[2]);
						fourYearTuition = Integer.parseInt(array[4]);
					}
					else if(status == 2)
					{
						twoYearTuition = Integer.parseInt(array[3]);
						fourYearTuition = Integer.parseInt(array[5]);
					}
					else
					{
						twoYearTuition = 0;
						fourYearTuition = 0;
					}
					
					if(housing == 1)
					{
						housingCost = Integer.parseInt(array[6]);
					}
					else if(housing == 2)
					{
						housingCost = Integer.parseInt(array[7]);
					}
					else
					{
						housingCost = 0;
					}
					break;
				}
				fileText = reader.readLine();
			}
			reader.close();
			totalTwoYearCost = twoYearTuition + housingCost;
			totalFourYearCost = fourYearTuition + housingCost;
		}
		catch(Exception error)
		{
			System.out.println("Error message: " + error);
		}
	}
	
	// The setState method sets the string value of the user's chosen state
	public void setState(String stateChoice)
	{
		stateStr = stateChoice;
	}
	
	// The setStatus method sets the string value of the user's residency status in all lower case characters
	public void setStatus(String statusChoice)
	{
		statusStr = statusChoice.toLowerCase();
	}
	
	// The setHousing method sets the string value of the user's housing option in all lower case characters
	public void setHousing(String housingChoice)
	{
		housingStr = housingChoice.toLowerCase();
	}
	
	// The setIndicators method sets the boolean indicator variables to indicate which financial aid solutions the user chose
	public void setIndicators(boolean fafsa, boolean scholarships, boolean tuitionAssistance, boolean taxCredits)
	{
		fafsaIndicator = fafsa;
		scholarshipIndicator = scholarships;
		tuitionAssistIndicator = tuitionAssistance;
		taxCreditIndicator = taxCredits;
	}
	
	// GET METHODS
	
	// The getTwoYearTuition method returns the two-year tuition cost as a string with commas placed appropriately
	public String getTwoYearTuition()
	{
		twoYearTuitionStr = NumberFormat.getNumberInstance(Locale.US).format(twoYearTuition);
		return twoYearTuitionStr;
	}
	
	// The getFourYearTuition method returns the four-year tuition cost as a string with commas placed appropriately
	public String getFourYearTuition()
	{
		fourYearTuitionStr = NumberFormat.getNumberInstance(Locale.US).format(fourYearTuition);
		return fourYearTuitionStr;
	}
	
	// The getHousingCost method returns the housing cost as a string with commas placed appropriately
	public String getHousingCost()
	{
		housingCostStr = NumberFormat.getNumberInstance(Locale.US).format(housingCost);
		return housingCostStr;
	}
	
	// The getTotalTwoYearCost method returns the total cost for a two-year college as a string with commas placed appropriately
	public String getTotalTwoYearCost()
	{
		totalTwoYearStr = NumberFormat.getNumberInstance(Locale.US).format(totalTwoYearCost);
		return totalTwoYearStr;
	}
	
	// The getTotalFourYearCost method returns the total cost for a four-year college as a string with commas placed appropriately
	public String getTotalFourYearCost()
	{
		totalFourYearStr = NumberFormat.getNumberInstance(Locale.US).format(totalFourYearCost);
		return totalFourYearStr;
	}
	
	// The getState method returns a string containing the user's chosen state
	public String getState()
	{
		return stateStr;
	}
	
	// The getStatus method returns a string containing the user's chosen residency status
	public String getStatus()
	{
		return statusStr;
	}
	
	// The getHousing method returns a string containing the user's chosen housing option
	public String getHousing()
	{
		return housingStr;
	}
	
	// The getFafsaIndicator method returns the fafsaIndicator boolean value
	public boolean getFafsaIndicator()
	{
		return fafsaIndicator;
	}
	
	// The getScholarshipIndicator method returns the scholarshipIndicator boolean value
	public boolean getScholarshipIndicator()
	{
		return scholarshipIndicator;
	}
	
	// The getTuitionAssistIndicator method returns the tuitionAssistIndicator boolean value
	public boolean getTuitionAssistIndicator()
	{
		return tuitionAssistIndicator;
	}
	
	// The getTaxCreditIndicator method returns the taxCreditIndicator boolean value
	public boolean getTaxCreditIndicator()
	{
		return taxCreditIndicator;
	}
}
