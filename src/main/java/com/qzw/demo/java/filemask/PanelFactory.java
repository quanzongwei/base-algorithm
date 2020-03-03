package com.qzw.demo.java.filemask;

import java.awt.*;

/**
 * @author BG388892
 * @date 2020/2/24
 */
public class PanelFactory {


    public static Panel generatePanelItem(Button btn1, Button btn2, Button btn3, String label) {
        Panel panel = new Panel(new BorderLayout(0,10));
        panel.setBackground(new Color(190, 190, 190));
        panel.setMinimumSize(new Dimension(650, 40));
        panel.setMaximumSize(new Dimension(650,40));
        Panel subPanel1 = new Panel(new FlowLayout(FlowLayout.LEFT));
        subPanel1.add(new Label(label));
        Panel subPanel2 = new Panel(new FlowLayout(FlowLayout.RIGHT));
        if (btn1 != null) {
            subPanel2.add(btn1);
        }
        if (btn2 != null) {
            subPanel2.add(btn2);
        }
        subPanel2.add(btn3);
        panel.add(subPanel1,BorderLayout.NORTH);
        panel.add(subPanel2,BorderLayout.CENTER);
        return panel;
    }
}
