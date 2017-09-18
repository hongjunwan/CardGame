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
	
	File path = new File(""); //���� �ҷ����� ����
	int RandomCard[] = new int [20]; // ī�� ���� ����
	ImageIcon imageIcon[]=new ImageIcon[20] ;  //�ո� �̹��� ������ 20�� ������
	
	//�г� 1����
	Panel start;
	JPanel screenpanel;		
	int score=0;  //���� �ʱ�ȭ
	int second=0;  //�ð� �ʱ�ȭ
	int card=20; //���� ī�尳�� �ʱ�ȭ
	JLabel labelScore;	//���� ��	
	JLabel labelTimer; //Ÿ�̸� ��	
	JLabel labelCard;//���� ī�� ����
	JButton StartBT;	 //���� ��ư
	Timer timersecond = new Timer(); //Ÿ�̸� ��ü �ʱ�ȭ

	JDialog Result;
	JLabel labelResult;
	JLabel labelResultScore;
	JButton buttonResult;
	
	//�г� 2����
	JPanel gamepanel;		
	ImageIcon imageBack ;			// �޸� �̹��� ����
	int startCheck=0;		// ���ӽ��� �˷��ִ� ���� (���ӽ��۽� 1,���� ���� ���ҽ� 0)
	JLabel labelImage[]=new JLabel[20] ;		// �̹��� ��Ÿ�� 20�� ��ư
	Timer MixTimer = new Timer();		// ī�� ���� �ӵ� ����
	Timer CardShowTimer = new Timer();		// ī�� �������� ������ �ð� ����
	Timer timerCardCheck = new Timer();		// ī�� 2���� �������� Ʋ���� ��� ���ʰ� �������� Ÿ�̸�
	JLabel ConfirmCardCheck;   	// ī�� ���ý� �Ǻ� (�Ȱ��� ī�� ��������)
	JLabel FirstCardSelect;		// ó�� ���õ� ī��
	JLabel SecondCardSelect;		// �ι�° ���õ� ī��
	int CardSelectCheck;		// ī�� ��� ���Ǵ��� üũ�ϱ�
	int FirstCardNumber;		// ù��° ���� ī�� ����
	int SecondCardNumber;	// �ι�° ���� ī�� ����
	int SelclectedImageNumber ;		// ���õǾ��� �̹����� ���� ����
	ImageIcon SelectedImage ;		// ���õǾ��� �̹��� �����ֱ�
	int GameScore=0;
	
	
	public CardMain(){
		
		super("ī�� ¦���߱� ����");
		
		screenpanel = new GameScreen(); //���� ȭ�� ���� �г�
		StartBT =new JButton("**START**");//���� ��ư �г� ����
		gamepanel = new CardGame();//ī�� ���� ���� �г� 
		
		add(StartBT,BorderLayout.NORTH);
		add(screenpanel, BorderLayout.SOUTH); // ���� ȭ�� ���� �г� ������ ��ġ
		add(gamepanel, BorderLayout.CENTER); // ī�� ���� ���� �г� �߾ӿ� ��ġ
		
		setSize(600,750); // ���� ȭ�� ũ��
		setVisible(true);  
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ���� ��ư
	}
		
		
//�г� 1
class GameScreen extends JPanel{		// ��ư, ��ī���
		
		public GameScreen(){
			
				setBackground(Color.red); // �� ���� ����
	            setLayout(new GridLayout(1,3,30,5));   // ���� ����
	        
	           
                
	        	labelTimer = new JLabel("�ð�:  "+second+" ��");	    // Ÿ�̸� �� ����
	            labelTimer.setFont(new Font("GOTHIC",Font.BOLD , 20));
	            labelTimer.setHorizontalAlignment(SwingConstants.RIGHT);
	    		
	            labelScore = new JLabel("���� :  "+score + "�� "); // ���� �� ����
	            labelScore.setFont(new Font("GOTHIC",Font.BOLD , 20));
	            labelScore.setHorizontalAlignment(SwingConstants.LEFT);
	            
	            labelCard = new JLabel("���� ī�� �� :  "+card + "�� "); //���� ī�� �� ����
	            labelCard.setFont(new Font("GOTHIC",Font.BOLD , 18));
	            labelCard.setHorizontalAlignment(SwingConstants.CENTER);
	            
	          
	    	
	         // �гο� �߰�
	        	add(labelScore);
	        	add(labelCard); 
	            add(labelTimer);
	            
		}
}

