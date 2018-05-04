package com.whh.sort;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * MergeSort
 * Created by xuzhuo on 2018/5/2.
 */
public class MergeSort extends BaseSort {
    private Rectangle[] tmpRectangles = null;

    @Override
    public void sort() throws Exception {
        tmpRectangles = new Rectangle[data.length];

        //构造一个新的数组，用于临时存储数据
        int[] tmpArr = new int[data.length];
        System.out.println(Arrays.toString(data));
        //递归调用
        sortRecursive(data, tmpArr, 0, tmpArr.length - 1);
        System.out.println(Arrays.toString(data));

    }

    /**
     * @param data 原数组
     * @param result 临时数组
     * @param start 数组排序开始索引
     * @param end 数组排序结束索引
     * @throws Exception
     */
    public void sortRecursive(int[] data, int[] result, int start, int end) throws Exception {
        //如果开始索引大于结束索引，表示已经拆分为最小了，直接返回
        if (start >= end){
            return;
        }
        //计算数组中间值，用于拆分排序
        int mid = ((end - start) >> 1) + start;
        //拆分数组起始值为start ~ mid  | mid + 1 ~ end
        int start1 = start, end1 = mid;
        int start2 = mid + 1, end2 = end;
        //对拆分的数组进行递归拆分排序
        sortRecursive(data, result, start1, end1);
        sortRecursive(data, result, start2, end2);

        //这里表示递归拆分完毕，开始排序
        int i = start;
        //先两个数组取值
        Rectangle rectangle;
        //排序拆分的两个数组，核心原理就是从数组中取值比较后放入临时数组
        while (start1 <= end1 && start2 <= end2){
            //下面逻辑可以写为如下的一句话，为了图形展示，进行了拆分
//            result[i++] = data[start1] <= data[start2]? data[start1++]: data[start2++];
            //如果左侧值<=右侧的值，那么取左侧值到临时数组中，同时左侧角标+1，表示标记左侧取值索引
            if (data[start1] <= data[start2]){
                rectangle = rectangles[start1];
                result[i] = data[start1];
                tmpRectangles[i] = rectangles[start1];
                start1++;
            } else {
                //否则取右侧的值，同时右侧角标+1
                rectangle = rectangles[start2];
                result[i] = data[start2];
                tmpRectangles[i] = rectangles[start2];
                start2++;
            }
            //移动到上面，重新确定位置
            rectangle.setFill(Color.RED);
            TimeUnit.MILLISECONDS.sleep(100);
            AnchorPane.setBottomAnchor(rectangle, anchorPane.getHeight()/2);
            TimeUnit.MILLISECONDS.sleep(100);
            AnchorPane.setLeftAnchor(rectangle, BORDER_WIDTH * (i + 1) + i * rectangle.getWidth());

            //临时数组存入的值+1，表示已经存入的值位置
            i++;
        }
        //这里如果第二个数组取值完毕，把数组1中值全部移动到临时数组中
        while (start1 <= end1){
            rectangle = rectangles[start1];

            result[i] = data[start1];
            tmpRectangles[i] = rectangles[start1];

            rectangle.setFill(Color.RED);
            TimeUnit.MILLISECONDS.sleep(100);
            AnchorPane.setBottomAnchor(rectangle, anchorPane.getHeight()/2);
            TimeUnit.MILLISECONDS.sleep(100);
            AnchorPane.setLeftAnchor(rectangle, BORDER_WIDTH * (i + 1) + i * rectangle.getWidth());

            i++;
            start1++;

        }
        //这里表示如果第一个数组取值完毕，把数组2中值全部移动到临时数组中
        while (start2 <= end2){
            rectangle = rectangles[start2];

            result[i] = data[start2];
            tmpRectangles[i] = rectangles[start2];

            rectangle.setFill(Color.RED);
            TimeUnit.MILLISECONDS.sleep(100);
            AnchorPane.setBottomAnchor(rectangle, anchorPane.getHeight()/2);
            TimeUnit.MILLISECONDS.sleep(100);
            AnchorPane.setLeftAnchor(rectangle, BORDER_WIDTH * (i + 1) + i * rectangle.getWidth());

            i++;
            start2++;
        }
        //把临时数组中数据移动到原数组中
        for (int j = start; j <= end; j++) {
            data[j] = result[j];
            rectangles[j] = tmpRectangles[j];
            rectangle = rectangles[j];
            TimeUnit.MILLISECONDS.sleep(100);
            rectangle.setFill(Color.YELLOW);
            AnchorPane.setBottomAnchor(rectangle, 0d);
            AnchorPane.setLeftAnchor(rectangle, BORDER_WIDTH * (j + 1) + j * rectangle.getWidth());

        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        init(20);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    protected void initData(int len) {
        //删除旧数据
        if (rectangles != null) {
            anchorPane.getChildren().removeAll(rectangles);
        }
        //初始化数据
        data = new int[len];
        Random random = new Random();
        IntStream.range(0, len).forEach((i) -> data[i] = random.nextInt(len) + 1);
        rectangles = new Rectangle[len];

        //单位长宽
        double unitColumnWidth = ((anchorPane.getWidth() - BORDER_WIDTH) / rectangles.length) - BORDER_WIDTH;
        double unitColumnHeight = ((anchorPane.getHeight() - BORDER_WIDTH * 10) / rectangles.length) / 2;

        //初始化长方形数据
        IntStream.range(0, len).forEach(i -> {
            rectangles[i] = new Rectangle(unitColumnWidth, data[i] * unitColumnHeight);
        });


        anchorPane.getChildren().addAll(rectangles);

        //初始化画图
        initDraw();

    }

    public static void main(String[] args) {
        launch(args);

    }
}
