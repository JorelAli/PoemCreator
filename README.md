# PoemCreator
A program to help create special poems

## What is the PoemCreator?
The PoemCreator is a program I wrote which utilises the 3D property of [stereograms](https://en.wikipedia.org/wiki/Stereoscopy) to embed secret messages into text. (See [hidden-3d.com](http://hidden-3d.com/index.php) for more examples on 3D stereograms). This program in particular is a form of [ASCII stereogram](https://en.wikipedia.org/wiki/ASCII_stereogram) which generates "text emphasis" stereograms which embed a secret message into a larger body of text.

## Screenshots
![text](https://raw.githubusercontent.com/Skepter/PoemCreator/master/PoemCreator/poemcreator.JPG "logo")

## Basic Usage
* Download the latest .jar file from the [latest releases page](https://github.com/Skepter/PoemCreator/releases/latest)
* Add a title and your name as the author
* Write your text in the main box below. Adding a new line adds a paragraph to the final result
* Add the list of 3D words in the box below. These must match the words in the main text EXACTLY (case sensitive).
* Press GENERATE POEM to generate your poem
* The poem result will appear in the _Peom Output_ box. You can copy your poem to the clipboard by pressing _Copy poem to clipboard_

## Optional Usage
* Check the _Show Title in 3D_ box to show the title in 3D
* Check the _Show Author in 3D_ box to show the author's name in 3D
* Check the _Generate 4 column poem (as opposed to 2 columns)_ box to display four columns in the poem output. This still adds the words in the center column when viewed, but for inexperienced viewers, this can be more preferable.

## Justification (v2.0 onwards)
Version 2.0 of the PoemCreator introduces a feature called "justification". This justifies the text (as best as possible) to help disguise words better (without justification, it may be easy to see which words have extra spaces between them, making them stand out without even viewing it as a stereogram).

### Issues with justification and why it's actually impossible to implement it perfectly
Basically, when justified, it modifies the spaces between words so they fill up the designated column. By doing this, it makes the text seem "cleaner" and more uniform. However, to make a word 3D at the beginning of a line, a space is required before said word. In order to mask this word, justification prevents such a space from appearing **(this means that 3D words at the beginning of a line with justification mode will be displayed incorrectly).** This is implicitly shown on the [wikipedia page](https://en.wikipedia.org/wiki/ASCII_stereogram) where the example at the bottom of the page does not use 3D words at the beginning or end of a line.

In addition, the justification system used in this program is not _perfect_ (for example, compared to Microsoft Word) due to the fact that the characters are written using ASCII and each letter has a specified width and some lines cannot be evenly distributed with equal number of spaces. I attempted to resolve this by ensuring the last words always line up at the end of the line, however this produced an undesired effect (3D words at the end of a row would not be viewed because the left and right columns would align perfectly, thus removing the 3D effect).

Therefore, based on the issues stated above, allowing words to be displayed at the beginning and end of a line whilst maintaining perfect justification (both the beginning and end of rows are in-line) is impossible.

### To justify or not to justify?
This program was created to help aid in the laborious creation of ASCII stereograms. It in no way creates a perfect 3D stereogram straight away. For inexperienced viewers who want to create a quick hidden message to fool their friends, this program is perfect. For those looking for a perfect result, this program is a stepping stone to help reduce the amount of time adjusting spaces manually (by generating most of it for you).

The choice of justification is up to you, if you can tweak your main text to get around the issues of justification, then that's great. If you're not worried if people can determine hidden words by staring at it for long enough (without viewing it in 3D) and counting spaces, don't justify.
