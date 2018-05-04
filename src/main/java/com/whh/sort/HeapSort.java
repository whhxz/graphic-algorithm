package com.whh.sort;

import javafx.stage.Stage;

import java.util.Arrays;

/**
 * HeapSort
 * Created by xuzhuo on 2018/5/3.
 */
public class HeapSort extends BaseSort {
    @Override
    public void sort() throws Exception {
        System.out.println(Arrays.toString(data));
        //获取最后一个树节点

        int lastIndex = data.length - 1;
        int lastParent = lastIndex >> 1;
        for (int i = lastParent; i >= 0; i--) {
            maxHeap(data, i, lastIndex);
        }
        System.out.println(Arrays.toString(data));

        //从堆中获取第一个到末尾，然后重新生成堆
        for (int i = lastIndex; i >= 0; i--) {
            swap(data, 0, i);
            maxHeap(data, 0, i - 1);
        }
        System.out.println(Arrays.toString(data));
    }

    private void maxHeap(int[] data, int parentIndex, int lastIndex) {
        //计数子节点
        int leftI = (parentIndex << 1) + 1;//2n + 1
        int rightI = leftI + 1;
        if (leftI > lastIndex) return;//表示已经没有子节点
        int maxI = leftI;
        //比较左右节点
        if (rightI <= lastIndex && data[leftI] < data[rightI]) {
            maxI = rightI;
        }
        //如果父节点小于子节点，切换父子节点
        if (data[parentIndex] < data[maxI]) {
            swap(data, parentIndex, maxI);
            //重构后续树节点
            maxHeap(data, maxI, lastIndex);
        }
    }

    private void swap(int[] data, int i, int j) {
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
