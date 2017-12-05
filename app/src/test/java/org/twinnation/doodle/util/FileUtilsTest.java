package org.twinnation.doodle.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * I feel like this is the most useless test I've ever made, but I am forced to do this.
 * Please forgive me.
 */
public class FileUtilsTest {

    @Test
    public void generateFilename() throws Exception {
        assertTrue(FileUtils.generateFileName().startsWith("doodle_"));
    }

}
