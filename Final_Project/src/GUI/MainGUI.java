package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import ProcessManagement.Managers;
import ReviewManagement.Review;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import java.awt.Container;
import java.awt.GridLayout;

import Items.Item;

class JPanel011 extends JPanel { // 1번 패널

	public JPanel011() { // 1번째 패널 생성자

		JButton btns[] = new JButton[Managers.managedList.itemList.size()];

		for (int i = 0; i < Managers.managedList.itemList.size(); i++) {
			Item nowItem = Managers.managedList.itemList.get(i);
			btns[i] = makeImageButton(nowItem.getImagePath(), nowItem.getName());
			if (i < 4) {
				btns[i].setBounds(25 + i * 150, 50, 100, 100);
			} else {
				btns[i].setBounds(25 + (i - 4) * 150, 300, 100, 100);
			}
			add(btns[i]);
		}

	}

	static int nowContentNumber = -1;

	private JButton makeImageButton(String imagePath, String buttonName) {
		ImageIcon image = new ImageIcon(imagePath);
		Image image2 = image.getImage();
		Image i4 = image2.getScaledInstance(160, 250, java.awt.Image.SCALE_SMOOTH);
		ImageIcon i5 = new ImageIcon(i4);

		JButton b = new JButton(buttonName, i5);
		b.setHorizontalTextPosition(SwingConstants.CENTER);
		b.setVerticalTextPosition(SwingConstants.BOTTOM);
		b.setBorderPainted(false);
		b.setFocusPainted(false);
		b.setBackground(Color.white);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Managers.recommendManager.addInterestItem(b.getLabel());
				new ReviewFrame(buttonName);
			}
		});
		return b;
	}
}

class JPanel022 extends JPanel implements ActionListener { // 2번째 패널
	public JPanel022() { // 2번째 패널 생성자
		ArrayList<Item> items = Managers.managedList.itemList;
		String header[] = { "종류", "장르", "제목", "방영년도", "평점", "시청등급", "줄거리" };

		ArrayList<String[]> itemsData = new ArrayList<String[]>();
		for (Item item : items) {
			String sample[] = { item.getType(), item.getCategories(), item.getName(), item.getTime() + "",
					item.getGrade() + "", item.getRating() + "", item.getSummary() };
			itemsData.add(sample);
		}
		String contents[][] = new String[itemsData.size()][];
		int index = 0;
		for (String[] itemData : itemsData) {
			contents[index++] = itemData;
		}
		DefaultTableModel model = new DefaultTableModel(contents, header);
		JTable table = new JTable(model);
		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.setLocation(100, 60);
		scrollpane.setSize(560, 460);
		add(scrollpane);

		setLayout(null);

		JTextField sText = new JTextField();
		sText.setLocation(100, 20);
		sText.setSize(560, 30);
		add(sText);
		/* 관리자에선 textField.getText()해서 inputstr[]에 넣고 add, delete */
		JButton sbutton = new JButton("검색");
		sbutton.setSize(80, 30);
		sbutton.setLocation(670, 20);
		add(sbutton);
		sbutton.addActionListener(this);

		JComboBox<String> combo;
		String[] cbdata = { "제목", "방영년도", "평점" };

		JComboBox<String> jcb = new JComboBox<String>(cbdata);
		jcb.setLocation(20, 20);
		jcb.setSize(70, 30);
		add(jcb);

		jcb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String field = (String) jcb.getSelectedItem();
				if (field.equals("제목")) {
					Managers.sortingManager.mySort(Managers.managedList.itemList, 1);
				} else if (field.equals("방영년도")) {
					Managers.sortingManager.mySort(Managers.managedList.itemList, 2);
				} else if (field.equals("평점")) {
					Managers.sortingManager.mySort(Managers.managedList.itemList, 3);
				} else {
					return;
				}
				tableModelUpdate(model);
			}

		});

		sbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String field = (String) jcb.getSelectedItem();
				String word = sText.getText();
				ArrayList<String[]> searchData = new ArrayList<String[]>();

				if (!word.equals(null)) {
					// 검색
					tableModelUpdate(model);
					DefaultTableModel m1 = new DefaultTableModel();
					for (Item item : Managers.managedList.itemList) {
						if (item.match(word)) {
							String sample[] = { item.getType(), item.getCategories(), item.getName(),
									item.getTime() + "", item.getRating() + "", item.getGrade() + "",
									item.getSummary() };
							searchData.add(sample);
							item.print();
						}
					}
					int num = searchData.size();
					String contents1[][] = new String[num][];
					int index = 0;
					for (String[] itemsss : searchData) {
						contents1[index++] = itemsss;
					}
					DefaultTableModel model1 = new DefaultTableModel(contents1, header);
					JTable table1 = new JTable(model1);
					JScrollPane scrollpane1 = new JScrollPane(table1);
					add(scrollpane1);
					JOptionPane.showMessageDialog(null, scrollpane1, "Result", -1);

				}
			}

		});

	}

	void tableModelUpdate(DefaultTableModel model) {
		int rowCnt = model.getRowCount();
		for (int i = 0; i < rowCnt; i++) {
			model.removeRow(0);
		}

		ArrayList<Item> items = Managers.managedList.itemList;
		ArrayList<String[]> itemsData = new ArrayList<String[]>();
		for (Item item : items) {
			String sample[] = { item.getType(), item.getCategories(), item.getName(), item.getTime() + "",
					item.getRating() + "", item.getSummary() };
			itemsData.add(sample);
		}
		String contents[][] = new String[itemsData.size()][];
		int index = 0;
		for (String[] itemData : itemsData) {
			contents[index++] = itemData;
		}

		for (int i = 0; i < contents.length; i++) {
			model.addRow(contents[i]);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}
}

