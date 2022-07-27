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
     * ÿ��Ԫ�ر߿�Ŀ��
     */
    private static final int ELEMENT_FRAME_WIDTH = 45;

    /**
     * ÿ��Ԫ�ر߿�ĳ���
     */
    private static final int ELEMENT_FRAME_HEIGHT = 45;

    /**
     * ÿ��Ԫ�صĿ��
     */
    private static final int ELEMENT_WIDTH = 8;

    /**
     * ÿ��Ԫ�صĳ���
     */
    private static final int ELEMENT_HEIGHT = 23;

    /**
     * ��βԪ�ؾ���߿�Ŀ��
     */
    private static final int FRAME_WIDTH = 20;

    /**
     * ��βԪ�ؾ���߿�ĸ߶�
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
     * ���������е�����
     */
    public static <T> void draw(List<T> list) throws IOException {


        int elementMaxLen = getElementMaxLen(list);
        System.out.println(elementMaxLen);

        // ���鳤��
        int size = list.size();
        int frameW = size * ELEMENT_FRAME_WIDTH * elementMaxLen + 2 * FRAME_WIDTH;
        int frameH = ELEMENT_FRAME_HEIGHT * elementMaxLen + 2 * FRAME_HEIGHT;


        // �õ�ͼƬ������
        BufferedImage bi = new BufferedImage(frameW, frameH, BufferedImage.TYPE_INT_RGB);
        // �õ����Ļ��ƻ���(����ͼƬ�ı�)
        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        g2.setColor(Color.WHITE); // ���ñ�����ɫ
        g2.fillRect(0, 0, frameW, frameH);// �������ͼƬ(��ʵ�������ñ�����ɫ)
        g2.setColor(Color.black);// ����������ɫ
        g2.setStroke(new BasicStroke(2.0f)); // �߿�Ӵ�
        g2.drawRect(1, 1, frameW - 2, frameH - 2); // ���߿���Ǻڱ߿�

        // ��������������
        drawRowLine(g2, list.size(), elementMaxLen);

        // ��Ԫ��֮�������
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


        g2.dispose(); // �ͷŶ���
        ImageIO.write(bi, "JPEG", new FileOutputStream("./a.jpg"));// ����ͼƬ JPEG��ʾ�����ʽ
    }

    /**
     * ��ȡԪ�س���
     */
    private static <T> int getElementLen(T element) {
        return element == null ? 0 : String.valueOf(element).length();
    }

    /**
     * ��ȡ�б�������Ԫ�س���
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
     * ����������������
     *
     * @param size ��Ҫ����Ԫ�ص�����
     */
    private static void drawRowLine(Graphics2D g2, int size, int maxElementLen) {
        g2.drawLine(FRAME_WIDTH, FRAME_HEIGHT, size * ELEMENT_FRAME_WIDTH + FRAME_WIDTH, FRAME_HEIGHT);
        g2.drawLine(FRAME_WIDTH, ELEMENT_FRAME_HEIGHT + FRAME_HEIGHT, size * ELEMENT_FRAME_WIDTH * maxElementLen + FRAME_WIDTH, ELEMENT_FRAME_HEIGHT + FRAME_HEIGHT);
    }

    /**
     * ����Ԫ��֮�������
     *
     * @param index ��Ҫ���Ƶ�������Ԫ������ + 1��
     */
    private static void drawColumnLine(Graphics2D g2, int index, int maxElementLen) {
        for (int i = 0; i < index; i++) {
            g2.drawLine(FRAME_WIDTH + (i * ELEMENT_FRAME_WIDTH), FRAME_HEIGHT, FRAME_WIDTH + (i * ELEMENT_FRAME_WIDTH * maxElementLen), ELEMENT_FRAME_HEIGHT + FRAME_HEIGHT);
        }
    }

}
