import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/**
 * ����
 */
public class Egg {
	int row, col;//����λ��
	int w = Yard.BLOCK_SIZE;//���
	int h = Yard.BLOCK_SIZE;//�߶�
	private static Random r = new Random();
	private Color color = Color.GREEN;//������ɫ

	public Egg(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public Egg() {
		this(r.nextInt(Yard.ROWS-2) + 2, r.nextInt(Yard.COLS));
	}
	
	//�ػ���
	public void reAppear() {
		this.row = r.nextInt(Yard.ROWS-2) + 2;//�µ��ڷ�Χ�ڵ����λ�ó���
		this.col = r.nextInt(Yard.COLS);
		this.color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));//��ɫ���
	}
	
	public Rectangle getRect() {
		return new Rectangle(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, w, h);
	}
	
	//����
	public void draw(Graphics g) {
		Color c = g.getColor();
		while(color.getRGB()== Yard.bg_color.getRGB())//�ڵ���ɫ�뱳����ɫ������ͬ
			color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
		
		g.setColor(color);
		g.fillOval(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, w, h);
		g.setColor(c);
		
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
}