//�г� 2
class CardGame extends JPanel{		// ���� ���� 
	
	public CardGame(){
	    
		setBackground(Color.green); // ��� ��
		setLayout(new GridLayout(4,5,5,10)); // ���� ����
		
		//ó�� ���� �̹��� �߰� �� ȿ��
		
		CardMix();		// ī�弯��
		setImage();			 // �̹��� �޾ƿ���
		setButtonImage();	// ��ư�� �̹��� �����
		setButtonNumber();   // ��ư�� ���� �̸� ���̱�
		

		for(int i =0; i<20;i++){  //�� �̹����� ���콺 ��� �߰�
			labelImage[i].addMouseListener(new CardSelect());
		}
		
		for(int i =0; i<20;i++){ //�̹��� �߰� 
			add(labelImage[i]);
			}
		StartBT.addActionListener(new GamestartButton()); //���� ���� ��ư�� ��� �ֱ�
	}
}

class GamestartButton implements ActionListener {    //  ���� ���۹�ư
    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	setButtonNumber(); //��ư�� ���� ���
    	startCheck=1; //���� ���� Ȯ��
    	
    	second = 0; //�ð� �ʱ�ȭ
    	score = 0; //���� �ʱ�ȭ
    	GameScore=0;//���� ���� �ʱ�ȭ
    	card=20;//ī�� ���� �ʱ�ȭ
		labelTimer.setText("  "+second+" ��");			
		labelScore.setText("���� :  "+score + "�� ");	
		labelCard.setText("���� ī�� �� :  "+card + "�� ");
		
    	MixTimer.cancel();	
	  	MixTimer = new Timer();		// �� ���� Ÿ�̸� ��ü �ʱ�ȭ
	  	CardShowTimer.cancel();	
		CardShowTimer = new Timer();		// ī�� �������� Ÿ�̸� ��ü �ʱ�ȭ
      	timersecond.cancel();				// 
	  	timersecond = new Timer();    // Ÿ�̸� ��ü �ʱ�ȭ
	  	
    	MixTimer.scheduleAtFixedRate(new TimerTask() { 		// ī�� ���� ���
    		int i=0;
			public void run() {
				CardMix();               //ī�� ����
				setImage();              //�̹��� �޾ƿ���
				setButtonResetImage();   //��ư�� ���µ� �̹��� ����
				i=i+1;
				if(i==20)MixTimer.cancel();			// 20�� �������� Ÿ�̸� �����Ű��
			}
		}, 0,50);			// 0���� �����ؼ� 0.005�ʰ������� ����
			
    	
		CardShowTimer.scheduleAtFixedRate(new TimerTask() {		// ī�� 2�� ������ �� ������ ���
			public void run() { 
				HideImage(); //�޸� �̹��� 
				CardShowTimer.cancel(); //2�� �������� ������
			}
		}, 2000, 1); 		// 2���� ����
    	

    	timersecond.scheduleAtFixedRate(new TimerTask() { 		//  ���ӽ����� �ð���
    
			public  void run() { 
				labelTimer.setText("  "+second+" ��");	//�ð��� ǥ��
				second=second+1;   //�ð��� ����
			}
		}, 2000,1000);		// 2���� 1�� �������� ����
    
    }
 }

