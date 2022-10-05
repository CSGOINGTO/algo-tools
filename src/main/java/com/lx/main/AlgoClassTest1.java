package com.lx.main;

import com.lx.annotations.AlgoMethod;
import com.lx.annotations.GifParam;
import com.lx.managers.image.DrawImageManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlgoClassTest1 {

    @AlgoMethod
    @GifParam(delayTime = 1000)
    public void process1() {
        List<Integer> list = new ArrayList<>();
        DrawImageManager<Integer> drawImageManager = new DrawImageManager<>();
        drawImageManager.start();
        for (int i = 0; i < 10; i++) {
            list.add(i * 10);
            drawImageManager.putList(new ArrayList<>(list));
        }

//        List<String> list1 = new ArrayList<>();
//        list1.add("a");
//        list1.add("b");
//        list1.add("c");
//        list1.add("aasdasda");
//        list1.add("aqwedasd");
//        list1.add("aqweqwe");
//        list1.add("aqwe");
//        list1.add("aqwe");
//        list1.add("aqwe");
//        DrawImages.draw(list1);

    }

    @AlgoMethod
    public void process2() throws IOException {
        List<Integer> list = new ArrayList<>();
        DrawImageManager<Integer> drawImageManager = new DrawImageManager<>();
        drawImageManager.start();
        for (int i = 0; i < 10; i++) {
            list.add(i * 10);
            drawImageManager.putList(new ArrayList<>(list));
        }

//        List<String> list1 = new ArrayList<>();
//        list1.add("a");
//        list1.add("b");
//        list1.add("c");
//        list1.add("aasdasda");
//        list1.add("aqwedasd");
//        list1.add("aqweqwe");
//        list1.add("aqwe");
//        list1.add("aqwe");
//        list1.add("aqwe");
//        DrawImages.draw(list1);

    }
}
