package io.github.skepter.poemcreator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Class for building a poem. Simple adds text to the poem.
 * Creates a simple single column poem a line at a time.
 * 
 * @author Jorel
 */
public class PoemBuilder {

	public final static int STR_LENGTH = 29;
	//2 for borders, 2 for left padding, 2 for right padding
	public final static int TXT_LENGTH = STR_LENGTH - 6;
	private List<String> builder;
	
	private boolean hasTitle;
	
	public PoemBuilder() {
		builder = new ArrayList<>();
		hasTitle = false;
	}
	
	public void addNewLine() {
		addNewLine(builder.size());
	}
	
	private void addNewLine(int index) {
		PoemString newline = new PoemString();
		for(int i = 0; i < TXT_LENGTH; i++) {
			try {
				newline.appendString(" ");
			} catch (PoemStringLengthException e) {
				//This case should never exist
				e.printStackTrace();
			}
		}
		builder.add(index, newline.toString());
	}
	
	private void addLine(PoemString string) {
		builder.add(string.toString());
	}
 	
	/**
	 * Generates a tonne of PoemStrings and adds it to the builder. Adds a new line at the end.
	 * @param str
	 * @throws PoemStringLengthException 
	 */
	public void addParagraph(String str) throws PoemStringLengthException {
		
		List<String> words = Arrays.asList(str.split(" "));
		Iterator<String> it = words.iterator();
		PoemString currentPoemString = new PoemString();
		
		//For each word in words
		while(it.hasNext()) {
			String nextWord = it.next();
			//Add it to the currentPoemString as a "new string"
			if(currentPoemString.isEmpty()) {
				try {
					currentPoemString.appendString(nextWord);
					currentPoemString.appendString(" ");
				} catch(PoemStringLengthException e) {
					/* ~SPECIAL_CASE~
					 * If this case occurs, there's a word which cannot be fit
					 * in a single PoemString. Ideally, this case should never
					 * occur. Since we don't know how to deal with it yet (i.e.
					 * I haven't decided on dealing with hyphenation or allowing
					 * a modified change for TXT_LENGTH, I'm just going to throw
					 * this exception
					 */
					throw e;
				}
			} else {
				try {
					currentPoemString.appendString(nextWord);
					currentPoemString.appendString(" ");
				} catch(PoemStringLengthException e) {
					//We're now full up, let's create a new PoemString and write
					//to our "buffer" (list)
					addLine(currentPoemString);
					try {
						currentPoemString = new PoemString(nextWord);
						currentPoemString.appendString(" ");
					} catch (PoemStringLengthException e1) {
						// See ~SPECIAL_CASE~ above
						throw e;
					}
				}
			}
		}

		//Adds final poemString
		addLine(currentPoemString);
		
		//Prints a new line :)
		addNewLine();
	}
	
	/**
	 * Author generator. Designed to be generated at the top of the poem
	 * @param author
	 * @throws PoemStringLengthException 
	 */
	public void generateAuthor(String author) throws PoemStringLengthException {
		PoemString authorStr = new PoemString("Created by " + author);
		authorStr.center();

		if (hasTitle) {
			addNewLine(3);
			builder.add(4, authorStr.toString());
			addNewLine(5);
		} else {
			// This case shouldn't occur "in production"
			addNewLine(0);
			builder.add(1, authorStr.toString());
			addNewLine(2);
		}
	}
	
	/**
	 * Creates a title. Always adds to beginning of builder
	 * @param title
	 * @param show - Whether the title should be explicitly shown
	 * @throws PoemStringLengthException
	 */
	public void generateTitle(String title, boolean show) throws PoemStringLengthException {
		//Prevent generating the title multiple times
		if(hasTitle) {
			return;
		}
		hasTitle = true;
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
