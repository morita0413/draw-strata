import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class Draw_strata_v3 {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("地層の定性表現");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        Draw_Strata tab1 = new Draw_Strata();
        tabbedPane.addTab("地層モデル描画", tab1);

        Check_Two_Strata tab2 = new Check_Two_Strata();
        tabbedPane.addTab("同一・連続性判定", tab2);
        
        Draw_Strata_v2 tab3 = new Draw_Strata_v2();
        tabbedPane.addTab("不整合地層モデル描画", tab3);

        frame.setSize(900, 720); 
        frame.getContentPane().add(tabbedPane);
        frame.setVisible(true);
    }
}

//---------------------------------地層描画クラス---------------------------
class Draw_Strata extends JPanel implements ActionListener {

	JPanel p = new JPanel();
	
	JTextField text1234, curve;

	String text1234String, curveString;
	
	//フォントサイズ
	int fontSize = 45;
	
	//四角形の左上の頂点(X,Y),一辺の大きさR
	int rectX = 100;
	int rectY = 100;
	int rectR = 400;

	//ボタン
	JButton button1 = new JButton("描画");
	JButton button2 = new JButton("回転");
	JButton button3 = new JButton("左右反転");
	JButton button4 = new JButton("上下反転");
	
	//各ボタンが押された回数
	int button1Count = 0;
	int button2Count = 0;
	int button3Count = 0;
	int button4Count = 0;

	//動的配列+初期化
	ArrayList<String> letter = new ArrayList<>(); //地層名
	ArrayList<ArrayList<Integer>> pair = new ArrayList<>(); //端点のペア
	ArrayList<ArrayList<Double>> point = new ArrayList<>(); //端点の座標
	ArrayList<ArrayList<Double>> control = new ArrayList<>(); //変曲点の座標
	ArrayList<ArrayList<Double>> letterPoint = new ArrayList<>(); //地層名の座標
	ArrayList<Integer> premiseCondition = new ArrayList<>(); //前提条件のエラー
	ArrayList<Integer> mainCondition = new ArrayList<>(); //本条件のエラー
	
	String text1234StringCopy = new String();
	String curveStringCopy = new String();
	
	public Draw_Strata() {

		super();
		
		setLayout(new BorderLayout()); // レイアウトマネージャを設定
		
		setVisible(true);//ウィンドウ表示

		//JLabel label = new JLabel();

		add(p, BorderLayout.PAGE_END); //検索ボックスの位置

		//文字列を取り出す
		text1234 = new JTextField();
		curve = new JTextField();

		set_button(p, button1, button2, button3, button4);
	}

	//ボタン押した時
	public void actionPerformed(ActionEvent e) {

		System.out.println("いずれかのボタンが押されました");
		
		//入力から取り出す
		text1234String = text1234.getText();
		curveString = curve.getText();
		
		String[] text1234StringArray = text1234String.split("");

		//初期化
		pair = new ArrayList<>();
		point = new ArrayList<>();
		control = new ArrayList<>();
		letter = new ArrayList<>();
		letterPoint = new ArrayList<>();
		premiseCondition = new ArrayList<>();
		mainCondition = new ArrayList<>();
		
		//print = 1にすると色々表示
		int print = 1;
		
		//1a2b3c4bパターン
		if(text1234StringArray[0].equals("1")) {
			
		}
		
		//(a)(b)(c)(b)パターン
		else if(text1234StringArray[0].equals("(")) {
			
		}

		
		//前提条件
		Validity_func.premise_condition_all(text1234String, curveString, premiseCondition);
		
		if(premiseCondition.size() == 0) {
			Validity_func.main_condition_all(text1234String, curveString, mainCondition);
		}

		//エラーがないとき(妥当性を全て満たすとき)
		if (premiseCondition.size() == 0 && mainCondition.size() == 0) {

			//各ボタンの処理
			if (e.getSource() == button1) {
				System.out.println("描画ボタンが押されました");

				//ボタン回数初期化
				button1Count = 1;
				button2Count = 0;
				button3Count = 0;
				button4Count = 0;
			}
			if (e.getSource() == button2) {
				System.out.println("回転ボタンが押されました");
				if (button2Count == 0) {
					text1234StringCopy = Operation_func.spin_string(text1234String);
					curveStringCopy = Operation_func.change_curve_string(curveString, 2);
				} else if (button2Count > 0) {
					text1234StringCopy = Operation_func.spin_string(text1234StringCopy);
					curveStringCopy = Operation_func.change_curve_string(curveStringCopy, 2);
				}

				button2Count++;
			}
			if (e.getSource() == button3) {
				System.out.println("左右反転ボタンが押されました");

				if (button3Count == 0) {
					text1234StringCopy = Operation_func.frip_string(text1234String, 3);
					curveStringCopy = Operation_func.change_curve_string(curveString, 3);
				} else if (button3Count > 0) {
					text1234StringCopy = Operation_func.frip_string(text1234StringCopy, 3);
					curveStringCopy = Operation_func.change_curve_string(curveStringCopy, 3);
				}

				button3Count++;
			}
			if (e.getSource() == button4) {
				System.out.println("上下反転ボタンが押されました");

				if (button4Count == 0) {
					text1234StringCopy = Operation_func.frip_string(text1234String, 4);
					curveStringCopy = Operation_func.change_curve_string(curveString, 4);
				} else if (button4Count > 0) {
					text1234StringCopy = Operation_func.frip_string(text1234StringCopy, 4);
					curveStringCopy = Operation_func.change_curve_string(curveStringCopy, 4);
				}

				button4Count++;
			}

			//描画以外のいずれかのボタンが押された場合、書き換えた結果をtext1234Stringに代入
			if (button2Count > 0 || button3Count > 0 || button4Count > 0) {
				text1234String = text1234StringCopy;
				curveString = curveStringCopy;
			}

			//pair,point,control計算(直線曲線対応)
			String_func.process_find_pair_point_control(text1234String, curveString, rectX, rectY, rectR,
					pair, point, control);

			//地層名計算
			String_func.find_letter_point(text1234String, curveString, rectX, rectY, rectR,
					letter, letterPoint, fontSize);

			//確認用
			if(print == 1) {
				System.out.println("-text1234String-");
				System.out.println(text1234String);
	
				System.out.println("-curveString-");
				System.out.println(curveString);
	
				System.out.println("-text1234SymbolOnly-");
				System.out.println(Arrays.toString(String_func.find_symbol_only(text1234String)));
				
				ArrayList<String> onlyOneSymbol = new ArrayList<>();
				ArrayList<Integer> onlyOneSymbolPoint = new ArrayList<>();
				String_func.find_only_one_symbol_point(text1234String, onlyOneSymbol, onlyOneSymbolPoint);
				System.out.println("-onlyOneSymbol-");
				System.out.println(onlyOneSymbol);
	
				System.out.println("-gap-");
				System.out.println(Arrays.deepToString(String_func.find_gap(text1234String)));
	
				System.out.println("-pair-");
				System.out.println(pair);
	
				System.out.println("-point-");
				System.out.println(point);
	
				System.out.println("-control-");
				System.out.println(control);
	
				System.out.println("-letter-");
				for (int i = 0; i < letter.size(); i++) {
					System.out.println(
							letter.get(i) + ": (" + letterPoint.get(i).get(0) + "," + letterPoint.get(i).get(1) + ")");
				}
			}
		}

		if(print == 1) {
			System.out.println("premiseCondition");
			System.out.println(premiseCondition);
	
			System.out.println("mainCondition");
			System.out.println(mainCondition);
		}

		repaint();
	}

	public void paint(Graphics g) {

		super.paint(g);

		//太線処理
		Graphics2D g2 = (Graphics2D) g;
		BasicStroke bs = new BasicStroke(5); //ここで太さ調節
		g2.setStroke(bs);

		//四角形描画
		Draw_func.draw_rect(g, rectX, rectY, rectR);

		//エラーがないとき(妥当性を全て満たすとき)
		if (premiseCondition.size() == 0 && mainCondition.size() == 0) {

			//いずれかのボタンが押されたとき
			if (button1Count > 0 || button2Count > 0 || button3Count > 0 || button4Count > 0) {

				//境界線描画(直線)
				if (curveString.equals("")) {
					Draw_func.draw_straight_line(g, pair, point);
				}

				//境界線描画(曲線)
				else if (curveString.equals("1") || curveString.equals("2") || curveString.equals("3")
						|| curveString.equals("4")) {
					Draw_func.draw_curve_line(g, pair, point, control);
				}

				//地層名表示
				Draw_func.draw_strata_name(g, letterPoint, letter, fontSize);

				//変化後の文字列表示
				Draw_func.draw_change_string(g2, text1234String, curveString, rectX, rectY, rectR,
						button2Count, button3Count, button4Count);
			}
		}

		Draw_func.draw_vali_ng(g, rectX, rectY, rectR, premiseCondition, mainCondition);

	}

	//ボタンを配置する関数
	public void set_button(JPanel p,
			JButton button1, JButton button2, JButton button3, JButton button4) {

		text1234.setPreferredSize(new Dimension(350, 40)); //大きさ
		text1234.setFont(new Font("ＭＳ Ｐゴシック", Font.BOLD, 40)); //フォント、サイズ

		curve.setPreferredSize(new Dimension(40, 40));
		curve.setFont(new Font("ＭＳ Ｐゴシック", Font.BOLD, 40));

		button1.setPreferredSize(new Dimension(80, 40));
		button1.setFont(new Font("ＭＳ Ｐゴシック", Font.BOLD, 20));
		button1.addActionListener(this);

		button2.setPreferredSize(new Dimension(80, 40));
		button2.setFont(new Font("ＭＳ Ｐゴシック", Font.BOLD, 20));
		button2.addActionListener(this);

		button3.setPreferredSize(new Dimension(120, 40));
		button3.setFont(new Font("ＭＳ Ｐゴシック", Font.BOLD, 20));
		button3.addActionListener(this);

		button4.setPreferredSize(new Dimension(120, 40));
		button4.setFont(new Font("ＭＳ Ｐゴシック", Font.BOLD, 20));
		button4.addActionListener(this);

		p.add(text1234);
		p.add(curve);
		p.add(button1);
		p.add(button2);
		p.add(button3);
		p.add(button4);
	}
}

//------------------------------------同一性連続性チェッククラス-----------------------------
class Check_Two_Strata extends JPanel implements ActionListener {
	
	JPanel p = new JPanel();
	
	//R = Right, L = Left
	JTextField text1234R, text1234L, curveR, curveL;

	String text1234StringR, curveStringR, text1234StringL, curveStringL;
	String text1234StringRCopy, curveStringRCopy, text1234StringLCopy, curveStringLCopy;
	
	//フォントサイズ
	int fontSize = 30;
	
	//四角形の左上の頂点(X,Y),一辺の大きさR
	int rectXR = 500;
	int rectXL = 100;
	
	int rectY = 100;
	int rectR = 300;

	//ボタン
	JButton button1 = new JButton("同一・連続判定");
	JButton button2 = new JButton("連続モデル描画");
	
	//各ボタンが押された回数
	int button1Count = 0;
	int button2Count = 0;
	
	//動的配列+初期化
	ArrayList<String> letterR = new ArrayList<>(); //地層名
	ArrayList<ArrayList<Integer>> pairR = new ArrayList<>(); //端点のペア
	ArrayList<ArrayList<Double>> pointR = new ArrayList<>(); //端点の座標
	ArrayList<ArrayList<Double>> controlR = new ArrayList<>(); //変曲点の座標
	ArrayList<ArrayList<Double>> letterPointR = new ArrayList<>(); //地層名の座標
	ArrayList<Integer> premiseConditionR = new ArrayList<>(); //前提条件のエラー
	ArrayList<Integer> mainConditionR = new ArrayList<>(); //本条件のエラー
	String text1234StringCopyR = new String();
	String curveStringCopyR = new String();
	
	ArrayList<String> letterL = new ArrayList<>(); //地層名
	ArrayList<ArrayList<Integer>> pairL = new ArrayList<>(); //端点のペア
	ArrayList<ArrayList<Double>> pointL = new ArrayList<>(); //端点の座標
	ArrayList<ArrayList<Double>> controlL = new ArrayList<>(); //変曲点の座標
	ArrayList<ArrayList<Double>> letterPointL = new ArrayList<>(); //地層名の座標
	ArrayList<Integer> premiseConditionL = new ArrayList<>(); //前提条件のエラー
	ArrayList<Integer> mainConditionL = new ArrayList<>(); //本条件のエラー
	String text1234StringCopyL = new String();
	String curveStringCopyL = new String();
	
	int[] idenSuccCondition = {0,0,0}; //同一性・連続性のエラー
	ArrayList<ArrayList<String>> succStringArray = new ArrayList<>(); //連続性がある記号列の組
		
	public Check_Two_Strata() {

		super();
		
		setLayout(null); // レイアウトマネージャを設定

		//JLabel label = new JLabel();

		//文字列を取り出す
		text1234R = new JTextField();
		curveR = new JTextField();
		
		text1234L = new JTextField();
		curveL = new JTextField();
		
		set_button2(p, button1, button2);
		
		button2.setVisible(false);

	}
	
	//ボタン押された時
	public void actionPerformed(ActionEvent e) {
		
		//入力から取り出す
		text1234StringR = text1234R.getText();
		curveStringR = curveR.getText();
		text1234StringL = text1234L.getText();
		curveStringL = curveL.getText();
		
		text1234StringRCopy = null;
		curveStringRCopy = null;
		text1234StringLCopy = null;
		curveStringLCopy = null;
		//初期化
		pairR = new ArrayList<>();
		pointR = new ArrayList<>();
		controlR = new ArrayList<>();
		letterR = new ArrayList<>();
		letterPointR = new ArrayList<>();
		premiseConditionR = new ArrayList<>();
		mainConditionR = new ArrayList<>();
		
		pairL = new ArrayList<>();
		pointL = new ArrayList<>();
		controlL = new ArrayList<>();
		letterL = new ArrayList<>();
		letterPointL = new ArrayList<>();
		premiseConditionL = new ArrayList<>();
		mainConditionL = new ArrayList<>();
		
		//print = 1にすると色々表示
		int print = 1;
		
		
		//各ボタンの処理
		//同一性・連続性判定
		if (e.getSource() == button1) {
			System.out.println("判定ボタンが押されました");

			//ボタン回数初期化
			button1Count = 1;
			button2Count = 0;
			
			succStringArray = new ArrayList<>(); 
			
			text1234StringRCopy = text1234StringR;
			curveStringRCopy = curveStringR;
			text1234StringLCopy = text1234StringL;
			curveStringLCopy = curveStringL;
			
			//前提条件
			Validity_func.premise_condition_all(text1234StringRCopy, curveStringRCopy, premiseConditionR);
			Validity_func.premise_condition_all(text1234StringLCopy, curveStringLCopy, premiseConditionL);

			//本条件
			if(premiseConditionR.size() == 0) {
				Validity_func.main_condition_all(text1234StringRCopy, curveStringRCopy, mainConditionR);
			}
			
			if(premiseConditionL.size() == 0) {
				Validity_func.main_condition_all(text1234StringLCopy, curveStringLCopy, mainConditionL);
			}

			//同一性・連続性判定
			if(premiseConditionL.size() == 0 && premiseConditionR.size() == 0 &&
					mainConditionL.size() == 0 && mainConditionR.size() == 0) {

				idenSuccCondition = Iden_succ_func.check_iden_succ(text1234StringLCopy, curveStringLCopy, text1234StringRCopy, curveStringRCopy);
			}

			//連続性を満たす記号列計算
			if (idenSuccCondition[1] == 1 && idenSuccCondition[2] == 1) {
				
				succStringArray = Iden_succ_func.find_succ_string(text1234StringL, curveStringL, text1234StringR, curveStringR);
				
				//ボタン名変更
				button2.setText("連続モデル描画");
			}

			//pair, point計算(右側)
			if (premiseConditionR.size() == 0 && mainConditionR.size() == 0) {

				//pair,point,control計算(直線曲線対応)
				String_func.process_find_pair_point_control(text1234StringRCopy, curveStringRCopy, rectXR, rectY, rectR,
						pairR, pointR, controlR);
				//地層名計算
				String_func.find_letter_point(text1234StringRCopy, curveStringRCopy, rectXR, rectY, rectR,
						letterR, letterPointR, fontSize);
			}
			//pair, point計算(左側)
			if (premiseConditionL.size() == 0 && mainConditionL.size() == 0) {

				//pair,point,control計算(直線曲線対応)
				String_func.process_find_pair_point_control(text1234StringLCopy, curveStringLCopy, rectXL, rectY, rectR,
						pairL, pointL, controlL);
				//地層名計算
				String_func.find_letter_point(text1234StringLCopy, curveStringLCopy, rectXL, rectY, rectR,
						letterL, letterPointL,fontSize);
			}
			
			//確認用
			if(print == 1 && 
					premiseConditionL.size() == 0 && premiseConditionR.size() == 0 &&
					mainConditionL.size() == 0 && mainConditionR.size() == 0) {
				
				System.out.println("-確認用-");
				
				boolean iden = Iden_succ_func.check_iden(text1234StringL, text1234StringR);
				System.out.printf("iden:%b\n",iden);
				
				System.out.printf("curveStringL,curveStringR:%s,%s\n", curveStringL,curveStringR);
				
				ArrayList<ArrayList<Integer>> connectionSide = Iden_succ_func.find_connection_string(text1234StringL, text1234StringR);
				System.out.println("-connectionSide-");
				System.out.println(connectionSide);

				System.out.println("-succStringArray-");
				System.out.println(succStringArray);
				
				System.out.printf("idenSuccCondition:[%d,%d,%d]\n",idenSuccCondition[0], idenSuccCondition[1], idenSuccCondition[2]);
			}
		}
		
		//連続性満たすときだけボタン表示
		if (idenSuccCondition[1] == 1 && idenSuccCondition[2] == 1) {
			button2.setVisible(true);
		}
		else if(idenSuccCondition[0] == 0 || idenSuccCondition[1] == -1 || idenSuccCondition[2] == -1){
			button2.setVisible(false);
		}
		
		//連続性を満たすようにモデル回転
		if (e.getSource() == button2) {
			System.out.println("描画ボタンが押されました");
			
			//ボタン名変更
			button2.setText("次の連続モデル");

			//組み合わせの種類以上押されると回数リセット
			if(button2Count == succStringArray.size()) {button2Count = 0;}
			
			if(button1Count > 0 && succStringArray.size() > 0) {

				if(succStringArray.size() > button2Count) {button2Count++;}
				text1234StringLCopy = succStringArray.get(button2Count-1).get(0);
				curveStringLCopy = succStringArray.get(button2Count-1).get(1);
				text1234StringRCopy = succStringArray.get(button2Count-1).get(2);
				curveStringRCopy = succStringArray.get(button2Count-1).get(3);
			
				//pair, point計算(右側)
				//pair,point,control計算(直線曲線対応)
				String_func.process_find_pair_point_control(text1234StringRCopy, curveStringRCopy, rectXR, rectY, rectR,
						pairR, pointR, controlR);
				//地層名計算
				String_func.find_letter_point(text1234StringRCopy, curveStringRCopy, rectXR, rectY, rectR,
						letterR, letterPointR, fontSize);
				
				//pair, point計算(左側)
				//pair,point,control計算(直線曲線対応)
				String_func.process_find_pair_point_control(text1234StringLCopy, curveStringLCopy, rectXL, rectY, rectR,
						pairL, pointL, controlL);
				//地層名計算
				String_func.find_letter_point(text1234StringLCopy, curveStringLCopy, rectXL, rectY, rectR,
						letterL, letterPointL,fontSize);

			}
		}
		
		repaint();
	}

