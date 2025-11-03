package kafkaPractice;

import com.jcraft.jsch.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.*;
import java.nio.file.*;
import java.time.Duration;
import java.util.*;
import java.util.Random;

public class BulkTransactionWithCount {
    static Random r = new Random();
    static int randNum = r.nextInt(900, 1000);
    static List<String> allTransactionIds = new ArrayList<String>();

    public static void main(String[] args) throws Exception {
        int randNum2 = r.nextInt(900, 10000);
        String transactionId = "TR20250926051906" + randNum;
        System.out.println("Base Transaction ID: " + transactionId);
        int count = 5; 
        String amount = "" + randNum2;
        int port = 22;
        String host = "49.249.29.5";
        String username = "chidori";
        String password = "@coe$rv!@#";

        String templateFilePath = "C:\\Users\\User\\Downloads\\Nagireddy\\Challenges\\JsonFlinkPay.txt"; // file with [ {...} ]
        String remoteUploadDir = "/home/chidori/Flink_Pay/swift_files/";
        String remoteScriptPath = "/home/chidori/Flink_Pay/kafka/bin/pushTxn.sh";

        System.out.println("Step 1: Creating file with multiple JSON objects...");
        String processedFilePath = createFileWithJsonArray(templateFilePath, transactionId, amount, count);


        System.out.println("Step 2: Uploading processed file to Linux...");
        String remoteFileName = uploadFileToLinux(processedFilePath, host, port, username, password, remoteUploadDir);

        System.out.println("Step 3: Running remote script...");
        String remoteFilePath = remoteUploadDir + "/" + remoteFileName;
        runRemoteScript(host, port, username, password, remoteScriptPath, remoteFilePath);

        new File(processedFilePath).delete();

        Thread.sleep(2000);
        validation(allTransactionIds);
    }

    private static String createFileWithJsonArray(String templatePath, String baseTransactionId, String amount, int count) throws IOException {
        String template = new String(Files.readAllBytes(Paths.get(templatePath)));

        // Extract JSON object (between [ and ])
        String jsonObjectTemplate = template.trim();
        if (jsonObjectTemplate.startsWith("[")) jsonObjectTemplate = jsonObjectTemplate.substring(1);
        if (jsonObjectTemplate.endsWith("]")) jsonObjectTemplate = jsonObjectTemplate.substring(0, jsonObjectTemplate.length() - 1);
        jsonObjectTemplate = jsonObjectTemplate.trim();

        List<String> allTxnIds = new ArrayList<>();
        StringBuilder jsonArrayBuilder = new StringBuilder();
        jsonArrayBuilder.append("[\n");

        for (int i = 0; i < count; i++) {
            String jsonObj = new String(jsonObjectTemplate);

            String transactionId = "TR" + System.currentTimeMillis() + new Random().nextInt(1000, 9999);
            String randomAmount = "" + new Random().nextInt(1000, 99999);

            jsonObj = jsonObj.replaceAll("__TRANSACTION_ID__", transactionId);
            jsonObj = jsonObj.replaceAll("__AMOUNT__", randomAmount);

            jsonArrayBuilder.append("  ").append(jsonObj);
            if (i < count - 1) jsonArrayBuilder.append(",\n");

            allTxnIds.add(transactionId);

            try { Thread.sleep(5); } catch (InterruptedException ignored) {}
        }

        jsonArrayBuilder.append("\n]");

        String fileName = "ATM_TRXN_ARRAY_" + baseTransactionId + ".txt";
        String outputPath = "C:\\Users\\User\\Desktop\\paymentTrxn\\" + fileName;
        Files.write(Paths.get(outputPath), jsonArrayBuilder.toString().getBytes());

        System.out.println("✅ File created: " + outputPath);
        allTransactionIds = List.copyOf(allTxnIds);

        writeTransactionIdsToExcel(allTxnIds);

        return outputPath;
    }

    private static String uploadFileToLinux(String localFilePath, String host, int port, String username, String password, String remoteDir) throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, port);
        session.setPassword(password);

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect(10000);

        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftp = (ChannelSftp) channel;

        File localFile = new File(localFilePath);
        String remoteFileName = localFile.getName();

        try (InputStream input = new FileInputStream(localFile)) {
            sftp.cd(remoteDir);
            sftp.put(input, remoteFileName);
            System.out.println("✅ Uploaded file to: " + remoteDir + "/" + remoteFileName);
        } finally {
            sftp.exit();
            session.disconnect();
        }

        return remoteFileName;
    }

    private static void runRemoteScript(String host, int port, String username, String password, String scriptPath, String filePathArg) throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, port);
        session.setPassword(password);

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect(10000);

        String command = scriptPath + " " + filePathArg;
        ChannelExec channel = (ChannelExec) session.openChannel("exec");
        channel.setCommand(command);
        channel.setErrStream(System.err);
        InputStream in = channel.getInputStream();
        channel.connect();

        System.out.println("🚀 Executing remote command: " + command);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("[remote] " + line);
        }

        int exitStatus = channel.getExitStatus();
        while (channel.isConnected()) Thread.sleep(100);
        channel.disconnect();
        session.disconnect();

        if (exitStatus == 0) System.out.println("✅ Script executed successfully.");
        else System.err.println("❌ Script failed, status code: " + exitStatus);
    }

    private static void writeTransactionIdsToExcel(List<String> transactionIds) {
        String excelPath = "C:\\Users\\User\\Desktop\\paymentTrxn\\Transaction_Log.xlsx";
        Workbook workbook;
        Sheet sheet;
        File file = new File(excelPath);

        try {
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheetAt(0);
                fis.close();
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Transactions");
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Transaction IDs");
            }

            int lastRow = sheet.getLastRowNum();
            for (String txnId : transactionIds) {
                Row row = sheet.createRow(++lastRow);
                row.createCell(0).setCellValue(txnId);
            }

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
    
    public static void validation(List<String> transactionIds) {
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();


        driver.get("http://49.249.29.5:8091/");
        driver.findElement(By.id("username")).sendKeys("rmgyantra");
        driver.findElement(By.id("inputPassword")).sendKeys("rmgy@9999");
        driver.findElement(By.xpath("//button[.=' Login']")).click();

        driver.findElement(By.xpath("//a[contains(.,'All Transactions')]")).click();

        for (String transactionId : transactionIds) {
            try {

                driver.findElement(By.xpath("//input[@placeholder='Search by Transaction Id']")).clear();

                driver.findElement(By.xpath("//input[@placeholder='Search by Transaction Id']")).sendKeys(transactionId);

 
                Thread.sleep(1500);

                String displayedId = driver.findElement(By.xpath("//td[contains(.,'TR')]")).getText();

                if (transactionId.equals(displayedId)) {
                    System.out.println("✅ Transaction ID found: " + transactionId);
                } else {
                    System.out.println("❌ Mismatch! Expected: " + transactionId + " | Found: " + displayedId);
                }

            } catch (Exception e) {
                System.out.println("❌ Transaction not found or error for ID: " + transactionId);
            }
        }
        
        driver.quit();
    }

}
