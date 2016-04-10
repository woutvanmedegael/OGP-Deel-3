
package hillbillies.model;

/**
 * A class used to compare different numbers.
 * @value
 * @author Wout Van Medegael & Adriaan Van Gerven 
 */
public class NbCompare {
	
	/**
	 * A final variable used to compare the given numbers, the accuracy.
	 */
	private static final double epsilon = 0.00001;
	/**
	 * Checks whether the 2 given numbers are equal, or negligible different
	 * @param nb1
	 * 		   The first number to check
	 * @param nb2
	 * 	       The second number to check
	 * @return Returns true if absolute value of the numbers is smaller than epsilon
	 * 			| result == (Math.abs(nb2-nb1)<epsilon;)
	 */
	public boolean equals(double nb1, double nb2){
		return Math.abs(nb2-nb1)<epsilon;
	}
	/**
	 * Checks whether the 2 given numbers are equal, or negligible different
	 * @param nb1
	 * 		   The first number to check
	 * @param nb2
	 * 	       The second number to check
	 * @return Returns true if absolute value of the numbers is smaller than epsilon
	 * 			| result == (Math.abs(nb2-nb1)<epsilon;)
	 */
	public boolean equals(float nb1, float nb2){
		return Math.abs(nb2-nb1)<epsilon;
	}
	/**
	 * Checks whether the first number is bigger than or equal to the second number
	 * @param nb1
	 * 		   The first number to check
	 * @param nb2
	 * 	       The second number to check
	 * @return Returns true if nb1 is greater than nb2 or nb1 equals nb2 as defined the equals method
	 * 			| result == (nb1>nb2 || this.equals(nb1,nb2))
	 */	
	public boolean isBiggerOrEquals(double nb1, double nb2){
		if (this.equals(nb1, nb2)){
			return true;
		} else if (nb1>nb2){
			return true;
		}
		return false;
	}
	/**
	 * Checks whether the first number is bigger than or equal to the second number
	 * @param nb1
	 * 		   The first number to check
	 * @param nb2
	 * 	       The second number to check
	 * @return Returns true if nb1 is greater than nb 2 or nb1 equals nb2 as defined in the equals method
	 * 			| result == (nb1>nb2 || this.equals(nb1,nb2))
	 */	
	public boolean isBiggerOrEquals(float nb1, float nb2){
		if (this.equals(nb1, nb2)){
			return true;
		} else if (nb1>nb2){
			return true;
		}
		return false;
	}
	/**
	 * Checks whether the first number is smaller than or equal to the second number
	 * @param nb1
	 * 		   The first number to check
	 * @param nb2
	 * 	       The second number to check
	 * @return Returns true if nb1 is smaller than nb 2 or nb1 equals nb2 as defined the equals method
	 * 			| result == (nb1<nb2 || this.equals(nb1,nb2))
	 */	
	public boolean isSmallerOrEquals(double nb1, double nb2){
		return (nb1<nb2||this.equals(nb1,nb2));
	}
	/**
	 * Checks whether the first number is smaller than or equal to the second number
	 * @param nb1
	 * 		   The first number to check
	 * @param nb2
	 * 	       The second number to check
	 * @return Returns true if nb1 is smaller than nb 2 or nb1 equals nb2 as defined the equals method
	 * 			| result == (nb1<nb2 || this.equals(nb1,nb2))
	 */	
	public boolean isSmallerOrEquals(float nb1, float nb2){
			return (nb1<nb2||this.equals(nb1,nb2));
	}
	/**
	 * Checks whether the first number is smaller than the second number.
	 * @param nb1
	 * 		   The first number to check
	 * @param nb2
	 * 	       The second number to check
	 * @return Returns true if nb1 is not equal - as defined the equals method - to number 2 and nb1< nb2
	 * 			| result == (nb1<nb2 && !this.equals(nb1,nb2))
	 */	
	public boolean isSmaller(double nb1, double nb2){
		if (this.equals(nb1, nb2)){
			return false;
		} else if (nb1<nb2){
			return true;
		}
		return false;
	}
	/**
	 * Checks whether the first number is smaller than the second number.
	 * @param nb1
	 * 		   The first number to check
	 * @param nb2
	 * 	       The second number to check
	 * @return Returns true if nb1 is not equal - as defined the equals method - to number 2 and nb1< nb2
	 * 			| result == (nb1<nb2 && !this.equals(nb1,nb2))
	 */	
	public boolean isSmaller(float nb1, float nb2){
		if (this.equals(nb1, nb2)){
			return false;
		} else if (nb1<nb2){
			return true;
		}
		return false;
	}
	/**
	 * Checks whether the first number is bigger than the second number.
	 * @param nb1
	 * 		   The first number to check
	 * @param nb2
	 * 	       The second number to check
	 * @return Returns true if nb1 is not equal - as defined the equals method - to number 2 and nb1 > nb2
	 * 			| result == (nb1<nb2 && !this.equals(nb1,nb2))
	 */	
	public boolean isBigger(double nb1, double nb2){
		if (this.equals(nb1, nb2)){
			return false;
		} else if (nb1>nb2){
			return true;
		}
		return false;
	}
	/**
	 * Checks whether the first number is bigger than the second number.
	 * @param nb1
	 * 		   The first number to check
	 * @param nb2
	 * 	       The second number to check
	 * @return Returns true if nb1 is not equal - as defined the equals method - to number 2 and nb1 > nb2
	 * 			| result == (nb1<nb2 && !this.equals(nb1,nb2))
	 */	
	public boolean isBigger(float nb1, float nb2){
		if (this.equals(nb1, nb2)){
			return false;
		} else if (nb1>nb2){
			return true;
		}
		return false;
	}
	
}