package com.whh.sort;

import com.whh.uil.SortUtils;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

/**
 * InsertSort
 * Created by xuzhuo on 2018/5/2.
 */
public class InsertSort extends BaseSort {
    @Override
    public void sort() {
        //从左遍历数组
        for (int i = 0; i < data.length; i++) {

            rectangles[i].setFill(Color.RED);
            //遍历节点i左边数组，目的是为了找到i节点在左侧已经排好序的数组中所在位置
            int insertIndex = i - 1;
            for (; insertIndex >= 0; insertIndex--) {
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //如果从i节点反向遍历过程中，下一个节点大于当前节点那么交换二者位置
                //因为内循环是从i-1节点进行，比较的是i-1的左侧节点，如果左侧节点较大，那么交换位置
                if (data[insertIndex] > data[insertIndex + 1]) {
                    SortUtils.changeData(data, rectangles, insertIndex, insertIndex + 1, 100);
                } else {
                    //如果左侧节点较小那么跳出内循环
                    break;
                }
            }
            rectangles[insertIndex + 1].setFill(Color.YELLOW);
        }
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
