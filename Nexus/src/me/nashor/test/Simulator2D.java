package me.nashor.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import me.nashor.nexus.Coordinates;
import me.nashor.nexus.Nexus;
import me.nashor.nexus.Nexus.Shape;
import me.nashor.nexus.Nexus.Spread;
 
public class Simulator2D extends Application {
   
   private static int WIDTH = 800;
   private static int HEIGHT = 800;
   private static int GRID = 10;
 
   private List<Nexus> nexuss = new ArrayList<Nexus>();
   
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage primaryStage) {
       Group root = new Group();
       //root.getChildren().add(getGridLayer());

       Canvas canvas = new Canvas(WIDTH, HEIGHT);
       final GraphicsContext gc = canvas.getGraphicsContext2D();
       
       canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

          @Override
          public void handle(MouseEvent e) {
             int x = (int) (e.getSceneX() / GRID);
             int y = (int) (e.getSceneY() / GRID);
             
             gc.setFill(Color.BISQUE);
             gc.fillRect(x * GRID + 1, y * GRID + 1, GRID - 1, GRID - 1);
             
             nexuss.add(new Nexus(new Coordinates(x, 0, y), 10, 10));
             
          }
            
         });
       
       Nexus nex = new Nexus(new Coordinates(23, 0, 25), 14, 10);
       nex.setShape(Shape.Chunk);
       nexuss.add(nex);
       //nexuss.add(new Nexus(new Coordinates(33, 0, 17), 6, 14));
       nexuss.add(new Nexus(new Coordinates(33, 0, 17), 6, 30));
       nexuss.add(new Nexus(new Coordinates(39, 0, 32), 21, 14));
       Nexus nex2 = new Nexus(new Coordinates(3, 0, 47), 13, 60);
       nex2.setSpread(Spread.Gaussian);
       nexuss.add(nex2);
       
       double maxPower = 0;
       for (Nexus nexus : nexuss) {
          if (nexus.getPower() > maxPower) {
             maxPower = nexus.getPower();
          }
       }
       
       Random random = new Random();
       
       int count = 0;
       Map<Nexus, Color> colorMap = new HashMap<Nexus, Color>();
       for (Nexus nexus : nexuss) {
          /*int r = random.nextInt(220 - 100 + 1) + 100;
          int g = random.nextInt(220 - 100 + 1) + 100;
          int b = random.nextInt(220 - 100 + 1) + 100;
          colorMap.put(nexus, Color.rgb(r, g, b));*/
          switch (count) {
          case 0:
             colorMap.put(nexus, Color.RED);
             break;
          case 1:
             colorMap.put(nexus, Color.ORANGE);
             break;
          case 2:
             colorMap.put(nexus, Color.GREEN);
             break;
          case 3:
             colorMap.put(nexus, Color.BLUE);
             break;
          default:
             colorMap.put(nexus, Color.PURPLE);
          }
          count ++;
       }
       
       for (int i = 0; i < WIDTH / GRID; i ++) {
          for (int j = 0; j < HEIGHT / GRID; j ++) {
             
             Nexus highestInfNexus = null;
             double highestInf = 1;
             
             for (Nexus nexus : nexuss) {
                double inf = nexus.influence(new Coordinates(i, 0, j));
                if (inf > highestInf) {
                   highestInf = inf;
                   highestInfNexus = nexus;
                   //gc.setFill(new Color(0, 0, 0, inf / maxPower));
                   //gc.fillRect(i * GRID + 1, j * GRID + 1, GRID - 1, GRID - 1);           
                   //break;
                }
             }
             if (highestInfNexus != null) {
                Color col = colorMap.get(highestInfNexus);
                int r = (int) (col.getRed() * 255);
                int g = (int) (col.getGreen() * 255);
                int b = (int) (col.getBlue() * 255);
                gc.setFill(Color.rgb(r, g, b, highestInf / maxPower));
                //gc.setFill(new Color(0, 0, 0, highestInf / maxPower));
                gc.fillRect(i * GRID + 1, j * GRID + 1, GRID - 1, GRID - 1);
             }
          }
       }
       
       gc.setStroke(Color.BLACK);
       gc.setLineWidth(3);
       
       for (Nexus nexus : nexuss) {
          int x = nexus.getCoords().x;
          int z = nexus.getCoords().z;
          gc.setFill(colorMap.get(nexus));
          gc.fillRect(x * GRID + 1, z * GRID + 1, GRID - 1, GRID - 1);
          gc.strokeRect(x * GRID + 1, z * GRID + 1, GRID - 1, GRID - 1);
       }
       
       root.getChildren().add(canvas);
       canvas.toBack();
       
       primaryStage.setScene(new Scene(root));
       primaryStage.show();
       
       /* 
       primaryStage.setTitle("Drawing Operations Test");
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        
        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
           
           double prevGX, prevGY;

           @Override
           public void handle(MouseEvent e) {
              double x = e.getSceneX();
              double y = e.getSceneY();
              
              int gX = (int) (x / GRID);
              int gY = (int) (y / GRID);
              
              if (prevGX != gX || prevGY != gY) {
                 gc.setFill(Color.WHITE);
                 gc.fillRect(prevGX * GRID + 1, prevGY * GRID + 1, GRID - 1, GRID - 1);
              }
              gc.setFill(Color.LIGHTGRAY);
              gc.fillRect(gX * GRID + 1, gY * GRID + 1, GRID - 1, GRID - 1);
              
              prevGX = gX;
              prevGY = gY;
              
           }
             
          });
        
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

         @Override
         public void handle(MouseEvent e) {
            int x = (int) (e.getSceneX() / GRID);
            int y = (int) (e.getSceneY() / GRID);
            
            gc.setFill(Color.BISQUE);
            gc.fillRect(x * GRID + 1, y * GRID + 1, GRID - 1, GRID - 1);
            
            nexuss.add(new Nexus(new Coordinates(x, 0, y), 10, 10));
            
         }
           
        });
        
        drawShapes(gc);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        */
    }
    
    private Canvas getGridLayer() {
       Canvas canvas = new Canvas(WIDTH, HEIGHT);
       GraphicsContext gc = canvas.getGraphicsContext2D();
       
       gc.setStroke(Color.GRAY);
       for (int i = GRID; i < WIDTH; i += GRID) {
          gc.strokeLine(i + .5, 0, i + .5, HEIGHT);
       }
       
       for (int j = GRID; j < HEIGHT; j += GRID) {
          gc.strokeLine(0, j + .5, WIDTH, j + .5);
       }
       
       return canvas;
    }

    private void drawShapes(GraphicsContext gc) {
       gc.setStroke(Color.GRAY);
       for (int i = GRID; i < WIDTH; i += GRID) {
          gc.strokeLine(i + .5, 0, i + .5, HEIGHT);
       }
       
       for (int j = GRID; j < HEIGHT; j += GRID) {
          gc.strokeLine(0, j + .5, WIDTH, j + .5);
       }
    }
}