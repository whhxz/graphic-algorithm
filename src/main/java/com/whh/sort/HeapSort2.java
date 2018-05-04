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
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * HeapSort2
 * Created by xuzhuo on 2018/5/3.
 */

class GraphVo {
    int num;
    StackPane stackPane;
    Text text;
    Circle circle;

    public GraphVo(int num, double radius) {
        Circle circle = new Circle(radius);
        circle.setStroke(Color.BLACK);
        circle.setStrokeType(StrokeType.INSIDE);
        circle.setStrokeWidth(2);
        circle.setFill(Color.AZURE);
        circle.relocate(0, 0);
        this.circle = circle;

        Group group = new Group(circle);
        Text text = new Text(String.valueOf(num));
        text.setFont(new Font(18));
        text.setBoundsType(TextBoundsType.VISUAL);
        this.text = text;

        StackPane stack = new StackPane();
        stack.getChildren().addAll(group, text);
        this.stackPane = stack;
        this.num = num;
    }

}

public class HeapSort2 extends Application {
    private static final double radius = 15d;
    private static final double firstTop = 150d;
    private static final int len = 20;
    GraphVo[] graphVos;

    Scene scene;

    AnchorPane anchorPane;

    Button startBtn;

    @Override
    public void start(Stage primaryStage) throws Exception {
        anchorPane = new AnchorPane();
        scene = new Scene(anchorPane, 1000, 700, Color.WHITE);
        addBtn();

        initData(len);
        drawTree();

        primaryStage.setScene(scene);
        primaryStage.show();


    }


    private void initData(int len) {
        graphVos = new GraphVo[len];

        Random random = new Random();
        IntStream.range(0, len).forEach((i) -> graphVos[i] = new GraphVo(random.nextInt(len) + 1, radius));
    }

