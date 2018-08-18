package us.deloitteinnovation.aca.batch.file.filter;

import java.io.File;
import java.io.FilenameFilter;

public class FileExtensionFilter implements FilenameFilter {
    private String ext;

    public FileExtensionFilter(String fileName) {
        this.ext = fileName.toLowerCase();
    }

    @Override
    public boolean accept(File dir, String name) {
        return name.toLowerCase().endsWith(ext);
    }
}
