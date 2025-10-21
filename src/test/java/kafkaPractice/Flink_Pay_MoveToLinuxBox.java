package kafkaPractice;

import com.jcraft.jsch.*;

import java.io.FileInputStream;

public class Flink_Pay_MoveToLinuxBox {
    public static void main(String[] args) {
        String user = "chidori";
        String host = "49.249.29.5";  // Linux box IP
        int port = 22;                // SSH port (default 22)
        String password = "@coe$rv!@#";  // or use private key
        String localFile = "C:\\Users\\User\\Downloads\\Nagireddy\\Challenges\\JavaTxn1.txt";
        String remoteDir = "/home/chidori/Flink_Pay/swift_files/";

        Session session = null;
        Channel channel = null;

        try {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channel = session.openChannel("sftp");
            channel.connect();

            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.put(new FileInputStream(localFile), remoteDir + "file.json");

            System.out.println("✅ File uploaded successfully!");
            sftpChannel.exit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channel != null) channel.disconnect();
            if (session != null) session.disconnect();
        }
    }
}

