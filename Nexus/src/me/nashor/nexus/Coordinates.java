package me.nashor.nexus;

public class Coordinates implements Comparable<Coordinates> {
   
   /**
    * The x-coordinate of the Coordinates.
    */
   final public int x;
   
   /**
    * The y-coordinate of the Coordinates.
    */
   final public int y;
   
   /**
    * The z-coordinate of the Coordinates.
    */
   final public int z;
   
   /**
    * The constructor for a set of x, y and z coordinates.
    * 
    * @param x the x-coordinate
    * @param y the y-coordinate
    * @param z the z-coordinate
    */
   public Coordinates(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }
   
   @Override
   public int compareTo(Coordinates coords) {
      // Coordinates should be sorted based on their x-coordinate
      return coords.x - x;
   }
   
   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof Coordinates))
         return false;
      if (obj == this)
         return true;
      
      Coordinates coords = (Coordinates) obj;
      return (coords.x == x && coords.y == y && coords.z == z);
   }
   
   @Override
   public String toString() {
      return String.format("{%d, %d, %d}", x, y, z);
   }
   
}