class JPanel033 extends JPanel {
	static List<Item> toShow;

	public JPanel033() {
		setLayout(null);
		JButton bt_img;
		toShow = Managers.recommendManager.provideRecommendItems();
		Item nowItem = toShow.get(0);
		nowArray++;
		summay = nowItem.getSummary();
		ImageIcon image = new ImageIcon(nowItem.getImagePath());
		Image image2 = image.getImage();
		Image i4 = image2.getScaledInstance(160, 250, java.awt.Image.SCALE_SMOOTH);
		ImageIcon i5 = new ImageIcon(i4);

		JButton b = new JButton(i5);
		b.setBounds(50, 20, 330, 550);
		b.setHorizontalTextPosition(SwingConstants.CENTER);
		b.setVerticalTextPosition(SwingConstants.BOTTOM);
		b.setBorderPainted(false);
		b.setFocusPainted(false);
		b.setBackground(Color.gray);
		add(b);

		JButton btTitle = new JButton("Title: " + nowItem.getName()); // 제목 데이터 읽어오기
		btTitle.setBorderPainted(false);
		btTitle.setContentAreaFilled(false);
		btTitle.setFocusPainted(false);
		btTitle.setOpaque(false);
		btTitle.setBounds(440, 150, 200, 30);
		btTitle.setForeground(Color.white);
		btTitle.setHorizontalAlignment(SwingConstants.LEFT);
		btTitle.setFont(new Font("고딕", Font.BOLD, 18));
		add(btTitle);

		JButton btGrade = new JButton("Grade: " + nowItem.getGrade()); // 평점 데이터 읽어오기
		btGrade.setBorderPainted(false);
		btGrade.setContentAreaFilled(false);
		btGrade.setFocusPainted(false);
		btGrade.setOpaque(false);
		btGrade.setBounds(440, 220, 200, 30);
		btGrade.setForeground(Color.white);
		btGrade.setHorizontalAlignment(SwingConstants.LEFT);
		btGrade.setFont(new Font("고딕", Font.BOLD, 18));
		add(btGrade);

		JButton btNext = new JButton("다음 추천 ->"); // 다음 추천 콘텐츠 보여주기
		btNext.setBounds(650, 220, 150, 30);
		btNext.setForeground(Color.white);
		btNext.setBackground(Color.pink);
		btNext.setHorizontalAlignment(SwingConstants.LEFT);
		btNext.setFont(new Font("고딕", Font.BOLD, 18));
		add(btNext);

		JButton bt1 = new JButton("줄거리");
		ActionListener handler = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("줄거리")) {
					JOptionPane.showMessageDialog(null, summay, "About 줄거리", JOptionPane.INFORMATION_MESSAGE); // 줄거리
																												// 데이터
																												// 읽어오기
				}
			}
		};
		bt1.addActionListener(handler);
		bt1.setBackground(Color.WHITE);
		bt1.setBounds(450, 300, 100, 30);
		add(bt1);

		btNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 새로운 추천 컨텐츠로 버튼 내용물 바꾸기
				toShow = Managers.recommendManager.provideRecommendItems();
				Item item = toShow.get(nowArray++);
				ImageIcon image = new ImageIcon(item.getImagePath());
				Image image2 = image.getImage();
				Image i4 = image2.getScaledInstance(320, 500, java.awt.Image.SCALE_SMOOTH);
				ImageIcon i5 = new ImageIcon(i4);
				b.setIcon(i5);
				btTitle.setText("Title: " + item.getName());
				btGrade.setText("Grade: " + item.getGrade());
				summay = item.getSummary();
				if (nowArray == 10)
					nowArray = 0;
			}
		});
	}

	static int nowArray = 0;
	static String summay;
}

