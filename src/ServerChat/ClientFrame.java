package ServerChat;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;



   public class ClientFrame extends JFrame{
   private Image img; 
      JTextArea textArea; //멤버 참조변수
      Color chinablue = new Color(255,255,255);
      JTextField tfMsg;

      JButton btnSend;

      
      ImageIcon  icon = new ImageIcon("C:\\Users\\user\\eclipse-workspace\\ChattingTest2\\background2.jpg");
      Socket socket;

      DataInputStream dis;

      DataOutputStream dos;   
      boolean identify=false;
      boolean identify2=false;
     
      String id =null;
      int idchange=1;
      private JPanel panel;
     
    
      public ClientFrame() {
   
         
         setTitle("채팅창");

         setBounds(450, 400, 500, 350);
         getContentPane().setLayout(null);
          
         

         

         textArea = new JTextArea();
        // textArea.setBackground(chinablue);
          
              
       
         textArea.setEditable(false); //쓰기 금지

         JScrollPane scrollPane = new JScrollPane(textArea);
            
         
         scrollPane.setBounds(31, 30, 413, 232);

         getContentPane().add(scrollPane);

               

         JPanel msgPanel = new JPanel();
         msgPanel.setBounds(0, 288, 484, 23);

         msgPanel.setLayout(new BorderLayout());
         
    

     
            
         tfMsg = new JTextField();

         btnSend = new JButton("send");

         msgPanel.add(tfMsg, BorderLayout.CENTER);

         msgPanel.add(btnSend, BorderLayout.EAST);

         

         getContentPane().add(msgPanel);
         
         panel = new JPanel() {
                 Image background=new ImageIcon(("C:\\\\Users\\\\user\\\\eclipse-workspace\\\\ChattingTest2\\\\background2.jpg")).getImage();
             public void paint(Graphics g) {//그리는 함수
                   g.drawImage(background, 0, 0, null);//background를 그려줌      
             }
         };
         panel.setBounds(0, 0, 484, 290);
         getContentPane().add(panel);
    

         //send 버튼 클릭에 반응하는 리스너 추가

         btnSend.addActionListener(new ActionListener() {         

            @Override

            public void actionPerformed(ActionEvent e) {

               sendMessage();

            }

         });

         //엔터키 눌렀을 때 반응하기

         tfMsg.addKeyListener(new KeyAdapter() {

            //키보드에서 키 하나를 눌렀을때 자동으로 실행되는 메소드..: 콜백 메소드

            @Override

            public void keyPressed(KeyEvent e) {            

               super.keyPressed(e);

               

            //입력받은 키가 엔터인지 알아내기, KeyEvent 객체가 키에대한 정보 갖고있음

               int keyCode = e.getKeyCode();

               switch(keyCode) {

               case KeyEvent.VK_ENTER:

                  sendMessage();

                  break;

               }

            }

         });

         

         setVisible(true);

         tfMsg.requestFocus();

         

         //서버와 연결하는 네트워크 작업 : 스레드 객체 생성 및 실행

         ClientThread clientThread = new ClientThread();

         clientThread.setDaemon(true);

         clientThread.start();

         

         addWindowListener(new WindowAdapter() {         

            @Override //클라이언트 프레임에 window(창) 관련 리스너 추가

            public void windowClosing(WindowEvent e) {            

               super.windowClosing(e);

               try {

                  if(dos != null) dos.close();

                  if(dis != null) dis.close();

                  if(socket != null) socket.close();

               } catch (IOException e1) {               

                  e1.printStackTrace();

               }

            }         

         });

         

      }//생성자

      

      //이너클래스 : 서버와 연결하는 네트워크 작업 스레드

      class ClientThread extends Thread {

         @Override

         public void run() {

            try {

               socket = new Socket("211.225.89.28", 10001);

               textArea.append("서버에 접속됐습니다.\n");
              // textArea.append("아이디가 입력되지 않았습니다. #id를 입력해.\n");
               textArea.append("아이디를 입력해주세요.\n");

               //데이터 전송을 위한 스트림 생성(입추력 모두)

               InputStream is = socket.getInputStream();

               OutputStream os = socket.getOutputStream();

               //보조스트림으로 만들어서 데이터전송 작업을 편하게 ※다른 보조스트림 사용

               dis = new DataInputStream(is);

               dos = new DataOutputStream(os);   

               

               while(true) {//상대방 메시지 받기

                  String msg = dis.readUTF();

                  textArea.append(" [SERVER] : " + msg + "\n");

                  textArea.setCaretPosition(textArea.getText().length());

               }

            } catch (UnknownHostException e) {

               textArea.append("서버 주소가 이상합니다.\n");

            } catch (IOException e) {

               textArea.append("서버와 연결이 끊겼습니다.\n");

            }

         }

      }
      void ChatBot() {
         textArea.append("공부하고 싶은 언어를 입력하세요\n");
          textArea.append("1.자바\n");
          textArea.append("2.c언어\n");
          textArea.append("3.html\n");
          identify =true;
          identify2 =true;
      }
      
      void Ch2(int b) {
         switch(b) {
            case 1:
               textArea.append("자바를 공부하고 싶으시다면 앞에 #java를 쓰고 위 명령어를 입력하세요.\n");
               textArea.append("ex)출력문,입출력방법\n");
               break;
            case 2:
               textArea.append("c언어를 공부하고 싶으시다면 #c를 쓰고 위 명령어를 입력하세요.\n");
               textArea.append(" ex)입출력방법,출력문,출력타입\n");
               break;
            case 3:
               textArea.append("html를 공부하고 싶으시다면 #html를 쓰고 위 명령어를 입력하세요.\n");
               textArea.append(" ex)<style>,<br>\n");
               break;
               
            }
        

      }
      
      void Java(String msg2) {
         if(msg2.contains("출력문")) {
         textArea.append("출력문은 System.out.println(\"입력값\")으로 입력하시면 값이 출력됩니다.\n");
       //  textArea.append("\n");
         }
         if(msg2.contains("입출력방법")) {
            textArea.append("Scanner \"선언값\" = new Scanner(System.in)을 입력하고 ctrl+shift+o를 통해\n");
            textArea.append("임포트한 후 변수값에 \"변수 = 선언값.nextInt();\"을 입력한다.\n");
           //textArea.append("\n");
         }
         
         
      }
      
      void Clang(String msg2) {
         if(msg2.contains("출력문")) {
            textArea.append("출력문은 printf(\"출력타입\",선언값);쌍따옴표 안에 값은 선언값에 따라 다르게 써야합니다.\n");
            textArea.append("출력타입은 정수값은 %d, 실수값은 %f, 문자열은 %c String은 %s로 써야합니다. \n");
         //   textArea.append("\n");
            }
            if(msg2.contains("입출력방법")) {
               textArea.append("Scanf(\"출력타입\",선언값);입니다.\n");
               textArea.append("비주얼스튜디오에선 scanf를 사용할때 #include<stdio.h>밑에\n");
               textArea.append("CRT_SECURE_NO_WARNINGS;를 써야합니다.\n");
            //   textArea.append("\n");
            }
            if(msg2.contains("출력타입")) {
            textArea.append("출력타입은 Int값은 %d, float,double값은 %f, char은 %c String과 배열있는 char \n");
            textArea.append("은 %s로 써야합니다. 8진수는 %o, 16진수는 %x로 씁니다.  \n");
          //  textArea.append("\n");
            }
      }
      
      void Html(String msg2) {
            if(msg2.contains("<style>")) {
               textArea.append("<style> 요소는 문서나 문서 일부에 대한 스타일 정보를 포함합니다.\n");
               textArea.append("<style> 요소는 head안에 위치해야 한다.\n");
           //    textArea.append("\n");
               
            }
            
            if(msg2.contains("<br>")) {
               textArea.append("<br> 요소는 텍스트 안에 줄바꿈을 생성합니다.\n");
            }
         }
      
      void Professor() {
         textArea.append("교수님 성함은 정석태 교수님이야.\n");
      }
      

      //메시지 전송하는 기능 메소드

      void ChangeId(String msg2) {
        
        id=msg2;
     
       }

      //메시지 전송하는 기능 메소드

         void sendMessage() {   
            int a;
          
            String msg = tfMsg.getText(); //TextField에 써있는 글씨를 얻어오기
         String name =null;
          if(idchange==1) {
                name=msg;
               ChangeId(name);
               idchange=0;
            }   
          
            tfMsg.setText(""); //입력 후 빈칸으로

            textArea.append(id+":" + msg + "\n");//1.TextArea(채팅창)에 표시

            textArea.setCaretPosition(textArea.getText().length());
               if(msg.contains("성함")&&msg.contains("교수")) {
                 Professor();
               }
            if(msg.equals("#chatbot")) {
               ChatBot();    
            }
            
            
          
            if(identify==true) {
                  a =Integer.parseInt(msg);
                  Ch2(a);
                   identify=false;
                   
            }
            
            
           
            if(identify2==true) {
              
          if(msg.contains("#java")) {    
               if(msg.contains("출력문")) {
                  Java(msg);
               }
               else if(msg.contains("입출력방법")) {
                  Java(msg);
               }    
          }
          if(msg.contains("#c")) {    
             if(msg.contains("출력문")) {
                 Clang(msg);
             } 
             else if(msg.contains("입출력방법")) {
                Clang(msg);
             } 
             else if(msg.contains("출력타입")) {
                Clang(msg);
             } 
          }
          
              if(msg.contains("#html")) {
              if(msg.contains("<style>")) {
                 Html(msg);
              }
              else if(msg.contains("<br>")) {
                 Html(msg);
              }
           }
             
              
          
     }
          
      

            //2.상대방(Server)에게 메시지 전송하기

            //아웃풋 스트림을 통해 상대방에 데이터 전송

            //네트워크 작업은 별도의 Thread가 하는 것이 좋음

            Thread t = new Thread() {

               @Override

               public void run() {

                  try { //UTF = 유니코드의 규약(포맷), 한글 깨지지 않게 해줌

                     dos.writeUTF(msg);

                     dos.flush(); //계속 채팅 위해 close()하면 안됨            

                  } catch (IOException e) {

                     e.printStackTrace();

                  }

               }

            };

            t.start();         

         }
   }//class
   