    private void addBtn() {
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
            reset();
            reset.setDisable(false);
        });
        AnchorPane.setTopAnchor(reset, 5d);
        AnchorPane.setLeftAnchor(reset, (scene.getWidth()) / 2 + 50);
        anchorPane.getChildren().add(reset);
    }

    private void reset() {
        initData(len);
        //删除旧数据
        if (graphVos != null) {
            anchorPane.getChildren().removeAll(anchorPane.getChildren().filtered((e) -> !(e instanceof Button)));
        }
        drawTree();
    }

    private void drawTree() {
        int treeDepth = (int) Math.floor(Math.log(graphVos.length) / Math.log(2));
        double maxWidth = scene.getWidth();
        double depthWidth = ((2 << (treeDepth - 1)) - 1) * (4 * radius);
        for (int i = treeDepth - 1; i >= 0; i--) {
            int leftIndex = (2 << i) - 1;
            int nextIndex = (2 << (i + 1)) - 1;

            double leftCenter = depthWidth / 2;
            double nodeSpace = depthWidth / ((2 << i) - 1);

            for (int j = leftIndex; j < nextIndex && j < graphVos.length; j++) {
                StackPane stack = graphVos[j].stackPane;
                AnchorPane.setTopAnchor(stack, firstTop + (i + 1) * radius * 2 + radius);
                AnchorPane.setLeftAnchor(stack, maxWidth / 2.0 - leftCenter - radius);
                leftCenter = leftCenter - nodeSpace;
            }
            depthWidth = depthWidth - nodeSpace;
        }
        StackPane stack = graphVos[0].stackPane;
        AnchorPane.setTopAnchor(stack, firstTop + radius);
        AnchorPane.setLeftAnchor(stack, maxWidth / 2 - radius);

        //画线
        for (int i = graphVos.length - 1; i > 0; i--) {

            Double x = AnchorPane.getLeftAnchor(graphVos[i].stackPane);
            Double y = AnchorPane.getTopAnchor(graphVos[i].stackPane);

            int parentIndex = (int) Math.floor((i - 1) / 2);
            Double px = AnchorPane.getLeftAnchor(graphVos[parentIndex].stackPane);
            Double py = AnchorPane.getTopAnchor(graphVos[parentIndex].stackPane);

            Line line = new Line(x + radius, y + radius, px + radius, py + radius);
            line.setStrokeWidth(2);

            anchorPane.getChildren().add(line);
        }
        for (GraphVo graphVo : graphVos) {
            anchorPane.getChildren().add(graphVo.stackPane);
        }
    }

    private void sort() throws Exception {
        //获取最后的索引
        int lastIndex = graphVos.length - 1;
        //计算最后一个父节点，因为不需要从子节点开始
        int lastParentIndex = lastIndex >> 1;
        toStr();
        //构造一个最大二叉堆
        for (int i = lastParentIndex; i >= 0; i--) {
            maxHeap(i, lastIndex);
        }
        for (GraphVo graphVo : graphVos) {
            graphVo.circle.setFill(Color.AZURE);
        }
        TimeUnit.SECONDS.sleep(2);
        toStr();
        //从二叉堆中取值，放入数组末尾，之后重新构造新的最大二叉堆
        for (int i = lastIndex; i >= 0; i--) {
            graphVos[0].circle.setFill(Color.YELLOW);
            //交换第一个到末尾
            swap(0, i);
            //重新构造最大二叉堆，已经交换到末尾的数除外，因为根节点已经变更，所以需要重新从根节点构造二叉堆
            maxHeap(0, i - 1);
        }
        toStr();
    }

    private void maxHeap(int parentIndex, int lastIndex) throws Exception {
        //计算父节点左节点 2 * i + 1
        int leftI = (parentIndex << 1) + 1;
        //计算父节点右节点
        int rightI = leftI + 1;
        //如果左节点已经超过最大索引，那么返回，表示已经是最后一个父节点了
        if (leftI > lastIndex) return;
        //知道左右索引最大值，默认为左侧索引
        int maxIndex = leftI;
        //如果右侧索引没超过最大索引，且右侧的值为最大值，那么最大值索引为右边
        if (rightI <= lastIndex && graphVos[rightI].num > graphVos[leftI].num) {
            maxIndex = rightI;
        }
        graphVos[parentIndex].circle.setFill(Color.RED);
        TimeUnit.MILLISECONDS.sleep(100);
        //比较父节点和最大子节点值，如果子节点大，那么需要交换父子节点，
        if (graphVos[maxIndex].num > graphVos[parentIndex].num) {

            swap(maxIndex, parentIndex);
            //因为父节点发生了变动，那么变动的子节点需要重新构造二叉堆，继续向下构造
            maxHeap(maxIndex, lastIndex);
        }
    }

    public void swap(int i, int j) throws Exception {

        GraphVo tmp = graphVos[i];
        graphVos[i] = graphVos[j];
        graphVos[j] = tmp;

        StackPane iStack = graphVos[i].stackPane;
        Double iTop = AnchorPane.getTopAnchor(iStack);
        Double iLeft = AnchorPane.getLeftAnchor(iStack);

        StackPane jStack = graphVos[j].stackPane;
        Double jTop = AnchorPane.getTopAnchor(jStack);
        Double jLeft = AnchorPane.getLeftAnchor(jStack);

        double xMove = (jLeft - iLeft) / 10;
        double yMove = (jTop - iTop) / 10;
        for (int k = 0; k < 10; k++) {
            TimeUnit.MILLISECONDS.sleep(50);

            AnchorPane.setLeftAnchor(jStack, jLeft - xMove * (k + 1));
            AnchorPane.setLeftAnchor(iStack, iLeft + xMove * (k + 1));

            AnchorPane.setTopAnchor(jStack, jTop - yMove * (k + 1));
            AnchorPane.setTopAnchor(iStack, iTop + yMove * (k + 1));

        }
        AnchorPane.setLeftAnchor(jStack, iLeft);
        AnchorPane.setLeftAnchor(iStack, jLeft);

        AnchorPane.setTopAnchor(jStack, iTop);
        AnchorPane.setTopAnchor(iStack, jTop);


    }

    private void toStr() {
        for (GraphVo graphVo : graphVos) {
            System.out.printf("%d ", graphVo.num);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
