package javaproject;

import java.awt.*;
import javax.swing.*;

import java.util.*;
import java.util.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;


public class CardMain extends JFrame{
	
	File path = new File(""); //파일 불러오기 위한
	int RandomCard[] = new int [20]; // 카드 숫자 저장
	ImageIcon imageIcon[]=new ImageIcon[20] ;  //앞면 이미지 저장할 20개 아이콘
	
	//패널 1구성
	Panel start;
	JPanel screenpanel;		
	int score=0;  //점수 초기화
	int second=0;  //시간 초기화
	int card=20; //남은 카드개수 초기화
	JLabel labelScore;	//점수 라벨	
	JLabel labelTimer; //타이머 라벨	
	JLabel labelCard;//남은 카드 개수
	JButton StartBT;	 //시작 버튼
	Timer timersecond = new Timer(); //타이머 객체 초기화

	JDialog Result;
	JLabel labelResult;
	JLabel labelResultScore;
	JButton buttonResult;
	
	//패널 2구성
	JPanel gamepanel;		
	ImageIcon imageBack ;			// 뒷면 이미지 저장
	int startCheck=0;		// 게임시작 알려주는 변수 (게임시작시 1,게임 시작 안할시 0)
	JLabel labelImage[]=new JLabel[20] ;		// 이미지 나타낼 20개 버튼
	Timer MixTimer = new Timer();		// 카드 섞는 속도 조절
	Timer CardShowTimer = new Timer();		// 카드 뒤집기전 보여줄 시간 조절
	Timer timerCardCheck = new Timer();		// 카드 2개를 뒤집고나서 틀렸을 경우 몇초간 보여줄지 타이머
	JLabel ConfirmCardCheck;   	// 카드 선택시 판별 (똑같은 카드 누른건지)
	JLabel FirstCardSelect;		// 처음 선택된 카드
	JLabel SecondCardSelect;		// 두번째 선택된 카드
	int CardSelectCheck;		// 카드 몇번 눌렷는지 체크하기
	int FirstCardNumber;		// 첫번째 눌린 카드 저장
	int SecondCardNumber;	// 두번째 눌린 카드 저장
	int SelclectedImageNumber ;		// 선택되어진 이미지의 숫자 저장
	ImageIcon SelectedImage ;		// 선택되어진 이미지 보여주기
	int GameScore=0;
	
	
	public CardMain(){
		
		super("카드 짝맞추기 게임");
		
		screenpanel = new GameScreen(); //게임 화면 구성 패널
		StartBT =new JButton("**START**");//시작 버튼 패널 구성
		gamepanel = new CardGame();//카드 게임 구성 패널 
		
		add(StartBT,BorderLayout.NORTH);
		add(screenpanel, BorderLayout.SOUTH); // 게임 화면 구성 패널 밑으로 배치
		add(gamepanel, BorderLayout.CENTER); // 카드 게임 구성 패널 중앙에 배치
		
		setSize(600,750); // 게임 화면 크기
		setVisible(true);  
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 종료 버튼
	}
		
		
//패널 1
class GameScreen extends JPanel{		// 버튼, 라벨카드널
		
		public GameScreen(){
			
				setBackground(Color.red); // 라벨 색깔 설정
	            setLayout(new GridLayout(1,3,30,5));   // 간격 설정
	        
	           
                
	        	labelTimer = new JLabel("시간:  "+second+" 초");	    // 타이머 라벨 구성
	            labelTimer.setFont(new Font("GOTHIC",Font.BOLD , 20));
	            labelTimer.setHorizontalAlignment(SwingConstants.RIGHT);
	    		
	            labelScore = new JLabel("점수 :  "+score + "점 "); // 점수 라벨 구성
	            labelScore.setFont(new Font("GOTHIC",Font.BOLD , 20));
	            labelScore.setHorizontalAlignment(SwingConstants.LEFT);
	            
	            labelCard = new JLabel("남은 카드 수 :  "+card + "개 "); //남은 카드 라벨 구성
	            labelCard.setFont(new Font("GOTHIC",Font.BOLD , 18));
	            labelCard.setHorizontalAlignment(SwingConstants.CENTER);
	            
	          
	    	
	         // 패널에 추가
	        	add(labelScore);
	        	add(labelCard); 
	            add(labelTimer);
	            
		}
}

//패널 2
class CardGame extends JPanel{		// 게임 구현 
	
