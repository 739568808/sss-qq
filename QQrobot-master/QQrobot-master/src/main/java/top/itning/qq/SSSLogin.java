package top.itning.qq;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

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
	
	private JFrame mainframe = new JFrame("");
	private JPanel fieldPanel = new JPanel();
	public static  JTextArea logMsg = new JTextArea();
	
	
	
	public void mainFrame(String userName) throws InterruptedException{
		try {
			mainframe.setTitle(NAME+"会员："+userName);
			mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			mainframe.setSize(805, 700);
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
			JPanel showQrcodepanel = new JPanel();
			showQrcodepanel.setLayout(null);
			showQrcodepanel.setBounds(10, 10, 430, 200);
			showQrcodepanel.setBorder(BorderFactory.createTitledBorder("扫码登录QQ"));
			
			JLabel register2 = new JLabel("");
			register2.setBounds(10, 20, 170, 170);
			register2.setForeground(Color.blue);
			while (SmartQQClient.FILEPATH==null) {
				Thread.sleep(100);
				
			}
			Icon qqicon = new ImageIcon(SmartQQClient.FILEPATH);
			register2.setIcon(qqicon);
			showQrcodepanel.add(register2);
			
			fieldPanel.add(showQrcodepanel);
			
			
			
			
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
	
	private void openMainFrame(String userName) throws Exception {
		new Thread(){
			public void run(){
				SmartQQClient smartQQClient = new SmartQQClient(null);
				List<Group> groupList = smartQQClient.getGroupList();
				
				JPanel grouphairJpanel = new JPanel();
				grouphairJpanel.setLayout(null);
				grouphairJpanel.setBounds(10, 95, 430, 200);
				grouphairJpanel.setBorder(BorderFactory.createTitledBorder("群发设置"));
				
				JList<JCheckBox> jList = new JList<JCheckBox>();
				for (Group group : groupList) {
//					System.out.println(group.getId()+"--"+group.getName());
					JCheckBox jCheckBox = new JCheckBox(group.getName()+"#"+group.getId()+"");
					jList.add(jCheckBox);
//					jCheckBox.setva
				}
				grouphairJpanel.add(jList);
				fieldPanel.add(grouphairJpanel);
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
