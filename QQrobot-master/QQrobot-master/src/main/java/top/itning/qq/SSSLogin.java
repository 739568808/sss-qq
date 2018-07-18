package top.itning.qq;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import top.itning.qq.db.DBSelect;
import top.itning.qq.db.User;
import top.itning.webqq.client.SmartQQClient;
import top.itning.webqq.model.Group;

public class SSSLogin {
	private final String NAME="唰唰唰-qq群发";
	public JFrame frame = new JFrame("登录");
	private Container c = frame.getContentPane();
	private JTextField username = new JTextField("");
	private JPasswordField password = new JPasswordField("");
	private static User user= null;
	//微博用户名密码
	private JTextField username_wb = new JTextField("");
	private JPasswordField password_wb = new JPasswordField("");
	
	private JButton ok = new JButton("登录");
	private JButton ok_wb = new JButton("绑定微博");
	public static JButton wb_job = new JButton("启动");
	public static JButton stop_job = new JButton("停止");
	private JButton cancel = new JButton("取消");
	private JLabel errorMeg = new JLabel("");
	private JLabel errorMeg_wb = new JLabel("");
	private JLabel errorMeg_qq = new JLabel("");
	
	private JFrame mainframe = new JFrame("");
	private JPanel fieldPanel = new JPanel();
	public static  JTextArea logMsg = new JTextArea();
	
	private JPanel showQrcodepanel = new JPanel();
	public static JComboBox allGrouphairTimeComboBox=new JComboBox();
	private JPanel cheakboxListpanel = new JPanel();
	public static JButton jb;
	public static JLabel qqUserName = new JLabel("");
	private SmartQQClient smartQQClient = null;
	private JTextArea contentTextArea=new JTextArea("");
	public static JComboBox grouphairTimeComboBox=new JComboBox();
	
	
	public static Thread groupHair;
	
