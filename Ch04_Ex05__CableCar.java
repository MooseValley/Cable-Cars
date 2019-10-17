/*
   Author: Mike O'Malley
   Description: My solution to the Cable Car problem which forms the
   worked example for most of Chapter 4.

   Structured Fortran 77 for Engineers and Scientists,
   D. M. Etter.
   (C) 1983.  ISBN: 0-8053-2520-4

   Chapter 4, Exercise 5, p88-125.

   My solution is a million times better than the books solution.
   For mine, everything is parameterised - so you can easily add extra towers -
   just add more items to the array and my code adapts automatically, or change
   the End Distance, increment, etc.

   In the text book, the solution had everything hard coded = EVIL.
   For example, it had lots of code like this (and it used GO TO everywhere):

      if (distanceFromStart <= 250)
         nearestTower = 1
         distToNearestTower = distanceFromStart
      else (distanceFromStart <= 750)
         nearestTower = 2
         distToNearestTower = ABS (distanceFromStart - 500)
      else
         nearestTower = 3
         distToNearestTower = ABS (distanceFromStart - 1000)

   and similar - which makes it *very* awkward to add more towers, change the
   total distance to travel, etc.

   YEUCK !!  A terrible solution.

   Mine is a million times better !  :D  :D

   Also, my solution graphs the velocity results so you can really see the trends.
   (See "Sample Output" below).

   Would be easy to convert this from feet and feet/sec to m and m/sec.


*************************************
Question: Cable Car Velocity
*************************************

A 1000 foot cable is stretched between two towers, with a supporting
tower (a 3rd tower) midway between the two end towers.  The velocity of
the cable car depends on its position on the cable.  When the cable car
is within 30 feet of a tower, its velocity (in feet/sec) is given by:

   V = 2.425 + 0.00175 * D ^ 2

Otherwise (if the cable car is NOT within 30 feet of a tower), its
velocity (in feet/sec) is given by:

   V = 0.625 + 0.12 * D - 0.00025 * D ^ 2

Where:
* D is the distance to the nearest tower.

Print a table starting with the cable car starting at the first tower
and moving to the last tower in increments of 10 feet.  At each location
along the cable, print the nearest tower number (1 = first, 2 = midddle,
3 = last), the distance from the first tower, and the velocity of the
cable car.


*************************************
Follow on Questions:  (by Mike O)
*************************************
Also include the distance to the nearest tower in the output table.

Also include a bar graph of the velocity at each location along the cable.
Use a string of *'s to indicate the magnitude of the velocity.
Print the bar graph to the right of the text table.

Store your tower positions in an array, so that  adding or removing
towers, changing distances, etc involves only as changing the values
in a single array.
For example, this arrray:

    double towerPositions []  = {0.0, 500.0, 1000.0};

would indicate that the there are 3 towers, located 0, 500.0, and 1000.0 from the starting point.

If we wanted to add more towers at 1799.0 and 2333.0, then our array would be:

    double towerPositions []  = {0.0, 500.0, 1000.0, 1799.0, 2333.0};

and that should be all we need to change in our code.  Our code should automatically
adapt to the new array size / data and work correctly with this new data.

Convert the formulae and output to metric (m, m/sec, etc).

Allow the user to select whether they want metric or imperial and output
data in the required units.


*/

