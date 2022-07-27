package com.lx.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DrawImages<T> {

    /**
     * 每个元素边框的宽度
     */
    private static final int ELEMENT_FRAME_WIDTH = 45;

    /**
     * 每个元素边框的长度
     */
    private static final int ELEMENT_FRAME_HEIGHT = 45;

    /**
     * 每个元素的宽度
     */
    private static final int ELEMENT_WIDTH = 8;

    /**
     * 每个元素的长度
     */
    private static final int ELEMENT_HEIGHT = 23;

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
        list.add(1234);
        list.add(245);
        list.add(32);
        list.add(1);
        list.add(12345);
        list.add(123456);
        list.add(1234567);
        list.add(12345678);
        DrawImages.draw(list);
    }

    /**
     * 绘制数组中的内容
     */
    public static <T> void draw(List<T> list) throws IOException {


        int elementMaxLen = getElementMaxLen(list);
        System.out.println(elementMaxLen);

        // 数组长度
        int size = list.size();
        int frameW = size * ELEMENT_FRAME_WIDTH * elementMaxLen + 2 * FRAME_WIDTH;
        int frameH = ELEMENT_FRAME_HEIGHT * elementMaxLen + 2 * FRAME_HEIGHT;


        // 得到图片缓冲区
        BufferedImage bi = new BufferedImage(frameW, frameH, BufferedImage.TYPE_INT_RGB);
        // 得到它的绘制环境(这张图片的笔)
        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        g2.setColor(Color.WHITE); // 设置背景颜色
        g2.fillRect(0, 0, frameW, frameH);// 填充整张图片(其实就是设置背景颜色)
        g2.setColor(Color.black);// 设置字体颜色
        g2.setStroke(new BasicStroke(2.0f)); // 边框加粗
        g2.drawRect(1, 1, frameW - 2, frameH - 2); // 画边框就是黑边框

        // 画上下两条横线
        drawRowLine(g2, list.size(), elementMaxLen);

        // 画元素之间的竖线
        drawColumnLine(g2, list.size() + 1, elementMaxLen);

        setElementFront(g2);

        for (int i = 0; i < list.size(); i++) {
            drawElement(g2, list.get(i), i);
        }

//        g2.drawString(String.valueOf(list.get(0)), leftW + ELEMENT_WIDTH, bottomH - ELEMENT_HEIGHT);
//
//
//        g2.drawString(String.valueOf(list.get(1)), leftW + 1 * ELEMENT_FRAME_WIDTH + ELEMENT_WIDTH, bottomH - ELEMENT_HEIGHT);
//
//
//        g2.drawString(String.valueOf(list.get(2)), leftW + 2 * ELEMENT_FRAME_WIDTH + ELEMENT_WIDTH, bottomH - ELEMENT_HEIGHT);


        g2.dispose(); // 释放对象
        ImageIO.write(bi, "JPEG", new FileOutputStream("./a.jpg"));// 保存图片 JPEG表示保存格式
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

    private static <T> void drawElement(Graphics2D g2, T element, int index) {
        g2.drawString(String.valueOf(element), FRAME_WIDTH + (index * ELEMENT_FRAME_WIDTH) + ELEMENT_WIDTH, ELEMENT_HEIGHT + FRAME_HEIGHT + g2.getFont().getSize());
    }

    private static void setElementFront(Graphics2D g2) {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.scale(1d, 3d);

        // create font using that transform
        Font stretchedFont = g2.getFont().deriveFont(affineTransform);

        g2.setFont(stretchedFont);
    }

    /**
     * 绘制上下两条横线
     *
     * @param size 需要绘制元素的总数
     */
    private static void drawRowLine(Graphics2D g2, int size, int maxElementLen) {
        g2.drawLine(FRAME_WIDTH, FRAME_HEIGHT, size * ELEMENT_FRAME_WIDTH + FRAME_WIDTH, FRAME_HEIGHT);
        g2.drawLine(FRAME_WIDTH, ELEMENT_FRAME_HEIGHT + FRAME_HEIGHT, size * ELEMENT_FRAME_WIDTH * maxElementLen + FRAME_WIDTH, ELEMENT_FRAME_HEIGHT + FRAME_HEIGHT);
    }

    /**
     * 绘制元素之间的竖线
     *
     * @param index 需要绘制的条数（元素总数 + 1）
     */
    private static void drawColumnLine(Graphics2D g2, int index, int maxElementLen) {
        for (int i = 0; i < index; i++) {
            g2.drawLine(FRAME_WIDTH + (i * ELEMENT_FRAME_WIDTH), FRAME_HEIGHT, FRAME_WIDTH + (i * ELEMENT_FRAME_WIDTH * maxElementLen), ELEMENT_FRAME_HEIGHT + FRAME_HEIGHT);
        }
    }

}
