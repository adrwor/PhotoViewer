package apps.aw.simplephotos.domain;

import apps.aw.simplephotos.domain.FileContent;
import apps.aw.simplephotos.domain.FileList;

public class ViewData {
    public FileList column1;
    public FileList column2;
    public FileContent fileContent;
    public String path;

    public ViewData(FileList column1, FileList column2, FileContent fileContent, String path) {
        this.column1 = column1;
        this.column2 = column2;
        this.fileContent = fileContent;
        this.path = path;
    }
}