	public void mainFrame(String userName) throws InterruptedException{
		try {
			mainframe.setTitle(NAME+"会员："+userName);
			mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			mainframe.setSize(805, 600);
			mainframe.setResizable(false);
			//居中
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Dimension screen = toolkit.getScreenSize();
			int x = (screen.width-mainframe.getWidth()) / 2;
			int y = (screen.height-mainframe.getHeight()) / 2;
			
			Image image = toolkit.getImage(this.getClass().getResource("logo.png"));
			mainframe.setIconImage(image);
			// 中部表单
			fieldPanel = new JPanel();
			fieldPanel.setLayout(null);
			
			//绑定微博
			showQrcodepanel = new JPanel();
			showQrcodepanel.setLayout(null);
			showQrcodepanel.setBounds(10, 10, 430, 200);
			showQrcodepanel.setBorder(BorderFactory.createTitledBorder("扫码登录QQ"));
			
			JLabel register2 = new JLabel("");
			register2.setBounds(10, 20, 170, 170);
			register2.setForeground(Color.blue);
			while (SmartQQClient.FILEPATH==null) {
				Thread.sleep(10);
			}
			Icon qqicon = new ImageIcon(SmartQQClient.FILEPATH);
			register2.setIcon(qqicon);
			showQrcodepanel.add(register2);
			
			fieldPanel.add(showQrcodepanel);
			
			qqUserName = new JLabel("");
			qqUserName.setBounds(10, 20, 100, 24);
			fieldPanel.add(qqUserName);
			
			errorMeg_qq.setForeground(Color.red);
			errorMeg_qq.setBounds(130, 20, 100, 24);
			fieldPanel.add(errorMeg_qq);
			
			JLabel grouphairTimeLabel = new JLabel("群发间隔/秒");
			grouphairTimeLabel.setBounds(20, 58, 100, 20);
			fieldPanel.add(grouphairTimeLabel);
			
			String[] grouphairTimeArr = {"10", "15", "30", "60"};  
			grouphairTimeComboBox = new JComboBox(grouphairTimeArr); 
			grouphairTimeComboBox.setBounds(95, 58, 50, 20);
//			grouphairTimeComboBox.hide();
			fieldPanel.add(grouphairTimeComboBox);
			
			JLabel allGrouphairTimeLabel = new JLabel("下次群发开始/分");
			allGrouphairTimeLabel.setBounds(190, 58, 100, 20);
			fieldPanel.add(allGrouphairTimeLabel);
			
			String[] allGrouphairTimeMinArr = {"5","10", "15", "30", "60"};  
			allGrouphairTimeComboBox = new JComboBox(allGrouphairTimeMinArr); 
			allGrouphairTimeComboBox.setBounds(290, 58, 50, 20);
//			allGrouphairTimeComboBox.hide();
			fieldPanel.add(allGrouphairTimeComboBox);
			
			
			JLabel groupContentLabel = new JLabel("群发内容:");
			groupContentLabel.setBounds(20, 88, 80, 20);
			fieldPanel.add(groupContentLabel);
			
			contentTextArea.setBounds(80, 88, 240, 30);
			contentTextArea.setText("互粉互粉，诚信互粉\r\nhttp://www.weibo.com/u/"+user.getWbUserId());
			fieldPanel.add(contentTextArea);
			
			
			
			jb = new JButton("开始执行");
	        jb.addActionListener(getSelected);
	        jb.setBounds(20, 125, 100, 50);
//	        jb.hide();
	        fieldPanel.add(jb);
	        
			
			logMsg.setBounds(450, 0, 340, 600);
			JScrollPane text2=new JScrollPane(logMsg);
			text2.setBounds(450, 0, 350, 600);
			fieldPanel.add(text2);
			
			mainframe.add(fieldPanel);
			mainframe.setLocation(x, y);
			mainframe.setVisible(true);
			frame.dispose();
			
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	ActionListener getSelected = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
	        if(e.getSource()==jb) {
	            List<String> strs=new ArrayList<String>();
	            Component[] jcbs = cheakboxListpanel.getComponents();//获取jp1里的全部组件(我们只在里面存放了jcheckbox)
	            for (Component component : jcbs) {
	                JCheckBox jcb = (JCheckBox) component;//需要强制转换成jcheckbox
	                if(jcb.isSelected()) {
	                    strs.add(jcb.getText());
	                }
	            }
	            if(strs.size()==0) {
	            	errorMeg_qq.setText("请选择QQ群");
	            	return;
	            }
	            String content = contentTextArea.getText();
				if (StringUtils.isEmpty(content)) {
					errorMeg_wb.setText("【错误操作】群发内容不允许为空！！！");
					return;
				}
	            int grouphairTime = Integer.valueOf(String.valueOf(grouphairTimeComboBox.getSelectedItem()))*1000;//秒
				int allGrouphairTime = Integer.valueOf(String.valueOf(allGrouphairTimeComboBox.getSelectedItem()))*60*1000;//分
				
				sendMsg(content, grouphairTime, allGrouphairTime, strs);
	        }
	    }
	};
	
