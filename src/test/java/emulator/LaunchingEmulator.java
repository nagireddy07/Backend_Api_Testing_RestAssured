package emulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class LaunchingEmulator {

    public static void main(String[] args) throws Exception {
        if (!isEmulatorRunning("Pixel_9a")) {
            launchEmulator();
            waitForBootCompletion();
        } else {
            System.out.println("Emulator is already running. Skipping launch...");
        }
        System.out.println("Ready to start automation now...");
    }

    public static boolean isEmulatorRunning(String avdName) {
        try {
            String adbPath = "C:\\Users\\User\\AppData\\Local\\Android\\Sdk\\platform-tools\\adb.exe";
            Process process = Runtime.getRuntime().exec(adbPath + " devices");
            process.waitFor();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("emulator") && line.contains("device")) {
                    System.out.println("Detected running emulator: " + line);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void launchEmulator() {
        try {
            String sdkPath = "C:\\Users\\User\\AppData\\Local\\Android\\Sdk";
            String emulatorPath = sdkPath + "\\emulator\\emulator.exe";
            String avdName = "Pixel_9a";

            ProcessBuilder pb = new ProcessBuilder(
                    emulatorPath, "-avd", avdName,"-no-snapshot-load"
            );
            pb.directory(new File(sdkPath + "\\emulator"));
            pb.inheritIO();
            pb.start();

            System.out.println("Launching emulator...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void waitForBootCompletion() throws Exception {
        String adbPath = "C:\\Users\\User\\AppData\\Local\\Android\\Sdk\\platform-tools\\adb.exe";
        Process process;

        while (true) {
            process = Runtime.getRuntime().exec(adbPath + " shell getprop sys.boot_completed");
            process.waitFor();
            String output = new String(process.getInputStream().readAllBytes()).trim();

            if ("1".equals(output)) break;

            System.out.println("Booting...");
            Thread.sleep(2000);
        }

        System.out.println("Emulator boot complete!");
    }
}