	public CardGame(){
	    
		setBackground(Color.green); // 배경 색
		setLayout(new GridLayout(4,5,5,10)); // 간격 설정
		
		//처음 실행 이미지 추가 및 효과
		
		CardMix();		// 카드섞기
		setImage();			 // 이미지 받아오기
		setButtonImage();	// 버튼에 이미지 씌우기
		setButtonNumber();   // 버튼에 각각 이름 붙이기
		

		for(int i =0; i<20;i++){  //각 이미지에 마우스 사건 추가
			labelImage[i].addMouseListener(new CardSelect());
		}
		
		for(int i =0; i<20;i++){ //이미지 추가 
			add(labelImage[i]);
			}
		StartBT.addActionListener(new GamestartButton()); //게임 시작 버튼에 사건 넣기
	}
}

class GamestartButton implements ActionListener {    //  게임 시작버튼
    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	setButtonNumber(); //버튼에 숫자 등록
    	startCheck=1; //게임 시작 확인
    	
    	second = 0; //시간 초기화
    	score = 0; //점수 초기화
    	GameScore=0;//게임 점수 초기화
    	card=20;//카드 개수 초기화
		labelTimer.setText("  "+second+" 초");			
		labelScore.setText("점수 :  "+score + "점 ");	
		labelCard.setText("남은 카드 수 :  "+card + "개 ");
		
    	MixTimer.cancel();	
	  	MixTimer = new Timer();		// 패 섞는 타이머 객체 초기화
	  	CardShowTimer.cancel();	
		CardShowTimer = new Timer();		// 카드 뒤집기전 타이머 객체 초기화
      	timersecond.cancel();				// 
	  	timersecond = new Timer();    // 타이머 객체 초기화
	  	
    	MixTimer.scheduleAtFixedRate(new TimerTask() { 		// 카드 섞는 모션
    		int i=0;
			public void run() {
				CardMix();               //카드 섞기
				setImage();              //이미지 받아오기
				setButtonResetImage();   //버튼에 리셋된 이미지 설정
				i=i+1;
				if(i==20)MixTimer.cancel();			// 20번 섞었으면 타이머 종료시키기
			}
		}, 0,50);			// 0초후 실행해서 0.005초간격으로 섞기
			
    	
		CardShowTimer.scheduleAtFixedRate(new TimerTask() {		// 카드 2초 보여준 후 뒤집는 모션
			public void run() { 
				HideImage(); //뒷면 이미지 
				CardShowTimer.cancel(); //2초 보여준후 뒤집기
			}
		}, 2000, 1); 		// 2초후 실행
    	

    	timersecond.scheduleAtFixedRate(new TimerTask() { 		//  게임시작후 시간초
    
			public  void run() { 
				labelTimer.setText("  "+second+" 초");	//시간초 표시
				second=second+1;   //시간초 증가
			}
		}, 2000,1000);		// 2초후 1초 간격으로 실행
    
    }
 }

