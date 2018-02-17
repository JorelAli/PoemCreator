package io.github.skepter.poemcreator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

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
	
	//Left and right columns
	private List<String> builderL;
	private List<String> builderR;
	
	private boolean hasTitle;
	private boolean hasBeenBuilt;
	
	//An index of what line the poem starts at (normally 6)
	private int poemStartingIndex;
	
	/*
	 * Once a poem has been created, it doesn't need to be rebuilt or
	 * re-compiled (to its resulting form).
	 */
	private String cachedResultL;
	private String cachedResultR;
	
	private static String NEW_LINE;
	
	public PoemBuilder() {
		builderL = new ArrayList<>();
		builderR = new ArrayList<>();
		hasTitle = false;
		hasBeenBuilt = false;
		cachedResultL = null;
		cachedResultR = null;
		
		setPoemStartingIndex(0);
		
		PoemString newline = new PoemString();
		for(int i = 0; i < TXT_LENGTH; i++) {
			try {
				newline.appendString(" ");
			} catch (PoemStringLengthException e) {
				//This case should never exist
				e.printStackTrace();
			}
		}
		NEW_LINE = newline.toString();
	}
	
	/* New line functions */
	
	public void addNewLine() {
		addNewLine(builderL.size());
		addNewLine(builderR.size());
	}
	
	private void addNewLine(int index) {
		builderL.add(index, NEW_LINE);
		builderR.add(index, NEW_LINE);
	}
		
	private void addLineL(PoemString string) {
		builderL.add(string.toString());
	}
	
	private void addLineR(PoemString string) {
		builderR.add(string.toString());
	}
	
	public boolean isNewLine(String line) {
		return line.replace("\n", "").equals(NEW_LINE);
	}

	/* End of new line functions */
	
	/**
	 * Generates a tonne of PoemStrings and adds it to the builder. Adds a new line at the end.
	 * @param str
	 * @param secretQueue 
	 * @throws PoemStringLengthException 
	 */
	public Queue<String> addParagraph(String str, Queue<String> secretQueue) throws PoemStringLengthException {
		
		List<String> words = Arrays.asList(str.split(" "));
		Iterator<String> it = words.iterator();
		
		PoemString currentPoemStringL = new PoemString();
		PoemString currentPoemStringR = new PoemString("");
		
		String secretWord = secretQueue.peek();
		
		//For each word in words
		while(it.hasNext()) {
			String nextWord = it.next();
			boolean addSecretWord = false;
			if(nextWord.equals(secretWord)) {
				//Because we CAN implement it, we can remove it from the queue
				addSecretWord = true;
				secretQueue.poll();
			}
			
			//Add it to the currentPoemString as a "new string"
			if(currentPoemStringL.isEmpty()) {
				try {
					if(addSecretWord) {
						currentPoemStringL.appendString(" " + nextWord);
						currentPoemStringR.appendString(nextWord + " ");
					} else {
						currentPoemStringL.appendString(nextWord);
						currentPoemStringR.appendString(nextWord);
					}
					
					//Spaces between words
					currentPoemStringL.appendString(" ");						
					currentPoemStringR.appendString(" ");
					
				} catch(PoemStringLengthException e) {
					/* ~SPECIAL_CASE~
					 * If this case occurs, there's a word which cannot be fit
					 * in a single PoemString. Ideally, this case should never
					 * occur. Since we don't know how to deal with it yet (i.e.
					 * I haven't decided on dealing with hyphenation or allowing
					 * a modified change for TXT_LENGTH, I'm just going to throw
					 * this exception
					 */
					e.printStackTrace();
				}
			} else {
				try {
					currentPoemStringL.appendString(nextWord);
					currentPoemStringL.appendString(" ");
					
					currentPoemStringR.appendString(nextWord);
					currentPoemStringR.appendString(" ");
				} catch(PoemStringLengthException e) {
					//We're now full up, let's create a new PoemString and write
					//to our "buffer" (list)
					addLineL(currentPoemStringL);
					addLineR(currentPoemStringR);
					try {
						currentPoemStringL = new PoemString(nextWord);
						currentPoemStringL.appendString(" ");
						
						currentPoemStringR = new PoemString(nextWord);
						currentPoemStringR.appendString(" ");
					} catch (PoemStringLengthException e1) {
						// See ~SPECIAL_CASE~ above
						e.printStackTrace();
					}
				}
			}
			
			//Update secretWord
			secretWord = secretQueue.peek();
		}
		
		//Adds final poemString
		addLineL(currentPoemStringL);
		addLineR(currentPoemStringR);
		
		//Prints a new line :)
		addNewLine();
				
		return secretQueue;
	}
	
	/**
	 * TODO multi-line author/titles
	 * Author generator. Designed to be generated at the top of the poem
	 * @param author
	 * @throws PoemStringLengthException 
	 */
	public void generateAuthor(String author) throws PoemStringLengthException {
		PoemString authorStr = new PoemString("Created by " + author);
		authorStr.center();

		if (hasTitle) {
			addNewLine(3);
			builderL.add(4, authorStr.toString());
			builderR.add(4, authorStr.toString());
			addNewLine(5);
			setPoemStartingIndex(6);
		} else {
			// This case shouldn't occur "in production"
			addNewLine(0);
			builderL.add(1, authorStr.toString());
			builderR.add(1, authorStr.toString());
			addNewLine(2);
			setPoemStartingIndex(3);
		}
	}
	
	/**
	 * Creates a title. Always adds to beginning of builder
	 * @param title
	 * @throws PoemStringLengthException
	 */
	public void generateTitle(String title) throws PoemStringLengthException {
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
		
		
		builderL.addAll(0, Arrays.asList(titleBlock));
		builderR.addAll(0, Arrays.asList(titleBlock));
	}
	
	/**
	 * Adds finishing line to poem
	 */
	public void build() {
		if(hasBeenBuilt) {
			//Has already been built, cannot rebuild
			System.out.println("You cannot rebuild a poem"); //TODO exception?
			return;
		}
		hasBeenBuilt = true;
		StringBuilder strBuilder = new StringBuilder("");
		for(int i = 0; i < STR_LENGTH; i++) {
			strBuilder.append("*");
		}
		builderL.add(strBuilder.toString());
		builderR.add(strBuilder.toString());
		cachedResultL = getResultingColumnL();
		cachedResultR = getResultingColumnR();
	}
	
	public void printResultingColumn() {
		String[] leftColumn = getResultingColumnL().split("\n");
		String[] rightColumn = getResultingColumnR().split("\n");
		for(int i = 0; i < leftColumn.length; i++) {
			System.out.println(leftColumn[i] + " " + rightColumn[i]);
		}
	}
	
	public String getResultingColumnL() {
		if(cachedResultL == null) {
			StringBuilder strBuilder = new StringBuilder();
			builderL.forEach(str -> {
				strBuilder.append(str + "\n");
			});
			return strBuilder.toString();
		} else {
			return cachedResultL;
		}
	}
	
	public String getResultingColumnR() {
		if(cachedResultR == null) {
			StringBuilder strBuilder = new StringBuilder();
			builderR.forEach(str -> {
				strBuilder.append(str + "\n");
			});
			return strBuilder.toString();
		} else {
			return cachedResultR;
		}
	}

	public int getPoemStartingIndex() {
		return poemStartingIndex;
	}

	//Good grief *Rolls eyes* These setter generators...
	private void setPoemStartingIndex(int poemStartingIndex) {
		this.poemStartingIndex = poemStartingIndex;
	}
}
