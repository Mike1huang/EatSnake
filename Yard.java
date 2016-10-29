import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 * 蛇活动场所
 */
public class Yard extends JPanel
{
	protected int score = 0;//记录得分
	protected static int ROWS = 50;//行数
	protected static int COLS = 50;//列数
	protected static int BLOCK_SIZE = 10;//每行每列的尺寸
	protected static Color bg_color = new Color(0x808080);//背景颜色，初始为灰色
	protected static boolean bg_line = true;//设置是否画出线条，默认画出
	
	PaintThread paintThread = new PaintThread();//内部进程
	private int sleepTime = 150;//设置进程睡眠时间
	private boolean gameOver = false;//设置游戏是否结束
	Font fontGameOver = new Font("宋体", Font.BOLD, 50);//字体样式
	
	
	Snake s = new Snake(this);//蛇实例
	Egg e = new Egg();//蛋实例
	
	//构造方法
	public Yard(){ 
		this.setSize(ROWS*BLOCK_SIZE, COLS*BLOCK_SIZE);
		this.addKeyListener(new KeyMonitor());//将键盘时间委托给内部类 KeyMonitor的实例
		
		new Thread(paintThread).start();
		
		this.setVisible(true);
	}
	
	//画出背景
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(bg_color);
		g.fillRect(0, 0, ROWS * BLOCK_SIZE, COLS * BLOCK_SIZE);
		g.setColor(Color.darkGray);
		if(bg_line == true ){//判断是否画线条
			for (int i = 1; i < ROWS; i++) {
				g.drawLine(0, BLOCK_SIZE * i, BLOCK_SIZE * COLS, BLOCK_SIZE * i);
			}
			for (int i = 1; i < COLS; i++) {
				g.drawLine(BLOCK_SIZE * i, 0, BLOCK_SIZE * i, BLOCK_SIZE * ROWS);
			}
		}
		g.setColor(Color.WHITE);
		g.drawString("score: "+score, 30, 30);

		g.setColor(Color.YELLOW);
		if (gameOver) {//判断游戏是否结束
			g.setFont(fontGameOver);
			g.drawString("游戏结束", ROWS * BLOCK_SIZE / 3, COLS * BLOCK_SIZE / 2);

			paintThread.pause();
		}

		g.setColor(c);

		s.eat(e);//判断蛇是否吃蛋
		e.draw(g);//画出蛋
		s.draw(g);//画出蛇

	}

	//画图线程
	private class PaintThread implements Runnable {
		private boolean running = true;//设置进程是否结束
		private boolean pause = false;//设置游戏是否停止
		private boolean flag = true;//控制暂停，继续游戏

		@Override
		public void run() {
			while (running) {	
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				if (pause) continue;
				else repaint();
			}
		}

		public void gameOver() {
			running = false;
		}

		public void pause() {//判断游戏是否暂停
			if(flag == true ) this.pause = true;
			else this.pause = false;
			flag = !flag;
		}

		public void reStart() {//重启游戏
			this.pause = false;
			s = new Snake(Yard.this);//新建蛇实例
			gameOver = false;
			score = 0;//得分清零
			e.reAppear();//重画蛋
		}
	}
     class KeyMonitor extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();//获得按键名称常量
			if (key == KeyEvent.VK_ENTER)//按下Enter键，重新游戏
				paintThread.reStart();
			else if(key == KeyEvent.VK_SPACE)//按下Space键，暂停或启动游戏
				if(gameOver == false) paintThread.pause();
			s.keyPressed(e);
		}
     }

	public void stop() {//设置游戏结束
		gameOver = true;
		
	}
	
	/**
	 * 拿到所得的分数
	 * @return 分数
	 */
	
	public int getScore() {
		return score;
	}
	
	/**
	 * 设置所得的分数
	 * @param score 分数
	 */
	
	public void setScore(int score) {
		this.score = score;
	}
	
	//获得线程睡眠时间
	public int getSleepTime(){
		return sleepTime;
	}
	
	//设置线程睡眠时间
	public void setSleepTime(int sleepTime){
		this.sleepTime = sleepTime;
	}	
}

