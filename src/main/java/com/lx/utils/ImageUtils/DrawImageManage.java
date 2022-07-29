package com.lx.utils.ImageUtils;

import com.madgag.gif.fmsware.AnimatedGifEncoder;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DrawImageManage<T> {

    private static ExecutorService executors = Executors.newSingleThreadExecutor((r) -> new Thread(r, "获取列表图片线程"));

    private DrawImageTask<T> drawImageTask = new DrawImageTask<>();


    public void start() {
        executors.execute(drawImageTask);
    }

    public void close() {
        drawImageTask.isRun = false;
        executors.shutdown();
        System.out.println("线程池关闭！");
    }

    public void putList(List<T> list) {
        try {
            System.out.println(list);
            drawImageTask.listBlockingDeque.put(list);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean drawGIF() throws FileNotFoundException {
        AnimatedGifEncoder e = new AnimatedGifEncoder();
        // 设置生成图片大小
        e.setSize(900, 1000);
        //生成的图片路径
        e.start(new FileOutputStream("./testgif.gif"));
        //图片之间间隔时间
        e.setDelay(500);
        //重复次数 0表示无限重复 默认不重复
        e.setRepeat(0);
        //添加图片
        List<BufferedImage> bufferedImageList = drawImageTask.bufferedImageList;
        if (bufferedImageList != null && bufferedImageList.size() != 0)
            bufferedImageList.forEach(e::addFrame);
        System.out.println("git绘制完毕！");
        return e.finish();

    }

}