	public void paint(Graphics g) {

		super.paint(g);

		//太線処理
		Graphics2D g2 = (Graphics2D) g;
		BasicStroke bs = new BasicStroke(5); //ここで太さ調節
		g2.setStroke(bs);

		//四角形描画
		g.drawRect(rectXR, rectY, rectR, rectR);
		g.drawRect(rectXL, rectY, rectR, rectR);
		
		//頂点番号描画
		if(button2Count == 0) {
			Draw_func.draw_vertex_number(g, curveStringR, null, rectXR, rectY, rectR); 
			Draw_func.draw_vertex_number(g, curveStringL, null, rectXL, rectY, rectR); 
		}
		
		//ボタン1(同一性・連続性判定)が押されたとき
		if (button1Count == 1 && button2Count == 0) {

			//エラーがないとき(妥当性を全て満たすとき)
			if (premiseConditionR.size() == 0 && mainConditionR.size() == 0) {
				//境界線描画(直線)
				if (curveStringR.equals("")) {
					Draw_func.draw_straight_line(g, pairR, pointR);
				}
				//境界線描画(曲線)
				else if (curveStringR.equals("1") || curveStringR.equals("2") || curveStringR.equals("3")
						|| curveStringR.equals("4")) {
					Draw_func.draw_curve_line(g, pairR, pointR, controlR);
				}
			}
			
			//エラーがないとき(妥当性を全て満たすとき)
			if (premiseConditionL.size() == 0 && mainConditionL.size() == 0) {
				//境界線描画(直線)
				if (curveStringL.equals("")) {
					Draw_func.draw_straight_line(g, pairL, pointL);
				}
				//境界線描画(曲線)
				else if (curveStringL.equals("1") || curveStringL.equals("2") || curveStringL.equals("3")
						|| curveStringL.equals("4")) {
					Draw_func.draw_curve_line(g, pairL, pointL, controlL);
				}
			}

			//地層名表示
			Draw_func.draw_strata_name(g, letterPointR, letterR, fontSize);
			Draw_func.draw_strata_name(g, letterPointL, letterL, fontSize);
		}

		if (button2Count > 0) {

			if(button1Count > 0 && succStringArray.size() > 0 && succStringArray.size() >= button2Count) {
				
				text1234StringLCopy = succStringArray.get(button2Count-1).get(0);
				curveStringLCopy = succStringArray.get(button2Count-1).get(1);
				text1234StringRCopy = succStringArray.get(button2Count-1).get(2);
				curveStringRCopy = succStringArray.get(button2Count-1).get(3);
				
				//境界線表示(曲線のみ)
				if (curveStringR.equals("1") || curveStringR.equals("2") || curveStringR.equals("3")
						|| curveStringR.equals("4")) {
					Draw_func.draw_curve_line(g, pairR, pointR, controlR);
				}
				if (curveStringL.equals("1") || curveStringL.equals("2") || curveStringL.equals("3")
							|| curveStringL.equals("4")) {
					Draw_func.draw_curve_line(g, pairL, pointL, controlL);
				}
				
				//頂点番号描画
				Draw_func.draw_vertex_number(g, curveStringR, succStringArray.get(button2Count-1).get(3), rectXR, rectY, rectR); 
				Draw_func.draw_vertex_number(g, curveStringL, succStringArray.get(button2Count-1).get(1), rectXL, rectY, rectR); 

				//回転角度表示
				Draw_func.draw_spin_angle(g, curveStringR, succStringArray.get(button2Count-1).get(3), rectXR, rectY, rectR); 
				Draw_func.draw_spin_angle(g, curveStringL, succStringArray.get(button2Count-1).get(1), rectXL, rectY, rectR); 

				
				//地層名表示
				Draw_func.draw_strata_name(g, letterPointR, letterR, fontSize);
				Draw_func.draw_strata_name(g, letterPointL, letterL, fontSize);
			
				//変化後の文字列表示
				Draw_func.draw_succ_string(g, text1234StringLCopy, curveStringLCopy, rectXL, rectY, rectR);
				Draw_func.draw_succ_string(g, text1234StringRCopy, curveStringRCopy, rectXR, rectY, rectR);
				
				//連続モデルの種類表示
				Draw_func.draw_succ_type(g, succStringArray.size(), button2Count, rectXR, rectY, rectR);
								
			}
		}
		

		//エラー表示
		Draw_func.draw_iden_succ_ng(g, rectXR, rectY, rectR,
										premiseConditionL, mainConditionL,
										premiseConditionR,  mainConditionR, idenSuccCondition);
	}

	//ボタンを配置する関数
	public void set_button2(JPanel p, JButton button1, JButton button2) {

		text1234R.setBounds(rectXR+20, rectY+rectR+100, 200, 30);
		text1234R.setFont(new Font("ＭＳ Ｐゴシック", Font.BOLD, 20));
		curveR.setBounds(rectXR+230, rectY+rectR+100, 40, 30);
		curveR.setFont(new Font("ＭＳ Ｐゴシック", Font.BOLD, 20));
		
		text1234L.setBounds(rectXL+20, rectY+rectR+100, 200, 30);
		text1234L.setFont(new Font("ＭＳ Ｐゴシック", Font.BOLD, 20));
		curveL.setBounds(rectXL+230, rectY+rectR+100, 40, 30);
		curveL.setFont(new Font("ＭＳ Ｐゴシック", Font.BOLD, 20));
		
		button1.setBounds(rectXL+rectR/2, rectY+rectR+150, 200, 30);
		button1.setFont(new Font("ＭＳ Ｐゴシック", Font.BOLD, 20));
		button1.addActionListener(this);
		
		button2.setBounds(rectXL+rectR/2+210, rectY+rectR+150, 200, 30);
		button2.setFont(new Font("ＭＳ Ｐゴシック", Font.BOLD, 20));
		button2.addActionListener(this);
		
		add(text1234R);
		add(curveR);
		add(text1234L);
		add(curveL);
		add(button1);
		add(button2);
	}

}

//----------------------------------不整合表示クラス--------------------------------------
class Draw_Strata_v2 extends JPanel implements ActionListener {

	JPanel p = new JPanel();
	
	JTextField text, heightRel;

	String textString, heightRelString;
	
	//フォントサイズ
	int fontSize = 45;
	
	//四角形の左上の頂点(X,Y),一辺の大きさR
	double rectX = 100;
	double rectY = 100;
	double rectR = 400;

	//ボタン
	JButton button1 = new JButton("描画");
	
	//各ボタンが押された回数
	int button1Count = 0;

	//動的配列+初期化
	ArrayList<String> letter = new ArrayList<>(); //地層名
	ArrayList<ArrayList<Integer>> pair = new ArrayList<>(); //端点のペア
	ArrayList<ArrayList<Double>> point = new ArrayList<>(); //端点の座標
	ArrayList<ArrayList<Double>> letterPoint = new ArrayList<>(); //地層名の座標
	//ArrayList<Integer> premiseCondition = new ArrayList<>(); //前提条件のエラー
	//ArrayList<Integer> mainCondition = new ArrayList<>(); //本条件のエラー
	
	ArrayList<ArrayList<Double>> cornerPoint = new ArrayList<>(); //各頂点の座標
	//この部分もう少し後の方がいいかも
	/*cornerPoint.add(new ArrayList<Double>(Arrays.asList(rectX, rectY)));
	cornerPoint.add(new ArrayList<>(Arrays.asList(rectX + rectR, rectY)));
	cornerPoint.add(new ArrayList<>(Arrays.asList(rectX + rectR, rectY + rectR)));
	cornerPoint.add(new ArrayList<>(Arrays.asList(rectX, rectY + rectR)));*/
	
	String textStringCopy = new String();
	String heightRelStringCopy = new String();
	
	public Draw_Strata_v2() {

		super();
		
		setLayout(new BorderLayout()); // レイアウトマネージャを設定
		
		setVisible(true);//ウィンドウ表示

		//JLabel label = new JLabel();

		add(p, BorderLayout.PAGE_END); //検索ボックスの位置

		//文字列を取り出す
		text = new JTextField();
		heightRel = new JTextField();

		set_button1(p, button1);
	}
	public void actionPerformed(ActionEvent e) {
		System.out.println("ボタンが押されました");
		
		//入力から取り出す
		textString = text.getText();
		heightRelString = heightRel.getText();
		
		//初期化
		cornerPoint = new ArrayList<>();
		pair = new ArrayList<>(); //端点のペア
		point = new ArrayList<>();
		
		cornerPoint.add(new ArrayList<Double>(Arrays.asList(rectX, rectY)));
		cornerPoint.add(new ArrayList<Double>(Arrays.asList(rectX + rectR, rectY)));
		cornerPoint.add(new ArrayList<Double>(Arrays.asList(rectX + rectR, rectY + rectR)));
		cornerPoint.add(new ArrayList<Double>(Arrays.asList(rectX, rectY + rectR)));
		
		
		//ここからテスト部分
		/*System.out.println("-cornerPoint-");
		System.out.println(cornerPoint);
		
		ArrayList<ArrayList<String>> cutText = new ArrayList<>();
		cutText = String_func.find_cut_string_2(textString);
		point = String_func.find_point_2(cutText.get(0), cornerPoint);
		
		String[] heightRelStringArray = heightRelString.split("");
		pair.add (String_func.find_pair_2(cutText.get(0), heightRelStringArray[0]));
		//System.out.println("-pair-");
		//System.out.println(pair);
		
		double topPoint[] = new double[4];
		topPoint = String_func.find_top_point(cutText.get(0), cornerPoint, heightRelStringArray[0]);
		
		System.out.println("-topPoint-");
		System.out.println(Arrays.toString(topPoint));
		
		
		ArrayList<ArrayList<Double>> updCornerPoint = new ArrayList<>();
		updCornerPoint =  String_func.upd_corner_point(cutText.get(0), cornerPoint, heightRelStringArray[0]);
		*/
		
		letter = String_func.change_arraylist_string(heightRelString);
		String_func.process_find_pair_point_2(textString, heightRelString, cornerPoint, point, letterPoint);
		 
		System.out.println("-ActionEvent e内ここから-");
		System.out.println("-conrnerPoint-");
		System.out.println(cornerPoint);
		System.out.println("-point-");
		System.out.println(point);
		System.out.println("-letterPoint-");
		System.out.println(letterPoint);
		System.out.println("-ActionEvent e内ここまで-");
		//ここまで
		
		repaint();
	}
	
	//ボタンを配置する関数
	public void set_button1(JPanel p, JButton button1) {

		text.setPreferredSize(new Dimension(500, 40)); //大きさ
		text.setFont(new Font("ＭＳ Ｐゴシック", Font.BOLD, 40)); //フォント、サイズ

		heightRel.setPreferredSize(new Dimension(150, 40));
		heightRel.setFont(new Font("ＭＳ Ｐゴシック", Font.BOLD, 40));

		button1.setPreferredSize(new Dimension(80, 40));
		button1.setFont(new Font("ＭＳ Ｐゴシック", Font.BOLD, 20));
		button1.addActionListener(this);

		p.add(text);
		p.add(heightRel);
		p.add(button1);
	}
	
	public void paint(Graphics g) {
		super.paint(g);

		//太線処理
		Graphics2D g2 = (Graphics2D) g;
		BasicStroke bs = new BasicStroke(5); //ここで太さ調節
		g2.setStroke(bs);

		//四角形描画
		Draw_func.draw_rect(g, (int)rectX, (int)rectY, (int)rectR);
		
		//境界線描画(直線)
		Draw_func.draw_straight_line_2(g, point);
		
		//地層名描画
		Draw_func.draw_strata_name_2(g, letterPoint, letter, fontSize);
		
		//文字列表示
		Draw_func.draw_string(g, textString, heightRelString, (int)rectX, (int)rectY, (int)rectR, fontSize-20);;
		
		//確認用
		/*System.out.println("-draw_straight_line時-");
		System.out.println("-pair-");
		System.out.println(pair);
		System.out.println("-point-");
		System.out.println(point);*/
		
	}
}


//----------------------------描画関連の関数をまとめたクラス------------------------------
class Draw_func {
	//四角形(外側)を描画する関数
	public static void draw_rect(Graphics g, int rectX, int rectY, int rectR) {
		//四角形描画
		g.drawRect(rectX, rectY, rectR, rectR);

		//四隅1234表示
		Font font1 = new Font("ＭＳ Ｐゴシック", Font.BOLD, 60);
		g.setFont(font1);
		g.drawString("1", rectX - 30, rectY - 5);
		g.drawString("2", rectX + rectR + 5, rectY - 5);
		g.drawString("3", rectX + rectR + 5, rectY + rectR + 50);
		g.drawString("4", rectX - 30, rectY + rectR + 50);
	}

	//直線を描画する関数
	public static void draw_straight_line(Graphics g, ArrayList<ArrayList<Integer>> pair,
			ArrayList<ArrayList<Double>> point) {
		for (int n = 0; n < pair.size(); n++) {
			//pair[n][0] -> pair.get(n).get(0)
			int pair0 = pair.get(n).get(0); //ペアの片方
			int pair1 = pair.get(n).get(1); //ペアのもう片方

			double startX = point.get(pair0).get(0); //片方のx座標
			double startY = point.get(pair0).get(1); //片方のy座標
			double endX = point.get(pair1).get(0); //もう片方のx座標
			double endY = point.get(pair1).get(1); //もう片方のy座標

			g.drawLine((int) startX, (int) startY, (int) endX, (int) endY);
		}
	}

	//曲線を描画する関数
	public static void draw_curve_line(Graphics g2, ArrayList<ArrayList<Integer>> pair,
			ArrayList<ArrayList<Double>> point, ArrayList<ArrayList<Double>> control) {
		Path2D.Double p = new Path2D.Double();

		for (int n = 0; n < pair.size(); n++) {

			int pair0 = pair.get(n).get(0); //ペアの片方
			int pair1 = pair.get(n).get(1); //ペアのもう片方

			double startX = point.get(pair0).get(0); //片方のx座標
			double startY = point.get(pair0).get(1); //片方のy座標
			double endX = point.get(pair1).get(0); //もう片方のx座標
			double endY = point.get(pair1).get(1); //もう片方のy座標
			double controlX = control.get(n).get(0); //変曲点のx座標
			double controlY = control.get(n).get(1); //変曲点のy座標

			p.moveTo(startX, startY); //始点
			p.quadTo(controlX, controlY, endX, endY); //quadTo(制御点x,制御点y,終点x,終点y)
			((Graphics2D) g2).draw(p);
		}
	}

	//地層名描画
	public static void draw_strata_name(Graphics g, ArrayList<ArrayList<Double>> letterPoint,
			ArrayList<String> letter, int fontSize) {

		double letterPointX = 0;
		double letterPointY = 0;

		Font font3 = new Font("ＭＳ Ｐゴシック", Font.BOLD, fontSize);
		g.setFont(font3);
		for (int i = 0; i < letter.size(); i++) {

			letterPointX = letterPoint.get(i).get(0);
			letterPointY = letterPoint.get(i).get(1);

			if (letter.get(i).equals("t") || letter.get(i).equals("T")) {

				g.setColor(Color.blue);
				g.drawString(letter.get(i), (int) letterPointX, (int) letterPointY);
				g.setColor(Color.black);
			}

			else {
				g.drawString(letter.get(i), (int) letterPointX, (int) letterPointY);
			}
		}

		//letter(地層名の座標など表示)
		/*System.out.println("-letter-");
		for(int i = 0; i <  letter.size(); i++){
			System.out.println(letter.get(i) + ": (" + letterPoint.get(i).get(0) + "," +letterPoint.get(i).get(1) + ")");
		}*/

	}

	//変化後の文字列表示
	public static void draw_change_string(Graphics g, String text1234String, String curveString,
			int rectX, int rectY, int rectR,
			int button2Count, int button3Count, int button4Count) {

		Font font1 = new Font("ＭＳ Ｐゴシック", Font.BOLD, 22);
		g.setFont(font1);

		int i = 0;
		String[] text1234StringArray = text1234String.split("");

		if (button2Count > 0 || button3Count > 0 || button4Count > 0) {
			if (button2Count % 4 == 0) {
				g.drawString("回転なし", rectX + 10, rectY + rectR + 60);
			}
			if (button2Count % 4 == 1) {
				g.drawString("90°回転", rectX + 10, rectY + rectR + 60);
			}
			if (button2Count % 4 == 2) {
				g.drawString("180°回転", rectX + 10, rectY + rectR + 60);
			}
			if (button2Count % 4 == 3) {
				g.drawString("270°回転", rectX + 10, rectY + rectR + 60);
			}

			//左右反転前後表示
			if (button3Count % 2 == 0) {
				g.drawString("左右反転なし", rectX + 130, rectY + rectR + 60);
			}
			if (button3Count % 2 == 1) {
				g.drawString("左右反転あり", rectX + 130, rectY + rectR + 60);
			}

			//上下反転前後表示
			if (button4Count % 2 == 0) {
				g.drawString("上下反転なし", rectX + 280, rectY + rectR + 60);
			}
			if (button4Count % 2 == 1) {
				g.drawString("上下反転あり", rectX + 280, rectY + rectR + 60);
			}

			g.drawString("変化後：", rectX + 20, rectY + rectR + 100);
			if (button2Count > 0 || button3Count > 0 || button4Count > 0) {

				//変化後表示
				for (i = 0; i < text1234StringArray.length; i++) {
					g.drawString(text1234StringArray[i], rectX + 100 + i * 17, rectY + rectR + 100);
				}
				if (!curveString.equals("")) {
					g.drawString(", " + curveString, rectX + 100 + i * 17, rectY + rectR + 100);
				}
			}
		}

	}

	//妥当性チェックの結果を表示する関数
	public static void draw_vali_ng(Graphics g, int rectX, int rectY, int rectR,
			ArrayList<Integer> premiseCondition, ArrayList<Integer> mainCondition) {
		Font font2 = new Font("ＭＳ Ｐゴシック", Font.BOLD, 25);
		g.setFont(font2);
		g.setColor(Color.red);
		int i = 0;

		//前提条件
		for (i = 0; i < premiseCondition.size(); i++) {
			if (premiseCondition.get(i) == 1) {
				g.drawString("NG : 文字列を入力してください.", rectX + rectR + 50, rectY + i * 30);
			}
			if (premiseCondition.get(i) == 2) {
				g.drawString("NG : 文字数は6以上にする必要があります.", rectX + rectR + 50, rectY + i * 30);
			}
			if (premiseCondition.get(i) == 3) {
				g.drawString("NG : 文字の個数は2の倍数にする必要があります.", rectX + rectR + 50, rectY + i * 30);
			}
			if (premiseCondition.get(i) == 4) {
				g.drawString("NG : 右側の引数は1,2,3,4のみです.", rectX + rectR + 50, rectY + i * 30);
			}
			if (premiseCondition.get(i) == 5) {
				g.drawString("NG : 同じ記号が連続しています.", rectX + rectR + 50, rectY + i * 30);
			}
			if (premiseCondition.get(i) == 6) {
				g.drawString("NG : 数字は1,2,3,4の順にする必要があります.", rectX + rectR + 50, rectY + i * 30);
			}
		}

		//本条件
		for (; i < mainCondition.size(); i++) {

			if (mainCondition.get(i) == 1) {
				g.drawString("NG : 端層記号は2つにする必要があります.", rectX + rectR + 50, rectY + i * 30);
			}
			if (mainCondition.get(i) == 2) {
				g.drawString("NG : この表現は対応していません.(頂点)", rectX + rectR + 50, rectY + i * 30);
			}
			if (mainCondition.get(i) == 3) {
				g.drawString("NG : 端点のペアが一意に見つかりません.", rectX + rectR + 50, rectY + i * 30);
			}
			if (mainCondition.get(i) == 4) {
				g.drawString("NG : この表現は対応していません.(異なる傾き)", rectX + rectR + 50, rectY + i * 30);
			}
			if (mainCondition.get(i) == 5) {
				g.drawString("NG : 曲がり方と境界線の傾きが一致しません.", rectX + rectR + 50, rectY + i * 30);
			}
			if (mainCondition.get(i) == 6) {
				g.drawString("NG : 両端間文字列内に同じ記号が存在します.", rectX + rectR + 50, rectY + i * 30);
			}
			if (mainCondition.get(i) == 7) {
				g.drawString("NG : 両端間文字列は互いに逆順である必要があります.", rectX + rectR + 50, rectY + i * 30);
			}
			if (mainCondition.get(i) == 8) {
				g.drawString("NG : t(T)は端にする必要があります.", rectX + rectR + 50, rectY + i * 30);
			}
		}
		g.setColor(Color.black);
	}
	
