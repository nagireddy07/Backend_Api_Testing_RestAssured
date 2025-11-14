package systemIntegration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunCommandsInCMD {

    public static void main(String[] args) {
        try {
            String emulatorCmd = "C:\\Users\\User\\AppData\\Local\\Android\\Sdk\\emulator\\emulator.exe -avd Medium_Phone";
            String appiumCmd = "appium --port 4724";

            runCommand(emulatorCmd);
            System.out.println("⏳ Waiting for emulator to boot...");
            waitForEmulatorBoot();

            Process appiumProcess = runCommandAndReturnProcess(appiumCmd);
            System.out.println("✅ Appium server started.");

            Thread.sleep(20000);

            if (appiumProcess.isAlive()) {
                appiumProcess.destroy();
                System.out.println("🛑 Appium server stopped.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runCommand(String command) throws IOException {
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
        builder.redirectErrorStream(true);
        Process process = builder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        new Thread(() -> {
            try {
                String line;
                while ((line = reader.readLine()) != null)
                    System.out.println(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static Process runCommandAndReturnProcess(String command) throws IOException {
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
        builder.redirectErrorStream(true);
        Process process = builder.start();
        return process;
    }

    public static void waitForEmulatorBoot() throws IOException, InterruptedException {
        boolean booted = false;
        while (!booted) {
            @SuppressWarnings("deprecation")
			Process process = Runtime.getRuntime().exec("adb shell getprop sys.boot_completed");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            if (line != null && line.trim().equals("1")) {
                booted = true;
                System.out.println("✅ Emulator fully booted!");
            } else {
                System.out.println("...booting...");
                Thread.sleep(5000);
            }
        }
    }
}
