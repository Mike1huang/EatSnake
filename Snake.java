import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;
/**
 * 代表蛇
 */

public class Snake {
	private Node head = null;//蛇头
	private Node tail = null;//蛇尾
	private int size = 0;//蛇尺寸
	private Node stone_h = null;//石头头
	private Node stone_t = null;//石头尾
	private int stone_size = 0;//
	private int left_size = 0;//设置是否留下石头
	private static Random r = new Random();
	
	private Node n = new Node(r.nextInt(Yard.ROWS), r.nextInt(Yard.COLS), Dir.L);//新建默认节点实例
	private Yard y;
	
	//构造方法
	public Snake(Yard y) {
		head = n;
		tail = n;
		size = 1;
		this.y = y;
	}
	
	//新建节点实例，并设为蛇尾，双向链表实现
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
	
	//新建节点实例，并设为蛇头，双向链表实现
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
	
	//遍历石头链表，蛇链表，画出每一个节点
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
	
	//蛇移动
	private void move() {
		addToHead();
		deleteFromTail();
		checkDead();
	}
	
	//判断是否产生游戏结束条件
	private void checkDead() {
		if(head.row < 0 || head.col < 0 || head.row >= Yard.ROWS || head.col >= Yard.COLS)  {//超出边界
			y.stop();
		}
		for(Node n = stone_h; n != null; n = n.next){//撞上石头
			if(head.row == n.row && head.col == n.col) {
				y.stop();
			}
		}
		for(Node n = head.next; n != null; n = n.next) {//撞到自己
			if(head.row == n.row && head.col == n.col) {
				y.stop();
			}
		}
	}

	//删除蛇尾节点
	private void deleteFromTail() {
		if(size == 0) return;
		tail = tail.prev;
		tail.next = null;
		
	}

	//节点内部类
	private class Node {
		int w = Yard.BLOCK_SIZE;//宽度
		int h = Yard.BLOCK_SIZE;//长度
		int row , col;//行列位置
		Dir dir = Dir.L;//方向
		Node next = null;//下一个节点
		Node prev = null;//上一个节点
		boolean isSnake = true;
		
		Node(int row, int col, Dir dir) {
			this.row = row;
			this.col = col;
			this.dir = dir;
		}
		
		//画出节点
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
		//设置为石头
		void setStone(){
			this.isSnake = false;
		}
	}
	
	//判断是否遇到蛋
	public void eat(Egg e) {
		if(this.getRect().intersects(e.getRect())) {
			left_size++;
			e.reAppear();
			this.addToHead();
			this.left();
			y.setScore(y.getScore() + 1);
		}
	}
	
	//判断是否产生石头
	public void left(){
		if(left_size == 10){
			left_size = 0;
			Node left_stone = tail;
			addToHead();
			deleteFromTail();
			left_stone.setStone();//设置为石头
			
			if(stone_h == null){//如果没有石头结点，则设为石头头和尾，否则添加到石头尾
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























