import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 * �߻����
 */
public class Yard extends JPanel
{
	protected int score = 0;//��¼�÷�
	protected static int ROWS = 50;//����
	protected static int COLS = 50;//����
	protected static int BLOCK_SIZE = 10;//ÿ��ÿ�еĳߴ�
	protected static Color bg_color = new Color(0x808080);//������ɫ����ʼΪ��ɫ
	protected static boolean bg_line = true;//�����Ƿ񻭳�������Ĭ�ϻ���
	
	PaintThread paintThread = new PaintThread();//�ڲ�����
	private int sleepTime = 150;//���ý���˯��ʱ��
	private boolean gameOver = false;//������Ϸ�Ƿ����
	Font fontGameOver = new Font("����", Font.BOLD, 50);//������ʽ
	
	
	Snake s = new Snake(this);//��ʵ��
	Egg e = new Egg();//��ʵ��
	
	//���췽��
	public Yard(){ 
		this.setSize(ROWS*BLOCK_SIZE, COLS*BLOCK_SIZE);
		this.addKeyListener(new KeyMonitor());//������ʱ��ί�и��ڲ��� KeyMonitor��ʵ��
		
		new Thread(paintThread).start();
		
		this.setVisible(true);
	}
	
	//��������
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(bg_color);
		g.fillRect(0, 0, ROWS * BLOCK_SIZE, COLS * BLOCK_SIZE);
		g.setColor(Color.darkGray);
		if(bg_line == true ){//�ж��Ƿ�����
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
		if (gameOver) {//�ж���Ϸ�Ƿ����
			g.setFont(fontGameOver);
			g.drawString("��Ϸ����", ROWS * BLOCK_SIZE / 3, COLS * BLOCK_SIZE / 2);

			paintThread.pause();
		}

		g.setColor(c);

		s.eat(e);//�ж����Ƿ�Ե�
		e.draw(g);//������
		s.draw(g);//������

	}

	//��ͼ�߳�
	private class PaintThread implements Runnable {
		private boolean running = true;//���ý����Ƿ����
		private boolean pause = false;//������Ϸ�Ƿ�ֹͣ
		private boolean flag = true;//������ͣ��������Ϸ

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

		public void pause() {//�ж���Ϸ�Ƿ���ͣ
			if(flag == true ) this.pause = true;
			else this.pause = false;
			flag = !flag;
		}

		public void reStart() {//������Ϸ
			this.pause = false;
			s = new Snake(Yard.this);//�½���ʵ��
			gameOver = false;
			score = 0;//�÷�����
			e.reAppear();//�ػ���
		}
	}
     class KeyMonitor extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();//��ð������Ƴ���
			if (key == KeyEvent.VK_ENTER)//����Enter����������Ϸ
				paintThread.reStart();
			else if(key == KeyEvent.VK_SPACE)//����Space������ͣ��������Ϸ
				if(gameOver == false) paintThread.pause();
			s.keyPressed(e);
		}
     }

	public void stop() {//������Ϸ����
		gameOver = true;
		
	}
	
	/**
	 * �õ����õķ���
	 * @return ����
	 */
	
	public int getScore() {
		return score;
	}
	
	/**
	 * �������õķ���
	 * @param score ����
	 */
	
	public void setScore(int score) {
		this.score = score;
	}
	
	//����߳�˯��ʱ��
	public int getSleepTime(){
		return sleepTime;
	}
	
	//�����߳�˯��ʱ��
	public void setSleepTime(int sleepTime){
		this.sleepTime = sleepTime;
	}	
}

