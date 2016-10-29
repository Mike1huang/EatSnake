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
	
	
	private static String ctl = "������˵����\r\n\r\nSpace����ͣ/���� \r\n Enter: ���¿�ʼ \r\n "
			+ "��������Ʒ��� \r\n �Ѷ�E����\r\n �Ѷ�S����ͨ\r\n �Ѷ�H������\r\n"
			+ "������������������������������������";
	private static String ctl2 = "���汾V2.0.1��\r\n�������ݣ�\r\n"
			+ "1.����ʯͷ\r\n"
			+ "2.��֪BUG������һ�����ʳ��������ڲ�\r\n"
			+ "����������������������������\r\n"
			+ "���汾V2.0��\r\n�������ݣ�"
			+ "\r\n1.���ӱ�����ɫѡ��ѡ��\r\n"
			+ "2.������ɫ��仯\r\n"
			+ "3.����һϵ��BUG��\r\n"
			+ "    *������Ϸ������Space�������ƶ��Ĵ���\r\n"
			+ "    *�Ż���Ϸ�߽��ж�����\r\n";
	private static String des[][] = {{"Score: "+SnakeFrame.y.getScore(),"More��"},{"E","S","H"},{"Ĭ��","����","�Զ���"},
			{"���","�Ѷ����","��ɫ���","�����Ϣ"},{"����","����"},{ctl,ctl2}};
	
	public InfPanel(){
		this.setBorder(new TitledBorder("Cin"));//����������
		this.setLayout(new BorderLayout());//�߲��ַ�ʽ
		
		//�½������
		this.jp = new JPanel[des[3].length];
		this.jp[0] = new JPanel(new GridLayout(3,1));//��壬���񲼾֣�3��1��
		this.add(jp[0], "South");
		for(int i = 1; i < jp.length; i++){
			this.jp[i] = new JPanel(new FlowLayout());//���������
			this.jp[0].add(this.jp[i]);//��ӵ����
		}
		
		//�½�����ǩ
		this.jl = new JLabel[des[0].length];
		for(int i = 0; i < jl.length; i++){
			jl[i] = new JLabel(des[0][i]);
		}
		this.add(jl[0], "North");//���÷ֱ�ǩ��ӵ�����山��
		this.jp[3].add(jl[1]);//����Ϣ��ǩ��ӵ���Ϣ���
		
		//�ı���
		this.jt = new JTextArea[des[5].length];
		this.js = new JScrollPane[des[5].length];//���ı�������������������
		for(int i = 0; i < this.jt.length; i++){
			this.jt[i] = new JTextArea(des[5][i]);
			this.js[i] = new JScrollPane(jt[i]);//�����ı��򴴽���������ʵ��
			jt[i].setLineWrap(true);//�����ı����Զ�����
			jt[i].setEditable(false);//���ò��ɱ༭
			jt[i].setFocusable(false);//�����ı��򲻻�ý���		
		}
		this.add(js[0], "Center");//����������˵���ı���Ĺ���������ӵ�������в�
		
		//��ɫ���
		this.jb = new JButton[des[2].length];
		for(int i = 0; i < this.jb.length; i++ ){
			this.jb[i] = new JButton(des[2][i]);
			this.jb[i].setFocusable(false);
			this.jb[i].addActionListener(this);
			this.jp[2].add(this.jb[i]);
		}
		
		//�Ѷ����
		this.jp[1].add(new JLabel("�Ѷȣ�"));
		ButtonGroup bgroup_diff =  new ButtonGroup();
		this.jr = new JRadioButton[des[1].length];
		for(int i = 0; i < this.jr.length; i++){
			this.jr[i] = new JRadioButton(des[1][i]);
			jr[i].setFocusable(false);//��ť����ý���
			this.jp[1].add(this.jr[i]);//����ѡ��ť��ӵ��Ѷ����
			this.jr[i].addActionListener(this);//ע�������
			bgroup_diff.add(this.jr[i]);//����ѡ��ť��ӵ���ť��
		}
		this.jr[1].setSelected(true);//���ð�ťSΪĬ��ѡ�а�ť
		
		//��Ϣ���
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
		
		if(ev.getActionCommand().equals("Ĭ��")){
			Yard.bg_color = new Color(0x808080); 
			Yard.bg_line = true;
		}
		if(ev.getActionCommand().equals("����")){
			Yard.bg_color = new Color(0x808080); 
			Yard.bg_line = false;
		}
		if(ev.getActionCommand().equals("�Զ���")){
			Color newBgColor = JColorChooser.showDialog(this, "ѡ�񱳾���ɫ", Color.GRAY);
			if(newBgColor != null) Yard.bg_color = newBgColor;
			Yard.bg_line = false;
		}
		
		if(ev.getActionCommand().equals("����")){
			JOptionPane.showMessageDialog(this,this.js[1]);
		}
		if(ev.getActionCommand().equals("����")){
			JOptionPane.showMessageDialog(this,"�汾��V2.0\r\n���ߣ�Cin\r\n�ο�����ʿ��̰������Ŀ\r\n2016��10��23��");
		}		
	}
	
	//�������÷ֱ�ǩ��Ա
	public JLabel returnJlScore(){
		return jl[0];
	}	
}