	//同一性、連続性のエラーの表示
	public static void draw_iden_succ_ng(Graphics g, int rectXR, int rectY, int rectR,
			ArrayList<Integer> premiseConditionL, ArrayList<Integer> mainConditionL,
			ArrayList<Integer> premiseConditionR, ArrayList<Integer> mainConditionR, int[] idenSuccCondition) {
		
	
		Font font2 = new Font("ＭＳ Ｐゴシック", Font.BOLD, 25);
		g.setFont(font2);
		g.setColor(Color.red);
		int i = 0;

		//前提条件を満たさない場合(左側)
		if (premiseConditionL.size() != 0) {
			g.drawString("前提条件 : NG(左側)", rectXR + rectR + 50, rectY + i * 30);
			i++;
		}
		//本条件を満たさない場合(左側)
		else if(mainConditionL.size() != 0) {
			g.drawString("本条件 : NG(左側)", rectXR + rectR + 50, rectY + i * 30);
			i++;
		}
		
		//前提条件を満たさない場合(右側)
		if (premiseConditionR.size() != 0) {
			g.drawString("前提条件 : NG(右側)", rectXR + rectR + 50, rectY + i * 30);
			i++;
		}
		//本条件を満たさない場合(右側)
		else if(mainConditionR.size() != 0) {
			g.drawString("本条件 : NG(右側)", rectXR + rectR + 50, rectY + i * 30);
			i++;
		}
		
		
		//前提条件、本条件を満たす場合
		if(premiseConditionL.size() == 0 && premiseConditionR.size() == 0 &&
				mainConditionL.size() == 0 && mainConditionR.size() == 0) {
			
			if(idenSuccCondition[0] == -1) {
				g.setColor(Color.red);
				g.drawString("同一性 : NG", rectXR + rectR + 50, rectY + i * 30);
				i++;
			}
			if(idenSuccCondition[0] == 1) {
				g.setColor(Color.green);
				g.drawString("同一性 : OK", rectXR + rectR + 50, rectY + i * 30);
				i++;
				
				if(idenSuccCondition[1] == -1) {
					g.setColor(Color.red);
					g.drawString("連続性 : NG(接続辺)", rectXR + rectR + 50, rectY + i * 30);
					i++;
				}
				if(idenSuccCondition[2] == -1) {
					g.setColor(Color.red);
					g.drawString("連続性 : NG(曲がる方向)", rectXR + rectR + 50, rectY + i * 30);
					i++;
				}
				else if(idenSuccCondition[1] == 1 && idenSuccCondition[2] == 1) {
					g.setColor(Color.green);
					g.drawString("連続性 : OK", rectXR + rectR + 50, rectY + i * 30);
					i++;
				}
			}
		}
		g.setColor(Color.black);
	}
	
	//変化後の文字列表示
	public static void draw_succ_string(Graphics g, String text1234String, String curveString,
											int rectX, int rectY, int rectR) {

		Font font1 = new Font("ＭＳ Ｐゴシック", Font.BOLD, 22);
		g.setFont(font1);

		int i = 0;
		String[] text1234StringArray = text1234String.split("");
		
		g.drawString("変化後：", rectX + 20, rectY + rectR + 90);
		//変化後表示
		for (i = 0; i < text1234StringArray.length; i++) {
			g.drawString(text1234StringArray[i], rectX + 100 + i * 17, rectY + rectR + 90);
		}
		
		g.drawString(", " + curveString, rectX + 100 + i * 17, rectY + rectR + 90);
	}
	
	//頂点番号の表示
	public static void draw_vertex_number(Graphics g, String curveString, String curveStringChange,
											int rectX, int rectY, int rectR) {
		
		int vertexNumberType = Iden_succ_func.find_spin_angle(curveString, curveStringChange);
		
		//表示部分
		Font font1 = new Font("ＭＳ Ｐゴシック", Font.BOLD, 60);
		g.setFont(font1);
		
		if(vertexNumberType == 0) {
			g.drawString("1", rectX - 30, rectY - 5);
			g.drawString("2", rectX + rectR + 5, rectY - 5);
			g.drawString("3", rectX + rectR + 5, rectY + rectR + 50);
			g.drawString("4", rectX - 30, rectY + rectR + 50);
		}
		if(vertexNumberType == 1) {
			g.drawString("4", rectX - 30, rectY - 5);
			g.drawString("1", rectX + rectR + 5, rectY - 5);
			g.drawString("2", rectX + rectR + 5, rectY + rectR + 50);
			g.drawString("3", rectX - 30, rectY + rectR + 50);
		}
		if(vertexNumberType == 2) {
			g.drawString("3", rectX - 30, rectY - 5);
			g.drawString("4", rectX + rectR + 5, rectY - 5);
			g.drawString("1", rectX + rectR + 5, rectY + rectR + 50);
			g.drawString("2", rectX - 30, rectY + rectR + 50);
		}
		if(vertexNumberType == 3) {
			g.drawString("2", rectX - 30, rectY - 5);
			g.drawString("3", rectX + rectR + 5, rectY - 5);
			g.drawString("4", rectX + rectR + 5, rectY + rectR + 50);
			g.drawString("1", rectX - 30, rectY + rectR + 50);
		}	
	}
	
	//回転角度の表示
	public static void draw_spin_angle(Graphics g, String curveString, String curveStringChange,
											int rectX, int rectY, int rectR) {
		
		int spinType = Iden_succ_func.find_spin_angle(curveString, curveStringChange);
		
		//表示部分
		Font font1 = new Font("ＭＳ Ｐゴシック", Font.BOLD, 22);
		g.setFont(font1);
		
		if(spinType == 0) {g.drawString("回転なし", rectX+rectR/3, rectY+rectR+50);}
		if(spinType == 1) {g.drawString("90°回転", rectX+rectR/3, rectY+rectR+50);}
		if(spinType == 2) {g.drawString("180°回転", rectX+rectR/3, rectY+rectR+50);}
		if(spinType == 3) {g.drawString("270°回転", rectX+rectR/3, rectY+rectR+50);}
	}
	
	//連続する組と表示している順番を表示
	public static void draw_succ_type(Graphics g, int ArraySize, int index,
										int rectX, int rectY, int rectR) {
		
		String arraySizeString = String.valueOf(ArraySize);
		String indexString = String.valueOf(index);
		
		Font font2 = new Font("ＭＳ Ｐゴシック", Font.BOLD, 25);
		g.setFont(font2);
		g.setColor(Color.black);

		g.drawString(indexString + "/" + arraySizeString + "表示中", rectX + rectR + 50, rectY + 3 * 30);
		
	}
	
	//地層名描画
	public static void draw_strata_name_2(Graphics g, ArrayList<ArrayList<Double>> letterPoint,
			ArrayList<String> letter, int fontSize) {

		double letterPointX = 0;
		double letterPointY = 0;

		Font font3 = new Font("ＭＳ Ｐゴシック", Font.BOLD, fontSize);
		g.setFont(font3);
		for (int i = 0; i < letterPoint.size(); i++) {

			letterPointX = letterPoint.get(i).get(0);
			letterPointY = letterPoint.get(i).get(1);

			if (letter.get(i).equals("t") || letter.get(i).equals("T")) {

				g.setColor(Color.blue);
				g.drawString(letter.get(i), (int) letterPointX, (int) letterPointY);
				g.setColor(Color.black);
			}

			else {
				g.drawString(letter.get(i), (int) letterPointX, (int) letterPointY);
			}
		}

		//letter(地層名の座標など表示)
		/*System.out.println("-letter-");
		for(int i = 0; i <  letter.size(); i++){
			System.out.println(letter.get(i) + ": (" + letterPoint.get(i).get(0) + "," +letterPoint.get(i).get(1) + ")");
		}*/
	}
	
	//直線を描画する関数
	public static void draw_straight_line_2(Graphics g,	ArrayList<ArrayList<Double>> point) {
		for (int n = 0; n < point.size()-1; n+=2) {

			double startX = point.get(n).get(0); //片方のx座標
			double startY = point.get(n).get(1); //片方のy座標
			double endX = point.get(n+1).get(0); //もう片方のx座標
			double endY = point.get(n+1).get(1); //もう片方のy座標

			g.drawLine((int) startX, (int) startY, (int) endX, (int) endY);
		}
	}
	
	//入力を描画する関数
	public static void draw_string(Graphics g, String text, String heightRel, int rectX, int rectY, int rectR, int fontSize) {

		if(text != null) {
			
			String[] textArray = text.split("->");
			
			double drawPointX = rectX + rectR + 100;
			double drawPointY = rectY;
		
			Font font3 = new Font("ＭＳ Ｐゴシック", Font.BOLD, fontSize);
			g.setFont(font3);
			g.setColor(Color.black);
			
			g.drawString("記号列", (int) drawPointX, (int) drawPointY);
			
			drawPointY += fontSize;
			
			//文字列描画
			g.drawString(textArray[0], (int) drawPointX, (int) drawPointY);
			drawPointY += fontSize;
			
			for (int i = 1; i < textArray.length; i++, drawPointY += fontSize) {
				
				g.drawString("->", (int) drawPointX, (int) drawPointY);
				
				//文字列描画
				g.drawString(textArray[i], (int) drawPointX + fontSize, (int) drawPointY);
			}
			
			drawPointY+=fontSize;
			g.drawString("堆積順",  (int) drawPointX, (int) drawPointY);
			drawPointY+=fontSize;
			g.drawString(heightRel, (int) drawPointX, (int) drawPointY);
			
		}
	}
}

//-----------------------------------描画時の文字列関連の関数をまとめたクラス--------------------------------------------
class String_func {
	//pair, point, controlをまとめて求める関数
	public static void process_find_pair_point_control(String text1234String, String curveString, int rectX, int rectY,
			int rectR,
			ArrayList<ArrayList<Integer>> pair, ArrayList<ArrayList<Double>> point,
			ArrayList<ArrayList<Double>> control) {

		ArrayList<ArrayList<Integer>> pairCopy = new ArrayList<>(find_pair(text1234String));
		ArrayList<ArrayList<Double>> pointCopy = new ArrayList<>(find_point(text1234String, rectX, rectY, rectR));
		ArrayList<ArrayList<Double>> controlCopy = new ArrayList<>();
		
		pointCopy = change_hori_vert(text1234String, curveString, rectX, rectY, rectR);

		if (!curveString.equals("")) {
			controlCopy = new ArrayList<>(find_control_point(text1234String, curveString, rectX, rectY, rectR));
		}

		//新しい値を元の引数にコピー
		pair.clear();
		pair.addAll(pairCopy);

		point.clear();
		point.addAll(pointCopy);

		control.clear();
		control.addAll(controlCopy);
	}

	//text1234をtext12,text23,text34,text41に分ける関数([1a2b3c4b] -> [a],[b],[c],[b])
	public static void find_cut_string(String text1234String,
			ArrayList<String> text12, ArrayList<String> text23,
			ArrayList<String> text34, ArrayList<String> text41) {

		//1,2,3,4を区切り文字として区切って、配列に入れる
		String[] text1234 = text1234String.split("[1234]");

		//リストを空にする(初期化的な)
		text12.clear();
		text23.clear();
		text34.clear();
		text41.clear();

		//区切った配列が空でない場合、1文字ずつ加える
		if (text1234.length > 1 && !text1234[1].isEmpty()) {
			text12.addAll(Arrays.asList(text1234[1].split("")));
		}
		if (text1234.length > 2 && !text1234[2].isEmpty()) {
			text23.addAll(Arrays.asList(text1234[2].split("")));
		}
		if (text1234.length > 3 && !text1234[3].isEmpty()) {
			text34.addAll(Arrays.asList(text1234[3].split("")));
		}
		if (text1234.length > 4 && !text1234[4].isEmpty()) {
			text41.addAll(Arrays.asList(text1234[4].split("")));
		}

		//確認用
		/*System.out.println("-text12-");
		System.out.println(text12 + ", size:" + text12.size());
		System.out.println("-text23-");
		System.out.println(text23 + ", size:" + text23.size());
		System.out.println("-text34-");
		System.out.println(text34 + ", size:" + text34.size());
		System.out.println("-text41-");
		System.out.println(text41 + ", size:" + text41.size());*/
	}

	//数字を除いた配列を求める関数([1,a,2,b,3,c,4,b] -> [a,b,c,b])
	public static String[] find_symbol_only(String text1234String) {

		String[] text1234StringArray = text1234String.split("");

		String[] text1234SymbolOnly = new String[text1234StringArray.length - 4];

		int index = 0;

		//拡張forループ
		for (String element : text1234StringArray) {

			//1-9でない時(不整合面対応のため1-4を1-9に変更)
			if (!element.matches("[1-9]")) {
				text1234SymbolOnly[index] = element;
				index++;
			}
		}

		//確認用
		//System.out.println("-text1234SymbolOnly-");
		//System.out.println(Arrays.toString(text1234SymbolOnly));

		return text1234SymbolOnly;
	}

	//各端点の変化を求める関数([a,b,c,b] -> [[b,a],[a,b],[b,c],[c,b]])
	public static String[][] find_gap(String text1234String) {

		//関数呼び出し
		String[] text1234SymbolOnly = find_symbol_only(text1234String);

		//処理ここから
		//層の変化を格納する配列
		String[][] gap = new String[text1234SymbolOnly.length][2];

		//gap[0]だけ特殊
		gap[0][0] = text1234SymbolOnly[text1234SymbolOnly.length - 1];
		gap[0][1] = text1234SymbolOnly[0];

		//gap[1]からの処理
		for (int i = 1; i < text1234SymbolOnly.length; i++) {
			gap[i][0] = text1234SymbolOnly[i - 1];
			gap[i][1] = text1234SymbolOnly[i];
		}

		//確認用
		/*System.out.println("-gap-");
		System.out.println(Arrays.deepToString(gap));*/

		return gap;
	}

	//1回だけ出現する記号の位置を求める関数([a,b,c,b] -> [0,2])
	public static void find_only_one_symbol_point(String text1234String, ArrayList<String> onlyOneSymbol,
			ArrayList<Integer> onlyOneSymbolPoint) {

		//関数呼び出し
		String[] text1234SymbolOnly = find_symbol_only(text1234String);

		//処理ここから
		for (int i = 0; i < text1234SymbolOnly.length; i++) {
			int count = 0;

			//配列全体を走査して要素の出現回数を数える
			for (int j = 0; j < text1234SymbolOnly.length; j++) {
				if (text1234SymbolOnly[i].equals(text1234SymbolOnly[j])) {
					count++;
				}
			}

			//出現回数が1回の要素のみを結果配列に追加
			if (count == 1) {
				onlyOneSymbol.add(text1234SymbolOnly[i]);
				onlyOneSymbolPoint.add(i);
			}
		}

		//確認用
		/*System.out.println("-onlyOneSymbol-");
		System.out.println(onlyOneSymbol);
		System.out.println("-onlyOneSymbolPoint-");
		System.out.println(onlyOneSymbolPoint);*/
	}

	//端点のペアを求める関数
	public static ArrayList<ArrayList<Integer>> find_pair(String text1234String) {

		//関数呼び出し
		String[] text1234SymbolOnly = find_symbol_only(text1234String);
		String[][] gap = find_gap(text1234String);

		//処理ここから
		ArrayList<ArrayList<Integer>> pair = new ArrayList<>();

		int pairCount = 0;

		for (int i = 0; i < text1234SymbolOnly.length; i++) {

			for (int r = i + 1; r < text1234SymbolOnly.length; r++) {

				if (gap[i][0].equals(gap[r][1]) && gap[i][1].equals(gap[r][0])) {

					//予想されるペアの数と実際のペアの数を比較
					if ((text1234SymbolOnly.length / 2) > pairCount) {
						pair.add(new ArrayList<Integer>(Arrays.asList(i, r)));
					}
					pairCount++;
				}
			}
		}
		//確認用
		//System.out.println("-pair-");
		//System.out.println(pair);

		return pair;
	}

	//各端点の座標を求める関数
	public static ArrayList<ArrayList<Double>> find_point(String text1234String, int rectX, int rectY, int rectR) {

		//関数呼び出し
		ArrayList<String> text12 = new ArrayList<>();
		ArrayList<String> text23 = new ArrayList<>();
		ArrayList<String> text34 = new ArrayList<>();
		ArrayList<String> text41 = new ArrayList<>();

		find_cut_string(text1234String, text12, text23, text34, text41);

		//処理ここから
		ArrayList<ArrayList<Double>> point = new ArrayList<>();

		int i = 0;
		double pointX = 0;
		double pointY = 0;

		//各pointの座標を計算
		if (text12.size() > 0) {
			for (i = 1; i <= text12.size(); i++) {

				//roundで小数点以下四捨五入
				pointX = Math.round(rectX + rectR * (i / (double) (text12.size() + 1)));
				pointY = rectY;
				point.add(new ArrayList<Double>(Arrays.asList(pointX, pointY)));
			}
		}

		if (text23.size() > 0) {
			for (i = 1; i <= text23.size(); i++) {
				pointX = rectX + rectR;
				pointY = Math.round(rectY + rectR * (i / (double) (text23.size() + 1)));
				point.add(new ArrayList<Double>(Arrays.asList(pointX, pointY)));
			}
		}

		if (text34.size() > 0) {
			for (i = 1; i <= text34.size(); i++) {
				pointX = Math.round(rectX + rectR - rectR * (i / (double) (text34.size() + 1)));
				pointY = rectY + rectR;
				point.add(new ArrayList<Double>(Arrays.asList(pointX, pointY)));
			}
		}

		if (text41.size() > 0) {
			for (i = 1; i <= text41.size(); i++) {
				pointX = rectX;
				pointY = Math.round(rectY + rectR - rectR * (i / (double) (text41.size() + 1)));
				point.add(new ArrayList<Double>(Arrays.asList(pointX, pointY)));
			}
		}

		//確認用
		//System.out.println("-point-");
		//System.out.println(point);

		return point;
	}

