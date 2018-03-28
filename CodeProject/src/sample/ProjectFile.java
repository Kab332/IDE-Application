package sample;

import java.io.File;

/**
 * ProjectFile
 *
 * It is recommended that you use this class as the entity type
 * for your tree view, but it is not mandatory.
 **/
public class ProjectFile {
    private File file;

    public ProjectFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public String toString() {
        return this.file.getName();
    }
}
