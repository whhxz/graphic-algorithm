package com.whh.sort;

import com.whh.uil.SortUtils;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * QuickSort
 * Created by xuzhuo on 2018/5/3.
 */
public class QuickSort extends BaseSort {
    @Override
    public void sort() throws Exception {
        System.out.println(Arrays.toString(data));
        //开始排序
        sort(data, 0, data.length - 1);
        System.out.println(Arrays.toString(data));
    }

    /**
     *
     * @param data 排序数组
     * @param leftIndex 数组左侧索引
     * @param rightIndex 右侧索引
     * @throws Exception
     */
    public void sort(int[] data, int leftIndex, int rightIndex)throws Exception{
        //如果支持索引大于右侧索引，表已经递归结束
        if (leftIndex >= rightIndex){
            if (rightIndex < 0){
                rectangles[leftIndex].setFill(Color.YELLOW);
            }else {
                rectangles[rightIndex].setFill(Color.YELLOW);
            }
            return;
        }
        //获取基准索引，存储采用的事中间值，也可以采用随机数
        int pivot = (leftIndex + rightIndex)/2;
        //交换基准元素到最后
        rectangles[pivot].setFill(Color.BLACK);
        SortUtils.changeData(data, rectangles, pivot, rightIndex, 200);

        //记录交换索引的位置，用于交换小于基准的值，默认为最左侧索引
        int swapIndex = leftIndex;
        //从左遍历到右
        for (int i = leftIndex; i < rightIndex; i++) {
            rectangles[i].setFill(Color.RED);
            TimeUnit.MILLISECONDS.sleep(200);
            //如果遍历的值小于基准，那么把该值交换到之前记录的左侧索引位置，然后索引位置+1
            if (data[i] <= data[rightIndex]){
                SortUtils.changeData(data, rectangles, swapIndex, i, 200);
                rectangles[i].setFill(Color.RED);
                rectangles[swapIndex].setFill(Color.BLUE);
                swapIndex++;
            }
            rectangles[i].setFill(Color.BLUE);
        }
        //一次遍历完成后，把最右侧的值（基准）替换到记录的索引位置，此时，左侧数据<=基准，右侧>基准
        SortUtils.changeData(data, rectangles, swapIndex, rightIndex, 200);
        rectangles[swapIndex].setFill(Color.YELLOW);
        //递归基准左右两侧数组
        sort(data, leftIndex, swapIndex - 1);
        sort(data, swapIndex + 1, rightIndex);
    }

    public void swap(int[]data, int i, int j){
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        init(20);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
