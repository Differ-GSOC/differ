package cz.nkp.differ.cmdline;

import cz.nkp.differ.compare.metadata.external.*;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper methods for tests
 * User: Jonatan Svensson <jonatansve@gmail.com>
 * Date: 2013-07-23
 * Time: 11:57
 */
public class TestHelper {
    private static int range = 10;

    /**
     * @param s the property name
     * @param l the hash map in which to look in
     * @return null if the value cannot be found in manual data (i.e. it is not significant property)
     *         Returns the value if the value is found and should be tested.
     */
    public static String lookForManualValue(String s, LinkedHashMap l) {
        String result;
        LinkedHashMap lh1 = (LinkedHashMap) l.get("identificationProperties");
        LinkedHashMap lh2 = (LinkedHashMap) l.get("validationProperties");
        LinkedHashMap lh3 = (LinkedHashMap) l.get("characterizationProperties");
        result = (String) lh1.get(s);
        if (result == null) {
            result = (String) lh2.get(s);
            if (result == null) {
                result = (String) lh3.get(s);
            }
        }
        return result;
    }

    /**
     * @param key             value to look for
     * @param transformedData list of all transformed data
     * @return true if key exists in transformed data
     *         false if it does not exist.
     */
    public static boolean lookFor(String key, List<cz.nkp.differ.compare.metadata.external.ResultTransformer.Entry> transformedData) {
        for (cz.nkp.differ.compare.metadata.external.ResultTransformer.Entry e : transformedData) {
            if (key.equals(e.getKey())) return true;
        }
        return false;
    }

    /**
     * Read file for metadataTransformer
     *
     * @param string file path
     * @return file in byte[] format
     * @throws IOException
     */
    public static byte[] readFile(String string) throws IOException {
        RandomAccessFile f = new RandomAccessFile(new File(string), "r");

        try {
            long longlength = f.length();
            int length = (int) longlength;
            if (length != longlength)
                throw new IOException("File size >= 2 GB");
            byte[] data = new byte[length];
            f.readFully(data);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            f.close();
        }
        return null;
    }
}
