package io.github.skepter.poemcreator;

/**
 * Creates poems. Stitches together the results of PoemBuilder to create 4
 * column poem results. Implements the secret.
 * 
 * @author Jorel
 *
 */
public class PoemCreator {

	public static void main(String[] a) {
		PoemBuilder builder = new PoemBuilder();
		try {
			builder.generateTitle("title");
			builder.addLine(new PoemString("this is simple"));
			builder.build();
			builder.printBuilder();
		} catch (PoemStringLengthException e) {
			e.printStackTrace();
		}
		
		
	}

	public PoemCreator() {
		// Requirements for a poem:
		/*
		 * Title
		 * 
		 * TitleShown (optional - depends if title should be shown)
		 * 
		 * Author (optional)
		 * 
		 * String[] or List<String> containing "body" of the text
		 * 
		 * String[] or List<String> containing the secret
		 */

	}

}
