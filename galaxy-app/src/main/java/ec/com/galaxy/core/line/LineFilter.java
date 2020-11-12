/**
 * 
 */
package ec.com.galaxy.core.line;

/**
 * @author abenalcazar
 *
 */
public class LineFilter
{
	private TypeLine.Type type;
	private String pattern;
	public LineFilter(TypeLine.Type type,String pattern)
	{
		this.type = type;
		this.pattern = pattern;
	}
	
	public String getPattern()
	{
		return this.pattern;
				
	}
	
	public TypeLine.Type getType()
	{
		return this.type;
	}
}