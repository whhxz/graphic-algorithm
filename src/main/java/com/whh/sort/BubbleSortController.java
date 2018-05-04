package com.whh.sort;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * BubbleSortController
 * Created by xuzhuo on 2018/4/28.
 */
public class BubbleSortController implements Initializable {
    @FXML
    Button btn;
    @FXML
    Label time;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void onBtnClick(MouseEvent event) {
        time.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
