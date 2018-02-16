package io.github.skepter.poemcreator;

/**
 * Creates poems. Stitches together the results of PoemBuilder to create 4
 * column poem results. Implements the secret.
 * 
 * @author Jorel
 *
 */
public class PoemCreator {

	//TODO GUI?
	
	public static void main(String[] a) {
		
		//Testing the raw PoemBuilder class
//		PoemBuilder builder = new PoemBuilder();
//		try {
//			//Tests title generation
//			builder.generateTitle("title");
//
//			//Adds a basic paragraph
//			builder.addParagraph("This is an insanely super long abnormal string which has length of over "
//					+ "twenty nine characters just to test the functionality of my paragraph function");
//
//			//Tests insane long word
//			try {
//				builder.addParagraph("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//			} catch (PoemStringLengthException e) {
//				//Ignore, this is just a test
//				System.out.println("Long word exception caught\n");
//			}
//			
//			//Test author generation
//			builder.generateAuthor("Jorel Ali");
//			builder.build();
//			builder.printResultingColumn();
//		} catch (PoemStringLengthException e) {
//			e.printStackTrace();
//		}
		
		//Using the PoemCreator constructor
		new PoemCreator("Title", false, "Jorel Ali", "This is an insanely super long abnormal string which has length of over "
				+ "twenty nine characters just to test the functionality of my paragraph function", new String[] {"has", "test"});
			
	}

	/**
	 * Creates a poem. All sanity checks are produced during "production" (this
	 * allows ease of determining errors when it "is compiled")
	 * 
	 * @param title
	 * @param showTitle
	 * @param author
	 *            If null, author won't be generated
	 * @param body
	 * @param secret
	 */
	public PoemCreator(String title, boolean showTitle, String author, String body, String[] secret) {
		PoemBuilder builder = new PoemBuilder();
		// Title
		try {
			// showTitle is dealt with in this class
			builder.generateTitle(title);
		} catch (PoemStringLengthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Author
		if (author != null) {
			try {
				builder.generateAuthor(author);
			} catch (PoemStringLengthException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Implementing the body into the poem
		String[] paragraphs = body.split("\n");
		for (String str : paragraphs) {
			try {
				builder.addParagraph(str);
			} catch (PoemStringLengthException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Sanity check for secret embedding
		int lastIndex = 0;
		for (String str : secret) {
			if (body.indexOf(str, lastIndex) == -1) {
				try {
					throw new SecretSanityException(str, lastIndex);
				} catch (SecretSanityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				lastIndex = body.indexOf(str, lastIndex);
			}
		}

		// Poem has been created, all checks dealt with.
		builder.build();

		String leftColumn = builder.getResultingColumn();
		String rightColumn = builder.getResultingColumn();
		
		/*
		 * Since the builder doesn't "self destruct" from the building process,
		 * we can add methods to the builder to aid in the analysis of it
		 */

		/*
		 * Basic idea of secret embedding:
		 * Add a single space before each secret in the left column.
		 * Add a single space after each secret in the right column.
		 * 
		 * Deal with "dual secrets" ('the world') later.
		 * When adding a space to each column, remove a space from * at the end of line.
		 */
		
	}

}
