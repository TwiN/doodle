package org.twinnation.doodle.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chris on 2017-11-24.
 */

public class FileUtils {

    private FileUtils() {}


    public static String generateFilename() {
        return "doodle_"+new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date())+".png";
    }

}
