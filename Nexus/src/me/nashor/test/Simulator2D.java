package me.nashor.test;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
 
public class Simulator2D extends Application {
   
   private static int WIDTH = 800;
   private static int HEIGHT = 800;
   private static int GRID = 15;
 
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage primaryStage) {
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
        
        drawShapes(gc);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
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