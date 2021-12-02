package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import ProcessManagement.Managers;

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
		JPanel jp_label = new JPanel();

		JToggleButton btns[] = new JToggleButton[Managers.managedList.itemList.size()];

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

	private JToggleButton makeImageButton(String imagePath, String buttonName) {
		ImageIcon image = new ImageIcon(imagePath);
		Image image2 = image.getImage();
		Image i4 = image2.getScaledInstance(160, 250, java.awt.Image.SCALE_SMOOTH);
		ImageIcon i5 = new ImageIcon(i4);

		JToggleButton b = new JToggleButton(buttonName, i5);
		b.setHorizontalTextPosition(SwingConstants.CENTER);
		b.setVerticalTextPosition(SwingConstants.BOTTOM);
		b.setBorderPainted(false);
		b.setFocusPainted(false);
		b.setBackground(Color.white);
		b.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					Managers.recommendManager.addInterestItem(b.getLabel());
				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {

					// isSelectedOrder = false;
				}
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
	public JPanel033() {
		// setLayout(null);
		JButton bt_img;

		// JTable table = new JTable();
		/*
		 * ImageIcon images = new ImageIcon("./NoRecommand.png"); Image im =
		 * images.getImage(); // 뽑아온 이미지 객체 사이즈를 새롭게 만들기! Image im2 =
		 * im.getScaledInstance(230, 230, Image.SCALE_SMOOTH);
		 * 
		 * ImageIcon icon2 = new ImageIcon(im2); bt_img = new JButton(icon2);
		 * bt_img.setSize(400, 400); bt_img.setLocation(220, 50);
		 * bt_img.setBorderPainted(false); bt_img.setFocusPainted(false);
		 * bt_img.setContentAreaFilled(false); add(bt_img);
		 * 
		 * JButton nextRecom; ImageIcon next = new ImageIcon("./NextRecommand.png");
		 * Image nextim = next.getImage(); Image im3 = nextim.getScaledInstance(150,
		 * 150, Image.SCALE_SMOOTH); ImageIcon icon3 = new ImageIcon(im3); nextRecom =
		 * new JButton(icon3); nextRecom.setSize(400, 400); nextRecom.setLocation(500,
		 * 50); nextRecom.setBorderPainted(false); // nextRecom.setFocusPainted(false);
		 * nextRecom.setContentAreaFilled(false); add(nextRecom);
		 */
		// 이벤트 처리하기

		// if 시청기록 없을 경우
		if (!Managers.recommendManager.existRecommendItem()) {
			// 원래 아무것도 아면 안됨
			/*
			 * JLabel label = new JLabel("추천드릴 콘텐츠가 없어요    :("); label.setLocation(330,
			 * 400); label.setSize(200, 20); add(label);
			 */
			///
			List<Item> toShow = Managers.recommendManager.provideRecommendItems();
			ImageIcon image = new ImageIcon("./images/Animation/겨울왕국.png");
			Image image2 = image.getImage();
			Image i4 = image2.getScaledInstance(320, 500, java.awt.Image.SCALE_SMOOTH);
			ImageIcon i5 = new ImageIcon(i4);

			JToggleButton b = new JToggleButton("겨울왕국", i5);
			b.setHorizontalTextPosition(SwingConstants.CENTER);
			b.setVerticalTextPosition(SwingConstants.BOTTOM);
			b.setBorderPainted(false);
			b.setFocusPainted(false);
			b.setBackground(Color.white);
			b.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent ev) {
					if (ev.getStateChange() == ItemEvent.SELECTED) {
					} else if (ev.getStateChange() == ItemEvent.DESELECTED) {

						// isSelectedOrder = false;
					}
				}
			});

			JLabel label = new JLabel("텍스트를 입력하시오.");
			JTextArea txt = new JTextArea();
			add(txt, BorderLayout.CENTER);
			add(b, BorderLayout.WEST);

			//
		} else {
			/*
			 * else 시청기록 있을 경우
			 * 
			 */

			List<Item> toShow = Managers.recommendManager.provideRecommendItems();
			ImageIcon image = new ImageIcon(Managers.managedList.itemList.get(0).getImagePath());
			Image image2 = image.getImage();
			Image i4 = image2.getScaledInstance(400, 700, java.awt.Image.SCALE_SMOOTH);
			ImageIcon i5 = new ImageIcon(i4);

			JToggleButton b = new JToggleButton(Managers.managedList.itemList.get(0).getName(), i5);
			b.setHorizontalTextPosition(SwingConstants.LEFT);
			b.setVerticalTextPosition(SwingConstants.BOTTOM);
			b.setBorderPainted(false);
			b.setFocusPainted(false);
			b.setBackground(Color.white);

			b.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent ev) {
					if (ev.getStateChange() == ItemEvent.SELECTED) {
						//
					} else if (ev.getStateChange() == ItemEvent.DESELECTED) {

						// isSelectedOrder = false;
					}
				}
			});
			add(b);
			for (Item item : toShow) {
				// 버튼 만들기
			}
		}
	}
}

