package com.lx.managers.image;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * DrawImageTask的管理器
 */
public class DrawImageManager<T> {

    private static ExecutorService executors = Executors.newSingleThreadExecutor((r) -> new Thread(r, "获取列表图片线程"));

    private DrawImageTask<T> drawImageTask = new DrawImageTask<>();

    public void start() {
        executors.execute(drawImageTask);
    }

    public void close() {
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
}
