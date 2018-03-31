package sample;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This is a runnable class that is used to compile and run programs. It currently only runs java programs and assumes
 * javac and java commands will work.
 */
public class RunProgram implements Runnable {
    String fileName;
    String filePath;

    String content = "";

    // Initializing the class with the fileName (including extension) and the filePath (the location) of the program
    public RunProgram(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    // When the class is run...
    public void run() {
        String divider = "";
        try {
            // Here we are getting just the name of the file without the extension for the java command
            String [] splitLine = fileName.split("\\.");
            String name = "";
            for (int i = 0; i < splitLine.length - 1; i++) {
                if (i > 0) {
                    name += ".";
                }
                name += splitLine[i];
            }

            // Placing " on both sides of the file path to handle spaces in between
            filePath = "\"" + filePath + "\"";

            // Using the correct connection between the filePath and fileName depending on whether you use Windows or Linux
            if (filePath.contains("\\")) {
                divider = "\\";
            } else if (filePath.contains("/")) {
                divider = "/";
            }

            // Running the javac and java command, the java command includes the class path
            runProcess("javac " + filePath + divider + fileName);
            runProcess("java -cp " + filePath + " " + name);

        } catch (Exception e) {
            e.printStackTrace();
        }
        // Storing the results of the run in a variable in the Main function for displaying purposes
        Main.consoleLines.clear();
        Main.consoleLines.setText(content);
        Main.consoleLines.setWrapText(true);
    }

    // This is where the command is run
    public void runProcess (String command) throws Exception {
        Process pr = Runtime.getRuntime().exec(command);
        pr.waitFor();
        String error = getStreamInfo(pr.getErrorStream());

        content += error;

        // If there is no error then the process' input stream is read and stored in contents
        if (content.isEmpty()) {
            content += getStreamInfo(pr.getInputStream());
        }

        pr.getErrorStream().close();
        pr.getInputStream().close();
    }

    // This function reads the stream that is passed, stores the values in a variable and returns it
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
