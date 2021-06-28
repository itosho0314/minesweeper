package minesweeper;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GUI extends Application
{
	boolean clickFlag; //初クリックの管理フラグ
	boolean bombFlag; //地雷を踏んだかどうか判断するフラグ
	boolean clearFlag; //クリアされてるかどうか判断する。

	private AnchorPane startAp;
	
	private Button btn;

	private Label resultLabel = new Label();
	
	private Scene sc1 = null;
	private Scene sc2 = null;
	private Scene sc3 = null;
	
	private Stage st = new Stage();

	//MakeField mf = new MakeField(this);
	
	/*GuiContorollerを起動するためのmainメゾッド*/
	public static void main(String[] args)
	{
		Application.launch(args);
	}

	/*javafxの画面を作る*/
	@Override
	public void start(Stage stage) throws Exception
	{

		stage.setTitle("マインスイーパー");
		stage.setHeight(600);
		stage.setWidth(700);

		st = stage;

		setScene1();

		stage.setScene(sc1);
		stage.sizeToScene();
		stage.setResizable(false);
		stage.show();
	}

	/*スタート画面を表示するメゾッド*/
	public void setScene1()
	{
		
		startAp = new AnchorPane();
		startAp.setPrefSize( 200 , 100 );
		sc1 = new Scene( startAp );

		setScene2();

		/*各種ボタンの設定*/
		btn = new Button();
		AnchorPane.setTopAnchor(btn,   35.0);
		AnchorPane.setLeftAnchor(btn,  65.0);
		btn.setPrefSize( 70 , 30 );
		btn.setText("スタート");

		btn.setOnMouseClicked(event -> changeScene(st, sc2));

		startAp.getChildren().addAll(btn);
	}
	
	/*ゲーム画面を表示するメゾッド*/
	public void setScene2()
	{

		MakeField mf = new MakeField(this);
		clickFlag = false;
		bombFlag = false;
		AnchorPane startAp = new AnchorPane();
		startAp.setPrefSize( 500 , 500 );
		mf.setCell();
		sc2 = new Scene( startAp );
		startAp.getChildren().add( mf );
		
	}
	
	/*コンティニューorクリア画面を表示するメソッド*/
	public void setScene3(boolean isClear)
	{
		

		startAp = new AnchorPane();
		startAp.setPrefSize( 300 , 200 );
		sc3 = new Scene( startAp );

		Button btn = new Button();
		btn.setPrefSize( 100 , 50 );
		AnchorPane.setTopAnchor(btn,   100.0);
		AnchorPane.setLeftAnchor(btn,  100.0);

		if(!isClear) {
			resultLabel.setText("ゲーム失敗");
		}else{
			resultLabel.setText("ゲームクリア");
		}
		AnchorPane.setTopAnchor(resultLabel,   50.0);
		AnchorPane.setLeftAnchor(resultLabel,  115.0);
		btn.setText("タイトルへ");
		btn.setOnMouseClicked(event -> changeScene( st , sc1));
		startAp.getChildren().addAll(btn,resultLabel);
		changeScene(st,sc3);
	}

	/*場面転換用のメソッド*/
	private void changeScene(Stage st , Scene sc)
	{
		st.setScene(sc);
	    st.show();
	}

}
