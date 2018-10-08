import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

public class DictionaryTree {

	public Map<Character, DictionaryTree> children = new LinkedHashMap<>();
	// Verifies if a word can be removed
	private boolean checkRemoved = false;
	// Use once to add the popularity to the word
	private int times = 1;
	// Used in predictToString as the first letter of a certain string
	private char firstChar = ' ';
	// Used to store the a string without the first character in predictToString
	private String restOfPrefix = "";
	// The maximum branch found
	private static int maxBranch = 0;

	/**
	 * Inserts the given word into this dictionary. If the word already exists,
	 * nothing will change.
	 *
	 * @param word
	 *            the word to insert
	 */
	void insert(String word) {
		if (word.equals("")) {
			return;
		}
		char firstChar = word.charAt(0);
		String restOfWord = word.substring(1);

		if (children.containsKey(firstChar)) {
			children.get(firstChar).insert(restOfWord);
			return;
		}

		DictionaryTree tree = new DictionaryTree();
		tree.insert(restOfWord);
		children.put(firstChar, tree);

	}

	/**
	 * Deletes the previous popularity if found
	 */
	void insertHelper(String word, int popularity) {
		assert (word != null && word.length() > 0);
		if (times == 1) {
			word += popularity;
			times++;
		}
		if (word.equals("")) {
			return;
		}
		char firstChar = word.charAt(0);
		String restOfWord = word.substring(1);
		for (Map.Entry<Character, DictionaryTree> e : children.entrySet()) {
			if (firstChar == e.getKey()) {
				e.getValue().insertHelper(restOfWord, popularity);
			}
			if (Character.isDigit(e.getKey())) {
				children.remove(e.getKey());
			}
		}
		DictionaryTree tree = new DictionaryTree();
		children.put(firstChar, tree);
		tree.insert(restOfWord);
	}

	/**
	 * Inserts a word and a prediction
	 */
	void insert(String word, int prediction) {
		assert (word != null && word.length() > 0);
		insertHelper(word, prediction);
		insert(word + prediction);
	}

	/**
	 * Sets the list
	 */
	public void setList(ArrayList<String> list) {
	}

	/**
	 * Removes the last letter of a specified word from this dictionary.
	 */
	void removeLastLetter(String word) {
		if (word.length() == 0)
			return;
		char firstChar = word.charAt(0);
		String restOfWord = word.substring(1);
		for (Map.Entry<Character, DictionaryTree> e : children.entrySet()) {
			if (firstChar == e.getKey() && isLeaf(e.getValue())) {
				children.remove(e.getKey(), e.getValue());
				e.getValue().removeLastLetter(restOfWord);
				break;
			} else
				e.getValue().removeLastLetter(restOfWord);
		}
	}

	/**
	 * Continuously removes the last letter of a specified word from this dictionary
	 * until it gets to the first letter.
	 */
	void removeLetters(String word) {
		if (word.length() == 0)
			return;
		int i = 0;
		String a = "";
		while (i < word.length() - 1) {
			a = word.substring(0, word.length() - i);
			removeLastLetter(a);
			i++;
		}
	}


	/**
	 * 
	 * @param word The word that is removed if possible
	 * @return	checkRemoved true if the word was removed, false if not  
	 */
	boolean removeHelper(String word) {
		removeLetters(word);
		char firstChar = word.charAt(0);
		for (Map.Entry<Character, DictionaryTree> e : children.entrySet()) {
			if (firstChar == e.getKey() && isLeaf(e.getValue())) {
				children.remove(e.getKey(), e.getValue());
				checkRemoved = true;
				break;
			}
		}
		return checkRemoved;

	}

	/**
	 * Removes the specified word from this dictionary. Returns true if the caller
	 * can delete this node without losing part of the dictionary, i.e. if this node
	 * has no children after deleting the specified word.
	 *
	 * @param word
	 *            the word to delete from this dictionary
	 * @return whether or not the parent can delete this node from its children
	 */
	boolean remove(String word) {
		if(word.length()==0)
			return false;
		else return removeHelper(word);
	}
	
	//Checks letter by letter if the word is in the tree
	boolean containsHelper(String word) {
		if (word.length() == 0)
			return true;
		char firstChar = word.charAt(0);
		String restOfWord = word.substring(1);
		for (Map.Entry<Character, DictionaryTree> e : children.entrySet()) {
			if (firstChar == e.getKey())
				return e.getValue().containsHelper(restOfWord);
		}
		return false;

	}

	/**
	 * Determines whether or not the specified word is in this dictionary.
	 *
	 * @param word
	 *            the word whose presence will be checked
	 * @return true if the specified word is stored in this tree; false otherwise
	 */
	boolean contains(String word) {
		if(word.length()==0)
			return false;
		else 
			return containsHelper(word);
	}
	/**
	 * @param prefix
	 *            the prefix of the word returned
	 * @return a word that starts with the given prefix, or an empty optional if no
	 *         such word is found.
	 */
	Optional<String> predict(String prefix) {
		assert (prefix != null && prefix.length() > 0);
		if(prefix.length()>0)
		return Optional.ofNullable(predictToString(prefix)).filter(s -> !s.isEmpty());
		else return Optional.empty();

	}

