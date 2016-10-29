import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class InfPanel extends JPanel implements ActionListener{
	private JLabel jl[];
	private JButton jb[];
	private JTextArea jt[] ;
	private JRadioButton jr[];
    private JPanel jp[];
	private JScrollPane js[];
	
	
	private static String ctl = "【操作说明】\r\n\r\nSpace：暂停/启动 \r\n Enter: 重新开始 \r\n "
			+ "方向键控制方向 \r\n 难度E：简单\r\n 难度S：普通\r\n 难度H：困难\r\n"
			+ "——————————————————";
	private static String ctl2 = "【版本V2.0.1】\r\n更新内容：\r\n"
			+ "1.增加石头\r\n"
			+ "2.已知BUG：蛋有一定几率出现在蛇内部\r\n"
			+ "——————————————\r\n"
			+ "【版本V2.0】\r\n更新内容："
			+ "\r\n1.增加背景颜色选择选项\r\n"
			+ "2.蛋的颜色会变化\r\n"
			+ "3.修正一系列BUG：\r\n"
			+ "    *修正游戏结束后按Space键继续移动的错误\r\n"
			+ "    *优化游戏边界判定条件\r\n";
	private static String des[][] = {{"Score: "+SnakeFrame.y.getScore(),"More："},{"E","S","H"},{"默认","纯灰","自定义"},
			{"面板","难度面板","颜色面板","软件信息"},{"更新","关于"},{ctl,ctl2}};
	
	public InfPanel(){
		this.setBorder(new TitledBorder("Cin"));//设置面板标题
		this.setLayout(new BorderLayout());//边布局方式
		
		//新建各面板
		this.jp = new JPanel[des[3].length];
		this.jp[0] = new JPanel(new GridLayout(3,1));//面板，网格布局，3行1列
		this.add(jp[0], "South");
		for(int i = 1; i < jp.length; i++){
			this.jp[i] = new JPanel(new FlowLayout());//创建分面板
			this.jp[0].add(this.jp[i]);//添加到面板
		}
		
		//新建各标签
		this.jl = new JLabel[des[0].length];
		for(int i = 0; i < jl.length; i++){
			jl[i] = new JLabel(des[0][i]);
		}
		this.add(jl[0], "North");//将得分标签添加到总面板北部
		this.jp[3].add(jl[1]);//将信息标签添加到信息面板
		
		//文本框
		this.jt = new JTextArea[des[5].length];
		this.js = new JScrollPane[des[5].length];//按文本框数量创建滚动窗格
		for(int i = 0; i < this.jt.length; i++){
			this.jt[i] = new JTextArea(des[5][i]);
			this.js[i] = new JScrollPane(jt[i]);//根据文本框创建滚动窗格实例
			jt[i].setLineWrap(true);//设置文本框自动分行
			jt[i].setEditable(false);//设置不可编辑
			jt[i].setFocusable(false);//设置文本框不获得焦点		
		}
		this.add(js[0], "Center");//将包含操作说明文本框的滚动窗格添加到总面板中部
		
		//颜色面板
		this.jb = new JButton[des[2].length];
		for(int i = 0; i < this.jb.length; i++ ){
			this.jb[i] = new JButton(des[2][i]);
			this.jb[i].setFocusable(false);
			this.jb[i].addActionListener(this);
			this.jp[2].add(this.jb[i]);
		}
		
		//难度面板
		this.jp[1].add(new JLabel("难度："));
		ButtonGroup bgroup_diff =  new ButtonGroup();
		this.jr = new JRadioButton[des[1].length];
		for(int i = 0; i < this.jr.length; i++){
			this.jr[i] = new JRadioButton(des[1][i]);
			jr[i].setFocusable(false);//按钮不获得焦点
			this.jp[1].add(this.jr[i]);//将单选按钮添加到难度面板
			this.jr[i].addActionListener(this);//注册监听器
			bgroup_diff.add(this.jr[i]);//将单选按钮添加到按钮组
		}
		this.jr[1].setSelected(true);//设置按钮S为默认选中按钮
		
		//信息面板
		this.jb = new JButton[des[4].length];
		for(int i = 0; i < jb.length; i++){
			this.jb[i] = new JButton(des[4][i]);
			this.jb[i].setFocusable(false);
			this.jb[i].addActionListener(this);
			this.jp[3].add(this.jb[i]);
		}
			
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent ev) {
		if(ev.getActionCommand().equals("E"))
			SnakeFrame.y.setSleepTime(300);
		if(ev.getActionCommand().equals("S"))
			SnakeFrame.y.setSleepTime(100);
		if(ev.getActionCommand().equals("H"))
			SnakeFrame.y.setSleepTime(50);
		
		if(ev.getActionCommand().equals("默认")){
			Yard.bg_color = new Color(0x808080); 
			Yard.bg_line = true;
		}
		if(ev.getActionCommand().equals("纯灰")){
			Yard.bg_color = new Color(0x808080); 
			Yard.bg_line = false;
		}
		if(ev.getActionCommand().equals("自定义")){
			Color newBgColor = JColorChooser.showDialog(this, "选择背景颜色", Color.GRAY);
			if(newBgColor != null) Yard.bg_color = newBgColor;
			Yard.bg_line = false;
		}
		
		if(ev.getActionCommand().equals("更新")){
			JOptionPane.showMessageDialog(this,this.js[1]);
		}
		if(ev.getActionCommand().equals("关于")){
			JOptionPane.showMessageDialog(this,"版本：V2.0\r\n作者：Cin\r\n参考：马士兵贪吃蛇项目\r\n2016年10月23日");
		}		
	}
	
	//返回面板得分标签成员
	public JLabel returnJlScore(){
		return jl[0];
	}	
}
