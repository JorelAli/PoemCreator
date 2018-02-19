package io.github.skepter.poemcreator;

class PoemString {
	
	private StringBuilder strBuilder;
	
	public PoemString() {
		strBuilder = new StringBuilder();
	}
	
	public PoemString(String inputStr) throws PoemStringLengthException {
		if (inputStr.length() > PoemBuilder.TXT_LENGTH) {
			throw new PoemStringLengthException(inputStr);
		}
		strBuilder = new StringBuilder(inputStr);
	}
	
	/**
	 * Adds a string to the PoemString
	 * 
	 * @param string
	 *            The string to add to PoemString
	 * @throws PoemStringLengthException
	 *             if a string CANNOT be added (doesn't add the string to
	 *             PoemString)
	 */
	public void appendString(String string) throws PoemStringLengthException {
		if (strBuilder.length() + string.length() > PoemBuilder.TXT_LENGTH) {
			throw new PoemStringLengthException(string);
		} else {
			strBuilder.append(string);
		}
	}

	/**
	 * Centers the string in the PoemString
	 */
	public void center() {
		int spacesNeeded = (PoemBuilder.TXT_LENGTH - strBuilder.length()) / 2;
		for (int i = 0; i < spacesNeeded; i++) {
			strBuilder.insert(0, " ");
		}
	}
	
	/**
	 * "Justification algorithm" found from this forum post:
	 * https://forums.anandtech.com/threads/java-full-justify-text-algorithm.1001858/#post-6839800
	 * 
	 * 1. Take the total column space of each line which is equal to the number
	 * of characters that will fit on a line. 
	 * 
	 * 2. Minus the number of characters
	 * in the words on that line. 
	 * 
	 * 3. Divide that by the number of words on that
	 * line minus 1 
	 * 
	 * 4. This gives you the number of spaces to append to each
	 * word EXCEPT for the last one.
	 */
	public void justify() {
		int columnSpace = PoemBuilder.TXT_LENGTH;
		int numCharacters = strBuilder.toString().replace(" ", "").length();
		int r = columnSpace - numCharacters;
		int numWordsMinusOne = strBuilder.toString().split(" ").length - 1;
		
		//Prevent division by 0
		if(numWordsMinusOne == 0) {
			return;
		}
		
		int spaces = r / numWordsMinusOne;
		
		//Personal preference: 5 or more spaces in the middle looks weird
		if(spaces >= 5) {
			return;
		}
		
		String strBuilderResult = strBuilder.toString();
		
		//Remove excess whitespace at the end
		strBuilderResult = strBuilderResult.trim();
		
		//Generate a string with the number of spaces
		StringBuilder spacesBuilder = new StringBuilder();
		for(int i = 0; i < spaces; i++) {
			spacesBuilder.append(" ");
		}
		
		//Incorporate the spaces
		strBuilderResult = strBuilderResult.replace(" ", spacesBuilder.toString());
		
		/*
		 * This produces unreliable results where certain words lose their 3D
		 * effect. This is due to left/right columns not retaining their
		 * differences if the word is at the end.
		 * 
		 * (Also, the end result doesn't look as good)
		 */
		
//		//If the length isn't maximal, adjust the last word to make it so
//		if(strBuilderResult.length() < columnSpace && numWordsMinusOne != 0) {
//			int spacesToAddToEnd = columnSpace - strBuilderResult.length();
//			
//			StringBuilder spacesEndBuilder = new StringBuilder();
//			for(int i = 0; i < spacesToAddToEnd; i++) {
//				spacesEndBuilder.append(" ");
//			}
//			
//			int lastSpaceIndex = strBuilderResult.trim().lastIndexOf(" ");
//			if(lastSpaceIndex != -1) {
//				StringBuilder result = new StringBuilder(strBuilderResult);
//				result.insert(lastSpaceIndex, spacesEndBuilder.toString());
//				strBuilderResult = result.toString();
//			}
//		}
		
		strBuilder = new StringBuilder(strBuilderResult);
	}
	
	public boolean isEmpty() {
		return strBuilder.length() == 0;
	}

	public String toString(boolean justified) {
		if(justified) {
			justify();
		}
		StringBuilder resultant = new StringBuilder(strBuilder);
		for (int i = resultant.length(); i < PoemBuilder.TXT_LENGTH; i++) {
			resultant.append(" ");
		}
		resultant.insert(0, "*  ");
		resultant.append("  *");
		return resultant.toString();
	}
}