	/**
	 * @param prefix
	 *            the prefix of the word returned
	 * @return wordWithPrefix a word that starts with the given prefix.
	 */
	String predictToString(String prefix) {
		int remainingLength = prefix.length();
		String wordWithPrefix = "";
		if (prefix.length() > 0) {
			firstChar = prefix.charAt(0);
			restOfPrefix = prefix.substring(1);
			remainingLength = restOfPrefix.length();
		}
		for (Map.Entry<Character, DictionaryTree> e : children.entrySet()) {
			if (firstChar == e.getKey() && remainingLength > 0) {
				return wordWithPrefix += e.getKey() + e.getValue().predictToString(restOfPrefix);
			} else if (remainingLength == 0) {
				return wordWithPrefix += e.getKey() + e.getValue().predictToString(restOfPrefix);
			}
		}
		return wordWithPrefix;
	}

	/**
	 * Predicts the (at most) n most popular full English words based on the
	 * specified prefix. If no word with the specified prefix is found, an empty
	 * Optional is returned.
	 *
	 * @param prefix
	 *            the prefix of the words found
	 * @param n
	 *            the popularity
	 * @return the (at most) n most popular words with the specified prefix
	 */
	List<String> predict(String prefix, int n) {
		throw new RuntimeException("DictionaryTree.predict not implemented yet");
	}

	/**
	 * Add one if the node has no children
	 * @return numOfLeaves the number of leaves
	 */
	int numberOfLeavesHelper() {
		int numOfLeaves = 0;
		for (DictionaryTree d : children.values()) {
			if (isLeaf(d)) {
				numOfLeaves++;
			}
			numOfLeaves += d.numberOfLeavesHelper();
		}
		return numOfLeaves;

	}

	/**
	 * @return numOfLeaves the number of leaves in this tree, i.e. the number of
	 *         words which are not prefixes of any other word.
	 */
	int numLeaves() {
		if(size()==1)
			return 1;
		else
			return numberOfLeavesHelper();
	}
	
	/**
	 * @return true if the node has children, false if not
	 */
	public boolean isLeaf(DictionaryTree d) {
		return (d.children.size() == 0);
	}

	//Goes through the tree and returns the highest number of children a node has 
	int maximumBranchingHelper() {
		assert (size() > 0);
		if (children.size() > maxBranch)
			maxBranch = children.size();
		for (DictionaryTree d : children.values()) {
			return d.maximumBranchingHelper();
		}
		return maxBranch;
	}

	/**
	 * @return maxBranch the maximum number of children held by any node in this
	 *         tree
	 */
	int maximumBranching() {
		if(size()==1)
			return 0;
		else
			return maximumBranchingHelper();
	}
	
	/**
	 * @return height the height of this tree, i.e. the length of the longest branch
	 */
	public int height() {
		assert (size() > 0);
		int height = -1;
		for (Map.Entry<Character, DictionaryTree> e : children.entrySet())
			height = Math.max(height, e.getValue().height());

		return 1 + height;
	}

	/**
	 * @return size the number of nodes in this tree
	 */
	public int size() {
		int size = 1;
		for (Map.Entry<Character, DictionaryTree> child : children.entrySet())
			size += child.getValue().size();

		return size;
	}

	// words the longest word from the in longestWord
	 String longestWordHelper(String currentWord,String longestWord) {
		assert (size() > 0);
		for (Map.Entry<Character, DictionaryTree> e : children.entrySet()) {
			if (isLeaf(e.getValue())) {
				currentWord = currentWord + e.getKey() + "";
				if((currentWord != null) && !currentWord.equals("")) {
				if(longestWord.length()<currentWord.length()) 
					longestWord = currentWord;
				}
			} else {
				currentWord = currentWord + e.getKey();
				longestWord = e.getValue().longestWordHelper(currentWord, longestWord);
				
			}
			currentWord = currentWord.substring(0, currentWord.length()-1);
		}
	return longestWord;
	}
	
	 //return what the longestWordHelper does but with no parameters
	 String longestWord() {
		 String theCurrentWord ="";
		 String theLongestWord = "";
		 return longestWordHelper(theCurrentWord, theLongestWord);
	 }
	 
	 //returns all the words into the array
	List<String> allWordsHelper(String currentWord,List<String> List ) {
		assert (size() > 0);
		for (Map.Entry<Character, DictionaryTree> e : children.entrySet()) {
			if (isLeaf(e.getValue())) {
				currentWord = currentWord + e.getKey() + "";
				if((currentWord != null) && !currentWord.equals("")) {
				List.add(currentWord);
				}
			} else {
				currentWord = currentWord + e.getKey();
				List = e.getValue().allWordsHelper(currentWord, List);
			}
			currentWord = currentWord.substring(0, currentWord.length()-1);
		}

		return List;
	}

	/**
	 * @return List all words stored in this tree as a list
	 */
	List<String> allWords() {
		List<String> aList = new ArrayList<>();
		return allWordsHelper("", aList);
	}

	/**
	 * Folds the tree using the given function. Each of this node's children is
	 * folded with the same function, and these results are stored in a collection,
	 * cResults, say, then the final result is calculated using f.apply(this,
	 * cResults).
	 *
	 * @param f
	 *            the summarising function, which is passed the result of invoking
	 *            the given function
	 * @param <A>
	 *            the type of the folded value
	 * @return the result of folding the tree using f
	 */
	<A> A fold(BiFunction<DictionaryTree, Collection<A>, A> f) {
		throw new RuntimeException("DictionaryTree.fold not implemented yet");
	}

}