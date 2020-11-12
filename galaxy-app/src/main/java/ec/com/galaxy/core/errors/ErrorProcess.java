/**
 * 
 */
package ec.com.galaxy.core.errors;

/**
 * @author abenalcazar
 *
 */
public enum ErrorProcess {
	
	NO_IDEA, // This code specifies not idea
	SUCCESS_OK,//This code specifies that there is no error
	NO_INPUT,//This code specifies that there is input error. Input provided is empty
	INVALID,//This code specifies that it does not match with any conversation line type specified in paragraph conversationType enum
	INVALID_ROMAN_CHARACTER,//This error code specifies that roman number have some illegal characters 
	INVALID_ROMAN_STRING,//This error code specifies that roman literal is in invalid format.
	INCORRECT_LINE_TYPE //This code specifies that a line has been identified as different type instead of its actual type

}
