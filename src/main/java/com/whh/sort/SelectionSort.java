package com.whh.sort;

import com.whh.uil.SortUtils;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

/**
 * SelectionSort
 * Created by xuzhuo on 2018/4/29.
 */
public class SelectionSort extends BaseSort {
    @Override
    public void start(Stage primaryStage) throws Exception {
        init(20);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void sort() {
        //从头遍历到尾
        for (int i = 0; i < data.length; i++) {
            //记录最小索引
            int minIndex = i;

            Rectangle rectangle = rectangles[i];//忽略
            rectangle.setFill(Color.YELLOW);//忽略
            //从当前位置继续向后遍历，i 的数据表示已经做好了排序
            for (int j = i + 1; j < data.length; j++) {
                rectangles[j].setFill(Color.RED);//忽略
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //如果向后遍历过程中，找到了最小值，所在最小索引为当前j
                if (data[minIndex] > data[j]){
                    rectangles[minIndex].setFill(Color.BLUE);
                    rectangles[j].setFill(Color.YELLOW);
                    minIndex = j;
                } else {
                    rectangles[j].setFill(Color.BLUE);
                }
            }
            //向右遍历完毕后，交换最小索引 和 i 的值
            SortUtils.changeData(data, rectangles, i, minIndex, 300);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