	//変曲点の座標を計算する関数
	public static ArrayList<ArrayList<Double>> find_control_point(String text1234String, String curveString,
			int rectX, int rectY, int rectR) {

		//関数呼び出し
		ArrayList<ArrayList<Integer>> pair = new ArrayList<>(find_pair(text1234String));
		ArrayList<ArrayList<Double>> point = new ArrayList<>(find_point(text1234String, rectX, rectY, rectR));

		//処理ここから
		ArrayList<ArrayList<Double>> controlPoint = new ArrayList<>();

		for (int n = 0; n < pair.size(); n++) {

			int pair0 = pair.get(n).get(0); //ペアの片方
			int pair1 = pair.get(n).get(1); //ペアのもう片方

			double startX = point.get(pair0).get(0); //片方のx座標
			double startY = point.get(pair0).get(1); //片方のy座標
			double endX = point.get(pair1).get(0); //もう片方のx座標
			double endY = point.get(pair1).get(1); //もう片方のy座標

			double controlX = 0; //変曲点のx座標
			double controlY = 0; //変曲点のy座標

			//controlX(1,4)
			if (curveString.equals("1") || curveString.equals("4")) {

				//大きい方
				if (startX >= endX) {
					controlX = startX;
				} else {
					controlX = endX;
				}
			}
			//controlX(2,3)
			if (curveString.equals("2") || curveString.equals("3")) {

				//小さい方
				if (startX >= endX) {
					controlX = endX;
				} else {
					controlX = startX;
				}
			}

			//controlY(1,2)
			if (curveString.equals("1") || curveString.equals("2")) {

				//大きい方
				if (startY >= endY) {
					controlY = startY;
				} else {
					controlY = endY;
				}
			}

			//controlY(3,4)
			if (curveString.equals("3") || curveString.equals("4")) {

				//小さい方
				if (startY >= endY) {
					controlY = endY;
				} else {
					controlY = startY;
				}
			}

			controlPoint.add(new ArrayList<Double>(Arrays.asList(controlX, controlY)));

		}

		//確認用
		//System.out.println("-controlPoint-");
		//System.out.println(controlPoint);

		return controlPoint;
	}

	//辺に対し平行な直線がある時の処理を行う関数(1,2,3,4いずれか)
	public static ArrayList<ArrayList<Double>> change_hori_vert(String text1234String, String curveString, int rectX,
			int rectY, int rectR) {

		//関数呼び出し
		ArrayList<String> text12 = new ArrayList<>();
		ArrayList<String> text23 = new ArrayList<>();
		ArrayList<String> text34 = new ArrayList<>();
		ArrayList<String> text41 = new ArrayList<>();

		find_cut_string(text1234String, text12, text23, text34, text41);

		ArrayList<ArrayList<Double>> point = new ArrayList<>(find_point(text1234String, rectX, rectY, rectR));
		ArrayList<ArrayList<Integer>> pair = new ArrayList<>(find_pair(text1234String));

		//処理ここから
		ArrayList<ArrayList<Double>> pointCopy = new ArrayList<>();
		ArrayList<ArrayList<Double>> pointCopy1 = new ArrayList<>();
		ArrayList<ArrayList<Double>> pointCopy2 = new ArrayList<>();

		double x1 = 0;
		double x2 = 0;
		double y1 = 0;
		double y2 = 0;

		double x1Copy = 0;
		double x2Copy = 0;
		double y1Copy = 0;
		double y2Copy = 0;

		int text12Size = text12.size();
		int text23Size = text23.size();
		int text34Size = text34.size();
		int text41Size = text41.size();
		
		//辺と平行か記録する(フラグ)
		int parallel = -1;

		//辺に対し平行な直線がある時の処理(1,2,3,4いずれか)
		for (int i = 0; i < pair.size(); i++) {

			x1 = point.get(pair.get(i).get(0)).get(0); //point[pair[i][0]][0]
			x2 = point.get(pair.get(i).get(1)).get(0); //point[pair[i][1]][0]
			y1 = point.get(pair.get(i).get(0)).get(1); //point[pair[i][0]][1]
			y2 = point.get(pair.get(i).get(1)).get(1); //point[pair[i][1]][1]

			//x座標が同じ時(縦棒)
			if (x1 == x2) {
				parallel = 1;
				
				x1Copy = x1;
				x2Copy = x2;
				y1Copy = y1;
				y2Copy = y2;

				// 「|」を「／」にする
				if (curveString.equals("1") || curveString.equals("3")) {

					//y座標の大小比較
					if (y1 <= y2) {
						x1Copy += rectR / (double) (text12Size + 1) / 2;
						x2Copy -= rectR / (double) (text34Size + 1) / 2;
					}

					else if (y1 > y2) {
						x1Copy -= rectR / (double) (text34Size + 1) / 2;
						x2Copy += rectR / (double) (text12Size + 1) / 2;
					}
				}

				// 「|」を「＼」にする
				else if (curveString.equals("2") || curveString.equals("4")) {

					if (y1 <= y2) {
						x1Copy -= rectR / (double) (text12Size + 1) / 2;
						x2Copy += rectR / (double) (text34Size + 1) / 2;
					}

					else if (y1 > y2) {
						x1Copy += rectR / (double) (text34Size + 1) / 2;
						x2Copy -= rectR / (double) (text12Size + 1) / 2;
					}
				}
			}

			//y座標が同じ時(横棒)
			//roundで小数点以下を四捨五入
			if (y1 == y2) {
				parallel = 1;

				x1Copy = x1;
				x2Copy = x2;
				y1Copy = y1;
				y2Copy = y2;

				//「―」を「／」にする
				if (curveString.equals("1") || curveString.equals("3")) {

					//x座標の大小比較
					if (x1 <= x2) {
						y1Copy += rectR / (double) (text23Size + 1) / 2;
						y2Copy -= rectR / (double) (text41Size + 1) / 2;
					}

					else if (x1 > x2) {
						y1Copy -= rectR / (double) (text41Size + 1) / 2;
						y2Copy += rectR / (double) (text23Size + 1) / 2;
					}
				}

				//「―」を「＼」にする
				else if (curveString.equals("2") || curveString.equals("4")) {

					if (x1 <= x2) {
						y1Copy -= rectR / (double) (text23Size + 1) / 2;
						y2Copy += rectR / (double) (text41Size + 1) / 2;
					}

					else if (x1 > x2) {
						y1Copy += rectR / (double) (text41Size + 1) / 2;
						y2Copy -= rectR / (double) (text23Size + 1) / 2;
					}
				}
			}

			if (parallel == 1) {
				pointCopy1.add(new ArrayList<Double>(Arrays.asList(x1Copy, y1Copy)));
				pointCopy2.add(new ArrayList<Double>(Arrays.asList(x2Copy, y2Copy)));
			}
		}

		Collections.reverse(pointCopy2);
		pointCopy.addAll(pointCopy1);
		pointCopy.addAll(pointCopy2);

		//確認用
		/*System.out.println("-pointCopy1-");
		System.out.println(pointCopy1);
		System.out.println("-pointCopy2-");
		System.out.println(pointCopy2);
		System.out.println("-pointChange-");
		System.out.println(pointCopy);*/

		if (!curveString.equals("") && parallel == 1) {
			return pointCopy;
		} else {
			return point;
		}
	}

	//曲線(ベジェ曲線)の座標計算
	public static double find_bezier_curve_point(double parT, double start, double end, double control) {
		double bezierCurvePoint = 0;

		//二次ベジェ曲線の式
		bezierCurvePoint = Math.pow((1 - parT), 2) * start + 2 * (1 - parT) * parT * control
				+ Math.pow(parT, 2) * end;

		return bezierCurvePoint;
	}

	//文字の座標を計算する関数
	public static void find_letter_point(String text1234String, String curveString, int rectX, int rectY, int rectR,
			ArrayList<String> letter, ArrayList<ArrayList<Double>> letterPoint, int fontSize) {

		//関数呼び出し
		String[] text1234SymbolOnly = find_symbol_only(text1234String);
		String[][] gap = find_gap(text1234String);
		ArrayList<ArrayList<Integer>> pair = new ArrayList<>(find_pair(text1234String));
		ArrayList<ArrayList<Double>> point = new ArrayList<>(find_point(text1234String, rectX, rectY, rectR));
		ArrayList<ArrayList<Double>> control = new ArrayList<>(
				find_control_point(text1234String, curveString, rectX, rectY, rectR));
		ArrayList<String> onlyOneSymbol = new ArrayList<>();
		ArrayList<Integer> onlyOneSymbolPoint = new ArrayList<>();

		find_only_one_symbol_point(text1234String, onlyOneSymbol, onlyOneSymbolPoint);

		//処理ここから
		String[] text1234StringArray = text1234String.split("");
		
		//角のタイプ格納(確認用)
		ArrayList<String> cornerType = new ArrayList<>();

		//letter = new ArrayList<>();
		//letterPoint =  new ArrayList<>(); 

		int i = 0;
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		int e = 0;

		double pointXAve = 0;
		double pointYAve = 0;

		double pointConXAve = 0;
		double pointConYAve = 0;

		//ベジェ曲線のパラメータt
		double parT = 0.5;

		double pointX1 = 0;
		double pointX2 = 0;
		double pointY1 = 0;
		double pointY2 = 0;

		//2直線に囲まれた部分(角でない部分)
		for (i = 0; i < text1234SymbolOnly.length; i++) {

			for (a = 0; a < text1234SymbolOnly.length; a++) {

				for (b = 0; b < text1234SymbolOnly.length; b++) {

					for (c = 0; c < text1234SymbolOnly.length; c++) {

						for (d = 0; d < text1234SymbolOnly.length; d++) {
							//2直線(4点)に囲まれた部分
							if (text1234SymbolOnly[i].equals(gap[a][0]) && text1234SymbolOnly[i].equals(gap[b][0])
									&& text1234SymbolOnly[i].equals(gap[c][1])
									&& text1234SymbolOnly[i].equals(gap[d][1]) && a != b && c != d) {

								//文字列検索(一致するものがない時)
								if (!letter.contains(text1234SymbolOnly[i])) {

									//直線の場合
									pointConXAve = (point.get(a).get(0) + point.get(b).get(0) + point.get(c).get(0)
											+ point.get(d).get(0)) / 4;
									pointConYAve = (point.get(a).get(1) + point.get(b).get(1) + point.get(c).get(1)
											+ point.get(d).get(1)) / 4;

									//曲線の場合
									if (!curveString.equals("")) {

										for (e = 0; e < pair.size(); e++) {
											if ((pair.get(e).get(0).equals(a) && pair.get(e).get(1).equals(c)) ||
													(pair.get(e).get(1).equals(a) && pair.get(e).get(0).equals(c))) {

												pointX1 = find_bezier_curve_point(parT, point.get(a).get(0),
														point.get(c).get(0), control.get(e).get(0));
												pointY1 = find_bezier_curve_point(parT, point.get(a).get(1),
														point.get(c).get(1), control.get(e).get(1));
											}

											else if ((pair.get(e).get(0).equals(a) && pair.get(e).get(1).equals(d)) ||
													(pair.get(e).get(1).equals(a) && pair.get(e).get(0).equals(d))) {

												pointX1 = find_bezier_curve_point(parT, point.get(a).get(0),
														point.get(d).get(0), control.get(e).get(0));
												pointY1 = find_bezier_curve_point(parT, point.get(a).get(1),
														point.get(d).get(1), control.get(e).get(1));
											}

											if ((pair.get(e).get(0).equals(b) && pair.get(e).get(1).equals(c)) ||
													(pair.get(e).get(1).equals(b) && pair.get(e).get(0).equals(c))) {

												pointX2 = find_bezier_curve_point(parT, point.get(b).get(0),
														point.get(c).get(0), control.get(e).get(0));
												pointY2 = find_bezier_curve_point(parT, point.get(b).get(1),
														point.get(c).get(1), control.get(e).get(1));
											}

											else if ((pair.get(e).get(0).equals(b) && pair.get(e).get(1).equals(d)) ||
													(pair.get(e).get(1).equals(b) && pair.get(e).get(0).equals(d))) {

												pointX2 = find_bezier_curve_point(parT, point.get(b).get(0),
														point.get(d).get(0), control.get(e).get(0));
												pointY2 = find_bezier_curve_point(parT, point.get(b).get(1),
														point.get(d).get(1), control.get(e).get(1));
											}
										}

										pointConXAve = (pointX1 + pointX2) / 2;
										pointConYAve = (pointY1 + pointY2) / 2;
									}

									//微調整(文字の大きさで代わるはず)
									pointConXAve -= fontSize/3;
									pointConYAve += fontSize/3;

									letterPoint.add(new ArrayList<Double>(Arrays.asList(pointConXAve, pointConYAve)));
									letter.add(text1234SymbolOnly[i]);
								}
							}
						}
					}
				}
			}
		}

		//角の処理
		for (a = 0; a < onlyOneSymbol.size(); a++) {

			for (b = 0; b < text1234SymbolOnly.length; b++) {

				for (c = 0; c < text1234SymbolOnly.length; c++) {

					if (onlyOneSymbol.get(a).equals(gap[b][0]) && onlyOneSymbol.get(a).equals(gap[c][1]) && b != c) {

						//文字列検索(一致するものがない時)
						if (!letter.contains(onlyOneSymbol.get(a))) {

							for (d = 0; d < text1234StringArray.length; d++) {

								// [1つしかない記号 数字 記号]の時、1つしかない記号が端になる
								if (text1234StringArray[d].equals(onlyOneSymbol.get(a))) {

									//直線の時
									if (curveString.equals("")) {
										pointXAve = (point.get(b).get(0) + point.get(c).get(0)) / 2;
										pointYAve = (point.get(b).get(1) + point.get(c).get(1)) / 2;
									}

									//曲線の場合
									else {

										for (e = 0; e < pair.size(); e++) {
											if (pair.get(e).get(0).equals(b) && pair.get(e).get(1).equals(c) ||
													pair.get(e).get(1).equals(b) && pair.get(e).get(0).equals(c)) {
												pointXAve = find_bezier_curve_point(parT, point.get(b).get(0),
														point.get(c).get(0), control.get(e).get(0));
												pointYAve = find_bezier_curve_point(parT, point.get(b).get(1),
														point.get(c).get(1), control.get(e).get(1));
											}
										}
									}

									//角を含む時
									//d(1回しか出現しない記号)が配列の最後でない場合
									if (d < text1234StringArray.length - 1) {
										//角2を確実に含む時
										if (text1234StringArray[d + 1].equals("2")) {

											//3つの角を含む時
											if (text1234StringArray[d + 2].equals("3")
													&& text1234StringArray[d + 3].equals("4")) {
												pointXAve = (pointXAve + (rectX * 3 + rectR * 2) / 3) / 2;
												pointYAve = (pointYAve + (rectY * 3 + rectR * 2) / 3) / 2;
												cornerType.add("2-3");
											}

											//2つの角を含む時
											else if (text1234StringArray[d + 2].equals("3")) {
												pointXAve = (pointXAve + (rectX * 2 + rectR * 2) / 2) / 2;
												pointYAve = (pointYAve + (rectY * 2 + rectR) / 2) / 2;
												cornerType.add("2-2");
											}

											//1つの角を含む時
											else {
												pointXAve = (pointXAve + rectX + rectR) / 2;
												pointYAve = (pointYAve + rectY) / 2;
												cornerType.add("2-1");
											}

										}

										//角3を確実に含む時
										else if (text1234StringArray[d + 1].equals("3")) {

											//3つの角を含む時
											if (text1234StringArray[d + 2].equals("4")
													&& d + 2 == text1234StringArray.length - 1) {
												pointXAve = (pointXAve + (rectX * 3 + rectR) / 3) / 2;
												pointYAve = (pointYAve + (rectY * 3 + rectR * 2) / 3) / 2;
												cornerType.add("3-3");
											}

											//2つの角を含む時
											else if (text1234StringArray[d + 2].equals("4")) {
												pointXAve = (pointXAve + (rectX * 2 + rectR) / 2) / 2;
												pointYAve = (pointYAve + rectY + rectR) / 2;
												cornerType.add("3-2");
											}

											//1つの角を含む時
											else {
												pointXAve = (pointXAve + rectX + rectR) / 2;
												pointYAve = (pointYAve + rectY + rectR) / 2;
												cornerType.add("3-1");
											}
										}

										//角4を確実に含む時
										else if (text1234StringArray[d + 1].equals("4")) {

											//3つの角を含む時
											if (d + 1 == text1234StringArray.length - 1
													&& text1234StringArray[0].equals("1")
													&& text1234StringArray[1].equals("2")) {
												pointXAve = (pointXAve + (rectX * 3 + rectR) / 3) / 2;
												pointYAve = (pointYAve + (rectY * 3 + rectR) / 3) / 2;
												cornerType.add("4-3");
											}

											//2つの角を含む時
											else if (d + 1 == text1234StringArray.length - 1
													&& text1234StringArray[0].equals("1")) {
												pointXAve = (pointXAve + (rectX * 2) / 2) / 2;
												pointYAve = (pointYAve + (rectY * 2 + rectR) / 2) / 2;
												cornerType.add("4-2");
											}

											//1つの角を含む時
											else if (d + 1 != text1234StringArray.length - 1) {
												pointXAve = (pointXAve + rectX) / 2;
												pointYAve = (pointYAve + rectY + rectR) / 2;
												cornerType.add("4-1");
											}
										}
									}

									//d(1回しか出現しない記号)が配列の最後の場合
									else {

										//1の角を確実に含む時
										if (d == text1234StringArray.length - 1) {

											//3つの角を含む時
											if (text1234StringArray[1].equals("2")
													&& text1234StringArray[2].equals("3")) {
												pointXAve = (pointXAve + (rectX * 3 + rectR * 2) / 3) / 2;
												pointYAve = (pointYAve + (rectY * 3 + rectR) / 3) / 2;
												cornerType.add("1-3");
											}

											//2つの角を含む時
											else if (text1234StringArray[1].equals("2")) {
												pointXAve = (pointXAve + (rectX * 2 + rectR) / 2) / 2;
												pointYAve = (pointYAve + (rectY * 2) / 2) / 2;
												cornerType.add("1-2");
											}

											//1つの角を含む時
											else if (!text1234StringArray[1].equals("2")) {
												pointXAve = (pointXAve + rectX) / 2;
												pointYAve = (pointYAve + rectY) / 2;
												cornerType.add("1-1");
											}
										}

									}

									//微調整(文字の大きさで代わるはず)
									pointXAve -= 15;
									pointYAve += 15;

									letterPoint.add(new ArrayList<Double>(Arrays.asList(pointXAve, pointYAve)));
									letter.add(text1234StringArray[d]);
								}
							}
						}
					}
				}
			}
		}

		//確認用(地層名の座標など表示)
		/*System.out.println("-letter-");
		for(i = 0; i <  letter.size(); i++){
			System.out.println(letter.get(i) + ": (" + letterPoint.get(i).get(0) + "," +letterPoint.get(i).get(1) + ")");
		}*/
		
		//System.out.println("-cornerType-");
		//System.out.println(cornerType);
	}
	
