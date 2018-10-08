import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class DictionaryTreeTests {


	@Test
	public void heightOfRootShouldBeZero() {
		DictionaryTree unit = new DictionaryTree();
		Assertions.assertEquals(0, unit.height());
	}

	@Test
	public void heightOfRootShouldBeFour() {
		DictionaryTree unit = new DictionaryTree();
		unit.insert("dogs");
		Assertions.assertEquals(4, unit.height());
	}
	
	@Test
	public void sizeOfRootShouldBeOne() {
		DictionaryTree unit = new DictionaryTree();
		Assertions.assertEquals(1, unit.size());
	}

	@Test
	public void sizeOfRootShouldBeTen() {
		DictionaryTree unit = new DictionaryTree();
		unit.insert("hi");
		unit.insert("dog");
		unit.insert("mind");
		Assertions.assertEquals(10, unit.size());
	}

	@Test 
	public void allWordsShoulbBeDot() {
		DictionaryTree unit = new DictionaryTree();
		unit.insert("dogs");
		unit.insert("dot");
		unit.remove("dogs");
		ArrayList<String> a = new ArrayList<>();
		a.add("dot");
		Assertions.assertEquals(a, unit.allWords());
	}
	
	@Test
	public void theBSTShouldContainTheInsertedWord() {
		DictionaryTree unit = new DictionaryTree();
		unit.insert("country");
		Assertions.assertEquals(true, unit.contains("country"));
	}
	
	@Test
	public void removeShouldBeTrue() {
		DictionaryTree unit = new DictionaryTree();
		unit.insert("word");
		Assertions.assertEquals(true, unit.remove("word"));
	}

	@Test
	public void removeShouldBeFalse() {
		DictionaryTree unit = new DictionaryTree();
		unit.insert("dogs");
		Assertions.assertEquals(false, unit.remove("cats"));
	}
	
	@Test
	public void removeShouldBeFalseIfThereIsNoWord() {
		DictionaryTree unit = new DictionaryTree();
		unit.insert("eat");
		Assertions.assertEquals(false, unit.remove(""));
	}

	@Test
	public void containsShouldBeFalse() {
		DictionaryTree unit = new DictionaryTree();
		unit.insert("word");
		Assertions.assertEquals(false , unit.contains("food"));
	}
	
	
	@Test 
	public void containsShouldBeTrue() {
		DictionaryTree unit = new DictionaryTree();
		unit.insert("word");
		Assertions.assertEquals(true, unit.contains("word"));
	}

	@Test 
	public void containsShouldBeFalseIfThereIsNoWord() {
		DictionaryTree unit = new DictionaryTree();
		unit.insert("word");
		Assertions.assertEquals(true, unit.contains("word"));
	}
	
	@Test
	public void predictShouldGiveWord() {
		DictionaryTree unit = new DictionaryTree();
		unit.insert("word");
		Assertions.assertEquals(Optional.ofNullable("word").filter(s -> !s.isEmpty()), unit.predict("wo"));
	}

	@Test
	public void predictShouldNotGiveWordForAnEmptyDictionaryTree() {
		DictionaryTree unit = new DictionaryTree();
		Assertions.assertEquals(Optional.ofNullable("").filter(s -> !s.isEmpty()), unit.predict("wo"));
	}

	@Test
	public void predictShouldNotGiveWord() {
		DictionaryTree unit = new DictionaryTree();
		unit.insert("yes");
		Assertions.assertEquals(Optional.ofNullable("").filter(s -> !s.isEmpty()), unit.predict("wo"));
	}
	@Test
	public void numberOfLeavesShouldBeTwo() {
		DictionaryTree unit = new DictionaryTree();
		unit.insert("banana");
		unit.insert("wolf");
		Assertions.assertEquals(2, unit.numLeaves());
	}

	@Test
	public void numberOfLeavesShouldBeOne() {
		DictionaryTree unit = new DictionaryTree();
		Assertions.assertEquals(1, unit.numLeaves());
	}
	
	@Test
	public void maximumBranchingShoulbBe2() {
		DictionaryTree unit = new DictionaryTree();
		unit.insert("banana");
		unit.insert("raft");
		Assertions.assertEquals(2, unit.maximumBranching());
	}

	@Test
	public void maximumBranchingShoulbBeZer0() {
		DictionaryTree unit = new DictionaryTree();
		Assertions.assertEquals(0, unit.maximumBranching());
	}
	
	@Test
	public void longestWordShouldBeBanana() {
		DictionaryTree unit = new DictionaryTree();
		unit.insert("banana");
		unit.insert("raft");
		unit.insert("top");
		Assertions.assertEquals("banana", unit.longestWord());
	}
	
	@Test
	public void longestWordShouldBeNothing() {
		DictionaryTree unit = new DictionaryTree();
		Assertions.assertEquals("", unit.longestWord());
	}

	@Test
	public void allWordsShoulbBe3() {
		DictionaryTree unit = new DictionaryTree();
		unit.insert("banana");
		unit.insert("raft");
		unit.insert("top");
		Assertions.assertEquals(3, unit.allWords().size());
	}
	
	@Test
	public void allWordsShoulbHaveBananaRaftAndTop() {
		DictionaryTree unit = new DictionaryTree();
		unit.insert("banana");
		unit.insert("raft");
		unit.insert("top");
		ArrayList<String> a = new ArrayList<>();
		a.add("banana");
		a.add("raft");
		a.add("top");
		Assertions.assertEquals(a, unit.allWords());
	}
	
	@Test
	public void allWordsShoulbBeEmpty() {
		DictionaryTree unit = new DictionaryTree();
		ArrayList<String> a = new ArrayList<>();
		Assertions.assertEquals(a, unit.allWords());
	}
	
	@Test
	public void theWordFoundInAllWordsShouldBeHad3() {
		DictionaryTree unit = new DictionaryTree();
		unit.insert("had", 2);
		unit.insert("had", 3);
		Assertions.assertEquals("had3", unit.allWords().get(0));
	}
}
