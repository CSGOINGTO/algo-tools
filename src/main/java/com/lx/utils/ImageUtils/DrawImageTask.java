package com.lx.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class DrawImageTask<T> implements Runnable {

    public BlockingDeque<List<T>> listBlockingDeque = new LinkedBlockingDeque<>();

    private AtomicInteger listIndex = new AtomicInteger(0);

    public List<BufferedImage> bufferedImageList = new ArrayList<>();

    public volatile boolean isRun = true;

    @Override
    public void run() {
        System.out.println("准备开始绘制图片！");
        while (isRun) {
            try {
                List<T> list = listBlockingDeque.take();
                System.out.println("绘制第" + listIndex.get() + "张List图片");
                BufferedImage draw = DrawImages.draw(list, String.valueOf(listIndex.getAndIncrement()));
                bufferedImageList.add(draw);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
