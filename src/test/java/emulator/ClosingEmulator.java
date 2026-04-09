package emulator;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ClosingEmulator {

    public static void main(String[] args) {
        String sdkPath = "C:\\Users\\User\\AppData\\Local\\Android\\Sdk";
        closeEmulator(sdkPath);
    }

    public static void closeEmulator(String sdkPath) {
        try {
            String adbPath = sdkPath + "\\platform-tools\\adb.exe";

            // Step 1: Get running emulator device ID
            String emulatorId = getRunningEmulatorId(adbPath);

            if (emulatorId == null) {
                System.out.println("No emulator is running!");
                return;
            }


            ProcessBuilder pb = new ProcessBuilder(
                    adbPath, "-s", emulatorId, "emu", "kill"
            );
            pb.start();

            System.out.println("Emulator close command sent successfully.");

        } catch (Exception e) {
            System.out.println("Failed to close emulator: " + e.getMessage());
            e.printStackTrace();
        }
    }

   
    private static String getRunningEmulatorId(String adbPath) {
        try {
            ProcessBuilder pb = new ProcessBuilder(adbPath, "devices");
            Process process = pb.start();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("emulator-") && line.endsWith("device")) {
                    return line.split("\t")[0];
                }
            }
        } catch (Exception ignored) {}

        return null;
    }
}

