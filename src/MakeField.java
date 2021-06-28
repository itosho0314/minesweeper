package minesweeper;

import java.util.Random;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import ponjan.CellManager;


public class MakeField extends AnchorPane{
	private int a,b,i,j; //ループ制御変数
	private int x = 10 ; //マスの横の範囲
	private int y = 10 ; //マスの縦の範囲

	boolean clickFlag; //初クリックの管理フラグ
	boolean bombFlag; //地雷を踏んだかどうか判断するフラグ
	boolean clearFlag; //クリアされてるかどうか判断する。
	
	private Button cell[][] = new Button[y][x];
	private Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

	private int bombsNum;
	int bombCell[] ;
	private CellManager[][] cm = new CellManager[y][x];
	private GUI gui;

	
	
	/*コンストラクタ*/
	public MakeField(GUI gui){
		this.gui = gui;
		this.bombsNum = 15;
		this.bombCell = new int[2];
	}
	
	/*ボタン作成する*/
	public void setCell() {
		/*マスを表示*/
		for(int i = 0; i < y; i++)
		{
			for(int j = 0; j < x; j++)
			{
				cell[i][j] = new Button();
				cell[i][j].setBackground(new Background(new BackgroundFill( Color.FORESTGREEN , CornerRadii.EMPTY , Insets.EMPTY )));
				cell[i][j].setPrefSize( 50 , 50 );
				setTopAnchor(cell[i][j], i * 50.0);
				setLeftAnchor(cell[i][j], j * 50.0);
				EventHandler<MouseEvent> Click = ( event ) -> this.click( event );
				cell[i][j].addEventHandler( MouseEvent.MOUSE_CLICKED , Click );
				cell[i][j].setBorder( border );
				cm[i][j] = new CellManager();
				getChildren().add(cell[i][j]);
			}
		}	
	}

	/*地雷を設置する*/
	public void setMine(int x, int y  )
	{
		int bombsCount = 0;
		Random r = new Random();
		
		while(true){
			for(int i = 0 ; i < 2 ; i++ ){
				bombCell[i] = r.nextInt(10);
			}
			/*すでにそのマスに地雷があるか判断する。*/
			if(cm[bombCell[0]][bombCell[1]].getBombFlag() ){
				continue;
			}
			/*地雷を配置していく*/
			cm[bombCell[0]][bombCell[1]].setBombFlag();
			cm[bombCell[0]][bombCell[1]].setClearFlag();
			bombsCount++;
			addAroundbomb(cm,x,y);
			if(bombsNum == bombsCount){
				break;
			}
		}
	}
	
	/*周りのマスに1追加する*/
	public void	addAroundbomb(CellManager[][] cm,int x ,int y) {
		/*地雷を配置していく*/
		for(int i = -1 ; i < 2 ; i++){
			for(int j = -1; j < 2; j++){
				if((bombCell[0] + i) < 0 || (x -1) < (bombCell[0] + i)){
					continue;
				}
				if((bombCell[1] + j) < 0 || (y -1) < (bombCell[1] + j)){
					continue;
				}
				cm[bombCell[0] + i][bombCell[1] + j].addAroundBombNum();
			}
		}
	}
	
	/*マスクリック後の挙動を制御するメソッド*/
	private void click( MouseEvent event)
	{

		switch(event.getButton())
		{
		 	case PRIMARY: //左クリック
		 		
		 		/*マスを開く*/
		 		for(i = 0 ; i < y ; i++)
		 		{
		 			for(j = 0; j < x ; j++)
		 			{
		 				if(event.getSource() == cell[i][j] && !cm[i][j].getOpenFlag())
		 				{
		 					if(!clickFlag) //初めてのクリックのとき。
							{
		 						clearFlag = false;
		 						cm[i][j].setBombFlag();
		 						setMine(x, y);
		 						cm[i][j].takeBombFlag();
		 						writeBombs();
		 			 			clickFlag = true;
							}
		 					openAroundCell( j , i );
		 				}
		 			}
		 		}break;

		 	case SECONDARY: //右クリック

		 		/*旗を建てる*/
				for(i = 0 ; i < y ; i++)
				{
					for(j = 0 ; j < x ; j++)
					{
						if(event.getSource() == cell[i][j])
						{
							if(!cm[i][j].getflagFlag() && !cm[i][j].getOpenFlag())
							{
								cm[i][j].setFlag();
								cell[i][j].setText("旗");
								cm[i][j].setOpenFlag();


							}
							 else if(cm[i][j].getflagFlag() && cm[i][j].getOpenFlag())
							 {
								 cm[i][j].takeFlag();
								 cell[i][j].setText("");
								 cm[i][j].takeOpenFlag();

							 }
						}
					}
				} break;

		 	default:
				break;
		}
	}

	/*デバック用*/
	private void writeBombs()
	{
		for( a = 0 ; a < y ; a++)
 		{
 			for( b = 0; b < x ; b++)
 			{
 				if(cm[a][b].getBombFlag())
 				{
 					cell[a][b].setText("爆");
 				}
 			}
 		}
	}

	/*マスの周りを開くメソッド*/
	private void openAroundCell( int n, int m)
	{
		if(cm[m][n].getAroundBombNum() == 0){
			if(!cm[m][n].getOpenFlag()){
				openCell( n , m );
					for(int a=-1;a<2;a++){
						for(int b=-1;b<2;b++){
							if( m+a < 0 || y - 1 <  m+a )
								continue;
							if( n+b < 0 || x - 1 <  n+b )
								continue;
							if(	!clearFlag)
								openAroundCell(n+b, m+ a);
							else
								break;
						}
					}
				}
			}
		else{
			openCell( n , m );
		}
	}

	/*マスを開くメソッド*/
	private void openCell( int n, int m)
	{
		if(!cm[m][n].getBombFlag() ){
			if(!cm[m][n].getOpenFlag()){
				if(!(cm[m][n].getAroundBombNum() == 0)){
					cell[m][n].setText(String.valueOf(cm[m][n].getAroundBombNum()));
					cell[m][n].setTextFill(Color.RED);
				}
				cm[m][n].setOpenFlag();
				cell[m][n].setBackground(new Background(new BackgroundFill( Color.WHITE , CornerRadii.EMPTY , Insets.EMPTY )));
				cm[m][n].setClearFlag();
				gameEnd();
			}
		}
		 else{
			 bombFlag = true;
			 gameEnd();
		 }
	}

	/*ゲームを終わらせるメソッド*/
	private void gameEnd()
	{
		int end = 0;
		if(bombFlag){
			gui.setScene3(false);
			gui.setScene2();
		}else{
			 for(i = 0 ; i < y ; i++){
					for(j = 0 ; j < x ; j++){
						if(cm[i][j].getClearFlag()){
							end++;
							if(end == x * y){
								clearFlag = true;
								gui.setScene3(true);
								gui.setScene2();
								
							}
						}
					}
				}
		 }

	}

}
