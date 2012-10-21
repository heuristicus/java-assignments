package ex15;

import java.io.Serializable;

/** The additional functionality required of a Kalah Board representation.  
 * Moves are numbered 1 to 6.
 * The players are numbered 0 and 1.
 **/
public abstract class KalahBoard extends Board implements Serializable
{
   /** sets up the board for the start of a game.
    * @param stonesPerPit the number of stones per pit at the start.  */
   public abstract void initialise(int stonesPerPit);
   
   /** Returns the number of stones in a given pit. */
   public abstract int getStones(int playerNum, int pitNum);

  /** Gets the number of stones in the given player's end pit or Kalah */
   public abstract int getKalah(int playerNum);

   /** Adds up the stones in player 0's pits and Kalah
    *  and subtracts the number of stones in player 1's pits and Kalah */
   public abstract int score();
}
