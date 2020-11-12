/**
 * 
 */
package ec.com.galaxy.core.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import ec.com.galaxy.core.errors.ErrorMessage;
import ec.com.galaxy.core.errors.ErrorProcess;
import ec.com.galaxy.core.exceptions.LineViolationException;
import ec.com.galaxy.core.exceptions.RomanException;
import ec.com.galaxy.core.line.TypeLine;

/**
 * @author abenalcazar
 *
 */
public class Processor {

	private ArrayList<String> output;
	private TypeLine typeLine;
	private HashMap<String, String> constantAssignments;
	private HashMap<String, String> computedLiterals;
	private ErrorMessage eMessage;

	public Processor() {
		this.typeLine = new TypeLine();
		this.eMessage = new ErrorMessage();
		this.constantAssignments = new HashMap<String, String>();
		this.computedLiterals = new HashMap<String, String>();
		this.output = new ArrayList<String>();
	}

	/**
	 * Read archive lines and process
	 * 
	 * @param lines
	 * @return
	 */
	public ArrayList<String> read(List<String> lines) {
		int count = 0;
		ErrorProcess error = null;
		for (String line : lines) {
			error = validateInputFile(line);
			if (error.equals(ErrorProcess.NO_IDEA)) {
				this.output.add(this.eMessage.getMessage(error));
				break;
			} else {
				this.eMessage.printMessage(error);
			}
			count++;
		}
		return this.output;
	}

	/**
	 * Method for validate input file
	 * 
	 * @param line
	 * @return
	 */
	private ErrorProcess validateInputFile(String line) {
		ErrorProcess error = ErrorProcess.SUCCESS_OK;
		TypeLine.Type lineType = this.typeLine.getLineType(line);
		
		if (lineType.equals(TypeLine.Type.ASSIGNED)) {
			executeAssignmentLine(line);
		}
		else if (lineType.equals(TypeLine.Type.CREDITS)) {
			executeCreditsLine(line);
		}
		else if (lineType.equals(TypeLine.Type.QUESTION_HOW_MUCH)) {
			executeHowMuchQuestion(line);
		}
		else if (lineType.equals(TypeLine.Type.QUESTION_HOW_MANY)) {
			executeHowManyCreditsQuestion(line);
		} else {
			error = ErrorProcess.NO_IDEA;
		}
		return error;
	}

	
	/**
	 * 
	 * @param line
	 */
	private void executeAssignmentLine(String line) {
		String[] splited = line.trim().split("\\s+");
		try {
			constantAssignments.put(splited[0], splited[2]);
		} catch (ArrayIndexOutOfBoundsException e) {
			this.eMessage.printMessage(ErrorProcess.INCORRECT_LINE_TYPE);
			System.out.print(e.getMessage());
		}
	}

	/**
	 * 
	 * @param line
	 * @throws Exception
	 */
	private void executeHowMuchQuestion(String line) {
		try {
			String formatted = line.split("\\sis\\s")[1].trim();
			formatted = formatted.replace("?", "").trim();
			String keys[] = formatted.split("\\s+");
			String romanResult = "";
			String completeResult = null;
			boolean errorOccured = false;

			for (String key : keys) {
				String romanValue = constantAssignments.get(key);
				if (romanValue == null) {
					completeResult = this.eMessage.getMessage(ErrorProcess.NO_IDEA);
					errorOccured = true;
					break;
				}
				romanResult += romanValue;
			}

			if (!errorOccured) {
				// Calculator roman number 
				romanResult = Calculator.romanToArabic(romanResult);
				completeResult = formatted + " is " + romanResult;
			}

			output.add(completeResult);

		} catch (RomanException e) {
			this.eMessage.printMessage(ErrorProcess.INVALID_ROMAN_STRING);
			System.out.print(e.getMessage());
		}
		catch (LineViolationException e) {
			this.eMessage.printMessage(ErrorProcess.INCORRECT_LINE_TYPE);
			System.out.print(e.getMessage());
		}
		
	
	}

	/**
	 * 
	 * @param line
	 */
	private void executeCreditsLine(String line) {
		try {
			String formatted = line.replaceAll("(is\\s+)|([c|C]redits\\s*)", "").trim();
			String[] keys = formatted.split("\\s");
			String toBeComputed = keys[keys.length - 2];
			float value = Float.parseFloat(keys[keys.length - 1]);
			String roman = "";

			for (int i = 0; i < keys.length - 2; i++) {
				roman += constantAssignments.get(keys[i]);
			}

			int romanNumber = Integer.parseInt(Calculator.romanToArabic(roman));
			float credit = (float) (value / romanNumber);

			computedLiterals.put(toBeComputed, credit + "");
		} catch (Exception e) {

			this.eMessage.printMessage(ErrorProcess.INCORRECT_LINE_TYPE);
			System.out.print(e.getMessage());

		}
	}

	/**
	 * 
	 * @param line
	 */
	private void executeHowManyCreditsQuestion(String line) {
		try {
			String formatted = line.split("(\\sis\\s)")[1];
			formatted = formatted.replace("?", "").trim();
			String[] keys = formatted.split("\\s");
			boolean found = false;
			String roman = "";
			String outputResult = null;
			Stack<Float> cvalues = new Stack<Float>();

			for (String key : keys) {
				found = false;

				String romanValue = constantAssignments.get(key);
				if (romanValue != null) {
					roman += romanValue;
					found = true;
				}

				String computedValue = computedLiterals.get(key);
				if (!found && computedValue != null) {
					cvalues.push(Float.parseFloat(computedValue));
					found = true;
				}

				if (!found) {
					outputResult = this.eMessage.getMessage(ErrorProcess.NO_IDEA);
					break;
				}
			}

			if (found) {
				float res = 1;
				for (int i = 0; i < cvalues.size(); i++)
					res *= cvalues.get(i);

				int finalres = (int) res;
				if (roman.length() > 0)
					finalres = (int) (Integer.parseInt(Calculator.romanToArabic(roman)) * res);
				outputResult = formatted + " is " + finalres + " Credits";
			}

			this.output.add(outputResult);

		} catch (Exception e) {
			this.eMessage.printMessage(ErrorProcess.INCORRECT_LINE_TYPE);
			System.out.print(e.getMessage());
		}

	}

}
