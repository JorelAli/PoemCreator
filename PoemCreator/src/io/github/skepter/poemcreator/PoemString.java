package io.github.skepter.poemcreator;

class PoemString {

	StringBuilder strBuilder;

	public PoemString() {
		strBuilder = new StringBuilder();
	}

	public PoemString(String inputStr) throws PoemStringLengthException {
		if (inputStr.length() > PoemBuilder.TXT_LENGTH) {
			throw new PoemStringLengthException();
		}
		strBuilder = new StringBuilder(inputStr);
	}

	public void appendString(String string) throws PoemStringLengthException {
		if (strBuilder.length() + string.length() > PoemBuilder.TXT_LENGTH) {
			throw new PoemStringLengthException();
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

	@Override
	public String toString() {
		for (int i = strBuilder.length(); i < PoemBuilder.TXT_LENGTH; i++) {
			strBuilder.append(" ");
		}
		strBuilder.insert(0, "*  ");
		strBuilder.append("  *");
		return strBuilder.toString();
	}
}
