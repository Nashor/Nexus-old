package me.nashor.nexus;

public class Nexus {
   
   public enum Shape {
      
      /**
       * The Nexus will protect a volume with a rectangular shape. 
       * The y-coordinate of the Nexus does not matter.
       */
      Chunk,
      
      /**
       * The Nexus will protect a volume with a cubed shape.
       */
      Cube,
      
      /**
       * The Nexus will protect a volume with a cylindrical shape.
       * The y-coordinate of the Nexus does not matter.
       */
      Cylinder,
      
      /**
       * The Nexus will protect a volume with a spherical shape.
       */
      Sphere;
      
   }
   
   public enum Spread {
      
      /**
       * The influence within the radius of the Nexus will decrease like a Gaussian function as the
       * distance away from the Nexus increases.
       */
      Gaussian,
      
      /**
       * The influence within the radius of the Nexus will be decreasing at a constant rate as the
       * distance away from the Nexus increases.
       */
      Linear,
      
      /**
       * The influence within the radius of the Nexus will be constant.
       */
      Solid;
      
   }
   
   /**
    * The shape of the Nexus. The default value is Sphere.
    */
   private Shape shape = Shape.Sphere;
   
   /**
    * The spread of the Nexus. The default value is Gaussian.
    */
   private Spread spread = Spread.Gaussian;
   
   /**
    * An object containing the x, y and z coordinates of the Nexus. This object specifies where in
    * the Minecraft world the Nexus is located.
    */
   private Coordinates coords;
   
   /** 
    * The power is equal to the influence at the origin of the Nexus. This means, when the distance
    * is equal to 0, the influence is equal to power.
    * <p>
    * This variable is used when calculating the influence for every kind of spread. When the Nexus
    * has a Solid Spread, the influence within the radius is always equal to the power.
    */
   private double power = 0.;
   
   /** 
    * The radius is used to define the boundary of the Nexus influence. When the distance is equal
    * to the radius, the influence will be 1. When the influence is less than 1, it is not 
    * considered to influence the block.
    */
   private double radius = 0.;
   
   /** 
    * The diffuse is used when calculating the influence for a Gaussian Spread type Nexus. This
    * variable is not set directly. Instead, it is a function of the power and radius. The value
    * is stored to avoid constant calculations for it.
    */
   private double diffuse = 0.;
   
   /**
    * The slope is used when calculating the influence for a Linear Spread type Nexus. This
    * variable is not set directly. Instead, it is a function of the power and radius. The value
    * is stored to avoid constant calculations for it.
    */
   private double slope = 0.;
   
   /**
    * The constructor for a simple Nexus.
    * 
    * @param x the x-coordinate of the Nexus
    * @param y the y-coordinate of the Nexus
    * @param z the z-coordinate of the Nexus
    * @param power the power of the Nexus
    * @param radius the radius of the Nexus
    */
   public Nexus(Coordinates coords, double power, double radius) {
      setCoords(coords);
      setPower(power);
      setRadius(radius);
   }
   
   /**
    * Returns the coordinates of the Nexus.
    * <p>
    * From the coordinates, the x, y and z coordinate values may be retrieved.
    * 
    * @return the coordinates of the Nexus
    */
   public Coordinates getCoords() {
      return coords;
   }

   /**
    * Sets the coordinates of the Nexus, allowing the location to be changed.
    * <p>
    * It is possible for Nexus to be moved within the Minecraft world. This function allows for the
    * Nexus to change coordinates in such a case. A new Coordinates object must be instantiated as
    * all fields in the Coordinate class are final.
    * 
    * @param coords the new coordinates of the Nexus
    */
   public void setCoords(Coordinates coords) {
      this.coords = coords;
   }
   
   /**
    * Returns the power of the Nexus.
    * @return the power of the Nexus
    */
   public double getPower() {
      return power;
   }
   
   /**
    * Sets the power, diffuse and slope of the Nexus.
    * <p>
    * When the power of the Nexus is set, so is the diffuse and slope. This is done because these
    * variables are functions of the power.
    * 
    * @param power the power of the Nexus
    */
   public void setPower(double power) {
      this.power = power;
      diffuse = Math.sqrt((radius*radius) / -Math.log(1. / power));
      slope = (power - 1) / radius;
   }
   
   /**
    * Returns the radius of the Nexus.
    * @return the radius of the Nexus
    */
   public double getRadius() {
      return radius;
   }
   
   /**
    * Sets the radius, diffuse and slope of the Nexus.
    * <p>
    * When the radius of the Nexus is set, so is the diffuse and slope. This is done because these
    * variables are functions of the radius.
    * 
    * @param radius the radius of the Nexus
    */
   public void setRadius(double radius) {
      this.radius = radius;
      diffuse = Math.sqrt((radius*radius) / -Math.log(1. / power));
      slope = (power - 1) / radius;
   }
   
   /**
    * Returns the shape of the Nexus.
    * @return the shape of the Nexus
    */
   public Shape getShape() {
      return shape;
   }
   
   /**
    * Sets the shape of the Nexus.
    * @param shape the shape of the Nexus
    */
   public void setShape(Shape shape) {
      this.shape = shape;
   }
   
   /**
    * Returns the spread of the Nexus.
    * @return the spread of the Nexus
    */
   public Spread getSpread() {
      return spread;
   }
   
   /**
    * Sets the spread of the Nexus.
    * @param spread the spread of the Nexus
    */
   public void setSpread(Spread spread) {
      this.spread = spread;
   }
   
   /**
    * Returns the distance from the specified coordinates of a point to the Nexus.
    * <p>
    * The distance calculation differs for each Shape the Nexus may take. For Chunk and Cylinder
    * shaped Nexus, the y-coordinate of the point is not taken into account.
    * 
    * @param coords the coordinates of the point
    * @return the distance from the point to the Nexus
    */
   public double distance(Coordinates coords) {
      int y;
      int dX, dY, dZ;
      
      y = coords.y;
      
      switch (shape) {
      case Chunk:
         y = this.coords.y;
         // Fall through
      case Cube:
         dX = Math.abs(this.coords.x - coords.x);
         dY = Math.abs(this.coords.y - y);
         dZ = Math.abs(this.coords.z - coords.z);
         return dX > dY ? (dX > dZ ? dX : dZ) : (dY > dZ ? dY : dZ);
      case Cylinder:
         y = this.coords.y;
         // Fall through
      default:
      //case Sphere:
         dX = this.coords.x - coords.x;
         dY = this.coords.y - y;
         dZ = this.coords.z - coords.z;
         return Math.sqrt(dX*dX + dY*dY + dZ*dZ);
      }
   }
   
   /**
    * Returns the influence of the Nexus at the specified coordinates of a point.
    * <p>
    * The influence at this point represents how well the Nexus protects that point. The higher the
    * influence is, the better protected the block will be. The Nexus with the highest influence at
    * the specified point is the one which protects it.
    * This calculation differs for each Spread the Nexus may take.
    * 
    * @param coords the coordinates of the point
    * @return the influence of the Nexus at the point
    */
   public double influence(Coordinates coords) {
      double dist;
      dist = distance(coords); 
      
      switch (spread) {
      case Linear:
         return power - dist * slope;
      case Solid:
         return dist <= radius ? power : 0;
      default:
      //case Gaussian:
         return power * Math.exp(dist*dist * -(1. / (diffuse*diffuse)));
      }
   }
   
   @Override
   public String toString() {
      return String.format("{%d, %d, %d} P=%f, R=%f, D=%f, S=%f", 
            coords.x, coords.x, coords.x, power, radius, diffuse, slope);
   }

}
