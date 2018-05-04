package com.whh.uil;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * SortUtils
 * Created by xuzhuo on 2018/4/28.
 */
public class SortUtils {
    public static void move(Rectangle left, Rectangle right, long time) {
        int moveTime = 10;

        Double rightLen = AnchorPane.getLeftAnchor(right);
        Double leftLen = AnchorPane.getLeftAnchor(left);

        double move = rightLen - leftLen;
        IntStream.range(0, moveTime).forEach((i) -> {
            try {
                TimeUnit.MILLISECONDS.sleep(time / moveTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            AnchorPane.setLeftAnchor(left, leftLen + (move / 10) * i);
            AnchorPane.setLeftAnchor(right, rightLen - (move / 10) * i);
        });
        AnchorPane.setLeftAnchor(left, rightLen);
        AnchorPane.setLeftAnchor(right, leftLen);
    }

    public static void exchange(int[] data, Rectangle[] rectangles, int left, int right, long time) {
        //如果右边较大，调换数组，否则只移动颜色
        if (data[left] > data[right]){
            int tmp = data[left];
            data[left] = data[right];
            data[right] = tmp;
            move(rectangles[left], rectangles[right], time);

            Rectangle tmpR = rectangles[left];
            rectangles[left] = rectangles[right];
            rectangles[right] = tmpR;
        } else {
            //move color
            Paint tmpFill = rectangles[left].getFill();
            rectangles[left].setFill(rectangles[right].getFill());
            rectangles[right].setFill(tmpFill);
        }
    }

    public static void changeColor(Rectangle[]rectangles, int i, int j){
        Paint iColor = rectangles[i].getFill();
        Paint jColor = rectangles[j].getFill();
        rectangles[i].setFill(jColor);
        rectangles[j].setFill(iColor);
    }
    public static void changeData(int[] data, Rectangle[] rectangles, int left, int right, long time){
        if (left == right)return;

        int tmp = data[left];
        data[left] = data[right];
        data[right] = tmp;

        Rectangle tmpR = rectangles[left];
        rectangles[left] = rectangles[right];
        rectangles[right] = tmpR;

        move(rectangles[left], rectangles[right], time);
    }
}
