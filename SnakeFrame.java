import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SnakeFrame extends JFrame{
	protected  static Yard y = new Yard();//�½�Yardʵ�����߻����
	protected InfPanel inf = new InfPanel();//�½�InfPanelʵ���������Ϣ
	
	public SnakeFrame(){
		super("̰����");
		this.setSize(Yard.ROWS*Yard.BLOCK_SIZE*3/2, Yard.COLS*Yard.BLOCK_SIZE*17/16);
		this.setLocationRelativeTo(null);//����
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addKeyListener(new KeyMonitor());
		this.setResizable(false);//�ߴ粻�ɱ�
		this.setFocusable(true);//��ý���
		
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, y, inf);//�ָ�����Yard���ұ���Ϣ���
		
		split.setDividerLocation(Yard.ROWS*Yard.BLOCK_SIZE);//���÷ָ�λ��
		split.setEnabled(false);//�ָ�񲻿ɲ���
		this.getContentPane().add(split);
		this.setVisible(true);//���ÿɼ�
	}

	public static void main(String[] args) {
		new SnakeFrame();

	}
	
	private class KeyMonitor extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			inf.returnJlScore().setText("Score: "+y.getScore());//ÿ�λ�ü����¼����������÷�
			y.new KeyMonitor().keyPressed(e);//��õļ����¼�����Yard��ʵ������
			
		}
	}
}
