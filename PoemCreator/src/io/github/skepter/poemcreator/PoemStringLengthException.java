package io.github.skepter.poemcreator;
class PoemStringLengthException extends Exception {
		
		private static final long serialVersionUID = 687842078405138596L;

		public PoemStringLengthException() {
			super("String cannot be longer than " + PoemBuilder.TXT_LENGTH + " characters!");
		}		
	}