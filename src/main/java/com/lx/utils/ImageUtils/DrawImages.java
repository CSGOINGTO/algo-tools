package com.lx.utils.ImageUtils;

import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DrawImages {

    /**
     * 每个元素边框的宽度
     */
    private static final int ELEMENT_FRAME_WIDTH = 100;

    /**
     * 每个元素边框的长度
     */
    private static final int ELEMENT_FRAME_HEIGHT = 100;

    /**
     * 首尾元素距离边框的宽度
     */
    private static final int FRAME_WIDTH = 20;

    /**
     * 首尾元素距离边框的高度
     */
    private static final int FRAME_HEIGHT = 10;

    public static void main(String[] args) throws IOException {
        List<Integer> list = new ArrayList<>();
        DrawImageManage<Integer> drawImageManage = new DrawImageManage<>();
        drawImageManage.start();
        for (int i = 0; i < 10; i++) {
            list.add(i * 10);
            drawImageManage.putList(new ArrayList<>(list));
        }
        if (drawImageManage.drawGIF()) {
            drawImageManage.close();
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

    /**
     * 绘制数组中的内容，并得到图片
     */
    public static <T> BufferedImage draw(List<T> list, String imageName) throws IOException {

        // 数组长度
        int size = list.size();
        // 画板的总宽度
        int frameW = size * ELEMENT_FRAME_WIDTH + 2 * FRAME_WIDTH;
        // 画板的总高度
        int frameH = ELEMENT_FRAME_HEIGHT + 2 * FRAME_HEIGHT;

        // 得到图片缓冲区
        BufferedImage bi = new BufferedImage(frameW, frameH, BufferedImage.TYPE_INT_RGB);

        // 得到它的绘制环境(这张图片的笔)
        Graphics2D g2 = (Graphics2D) bi.getGraphics();

        // 设置Graphics2D对象g2的参数
        setGraphicsParam(g2, frameW, frameH);

        // 绘制画板列表的上下两条横线
        drawRowLine(g2, size);

        // 绘制画板列表元素之间的竖线
        drawColumnLine(g2, size + 1);

        // 填充列表元素
        drawElement(g2, list);

        // 释放对象
        g2.dispose();

        // 保存图片
        ImageIO.write(bi, "png", new FileOutputStream("./" + imageName + ".png"));
        return bi;
    }

    /**
     * 获取元素长度
     */
    private static <T> int getElementLen(T element) {
        return element == null ? 0 : String.valueOf(element).length();
    }

    /**
     * 获取列表中最大的元素长度
     */
    private static <T> int getElementMaxLen(List<T> list) {
        int maxLen = 0;
        if (list == null || list.size() == 0) return maxLen;
        for (T element : list) {
            maxLen = Math.max(maxLen, getElementLen(element));
        }
        return maxLen;
    }

    private static <T> void drawElement(Graphics2D g2, List<T> list) {
        int fontSize = g2.getFont().getSize();
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(g2.getFont());
        for (int index = 0; index < list.size(); index++) {
            String content = String.valueOf(list.get(index));
            g2.drawString(content, FRAME_WIDTH + (index * ELEMENT_FRAME_WIDTH) + (ELEMENT_FRAME_WIDTH / 2) - (fontSize / 2 * content.length() / 2), FRAME_HEIGHT + ELEMENT_FRAME_HEIGHT / 2 - fontSize / 2 + metrics.getAscent());
        }
    }

    private static void setGraphicsParam(Graphics2D g2, int frameW, int frameH) {
        g2.setColor(Color.WHITE); // 设置背景颜色
        g2.fillRect(0, 0, frameW, frameH);// 填充整张图片(其实就是设置背景颜色)
        g2.setColor(Color.black);// 设置字体颜色
        g2.setStroke(new BasicStroke(2.0f)); // 边框加粗
        g2.drawRect(1, 1, frameW - 2, frameH - 2); // 画边框就是黑边框
        Font font = new Font("Times New Roman", Font.BOLD, 20);
        g2.setFont(font);
    }

    /**
     * 绘制上下两条横线
     *
     * @param size 需要绘制元素的总数
     */
    private static void drawRowLine(Graphics2D g2, int size) {
        g2.drawLine(FRAME_WIDTH, FRAME_HEIGHT, size * ELEMENT_FRAME_WIDTH + FRAME_WIDTH, FRAME_HEIGHT);
        g2.drawLine(FRAME_WIDTH, ELEMENT_FRAME_HEIGHT + FRAME_HEIGHT, size * ELEMENT_FRAME_WIDTH + FRAME_WIDTH, ELEMENT_FRAME_HEIGHT + FRAME_HEIGHT);
    }

    /**
     * 绘制元素之间的竖线
     *
     * @param index 需要绘制的条数（元素总数 + 1）
     */
    private static void drawColumnLine(Graphics2D g2, int index) {
        for (int i = 0; i < index; i++) {
            g2.drawLine(FRAME_WIDTH + (i * ELEMENT_FRAME_WIDTH), FRAME_HEIGHT, FRAME_WIDTH + (i * ELEMENT_FRAME_WIDTH), ELEMENT_FRAME_HEIGHT + FRAME_HEIGHT);
        }
    }

}
