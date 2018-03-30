package sample;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RunProgram implements Runnable {
    String fileName;
    String filePath;

    String content = "";

    public RunProgram(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public void run() {
        try {
            String [] splitLine = fileName.split("\\.");
            String name = "";

//            String extension = splitLine[splitLine.length - 1];

            for (int i = 0; i < splitLine.length - 1; i++) {
                if (i > 0) {
                    name += ".";
                }
                name += splitLine[i];
            }

            runProcess("javac " + filePath + "\\" + fileName);
            runProcess("java -cp " + filePath + " " + name);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(content);
        Main.consoleLines.setText(content);
    }

    public void runProcess (String command) throws Exception {
        Process pr = Runtime.getRuntime().exec(command);
        pr.waitFor();
        String error = getStreamInfo(pr.getErrorStream());

        content += error;
        content += getStreamInfo(pr.getInputStream());
    }

    public String getStreamInfo (InputStream ios) throws Exception {
        String line = "";
        String content = "";

        int i = 0;

        BufferedReader in = new BufferedReader(new InputStreamReader(ios));
        while ((line = in.readLine()) != null) {
            if (i > 0) {
                content += "\n";
            }
            content += line;
            i++;
        }
        return content;
    }
}
