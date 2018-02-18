package io.github.skepter.poemcreator;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Creates poems. Stitches together the results of PoemBuilder to create 4
 * column poem results. Implements the secret.
 * 
 * @author Jorel
 *
 */
public class PoemCreator {

	//TODO GUI?
	
	private String result;
	
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
		new PoemCreator("Title", false, "Jorel Ali", true, "This is an insanely super long abnormal string which has length of over "
				+ "twenty nine characters just to test the functionality of my paragraph function", new String[] {"This", "string", "functionality"});
			
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
	public PoemCreator(String title, boolean showTitle, String author, boolean showAuthor, String body, String[] secret) {
		PoemBuilder builder = new PoemBuilder();
		// Title
		try {
			// showTitle is dealt with in this class
			builder.generateTitle(title, showTitle);
		} catch (PoemStringLengthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Author
		if (author != null) {
			try {
				builder.generateAuthor(author, showAuthor);
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
		
		/*
		 * Convert Secret[] into a Queue Basically, I plan to implement a queue
		 * system, where when the next available secret word is found in the
		 * body, it will be dequeued, so you don't have to mess with indices and
		 * stuff
		 */
		Queue<String> secretQueue = new LinkedList<>();
		for(String str : secret) {
			secretQueue.add(str);
		}
		
		//secretQueue.poll();
		
		// Implementing the body into the poem
		String[] paragraphs = body.split("\n");
		for (String str : paragraphs) {
			try {
				//Ensures that the queue continually updates when consumed
				secretQueue = builder.addParagraph(str, secretQueue);
			} catch (PoemStringLengthException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		

		// Poem has been created, all checks dealt with.
		builder.build();
		
		result = builder.getResultingColumns();
		
		
		//builder.printResultingColumns();
//
//		String leftColumn = builder.getResultingColumnL();
//		String rightColumn = builder.getResultingColumnL();
		
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
	
	public String getResult() {
		return result;
	}

}
