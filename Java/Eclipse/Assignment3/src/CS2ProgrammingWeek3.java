///////////////////////////////////////////
//
// Test frame for CS2 programming assignments
//   Created 12-10-2014 by Rick Leinecker
//
///////////////////////////////////////////

public class CS2ProgrammingWeek3 
{
	
	///////////////////////////////////////////
	//
	// Start of assignment code.
	//
	///////////////////////////////////////////
	
	/**
	 * Returns the last name, first name, and PID of the student.
	 * 
	 * This is required in order to get credit for the programming assignment.
	 */
	static String GetNameAndPID()
	{
		return( "Downey, Eric, e3118280");
	}
	
	//	Problem #1
	//	Given a string and a non-empty substring sub, compute recursively if at 
	//	least n copies of sub appear in the string somewhere, possibly with 
	//	overlapping. N will be non-negative.

	//	subCopies("catcowcat", "cat", 2) → true
	//	subCopies("catcowcat", "cow", 2) → false
	//	subCopies("catcowcat", "cow", 1) → true
	
	/**
	 * 
	 * @param str, sub, n
	 * 		String str contains the original string to be tested against
	 * 		String sub contains the string that is used to test against str
	 * 		int n specifies how many copies of sub to check for
	 * 
	 * @return
	 * 		returns true if there are n copies of sub in str
	 * 		returns false if there are not n copies of sub in str
	 */
	static boolean subCopies(String str, String sub, int n) 
	{		
		boolean check = false;
		String testSub;
		int number = 0;
		int stopper = sub.length() - 1;

			for (int i = 0; i < str.length() - stopper; i++) { 
				testSub = str.substring(i);
				System.out.println(testSub);
				for (int j = 0; j < sub.length(); j++) {
					if (testSub.charAt(j) == sub.charAt(j)) {
						check = true;
					} else {
						check = false;
						continue;
					}
				}					
				if (check == true) {
					number++;				
				}			
			}
		if (number == n) {
			return true;
		} else {return false;}
	}

	//	Problem #2
	//	Given a non-negative int n, return the sum of its digits recursively 
	//	(no loops). Note that mod (%) by 10 yields the rightmost 
	//	digit (126 % 10 is 6), while divide (/) by 10 removes the 
	//	rightmost digit (126 / 10 is 12).

	//	sumDigitsInNumber(126) → 9
	//	sumDigitsInNumber(49) → 13
	//	sumDigitsInNumber(12) → 3
	
	/**
	 * 
	 * @param n
	 * 		int n contains integer to deal with.
	 * 
	 * @return integer
	 * 		integer that is the sum of each digit in int n.
	 */
	static int sumDigitsInNumber(int n) 
	{			
	    int r = n%10;       					// Remainder, last digit of the number
	    int num = n/10;     					// The rest of the number without the last digit
	    if(num == 0)
	    {
	    	return n;							// If number is now 0; return n
	    } else {
	    	return sumDigitsInNumber(num) + r;	// Recursive method call
	    }
	}	

	//	Problem #3
	//	Given base and n that are both 1 or more, compute recursively (no loops) 
	//	the value of base to the n power, so powerN(3, 2) is 9 (3 squared).

	//	exponential(3, 1) → 3
	//	exponential(3, 2) → 9
	//	exponential(3, 3) → 27
	
	/**
	 * 
	 * @param base, n
	 * 		int base containing the base of the term
	 * 		int n containing the exponent of the term
	 * 
	 * @return integer
	 * 		integer that corresponds with equating base to the n power
	 */
	static int exponential(int base, int n) 
	{
		if (n < 1) {
			return 1;			
		} else {
			return base * exponential(base, n - 1);
		}		
	}	

	//	Problem #4
	//	Given a string, compute recursively (no loops) a new string 
	//	where all the lowercase 'x' chars have been changed to 'y' chars. 

	//	changeXtoY("codex") → "codey"
	//	changeXtoY("xxhixx") → "yyhiyy"
	//	changeXtoY("xhixhix") → "yhiyhiy"
	
	/**
	 * 
	 * @param str
	 * 		String containing original string of chars to deal with
	 * 
	 * @return String
	 * 		String of characters where the lowercase x's have been changed to y's
	 */
	public static String changeXtoY(String str) 
	{
		String newStr = str.replace('x', 'y');
		if (newStr == str) {
			return changeXtoY(str);
		} else {
			return newStr;			
		}
	}
	
