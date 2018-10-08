In the Dictionary tree methods the following methods do the following:

	*insert: recursively puts the first letter into the BST and calls the rest of the word.

	*insertHelper: recursively puts the first letter into the BST and calls the rest of the word and if it has not got a popularity, then it will add the word if it is not there. Otherwise it will delete the poularity.

	*insert: applies insertHelper, than uses the previous insert method to add the word if needed.

	*removeLastLetter: recersively goes down the tree until it finds the last letter. If it is not a leaf it deletes that node.

	*removeLetters: Continuously removes the last letter of a specified word from this BST with the removeLastLetter until it reaches the first letter.
	
	*removeHelper: uses the previous method (removerLetters) and if the first letter is a leaf it removes it
	
	*remove: If there is no word it returns false, otherwise it returns the removerHelper method. 

	*containsHelper: goes down the BST and and checks letter by letter if it's there .

	*contains: If there is no word it returns false, otherwise it returns the containsHelper method. 
	
	*predict:receives a string from the predictToString method and return it as an Optional.

	*predictToString: goes down the binary tree and adds each letter to a string and when it cannot find anymore of the prefixes letters it continues to add the letters to the string.

	*numberOfLeavesHelper: checks the BST if a node is a leaf by using the isLeaf method.

	*numLeaves: If there is no word inserted then it returns 1, otherwise it returns the numberOfLeavesHelper method. 

	*isLeaf: checks the number of children the node has.

	*maximumBranchingHelper: goes through every node and returns the maximum number of children found.

	*maximumBranching:  If there is no word inserted then it returns 1, otherwise it returns the maximumBranchingHelper method. 

	*height: goes down the tree and adding 1 to every level it passes.

	*size: goes through every node and returns the total number of children found.

	*longestWordHelper: Goes down the BST and stores each word in currentWord and stores the longest in longestWord.

	*longestWord: returns the string from the longestWordHelper method does but with no parameters.
	
	*allWordsHelper: goes down every path storing the nodes in a string and when it has found a leaf it adds it to the arrayList.

	*allWords: returns the list from the allWordsHelper method does but with no parameters.
	
	Link to repository: https://git.cs.bham.ac.uk/axe749/Coursework.git