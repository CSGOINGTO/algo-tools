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

        // �õ�ͼƬ������
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // �õ����Ļ��ƻ���(����ͼƬ�ı�)
        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        g2.setColor(Color.WHITE); // ���ñ�����ɫ
        g2.fillRect(0, 0, width, height);// �������ͼƬ(��ʵ�������ñ�����ɫ)
        g2.setColor(Color.black);// ����������ɫ
        g2.setStroke(new BasicStroke(2.0f)); // �߿�Ӵ�
        g2.drawRect(1, 1, width - 2, height - 2); // ���߿���Ǻڱ߿�


        BufferedImage image1 = ImageIO.read(new File(GifDemo.class.getResource("/1.png").getFile()));
        BufferedImage image2 = ImageIO.read(new File(GifDemo.class.getResource("/2.png").getFile()));
        BufferedImage image3 = ImageIO.read(new File(GifDemo.class.getResource("/3.png").getFile()));
        AnimatedGifEncoder e = new AnimatedGifEncoder();
        // ��������ͼƬ��С
        e.setSize(900, 1000);
        //���ɵ�ͼƬ·��
        e.start(new FileOutputStream("./testgif.gif"));
        //ͼƬ֮����ʱ��
        e.setDelay(500);
        //�ظ����� 0��ʾ�����ظ� Ĭ�ϲ��ظ�
        e.setRepeat(1);
        //���ͼƬ
        e.addFrame(image1);
        e.addFrame(image2);
        e.addFrame(image3);
        e.finish();
    }
}
