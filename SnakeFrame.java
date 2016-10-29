import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SnakeFrame extends JFrame{
	protected  static Yard y = new Yard();//新建Yard实例，蛇活动场所
	protected InfPanel inf = new InfPanel();//新建InfPanel实例，面板信息
	
	public SnakeFrame(){
		super("贪吃蛇");
		this.setSize(Yard.ROWS*Yard.BLOCK_SIZE*3/2, Yard.COLS*Yard.BLOCK_SIZE*17/16);
		this.setLocationRelativeTo(null);//居中
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addKeyListener(new KeyMonitor());
		this.setResizable(false);//尺寸不可变
		this.setFocusable(true);//获得焦点
		
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, y, inf);//分割窗格，左边Yard，右边信息面板
		
		split.setDividerLocation(Yard.ROWS*Yard.BLOCK_SIZE);//设置分割位置
		split.setEnabled(false);//分割窗格不可操作
		this.getContentPane().add(split);
		this.setVisible(true);//设置可见
	}

	public static void main(String[] args) {
		new SnakeFrame();

	}
	
	private class KeyMonitor extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			inf.returnJlScore().setText("Score: "+y.getScore());//每次获得键盘事件，设置面板得分
			y.new KeyMonitor().keyPressed(e);//获得的键盘事件交由Yard类实例处理
			
		}
	}
}
