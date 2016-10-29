import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;
/**
 * ������
 */

public class Snake {
	private Node head = null;//��ͷ
	private Node tail = null;//��β
	private int size = 0;//�߳ߴ�
	private Node stone_h = null;//ʯͷͷ
	private Node stone_t = null;//ʯͷβ
	private int stone_size = 0;//
	private int left_size = 0;//�����Ƿ�����ʯͷ
	private static Random r = new Random();
	
	private Node n = new Node(r.nextInt(Yard.ROWS), r.nextInt(Yard.COLS), Dir.L);//�½�Ĭ�Ͻڵ�ʵ��
	private Yard y;
	
	//���췽��
	public Snake(Yard y) {
		head = n;
		tail = n;
		size = 1;
		this.y = y;
	}
	
	//�½��ڵ�ʵ��������Ϊ��β��˫������ʵ��
	public void addToTail() {
		Node node = null;
		switch(tail.dir) {
		case L :
			node = new Node(tail.row, tail.col + 1, tail.dir);
			break;
		case U :
			node = new Node(tail.row + 1, tail.col, tail.dir);
			break;
		case R :
			node = new Node(tail.row, tail.col - 1, tail.dir);
			break;
		case D :
			node = new Node(tail.row - 1, tail.col, tail.dir);
			break;
		}
		tail.next = node;
		node.prev = tail;
		tail = node;
		size ++;
	}
	
	//�½��ڵ�ʵ��������Ϊ��ͷ��˫������ʵ��
	public void addToHead() {
		Node node = null;
		switch(head.dir) {
		case L :
			node = new Node(head.row, head.col - 1, head.dir);
			break;
		case U :
			node = new Node(head.row - 1, head.col, head.dir);
			break;
		case R :
			node = new Node(head.row, head.col + 1, head.dir);
			break;
		case D :
			node = new Node(head.row + 1, head.col, head.dir);
			break;
		}
		node.next = head;
		head.prev = node;
		head = node;
		size ++;
	}
	
	//����ʯͷ��������������ÿһ���ڵ�
	public void draw(Graphics g) {
		if(size <= 0) return;
		if(stone_size > 0){
			for(Node n = stone_h; n != null; n = n.next)
				n.draw(g);
		}
		move();
		for(Node n = head; n != null; n = n.next) {
			if(n.row >=0 && n.row < Yard.ROWS && n.col >= 0 && n.col < Yard.COLS) n.draw(g);
		}
	}
	
	//���ƶ�
	private void move() {
		addToHead();
		deleteFromTail();
		checkDead();
	}
	
	//�ж��Ƿ������Ϸ��������
	private void checkDead() {
		if(head.row < 0 || head.col < 0 || head.row >= Yard.ROWS || head.col >= Yard.COLS)  {//�����߽�
			y.stop();
		}
		for(Node n = stone_h; n != null; n = n.next){//ײ��ʯͷ
			if(head.row == n.row && head.col == n.col) {
				y.stop();
			}
		}
		for(Node n = head.next; n != null; n = n.next) {//ײ���Լ�
			if(head.row == n.row && head.col == n.col) {
				y.stop();
			}
		}
	}

	//ɾ����β�ڵ�
	private void deleteFromTail() {
		if(size == 0) return;
		tail = tail.prev;
		tail.next = null;
		
	}

	//�ڵ��ڲ���
	private class Node {
		int w = Yard.BLOCK_SIZE;//���
		int h = Yard.BLOCK_SIZE;//����
		int row , col;//����λ��
		Dir dir = Dir.L;//����
		Node next = null;//��һ���ڵ�
		Node prev = null;//��һ���ڵ�
		boolean isSnake = true;
		
		Node(int row, int col, Dir dir) {
			this.row = row;
			this.col = col;
			this.dir = dir;
		}
		
		//�����ڵ�
		void draw(Graphics g) {
			Color c = g.getColor();
			if(isSnake == true){
				g.setColor(Color.ORANGE);
				g.drawRect(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, w, h);
			}
			else{
				g.setColor(Color.BLACK);
				g.fillRect(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, w, h);
			}
			g.setColor(c);
		}
		//����Ϊʯͷ
		void setStone(){
			this.isSnake = false;
		}
	}
	
	//�ж��Ƿ�������
	public void eat(Egg e) {
		if(this.getRect().intersects(e.getRect())) {
			left_size++;
			e.reAppear();
			this.addToHead();
			this.left();
			y.setScore(y.getScore() + 1);
		}
	}
	
	//�ж��Ƿ����ʯͷ
	public void left(){
		if(left_size == 10){
			left_size = 0;
			Node left_stone = tail;
			addToHead();
			deleteFromTail();
			left_stone.setStone();//����Ϊʯͷ
			
			if(stone_h == null){//���û��ʯͷ��㣬����Ϊʯͷͷ��β��������ӵ�ʯͷβ
				stone_h = left_stone;
				stone_t = left_stone;
				stone_size = 1;
			}
			else{
				stone_t.next = left_stone;
				left_stone.prev = stone_t;
				stone_t = left_stone;
				stone_size++;
			}
		}
	}
	
	private Rectangle getRect() {
		return new Rectangle(Yard.BLOCK_SIZE * head.col, Yard.BLOCK_SIZE * head.row, head.w, head.h);
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_LEFT :
			if(head.dir != Dir.R)
				head.dir = Dir.L;
			break;
		case KeyEvent.VK_UP :
			if(head.dir != Dir.D)
				head.dir = Dir.U;
			break;
		case KeyEvent.VK_RIGHT :
			if(head.dir != Dir.L)
				head.dir = Dir.R;
			break;
		case KeyEvent.VK_DOWN :
			if(head.dir != Dir.U)
				head.dir = Dir.D;
			break;
		}
	}
}























