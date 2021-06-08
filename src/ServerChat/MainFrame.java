package ServerChat;


	
// TODO Auto-generated method stub
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;



public class MainFrame extends JFrame {
   public MainFrame() {

      setTitle("자바 5조 채팅프로그램");
      setResizable(false);
      setBounds(10, 50, 400, 250);
      
      
      setResizable(false);//창의 크기를 변경하지 못하게
       setLocationRelativeTo(null);//창이 가운데 나오게
      setDefaultCloseOperation(EXIT_ON_CLOSE);

      

      JPanel panel = new JPanel(){
           Image background=new ImageIcon(("C:\\Users\\user\\eclipse-workspace\\ChattingTest2\\mario.jpg")).getImage();
       public void paint(Graphics g) {//그리는 함수
             g.drawImage(background, 0, 0, null);//background를 그려줌      
       }
      };
     
    
     
      

      
      JButton btnServer = new JButton("Server");
      btnServer.setForeground(new Color(255, 255, 255));
      btnServer.setBackground(new Color(204, 153, 102));
      btnServer.setBounds(55, 37, 80, 23);
      
      
      
      JButton btnClient = new JButton("Client");
      btnClient.setForeground(new Color(255, 255, 255));
      btnClient.setBackground(new Color(204, 153, 102));
      btnClient.setBounds(243, 37, 80, 23);

      

      //버튼클릭 액션에 반응하기 위해 리스너 객체 생성 및 추가
      
      
      
      
      btnServer.addActionListener(new ActionListener() {         

         @Override

         public void actionPerformed(ActionEvent e) {

            //ServerFrame 객체 생성

            ServerFrame frame = new ServerFrame();

         }

      });

      btnClient.addActionListener(new ActionListener() {         

         @Override

         public void actionPerformed(ActionEvent e) {

            //ClientFrame 객체 생성

            ClientFrame frame = new ClientFrame();

         }

      });
      panel.setLayout(null);

      

      panel.add(btnServer);

      panel.add(btnClient);

      

      getContentPane().add(panel, BorderLayout.CENTER);

      

      setVisible(true);

   }



   public static void main(String[] args) {

      new MainFrame();

   }


}