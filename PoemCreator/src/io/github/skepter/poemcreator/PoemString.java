package io.github.skepter.poemcreator;

class PoemString {

	//Testing justification
	public static void main(String[] a) {
		PoemString aa = new PoemString();
		try {
			aa.appendString("hello world how");
		} catch (PoemStringLengthException e) {
			e.printStackTrace();
		}
		aa.justify();
		System.out.println(aa);
	}
	
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

	public void appendSpecialString(String str, String secret) throws PoemStringLengthException {
		
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
	 * 1. Take the total collumn space of each line which is equal to the number
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
		
		int spaces = r / numWordsMinusOne;
		
		//TODO implement this justification
		System.out.println(spaces);
	}
	
	public boolean isEmpty() {
		return strBuilder.length() == 0;
	}

	@Override
	public String toString() {
		StringBuilder resultant = new StringBuilder(strBuilder);
		for (int i = resultant.length(); i < PoemBuilder.TXT_LENGTH; i++) {
			resultant.append(" ");
		}
		resultant.insert(0, "*  ");
		resultant.append("  *");
		return resultant.toString();
	}
}
