package io.github.skepter.poemcreator;

class PoemString {

	StringBuilder strBuilder;
	
	

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