class ReviewFrame extends JFrame {// 리뷰 Panel
	DefaultTableModel model;
	static Item nowItem = null;

	public ReviewFrame(String itemName) {
		for (Item item : Managers.managedList.itemList) {
			if (item.getName().equals(itemName)) {
				nowItem = item;
				break;
			}
		}
		JLabel label[] = new JLabel[10]; // Label 迭
		JTextField textfield[] = new JTextField[5];
		JButton review;
		Container contentPane;
		JLabel imageLabel;
		ImageIcon img0;

		// GridLayout frameLayout = new GridLayout(6, 1); // ü ū Ʋ GridLayout
		// frameLayout.setVgap(3); // GridLayout
		setLayout(null); // Layout set

		label[0] = new JLabel("영화 리뷰");
		label[1] = new JLabel("영화 정보");

		JButton bt_img1;
		ImageIcon images = new ImageIcon(nowItem.getImagePath());
		Image im = images.getImage(); // 뽑아온 이미지 객체 사이즈를 새롭게 만들기!
		Image im2 = im.getScaledInstance(200, 300, Image.SCALE_SMOOTH); // 새로 조절된 사이즈의 이미지(im2)를
		ImageIcon icon2 = new ImageIcon(im2);
		bt_img1 = new JButton(icon2);
		bt_img1.setSize(200, 300);
		bt_img1.setLocation(30, 30);
		add(bt_img1);

		JButton b = new JButton(nowItem.getName());
		b.setBorderPainted(false);
		b.setContentAreaFilled(false);
		b.setBorderPainted(false);
		b.setOpaque(false);
		b.setBounds(15, 300, 300, 100);
		b.setForeground(Color.black);
		b.setHorizontalAlignment(SwingConstants.LEFT);
		b.setFont(new Font("고딕", Font.BOLD, 16));

		add(b);

		JButton btt = new JButton(nowItem.getTime() + "");
		btt.setBorderPainted(false);
		btt.setContentAreaFilled(false);
		btt.setBorderPainted(false);
		btt.setOpaque(false);
		btt.setBounds(15, 350, 300, 100);
		btt.setForeground(Color.black);
		btt.setHorizontalAlignment(SwingConstants.LEFT);
		btt.setFont(new Font("고딕", Font.BOLD, 16));
		add(btt);

		JButton bttt = new JButton(nowItem.getGrade() + "");
		bttt.setBorderPainted(false);
		bttt.setContentAreaFilled(false);
		bttt.setBorderPainted(false);
		bttt.setOpaque(false);
		bttt.setBounds(15, 400, 300, 100);
		bttt.setForeground(Color.black);
		bttt.setHorizontalAlignment(SwingConstants.LEFT);
		bttt.setFont(new Font("고딕", Font.BOLD, 16));
		add(bttt);

		ArrayList<String[]> revs = new ArrayList<String[]>();
		for (Review rev : Managers.reviewManager.findByName(nowItem.getName())) {
			String[] sample = { rev.userId, rev.grade + "", rev.content };
			revs.add(sample);
		}
		String header[] = { "이름", "평점", "내용" };
		String contents[][] = new String[revs.size()][];
		int index = 0;
		for (String[] revData : revs) {
			contents[index++] = revData;
		}

		model = new DefaultTableModel(contents, header);

		JTable table = new JTable(model);
		table.getColumn("이름").setPreferredWidth(2);
		table.getColumn("평점").setPreferredWidth(2);
		table.getColumn("내용").setPreferredWidth(40);
		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.setLocation(270, 100);
		scrollpane.setSize(520, 450);
		add(scrollpane);

		review = new JButton("리뷰작성하기");
		review.setSize(120, 40);
		review.setLocation(450, 20);
		review.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ReviewAdd();
				bttt.setText(nowItem.getGrade() + "");
			}
		});

		add(review);

		setName("영화 상세 보기");
		setSize(900, 650);
		setLocation(400, 200);
		setVisible(true);
	}

	void tableModelUpdate(DefaultTableModel model) {
		int rowCnt = model.getRowCount();
		for (int i = 0; i < rowCnt; i++) {
			model.removeRow(0);
		}

		ArrayList<String[]> revs = new ArrayList<String[]>();
		for (Review rev : Managers.reviewManager.findByName(nowItem.getName())) {
			String[] sample = { rev.userId, rev.grade + "", rev.content };
			revs.add(sample);
		}
		String header[] = { "이름", "평점", "내용" };
		String contents[][] = new String[revs.size()][];
		int index = 0;
		for (String[] revData : revs) {
			contents[index++] = revData;
		}

		for (int i = 0; i < contents.length; i++) {
			model.addRow(contents[i]);
		}
	}

	public class ReviewAdd extends JFrame {
		JLabel label[] = new JLabel[11]; // Label 배열로 선언
		JTextField textfield[] = new JTextField[5]; // TextField 배열로 선언
		JTextArea textarea = new JTextArea(); // TextArea 선언
		JScrollPane sp; // TextArea의 스크롤바를 위한 ScrollPane 선언

		JRadioButton bad, soso, good, verygood, perfect; // RadioButton 선언
		JCheckBox travel, sleep; // CheckBos 선언
		ButtonGroup bg; // RadioButton의 그룹핑을 위한 ButtonGroup 선언
		JButton trans, cancel; // Button 선언

		JPanel panel3, panel4, panel5, panel6;
		JPanel panel31, panel32, panel41, panel42;

		public ReviewAdd() {
			GridLayout frameLayout = new GridLayout(6, 1); // 전체적인 큰 틀을 GridLayout으로 설정
			frameLayout.setVgap(3); // GridLayout간의 간격 설정
			setLayout(frameLayout); // 설정한 Layout을 set

			label[3] = new JLabel("별점");
			label[4] = new JLabel("영화");
			label[5] = new JLabel("영화리뷰");
			label[7] = new JLabel("*필수입력");
			textarea = new JTextArea(1, 1); // 리뷰작성
			bad = new JRadioButton("★☆☆☆☆");
			soso = new JRadioButton("★★☆☆☆");
			good = new JRadioButton("★★★☆☆");
			verygood = new JRadioButton("★★★★☆");
			perfect = new JRadioButton("★★★★★");

			bg = new ButtonGroup();
			bg.add(bad);
			bg.add(soso);
			bg.add(good);
			bg.add(verygood);
			bg.add(perfect);

			trans = new JButton("전송");
			cancel = new JButton("취소");

			panel3 = new JPanel();
			panel4 = new JPanel();
			panel5 = new JPanel();
			panel6 = new JPanel();

			panel31 = new JPanel();
			panel41 = new JPanel();
			panel42 = new JPanel();

			BorderLayout layout1 = new BorderLayout();
			BorderLayout layout2 = new BorderLayout();
			BorderLayout layout3 = new BorderLayout();
			BorderLayout layout4 = new BorderLayout();
			BorderLayout layout5 = new BorderLayout();

			panel3.setLayout(layout3);
			panel4.setLayout(layout4);
			panel5.setLayout(layout5);

			panel31.add(label[3]); // 평점
			panel31.add(bad); // 1점
			panel31.add(soso); // 2점
			panel31.add(good);
			panel31.add(verygood);
			panel31.add(perfect);
			panel3.add("West", panel31); // BorderLayout의 왼쪽

			panel42.add(label[7]); // *필수입력
			panel4.add("West", panel41); // BorderLayout의 왼쪽
			panel4.add("East", panel42); // BorderLayout의 오른쪽

			panel5.add("West", label[5]); // 영화리뷰
			sp = new JScrollPane(textarea); // TextArea에 ScrollPane을 적용
			sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			panel5.add(sp); // TextArea에 적용된 ScrollPane을 붙임

			panel6.add(trans); // 전송
			panel6.add(cancel); // 취소

			this.add(panel3);
			this.add(panel4);
			this.add(panel5);
			this.add(panel6);

			setTitle("리뷰작성");
			setSize(600, 350);
			setVisible(true);

			trans.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					float selectedGrade = 0;
					if (bad.isSelected())
						selectedGrade = 1;
					else if (soso.isSelected())
						selectedGrade = 2;
					else if (good.isSelected())
						selectedGrade = 3;
					else if (verygood.isSelected())
						selectedGrade = 4;
					else if (perfect.isSelected())
						selectedGrade = 5;
					Managers.reviewManager.writeRev(Managers.nowUser.userId, nowItem.getName(), selectedGrade,
							textarea.getText());
					tableModelUpdate(model);
					dispose();
				}
			});
			cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});

		}
	}
}

