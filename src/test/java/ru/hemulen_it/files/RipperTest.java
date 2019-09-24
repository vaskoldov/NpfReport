package ru.hemulen_it.files;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class RipperTest extends TestCase {
    @Test
    public static void testUnpackToBuffer() {
        Ripper testRipper = new Ripper();
        File arcFile = new File("D:\\Temp\\base-storage\\770I01\\000b172a-d87c-4755-ad95-bcd41f8e3257\\ПФР_081025_7726260499_ДНПФД_20190627_693f0452-984a-11e9-86b9-005056897fe0.xml.gz");
        ByteArrayOutputStream os = testRipper.UnpackToBuffer(arcFile);
        System.out.println(os.toString());
    }
}