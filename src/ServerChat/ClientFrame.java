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
      JTextArea textArea; //��� ��������
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
   
         
         setTitle("ä��â");

         setBounds(450, 400, 500, 350);
         getContentPane().setLayout(null);
          
         

         

         textArea = new JTextArea();
        // textArea.setBackground(chinablue);
          
              
       
         textArea.setEditable(false); //���� ����

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
             public void paint(Graphics g) {//�׸��� �Լ�
                   g.drawImage(background, 0, 0, null);//background�� �׷���      
             }
         };
         panel.setBounds(0, 0, 484, 290);
         getContentPane().add(panel);
    

         //send ��ư Ŭ���� �����ϴ� ������ �߰�

         btnSend.addActionListener(new ActionListener() {         

            @Override

            public void actionPerformed(ActionEvent e) {

               sendMessage();

            }

         });

         //����Ű ������ �� �����ϱ�

         tfMsg.addKeyListener(new KeyAdapter() {

            //Ű���忡�� Ű �ϳ��� �������� �ڵ����� ����Ǵ� �޼ҵ�..: �ݹ� �޼ҵ�

            @Override

            public void keyPressed(KeyEvent e) {            

               super.keyPressed(e);

               

            //�Է¹��� Ű�� �������� �˾Ƴ���, KeyEvent ��ü�� Ű������ ���� ��������

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

         

         //������ �����ϴ� ��Ʈ��ũ �۾� : ������ ��ü ���� �� ����

         ClientThread clientThread = new ClientThread();

         clientThread.setDaemon(true);

         clientThread.start();

         

         addWindowListener(new WindowAdapter() {         

            @Override //Ŭ���̾�Ʈ �����ӿ� window(â) ���� ������ �߰�

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

         

      }//������

      

      //�̳�Ŭ���� : ������ �����ϴ� ��Ʈ��ũ �۾� ������

      class ClientThread extends Thread {

         @Override

         public void run() {

            try {

               socket = new Socket("211.225.89.28", 10001);

               textArea.append("������ ���ӵƽ��ϴ�.\n");
              // textArea.append("���̵� �Էµ��� �ʾҽ��ϴ�. #id�� �Է���.\n");
               textArea.append("���̵� �Է����ּ���.\n");

               //������ ������ ���� ��Ʈ�� ����(���߷� ���)

               InputStream is = socket.getInputStream();

               OutputStream os = socket.getOutputStream();

               //������Ʈ������ ���� ���������� �۾��� ���ϰ� �شٸ� ������Ʈ�� ���

               dis = new DataInputStream(is);

               dos = new DataOutputStream(os);   

               

               while(true) {//���� �޽��� �ޱ�

                  String msg = dis.readUTF();

                  textArea.append(" [SERVER] : " + msg + "\n");

                  textArea.setCaretPosition(textArea.getText().length());

               }

            } catch (UnknownHostException e) {

               textArea.append("���� �ּҰ� �̻��մϴ�.\n");

            } catch (IOException e) {

               textArea.append("������ ������ ������ϴ�.\n");

            }

         }

      }
      void ChatBot() {
         textArea.append("�����ϰ� ���� �� �Է��ϼ���\n");
          textArea.append("1.�ڹ�\n");
          textArea.append("2.c���\n");
          textArea.append("3.html\n");
          identify =true;
          identify2 =true;
      }
      
      void Ch2(int b) {
         switch(b) {
            case 1:
               textArea.append("�ڹٸ� �����ϰ� �����ôٸ� �տ� #java�� ���� �� ��ɾ �Է��ϼ���.\n");
               textArea.append("ex)��¹�,����¹��\n");
               break;
            case 2:
               textArea.append("c�� �����ϰ� �����ôٸ� #c�� ���� �� ��ɾ �Է��ϼ���.\n");
               textArea.append(" ex)����¹��,��¹�,���Ÿ��\n");
               break;
            case 3:
               textArea.append("html�� �����ϰ� �����ôٸ� #html�� ���� �� ��ɾ �Է��ϼ���.\n");
               textArea.append(" ex)<style>,<br>\n");
               break;
               
            }
        

      }
      
      void Java(String msg2) {
         if(msg2.contains("��¹�")) {
         textArea.append("��¹��� System.out.println(\"�Է°�\")���� �Է��Ͻø� ���� ��µ˴ϴ�.\n");
       //  textArea.append("\n");
         }
         if(msg2.contains("����¹��")) {
            textArea.append("Scanner \"����\" = new Scanner(System.in)�� �Է��ϰ� ctrl+shift+o�� ����\n");
            textArea.append("����Ʈ�� �� �������� \"���� = ����.nextInt();\"�� �Է��Ѵ�.\n");
           //textArea.append("\n");
         }
         
         
      }
      
      void Clang(String msg2) {
         if(msg2.contains("��¹�")) {
            textArea.append("��¹��� printf(\"���Ÿ��\",����);�ֵ���ǥ �ȿ� ���� ���𰪿� ���� �ٸ��� ����մϴ�.\n");
            textArea.append("���Ÿ���� �������� %d, �Ǽ����� %f, ���ڿ��� %c String�� %s�� ����մϴ�. \n");
         //   textArea.append("\n");
            }
            if(msg2.contains("����¹��")) {
               textArea.append("Scanf(\"���Ÿ��\",����);�Դϴ�.\n");
               textArea.append("���־�Ʃ������� scanf�� ����Ҷ� #include<stdio.h>�ؿ�\n");
               textArea.append("CRT_SECURE_NO_WARNINGS;�� ����մϴ�.\n");
            //   textArea.append("\n");
            }
            if(msg2.contains("���Ÿ��")) {
            textArea.append("���Ÿ���� Int���� %d, float,double���� %f, char�� %c String�� �迭�ִ� char \n");
            textArea.append("�� %s�� ����մϴ�. 8������ %o, 16������ %x�� ���ϴ�.  \n");
          //  textArea.append("\n");
            }
      }
      
      void Html(String msg2) {
            if(msg2.contains("<style>")) {
               textArea.append("<style> ��Ҵ� ������ ���� �Ϻο� ���� ��Ÿ�� ������ �����մϴ�.\n");
               textArea.append("<style> ��Ҵ� head�ȿ� ��ġ�ؾ� �Ѵ�.\n");
           //    textArea.append("\n");
               
            }
            
            if(msg2.contains("<br>")) {
               textArea.append("<br> ��Ҵ� �ؽ�Ʈ �ȿ� �ٹٲ��� �����մϴ�.\n");
            }
         }
      
      void Professor() {
         textArea.append("������ ������ ������ �������̾�.\n");
      }
      

      //�޽��� �����ϴ� ��� �޼ҵ�

      void ChangeId(String msg2) {
        
        id=msg2;
     
       }

      //�޽��� �����ϴ� ��� �޼ҵ�

         void sendMessage() {   
            int a;
          
            String msg = tfMsg.getText(); //TextField�� ���ִ� �۾��� ������
         String name =null;
          if(idchange==1) {
                name=msg;
               ChangeId(name);
               idchange=0;
            }   
          
            tfMsg.setText(""); //�Է� �� ��ĭ����

            textArea.append(id+":" + msg + "\n");//1.TextArea(ä��â)�� ǥ��

            textArea.setCaretPosition(textArea.getText().length());
               if(msg.contains("����")&&msg.contains("����")) {
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
               if(msg.contains("��¹�")) {
                  Java(msg);
               }
               else if(msg.contains("����¹��")) {
                  Java(msg);
               }    
          }
          if(msg.contains("#c")) {    
             if(msg.contains("��¹�")) {
                 Clang(msg);
             } 
             else if(msg.contains("����¹��")) {
                Clang(msg);
             } 
             else if(msg.contains("���Ÿ��")) {
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
          
      

            //2.����(Server)���� �޽��� �����ϱ�

            //�ƿ�ǲ ��Ʈ���� ���� ���濡 ������ ����

            //��Ʈ��ũ �۾��� ������ Thread�� �ϴ� ���� ����

            Thread t = new Thread() {

               @Override

               public void run() {

                  try { //UTF = �����ڵ��� �Ծ�(����), �ѱ� ������ �ʰ� ����

                     dos.writeUTF(msg);

                     dos.flush(); //��� ä�� ���� close()�ϸ� �ȵ�            

                  } catch (IOException e) {

                     e.printStackTrace();

                  }

               }

            };

            t.start();         

         }
   }//class
   