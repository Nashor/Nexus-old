package me.nashor.nexus;

public class Nexus {
   
   private int x, y, z;
   
   private double power, radius;
   
   private double spread;
   
   public Nexus(int x, int y, int z, double power, double radius) {
      move(x, y, z);
      
      setPower(power);
      setRadius(radius);
   }
   
   public int getX() {
      return x;
   }
   
   public int getY() {
      return y;
   }
   
   public int getZ() {
      return z;
   }
   
   public void move(int newX, int newY, int newZ) {
      x = newX;
      y = newY;
      z = newZ;
   }
   
   public double getPower() {
      return power;
   }
   
   public void setPower(double power) {
      this.power = power;
   }
   
   public double getRadius() {
      return radius;
   }
   
   public void setRadius(double radius) {
      this.radius = radius;
      spread = Math.sqrt((radius*radius) / -Math.log(1. / power));
   }
   
   public double distance(int x, int y, int z) {
      int dX, dY, dZ;
      dX = this.x - x;
      dY = this.y - y;
      dZ = this.z - z;
      
      return Math.sqrt(dX*dX + dY*dY + dZ*dZ);
   }
   
   public double influence(int x, int y, int z) {
      double dist;
      dist = distance(x, y, z); 
            
      return power * Math.exp(dist*dist * -(1. / (spread*spread)));
   }
   
   @Override
   public String toString() {
      return String.format("{%d, %d, %d} P=%f, R=%f", x, y, z, power, radius);
   }

}
