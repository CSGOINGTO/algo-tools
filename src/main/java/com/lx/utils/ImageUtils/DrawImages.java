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
     * ÿ��Ԫ�ر߿�Ŀ��
     */
    private static final int ELEMENT_FRAME_WIDTH = 100;

    /**
     * ÿ��Ԫ�ر߿�ĳ���
     */
    private static final int ELEMENT_FRAME_HEIGHT = 100;

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
        list.add(123456789);
        list.add(1234567890);
        DrawImages.draw(list, "1");

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
     * ���������е����ݣ����õ�ͼƬ
     */
    public static <T> void draw(List<T> list, String imageName) throws IOException {

        // ���鳤��
        int size = list.size();
        // ������ܿ��
        int frameW = size * ELEMENT_FRAME_WIDTH + 2 * FRAME_WIDTH;
        // ������ܸ߶�
        int frameH = ELEMENT_FRAME_HEIGHT + 2 * FRAME_HEIGHT;

        // �õ�ͼƬ������
        BufferedImage bi = new BufferedImage(frameW, frameH, BufferedImage.TYPE_INT_RGB);

        // �õ����Ļ��ƻ���(����ͼƬ�ı�)
        Graphics2D g2 = (Graphics2D) bi.getGraphics();

        // ����Graphics2D����g2�Ĳ���
        setGraphicsParam(g2, frameW, frameH);

        // ���ƻ����б��������������
        drawRowLine(g2, size);

        // ���ƻ����б�Ԫ��֮�������
        drawColumnLine(g2, size + 1);

        // ����б�Ԫ��
        drawElement(g2, list);

        // �ͷŶ���
        g2.dispose();

        // ����ͼƬ
        ImageIO.write(bi, "png", new FileOutputStream("./" + imageName + ".png"));
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

    private static <T> void drawElement(Graphics2D g2, List<T> list) {
        int fontSize = g2.getFont().getSize();
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(g2.getFont());
        for (int index = 0; index < list.size(); index++) {
            String content = String.valueOf(list.get(index));
            g2.drawString(content, FRAME_WIDTH + (index * ELEMENT_FRAME_WIDTH) + (ELEMENT_FRAME_WIDTH / 2) - (fontSize / 2 * content.length() / 2), FRAME_HEIGHT + ELEMENT_FRAME_HEIGHT / 2 - fontSize / 2 + metrics.getAscent());
        }
    }

    private static void setGraphicsParam(Graphics2D g2, int frameW, int frameH) {
        g2.setColor(Color.WHITE); // ���ñ�����ɫ
        g2.fillRect(0, 0, frameW, frameH);// �������ͼƬ(��ʵ�������ñ�����ɫ)
        g2.setColor(Color.black);// ����������ɫ
        g2.setStroke(new BasicStroke(2.0f)); // �߿�Ӵ�
        g2.drawRect(1, 1, frameW - 2, frameH - 2); // ���߿���Ǻڱ߿�
        Font font = new Font("Times New Roman", Font.BOLD, 20);
        g2.setFont(font);
    }

    /**
     * ����������������
     *
     * @param size ��Ҫ����Ԫ�ص�����
     */
    private static void drawRowLine(Graphics2D g2, int size) {
        g2.drawLine(FRAME_WIDTH, FRAME_HEIGHT, size * ELEMENT_FRAME_WIDTH + FRAME_WIDTH, FRAME_HEIGHT);
        g2.drawLine(FRAME_WIDTH, ELEMENT_FRAME_HEIGHT + FRAME_HEIGHT, size * ELEMENT_FRAME_WIDTH + FRAME_WIDTH, ELEMENT_FRAME_HEIGHT + FRAME_HEIGHT);
    }

    /**
     * ����Ԫ��֮�������
     *
     * @param index ��Ҫ���Ƶ�������Ԫ������ + 1��
     */
    private static void drawColumnLine(Graphics2D g2, int index) {
        for (int i = 0; i < index; i++) {
            g2.drawLine(FRAME_WIDTH + (i * ELEMENT_FRAME_WIDTH), FRAME_HEIGHT, FRAME_WIDTH + (i * ELEMENT_FRAME_WIDTH), ELEMENT_FRAME_HEIGHT + FRAME_HEIGHT);
        }
    }

}
