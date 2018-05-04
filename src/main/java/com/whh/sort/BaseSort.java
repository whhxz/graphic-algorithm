package com.whh.sort;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * BaseSort
 * Created by xuzhuo on 2018/4/29.
 */
public abstract class BaseSort extends Application {
    protected int data[];
    protected Rectangle[] rectangles;
    protected AnchorPane anchorPane;
    protected final int BORDER_WIDTH = 5;
    protected Button startBtn;
    protected Scene scene;

    protected void init(int len) {
        anchorPane = new AnchorPane();
        scene = new Scene(anchorPane, 600, 500, Color.WHITE);

        startBtn = new Button("start");
        Button reset = new Button("reset");
        //添加点击事件
        startBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            startBtn.setDisable(true);
            reset.setDisable(true);
            Thread thread = new Thread(() -> {
                try {
                    sort();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                startBtn.setDisable(false);
                reset.setDisable(false);
            });
            thread.setDaemon(true);
            thread.start();
        });
        //添加btn位置
        AnchorPane.setTopAnchor(startBtn, 5d);
            AnchorPane.setLeftAnchor(startBtn, (scene.getWidth()) / 2 - 50);
        anchorPane.getChildren().add(startBtn);

        reset.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            reset.setDisable(true);
            initData(len);
            reset.setDisable(false);
        });
        AnchorPane.setTopAnchor(reset, 5d);
        AnchorPane.setLeftAnchor(reset, (scene.getWidth()) / 2 + 50);
        anchorPane.getChildren().add(reset);

        initData(len);
    }

    protected void initData(int len){
        //删除旧数据
        if (rectangles != null){
            anchorPane.getChildren().removeAll(rectangles);
        }
        //初始化数据
        data = new int[len];
        Random random = new Random();
        IntStream.range(0, len).forEach((i) -> data[i] = random.nextInt(len) + 1);
        rectangles = new Rectangle[len];
        //单位长宽
        double unitColumnWidth = ((anchorPane.getWidth() - BORDER_WIDTH) / rectangles.length) - BORDER_WIDTH;
        double unitColumnHeight = (anchorPane.getHeight() - BORDER_WIDTH * 10) / rectangles.length;

        //初始化长方形数据
        IntStream.range(0, len).forEach(i -> {
            rectangles[i] = new Rectangle(unitColumnWidth, data[i] * unitColumnHeight);
        });


        anchorPane.getChildren().addAll(rectangles);

        //初始化画图
        initDraw();
    }

    protected void initDraw(){
        IntStream.range(0, rectangles.length).forEach(i -> {
            Rectangle rectangle = rectangles[i];
            rectangle.setFill(Color.BLUE);
            rectangle.setStroke(Color.BLACK);
            AnchorPane.setBottomAnchor(rectangle, 0d);
            AnchorPane.setLeftAnchor(rectangle, BORDER_WIDTH * (i + 1) + i * rectangle.getWidth());
        });

    }

    public abstract void sort() throws Exception;
}