class JPanel044 extends JPanel {// 리뷰 Panel
	public JPanel044() {
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
		ImageIcon images = new ImageIcon("./images/Animation/겨울왕국.png");
		Image im = images.getImage(); // 뽑아온 이미지 객체 사이즈를 새롭게 만들기!
		Image im2 = im.getScaledInstance(200, 300, Image.SCALE_SMOOTH); // 새로 조절된 사이즈의 이미지(im2)를
		ImageIcon icon2 = new ImageIcon(im2);
		bt_img1 = new JButton(icon2);
		bt_img1.setSize(200, 300);
		bt_img1.setLocation(30, 30);
		add(bt_img1);

		JButton b = new JButton("작품명");
		b.setBorderPainted(false);
		b.setContentAreaFilled(false);
		b.setBorderPainted(false);
		b.setOpaque(false);
		b.setBounds(15, 300, 300, 100);
		b.setForeground(Color.black);
		b.setHorizontalAlignment(SwingConstants.LEFT);
		b.setFont(new Font("고딕", Font.BOLD, 16));
		
		add(b);

		JButton btt = new JButton("상영시기");
		btt.setBorderPainted(false);
		btt.setContentAreaFilled(false);
		btt.setBorderPainted(false);
		btt.setOpaque(false);
		btt.setBounds(15, 350, 300, 100);
		btt.setForeground(Color.black);
		btt.setHorizontalAlignment(SwingConstants.LEFT);
		btt.setFont(new Font("고딕", Font.BOLD, 16));
		add(btt);

		JButton btlist = new JButton("리뷰 목록");
		btlist.setBorderPainted(false);
		btlist.setContentAreaFilled(false);
		btlist.setBorderPainted(false);
		btlist.setOpaque(false);
		btlist.setBounds(400, 200, 200, 80);
		btlist.setForeground(Color.black);
		btlist.setHorizontalAlignment(SwingConstants.LEFT);
		btlist.setFont(new Font("고딕", Font.BOLD, 18));
		add(btlist);

		review = new JButton("리뷰작성하기");
		review.setSize(120, 40);
		review.setLocation(450, 20);
		review.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ReviewAdd();
			}
			});
		
		add(review);

		setName("영화 상세 보기");
		setSize(600, 600);
		setLocation(400, 200);
		setVisible(true);
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
		JComboBox<String> jcb; // ComboBox 선언
		String phone[] = { "도둑들", "귀멸의 칼날", "베놈", "랑조", "컨저링", "기생충" }; // ComboBox를 위한 배열 선언

		JPanel panel3, panel4, panel5, panel6;
		JPanel panel31, panel32, panel41, panel42;

		public ReviewAdd() {

			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			GridLayout frameLayout = new GridLayout(6, 1); // 전체적인 큰 틀을 GridLayout으로 설정
			frameLayout.setVgap(3); // GridLayout간의 간격 설정
			this.setLayout(frameLayout); // 설정한 Layout을 set

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

			jcb = new JComboBox<String>(phone);

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

			panel41.add(label[4]); // 영화
			panel41.add(jcb); // ComboBox
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
		}
	}
}

public class MainGUI extends JFrame {
	public JPanel011 jpanel01 = null;
	public JPanel022 jpanel02 = null;
	public JPanel033 jpanel03 = null;
	public JPanel044 jpanel04 = null;
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
		jpanel04 = new JPanel044();

		JTabbedPane jtab = new JTabbedPane(); // JTabbedPane 객체 생성
		jtab.setTabPlacement(JTabbedPane.TOP);
		jtab.addTab("Contents", jpanel01);
		jtab.addTab("Search/Sorting", jpanel02);
		jtab.addTab("Recommend", jpanel03);
		jtab.addTab("Review", jpanel04);

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