	//	Problem #5
	//	Given an array of ints, compute recursively if the array 
	//	contains a 6. We'll use the convention of considering only 
	//	the part of the array that begins at the given index. 
	//	In this way, a recursive call can pass index+1 to move down 
	//	the array. The initial call will pass in index as 0. 

	//	find6({1, 6, 4}, 0) → true
	//	find6({1, 4}, 0) → false
	//	find6({6}, 0) → true	
	
	/**
	 * 
	 * @param nums, index
	 * 		int[] list containing the original array of numbers
	 * 		int containing the position to start in nums
	 * 
	 * @return boolean
	 * 		returns true if a 6 is found in the array
	 * 		returns false if no 6 is found in the array
	 */
	static boolean find6(int[] nums, int index) 
	{
		if (nums[index] == 6) {
			return true;
		} else if (nums.length == index + 1) {
			return false;
		} else {
			return find6(nums, index + 1);
		}		
	}
	
	//	Problem #6
	//	Given a string, compute recursively a new string where all 
	//	the adjacent chars are now separated by a "*".   

	//	insertAsterisk("hello") → "h*e*l*l*o"
	//	insertAsterisk("abc") → "a*b*c"
	//	insertAsterisk("ab") → "a*b"
	
	/**
	 * 
	 * @param str
	 * 		String containing the original chars
	 * 
	 * @return AbridgedList
	 * 		String with an asterisk between each char
	 */
	static String insertAsterisk(String str) 
	{
		return str;
	}
	
	
	//	Problem #7
	//	We'll say that a "pair" in a string is two instances of 
	//	a char separated by a char. So "AxA" the A's make a pair. 
	//	Pair's can overlap, so "AxAxA" contains 3 pairs -- 2 for 
	//	A and 1 for x. Recursively compute the number of 
	//	pairs in the given string.  

	//	numberPairs("axa") → 1
	//	numberPairs("axax") → 2
	//	numberPairs("axbx") → 1
	
	/**
	 * 
	 * @param str
	 * 		String containing the original chars provided
	 * 
	 * @return 
	 * 		int with the number of pairs as defined above
	 */
	static int numberPairs(String str) 
	{
		int num = 1;
		return num;
	}
	
	//	Problem #8
	//	Given a string, return recursively a "cleaned" string where 
	//	adjacent chars that are the same have been reduced 
	//	to a single char. So "yyzzza" yields "yza".  

	//	reduceChars("yyzzza") → "yza"
	//	reduceChars("abbbcdd") → "abcd"
	//	reduceChars("Hello") → "Helo"
	
	/**
	 * 
	 * @param str
	 * 		String containing the original chars
	 * 
	 * @return 
	 * 		String with all repeated, adjacent chars are removed
	 */
	static String reduceChars(String str) 
	{
		return str;
	}
	
	//	Problem #9
	//	Given a string, return true if it is a nesting of zero or more 
	//	pairs of parenthesis, like "(())" or "((()))". Suggestion: 
	//	check the first and last chars, and then recur on what's inside them.  

	//	nestedParens("(())") → true
	//	nestedParens("((()))") → true
	//	nestedParens("(((x))") → false
	
	/**
	 * 
	 * @param str
	 * 		String containing the original chars
	 * 
	 * @return 
	 * 		returns true if there are zero or more pairs of parenthesis
	 * 		returns false if there are not zero or more pairs of parenthesis
	 */
	static boolean nestedParens(String str) 
	{
		return false;
	}
	
	//	Problem #10

	//	Given a string and a non-empty substring sub, compute 
	//	recursively the largest substring which starts and 
	//	ends with sub and return its length.  

	//	subStrSub("catcowcat", "cat") → 9
	//	subStrSub("catcowcat", "cow") → 3
	//	subStrSub("cccatcowcatxx", "cat") → 9
	
	/**
	 * 
	 * @param str, sub
	 * 		String containing the original chars to be tested against
	 * 		String containing the original chars to test with
	 * 
	 * @return 
	 * 		integer containing the largest number of chars in string 
	 * 		that start and end with sub
	 */
	static int subStrSub(String str, String sub) 
	{
		int num = str.length();
		return num;
	}
	
	///////////////////////////////////////////
	//
	// End of assignment code.
	//
	///////////////////////////////////////////
	
	public static void main(String[] args)
	{
	}
}
	
