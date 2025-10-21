package kafkaPractice;
import com.jcraft.jsch.*;

import java.io.InputStream;

public class Flink_Pay_PushToKafka {
    public static void main(String[] args) {
        String user = "chidori";
        String host = "49.249.29.5";
        int port = 22;
        String password = "@coe$rv!@#";
        String command = "./Flink_Pay/kafka/bin/pushTxn_swift.sh /home/chidori/Flink_Pay/swift_files/JavaTxn1.txtfile.json";

        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
            channelExec.setCommand(command);
            channelExec.setErrStream(System.err);

            InputStream in = channelExec.getInputStream();
            channelExec.connect();

            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    System.out.print(new String(tmp, 0, i));
                }
                if (channelExec.isClosed()) {
                    System.out.println("Exit Status: " + channelExec.getExitStatus());
                    break;
                }
                Thread.sleep(1000);
            }

            channelExec.disconnect();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
