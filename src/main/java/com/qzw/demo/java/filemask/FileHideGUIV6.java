package com.qzw.demo.java.filemask;

import com.qzw.demo.java.filehide.FileHideException;
import com.qzw.demo.java.filehide.FileHideV6;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * v2 支持选择文件夹
 * v3 自我修改二进制数据, 失败则报错
 * v4 保存v3的一些例子
 * v5 移动到filemask中
 * v6 pannel移动到一个PanelFactory类中,并给出button click的监听事件
 *
 * @author BG388892
 * @date 2020/1/3
 */
@Log4j2
public class FileHideGUIV6 {
    Logger loggerFactory = LoggerFactory.getLogger("");
    public static String LINE_SEPARATOR = System.getProperty("line.separator");
    // 修改为jFrame就成了swing的了, 就能使用jButton了
    public static Frame f = null;
    static FileDialog fileDialog = null;
    public static TextArea ta;
    public static Dialog dialog;
    static Button dialogOkBtn;
    public static Label label;
    static MenuBar menuBar;
    static Menu menu;
    static MenuItem menuItem4Open = null;
    static MenuItem menuItem4Exit = null;

    static Button btn4NameEncrypt = new Button("文件名称加密");
    static Button btn4NameDecrypt = new Button("文件名称解密");

    static Button btn4ContentEncrypt = new Button("文件内容加密");
    static Button btn4ContentDecrypt = new Button("文件内容解密");


    static JButton jButton;

    public static void main(String[] args) {
        f = new Frame("FileMask");
        f.setLayout(new BorderLayout(0, 10));

        f.setSize(650, 550);
        f.setLocation(300, 50);

        f.setIconImage(Toolkit.getDefaultToolkit().createImage("qq.png"));
        f.setVisible(true);
        f.addWindowListener(new MyWinAdapter());

        f.setResizable(false);
        menuBar = new MenuBar();
        menu = new Menu("file");
        menuItem4Open = new MenuItem("open");
        menuItem4Exit = new MenuItem("exit");

        menuBar.add(menu);
        menu.add(menuItem4Open);
        menu.add(menuItem4Exit);

        f.setMenuBar(menuBar);
        // new
        Button btn11 = new Button("文件夹级联加密");
        Button btn12 = new Button("文件夹加密");
        Button btn13 = new Button("文件加密");
        Panel panel1 = PanelFactory.generatePanelItem(btn11, btn12, btn13, "加密方式一:文件名称加密(支持对文件夹的名称加密)");


        Button btn21 = new Button("文件夹级联加密");
        Button btn22 = new Button("文件夹加密");
        Button btn23 = new Button("文件加密");
        Panel panel2 = PanelFactory.generatePanelItem(btn21, btn22, btn23, "加密方式二:文件头部加密");

        Button btn31 = new Button("文件夹级联加密");
        Button btn32 = new Button("文件夹加密");
        Button btn33 = new Button("文件加密");
        Panel panel3 = PanelFactory.generatePanelItem(btn31, btn32, btn33, "加密方式三:文件内容加密(加密速度较慢, 1G大小的文件耗时约10秒)");


        Button btn41 = new Button("一件解密");
        Panel panel4 = new Panel(new FlowLayout(FlowLayout.RIGHT));
        panel4.setBackground(new Color(199, 199, 199));
        panel4.add(btn41);
        panel4.setSize(600, 20);

        Panel panelCombine1 = new Panel(new BorderLayout(0, 10));
        panelCombine1.add(panel1, BorderLayout.NORTH);
        panelCombine1.add(panel2, BorderLayout.CENTER);

        Panel panelCombine2 = new Panel(new BorderLayout(0, 10));
        panelCombine2.add(panel3, BorderLayout.NORTH);
        panelCombine2.add(panel4, BorderLayout.CENTER);

        // 北
        f.add(panelCombine1, BorderLayout.NORTH);
        // 中
        f.add(panelCombine2, BorderLayout.CENTER);


        ta = new TextArea();
        ta.setPreferredSize(new Dimension(0, 200));
        // 南
        f.add(ta, BorderLayout.SOUTH);
        f.setResizable(true);


        // 阻塞其他事件
        dialog = new Dialog(f, "提示", true);
        dialog.setLayout(new FlowLayout());
        dialogOkBtn = new Button("OK");
        label = new Label();
        dialog.add(label);
        dialog.add(dialogOkBtn);
        dialog.setLocation(400, 100);
        dialog.setSize(200, 200);
        eventResolver();
        // btn事件绑定

        ButtonActionFactory.btn11(btn11);
        ButtonActionFactory.btn12(btn12);
        ButtonActionFactory.btn13(btn13);

        ButtonActionFactory.btn21(btn21);
        ButtonActionFactory.btn22(btn22);
        ButtonActionFactory.btn23(btn23);

        ButtonActionFactory.btn31(btn31);
        ButtonActionFactory.btn32(btn32);
        ButtonActionFactory.btn33(btn33);
        ButtonActionFactory.btn41(btn41);
    }

    private static void encryptContentEventResolver() {
        btn4ContentDecrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ta.append("加密方式二:文件内容加密(对目标文件夹下所有的文件和文件夹的名称进行加密)\n\n");
            }
        });

        btn4ContentEncrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileSystemView fsv = FileSystemView.getFileSystemView();
                //C:\Users\BG388892\Desktop\新建文件夹 (4), 获取应用程序运行时所在的文件夹, 可以借此新增配置文件
                ta.append(System.getProperty("user.dir"));
                try {
                    testexe();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    log.info("加密出错", ex);
                }
            }
        });
    }

    public static void testexe() throws IOException {
//        File file = new File("C:\\Users\\BG388892\\Desktop\\filehideV100.exe");
        File file = new File(System.getProperty("user.dir") + "\\filehideV100.exe");
//        FileOutputStream fileOutputStream = new FileOutputStream(file,true);

        log.info(file.getPath());
        if (file.exists()) {
            System.out.println("文件存在");
            log.info("文件存在");
        } else {
            System.out.println("文件不存在");
            log.info("文件不存在");

            return;
        }
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        System.out.println(raf.length());
        int read = raf.read();
        System.out.println(read);
        raf.seek(78);
        ta.append(String.valueOf(raf.read()));
        ta.append(String.valueOf(raf.read()));
        ta.append(String.valueOf(raf.read()));
        ta.append(String.valueOf(raf.read()));
        raf.seek(78);
        String str = "AAAAA";
        raf.write(str.getBytes());

        raf.close();

        System.out.println();
        System.out.println(str.getBytes().length);
    }


    private static void eventResolver() {
        //事件
        menuItem4Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        menuItem4Open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //选择文件夹
                JFileChooser fileChooser = new JFileChooser();
                FileSystemView fsv = FileSystemView.getFileSystemView();
                System.out.println(fsv.getHomeDirectory());
                fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
                fileChooser.setDialogTitle("请选择要上传的文件...");
                fileChooser.setApproveButtonText("确定");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int result = fileChooser.showOpenDialog(f);
                if (JFileChooser.APPROVE_OPTION == result) {
                    String path = fileChooser.getSelectedFile().getPath();
                    System.out.println("path: " + path);
                }
            }
        });

        dialogOkBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        });
    }

    public static class MyWinAdapter extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }
}
