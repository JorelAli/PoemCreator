package io.github.skepter.poemcreator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for building a poem. Simple adds text to the poem.
 * Creates a simple single column poem a line at a time.
 * 
 * @author Jorel
 */
public class PoemBuilder {

	//TODO Possibility to allow this class to create PoemStrings by
	//breaking up strings which are too long?
	
	public final static int STR_LENGTH = 29;
	//2 for borders, 2 for left padding, 2 for right padding
	public final static int TXT_LENGTH = STR_LENGTH - 6;
	private List<String> builder;
	
	public PoemBuilder() {
		builder = new ArrayList<>();
		//EACH string cannot be longer than 29 characters!
		//*                           *
		//generateNewLine();
//		addNewLine();
//		try {
//			generateTitle("Test Poem");
//		} catch (PoemStringLengthException e) {
//			e.printStackTrace();
//		}
//		System.out.println(builder);
	}
	
	public void addNewLine() {
		PoemString newline = new PoemString();
		for(int i = 0; i < TXT_LENGTH; i++) {
			try {
				newline.appendString(" ");
			} catch (PoemStringLengthException e) {
				//This case should never exist
				e.printStackTrace();
			}
		}
		builder.add(newline.toString());
	}
	
	public void addLine(PoemString string) {
		builder.add(string.toString());
	}
 	
	/**
	 * Generates a tonne of PoemStrings and adds it to the builder. Adds a new line at the end.
	 * @param str
	 */
	public void addParagraph(String str) {
		
	}
	
	/**
	 * Creates a title. Always adds to beginning of builder
	 * @throws PoemStringLengthException 
	 */
	public void generateTitle(String title) throws PoemStringLengthException {
		
		//Title block
		String[] titleBlock = new String[3];
		
		//Header and footer (These don't follow the rules of a PoemString
		StringBuilder titleHeader = new StringBuilder("/");
		StringBuilder titleFooter = new StringBuilder("/");
		for(int i = 0; i < STR_LENGTH - 1; i++) {
			titleHeader.append("*");
			titleFooter.insert(0, "*");
		}
		
		titleBlock[0] = titleHeader.toString();
		titleBlock[2] = titleFooter.toString();
		
		//Title itself		
		PoemString poemString = new PoemString(title);
		poemString.center();
		titleBlock[1] = poemString.toString();
		
		
		builder.addAll(0, Arrays.asList(titleBlock));
	}
	
	public List<String> getBuilder() {
		return builder;
	}
	
	public void build() {
		StringBuilder strBuilder = new StringBuilder("");
		for(int i = 0; i < STR_LENGTH; i++) {
			strBuilder.append("*");
		}
		builder.add(strBuilder.toString());
	}
	
	public void printBuilder() {
		builder.forEach(System.out::println);
	}
}
