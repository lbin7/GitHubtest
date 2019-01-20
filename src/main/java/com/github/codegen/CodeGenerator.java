package com.github.codegen;

import com.github.codegen.util.DBConnection;
import com.github.codegen.window.ChooseFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 代码生成器主窗口(程序入口)
 *
 * @author LB
 * @date 2019-1-20 下午5:31:17
 * @version 1.0
 */
public class CodeGenerator {

    // 定义窗口的宽度
    private static final int WIN_WIDTH = 320;
    // 定义窗口的高度
    private static final int WIN_HEIGHT = 330;
    // 定义数据库类型下拉框
    private JComboBox dbComboBox;
    // 定义主机地址文本框
    private  JTextField connTextField;
    // 定义用户名文本框
    private  JTextField usernameTextField;
    // 定义密码文本框
    private  JTextField pwdTextField;
    // 定义端口文本框
    private  JTextField portTextField;
    // 定义数据库连接对象
    private DBConnection dbConnection;
    // 定义数据库名称下拉框
    private JComboBox dbComboBoxName;

    /** 应用初始化方法 */
    private void init(){
        // 创建窗口
        final JFrame frame = new JFrame("代码【生成器】");
        // 获取电脑屏幕的宽度与高度
        int w = (Toolkit.getDefaultToolkit().getScreenSize().width - WIN_WIDTH) / 2;
        int h = (Toolkit.getDefaultToolkit().getScreenSize().height - WIN_HEIGHT) / 2;
        // 设置窗口显示的位置、宽与高
        frame.setBounds(w, h, WIN_WIDTH, WIN_HEIGHT);

        // 设置窗口的图标
        frame.setIconImage(new ImageIcon(this.getClass().getResource("/icon.png"))
                .getImage());

        // 创建面板
        final JPanel jPanel = new JPanel();

        /** ########## 第一行 ########### */
        // 创建JLabel
        JLabel dbLabel = new JLabel("数据库类型：");
        dbLabel.setPreferredSize(new Dimension(90,30));
        jPanel.add(dbLabel);

        // 创建ComboBox数据模型
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement("MySQL");
        model.addElement("Oracle");
        dbComboBox = new JComboBox(model);
        // 设置下拉框的高和宽
        dbComboBox.setPreferredSize(new Dimension(180,30));
        jPanel.add(dbComboBox);


        /** ########## 第二行 ########### */
        // 创建JLabel
        JLabel connLabel = new JLabel("主机地址：");
        connLabel.setPreferredSize(new Dimension(90,30));
        jPanel.add(connLabel);

        // 创建TextField
        connTextField = new JTextField("127.0.0.1");
        // 设置高和宽
        connTextField.setPreferredSize(new Dimension(180,30));
        jPanel.add(connTextField);

        /** ########## 第三行 ########### */
        // 创建JLabel
        JLabel usernameLabel = new JLabel("用户名：");
        usernameLabel.setPreferredSize(new Dimension(90,30));
        jPanel.add(usernameLabel);

        // 创建TextField
        usernameTextField = new JTextField("root");
        // 设置高和宽
        usernameTextField.setPreferredSize(new Dimension(180,30));
        jPanel.add(usernameTextField);

        /** ########## 第四行 ########### */
        // 创建JLabel
        JLabel pwdLabel = new JLabel("密码：");
        pwdLabel.setPreferredSize(new Dimension(90,30));
        jPanel.add(pwdLabel);

        // 创建TextField
        pwdTextField = new JTextField("root");
        // 设置高和宽
        pwdTextField.setPreferredSize(new Dimension(180,30));
        jPanel.add(pwdTextField);

        /** ########## 第五行 ########### */
        // 创建JLabel
        JLabel portLabel = new JLabel("端口：");
        portLabel.setPreferredSize(new Dimension(90,30));
        jPanel.add(portLabel);

        // 创建TextField
        portTextField = new JTextField("3306");
        // 设置高和宽
        portTextField.setPreferredSize(new Dimension(180,30));
        jPanel.add(portTextField);


        /** ########## 第六行 ########### */
        // 创建JLabel
        JLabel dbLabel2 = new JLabel("选择数据库：");
        dbLabel2.setPreferredSize(new Dimension(90,30));
        jPanel.add(dbLabel2);


        dbComboBoxName = new JComboBox();
        // 设置下拉框的高和宽
        dbComboBoxName.setPreferredSize(new Dimension(180,30));
        jPanel.add(dbComboBoxName);

        /** ########## 第七行 ########### */
        // 创建JButton
        JButton connButton = new JButton("连接数据库");
        connButton.setPreferredSize(new Dimension(100,30));
        // 为按钮绑定点击事件
        connButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                // 获取用户选择的数据库类型
                String dbType = dbComboBox.getSelectedItem().toString();
                // 判断用户选择的数据库类型
                if (dbType.equalsIgnoreCase("oracle")){ // Oracle
                    // 弹出提示窗口
                    JOptionPane.showMessageDialog(null, "AI：连接Oracle暂未实现！",
                            "提示信息",JOptionPane.INFORMATION_MESSAGE);
                }else { // MySQL
                    String host = connTextField.getText();
                    String username = usernameTextField.getText();
                    String password = pwdTextField.getText();
                    String port = portTextField.getText();
                    // 创建连接对象
                    dbConnection = new DBConnection(dbType, host, username, password, port);
                    try {
                        // 获取数据库名称
                        java.util.List<String> dbList = dbConnection.getSysDataBaseName();
                        // 创建ComboBox数据模型
                        DefaultComboBoxModel model2 = new DefaultComboBoxModel(dbList.toArray());
                        dbComboBoxName.setModel(model2);

                        // 弹出提示窗口
                        JOptionPane.showMessageDialog(null, "AI：连接"+ dbType +"成功！",
                                "提示信息",JOptionPane.INFORMATION_MESSAGE);
                    }catch (Exception ex){
                        // 弹出提示窗口
                        JOptionPane.showMessageDialog(null, "AI：连接"+ dbType +"失败！",
                                "提示信息",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        jPanel.add(connButton);

        // 创建JButton
        JButton okButton = new JButton("确定");
        okButton.setPreferredSize(new Dimension(100,30));
        // 为按钮绑定点击事件
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                // 获取用户选择的数据库名称
                Object dbName = dbComboBoxName.getSelectedItem();
                // 判断用户选择的数据库名称
                if (dbName == null || "".equals(dbName.toString())){
                    // 弹出提示窗口
                    JOptionPane.showMessageDialog(null, "AI：请选择连接的数据库！",
                            "提示信息",JOptionPane.INFORMATION_MESSAGE);
                }else {
                    frame.setVisible(false);
                    // 获取用户选择的数据库类型
                    String dbType = dbComboBox.getSelectedItem().toString();
                    new ChooseFrame().showChooseWindow(dbType, dbName.toString(),dbConnection);
                }
            }
        });
        jPanel.add(okButton);

        // 设置布局(离上边、下边10个像素)
        jPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 12,12));

        // 窗口中添加面板
        frame.add(jPanel);
        // 设置窗口默认关闭操作
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口的背景颜色
        frame.setBackground(Color.WHITE);
        // 设置窗口不能最大化
        frame.setResizable(false);
        // 设置窗口可见
        frame.setVisible(true);
    }

    public static void main(String[] args){
        new CodeGenerator().init();
    }
}