private class CardSelect implements MouseListener{		// 두개 카드 확인 및 결과 표시
	public void mousecondlicked(java.awt.event.MouseEvent e) {		
	}
	public void mouseEntered(java.awt.event.MouseEvent e) {
	}
	public void mouseExited(java.awt.event.MouseEvent e) {	
	}
	public void mousePressed(java.awt.event.MouseEvent e) {		
		ConfirmCardCheck = ((JLabel)e.getSource()); // 선택한 카드 저장 (카드 선택시 판별을 위해 사용)
		
		if(ConfirmCardCheck.getName()=="success"){    // 눌린 버튼이 success이면 이미 짝을 맞춘 카드이므로
        	if(CardSelectCheck==0) //처음에 성공한 카드를 클릭 했을때는
        		CardSelectCheck =0; //한개도 뒤집지 않은 상태이므로 체크 넘버는 0으로(처음 상태로 다시)
        	else if(CardSelectCheck==1)//만약 카드를 하나 뒤집고 성공한 카드를 선택 했을시에는
        		CardSelectCheck=1; //이미 하나는 선택한 상태 이므로 체크 넘버는 1로(1개만 더 선택하면 되므로)
		}
		else if(startCheck==1 && (CardSelectCheck==0 || CardSelectCheck==1)){  // 카드가 뒤집어 졌을때만 실행하기위해
    		CardSelectCheck += 1;		 // 카드 선택 될때마다 카운트  +1
    	// 첫번째 선택된 카드 번호
    		if(CardSelectCheck==1){		//카드 1개 선택시 
    		
    			FirstCardSelect=((JLabel)e.getSource());  // 첫번째 눌린 버튼객체 가져오기
    			SelclectedImageNumber =Integer.parseInt(FirstCardSelect.getName()) -1;  // 이름이 string 이므로 int로 변환
    			SelectedImage = new ImageIcon(path +"pic/card/"+RandomCard[SelclectedImageNumber]+".jpg"); //이미지 불러오기
    			FirstCardSelect.setIcon(SelectedImage);     // 버튼 눌려진 이미지 보여주기
    			FirstCardNumber = RandomCard[SelclectedImageNumber];	// 카드 번호가 10이하면 그냥 저장
    			
    			if(RandomCard[SelclectedImageNumber]>10)			// 카드 번호가 10보다 크면 -10
    				FirstCardNumber = RandomCard[SelclectedImageNumber]-10;
    	}	// 첫번째 선택된 카드 번호

    	// 두번째 선택된 카드 번호
    		if(ConfirmCardCheck.getName()==FirstCardSelect.getName()){		// 두번째 클릭이 처음클릭한 카드를 또 선택했으면 카운트0
    			CardSelectCheck =1;//다시 체크 넘버 1로 만듬 1로 초기화 하지 않을 경우 체크 넘버는 3이 되면 카드 조건식에 부합하므로
    		}
    		else if(CardSelectCheck==2	){		//카드 2개 선택시	
    			
    			
    			SecondCardSelect=((JLabel)e.getSource());  // 두번째 눌린 버튼객체 가져오기
    			SelclectedImageNumber =Integer.parseInt(SecondCardSelect.getName()) -1;  // 이름이 string 이므로 int로 변환
    			SelectedImage = new ImageIcon(path +"pic/card/"+RandomCard[SelclectedImageNumber]+".jpg"); //이미지 불러오기
    			SecondCardSelect.setIcon(SelectedImage);     // 버튼 눌려진 이미지 보여주기
    			SecondCardNumber = RandomCard[SelclectedImageNumber];	// 카드 번호가 10이하면 그냥 저장
    			
    			if(RandomCard[SelclectedImageNumber]>10)				// 카드 번호가 10보다 크면 -10
    				SecondCardNumber = RandomCard[SelclectedImageNumber]-10;
    			
    			if(FirstCardNumber==SecondCardNumber ){   	// 첫번째 두번째 선택된 카드 비교
					CardSelectCheck =0;		// 카드 두개 선택되면 다시 초기화 다른 카드를 뒤집기 위해
					FirstCardSelect.setName("success"); // 같은 카드를 찾았으므로 이미지 고정(카드 선택 못하게 만듬)
					SecondCardSelect.setName("success");// 같은 카드를 찾았으므로 이미지 고정(카드 선택 못하게 만듬)
					GameScore += 10; //카드 맞출시 10점씩
					labelScore.setText("점수 :  "+GameScore+" 점"); //실시간 점수 나타내기
					card=card-2; //카드 맞출시 (2개를 맞추는 것이므로 -2)남은 카드 개수 표시
					labelCard.setText("남은 카드 수 :  "+card + "개 ");//실시간 남은 카드 개수 표시
					
					
					if(card==0){//남은 카드 개수가 0이면
						GameScore = GameScore-second; //최종점수는 점수 빼기 시간초
						timersecond.cancel();		//  타이머 정지
						labelTimer.setText("  "+second+" 초");//종료시 정확한 시간 보기위해	
						Result();//결과 창 불러오기
						}
					}
				else{
					timerCardCheck = new Timer(); 
					timerCardCheck.scheduleAtFixedRate(new TimerTask() { 	//첫번째 두번째 카드비교후 틀리면 몇초 보여줄지 설정 
	
						public void run() {
							CardSelectCheck =0;		// 두번째 카드 선택되면 다시  초기화 
							FirstCardSelect.setIcon(imageBack); // 틀렸으므로 첫번째 카드 다시 뒤집기
							SecondCardSelect.setIcon(imageBack);// 틀렸으므로 두번째 카드 다시 뒤집기
							timerCardCheck.cancel(); // 뒤집고 설정된 시간 다되면 타이머 종료
						}
					}, 300,1);// 0.4초후 카드 뒷면 보이게 하기.
				} 
    		}
		}
	}// 두번째 선택된 카드 번호		
 
