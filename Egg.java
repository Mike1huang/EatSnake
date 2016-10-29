import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/**
 * 代表蛋
 */
public class Egg {
	int row, col;//蛋的位置
	int w = Yard.BLOCK_SIZE;//宽度
	int h = Yard.BLOCK_SIZE;//高度
	private static Random r = new Random();
	private Color color = Color.GREEN;//蛋的颜色

	public Egg(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public Egg() {
		this(r.nextInt(Yard.ROWS-2) + 2, r.nextInt(Yard.COLS));
	}
	
	//重画蛋
	public void reAppear() {
		this.row = r.nextInt(Yard.ROWS-2) + 2;//新蛋在范围内的随机位置出现
		this.col = r.nextInt(Yard.COLS);
		this.color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));//颜色随机
	}
	
	public Rectangle getRect() {
		return new Rectangle(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, w, h);
	}
	
	//画蛋
	public void draw(Graphics g) {
		Color c = g.getColor();
		while(color.getRGB()== Yard.bg_color.getRGB())//节点颜色与背景颜色不能相同
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
