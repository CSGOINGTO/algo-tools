package com.lx.collections;

import com.madgag.gif.fmsware.AnimatedGifEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

public class GifDemo {
    public static void main(String[] args) throws Exception {
        int width = 1000;
        int height = 800;

        // 得到图片缓冲区
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 得到它的绘制环境(这张图片的笔)
        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        g2.setColor(Color.WHITE); // 设置背景颜色
        g2.fillRect(0, 0, width, height);// 填充整张图片(其实就是设置背景颜色)
        g2.setColor(Color.black);// 设置字体颜色
        g2.setStroke(new BasicStroke(2.0f)); // 边框加粗
        g2.drawRect(1, 1, width - 2, height - 2); // 画边框就是黑边框


        BufferedImage image1 = ImageIO.read(new File(GifDemo.class.getResource("/1.png").getFile()));
        BufferedImage image2 = ImageIO.read(new File(GifDemo.class.getResource("/2.png").getFile()));
        BufferedImage image3 = ImageIO.read(new File(GifDemo.class.getResource("/3.png").getFile()));
        AnimatedGifEncoder e = new AnimatedGifEncoder();
        // 设置生成图片大小
        e.setSize(900, 1000);
        //生成的图片路径
        e.start(new FileOutputStream("./testgif.gif"));
        //图片之间间隔时间
        e.setDelay(500);
        //重复次数 0表示无限重复 默认不重复
        e.setRepeat(1);
        //添加图片
        e.addFrame(image1);
        e.addFrame(image2);
        e.addFrame(image3);
        e.finish();
    }
}