/*
*************************************
Sample Output:
*************************************


     C a b l e   C a r   R e p o r t

 Distance    Nearest    Distance to   Cable Car
From Start  Tower Num  Nearest Tower  Velocity
 (feet)                   (feet)      (feet/sec)
----------  ---------  -------------  ----------
       0        1            0            2.43     ****
      10        1           10            2.60     ****
      20        1           20            3.13     *****
      30        1           30            4.00     *******
      40        1           40            5.02     *********
      50        1           50            6.00     ***********
      60        1           60            6.92     ************
      70        1           70            7.80     **************
      80        1           80            8.63     ****************
      90        1           90            9.40     *****************
     100        1          100           10.13     ******************
     110        1          110           10.80     ********************
     120        1          120           11.42     *********************
     130        1          130           12.00     **********************
     140        1          140           12.53     ***********************
     150        1          150           13.00     ************************
     160        1          160           13.42     *************************
     170        1          170           13.80     *************************
     180        1          180           14.12     **************************
     190        1          190           14.40     ***************************
     200        1          200           14.63     ***************************
     210        1          210           14.80     ***************************
     220        1          220           14.92     ***************************
     230        1          230           15.00     ****************************
     240        1          240           15.02     ****************************
     250        1          250           15.00     ****************************
     260        2          240           15.02     ****************************
     270        2          230           15.00     ****************************
     280        2          220           14.92     ***************************
     290        2          210           14.80     ***************************
     300        2          200           14.63     ***************************
     310        2          190           14.40     ***************************
     320        2          180           14.12     **************************
     330        2          170           13.80     *************************
     340        2          160           13.42     *************************
     350        2          150           13.00     ************************
     360        2          140           12.53     ***********************
     370        2          130           12.00     **********************
     380        2          120           11.42     *********************
     390        2          110           10.80     ********************
     400        2          100           10.13     ******************
     410        2           90            9.40     *****************
     420        2           80            8.63     ****************
     430        2           70            7.80     **************
     440        2           60            6.92     ************
     450        2           50            6.00     ***********
     460        2           40            5.02     *********
     470        2           30            4.00     *******
     480        2           20            3.13     *****
     490        2           10            2.60     ****
     500        2            0            2.43     ****
     510        2           10            2.60     ****
     520        2           20            3.13     *****
     530        2           30            4.00     *******
     540        2           40            5.02     *********
     550        2           50            6.00     ***********
     560        2           60            6.92     ************
     570        2           70            7.80     **************
     580        2           80            8.63     ****************
     590        2           90            9.40     *****************
     600        2          100           10.13     ******************
     610        2          110           10.80     ********************
     620        2          120           11.42     *********************
     630        2          130           12.00     **********************
     640        2          140           12.53     ***********************
     650        2          150           13.00     ************************
     660        2          160           13.42     *************************
     670        2          170           13.80     *************************
     680        2          180           14.12     **************************
     690        2          190           14.40     ***************************
     700        2          200           14.63     ***************************
     710        2          210           14.80     ***************************
     720        2          220           14.92     ***************************
     730        2          230           15.00     ****************************
     740        2          240           15.02     ****************************
     750        2          250           15.00     ****************************
     760        3          240           15.02     ****************************
     770        3          230           15.00     ****************************
     780        3          220           14.92     ***************************
     790        3          210           14.80     ***************************
     800        3          200           14.63     ***************************
     810        3          190           14.40     ***************************
     820        3          180           14.12     **************************
     830        3          170           13.80     *************************
     840        3          160           13.42     *************************
     850        3          150           13.00     ************************
     860        3          140           12.53     ***********************
     870        3          130           12.00     **********************
     880        3          120           11.42     *********************
     890        3          110           10.80     ********************
     900        3          100           10.13     ******************
     910        3           90            9.40     *****************
     920        3           80            8.63     ****************
     930        3           70            7.80     **************
     940        3           60            6.92     ************
     950        3           50            6.00     ***********
     960        3           40            5.02     *********
     970        3           30            4.00     *******
     980        3           20            3.13     *****
     990        3           10            2.60     ****
   1,000        3            0            2.43     ****

*/

public class Ch04_Ex05__CableCar
{
   // Constants:
   private static final double endDistance        = 1000.0;
   private static final double increment          = 10.0;
   private static final double towerPositions []  = {0.0, endDistance / 2.0, endDistance};


   private static String GetStringOfCharacters (double val, double maxVal, char fillChar, int maxStringLength)
   {
      int numChars = (int) (val / maxVal * maxStringLength);
      String outString = "";

      for (int k = 0; k < numChars; k++)
      {
         outString = outString + fillChar;
      }

      return outString;
   }

   public static void main (String[] args)
   {
      double distToNearestTower = 0.0;
      double distanceFromStart  = 0.0;
      double velocity           = 0.0;
      int    nearestTowerNum    = 0;

      System.out.println ("     C a b l e   C a r   R e p o r t           ");
      System.out.println ();
      System.out.println (" Distance    Nearest    Distance to   Cable Car ");
      System.out.println ("From Start  Tower Num  Nearest Tower  Velocity  ");
      System.out.println (" (feet)                   (feet)      (feet/sec)");
      System.out.println ("----------  ---------  -------------  ----------");

      while (distanceFromStart <= endDistance)
      {
         distToNearestTower = endDistance;
         nearestTowerNum    = 0;

         for (int k = 0; k < towerPositions.length; k++)
         {
            if (distToNearestTower > Math.abs (distanceFromStart - towerPositions [k]))
            {
               distToNearestTower =  Math.abs (distanceFromStart - towerPositions [k]);
               nearestTowerNum    = k + 1; // So 1st tower is 1 not 0.
            }
         }

         if (distToNearestTower < 30.0)
         {
            velocity = 2.425 + 0.00175 * distToNearestTower * distToNearestTower;
         }
         else
         {
            velocity = 0.625 + 0.12 * distToNearestTower - 0.00025 * distToNearestTower * distToNearestTower;
         }


         System.out.println (" " + String.format ("%,7.0f", distanceFromStart)  + "  " +
                                   String.format ("%,7d",   nearestTowerNum )   + "      " +
                                   String.format ("%,7.0f", distToNearestTower) + "         " +
                                   String.format ("%,7.2f", velocity)           + "     " +
                                   GetStringOfCharacters (velocity, 16.0, '*', 30));

         distanceFromStart = distanceFromStart + increment;
      }
   }
}