private class CardSelect implements MouseListener{		// �ΰ� ī�� Ȯ�� �� ��� ǥ��
	public void mousecondlicked(java.awt.event.MouseEvent e) {		
	}
	public void mouseEntered(java.awt.event.MouseEvent e) {
	}
	public void mouseExited(java.awt.event.MouseEvent e) {	
	}
	public void mousePressed(java.awt.event.MouseEvent e) {		
		ConfirmCardCheck = ((JLabel)e.getSource()); // ������ ī�� ���� (ī�� ���ý� �Ǻ��� ���� ���)
		
		if(ConfirmCardCheck.getName()=="success"){    // ���� ��ư�� success�̸� �̹� ¦�� ���� ī���̹Ƿ�
        	if(CardSelectCheck==0) //ó���� ������ ī�带 Ŭ�� ��������
        		CardSelectCheck =0; //�Ѱ��� ������ ���� �����̹Ƿ� üũ �ѹ��� 0����(ó�� ���·� �ٽ�)
        	else if(CardSelectCheck==1)//���� ī�带 �ϳ� ������ ������ ī�带 ���� �����ÿ���
        		CardSelectCheck=1; //�̹� �ϳ��� ������ ���� �̹Ƿ� üũ �ѹ��� 1��(1���� �� �����ϸ� �ǹǷ�)
		}
		else if(startCheck==1 && (CardSelectCheck==0 || CardSelectCheck==1)){  // ī�尡 ������ �������� �����ϱ�����
    		CardSelectCheck += 1;		 // ī�� ���� �ɶ����� ī��Ʈ  +1
    	// ù��° ���õ� ī�� ��ȣ
    		if(CardSelectCheck==1){		//ī�� 1�� ���ý� 
    		
    			FirstCardSelect=((JLabel)e.getSource());  // ù��° ���� ��ư��ü ��������
    			SelclectedImageNumber =Integer.parseInt(FirstCardSelect.getName()) -1;  // �̸��� string �̹Ƿ� int�� ��ȯ
    			SelectedImage = new ImageIcon(path +"pic/card/"+RandomCard[SelclectedImageNumber]+".jpg"); //�̹��� �ҷ�����
    			FirstCardSelect.setIcon(SelectedImage);     // ��ư ������ �̹��� �����ֱ�
    			FirstCardNumber = RandomCard[SelclectedImageNumber];	// ī�� ��ȣ�� 10���ϸ� �׳� ����
    			
    			if(RandomCard[SelclectedImageNumber]>10)			// ī�� ��ȣ�� 10���� ũ�� -10
    				FirstCardNumber = RandomCard[SelclectedImageNumber]-10;
    	}	// ù��° ���õ� ī�� ��ȣ

    	// �ι�° ���õ� ī�� ��ȣ
    		if(ConfirmCardCheck.getName()==FirstCardSelect.getName()){		// �ι�° Ŭ���� ó��Ŭ���� ī�带 �� ���������� ī��Ʈ0
    			CardSelectCheck =1;//�ٽ� üũ �ѹ� 1�� ���� 1�� �ʱ�ȭ ���� ���� ��� üũ �ѹ��� 3�� �Ǹ� ī�� ���ǽĿ� �����ϹǷ�
    		}
    		else if(CardSelectCheck==2	){		//ī�� 2�� ���ý�	
    			
    			
    			SecondCardSelect=((JLabel)e.getSource());  // �ι�° ���� ��ư��ü ��������
    			SelclectedImageNumber =Integer.parseInt(SecondCardSelect.getName()) -1;  // �̸��� string �̹Ƿ� int�� ��ȯ
    			SelectedImage = new ImageIcon(path +"pic/card/"+RandomCard[SelclectedImageNumber]+".jpg"); //�̹��� �ҷ�����
    			SecondCardSelect.setIcon(SelectedImage);     // ��ư ������ �̹��� �����ֱ�
    			SecondCardNumber = RandomCard[SelclectedImageNumber];	// ī�� ��ȣ�� 10���ϸ� �׳� ����
    			
    			if(RandomCard[SelclectedImageNumber]>10)				// ī�� ��ȣ�� 10���� ũ�� -10
    				SecondCardNumber = RandomCard[SelclectedImageNumber]-10;
    			
    			if(FirstCardNumber==SecondCardNumber ){   	// ù��° �ι�° ���õ� ī�� ��
					CardSelectCheck =0;		// ī�� �ΰ� ���õǸ� �ٽ� �ʱ�ȭ �ٸ� ī�带 ������ ����
					FirstCardSelect.setName("success"); // ���� ī�带 ã�����Ƿ� �̹��� ����(ī�� ���� ���ϰ� ����)
					SecondCardSelect.setName("success");// ���� ī�带 ã�����Ƿ� �̹��� ����(ī�� ���� ���ϰ� ����)
					GameScore += 10; //ī�� ����� 10����
					labelScore.setText("���� :  "+GameScore+" ��"); //�ǽð� ���� ��Ÿ����
					card=card-2; //ī�� ����� (2���� ���ߴ� ���̹Ƿ� -2)���� ī�� ���� ǥ��
					labelCard.setText("���� ī�� �� :  "+card + "�� ");//�ǽð� ���� ī�� ���� ǥ��
					
					
					if(card==0){//���� ī�� ������ 0�̸�
						GameScore = GameScore-second; //���������� ���� ���� �ð���
						timersecond.cancel();		//  Ÿ�̸� ����
						labelTimer.setText("  "+second+" ��");//����� ��Ȯ�� �ð� ��������	
						Result();//��� â �ҷ�����
						}
					}
				else{
					timerCardCheck = new Timer(); 
					timerCardCheck.scheduleAtFixedRate(new TimerTask() { 	//ù��° �ι�° ī����� Ʋ���� ���� �������� ���� 
	
						public void run() {
							CardSelectCheck =0;		// �ι�° ī�� ���õǸ� �ٽ�  �ʱ�ȭ 
							FirstCardSelect.setIcon(imageBack); // Ʋ�����Ƿ� ù��° ī�� �ٽ� ������
							SecondCardSelect.setIcon(imageBack);// Ʋ�����Ƿ� �ι�° ī�� �ٽ� ������
							timerCardCheck.cancel(); // ������ ������ �ð� �ٵǸ� Ÿ�̸� ����
						}
					}, 300,1);// 0.4���� ī�� �޸� ���̰� �ϱ�.
				} 
    		}
		}
	}// �ι�° ���õ� ī�� ��ȣ		
 
