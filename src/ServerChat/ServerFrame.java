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

import java.net.ServerSocket;

import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JFrame;

import javax.swing.JPanel;

import javax.swing.JScrollPane;

import javax.swing.JTextArea;

import javax.swing.JTextField;


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

import java.net.ServerSocket;

import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JFrame;

import javax.swing.JPanel;

import javax.swing.JScrollPane;

import javax.swing.JTextArea;

import javax.swing.JTextField;



public class ServerFrame extends JFrame  {

   JTextArea textArea; //��� ��������

   JTextField tfMsg;

   JButton btnSend;

   Color chinablue = new Color(255,255,255);

   ServerSocket serverSocket;

   Socket socket;

   DataInputStream dis;

   DataOutputStream dos;
   int idchange=1;
   String name =null;
   private JPanel panel;
   

   public ServerFrame() {      

      setTitle("Server");
      setResizable(false);
      setBounds(450, 50, 500, 350);
      getContentPane().setLayout(null);

      

      textArea = new JTextArea();  
      textArea.setBackground(chinablue);

      textArea.setEditable(false); //���� ����

      JScrollPane scrollPane = new JScrollPane(textArea);
      scrollPane.setBounds(30, 30, 410, 230);
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
      panel.setBounds(0, 0, 494, 300);
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

      

      //������ ������ �� �ֵ��� ���������� ����� ����� �� �ִ� �غ� �۾�!

      //��Ʈ��ũ �۾��� Main Thread�� �ϰ��ϸ� �ٸ� �۾�(Ű���� �Է�, Ŭ�� ��..)���� 

      //���� �� �� ����, ���α׷��� ����, �׷��� Main�� UI�۾��� �����ϵ��� �ϰ�, 

      //�ٸ� �۾���(���� �ɸ���)��  ������ Thread���� �����ϴ� ���� ������.   

      ServerThread serverThread = new ServerThread();

      serverThread.setDaemon(true); //���� ������ ���� ����

      serverThread.start();

      

      addWindowListener(new WindowAdapter() {         

         @Override //Ŭ���̾�Ʈ �����ӿ� window(â) ���� ������ �߰�

         public void windowClosing(WindowEvent e) {            

            super.windowClosing(e);

            try {

               if(dos != null) dos.close();

              if(dis != null) dis.close();

              if(socket != null) socket.close();

               if(serverSocket != null) serverSocket.close();

            } catch (IOException e1) {               

               e1.printStackTrace();

            }

         }         

      });

   }//������ �޼ҵ�   

   

   //�̳�Ŭ���� : ���������� �����ϰ� Ŭ���̾�Ʈ�� ������ ����ϰ�,

   //����Ǹ� �޽����� ���������� �޴� ���� ����

   class ServerThread extends Thread {

      @Override

      public void run() {         

         try {  //���� ���� ���� �۾�

            serverSocket = new ServerSocket(10001);

            textArea.append("���������� �غ�ƽ��ϴ�...\n");

            textArea.append("Ŭ���̾�Ʈ�� ������ ��ٸ��ϴ�.\n");            

            socket = serverSocket.accept();//Ŭ���̾�Ʈ�� �����Ҷ����� Ŀ��(������)�� ���

            textArea.append(socket.getInetAddress().getHostAddress() + "���� �����ϼ̽��ϴ�.\n");
          

            

            //����� ���� ��Ʈ�� ����

            dis = new DataInputStream(socket.getInputStream());

            dos = new DataOutputStream(socket.getOutputStream());

            

            while(true) {

               //������ ������ �����͸� �б�

               String msg = dis.readUTF();//������ ���������� ���
               
               if(idchange==1) {
                  name=msg;
                  idchange=0;
               }
               textArea.append(name+":" + msg + "\n");

               textArea.setCaretPosition(textArea.getText().length());

            }            

            

         } catch (IOException e) {

            textArea.append("Ŭ���̾�Ʈ�� �������ϴ�.\n");

         }

      }

   }

   

   //�޽��� �����ϴ� ��� �޼ҵ�

   void sendMessage() {   

      String msg = tfMsg.getText(); //TextField�� ���ִ� �۾��� ������

      tfMsg.setText(""); //�Է� �� ��ĭ����

      textArea.append(" [SERVER] : " + msg + "\n");//1.TextArea(ä��â)�� ǥ��

      textArea.setCaretPosition(textArea.getText().length()); //��ũ�� ���󰡰�

      //2.����(Client)���� �޽��� �����ϱ�

      Thread t = new Thread() {

         @Override

         public void run() {

            try {

               dos.writeUTF(msg);

               dos.flush();

            } catch (IOException e) {

               e.printStackTrace();

            }

         }

      };      

      t.start();

   }   

}//class