	private void sendMsg(String content,int grouphairTime,int allGrouphairTime,List<String> strs) {
		groupHair = new Thread() {
			public void run() {
				logMsg.setText("开始群发\n"+logMsg.getText());
				for (;;) {
					List<Group> groupList = smartQQClient.getGroupList();
					try {
						Long groupId = null;
						for (String name : strs) {
							
							for (Group group : groupList) {
								if (group.getName().equals(name)) {
									groupId = group.getId();
								}
							}
							smartQQClient.sendMessageToGroup(groupId, content);
							logMsg.setText("【群发】"+name+"发送成功\n"+logMsg.getText());
							Thread.sleep(grouphairTime);
						}
						logMsg.setText("【群发】完成下次开始时间"+allGrouphairTime/1000+"秒后开始\n"+logMsg.getText());
						Thread.sleep(allGrouphairTime);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		};
		groupHair.start();

	}
	
	private void openMainFrame(String userName) throws Exception {
		new Thread(){
			public void run(){
				smartQQClient = new SmartQQClient(null);
				if (smartQQClient != null) {
					showQrcodepanel.hide();
				}
				List<Group> groupList = smartQQClient.getGroupList();
				
				int y=20;
				int x= 0;
				for (Group group : groupList) {
					JCheckBox jCheckBox = new JCheckBox(group.getName()/*+" >>"+group.getId()*/);
					int xxx =10;
					if (x%2==0) {
						xxx = xxx*20;
						y+=20;
					}
					jCheckBox.setBounds(xxx, y, 160, 20);
					cheakboxListpanel.add(jCheckBox);
					x++;
				}
				cheakboxListpanel.setBounds(10, 170, 400, 600);
		        fieldPanel.add(cheakboxListpanel);
//		        fieldPanel.setVisible(true);
		        mainframe.setVisible(true);
	           }
			
		}.start();

		mainFrame(userName);
	}
	
	ActionListener closeLogin = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				System.exit(0);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	};
	ActionListener okListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String userName = username.getText();
				char[] passwords = password.getPassword();
				if (StringUtils.isEmpty(userName)) {
					errorMeg.setText("用户名不能为空");
					return;
				}
				if (ArrayUtils.isEmpty(passwords)) {
					errorMeg.setText("密码不能为空");
					return;
				}
				//数据库读取是否注册过
				
				User u = DBSelect.selectUser(userName, String.valueOf(password.getPassword()));
				if (u == null || u.getId()==0) {
					errorMeg.setText("用户名或密码错误，请重新输入");
					return;
				}
				user = u;
				//检查通过显示
				openMainFrame(userName);
			} catch (Exception e1) {
			}
		}
	};
	
	private void initFrame() {
		
		// 顶部
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout());
		titlePanel.add(new JLabel(NAME+"会员登录"));
		c.add(titlePanel, "North");

		// 中部表单
		JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout(null);
		JLabel a1 = new JLabel("用户名:");
		a1.setBounds(70, 20, 50, 20);
		JLabel a2 = new JLabel("密  码:");
		a2.setBounds(70, 60, 50, 20);
		fieldPanel.add(a1);
		fieldPanel.add(a2);
		errorMeg.setForeground(Color.red);
		errorMeg.setBounds(130, 80, 180, 20);
		fieldPanel.add(errorMeg);
		
		username.setBounds(130, 20, 120, 20);
		password.setBounds(130, 60, 120, 20);
		fieldPanel.add(username);
		fieldPanel.add(password);
		
		JLabel register = new JLabel("暂不提供注册服务");
		register.setBounds(70, 85, 220, 50);
		register.setForeground(Color.gray);
		JLabel register3 = new JLabel("联系客服获取用户名密码");
		register3.setBounds(70, 105, 220, 50);
		register3.setForeground(Color.blue);
		
		JLabel register2 = new JLabel("QQ：1012507575");
		register2.setBounds(70, 125, 220, 50);
		register2.setForeground(Color.blue);
		Icon qqicon = new ImageIcon(this.getClass().getResource("qq.png"));
		register2.setIcon(qqicon);
//		register.addMouseListener(registerListener);
		fieldPanel.add(register);
		fieldPanel.add(register2);
		fieldPanel.add(register3);
		
		c.add(fieldPanel, "Center");
		
		

		// 底部按钮
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		ok.addActionListener(okListener);
		buttonPanel.add(ok);
		cancel.addActionListener(closeLogin);
		buttonPanel.add(cancel);
		c.add(buttonPanel, "South");
		
		
	}
	
	public SSSLogin() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setSize(350, 250);
		frame.setResizable(false);
		//居中
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		int x = (screen.width-frame.getWidth()) / 2;
		int y = (screen.height-frame.getHeight()) / 2;
		Image image = toolkit.getImage(this.getClass().getResource("logo.png"));
		frame.setIconImage(image);
		
		frame.setLocation(x, y);
		c.setLayout(new BorderLayout());
		initFrame();
		frame.setVisible(true);
	}
	
	
	
	public static void main(String[] args) {
		try {
//			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//			javax.swing.UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}            

		new SSSLogin();
	}

}
