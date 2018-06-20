package top.itning.qq;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.*;
 
public class TT extends JFrame implements ActionListener{
    JPanel jp1; //专门用于存放JCheckBox按钮
    JButton jb;
    public TT() {
        JCheckBox jcb1 = new JCheckBox("足球");
        JCheckBox jcb2 = new JCheckBox("跑步");
        JCheckBox jcb3 = new JCheckBox("游泳");
 
        jp1 = new JPanel();
        jp1.add(jcb1);
        jp1.add(jcb2);
        jp1.add(jcb3);
        add(jp1);
         
        JPanel jp2 = new JPanel();
        jb = new JButton("查看选择的兴趣");
        jb.addActionListener(this);
        jp2.add(jb);
        add(jp2,BorderLayout.SOUTH);
        setTitle("测试");// 标题
        setSize(270, 180);// 窗口大小
        setLocationRelativeTo(null);// 窗口居中
        setDefaultCloseOperation(EXIT_ON_CLOSE);// 窗口点击关闭时,退出程序
        setVisible(true);// 窗口可见
    }
 
    public static void main(String[] args) {
        new TT();// 创建窗口实例
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==jb) {
            String str="";
            Component[] jcbs = jp1.getComponents();//获取jp1里的全部组件(我们只在里面存放了jcheckbox)
            for (Component component : jcbs) {
                JCheckBox jcb = (JCheckBox) component;//需要强制转换成jcheckbox
                if(jcb.isSelected()) {
                    str+=jcb.getText()+"  ";
                }
            }
            if(str.equals("")) {
                str="没有选中任何兴趣爱好";
            }
            JOptionPane.showMessageDialog(this,str);//弹出对话框,显示选择结果
        }
    }
 
}