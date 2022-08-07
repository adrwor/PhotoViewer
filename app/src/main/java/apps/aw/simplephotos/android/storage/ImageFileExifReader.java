package apps.aw.simplephotos.android.storage;

import androidx.exifinterface.media.ExifInterface;

import java.io.File;
import java.io.IOException;

import apps.aw.simplephotos.java.treenavigator.FileMetaData;
import apps.aw.simplephotos.java.treenavigator.FileMetaDataReader;

public class ImageFileExifReader implements FileMetaDataReader {

    //contains valid suffixes
    private static final String[] extensions = {"jpg", "jpeg", "png", "gif", "webp"};

    /**
     * Checks if the given file is an image file that the Glide library can handle
     * @param file the file to be checked
     * @return true if the file is a proper image file
     */
    public boolean isImageFile(File file) {
        String fileName = file.getName();
        return isImageFileName(fileName);
    }

    /**
     * Checks if the given filename has a proper name and an extension like .png, .jpg, ...
     * that the Glide library can handle.
     * @param fileName string to be checked
     * @return true if it is a proper image file name
     */
    public static boolean isImageFileName(String fileName) {
        for (String s : extensions) { //go through all extensions
            if(getExtension(fileName).toLowerCase().equals(s)) {
                return true;    //only true if it has one of the valid extensionsss
            }
        }
        return false;
    }

    /**
     * Returns filename extension (without the dot).
     * @param fileName the filename
     * @return the extension, without the dot. If no extension: empty string.
     */
    public static String getExtension(String fileName) {
        if(!fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".")+1); //return extension, without "."
    }


    /**
     * gets the original date
     * @param imageFile The image file.
     * @return The date for TAG_DATETIME_ORIGINAL
     */
    public static String getExifDateTime(File imageFile) {
        String dateTimeString = "";
        try {
            ExifInterface exifInterface = new ExifInterface(imageFile);
            dateTimeString = exifInterface.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL);
            if(dateTimeString == null) {
                dateTimeString = "";
            }
        }
        catch (IOException e) {
            return "";
        }
        return dateTimeString;
    }

    @Override
    public FileMetaData readFileMetaData(File file) {
        FileMetaData fileMetaData = new FileMetaData();
        fileMetaData.isImageFile = false; // is anyway false
        if(isImageFile(file)) {
            fileMetaData.originalDateTime = getExifDateTime(file);
            fileMetaData.isImageFile = true;
        }
        return fileMetaData;
    }





    /*TODO:
    -method for reading EXIF data to get date of image creation, orientation
        -datetime priority: 1. original datetime
                            2. digital dategtime
                            3. datetime (file change time)
    -check if file is a movie
    -which file types can Glide library read?

     */

}
