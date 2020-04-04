package seniorProject;

// File Name: StudentAidForm.java
// Creator: Bailey Schunn
// Date Last Modified: 3/20/2020
// Purpose: Main application file, generates a PDF file containing
//          financial aid information based on user selections

// This application uses the iText external Java library for the purposes of PDF creation
// Please visit www.itextpdf.com to access iText documentation

// IMPORT STATEMENTS
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Link;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.element.List;

public class StudentAidForm extends JFrame implements ItemListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	
	// ARRAYS AND VARIABLES
	
	// Dropdown menu options
	String[] stateOptions = {"", "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};
	String[] statusOptions = {"", "In-State", "Out-Of-State"};
	String[] housingOptions = {"", "On-Campus", "Off-Campus", "At Home"};
	
	// Additional variables
	String state = stateOptions[0];
	int stateIndex = 0;
	String status = statusOptions[0];
	int statusIndex = 0;
	String housing = housingOptions[0];
	int housingIndex = 0;
	boolean fafsa = false;
	boolean scholarship = false;
	boolean tuitionAssist = false;
	boolean taxCredits = false;
	
	// The default file path for the PDF created by the application
	// The PDF file is stored in the same location as the application file
	String dest = "FinancialAidGuide.pdf";
	
	// Objects that set up the layout for the JFrame
	FlowLayout flow = new FlowLayout();
	
	JComboBox<String> stateChoice = new JComboBox<String>(stateOptions);
	JComboBox<String> statusChoice = new JComboBox<String>(statusOptions);
	JComboBox<String> housingChoice = new JComboBox<String>(housingOptions);
	
	JCheckBox fafsaOption = new JCheckBox("Free Application For Student Aid (FAFSA)", false);
	JCheckBox scholarshipOption = new JCheckBox("Scholarships", false);
	JCheckBox tuitionAssistOption = new JCheckBox("Employer Tuition Assistance Programs", false);
	JCheckBox taxCreditsOption = new JCheckBox("Education Tax Credits", false);
	
	JButton resetButton = new JButton("Reset Form");
	JButton submitButton = new JButton("Create PDF");
	
	JLabel line1 = new JLabel("Use this form to create a PDF file with helpful information about the costs of ");
	JLabel line2 = new JLabel("attending college and different methods of financing your education without ");
	JLabel line3 = new JLabel("going into debt.");
	JLabel finAid = new JLabel("Choose which financial aid solutions you would like more information about: ");
	Font boldFont = new Font("Arial", Font.BOLD, 13);
	JLabel warning = new JLabel("");
	
	// CONSTRUCTOR
	// Inserts text and input options into the JFrame
	public StudentAidForm()
	{
		super("Financial Aid Guidesheet");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Stops running the application when the user closes out of the frame
		setLayout(flow);
		add(new JLabel("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"));
		add(line1);
		line1.setFont(boldFont);
		add(line2);
		line2.setFont(boldFont);
		add(line3);
		line3.setFont(boldFont);
		add(new JLabel("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"));
		add(new JLabel("In which state will you be attending college? : "));
		add(stateChoice);
		add(new JLabel("Will you be considered an in-state or out-of-state student? : "));
		add(statusChoice);
		add(new JLabel("Where will you be living while attending college? : "));
		add(housingChoice);
		add(new JLabel("                                                                       "));
		add(new JLabel("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"));
		add(finAid);
		finAid.setFont(boldFont);
		add(new JLabel("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"));
		add(fafsaOption);
		add(new JLabel(" || "));
		add(scholarshipOption);
		add(tuitionAssistOption);
		add(new JLabel(" || "));
		add(taxCreditsOption);
		add(new JLabel("                                                                                                                                         "));
		add(resetButton);
		add(submitButton);
		add(warning);
		warning.setFont(boldFont);
		warning.setForeground(Color.RED);
		
		// Sets the following objects as event sources and the class as the listener
		stateChoice.addItemListener(this);
		statusChoice.addItemListener(this);
		housingChoice.addItemListener(this);
		fafsaOption.addItemListener(this);
		scholarshipOption.addItemListener(this);
		tuitionAssistOption.addItemListener(this);
		taxCreditsOption.addItemListener(this);
		resetButton.addActionListener(this);
		submitButton.addActionListener(this);
		
	}
	
	// METHODS
	
	// Sets the action events for the drop-down menus and check-boxes
	public void itemStateChanged(ItemEvent e)
	{
		Object source = e.getSource();
		if(source == stateChoice)
		{
			stateIndex = stateChoice.getSelectedIndex();
			state = stateOptions[stateIndex];
		}
		else if(source == statusChoice)
		{
			statusIndex = statusChoice.getSelectedIndex();
			status = statusOptions[statusIndex];
		}
		else if(source == housingChoice)
		{
			housingIndex = housingChoice.getSelectedIndex();
			housing = housingOptions[housingIndex];
		}
		else if(source == fafsaOption)
		{
			int select = e.getStateChange();
			if(select == ItemEvent.SELECTED)
			{
				fafsa = true;
			}
			else
			{
				fafsa = false;
			}
		}
		else if(source == scholarshipOption)
		{
			int select = e.getStateChange();
			if(select == ItemEvent.SELECTED)
			{
				scholarship = true;
			}
			else
			{
				scholarship = false;
			}
		}
		else if(source == tuitionAssistOption)
		{
			int select = e.getStateChange();
			if(select == ItemEvent.SELECTED)
			{
				tuitionAssist = true;
			}
			else
			{
				tuitionAssist = false;
			}
		}
		else
		{
			int select = e.getStateChange();
			if(select == ItemEvent.SELECTED)
			{
				taxCredits = true;
			}
			else
			{
				taxCredits = false;
			}
		}
	}
	
	// Sets the action events for the Reset Form and Create PDF buttons
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		if(source == resetButton)
		{
			stateChoice.setSelectedIndex(0);
			stateIndex = 0;
			state = stateOptions[0];
			statusChoice.setSelectedIndex(0);
			statusIndex = 0;
			status = statusOptions[0];
			housingChoice.setSelectedIndex(0);
			housingIndex = 0;
			housing = housingOptions[0];
			fafsaOption.setSelected(false);
			fafsa = false;
			scholarshipOption.setSelected(false);
			scholarship = false;
			tuitionAssistOption.setSelected(false);
			tuitionAssist = false;
			taxCreditsOption.setSelected(false);
			taxCredits = false;
			warning.setText("");
		}
		else
		{
			// Displays a warning message if user did not answer first three questions
			if(stateIndex == 0 || statusIndex == 0 || housingIndex == 0)
			{
				warning.setText("Please respond to the first three questions in the form.");
			}
			else // Creates the PDF, then opens the PDF file once created
			{
				warning.setText("");
				InfoSheet pdfFile = new InfoSheet(stateIndex, statusIndex, housingIndex, state, status, housing, fafsa, scholarship, tuitionAssist, taxCredits);
				try
				{
					File pdfDoc = new File(dest);
					int i = 1;
					while(pdfDoc.exists()) // Creates new file name if current file already exists
					{
						dest = "FinancialAidGuide(" + i + ").pdf";
						pdfDoc = new File(dest);
						i++;
					}
					PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
				    Document document = new Document(pdf);
				    
				    // Introduction Section
				    Text title = new Text("College Financial Aid - A Guide to Avoiding Student Loan Debt");
				    title.setFontColor(ColorConstants.BLUE);
				    title.setFontSize(18);
				    title.setBold();
				    title.setUnderline();
				    document.add(new Paragraph(title));
				    
				    document.add(new Paragraph("Congratulations on pursuing your college education! This is a very exciting and important time in your life, and we hope this guide will help you make the best financial decisions as you prepare to further your education."));
				    
				    document.add(new Paragraph("As you know, college isn’t cheap. Between the costs of tuition, books, housing, and other expenses, earning a bachelor’s degree can cost tens of thousands of dollars. Many advisors will tell you that, unless you earn prestigious scholarships or have wealthy family members who are willing to help you, you will likely have to take out student loans to pay for your education."));
				    
				    document.add(new Paragraph("The good news is that you do have more options! The goal of this guide is to provide you with knowledge and resources that you can use to avoid as much student loan debt as possible. With that said, let’s get started!"));
				    
				    document.add(new Paragraph(""));
				    
				    // Cost Comparison Section
				    Text costComparison = new Text("Cost Comparison: Two-Year vs. Four-Year Colleges");
				    costComparison.setFontColor(ColorConstants.BLUE);
				    costComparison.setFontSize(16);
				    document.add(new Paragraph(costComparison));
				    
				    document.add(new Paragraph("As opposed to attending a university for all four years of your undergraduate degree program, starting your education at a two-year or community college can save you thousands of dollars in tuition costs. You’ll also enjoy smaller class sizes compared to a university, which is a plus when you eventually find yourself needing one-on-one help from your professor. And on top of everything, you’ll graduate with an associate’s degree before transferring to a university to finish up your final two years of school."));
				    
				    document.add(new Paragraph("To show you just how much money you can save by choosing this route, we’ve included a cost comparison table below. This table compares the average cost of attending a two-year college to the average cost of attending a four-year college for a single year. Please note that this table does not include the cost of books, supplies, or other fees, as these costs will vary by school and degree program."));
				    
				    // Inserts a statement showing the user's state, residency status, and housing option
				    String inputStatementText = "You've indicated that you'll be attending college in " + pdfFile.getState() + " as an " + pdfFile.getStatus() + " student living " + pdfFile.getHousing() + ".";
				    Text inputStatement = new Text(inputStatementText);
				    inputStatement.setFontColor(ColorConstants.RED);
				    document.add(new Paragraph(inputStatement));
				    
				    // Displays a note about on-campus housing at two-year colleges if the user chooses the on-campus housing option
				    if(housingIndex == 1)
				    {
				    	Text onCampusNote = new Text("Keep in mind that not all two-year colleges offer on-campus housing. See your college admissions advisor for details on available housing options.");
				    	onCampusNote.setFontColor(ColorConstants.RED);
				    	document.add(new Paragraph(onCampusNote));
				    }
				    
				    document.add(new Paragraph(""));
				    
				    // Sets up the cost comparison table
				    Table costComparisonTable = new Table(UnitValue.createPercentArray(3)).useAllAvailableWidth();
				    costComparisonTable.addCell("");
				    
				    Text header1 = new Text("Two-Year College");
				    header1.setFontColor(ColorConstants.BLUE);
				    header1.setBold();
				    Cell colHeader1 = new Cell();
				    colHeader1.add(new Paragraph(header1));
				    colHeader1.setTextAlignment(TextAlignment.CENTER);
				    costComparisonTable.addCell(colHeader1);
				    
				    Text header2 = new Text("Four-Year College");
				    header2.setFontColor(ColorConstants.BLUE);
				    header2.setBold();
				    Cell colHeader2 = new Cell();
				    colHeader2.add(new Paragraph(header2));
				    colHeader2.setTextAlignment(TextAlignment.CENTER);
				    costComparisonTable.addCell(colHeader2);
				    
				    Text tuitionRowText = new Text("Tuition:");
				    tuitionRowText.setFontColor(ColorConstants.BLUE);
				    tuitionRowText.setBold();
				    Cell tuitionRow = new Cell();
				    tuitionRow.add(new Paragraph(tuitionRowText));
				    tuitionRow.setTextAlignment(TextAlignment.CENTER);
				    costComparisonTable.addCell(tuitionRow);
				    
				    Cell twoYearTuitionCell = new Cell();
				    twoYearTuitionCell.add(new Paragraph("$" + pdfFile.getTwoYearTuition()));
				    twoYearTuitionCell.setTextAlignment(TextAlignment.CENTER);
				    costComparisonTable.addCell(twoYearTuitionCell);
				    
				    Cell fourYearTuitionCell = new Cell();
				    fourYearTuitionCell.add(new Paragraph("$" + pdfFile.getFourYearTuition()));
				    fourYearTuitionCell.setTextAlignment(TextAlignment.CENTER);
				    costComparisonTable.addCell(fourYearTuitionCell);
				    
				    Text housingRowText = new Text("Housing:");
				    housingRowText.setFontColor(ColorConstants.BLUE);
				    housingRowText.setBold();
				    Cell housingRow = new Cell();
				    housingRow.add(new Paragraph(housingRowText));
				    housingRow.setTextAlignment(TextAlignment.CENTER);
				    costComparisonTable.addCell(housingRow);
				    
				    Cell housingCell1 = new Cell();
				    housingCell1.add(new Paragraph("$" + pdfFile.getHousingCost()));
				    housingCell1.setTextAlignment(TextAlignment.CENTER);
				    costComparisonTable.addCell(housingCell1);
				    
				    Cell housingCell2 = new Cell();
				    housingCell2.add(new Paragraph("$" + pdfFile.getHousingCost()));
				    housingCell2.setTextAlignment(TextAlignment.CENTER);
				    costComparisonTable.addCell(housingCell2);
				    
				    Text totalRowText = new Text("TOTAL:");
				    totalRowText.setFontColor(ColorConstants.BLUE);
				    totalRowText.setBold();
				    Cell totalRow = new Cell();
				    totalRow.add(new Paragraph(totalRowText));
				    totalRow.setTextAlignment(TextAlignment.CENTER);
				    costComparisonTable.addCell(totalRow);
				    
				    Text twoYearTotalText = new Text("$" + pdfFile.getTotalTwoYearCost());
				    twoYearTotalText.setBold();
				    Cell twoYearTotal = new Cell();
				    twoYearTotal.add(new Paragraph(twoYearTotalText));
				    twoYearTotal.setTextAlignment(TextAlignment.CENTER);
				    costComparisonTable.addCell(twoYearTotal);
				    
				    Text fourYearTotalText = new Text("$" + pdfFile.getTotalFourYearCost());
				    fourYearTotalText.setBold();
				    Cell fourYearTotal = new Cell();
				    fourYearTotal.add(new Paragraph(fourYearTotalText));
				    fourYearTotal.setTextAlignment(TextAlignment.CENTER);
				    costComparisonTable.addCell(fourYearTotal);
				    
				    document.add(costComparisonTable);
				    
				    // Displays a message if user did not select any financial aid options
				    if(pdfFile.getFafsaIndicator() == false && pdfFile.getScholarshipIndicator() == false && pdfFile.getTuitionAssistIndicator() == false && pdfFile.getTaxCreditIndicator() == false)
				    {
				    	document.add(new Paragraph(""));
				    	Text noFinAid = new Text("Create a new PDF to learn more about financial aid solutions that may be available to you!");
				    	noFinAid.setFontColor(ColorConstants.RED);
				    	document.add(new Paragraph(noFinAid));
				    }
				    else
				    {
				    	// Continues onto the next page of the document
				    	document.add(new AreaBreak());
				    }
				    
				    // Displays info about FAFSA if user selected the FAFSA option in the form
				    if(pdfFile.getFafsaIndicator() == true)
				    {
				    	Text fafsaHeader = new Text("The Free Application for Student Aid (FAFSA)");
				    	fafsaHeader.setFontColor(ColorConstants.BLUE);
				    	fafsaHeader.setFontSize(16);
					    document.add(new Paragraph(fafsaHeader));
					    
					    document.add(new Paragraph("The Free Application for Student Aid, or FAFSA form, is an online form that you complete before each year of college to determine your eligibility for different types of financial aid. The types of aid you may qualify for can include grants, federal work-study programs, federal student loans, and other specialized types of financial aid. "));
					    
					    document.add(new Paragraph("It is recommended that you complete this form every year you are in school, regardless of whether you think you’ll qualify for any financial aid. If you need help filling out the FAFSA form, the official FAFSA website provides great tools and walkthroughs to help make the process as painless as possible."));
					    
					    document.add(new Paragraph("The FAFSA website also includes some great resources to help you find other sources of financial aid such as scholarships. In fact, some of the other resources mentioned in this guide are explained in detail on their site as well."));
					    
					    // Sets up a link to the FAFSA website
					    Link fafsaLink = new Link("www.studentaid.gov", PdfAction.createURI("https://studentaid.gov/"));
					    fafsaLink.setFontColor(ColorConstants.BLUE);
					    fafsaLink.setBold();
					    fafsaLink.setUnderline();
					    Paragraph fafsaWeblink = new Paragraph("Visit ").add(fafsaLink).add(" to complete the FAFSA form or to learn more about the types of financial aid available to you.");
					    document.add(fafsaWeblink);
					    
					    document.add(new Paragraph(""));
				    }
				    
				    // Displays info about scholarships if user selected the scholarships option in the form
				    if(pdfFile.getScholarshipIndicator() == true)
				    {
				    	Text scholarshipHeader = new Text("Scholarships");
				    	scholarshipHeader.setFontColor(ColorConstants.BLUE);
				    	scholarshipHeader.setFontSize(16);
					    document.add(new Paragraph(scholarshipHeader));
					    
					    document.add(new Paragraph("In a nutshell, scholarships provide you with free money to use toward your college expenses. That’s right, FREE! This money does not need to be repaid. There are thousands of scholarships that you can apply for from a variety of sources, including schools, employers, private companies, non-profits, and other organizations. The trick in finding the right scholarships to apply for is knowing where to look. Here’s a list of people and places you should check with to get you started on your search:"));
					    
					    document.add(new Paragraph(""));
					    
					    List scholarshipList = new List();
					    scholarshipList.add("your college financial aid office");
					    scholarshipList.add("a high school counselor");
					    scholarshipList.add("organizations related to your field of interest");
					    scholarshipList.add("your employer or your parents’ employers");
					    scholarshipList.add("honor societies or other academic organizations you may be involved with");
					    document.add(scholarshipList);
					    
					    document.add(new Paragraph(""));
					    
					    document.add(new Paragraph("When searching for scholarships, beware of financial aid scams! Always keep in mind that you will never have to pay to apply for any legitimate scholarship."));
					    
					    document.add(new Paragraph(""));
				    }
				    
				    // Displays info about employer tuition assistance if user selected the employer tuition assistance option in the form
				    if(pdfFile.getTuitionAssistIndicator() == true)
				    {
				    	Text tuitionAssistHeader = new Text("Employer Tuition Assistance Programs");
				    	tuitionAssistHeader.setFontColor(ColorConstants.BLUE);
				    	tuitionAssistHeader.setFontSize(16);
					    document.add(new Paragraph(tuitionAssistHeader));
					    
					    document.add(new Paragraph("Believe it or not, there are some employers out there who are willing to pay for some or all of their employees’ college expenses. These programs can vary widely in terms of employee eligibility requirements, the amount of assistance they provide, and how that assistance is paid out. However, these programs can prove to be an invaluable benefit, saving employees thousands of dollars in college expenses. When looking at an employer tuition assistance program, there are several questions you will want to find the answers to before committing yourself to the program:"));
					    
					    document.add(new Paragraph(""));
					    
					    List questionsList = new List();
					    questionsList.add("When will you become eligible to receive this benefit? Are you eligible as soon as you are hired, or do you need to work at the company for a year or two first?");
					    questionsList.add("How much aid can you receive? Does the program cover all of your expenses or just a certain percentage?");
					    questionsList.add("What expenses are covered? Does the program only cover tuition costs, or does it also cover books and other supplies?");
					    questionsList.add("Is the financial aid paid directly to the college up-front, or will you receive a reimbursement at a later date? If you will be reimbursed, when can you expect to receive the reimbursement payment?");
					    questionsList.add("What kind of degree programs are covered? Will you have to attend a specific college?");
					    questionsList.add("Are there any other eligibility requirements? Will you have to maintain a certain GPA? Are there any specific work performance requirements?");
					    questionsList.add("What will happen if you leave the company? Will you be required to stay with the company for a certain length of time after graduating?");
					    document.add(questionsList);
					    
					    document.add(new Paragraph(""));
					    
					    document.add(new Paragraph("Make sure you obtain as much information as possible before applying for any tuition assistance. Contact the company’s HR department with any questions you may have about their tuition assistance benefits."));
					    
					    document.add(new Paragraph(""));
				    }
				    
				    // Displays info about education tax credits if user selected the education tax credits option in the form
				    if(pdfFile.getTaxCreditIndicator() == true)
				    {
				    	Text taxCreditHeader = new Text("Education Tax Credits");
				    	taxCreditHeader.setFontColor(ColorConstants.BLUE);
				    	taxCreditHeader.setFontSize(16);
					    document.add(new Paragraph(taxCreditHeader));
					    
					    document.add(new Paragraph("The federal government offers two tax credits that can help to offset the costs of attending college: the American Opportunity Credit and the Lifetime Learning Credit."));
					    
					    document.add(new Paragraph(""));
					    
					    List taxCreditList = new List();
					    taxCreditList.add("The American Opportunity Credit allows you to claim up to $2,500 per student per year for the first four years of school.");
					    taxCreditList.add("The Lifetime Learning Credit allows you to claim up to $2,000 per student per year for any college or career school tuition and fees, as well as required books and supplies.");
					    document.add(taxCreditList);
					    
					    document.add(new Paragraph(""));
					    
					    document.add(new Paragraph("Be sure to consult a tax professional before attempting to claim either of these tax credits to ensure that you are eligible to receive them. If you determine you are eligible to receive a tax credit, keep in mind that expenses covered by tax-free aid (scholarships, grants, tax-free tuition assistance, etc.) cannot be applied toward earning an education tax credit."));
					    
					    // Sets up a link to the IRS website for education tax credits
					    Link irsLink = new Link("www.irs.gov", PdfAction.createURI("https://www.irs.gov/credits-deductions/individuals/education-credits-aotc-llc"));
					    irsLink.setFontColor(ColorConstants.BLUE);
					    irsLink.setBold();
					    irsLink.setUnderline();
					    Paragraph irsWeblink = new Paragraph("To learn more about these credits, visit the official IRS website at ").add(irsLink).add(".");
					    document.add(irsWeblink);
				    }
				    
				    document.close();
				    
				    // Opens the PDF file once created
					File file = new File(dest);
					if(!Desktop.isDesktopSupported())
					{
						System.out.println("Unable to open PDF file");
					}
					else
					{
						Desktop desktop = Desktop.getDesktop();
						if(file.exists())
						{
							desktop.open(file);
						}
						else
						{
							System.out.println("Unable to open PDF file");
						}
					}
				}
				catch(Exception error)
				{
					System.out.println("Error message: " + error);
				}
			}
		}
	}

	// Main method
	public static void main(String[] args) 
	{
		StudentAidForm frame = new StudentAidForm();
		String OS = System.getProperty("os.name"); // Gets the name of the operating system
		if(OS.startsWith("Windows") == true) // Sets the size of the window for users with Windows OS
		{
			frame.setSize(525,475);
		}
		else // Sets the size of the window for users not using Windows OS
		{
			frame.setSize(555,475);
		}
		frame.setLocationRelativeTo(null); // Sets the window to open in the center of the screen
		frame.setVisible(true); // Sets the window to appear as visible
	}
}