public class MainGUI extends JFrame {
	public JPanel011 jpanel01 = null;
	public JPanel022 jpanel02 = null;
	public JPanel033 jpanel03 = null;
	final static int GUI_WIDTH = 900;
	final static int GUI_HEIGHT = 650;

	public MainGUI() {
		init();
		runFrame();
	}

	public void init() {
		setTitle("OTT Service_ Coffee Machine");
		jpanel01 = new JPanel011();
		jpanel02 = new JPanel022();
		jpanel03 = new JPanel033();

		jpanel03.setBackground(Color.darkGray);
		jpanel01.setBackground(Color.darkGray);
		JTabbedPane jtab = new JTabbedPane(); // JTabbedPane 객체 생성
		jtab.setTabPlacement(JTabbedPane.TOP);
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(jpanel01);
		jtab.add("Contents", scroll);

		jtab.addTab("Search/Sorting", jpanel02);
		jtab.addTab("Recommend", jpanel03);

		Dimension dim = new Dimension(430, 400);
		setResizable(false);
		add(jtab);
		setLocation(400, 100);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBackground(Color.black);
		setSize(GUI_WIDTH, GUI_HEIGHT);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public void runFrame() {
		setVisible(true);
	}

	public static void setJButtonStyle(JButton btn) {
		btn.setBackground(Color.DARK_GRAY);
		btn.setForeground(Color.WHITE);
		btn.setFont(new Font("맑은 고딕", 0, 15));
		btn.setHorizontalAlignment(JLabel.CENTER);
	}
}