	//-------------------不整合面を含む表現用の関数-----------------------
	//処理をまとめた関数(仮)
	public static void process_find_pair_point_2(String text, String heightRel, ArrayList<ArrayList<Double>> cornerPoint,
													ArrayList<ArrayList<Double>> point, ArrayList<ArrayList<Double>> letterPoint) {
		
		ArrayList<ArrayList<String>> cutText = new ArrayList<>();
		ArrayList<ArrayList<Double>> cornerPointCopy = new ArrayList<>();
		ArrayList<ArrayList<Double>> pointCopy = new ArrayList<>();
		ArrayList<ArrayList<Double>> letterPointCopy = new ArrayList<>();
		
		ArrayList<ArrayList<Double>> allPoint = new ArrayList<>();
		ArrayList<Integer> pair = new ArrayList<>();
		
		ArrayList<Double> row1 = new ArrayList<>();//二次元動的配列に値代入用1
		ArrayList<Double> row2 = new ArrayList<>();//二次元動的配列に値代入用2
		
		//point = new ArrayList<>(); //後で消すかも
		//letterPoint = new ArrayList<>(); //後で消すかも
		
		//[1a2b3c4b->12b3c4] -> [[1a2b3c4b],[12b3c4]]
		cutText = find_cut_string_2(text);
		
		//[abc] -> [a,b,c]
		String[] heightRelArray = heightRel.split("");
		
		cornerPointCopy.addAll(cornerPoint);
		
		double[] topPoint = new double[4];
		
		int i = 0;
		int t = 0;
		int p = 0;
		
		for(i = 0;i < cutText.size();i++) {
			//一番上の端点を求めて、pointに追加
			System.out.println(i +"番目の文字列");
			if(i < cutText.size()-1) {
			
				topPoint = find_top_point(cutText.get(i), cornerPointCopy, heightRelArray[i]);
				row1 = new ArrayList<>();
				row2 = new ArrayList<>();
				
				//pointに追加する処理
				row1.add(topPoint[0]); row1.add(topPoint[1]);
				row2.add(topPoint[2]); row2.add(topPoint[3]);
				pointCopy.add(row1);  pointCopy.add(row2);
				
				//地層名の座標計算				
				row1 = new ArrayList<>();
				row1 = find_letter_point_2(cutText.get(i), cornerPointCopy, heightRelArray[i]);
				letterPointCopy.add(row1);
				
				//頂点の座標更新
				cornerPointCopy = upd_corner_point(cutText.get(i), cornerPointCopy, heightRelArray[i]);
			}
			
			//最後の文字列はペアが全て存在するので、別の処理(仮)
			else{
				allPoint = find_point_2(cutText.get(i), cornerPointCopy);
				pair = find_pair_3(cutText.get(i));
				
				for(t = 0; t < pair.size(); t++) {
					row1 = new ArrayList<>();
					
					//pointに追加する処理
					row1.add(allPoint.get(pair.get(t)).get(0));
					row1.add(allPoint.get(pair.get(t)).get(1));
					pointCopy.add(row1);
				}
				
				//地層名の座標計算
				for(p = i; p < heightRelArray.length; p++) {
					row1 = new ArrayList<>();
					row1 = find_letter_point_3(cutText.get(i), cornerPointCopy, heightRelArray[p]);
					letterPointCopy.add(row1);
				}
			}
		}
		

		
		//確認用
		System.out.println("-process_find_pair_point_2内ここから-");
		System.out.println("-cutText-");
		System.out.println(cutText);
		System.out.println("-heightRelArray-");
		System.out.println(Arrays.toString(heightRelArray));
		System.out.println("-pointCopy-");
		System.out.println(pointCopy);
		System.out.println("-cornerPoint-");
		System.out.println(cornerPoint);
		System.out.println("-cornerPointCopy-");
		System.out.println(cornerPointCopy);
		System.out.println("-letterPointCopy-");
		System.out.println(letterPointCopy);
		System.out.println("-process_find_pair_point_2内ここまで-");
		
		//新しい値を元の引数にコピー
		point.clear();
		point.addAll(pointCopy);
		letterPoint.clear();
		letterPoint.addAll(letterPointCopy);

	}
	
	//textを分ける関数([1a2b3c4b->12b3c4] -> [[1a2b3c4b],[12b3c4]])
	public static ArrayList<ArrayList<String>> find_cut_string_2(String textString) {
		
		ArrayList<ArrayList<String>> cutText = new ArrayList<>();
		
		String[] textStringArray = textString.split("->");
		
		//"->"で区切った文字列(textStringArray)をcutTextの要素に追加
		for(int i = 0; i < textStringArray.length; i++) {
			cutText.add(new ArrayList<>(Arrays.asList(textStringArray[i])));
		}
		
		//確認用
		//System.out.println("-cut_string_2-");
		//System.out.println(cutText);
		
		return cutText;
	}
	
	//一番上の境界線の端点の座標を求める関数
	public static double[] find_top_point(ArrayList<String> cutText, ArrayList<ArrayList<Double>> cornerPoint, 
										String topString) {
		
		//境界線の端点を入れる配列(x座標1,y座標1,x座標2,y座標2)
		double[] topPoint = new double[4];
		
		//System.out.println("-find_top_point:start-");
		
		//point:各端点の座標[[x座標,y座標],[x座標,y座標],...]
		ArrayList<ArrayList<Double>> point = new ArrayList<>();
		ArrayList<Integer> pair = new ArrayList<>();

		point = find_point_2(cutText, cornerPoint);
		pair = find_pair_2(cutText, topString);
		
		topPoint[0] = point.get(pair.get(0)).get(0);
		topPoint[1] = point.get(pair.get(0)).get(1);
		topPoint[2] = point.get(pair.get(1)).get(0);
		topPoint[3] = point.get(pair.get(1)).get(1);
		
		//確認用
		//System.out.println(pair);
		//System.out.println(point);
		//System.out.println("-cutText-");
		//System.out.println(cutText);
		//System.out.println("-cornerPoint-");
		//System.out.println(cornerPoint);
		
		//System.out.println("-find_top_point:end-");
		
		return topPoint;
		
	}
	
	//任意の層記号のペアを探す関数(正しく動作すれば要素数は2だが、動的配列にすることで妥当性判定も行える)
	//gapのi番目とr番目がペア
	public static ArrayList<Integer> find_pair_2(ArrayList<String> cutText, String topString) {

		//System.out.println("-find_pair_2:start-");
		//関数呼び出し
		String[] SymbolOnly = null;
		String[][] gap = null;
		
		SymbolOnly = find_symbol_only_2(cutText);
		gap = find_gap_2(cutText);
		
		//関数の引数がString型なので変換(?)している
		/*for (String cutText2 : cutText) {
			gap = find_gap(cutText2);
		}*/
		System.out.println("-SymbolOnly-");
		System.out.println(Arrays.toString(SymbolOnly));
		System.out.println("-gap-");
		System.out.println(Arrays.deepToString(gap));


		//処理ここから
		ArrayList<Integer> pair = new ArrayList<>();

		for (int i = 0; i < gap.length; i++) {

			for (int r = i + 1; r < gap.length; r++) {

				if ((gap[i][0].equals(topString) && gap[r][1].equals(topString)) 
						|| (gap[i][1].equals(topString) && gap[r][0].equals(topString))) {

						pair.add(i);
						pair.add(r);
				}
			}
			
			//gapの最後とgapの最初がペアになる場合
			if(i == gap.length-1) {
				
				if (gap[i][1].equals(topString) && gap[0][0].equals(topString)) {

						pair.add(0);
						pair.add(i);
				}
			}
		}
		//確認用
		//System.out.println("-pair_2-");
		//System.out.println(pair);
		//System.out.println("-gap-");
		//System.out.println(Arrays.deepToString(gap));
		
		//System.out.println("-find_pair_2:end-");
		return pair;
	}
	
	//任意の層記号のペアを探す関数(完全一致ver.)
	//gapのi番目とr番目がペア
	public static ArrayList<Integer> find_pair_3(ArrayList<String> cutText) {

		//System.out.println("-find_pair_3:start-");
		//関数呼び出し
		String[][] gap = null;
		
		gap = find_gap_2(cutText);
		
		//System.out.println("-SymbolOnly-");
		//System.out.println(Arrays.toString(SymbolOnly));
		//System.out.println("-gap-");
		//System.out.println(Arrays.deepToString(gap));


		//処理ここから
		ArrayList<Integer> pair = new ArrayList<>();

		for (int i = 0; i < gap.length; i++) {

			for (int r = i + 1; r < gap.length; r++) {

				if ((gap[i][0].equals(gap[r][1])) && (gap[i][1].equals(gap[r][0]))) {

						pair.add(i);
						pair.add(r);
				}
			}
		}
		//確認用
		//System.out.println("-pair_3-");
		//System.out.println(pair);
		System.out.println("-gap-");
		System.out.println(Arrays.deepToString(gap));
		
		//System.out.println("-find_pair_3:end-");
		return pair;
	}
	
	//各端点の座標を求める関数2
	//edgePointの形は[[x座標1,y座標1],[x座標2,y座標2], ...]
	public static ArrayList<ArrayList<Double>> find_point_2(ArrayList<String> cutText, ArrayList<ArrayList<Double>> cornerPoint) {

		System.out.println("-find_point_2:start-");

		ArrayList<ArrayList<Double>> edgePoint = new ArrayList<>();
		
		//頂点の数
		int cornerCount = cornerPoint.size();
		
		//辺n,n+1の場合、文字列上で頂点n-1から頂点nの部分を確認する

		//1から9を区切り文字として区切って、配列に入れる
		//cutCutText[0]は1の前、つまり空
		//cutCutText[n]はnからn+1の文字列
		String[] cutCutText = null;
		
		cutCutText = find_symbol_only_3(cutText);
		
		
		//System.out.println("-cutCutText-");
		//System.out.println(Arrays.toString(cutCutText));
		
		//2頂点の座標差
		double cornerDiffX =  0;
		double cornerDiffY =  0;
		
		//2頂点間の端点の数
		int pointCount = 0;
		
		//各端点のx座標y座標
		double pointX = 0;
		double pointY = 0;
		
		//n,n+1の場合(0 <= n < cornerCount-1)
		for(int i = 0; i < cornerCount && i < cutCutText.length-1; i++) {
			
			//n,n+1の場合
			if(i != cornerCount-1) {
				//頂点番号の大きい方から小さい方を引く
				cornerDiffX =  cornerPoint.get(i+1).get(0) - cornerPoint.get(i).get(0);
				cornerDiffY =  cornerPoint.get(i+1).get(1) - cornerPoint.get(i).get(1);
			
				//文字列が空の場合は文字数0
				if(cutCutText[i+1].equals("")) {pointCount = 0;}
				else{pointCount = cutCutText[i+1].split("").length;}
			}
			
			//n,1の場合
			else {
				//頂点番号の大きい方(0)から小さい方(n)を引く
				cornerDiffX =  cornerPoint.get(0).get(0) - cornerPoint.get(i).get(0);
				cornerDiffY =  cornerPoint.get(0).get(1) - cornerPoint.get(i).get(1);
			
				if(cutCutText[i+1].equals("")) {pointCount = 0;}
				else{pointCount = cutCutText[i+1].split("").length;}
			}
			
			//確認用
			/*System.out.println("-pointCount-");
			System.out.println(pointCount);
			System.out.println("-cornerDiffX-");
			System.out.println(cornerDiffX);
			System.out.println("-cornerDiffY-");
			System.out.println(cornerDiffY);*/
			
			
			for(int t = 0; t < pointCount; t++) {
									
				//頂点番号の小さい方から、割った差を加える
				//System.out.println("-ここですか？-");
				//System.out.println(i);
				pointX = cornerPoint.get(i).get(0) + (t+1) * (cornerDiffX / (pointCount+1));
				pointY = cornerPoint.get(i).get(1) + (t+1) * (cornerDiffY / (pointCount+1));
				
				edgePoint.add(new ArrayList<Double>(Arrays.asList(pointX, pointY)));
				//System.out.println("-動いてます-");
			}
		}

		//確認用
		//System.out.println("-find_point_2内-");
		System.out.println("-cornerPoint-");
		System.out.println(cornerPoint);
		System.out.println("-edgePoint-");
		System.out.println(edgePoint);
		
		System.out.println("-find_point_2:end-");
		return edgePoint;
	}
	
	//頂点の座標を更新する関数
	//端点が右辺or左辺に存在する前提の関数
	public static ArrayList<ArrayList<Double>> upd_corner_point(ArrayList<String> cutText, ArrayList<ArrayList<Double>> cornerPoint,
																String topString){
		
		//更新後の座標
		ArrayList<ArrayList<Double>> updCornerPoint = new ArrayList<>();
		//元の座標をコピー
		updCornerPoint.addAll(cornerPoint);
		
		//除く要素番号
		ArrayList<Integer> cutCorner = new ArrayList<>();
		
		//外周の上と左
		double top = find_min(cornerPoint, 1);
		
		//境界線の端点の座標
		double[] topPoint = find_top_point(cutText, cornerPoint, topString);
		
		//境界線の端点の左側、右側
		double pointLeft = Math.min(topPoint[0], topPoint[2]);
		double pointRight = Math.max(topPoint[0], topPoint[2]);
		
		//境界線の端点の左側、右側
		double pointTop = Math.min(Math.min(topPoint[1], topPoint[3]),top);
		double pointBottom = Math.max(topPoint[1], topPoint[3]);
		
		int i = 0;
		for(i = 0; i < cornerPoint.size(); i++) {
			
			//境界線の端点(2点)の間にある頂点
			if(pointLeft <= cornerPoint.get(i).get(0) && cornerPoint.get(i).get(0) <= pointRight
				&& pointTop <= cornerPoint.get(i).get(1) && cornerPoint.get(i).get(1) <= pointBottom) {
				
				//位置を記録
				cutCorner.add(i);
			}		
		}
		
		//境界線の端点に挟まれた要素を削除
		//0からだとだるま落としみたいになるので最後から
		for(i = cutCorner.size()-1; i >= 0; i--) {
			updCornerPoint.remove((int)cutCorner.get(i));
		}
		
		//System.out.println("-updCornerPoint-");
		//System.out.println(updCornerPoint);
		
		//境界線の端点を頂点に追加
		ArrayList<Double> newRow1 = new ArrayList<>();
		ArrayList<Double> newRow2 = new ArrayList<>();
		
		if(topPoint[0] <= topPoint[2]) {
			newRow1.add(topPoint[0]);
			newRow1.add(topPoint[1]);
			newRow2.add(topPoint[2]);
			newRow2.add(topPoint[3]);
		}
		
		else if(topPoint[0] > topPoint[2]) {
			newRow1.add(topPoint[2]);
			newRow1.add(topPoint[3]);
			newRow2.add(topPoint[0]);
			newRow2.add(topPoint[1]);
		}
		
		//省く頂点が1つの時
		/*if(cutCorner.size() == 1) {
			updCornerPoint.add(cutCorner.get(0),newRow2);
			updCornerPoint.add(cutCorner.get(0),newRow1);
		}
		//省く頂点が2つの時
		else if(cutCorner.size() == 2) {
			
			updCornerPoint.add(cutCorner.get(0),newRow1);
			updCornerPoint.add(cutCorner.get(1),newRow2);
		}*/
		
		//省く頂点が3つの時
		
		//省く頂点がn個
		
		//System.out.println("testtttt");
		//System.out.println(cutCorner.get(0));
		//System.out.println((int)cutCorner.get(0));

		//追加
		updCornerPoint.add(cutCorner.get(0),newRow2);
		updCornerPoint.add(cutCorner.get(0),newRow1);
		
		//端点の座標、頂点の座標から省く頂点、加える頂点がわかる
		
		//確認用
		/*System.out.println("-top,left-");
		System.out.print(top +",");
		System.out.println(left);
		System.out.println("-topPoint-");
		System.out.println(Arrays.toString(topPoint));
		System.out.println("-pointTop,pointLeft-");
		System.out.print(pointTop +",");
		System.out.println(pointLeft);
		System.out.println("-newRow1-");
		System.out.println(newRow1);
		System.out.println("-newRow2-");
		System.out.println(newRow2);*/
		System.out.println("-cutCorner-");
		System.out.println(cutCorner);
		System.out.println("-cornerPoint-");
		System.out.println(cornerPoint);
		System.out.println("-updCornerPoint-");
		System.out.println(updCornerPoint);
		
		return updCornerPoint;	
	}
	
	//配列内の最小値を求める関数(二次元配列, 行番号)
	public static double find_min(ArrayList<ArrayList<Double>> cornerPoint, int index) {
		double min = cornerPoint.get(0).get(index);
		
		for(int i = 1; i < cornerPoint.size(); i++) {
			if(min > cornerPoint.get(i).get(index)) {
				min = cornerPoint.get(i).get(index);
			}
		}
		
		return min;
	}
	
	//一番上の文字の座標を求める関数
	//upd_corner_pointとほぼ同じ
	public static ArrayList<Double> find_letter_point_2(ArrayList<String> cutText, ArrayList<ArrayList<Double>> cornerPoint,
																String topString){
		
		//一番上の文字の座標
		ArrayList<Double> letterPoint = new ArrayList<>();
		
		//除く要素番号
		ArrayList<Integer> cutCorner = new ArrayList<>();
		
		//外周の上
		//ここを修正()
		double top = find_min(cornerPoint, 1);
		
		//境界線の端点の座標
		//ここでエラー
		System.out.println("-find_letter_point_2-");
		System.out.println("-cutText-");
		System.out.println(cutText);
		System.out.println("-cornerPoint-");
		System.out.println(cornerPoint);
		System.out.println("-topString-");
		System.out.println(topString);
		double[] topPoint = find_top_point(cutText, cornerPoint, topString);
		
		//境界線の端点の左側、右側
		double pointLeft = Math.min(topPoint[0], topPoint[2]);
		double pointRight = Math.max(topPoint[0], topPoint[2]);
		
		//境界線の端点の左側、右側
		double pointTop = Math.min(Math.min(topPoint[1], topPoint[3]),top);
		double pointBottom = Math.max(topPoint[1], topPoint[3]);
		
		int i = 0;
		for(i = 0; i < cornerPoint.size(); i++) {
			
			//境界線の端点(2点)の間にある頂点
			if(pointLeft <= cornerPoint.get(i).get(0) && cornerPoint.get(i).get(0) <= pointRight
				&& pointTop <= cornerPoint.get(i).get(1) && cornerPoint.get(i).get(1) <= pointBottom) {
				
				//位置を記録
				cutCorner.add(i);
			}		
		}
		
		double pointSumX = 0;
		double pointSumY = 0;
		double pointAveX = (topPoint[0] + topPoint[2]) / 2;
		double pointAveY = (topPoint[1] + topPoint[3]) / 2;
		double pointX = 0;
		double pointY = 0;
		
		//省く頂点がnの場合
		for(i = 0; i < cutCorner.size(); i++) {
			
			//境界線を除く頂点の座標の合計
			pointSumX += cornerPoint.get(cutCorner.get(i)).get(0);
			pointSumY += cornerPoint.get(cutCorner.get(i)).get(1);
		}
		
		pointX = ((pointSumX / cutCorner.size()) +  pointAveX) / 2;
		pointY = ((pointSumY / cutCorner.size()) +  pointAveY) / 2;
		
		//微調整(文字の大きさで代わるはず)
		pointX -= 15;
		pointY += 15;
		
		letterPoint.add(pointX);
		letterPoint.add(pointY);
		
		return letterPoint;	
	}
	
