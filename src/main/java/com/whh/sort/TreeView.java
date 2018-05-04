package com.whh.sort;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * TreeView
 * Created by xuzhuo on 2018/5/3.
 */
public class TreeView extends Application {
    protected Button startBtn;

    protected int[] data;

    protected StackPane[] panes;

    protected Scene scene;

    protected AnchorPane anchorPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        int len = 20;
        data = new int[len];
        panes = new StackPane[len];
        Random random = new Random();
        IntStream.range(0, len).forEach((i) -> data[i] = random.nextInt(len) + 1);


        anchorPane = new AnchorPane();
        scene = new Scene(anchorPane, 1000, 700, Color.WHITE);

        startBtn = new Button("start");
        Button reset = new Button("reset");
        //添加点击事件
        startBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            startBtn.setDisable(true);
            reset.setDisable(true);
            Thread thread = new Thread(() -> {

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

            reset.setDisable(false);
        });
        AnchorPane.setTopAnchor(reset, 5d);
        AnchorPane.setLeftAnchor(reset, (scene.getWidth()) / 2 + 50);
        anchorPane.getChildren().add(reset);
//        new Thread(this::initDraw).start();
        initDraw();

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void initDraw(){
        int treeDepth = (int) Math.floor(Math.log(data.length) / Math.log(2));
        double maxWidth = scene.getWidth();
        int radius = 15;
        double firstTop = 150d;
        double depthWidth = ((2 << (treeDepth - 1)) - 1)* (4 * radius);
        for (int i = treeDepth - 1; i >= 0; i--){
            int leftIndex = (2 << i) - 1;
            int nextIndex = (2 << (i + 1)) - 1;

            double leftCenter = depthWidth / 2;
            double nodeSpace = depthWidth / ((2 << i) - 1);

            for (int j = leftIndex; j < nextIndex && j < data.length; j++){
                Circle circle = new Circle(radius);
                circle.setStroke(Color.BLACK);
                circle.setStrokeType(StrokeType.INSIDE);
                circle.setStrokeWidth(2);
                circle.setFill(Color.AZURE);
                circle.relocate(0, 0);

                Group group = new Group(circle);
                Text text = new Text(String.valueOf(data[j]));
                text.setFont(new Font(18));
                text.setBoundsType(TextBoundsType.VISUAL);

                StackPane stack = new StackPane();
                stack.getChildren().addAll(group, text);

                AnchorPane.setTopAnchor(stack, firstTop + (i + 1) * radius * 2 + radius);
                AnchorPane.setLeftAnchor(stack, maxWidth / 2.0 - leftCenter - radius);
                panes[j] = stack;
                leftCenter = leftCenter - nodeSpace;
            }
            depthWidth = depthWidth - nodeSpace;
        }
        Circle circle = new Circle(radius);
        circle.setStroke(Color.BLACK);
        circle.setStrokeType(StrokeType.INSIDE);
        circle.setStrokeWidth(2);
        circle.setFill(Color.AZURE);
        circle.relocate(0, 0);

        Group group = new Group(circle);
        Text text = new Text(String.valueOf(data[0]));
        text.setFont(new Font(18));
        text.setBoundsType(TextBoundsType.VISUAL);

        StackPane stack = new StackPane();
        stack.getChildren().addAll(group, text);

        AnchorPane.setTopAnchor(stack, firstTop + radius);
        AnchorPane.setLeftAnchor(stack, maxWidth / 2 - radius);
        panes[0] = stack;

        for (int i = panes.length - 1; i > 0; i--) {
            Double x = AnchorPane.getLeftAnchor(panes[i]);
            Double y = AnchorPane.getTopAnchor(panes[i]);

            int parentIndex = (int)Math.floor((i - 1) / 2);
            Double px = AnchorPane.getLeftAnchor(panes[parentIndex]);
            Double py = AnchorPane.getTopAnchor(panes[parentIndex]);

            Line line = new Line(x + radius, y + radius, px + radius, py + radius);
            line.setStrokeWidth(2);

            anchorPane.getChildren().add(line);
        }
        anchorPane.getChildren().addAll(panes);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
