package kafkaPractice;


import com.jcraft.jsch.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Random;


public class BulkTransactionJson {
    static Random r = new Random();
    static int randNum = r.nextInt(900, 1000);
    public static void main(String[] args) throws Exception {
        int randNum2 = r.nextInt(900, 10000);
        // === PLACEHOLDER REPLACEMENTS ===
        String transactionId = "TR20250926051906"+randNum;
        System.out.println(transactionId);
        String amount = ""+randNum2;

        // === CONNECTION CONFIGURATION ===
        int port = 22;
        String host = "49.249.29.5";
        String username = "chidori";
        String password = "@coe$rv!@#";

        // === PATHS ===
        String templateFilePath = "C:\\Users\\User\\Downloads\\Nagireddy\\Challenges\\BulkTxnJSON.txt";  // file with placeholders
        String remoteUploadDir = "/home/chidori/Flink_Pay/swift_files/";
        String remoteScriptPath = "/home/chidori/Flink_Pay/kafka/bin/pushTxn.sh";

        
        // === STEP 1: Replace placeholders variables and create processed file ===
        System.out.println("Replace placeholders variables and create processed file");
        String processedFilePath = createFileWithReplacements(templateFilePath, transactionId, amount);

        
        // === STEP 2: Upload processed file with a unique name ===
        System.out.println("Upload processed file with a unique name");
        String remoteFileName = uploadFileToLinux(processedFilePath, host, port, username, password, remoteUploadDir);

        
        // === STEP 3: Run the shell script with the remote file ===
        System.out.println("Run the shell script with the remote file");
        String remoteFilePath = remoteUploadDir + "/" + remoteFileName;
        runRemoteScript(host, port, username, password, remoteScriptPath, remoteFilePath);
         new File(processedFilePath).delete();
          
    }

    private static String createFileWithReplacements(String templatePath, String transactionId, String amount) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(templatePath)));
        StringBuilder contentBuilder = new StringBuilder(content);

        // Replace placeholders and collect all new transaction IDs
        java.util.List<String> generatedIds = replaceMultipleTransactionIds(contentBuilder, content);
        content = contentBuilder.toString();

        // Replace amount
        content = content.replaceAll("__AMOUNT__", amount);

        // Write to new file
        String fileName = "ATM_TRXN_" + transactionId + ".txt";
        String outputPath = "C:\\Users\\User\\Desktop\\paymentTrxn\\" + fileName;
        Files.write(Paths.get(outputPath), content.getBytes());

        System.out.println(outputPath);

        // === Log the transaction IDs to Excel ===
        writeTransactionIdsToExcel(generatedIds);

        return outputPath;
    }

    private static java.util.List<String> replaceMultipleTransactionIds(StringBuilder contentBuilder, String content) {
        String placeholder = "__TRANSACTION_ID__";
        java.util.List<String> generatedIds = new java.util.ArrayList<>();

        StringBuilder newContent = new StringBuilder();
        int index = 0;
        int lastEnd = 0;

        while ((index = content.indexOf(placeholder, lastEnd)) != -1) {
            newContent.append(content, lastEnd, index);

            // Generate unique random transaction ID
            String randomId = "TR" + System.currentTimeMillis() + new java.util.Random().nextInt(1000, 9999);

            newContent.append(randomId);
            generatedIds.add(randomId);

            lastEnd = index + placeholder.length();
        }

        newContent.append(content.substring(lastEnd));

        // Update original content
        contentBuilder.setLength(0);
        contentBuilder.append(newContent);

        return generatedIds;
    }
    
    private static void writeTransactionIdsToExcel(java.util.List<String> transactionIds) {
        String excelPath = "C:\\Users\\User\\Desktop\\paymentTrxn\\Transaction_Log.xlsx";
        org.apache.poi.ss.usermodel.Workbook workbook;
        org.apache.poi.ss.usermodel.Sheet sheet;

        File file = new File(excelPath);

        try {
            if (file.exists()) {
                // If file already exists, open it
                FileInputStream fis = new FileInputStream(file);
                workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook(fis);
                sheet = workbook.getSheetAt(0);
                fis.close();
            } else {
                // Create a new workbook and sheet
                workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook();
                sheet = workbook.createSheet("Transactions");

                // Create header
                org.apache.poi.ss.usermodel.Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Transaction IDs");
            }

            // Find the next empty row
            int lastRow = sheet.getLastRowNum();
            for (String txnId : transactionIds) {
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(++lastRow);
                row.createCell(0).setCellValue(txnId);
            }

            // Write back to the file
            FileOutputStream fos = new FileOutputStream(excelPath);
            workbook.write(fos);
            fos.close();
            workbook.close();

            System.out.println("✅ Transaction IDs logged to Excel: " + excelPath);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Failed to write transaction IDs to Excel");
        }
    }




    
    // STEP 2: Upload file via SFTP and rename it on the remote server to include a timestamp
    private static String uploadFileToLinux(String localFilePath, String host, int port,
                                            String username, String password, String remoteDir) throws Exception {

        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, port);
        session.setPassword(password);

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        session.connect(10000); // 10 seconds timeout

        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftp = (ChannelSftp) channel;

        File localFile = new File(localFilePath);

        // === Generate filename for remote file ===
        String remoteFileName = localFile.getName();

        try (InputStream input = new FileInputStream(localFile)) {
               
        	sftp.cd(remoteDir);
            // Upload the file with the new remote name
            sftp.put(input, remoteFileName);
            System.out.println("Uploaded file to: " + remoteDir + "/" + remoteFileName);
            
        } finally {
            sftp.exit();
            session.disconnect();
        }

        return remoteFileName;
    }

    // STEP 3: SSH into server and run the Kafka push script
    private static void runRemoteScript(String host, int port, String username, String password,
                                        String scriptPath, String filePathArg) throws Exception {

        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, port);
        session.setPassword(password);

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        session.connect(10000);
        
        //command creation---
        String command = scriptPath + " " + filePathArg;

        ChannelExec channel = (ChannelExec) session.openChannel("exec");
        
        //command execution
        channel.setCommand(command);
        channel.setErrStream(System.err);
        InputStream in = channel.getInputStream();

        channel.connect();

        System.out.println("Executing this command: " + command);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        // reading output of cmd in linux and printing it in the console
        while ((line = reader.readLine()) != null) {
            System.out.println("[remote] " + line);
        }

        // Wait until the channel is done
        int exitStatus = channel.getExitStatus();
        while (channel.isConnected()) {
            Thread.sleep(100);
        }

        channel.disconnect();
        session.disconnect();

        if (exitStatus == 0) {
            System.out.println("Script executed successfully.");
        } else {
            System.err.println("Script failed and status code : " + exitStatus);
        }
    }
}