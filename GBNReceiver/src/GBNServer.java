import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class GBNServer {
	static int receiverPort = 5566;
	public static void main(String args[]) throws Exception {
		int n = 0, d = 0;
		DatagramSocket serverSocket = new DatagramSocket(receiverPort);
		byte[] receiveData = new byte[100];

		while (true) {

			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);
			serverSocket.receive(receivePacket);
			String received = new String(receivePacket.getData());

			char b = received.charAt(0);
			int i = b - '0';
			if (Math.random() < 0.9) {
				System.out.println("�յ�" + received);

				if (i <= n) {
					n++;
					System.out.println("**************����ack" + i);
				} else {//������Χ�ˣ���Ҫ
					if (n > 0)
						d = n - 1;
					System.out.println("����" + "������ack" + d);
				}
				byte[] sendData = new byte[100];
				String temp=""+received.charAt(0);
				sendData=temp.getBytes();
				InetAddress IPAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();
				DatagramPacket sendPacket = new DatagramPacket(sendData,
						sendData.length, IPAddress, port);
				serverSocket.send(sendPacket);
			}
		}
	}
}