	//ペアが全て存在する場合の地層名の座標を計算(1層記号ずつ)
	public static ArrayList<Double> find_letter_point_3(ArrayList<String> cutText, ArrayList<ArrayList<Double>> cornerPoint,
														String topString) {

		System.out.println("-find_letter_point_3:start-");
		ArrayList<Double> letterPoint = new ArrayList<>();
		
		//関数呼び出し
		//String[] text1234SymbolOnly = find_symbol_only(cutText);
		//String[][] gap = find_gap(cutText);
		//ArrayList<Integer> pair = new ArrayList<>(find_pair_3(cutText));
		ArrayList<ArrayList<Double>> point = new ArrayList<>(find_point_2(cutText, cornerPoint));
		String[][] gap = null;
		
		String[] cutCutText1 = null;
		
		gap = find_gap_2(cutText);
		
		//cutCutText1に1文字ずつ入れる
		for (String part1 : cutText) {
			cutCutText1 = part1.split("");
		}
		
		//for文用
		int i = 0;
		int t = 0;
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		
		//頂点を含むかどうかのフラグ(1:含む、0:含まない)
		int contCornerFlag = -1;
		//隅(端)かどうかのフラグ(1:隅、0:隅でない)
		int angleFlag = -1;
		
		//cutCutText1の何番目にtopStringと同じ記号があるか
		int symbolIndex = -1;
		
		//同じ記号の出現回数
		int sameSymbolCount = 0;
		
		//囲んでいる頂点
		ArrayList<Integer> conCornerSet = new ArrayList<>();
		
		//囲んでいる頂点の座標の合計
		double cornerXSum = 0;
		double cornerYSum = 0;
		
		//囲んでいる頂点の座標の平均
		double cornerXAve = 0;
		double cornerYAve = 0;
		
		//境界線の端点の座標の合計
		double pointXSum = 0;
		double pointYSum = 0;
		
		//境界線の端点の座標の平均
		double pointXAve = 0;
		double pointYAve = 0;
		
		//文字の座標
		double letterPointX = 0;
		double letterPointY = 0;
		
		//頂点を含むか調べる
		//最後の文字以外の場合
		for(i = 0; i < cutCutText1.length - 1; i++) {
			
			if(cutCutText1[i].equals(topString)) {
				
				symbolIndex = i;
				
				//層記号の次の記号が数字の場合は頂点を含む
				if(cutCutText1[i+1].matches("[1-9]")) {
					contCornerFlag = 1;
				}
				else {contCornerFlag = 0;}
			}
		}
		
		//最後の文字の場合
		if(cutCutText1[cutCutText1.length-1].equals(topString)) {
			
			//層記号の次の記号が数字の場合は頂点を含む
			if(cutCutText1[0].equals("1")) {
				contCornerFlag = 1;
				symbolIndex = i;
			}
			else {contCornerFlag = 0;}
		}
		System.out.println("-debug1-");
		//角か角じゃないかの判定
		//頂点を含むもの
		for(i = 0; i < cutCutText1.length; i++) {
			
			//出現する記号の回数を数える
			if(cutCutText1[i].equals(cutCutText1[symbolIndex]) && i != symbolIndex) {
				//angleFlag = 0;
				sameSymbolCount += 1;
			}
			else if(sameSymbolCount == 0){
				//angleFlag = 1;
			}
		}
		//1回しか出現しない記号が端になる
		if(sameSymbolCount == 0) {angleFlag = 1;}
		else {angleFlag = 0;}
		
		System.out.println("-debug2-");
		System.out.println("-angleFlag-");
		System.out.println(angleFlag);
		System.out.println("-contCornerFlag-");
		System.out.println(contCornerFlag);

		//角の場合(必ず頂点を含む)
		//境界線の端点の中心を求める
		if(angleFlag == 1) {
			for(a = 0; a < gap.length; a++) {
				for(b = 0; b < gap.length; b++) {
					
					//囲まれている2点を探す
					if (gap[a][0].equals(topString) && gap[b][1].equals(topString) && a != b) {
						
						//2点の平均
						pointXSum = (point.get(a).get(0) + point.get(b).get(0));
						pointYSum = (point.get(a).get(1) + point.get(b).get(1));
						//pointX = (point.get(a).get(0) + point.get(b).get(0)) / 2;
						//pointY = (point.get(a).get(1) + point.get(b).get(1)) / 2;
					}
				}
			}	
		}
		System.out.println("-debug3-");
		//頂点の計算
		//端の場合
		if(angleFlag == 1) {
			//どの頂点に囲まれているか調べる
			//その文字の次に数字が何個並んでいるか調べる
			for(i = symbolIndex+1; i < cutCutText1.length; i++) {
				//System.out.println("cutCutText1[i]");
				//System.out.println(cutCutText1[i]);
				if(cutCutText1[i].matches("[1-9]")) {
					
					//conCornerSetに頂点のindexを格納
					//stringをintに変換し、conCornerSetに追加
					conCornerSet.add(Integer.parseInt(cutCutText1[i]));
					
					//文字列の最後が数字の場合
					if(i == cutCutText1.length-1) {
						
						//最初から調べる
						for(t = 0; t < cutCutText1.length; t++) {
							if(cutCutText1[t].matches("[1-9]")) {
								
								//stringをintに変換し、conCornerSetに追加
								conCornerSet.add(Integer.parseInt(cutCutText1[t]));
							}
							else {break;}
						}
					}
				}
				
				else {break;}
			}
			
			//1を含む場合
			//文字列の最後が記号の場合
			if(symbolIndex == cutCutText1.length-1) {
			
				//最初から調べる
				for(i = 0; i < cutCutText1.length; i++) {
					if(cutCutText1[i].matches("[1-9]")) {
						
						//stringをintに変換し、conCornerSetに追加
						conCornerSet.add(Integer.parseInt(cutCutText1[i]));
					}
					else {break;}
				}
			}
			
			System.out.println(conCornerSet);
			System.out.println(cornerPoint);
			
			//頂点の座標の平均を求める
			for(i = 0; i < conCornerSet.size(); i++) {
				System.out.println("-debug3.5-");
				//conCornerSetには囲まれた頂点番号が入っているので、-1すると cornerPointでの頂点番号の座標が分かる
				cornerXSum += cornerPoint.get(conCornerSet.get(i)-1).get(0);
				cornerYSum += cornerPoint.get(conCornerSet.get(i)-1).get(1);
			}
			cornerXAve = cornerXSum / conCornerSet.size();
			cornerYAve = cornerYSum / conCornerSet.size();
		}
		
		System.out.println("-debug4-");
		//2直線に囲まれている地層(頂点含む含まない兼用)
		if(angleFlag == 0) {
			for(a = 0; a < gap.length; a++) {
				for(b = 0; b < gap.length; b++) {
					for(c = 0; c < gap.length; c++) {
						for(d = 0; d < gap.length; d++) {
							
							//囲まれている4点を探す
							if (gap[a][0].equals(topString) && gap[b][0].equals(topString)
									&& gap[c][1].equals(topString) && gap[d][1].equals(topString)
									&& a != b && c != d) {
								
								//4点の合計
								pointXSum = (point.get(a).get(0) + point.get(b).get(0) + point.get(c).get(0) + point.get(d).get(0));
								pointYSum = (point.get(a).get(1) + point.get(b).get(1) + point.get(c).get(1) + point.get(d).get(1));
							
								//4点の平均
								pointXAve = pointXSum / 4;
								pointYAve = pointYSum / 4;
							}
						}
					}
				}
			}
		}
		
		//2直線に囲まれる場合
		if(angleFlag == 0) { 	
			//頂点を含まないので2直線の平均
			letterPointX = pointXAve;
			letterPointY = pointYAve;
		}
		
		//端の場合
		else if(angleFlag == 1) {
			//1直線と頂点の平均
			letterPointX = (pointXSum + cornerXSum) / (conCornerSet.size()+2);
			letterPointY = (pointYSum + cornerYSum) / (conCornerSet.size()+2);
		}
		
		//微調整(文字の大きさで代わるはず)
		letterPointX -= 15;
		letterPointY += 15;
		
		letterPoint.add(letterPointX);
		letterPoint.add(letterPointY);
		
		System.out.println("-pointXAve-");
		System.out.println(pointXAve);
		System.out.println("-pointYAve-");
		System.out.println(pointYAve);
		System.out.println("-cornerXAve-");
		System.out.println(cornerXAve);
		System.out.println("-cornerYAve-");
		System.out.println(cornerYAve);
		System.out.println("-letterPoint-");
		System.out.println(letterPoint);
		System.out.println("-conCornerSet-");
		System.out.println(conCornerSet);
		System.out.println("-cutCutText1-");
		System.out.println(Arrays.toString(cutCutText1));
		
		System.out.println("-find_letter_point_3:end-");
		return letterPoint;
	}

	
	//数字を除いた配列を求める関数2([1,a,2,b,3,c,4,b,e] -> [a,b,c,b,e])
	public static String[] find_symbol_only_2(ArrayList<String> cutText) {
		//System.out.println("-find_symbol_only_2:start-");
		
		String[] cutCutText1 = null;
		String[] cutCutText2 = null;
		ArrayList<String> cutCutText3 = new ArrayList<>();
		String[] cutCutText4 = null;
		
		//1～9を区切り文字として区切る
		for (String part1 : cutText) {
			cutCutText1 = part1.split("[1-9]");
			
			//1文字ずつ区切る
			for (String part2 : cutCutText1) {
				cutCutText2 = part2.split("");
				
				for (String part3 : cutCutText2) {
					cutCutText3.add(part3);
		        }
			}
		}
		
		cutCutText4 = cutCutText3.toArray(new String[0]);
		
		//split("")してから、1-9を除く
		
		//[a,b,cd]のようになるので処理を行う
		//String[]をStringに
		//cutCutText1 = String.join("", cutCutText);
		//cutCutText2 = cutCutText1.split("");
		
		

		//確認用
		//System.out.println("-cutCutText-");
		//System.out.println(Arrays.toString(cutCutText4));
		//System.out.println(cutCutText3);
		//System.out.println("-cutText-");
		//System.out.println(cutText);

		//System.out.println("-find_symbol_only_2:end-");
		return cutCutText4;
	}
	
	//数字を除いた配列を求める関数2([1,a,2,b,3,c,4,b,e] -> [a,b,c,be])
	public static String[] find_symbol_only_3(ArrayList<String> cutText) {
		//System.out.println("-find_symbol_only_2:start-");
		
		String[] cutCutText1 = null;
		
		//1～9を区切り文字として区切る
		for (String part1 : cutText) {
			cutCutText1 = part1.split("[1-9]");
		}
		
		//確認用
		//System.out.println("-cutCutText-");
		//System.out.println(Arrays.toString(cutCutText1));
		//System.out.println(cutCutText3);
		//System.out.println("-cutText-");
		//System.out.println(cutText);

		//System.out.println("-find_symbol_only_2:end-");
		return cutCutText1;
	}
	
	//各端点の変化を求める関数([a,b,c,b] -> [[b,a],[a,b],[b,c],[c,b]])
	public static String[][] find_gap_2(ArrayList<String> cutText) {

		//関数呼び出し
		String[] text1234SymbolOnly = find_symbol_only_2(cutText);

		//記号の数(空文字を除く)
		int symbolCount = 0;
		
		for (String str : text1234SymbolOnly) {
			if (!str.isEmpty()) {
				symbolCount++;
            }
		}
		//処理ここから
		//層の変化を格納する配列
		String[][] gap = new String[symbolCount][2];

		int i = 0;
		int index = 0;
		int indexCopy = 0;
		int t = 0;
		
		for(i = 0, index = 0; i < text1234SymbolOnly.length && index < symbolCount;i++) {
			
			//空でないとき
			if(!text1234SymbolOnly[i].isEmpty()) {
				//gap[0]だけ特殊
				if(index == 0) {
					gap[0][1] = text1234SymbolOnly[i];
					
					for(t = text1234SymbolOnly.length-1; t >= 0; t--) {
						//空でないとき
						if(!text1234SymbolOnly[t].isEmpty() && gap[0][0] == null) {
							gap[0][0] = text1234SymbolOnly[t];
							index++;
						}
					}
				}
				//gap[1]からの処理
				else {
					indexCopy = index;
					gap[index][1] = text1234SymbolOnly[i];
					
					for(t = i-1; t >= 0; t--) {
						//空でないとき
						if(!text1234SymbolOnly[t].isEmpty() && gap[indexCopy][0] == null) {
							gap[index][0] = text1234SymbolOnly[t];
							indexCopy = index;
							index++;
						}
					}
					
				}
			}
		}
		//gap[0]だけ特殊
		//gap[0][0] = text1234SymbolOnly[text1234SymbolOnly.length - 1];
		//gap[0][1] = text1234SymbolOnly[0];

		//gap[1]からの処理
		/*for (int i = 1; i < text1234SymbolOnly.length; i++) {
			gap[i][0] = text1234SymbolOnly[i - 1];
			gap[i][1] = text1234SymbolOnly[i];
		}*/

		//確認用
		/*System.out.println("-gap-");
		System.out.println(Arrays.deepToString(gap));*/

		return gap;
	}
	
	//String型->ArrayList<String>型にする関数
	public static ArrayList<String> change_arraylist_string(String text){
		String[] textArray = text.split("");
		ArrayList<String> textDynamicArray = new ArrayList<>(Arrays.asList(textArray));
		
		return textDynamicArray;
	}
}

//-----------------妥当性関連の関数をまとめたクラス-------------------
class Validity_func{
	
	//前提条件をまとめた関数
	public static void premise_condition_all(String text1234String, String curveString, ArrayList<Integer> premiseCondition) {
		ArrayList<Integer> premiseConditionCopy = new ArrayList<>(premiseCondition);
		
		//前提条件1(文字列が無い場合)
		premise_condition_1(text1234String, premiseConditionCopy);
		
		if(premiseConditionCopy.size() == 0) {
			//前提条件2(6文字以上かどうか)
			premise_condition_2(text1234String, premiseConditionCopy);
			//前提条件3(文字の個数は2の倍数)
			premise_condition_3(text1234String, premiseConditionCopy);			
		}
		
		if(premiseConditionCopy.size() == 0) {
			//前提条件5(同じ記号が連続)
			premise_condition_5(text1234String, premiseConditionCopy);
			//前提条件6(1234の順かどうか)
			premise_condition_6(text1234String, premiseConditionCopy);
		}
		
		if(!curveString.equals("")) {
			//前提条件4(右側の引数は1,2,3,4のみ)
			premise_condition_4(curveString, premiseConditionCopy);
		}
		
		premiseCondition.clear();
		premiseCondition.addAll(premiseConditionCopy);
	}
	
	//前提条件1(文字列が無い場合)
	public static void premise_condition_1(String text1234String, ArrayList<Integer> premiseCondition){
		ArrayList<Integer> premiseConditionCopy = new ArrayList<>(premiseCondition);
		
		if(text1234String.equals("")) {
			premiseConditionCopy.add(1);
			//書き換え
			premiseCondition.clear();
			premiseCondition.addAll(premiseConditionCopy);
		}
	}
	
	//前提条件2(6文字以上かどうか)
	public static void premise_condition_2(String text1234String, ArrayList<Integer> premiseCondition){
		String[] text1234StringArray = text1234String.split("");
		ArrayList<Integer> premiseConditionCopy = new ArrayList<>(premiseCondition);
		
		if(text1234StringArray.length < 6) {
			premiseConditionCopy.add(2);
			
			premiseCondition.clear();
			premiseCondition.addAll(premiseConditionCopy);
		}
	}
	
	//前提条件3(文字の個数は2の倍数)
	public static void premise_condition_3(String text1234String, ArrayList<Integer> premiseCondition){
		String[] text1234StringArray = text1234String.split("");
		ArrayList<Integer> premiseConditionCopy = new ArrayList<>(premiseCondition);
		
		if(text1234StringArray.length % 2 != 0) {
			premiseConditionCopy.add(3);

			premiseCondition.clear();
			premiseCondition.addAll(premiseConditionCopy);
		}
	}

	//前提条件4(右側の引数は1,2,3,4のみ)
	public static void premise_condition_4(String curveString, ArrayList<Integer> premiseCondition){
		ArrayList<Integer> premiseConditionCopy = new ArrayList<>(premiseCondition);
		
		if(!curveString.matches("[1-4]")) {
			premiseConditionCopy.add(4);

			premiseCondition.clear();
			premiseCondition.addAll(premiseConditionCopy);
		}
	}
	
	//前提条件5(同じ記号が連続)
	public static void premise_condition_5(String text1234String, ArrayList<Integer> premiseCondition){
		String[] text1234StringArray = text1234String.split("");
		ArrayList<Integer> premiseConditionCopy = new ArrayList<>(premiseCondition);
		
		for(int i = 0; i < text1234StringArray.length - 1; i++){

			if(text1234StringArray[i].equals(text1234StringArray[i+1])){
				premiseConditionCopy.add(5);
				
				premiseCondition.clear();
				premiseCondition.addAll(premiseConditionCopy);
				
				break;
			}
		}
	}	
	
	//前提条件6(1234の順かどうか)
	public static void premise_condition_6(String text1234String, ArrayList<Integer> premiseCondition){
		
		String[] text1234StringArray = text1234String.split("");
		ArrayList<Integer> premiseConditionCopy = new ArrayList<>(premiseCondition);
		
		//エラーフラグ
		int ngFlag = 0;
		
		//期待する数字
		int expectedNumber = 1; 	
		
		for (String str : text1234StringArray) {
			
			//1-4にマッチするか
			if (str.matches("[1-4]")) {
				
				//文字を整数にする
				int number = Integer.parseInt(str);
	
				//期待する数字でない場合
				if (number != expectedNumber) {
					ngFlag = 1;
					break;
		        }
		        expectedNumber++;
		    }
			
			//4以降に数字が出現する場合
			else if(isNumeric(str) && ngFlag == 0){
				ngFlag = 1;
				break;
			}
		}
		
		if(ngFlag == 1) {
			premiseConditionCopy.add(6);
			//書き換え
			premiseCondition.clear();
			premiseCondition.addAll(premiseConditionCopy);
		}
	}
	