	public void mouseReleased(java.awt.event.MouseEvent e) {
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {				
    }
}
// 게임 시작버튼

public void CardMix(){			// 카드 섞기
	
	int i=0; //초기화
	int rand=0; //초기화
	while(true){
		
		rand =  (int)(Math.random()*20+1);//숫자 0~19에서 +1더한 숫자 저장
		RandomCard[i] =rand;  //RandomCard 배열에 숫자 넣기

  aa : for(int j=0; j<20; j++){			//카드20번 섞기
			if(j==i)
				break aa ;              //j와i같으면 aa for 문 탈출
			if(RandomCard[j]==rand){    
				/*randomcard각 배열마다 중복된 숫자를 만들지 않게 하기 위한 조건
				  ex}randomcard[1]에 3이 들어가있는데 randomcard[5]차례에
				     또 3이 들어가면 randomcard[5]에 앞에 배열에서 나온숫자랑 다른 값이
				   들어가기 전까지 계속 randomcard[5]반복
				 */
				i=i-1 ;
				}
		}
		i=i+1;
		if(i==-1)i=0;
		if(i==20)break; //20되면 while문 탈출
	}

}

public void setButtonNumber(){		// 버튼에 숫자 주기
	
	for ( int i = 0; i<20; i++){
		 labelImage[i].setName(Integer.toString(i+1)); //이미지 배열에 이름 주기
	}		
}

public void setImage(){				// 이미지객체에 그림 가져오기
	
	imageBack = new ImageIcon(path +"pic/card/0.jpg"); // 뒷면 이미지 불러오기
	for ( int i = 0; i<20; i++){
		 imageIcon[i]= new ImageIcon(path +"pic/card/"+RandomCard[i]+".jpg");//사진 파일 위치 불러오기
	}
}

public void setButtonResetImage(){		// 버튼에 리셋된 이미지 재설정하기

	for ( int i = 0; i<20; i++){
		 labelImage[i].setIcon(imageIcon[i]);
	}
}

public void HideImage(){		// 버튼 그림 뒷면 보이기
	
	for ( int i = 0; i<20; i++){
		 labelImage[i].setIcon(imageBack);
	}	
}

public void setButtonImage(){		// 버튼 설정 및 처음 가져온 그림 보여주기
	
	for ( int i = 0; i<20; i++){
		 labelImage[i] =  new JLabel(imageIcon[i]);
		 labelImage[i].setBackground(Color.green); 	// 배경색

	}	
}

void Result(){		// 결과 창 보여주기	
		
	labelResult = new JLabel(); //결과 라벨 구성
	labelResult.setHorizontalAlignment(NORMAL);
	labelResultScore = new JLabel(); //점수 결과 라벨 구성
	labelResultScore.setHorizontalAlignment(NORMAL);
	
	Result = new JDialog(this, "게임 결과"); //다이어로그 이름
	Result.setLayout(new GridLayout(3,1)); 
	Result.setBounds(230, 300, 200, 100);
			
	Result.add(labelResult); //라벨 추가
	Result.add(labelResultScore);
	//패널에 띄울 텍스트 
	labelResult.setText("카드를 모두 맞추었습니다 !!");
	labelResultScore.setText("점수 :  "+GameScore+" 점 획득");
	Result.setVisible(true);
	
	setDefaultCloseOperation(Result.EXIT_ON_CLOSE); //결과 창 종료
}


public static void main(String[] args) { // 실행
	new CardMain();
}			
}

