package me.nashor.nexus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import me.nashor.nexus.Nexus.Shape;
import me.nashor.nexus.Nexus.Spread;


public class Main {

   public static void main(String[] args) {
      Main main = new Main();
      //main.test4();
      
      Nexus nexus = new Nexus(new Coordinates(23, 0, 25), 10, 20);
      nexus.setShape(Shape.Chunk);
      
      double dist = nexus.distance(new Coordinates(23, 0, 3));
      System.out.println(dist);
      double inf = nexus.influence(new Coordinates(23, 0, 3));
      System.out.println(inf);
   }
   
   public double[] simRange(int start, int stop) {
      start *= 10;
      stop *= 10;
      
      int i = 0;
      double range[] = new double[stop - start + 1];
      
      for (i = 0; i <= stop - start; i ++) {
         range[i] = (start + i) / 10.;
      }
      
      return range;
   }
   
   public void test4() {
      SortedSet<Coordinates> set = new TreeSet<Coordinates>();
      set.add(new Coordinates(45, 64, 72));
      set.add(new Coordinates(99, 11, -47));
      set.add(new Coordinates(-8, 63, 249));
      
      for (Coordinates coords : set) {
         System.out.println(coords);
      }
   }
   
   public void test3() {
      Nexus nexus;
      
      nexus = new Nexus(new Coordinates(50, 50, 50), 10, 25);
      nexus.setSpread(Spread.Linear);
      
      System.out.println(nexus.influence(new Coordinates(75, 50, 50)));
   }
   
   public void test2() {
      int x, y, z;
      Nexus nexus;
      
      x = -3647;
      y = 101;
      z = 1177;
      nexus = new Nexus(new Coordinates(-3637, 245, 467), 67, 238);
      
      System.out.println(nexus.influence(new Coordinates(x, y, z)));
   }
   
   public void test1() {
      final int NUM_NEXUS = 10000;
      final int WORLD_LENGTH = 6000;
      final int WORLD_HEIGHT = 256;
      final Random rand = new Random();
      
      int rX, rY, rZ;
      int rPower, rRadius;
      
      List<Nexus> nexuz = new ArrayList<Nexus>(NUM_NEXUS);
      for (int i = 0; i < NUM_NEXUS; i ++) {
         rX = rand.nextInt(WORLD_LENGTH * 2 + 1) - WORLD_LENGTH;
         rY = rand.nextInt(WORLD_HEIGHT + 1);
         rZ = rand.nextInt(WORLD_LENGTH * 2 + 1) - WORLD_LENGTH;
         
         rPower = rand.nextInt(80 + 1) + 20;
         rRadius = rand.nextInt(300 + 1) + 5;
         
         nexuz.add(new Nexus(new Coordinates(rX, rY, rZ), rPower, rRadius));
      }
      
      rX = rand.nextInt(WORLD_LENGTH * 2 + 1) - WORLD_LENGTH;
      rY = rand.nextInt(WORLD_HEIGHT + 1);
      rZ = rand.nextInt(WORLD_LENGTH * 2 + 1) - WORLD_LENGTH;
      
      double inf;
      double highestInf = 1;
      Nexus highestInfNexus = null; 
      
      long startTime, stopTime;
      
      startTime = System.currentTimeMillis();
      for (Nexus nexus : nexuz) {
         inf = nexus.influence(new Coordinates(rX, rY, rZ));
         if (inf > highestInf) {
            highestInf = inf;
            highestInfNexus = nexus;
         }
      }
      stopTime = System.currentTimeMillis();
      
      System.out.println(stopTime - startTime);
      
      System.out.println(String.format("Influence: %f", highestInf));
      System.out.println(String.format("Point: {%d, %d, %d}", rX, rY, rZ));
      System.out.println(String.format("Nexus: %s", highestInfNexus));
      if (highestInfNexus != null)
         System.out.println(String.format("Distance: %s", highestInfNexus.distance(new Coordinates(rX, rY, rZ))));
   }

}