	public void mouseReleased(java.awt.event.MouseEvent e) {
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {				
    }
}
// ���� ���۹�ư

public void CardMix(){			// ī�� ����
	
	int i=0; //�ʱ�ȭ
	int rand=0; //�ʱ�ȭ
	while(true){
		
		rand =  (int)(Math.random()*20+1);//���� 0~19���� +1���� ���� ����
		RandomCard[i] =rand;  //RandomCard �迭�� ���� �ֱ�

  aa : for(int j=0; j<20; j++){			//ī��20�� ����
			if(j==i)
				break aa ;              //j��i������ aa for �� Ż��
			if(RandomCard[j]==rand){    
				/*randomcard�� �迭���� �ߺ��� ���ڸ� ������ �ʰ� �ϱ� ���� ����
				  ex}randomcard[1]�� 3�� ���ִµ� randomcard[5]���ʿ�
				     �� 3�� ���� randomcard[5]�� �տ� �迭���� ���¼��ڶ� �ٸ� ����
				   ���� ������ ��� randomcard[5]�ݺ�
				 */
				i=i-1 ;
				}
		}
		i=i+1;
		if(i==-1)i=0;
		if(i==20)break; //20�Ǹ� while�� Ż��
	}

}

public void setButtonNumber(){		// ��ư�� ���� �ֱ�
	
	for ( int i = 0; i<20; i++){
		 labelImage[i].setName(Integer.toString(i+1)); //�̹��� �迭�� �̸� �ֱ�
	}		
}

public void setImage(){				// �̹�����ü�� �׸� ��������
	
	imageBack = new ImageIcon(path +"pic/card/0.jpg"); // �޸� �̹��� �ҷ�����
	for ( int i = 0; i<20; i++){
		 imageIcon[i]= new ImageIcon(path +"pic/card/"+RandomCard[i]+".jpg");//���� ���� ��ġ �ҷ�����
	}
}

public void setButtonResetImage(){		// ��ư�� ���µ� �̹��� �缳���ϱ�

	for ( int i = 0; i<20; i++){
		 labelImage[i].setIcon(imageIcon[i]);
	}
}

public void HideImage(){		// ��ư �׸� �޸� ���̱�
	
	for ( int i = 0; i<20; i++){
		 labelImage[i].setIcon(imageBack);
	}	
}

public void setButtonImage(){		// ��ư ���� �� ó�� ������ �׸� �����ֱ�
	
	for ( int i = 0; i<20; i++){
		 labelImage[i] =  new JLabel(imageIcon[i]);
		 labelImage[i].setBackground(Color.green); 	// ����

	}	
}

void Result(){		// ��� â �����ֱ�	
		
	labelResult = new JLabel(); //��� �� ����
	labelResult.setHorizontalAlignment(NORMAL);
	labelResultScore = new JLabel(); //���� ��� �� ����
	labelResultScore.setHorizontalAlignment(NORMAL);
	
	Result = new JDialog(this, "���� ���"); //���̾�α� �̸�
	Result.setLayout(new GridLayout(3,1)); 
	Result.setBounds(230, 300, 200, 100);
			
	Result.add(labelResult); //�� �߰�
	Result.add(labelResultScore);
	//�гο� ��� �ؽ�Ʈ 
	labelResult.setText("ī�带 ��� ���߾����ϴ� !!");
	labelResultScore.setText("���� :  "+GameScore+" �� ȹ��");
	Result.setVisible(true);
	
	setDefaultCloseOperation(Result.EXIT_ON_CLOSE); //��� â ����
}


public static void main(String[] args) { // ����
	new CardMain();
}			
}

