package com.lx.utils.ImageUtils;

import com.madgag.gif.fmsware.AnimatedGifEncoder;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class DrawImageTask<T> implements Runnable {

    /**
     * 存放需要绘制Image的List
     */
    public BlockingDeque<List<T>> listBlockingDeque = new LinkedBlockingDeque<>();

    /**
     * 存放绘制完成的Image
     */
    public List<BufferedImage> bufferedImageList = new ArrayList<>();

    /**
     * 对绘制Image的结果进行计数
     */
    private AtomicInteger listIndex = new AtomicInteger(0);

    /**
     * 对本次绘制进行监听的监听器
     */
    private DrawImageListener drawImageListener;

    public DrawImageTask() {
    }

    public DrawImageTask(DrawImageListener drawImageListener) {
        this.drawImageListener = drawImageListener;
    }

    @Override
    public void run() {
        System.out.println("准备开始绘制图片！");
        while (!drawImageListener.isTaskCompleted) {
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

    public void drawGIF() throws FileNotFoundException {
        AnimatedGifEncoder e = new AnimatedGifEncoder();
        // 设置生成图片大小
        e.setSize(900, 1000);
        //生成的图片路径
        e.start(new FileOutputStream("./gif.gif"));
        //图片之间间隔时间
        e.setDelay(500);
        //重复次数 0表示无限重复 默认不重复
        e.setRepeat(0);
        //添加图片
        if (bufferedImageList != null && bufferedImageList.size() != 0)
            bufferedImageList.forEach(e::addFrame);
        System.out.println("git绘制完毕！");
        e.finish();
    }
}
