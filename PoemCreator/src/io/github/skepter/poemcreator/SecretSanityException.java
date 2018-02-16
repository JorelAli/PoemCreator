package io.github.skepter.poemcreator;
class SecretSanityException extends Exception {
		
	private static final long serialVersionUID = -1902610260122700593L;

		public SecretSanityException(String string, int index) {
			super("String '" + string + "' was not found in the body after index " + index + "!");
		}		
	}