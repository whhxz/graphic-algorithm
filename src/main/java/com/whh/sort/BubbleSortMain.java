package com.whh.sort;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;


/**
 * BubbleSortMain
 * Created by xuzhuo on 2018/4/28.
 */
public class BubbleSortMain extends Application {
    private int[] data;


    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root= FXMLLoader.load(this.getClass().getResource("BubbleSortView.fxml"));
//        primaryStage.setTitle("My Application");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();
        data = new int[10];
        Random random = new Random();
        for (int i = 0; i < data.length; i++) {
            data[i] = random.nextInt(50);
        }

        primaryStage.setTitle("BubbleSort");
        Group root = new Group();
        Canvas canvas = new Canvas(600, 400);
        draw(canvas);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public void draw(Canvas canvas) {
        GraphicsContext g = canvas.getGraphicsContext2D();
        double columnWidth = (canvas.getWidth() - 4 * 20) / data.length;
        double columnHeight = (canvas.getHeight() - 4 * 20) / data.length;

        for (int i = 0; i < data.length; i++) {
            g.setFill(Color.BLUE);
            g.fillRect(columnWidth * (i + 1), canvas.getHeight() - data[i] * columnHeight, columnWidth, data[i] * columnHeight);
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