	//数字かどうか判定
	public static boolean isNumeric(String s) {
		try{
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	
	//本条件をまとめた関数
	public static void main_condition_all(String text1234String, String curveString ,ArrayList<Integer> mainCondition){
		
		ArrayList<Integer> mainConditionCopy = new ArrayList<>(mainCondition);
		
		//本条件1(端層記号は2種類)
		main_condition_1(text1234String, mainConditionCopy);
		
		if(mainConditionCopy.size() == 0) {
			//本条件2(頂点ができる文字列を省く)
			main_condition_2(text1234String, mainConditionCopy);
			//本条件3(ペアの数は文字列の半分+ペアが一意に決まる)
			main_condition_3(text1234String, mainConditionCopy);
			//本条件4(ハの字)
			main_condition_4(text1234String, mainConditionCopy);
			//本条件5(曲がり方と境界線の傾き)
			main_condition_5(text1234String, curveString ,mainConditionCopy);
			
			if(text1234String.length() >= 8) {
				//本条件6(両端間文字列内に同じ記号)
				main_condition_6(text1234String, mainConditionCopy);
				//本条件7(両端間文字列は互いに逆順)
				main_condition_7(text1234String, mainConditionCopy);
			}
		}
		//本条件8(t(T)は端)
		main_condition_8(text1234String, mainConditionCopy);
		
		mainCondition.clear();
		mainCondition.addAll(mainConditionCopy);
	}
	
	//本条件1(端層記号は2種類)
	public static void main_condition_1(String text1234String, ArrayList<Integer> mainCondition){
		
		ArrayList<Integer> mainConditionCopy = new ArrayList<>(mainCondition);
		ArrayList<String> onlyOneSymbol = new ArrayList<>();
		ArrayList<Integer> onlyOneSymbolPoint = new ArrayList<>();
		
		String_func.find_only_one_symbol_point(text1234String, onlyOneSymbol, onlyOneSymbolPoint);
		
		if(onlyOneSymbol.size() != 2) {
			mainConditionCopy.add(1);
			//書き換え
			mainCondition.clear();
			mainCondition.addAll(mainConditionCopy);
		}
	}
	
	//本条件2(頂点ができる文字列を省く)
	public static void main_condition_2(String text1234String, ArrayList<Integer> mainCondition){
		
		String[] text1234StringArray = text1234String.split("");
		ArrayList<Integer> mainConditionCopy = new ArrayList<>(mainCondition);
		ArrayList<String> onlyOneSymbol = new ArrayList<>();
		ArrayList<Integer> onlyOneSymbolPoint = new ArrayList<>();
		int count = 0;
		
		String_func.find_only_one_symbol_point(text1234String, onlyOneSymbol, onlyOneSymbolPoint);
		
		for(int i = 0; i < text1234StringArray.length; i++) {
			
			if(text1234StringArray[i].equals("2")) {
				if(text1234StringArray[i-1].equals(onlyOneSymbol.get(0)) 
					|| text1234StringArray[i-1].equals(onlyOneSymbol.get(1))) {count++;}
			}
			if(text1234StringArray[i].equals("3")) {
				if(text1234StringArray[i-1].equals(onlyOneSymbol.get(0)) 
					|| text1234StringArray[i-1].equals(onlyOneSymbol.get(1))) {count++;}
			}
			if(text1234StringArray[i].equals("4")) {
				if(text1234StringArray[i-1].equals(onlyOneSymbol.get(0)) 
					|| text1234StringArray[i-1].equals(onlyOneSymbol.get(1))) {count++;}
			}
			
			else if(i == text1234StringArray.length-1) {
				if(text1234StringArray[i].equals(onlyOneSymbol.get(0)) 
					|| text1234StringArray[i].equals(onlyOneSymbol.get(1))) {count++;}
			}
		}
		
		//確認用
		//System.out.println("---mainCondition2---");
		//System.out.printf("count:%d, onlyOneSymbolsize():%d\n",count,onlyOneSymbol.size());

		
		if(count != onlyOneSymbol.size()) {
			mainConditionCopy.add(2);

			mainCondition.clear();
			mainCondition.addAll(mainConditionCopy);
		}
	}
	
	//本条件3(ペアの数は文字列の半分+ペアが一意に決まる)
	public static void main_condition_3(String text1234String, ArrayList<Integer> mainCondition){
		
		ArrayList<Integer> mainConditionCopy = new ArrayList<>(mainCondition);
		ArrayList<ArrayList<Integer>> pair = new ArrayList<>(String_func.find_pair(text1234String));
		String[][] gap = String_func.find_gap(text1234String);
		String[] text1234SymbolOnly = String_func.find_symbol_only(text1234String);
		int i,a;
		
		if(pair.size() != text1234SymbolOnly.length/2) {mainConditionCopy.add(3);}
		
		else {
			for(i = 0; i < gap.length-1; i++){
				
				for(a = i+1; a < gap.length; a++ ){
					//重複がある場合
					if(gap[i][0].equals(gap[a][0]) && gap[i][1].equals(gap[a][1])){
						mainConditionCopy.add(3);
					}
				}
			}
		}
		
		if(mainConditionCopy.size() == 1) {
			//書き換え
			mainCondition.clear();
			mainCondition.addAll(mainConditionCopy);
		}
	}
	
	//本条件4(ハの字)
	public static void main_condition_4(String text1234String, ArrayList<Integer> mainCondition){
		
		ArrayList<Integer> mainConditionCopy = new ArrayList<>(mainCondition);
		
		int[] edgeCurve = find_edge_curve(text1234String);
		
		if(edgeCurve[0] >= 1 && edgeCurve[1] >= 1){
			mainConditionCopy.add(4);
			//書き換え
			mainCondition.clear();
			mainCondition.addAll(mainConditionCopy);
		}
	}
	
	//本条件5(曲がり方と境界線の傾き)
	public static void main_condition_5(String text1234String, String curveString, ArrayList<Integer> mainCondition){
		ArrayList<Integer> mainConditionCopy = new ArrayList<>(mainCondition);
		int[] edgeCurve = find_edge_curve(text1234String);
		
		if(edgeCurve[0] >= 1) {
			if(curveString.equals("2") || curveString.equals("4")) {mainConditionCopy.add(5);}
		}
		if(edgeCurve[1] >= 1) {
			if(curveString.equals("1") || curveString.equals("3")) {mainConditionCopy.add(5);}
		}
		else if(edgeCurve[2] >= 1 && edgeCurve[0] == 0 && edgeCurve[1] == 0) {
			//何もしない
		}
		
		//書き換え
		mainCondition.clear();
		mainCondition.addAll(mainConditionCopy);
	}
	
	//端層記号の傾きを調べる関数([0]に13タイプ、[1]に24タイプ、[2]にどちらでもないタイプ)
	public static int[] find_edge_curve(String text1234String){
		
		String[] text1234StringArray = text1234String.split("");
		
		ArrayList<String> onlyOneSymbol = new ArrayList<>();
		ArrayList<Integer> onlyOneSymbolPoint = new ArrayList<>();
		
		int i,a;
		int curveType24 = 0;
		int curveType13 = 0;
		int curveTypeNeither = 0;
	 		
		String_func.find_only_one_symbol_point(text1234String, onlyOneSymbol, onlyOneSymbolPoint);
		
		for(i = 0; i < onlyOneSymbol.size(); i++){

			for(a = 0; a < text1234StringArray.length; a++){

				if(onlyOneSymbol.get(i).equals(text1234StringArray[a])){

					//端層記号が文字列の最後の場合
					if(a == text1234StringArray.length -1){
						
						//数字が2連続する場合(Array[0]==1は確定)
						if(text1234StringArray[1].equals("2")){

							//数字が3連続する場合(24タイプ)
							if(text1234StringArray[2].equals("3")){curveType24 += 1;}
							//2連続する場合(どちらでもないタイプ)
							else{curveTypeNeither += 1;}
						}
						//連続しない場合
						else{curveType13 += 1;}
					}

					//それ以外の場合
					else{
						//次の数字が2の場合
						if(text1234StringArray[a+1].equals("2")){

							//数字が2連続する場合
							if(text1234StringArray[a+2].equals("3")){

								//数字が3連続する場合
								if(text1234StringArray[a+3].equals("4")){curveType13 += 1;}
								//2連続する場合
								else{curveTypeNeither += 1;}
							}
							//連続しない場合
							else{curveType24 += 1;}
						}

						//次の数字が3の場合
						if(text1234StringArray[a+1].equals("3")){

							//数字が2連続する場合
							if(text1234StringArray[a+2].equals("4")){

								//数字が3連続する場合
								if(a+2 == text1234StringArray.length -1){curveType24 += 1;}
								//2連続する場合
								else{curveTypeNeither += 1;}
							}
							//連続しない場合
							else{curveType13 += 1;}
						}

						//次の数字が4の場合
						if(text1234StringArray[a+1].equals("4")){

							//数字が2連続する場合
							if(a+1 == text1234StringArray.length -1){

								//数字が3連続する場合
								if(text1234StringArray[1].equals("2")){curveType13 += 1;}
								//2連続する場合
								else{curveTypeNeither += 1;}
							}
							//連続しない場合
							else{curveType24 += 1;}
						}
					}
				}
			}
		}

		int[] edgeCurve = {curveType13, curveType24, curveTypeNeither};
		
		//確認用
		//System.out.printf("curveType13:%s\n",edgeCurve[0]);
		//System.out.printf("curveType24:%s\n",edgeCurve[1]);
		//System.out.printf("curveTypeNeither:%s\n",edgeCurve[2]);
		
		return edgeCurve;
	}
		
	//本条件6(両端間文字列内に同じ記号)
	public static void main_condition_6(String text1234String, ArrayList<Integer> mainCondition){
		ArrayList<Integer> mainConditionCopy = new ArrayList<>(mainCondition);
		//回文1
		ArrayList<String> palindrome0 = find_palindrome(text1234String, 0);
		//回文2
		ArrayList<String> palindrome1 = find_palindrome(text1234String, 1);
		
		if(palindrome0.size() > 0 && palindrome1.size() > 0) {
			
			for (int i = 0; i < palindrome0.size(); i++) {
				int count = 0;
				
				//配列全体を走査して要素の出現回数を数える
				for (int j = 0; j < palindrome0.size(); j++) {
					if (palindrome0.get(i).equals(palindrome0.get(j))) {
						count++;
					}
				}
				
				//出現回数が1回の要素のみを結果配列に追加
				if (count != 1) {
					mainConditionCopy.add(6);
					//書き換え
					mainCondition.clear();
					mainCondition.addAll(mainConditionCopy);
					break;
				}
			}
			
			//回文1がエラーでない場合
			if(!mainConditionCopy.contains(6)) {
				for (int i = 0; i < palindrome1.size(); i++) {
					int count = 0;
					
					//配列全体を走査して要素の出現回数を数える
					for (int j = 0; j < palindrome1.size(); j++) {
						if (palindrome1.get(i).equals(palindrome1.get(j))) {
							count++;
						}
					}
					
					//出現回数が1回の要素のみを結果配列に追加
					if (count != 1) {
						mainConditionCopy.add(6);
						//書き換え
						mainCondition.clear();
						mainCondition.addAll(mainConditionCopy);
						break;
					}
				}
			}
		}
	}
	
	//本条件7(両端間文字列は互いに逆順)
	public static void main_condition_7(String text1234String, ArrayList<Integer> mainCondition){
		
		ArrayList<Integer> mainConditionCopy = new ArrayList<>(mainCondition);
		ArrayList<String> palindrome0 = find_palindrome(text1234String, 0);
		ArrayList<String> palindrome1 = find_palindrome(text1234String, 1);
		ArrayList<String> palindrome0Copy = new ArrayList<>(palindrome0);

		Collections.reverse(palindrome0Copy);
		
		boolean notequal = false;
		
		if (palindrome0Copy == null ||  palindrome1 == null) {notequal = true;}
		
		if (palindrome0Copy.size() != palindrome1.size()) {notequal = true;}
		
		if(notequal == false) {
			for (int i = 0; i < palindrome0Copy.size(); i++) {
	    		if (!Objects.equals(palindrome0Copy.get(i), palindrome1.get(i))) {
	    			notequal = true;
		        }
		    }
	    }
		
		if(notequal == true) {
			mainConditionCopy.add(7);
			//書き換え
			mainCondition.clear();
			mainCondition.addAll(mainConditionCopy);
		}
	}
	
	//動的配列にする-----------------------------------------------------
	//両端点間文字列(回文)を求める関数
	public static ArrayList<String> find_palindrome(String text1234String, int number) {
		
		//回文の配列
		ArrayList<String> palindrome = new ArrayList<>();
		String[] text1234SymbolOnly = String_func.find_symbol_only(text1234String);
		ArrayList<String> onlyOneSymbol = new ArrayList<>();
		ArrayList<Integer> onlyOneSymbolPoint = new ArrayList<>();
		
		String_func.find_only_one_symbol_point(text1234String, onlyOneSymbol, onlyOneSymbolPoint);
		
		if(onlyOneSymbol.size() == 2) {
			int i;
			int frist = onlyOneSymbolPoint.get(0);
			int end = onlyOneSymbolPoint.get(1);
			
			//回文1の処理
			if(number == 0) {
				for(i = frist+1; i < end; i++) {
					palindrome.add(text1234SymbolOnly[i]);
				}
			}
			
			//回文2
			if(number == 1) {
				//処理1
				for(i = end+1; i < text1234SymbolOnly.length ; i++) {
					palindrome.add(text1234SymbolOnly[i]);
				}
				//処理2
				for(i = 0; i < frist; i++) {
					palindrome.add(text1234SymbolOnly[i]);
				}
			}
			
			//確認用
			//System.out.printf("回文%d:%s\n", number, palindrome);
		}
		return palindrome;
	}
	
	//本条件8(t(T)は端)
	public static void main_condition_8(String text1234String, ArrayList<Integer> mainCondition){
		
		ArrayList<Integer> mainConditionCopy = new ArrayList<>(mainCondition);
		ArrayList<String> onlyOneSymbol = new ArrayList<>();
		ArrayList<Integer> onlyOneSymbolPoint = new ArrayList<>();
			
		String_func.find_only_one_symbol_point(text1234String, onlyOneSymbol, onlyOneSymbolPoint);
		
		if((text1234String.indexOf("t") != -1) && !onlyOneSymbol.contains("t")) {
			mainConditionCopy.add(8);
			//書き換え
			mainCondition.clear();
			mainCondition.addAll(mainConditionCopy);
		}
		
		if((text1234String.indexOf("T") != -1) && !onlyOneSymbol.contains("T")) {
			mainConditionCopy.add(8);
			//書き換え
			mainCondition.clear();
			mainCondition.addAll(mainConditionCopy);
		}
	}
}

//-----------------反転回転関連の関数をまとめたクラス----------------------
class Operation_func {

	//回転
	public static String spin_string(String text1234String) {

		//System.out.println("90°回転");

		String text1234StringCopy;

		ArrayList<String> text12 = new ArrayList<>();
		ArrayList<String> text23 = new ArrayList<>();
		ArrayList<String> text34 = new ArrayList<>();
		ArrayList<String> text41 = new ArrayList<>();

		String_func.find_cut_string(text1234String, text12, text23, text34, text41);

		//確認用1
		/*System.out.println("-text1234String(変化前)-");
		System.out.println(text1234String);
		System.out.println("-text12, text23, text34, text41(変化前)-");
		System.out.printf("%s,%s,%s,%s\n", text12, text23, text34, text41);*/

		ArrayList<String> text12Copy = new ArrayList<>(text12);
		ArrayList<String> text23Copy = new ArrayList<>(text23);
		ArrayList<String> text34Copy = new ArrayList<>(text34);
		ArrayList<String> text41Copy = new ArrayList<>(text41);

		text12 = new ArrayList<>(text41Copy);
		text23 = new ArrayList<>(text12Copy);
		text34 = new ArrayList<>(text23Copy);
		text41 = new ArrayList<>(text34Copy);

		//text12～text41繋げる関数呼び出し
		text1234StringCopy = connect_string(text12, text23, text34, text41);

		//確認用2
		/*System.out.println("-text1234String(変化後)-");
		System.out.println(text1234StringCopy);
		System.out.println("-text12, text23, text34, text41(変化後)-");
		System.out.printf("%s,%s,%s,%s\n", text12, text23, text34, text41);*/

		return text1234StringCopy;
	}

	//反転
	public static String frip_string(String text1234String, int buttonType) {
		String text1234StringCopy = null;

		ArrayList<String> text12 = new ArrayList<>();
		ArrayList<String> text23 = new ArrayList<>();
		ArrayList<String> text34 = new ArrayList<>();
		ArrayList<String> text41 = new ArrayList<>();

		String_func.find_cut_string(text1234String, text12, text23, text34, text41);

		List<String> text12Copy = new ArrayList<>();
		List<String> text23Copy = new ArrayList<>();
		List<String> text34Copy = new ArrayList<>();
		List<String> text41Copy = new ArrayList<>();

		ArrayList<String> text12Change = new ArrayList<>();
		ArrayList<String> text23Change = new ArrayList<>();
		ArrayList<String> text34Change = new ArrayList<>();
		ArrayList<String> text41Change = new ArrayList<>();

		String[] vertexRegion = find_vertex_region(text12, text23, text34, text41);

		int a = 0;
		int b = 0;

		//確認用1
		/*System.out.println("-vertexRegion-");
		System.out.println(Arrays.toString(vertexRegion));
		System.out.println("-text12, text23, text34, text41(変化前)-");
		System.out.printf("%s,%s,%s,%s\n", text12, text23, text34, text41);*/

		if (text12.size() > 0) {
			//text12の0番目からtexr12.size()-1番目の要素までの部分リストを取得	        
			text12Copy = new ArrayList<>(text12.subList(0, text12.size() - 1));

			//先頭にvertexRegion[0]挿入
			text12Copy.add(0, vertexRegion[0]);

			//逆順にする
			for (a = 0, b = text12.size() - 1; a < text12.size() && b >= 0; a++, b--) {
				text12Change.add(text12Copy.get(b));
			}
		}

		if (text23.size() > 0) {
			text23Copy = new ArrayList<>(text23.subList(0, text23.size() - 1));
			text23Copy.add(0, vertexRegion[1]);
			for (a = 0, b = text23.size() - 1; a < text23.size() && b >= 0; a++, b--) {
				text23Change.add(text23Copy.get(b));
			}
		}

		if (text34.size() > 0) {
			text34Copy = new ArrayList<>(text34.subList(0, text34.size() - 1));
			text34Copy.add(0, vertexRegion[2]);
			for (a = 0, b = text34.size() - 1; a < text34.size() && b >= 0; a++, b--) {
				text34Change.add(text34Copy.get(b));
			}
		}

		if (text41.size() > 0) {
			text41Copy = new ArrayList<>(text41.subList(0, text41.size() - 1));
			text41Copy.add(0, vertexRegion[3]);
			for (a = 0, b = text41.size() - 1; a < text41.size() && b >= 0; a++, b--) {
				text41Change.add(text41Copy.get(b));
			}
		}

		//左右反転
		if (buttonType == 3) {
			text1234StringCopy = connect_string(text12Change, text41Change, text34Change, text23Change);
		}
		//上下反転
		else if (buttonType == 4) {
			text1234StringCopy = connect_string(text34Change, text23Change, text12Change, text41Change);
		}

		//確認用2
		/*System.out.println("-text12, text23, text34, text41(変化後)-");
		System.out.printf("%s,%s,%s,%s\n", text12Change, text23Change, text34Change, text41Change);*/

		return text1234StringCopy;
	}

	//頂点を含む領域の処理
	public static String[] find_vertex_region(ArrayList<String> text12, ArrayList<String> text23, ArrayList<String> text34,
			ArrayList<String> text41) {

		//vertexRegion[0]に頂点1を含む層が格納
		String[] vertexRegion = new String[4];

		//vertexRegion[0] 頂点1を含む領域
		if (text41.size() > 0) {
			vertexRegion[0] = text41.get(text41.size() - 1);
		} else {
			if (text34.size() > 0) {
				vertexRegion[0] = text34.get(text34.size() - 1);
			} else {
				if (text23.size() > 0) {
					vertexRegion[0] = text23.get(text23.size() - 1);
				}
			}
		}

		//vertexRegion[1] 頂点2を含む領域
		if (text12.size() > 0) {
			vertexRegion[1] = text12.get(text12.size() - 1);
		} else {
			if (text41.size() > 0) {
				vertexRegion[1] = text41.get(text41.size() - 1);
			} else {
				if (text34.size() > 0) {
					vertexRegion[1] = text34.get(text34.size() - 1);
				}
			}
		}

		//vertexRegion[2] 頂点3を含む領域
		if (text23.size() > 0) {
			vertexRegion[2] = text23.get(text23.size() - 1);
		} else {
			if (text12.size() > 0) {
				vertexRegion[2] = text12.get(text12.size() - 1);
			} else {
				if (text41.size() > 0) {
					vertexRegion[2] = text41.get(text41.size() - 1);
				}
			}
		}

		//vertexRegion[3] 頂点4を含む領域
		if (text34.size() > 0) {
			vertexRegion[3] = text34.get(text34.size() - 1);
		} else {
			if (text23.size() > 0) {
				vertexRegion[3] = text23.get(text23.size() - 1);
			} else {
				if (text12.size() > 0) {
					vertexRegion[3] = text12.get(text12.size() - 1);
				}
			}
		}

		return vertexRegion;
	}

	//text12～text41繋げる関数
	public static String connect_string(ArrayList<String> text12, ArrayList<String> text23, ArrayList<String> text34,
			ArrayList<String> text41) {

		//1,2,3,4も加えるので+4
		int text1234StringArraySize = text12.size() + text23.size() + text34.size() + text41.size() + 4;
		int i = 0;
		int n = 0;

		String text1234String = null;
		String[] text1234StringArray = new String[text1234StringArraySize];
		StringBuilder builder = new StringBuilder();

		text1234StringArray[0] = "1";
		text1234StringArray[text12.size() + 1] = "2";
		text1234StringArray[text12.size() + text23.size() + 2] = "3";
		text1234StringArray[text12.size() + text23.size() + text34.size() + 3] = "4";

		for (i = 1; i < text1234StringArraySize; i++) {

			if (i < text12.size() + 1) {
				for (n = 0; n < text12.size(); n++, i++) {
					text1234StringArray[i] = text12.get(n);
				}
			}

			if (text12.size() + 1 < i && i < text12.size() + text23.size() + 2) {
				for (n = 0; n < text23.size(); n++, i++) {
					text1234StringArray[i] = text23.get(n);
				}
			}

			if (text12.size() + text23.size() + 2 < i && i < text12.size() + text23.size() + text34.size() + 3) {
				for (n = 0; n < text34.size(); n++, i++) {
					text1234StringArray[i] = text34.get(n);
				}
			}

			if (text12.size() + text23.size() + text34.size() + 3 < i
					&& i <= text12.size() + text23.size() + text34.size() + text41.size() + 4) {
				for (n = 0; n < text41.size(); n++, i++) {
					text1234StringArray[i] = text41.get(n);
				}
			}
		}

		//確認用
		/*System.out.println("-text12, text23, text34, text41-");
		System.out.printf("%s,%s,%s,%s\n", text12, text23, text34, text41);
		System.out.println("-text1234StringArray(変化後)-");
		System.out.println(Arrays.toString(text1234StringArray));*/

		for (String element : text1234StringArray) {
			builder.append(element); // 要素を結合
		}

		text1234String = builder.toString(); // Builderを文字列に変換

		return text1234String;
	}

	//curveStringを書き換える、typeはボタンのタイプ
	public static String change_curve_string(String curveString, int type) {

		String curveStringCopy = curveString;

		//回転
		if (type == 2) {
			//curveString書き換え
			if (curveString.equals("1")) {
				curveStringCopy = "2";
			} else if (curveString.equals("2")) {
				curveStringCopy = "3";
			} else if (curveString.equals("3")) {
				curveStringCopy = "4";
			} else if (curveString.equals("4")) {
				curveStringCopy = "1";
			}
		}

		//左右反転
		else if (type == 3) {
			if (curveString.equals("1")) {
				curveStringCopy = "2";
			} else if (curveString.equals("2")) {
				curveStringCopy = "1";
			} else if (curveString.equals("3")) {
				curveStringCopy = "4";
			} else if (curveString.equals("4")) {
				curveStringCopy = "3";
			}
		}

		//上下反転
		else if (type == 4) {
			if (curveString.equals("1")) {
				curveStringCopy = "4";
			} else if (curveString.equals("2")) {
				curveStringCopy = "3";
			} else if (curveString.equals("3")) {
				curveStringCopy = "2";
			} else if (curveString.equals("4")) {
				curveStringCopy = "1";
			}
		}
		return curveStringCopy;
	}
}

//-----------------同一、連続性判定関連の関数をまとめたクラス----------------------
//,succesion
class Iden_succ_func {
	
	//同一性、連続性の判定結果をまとめる関数
	public static int[] check_iden_succ(String text1234StringL, String curveStringL, 
											String text1234StringR, String curveStringR) {
		//[同一性、連続性1、連続性2], 0:未調査, -1:条件満たさない, 1:条件満たす
		int[] checkIdenSucc = {-1,0,0};
		
		//繋がる辺の組
		ArrayList<ArrayList<Integer>> connectionString = new ArrayList<>(find_connection_string(text1234StringL, text1234StringR));
		//連続性を満たす記号列の組
		ArrayList<ArrayList<String>> succString = new ArrayList<>(find_succ_string(text1234StringL, curveStringL, text1234StringR, curveStringR));
		
		//同一性(満たす場合)
		if(check_iden(text1234StringL, text1234StringR) == true) {
			checkIdenSucc[0] = 1;
			
			//連続性1(接続辺に全ての端点)
			if(connectionString.size() != 0) {checkIdenSucc[1] = 1;}
			else if(connectionString.size() == 0) {checkIdenSucc[1] = -1;}
			
			//連続性2(曲がる方向)
			if(succString.size() != 0) {checkIdenSucc[2] = 1;}
			else if(succString.size() == 0) {checkIdenSucc[2] = -1;}
		}
		//同一性(満たさない場合)
		else if(check_iden(text1234StringL, text1234StringR) == false) {checkIdenSucc[0] = -1;}
		
		return checkIdenSucc;
	}
	
	//2つの文字列が同一性を持つか判定する関数
	public static boolean check_iden(String text1234StringL, String text1234StringR) {
		
		boolean equal = false;
		
		int i = 0;
		int type = 0; //0:未調査, -1:偽, 1:真
		
		ArrayList<String> palindrome1R = find_iden_string(text1234StringR);
		ArrayList<String> palindrome1L = find_iden_string(text1234StringL);
		
		ArrayList<String> palindrome1LReverse = new ArrayList<>(palindrome1L);
		
		Collections.reverse(palindrome1LReverse);
		
		if (palindrome1R == null ||  palindrome1L == null) {type = -1;}
		
		if (palindrome1R.size() != palindrome1L.size()) {type = -1;}
		
		if(type != 1) {	
			for (i = 0; i < palindrome1R.size(); i++) {
				//昇順にチェック
	    		if (Objects.equals(palindrome1R.get(i), palindrome1L.get(i))) {
	    			type = 1;
	    		}
	    		else {
	    			type = -1;
	    			break;
	    		}
			} 
		}
		
		if(type != 1) {
			for (i = 0; i < palindrome1R.size(); i++) {
				//逆順にチェック
	    		if (Objects.equals(palindrome1R.get(i), palindrome1LReverse.get(i))) {
	    			type = 1;
	    		}
	    		else {
	    			type = -1;
	    			break;
	    		}
			}
	    }
		
		if(type == -1) {equal = false;}
		if(type == 1) {equal = true;}
		
		return equal;
	}
	
	//連続性チェックして回転後の記号列(文字列+曲がる方向)を出力する
	public static ArrayList<ArrayList<String>> find_succ_string(String text1234StringL, String curveStringL, 
															String text1234StringR, String curveStringR) {

		//回転後の記号列
		ArrayList<ArrayList<String>> succStringArray = new ArrayList<>();
		ArrayList<String> succStringPair = new ArrayList<>();
		
		//同一性チェック
		boolean checkIden = check_iden(text1234StringL, text1234StringR);
		boolean checkCurve = false;
		
		//スピンタイプ
		ArrayList<ArrayList<Integer>> spinTypeArray = new ArrayList<>();
		
		String spinStringR, spinStringL, spinCurveR, spinCurveL;
		
		int i = 0;
		
		//同一性を満たす場合のみ行う
		if(checkIden == true) {
			spinTypeArray =  find_connection_string(text1234StringL, text1234StringR);
			//System.out.printf("spintypeArray:%d\n", spinTypeArray);
			
			for(i = 0; i < spinTypeArray.size(); i++) {
				
				//曲がる方向を変える(回転させる)
				spinCurveL = spin_connection_curve(curveStringL, spinTypeArray.get(i).get(0), "left");
				spinCurveR = spin_connection_curve(curveStringR, spinTypeArray.get(i).get(1), "right");
				
				//変えた方向に関して連続性判定
				checkCurve = check_hori_succ_curve(spinCurveL, spinCurveR);
				
				//連続性を満たすとき
				if(checkCurve == true) {
					
					//文字列を変える(回転させる)
					spinStringL = spin_connection_string(text1234StringL, spinTypeArray.get(i).get(0), "left");
					spinStringR = spin_connection_string(text1234StringR, spinTypeArray.get(i).get(1), "right");
					
					//1次元配列に格納
					succStringPair = new ArrayList<>();
					succStringPair.add(spinStringL);
					succStringPair.add(spinCurveL);
					succStringPair.add(spinStringR);
					succStringPair.add(spinCurveR);
					
					//2次元配列に格納
					succStringArray.add(succStringPair);
				}
			}
		}
		
		return succStringArray;
	}
	
	//接続辺に合わせて文字列を回転させる
	public static String spin_connection_string(String text1234String, int spinType, String rightleft){
		
		String text1234StringSpin = null;
		String text1234StringSpinCopy = new String(text1234String);
		
		int spinCount = 0;
		
		if(rightleft.equals("right")) {
			if(spinType == 1) {spinCount = 3;}
			if(spinType == 2) {spinCount = 2;}
			if(spinType == 3) {spinCount = 1;}
			if(spinType == 4) {spinCount = 0;}
		}
		
		if(rightleft.equals("left")) {
			if(spinType == 1) {spinCount = 1;}
			if(spinType == 2) {spinCount = 0;}
			if(spinType == 3) {spinCount = 3;}
			if(spinType == 4) {spinCount = 2;}
		}
		
		for(int i = 0; i < spinCount; i++) {
			
			if(i == 0) {
				text1234StringSpin = Operation_func.spin_string(text1234String);
			}
			if(i > 0) {
				text1234StringSpin = Operation_func.spin_string(text1234StringSpinCopy);
			}
			
			text1234StringSpinCopy = new String (text1234StringSpin);
		}
		
		return text1234StringSpinCopy;
	}
	
	//接続辺に合わせて曲がる方向を回転させる
	public static String spin_connection_curve(String curveString, int spinType, String rightleft){
		
		String curveStringSpin = null;
		String curveStringSpinCopy = new String(curveString);
		
		int spinCount = 0;
		
		if(rightleft.equals("right")) {
			if(spinType == 1) {spinCount = 3;}
			if(spinType == 2) {spinCount = 2;}
			if(spinType == 3) {spinCount = 1;}
			if(spinType == 4) {spinCount = 0;}
		}
		
		if(rightleft.equals("left")) {
			if(spinType == 1) {spinCount = 1;}
			if(spinType == 2) {spinCount = 0;}
			if(spinType == 3) {spinCount = 3;}
			if(spinType == 4) {spinCount = 2;}
		}
		
		for(int i = 0; i < spinCount; i++) {
			
			if(i == 0) {
				curveStringSpin = Operation_func.change_curve_string(curveString, 2);
			}
			if(i > 0) {
				curveStringSpin = Operation_func.change_curve_string(curveStringSpinCopy, 2);
			}
			
			curveStringSpinCopy = new String (curveStringSpin);	
		}
		
		return curveStringSpinCopy;
	}
	
	
	
	//文字列から層全体の隣接関係を求める関数
	public static ArrayList<String> find_iden_string(String text1234String){
		
		ArrayList<String> idenString = new ArrayList<>();
		
		ArrayList<String> palindrome1 = Validity_func.find_palindrome(text1234String, 0);
		ArrayList<String> onlyOneSymbol = new ArrayList<>();
		ArrayList<Integer> onlyOneSymbolPoint = new ArrayList<>();
		
		String_func.find_only_one_symbol_point(text1234String, onlyOneSymbol, onlyOneSymbolPoint);
		
		if(onlyOneSymbol.size() == 2) {
			
			idenString.add(onlyOneSymbol.get(0));
			idenString.addAll(palindrome1);
			idenString.add(onlyOneSymbol.get(1));
		}
		
		//確認用
		//System.out.println("-idenString-");
		//System.out.println(idenString);

		return idenString;
	}
	
	//文字列で連続性のある可能性がある辺を出力(回転も考慮)(解が複数ある場合は複数出力)
	public static ArrayList<ArrayList<Integer>> find_connection_string(String text1234StringL, String text1234StringR){
		
		ArrayList<ArrayList<Integer>> connectionSide = new ArrayList<>();
		
		//接続する可能性のある辺を格納する配列
		ArrayList<Integer> connectionR = new ArrayList<>();
		ArrayList<Integer> connectionL = new ArrayList<>();
		
		ArrayList<String> text12R = new ArrayList<>();
		ArrayList<String> text23R = new ArrayList<>();
		ArrayList<String> text34R = new ArrayList<>();
		ArrayList<String> text41R = new ArrayList<>();
		
		ArrayList<String> text12L = new ArrayList<>();
		ArrayList<String> text23L = new ArrayList<>();
		ArrayList<String> text34L = new ArrayList<>();
		ArrayList<String> text41L = new ArrayList<>();
		
		//String_func.find_cut_string(text1234StringR, text12R, text23R, text34R, text41R);
		find_each_side_strata(text1234StringR, text12R, text23R, text34R, text41R);
		
		//String_func.find_cut_string(text1234StringL, text12L, text23L, text34L, text41L);
		find_each_side_strata(text1234StringL, text12L, text23L, text34L, text41L);
		
		//隣接関係を求める関数
		ArrayList<String> palindrome1R = find_iden_string(text1234StringR);
		ArrayList<String> palindrome1L = find_iden_string(text1234StringL);
		
		//文字列比較時に一時的に文字列を格納する配列
		ArrayList<String> textL = new ArrayList<>();
		ArrayList<String> textR = new ArrayList<>();
		
		if(text12R.size() == palindrome1R.size()) {connectionR.add(1);}
		if(text23R.size() == palindrome1R.size()) {connectionR.add(2);}
		if(text34R.size() == palindrome1R.size()) {connectionR.add(3);}
		if(text41R.size() == palindrome1R.size()) {connectionR.add(4);}
		
		if(text12L.size() == palindrome1L.size()) {connectionL.add(1);}
		if(text23L.size() == palindrome1L.size()) {connectionL.add(2);}
		if(text34L.size() == palindrome1L.size()) {connectionL.add(3);}
		if(text41L.size() == palindrome1L.size()) {connectionL.add(4);}
		
		for(int l = 0; l < connectionL.size(); l++) {
			
			for(int r = 0; r < connectionR.size(); r++) {
				
				textR.clear();
				textL.clear();
				
				if(connectionR.get(r) == 1) {textR.addAll(text12R);}
				if(connectionR.get(r) == 2) {textR.addAll(text23R);}
				if(connectionR.get(r) == 3) {textR.addAll(text34R);}
				if(connectionR.get(r) == 4) {textR.addAll(text41R);}
				
				if(connectionL.get(l) == 1) {textL.addAll(text12L);}
				if(connectionL.get(l) == 2) {textL.addAll(text23L);}
				if(connectionL.get(l) == 3) {textL.addAll(text34L);}
				if(connectionL.get(l) == 4) {textL.addAll(text41L);}
				
				Collections.reverse(textR);

				if(textR.equals(textL)) {
					//二次元配列で[Lの接続辺,Rの接続辺]を格納する
					connectionSide.add(new ArrayList<Integer>(Arrays.asList(connectionL.get(l), connectionR.get(r))));
				}
				
				//確認用
				//System.out.println("-textL,R-");
				//System.out.printf("%s, %s\n", textL, textR);
			}
		}
		
		//確認用
		//System.out.println("-connectionSide-");
		//System.out.println(connectionSide);
		
		return connectionSide;
	}
	
	//文字列から指定した辺の隣接関係を求める関数
	/*public static ArrayList<String> find_connection_string(String text1234String, String side){
		
		ArrayList<String> connectionString = new ArrayList<>();
		
		//関数呼び出し
		String[] symbolOnly = String_func.find_symbol_only(text1234String);
		
		ArrayList<String> text12 = new ArrayList<>();
		ArrayList<String> text23 = new ArrayList<>();
		ArrayList<String> text34 = new ArrayList<>();
		ArrayList<String> text41 = new ArrayList<>();

		String_func.find_cut_string(text1234String, text12, text23, text34, text41);

		//上辺(辺12, top)
		if(side.equals("top")) {
			connectionString.add(symbolOnly[symbolOnly.length-1]);
			connectionString.addAll(text12);
			connectionString.add(symbolOnly[text12.size()]);
		}
		
		//右辺(辺23, right)
		if(side.equals("right")) {	
			//text12が存在しない場合
			if(text12.size() == 0) {
				connectionString.add(symbolOnly[symbolOnly.length-1]);
			}
			else{
				connectionString.add(symbolOnly[text12.size()]);
			}
			
			connectionString.addAll(text23);
			connectionString.add(symbolOnly[text12.size()+text23.size()]);
		}
		
		//下辺(辺34, bottom)
		if(side.equals("bottom")) {
			//text12,text23が存在しない場合
			if(text12.size() == 0 && text23.size() == 0) {
				connectionString.add(symbolOnly[symbolOnly.length-1]);
			}
			//text12が存在して、text23が存在しない場合
			else if(text12.size() != 0 && text23.size() == 0) {
				connectionString.add(symbolOnly[text12.size()]);
			}
			else{connectionString.add(symbolOnly[text12.size()]);}
			connectionString.addAll(text34);
			connectionString.add(symbolOnly[text12.size()+text23.size()+text34.size()]);
		}
		
		//左辺(辺41, left)
		if(side.equals("left")) {
			connectionString.add(symbolOnly[symbolOnly.length-1]);
			connectionString.addAll(text41);
			connectionString.add(symbolOnly[text12.size()]);
		}
		
		//確認用
		System.out.println("-connectionString-");
		System.out.println(connectionString);
		
		return connectionString;
	}*/
	
	//曲がり方の連続性判定
	public static boolean check_hori_succ_curve(String curveStringL, String curveStringR) {
		boolean succCurve = false;
		
		if(curveStringL == null || curveStringR == null) {
			succCurve = false;
			return succCurve;
		}
		
		//横の連続性だけ考えればよさそう
		else if(curveStringL.equals("1")) {
			if(curveStringR.equals("1") || curveStringR.equals("3")) {
				succCurve = true;
			}
		}
		
		else if(curveStringL.equals("2")) {
			if(curveStringR.equals("1") || curveStringR.equals("2") || curveStringR.equals("4")) {
				succCurve = true;
			}
		}
		
		else if(curveStringL.equals("3")) {
			if(curveStringR.equals("1") || curveStringR.equals("3") || curveStringR.equals("4")) {
				succCurve = true;
			}
		}
		
		else if(curveStringL.equals("4")) {
			if(curveStringR.equals("2") || curveStringR.equals("4")) {
				succCurve = true;
			}
		}
		
		return succCurve;
	}
	
	//各辺の隣接関係を出力する
	public static void find_each_side_strata(String text1234String,
			ArrayList<String> text12, ArrayList<String> text23,
			ArrayList<String> text34, ArrayList<String> text41) {
		
		String_func.find_cut_string(text1234String, text12, text23, text34, text41);
		
		String[] symbolOnly = String_func.find_symbol_only(text1234String);
		
		ArrayList<String> text12Copy = new ArrayList<>(text12);
		ArrayList<String> text23Copy = new ArrayList<>(text23);
		ArrayList<String> text34Copy = new ArrayList<>(text34);
		ArrayList<String> text41Copy = new ArrayList<>(text41);
		
		//順番に直前の辺の記号を先頭に加える
		//text12
		text12Copy.add(0,symbolOnly[symbolOnly.length-1]);
		
		//text23
		text23Copy.add(0,text12Copy.get(text12Copy.size()-1));
		
		//text34
		text34Copy.add(0,text23Copy.get(text23Copy.size()-1));
				
		//text41
		text41Copy.add(0,text34Copy.get(text34Copy.size()-1));
		
		//新しい値を元の引数にコピー
		text12.clear();
		text12.addAll(text12Copy);
		text23.clear();
		text23.addAll(text23Copy);
		text34.clear();
		text34.addAll(text34Copy);
		text41.clear();
		text41.addAll(text41Copy);
	}
	
	public static int find_spin_angle(String curveString, String curveStringChange) {
		
	int spinAngle = 0;
	
	//タイプ分け
	if(curveStringChange == null) {spinAngle = 0;}
	
	else{
		if(curveString.equals("1")) {
			if(curveStringChange.equals("1")) {spinAngle = 0;}
			if(curveStringChange.equals("2")) {spinAngle = 1;}
			if(curveStringChange.equals("3")) {spinAngle = 2;}
			if(curveStringChange.equals("4")) {spinAngle = 3;}
		}
		if(curveString.equals("2")) {
			if(curveStringChange.equals("1")) {spinAngle = 3;}
			if(curveStringChange.equals("2")) {spinAngle = 0;}
			if(curveStringChange.equals("3")) {spinAngle = 1;}
			if(curveStringChange.equals("4")) {spinAngle = 2;}
		}
		if(curveString.equals("3")) {
			if(curveStringChange.equals("1")) {spinAngle = 2;}
			if(curveStringChange.equals("2")) {spinAngle = 3;}
			if(curveStringChange.equals("3")) {spinAngle = 0;}
			if(curveStringChange.equals("4")) {spinAngle = 1;}
		}
		if(curveString.equals("4")) {
			if(curveStringChange.equals("1")) {spinAngle = 1;}
			if(curveStringChange.equals("2")) {spinAngle = 2;}
			if(curveStringChange.equals("3")) {spinAngle = 3;}
			if(curveStringChange.equals("4")) {spinAngle = 0;}
		}
		
	}
	
	return spinAngle;
	}
}
