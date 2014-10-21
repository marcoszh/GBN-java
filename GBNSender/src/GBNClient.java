import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.Timer;

public class GBNClient {
	static int receiverPort=5566;
	static InetAddress receiverIp;
	static byte[] receiveData = new byte[100];
	static byte[] sendData = new byte[100];
	public static void main(String args[]) throws Exception {
		DatagramSocket clientSocket = new DatagramSocket();
		boolean flag = false;
		Timer[] tt = new Timer[5];
		String[] send = new String[5];
		int n = 0;
		for (int k = 0; k < 5; k++)
			send[k] = "packet" + k;
		receiverIp = InetAddress.getByName("localhost");
		
		
		for (int k = 0; k < 5; k++) {
			sendData = send[k].getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData,
					sendData.length, receiverIp, receiverPort);
			clientSocket.send(sendPacket);
			ActionListener taskPerformer = new myActionListener(clientSocket,
					sendPacket, k);
			tt[k] = new Timer(5000, taskPerformer);
			tt[k].start();

		}

		while (flag == false) {

			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);
			clientSocket.receive(receivePacket);
			String sentence = new String(receivePacket.getData());
			char b = sentence.charAt(0);
			int i = b - '0';
			if (i <= n) {
				n++;
				System.out.println("");
				System.out.println("********收到ack" + i + "，停止发送Packet" + i
						+ "********");

				tt[i].stop();
			}
			if (n == 5) {
				System.out.println("\n所有数据发送完毕！");

			}
		}
	}

}

class myActionListener implements ActionListener {
	private DatagramSocket clientSocket;
	private DatagramPacket sendPacket;
	int k;

	public myActionListener(DatagramSocket clientsocket,
			DatagramPacket sendpacket, int i) {
		clientSocket = clientsocket;
		sendPacket = sendpacket;
		k = i;
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("超时! \nPacket" + k + "未收到回应, 再次发送");
		try {

			clientSocket.send(sendPacket);
		} 
		catch (Exception exception) {
		}
	};
}