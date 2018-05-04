package com.whh.sort;

import com.whh.uil.SortUtils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;


/**
 * BubbleSortMain
 * Created by xuzhuo on 2018/4/28.
 */
public class BubbleSortMain2 extends Application {
    private int[] data;
    Button startBtn;
    Rectangle[] rectangles;
    AnchorPane anchorPane;
    private final int BORDER_WIDTH = 5;


    @Override
    public void start(Stage primaryStage) {
        anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 600, 500, Color.WHITE);

        startBtn = new Button("start");
        startBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            startBtn.setDisable(true);
            sort();
//            System.out.println(Arrays.toString(data));
        });

        AnchorPane.setTopAnchor(startBtn, 5d);
        AnchorPane.setLeftAnchor(startBtn, (scene.getWidth() - startBtn.getWidth())/2);
        anchorPane.getChildren().add(startBtn);


        data = new int[20];
        Random random = new Random();
        IntStream.range(0, data.length).forEach(i -> data[i] = random.nextInt(data.length) + 1);

        rectangles = new Rectangle[data.length];

        double unitColumnWidth = ((anchorPane.getWidth() - BORDER_WIDTH) / rectangles.length) - BORDER_WIDTH;
        double unitColumnHeight = (anchorPane.getHeight() - BORDER_WIDTH * 10) / rectangles.length;

        IntStream.range(0, rectangles.length).forEach(i -> {
            rectangles[i] = new Rectangle(unitColumnWidth, data[i] * unitColumnHeight);
        });
        anchorPane.getChildren().addAll(rectangles);

        IntStream.range(0, rectangles.length).forEach(i -> {
            Rectangle rectangle = rectangles[i];
            rectangle.setStroke(Color.BLACK);
            rectangle.setFill(Color.BLUE);
            AnchorPane.setBottomAnchor(rectangle, 0d);
            AnchorPane.setLeftAnchor(rectangle, BORDER_WIDTH * (i + 1) + i * rectangle.getWidth());
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void sort(){
        new Thread(() -> {
            //记录右边已经排好序的索引
            int rightIndex = data.length - 1;
            do {
                rectangles[0].setFill(Color.YELLOW);//图设置颜色、可忽略
                //从第一个开始遍历，直到右边已经排好序的索引
                for (int i = 0; i < rightIndex; i++) {
                    //图 忽略
                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //这里逻辑是如果i+1 的值大于 i 那么交换二者位置
                    SortUtils.exchange(data, rectangles, i, i + 1, 50);
                }
                //遍历完成后找到当前最大值后，右边已经排好序的索引左移
                rightIndex--;
            }while (rightIndex >= 0);//如果已经排好序的索引>=0表示已经拍下完毕。
            startBtn.setDisable(false);
        }).start();

    }
    public static void main(String[] args) {
        launch(args);
    }
}
