package com.lx.utils.asm;

import com.lx.managers.annotation.AnnotationManager;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

public class MainClassAnalysisTest {

    @Test
    public void analysis() throws IOException {
//        new AnnotationManager().process();
//
//        new MainClassAnalysis().analysis();

        int maxValue = (int) Double.MAX_VALUE;
        System.out.println(maxValue);

        BigDecimal bigDecimal = new BigDecimal(Double.MAX_VALUE);
        System.out.println(bigDecimal.intValue());

    }
}