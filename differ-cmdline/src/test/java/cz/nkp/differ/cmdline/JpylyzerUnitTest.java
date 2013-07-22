package cz.nkp.differ.cmdline;

import cz.nkp.differ.compare.metadata.external.ResultTransformer;
import cz.nkp.differ.compare.metadata.external.ResultTransformer.Entry;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Test class for Jpylyzer transformer.
 * Test context includes significant properties for the given extractor.
 * The testing makes sure that the properties transformed are being
 * recognized properly and that no values are missing in the significant
 * properties list.
 *
 * @author Jan Stavel / Jonatan Svensson
 * @version 15-07-2013
 *
 *
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:jpylyzerTestsCtx.xml"})
public class JpylyzerUnitTest {

    List<ResultTransformer.Entry> transformedData;
    @Autowired
    private Map<String,Object> image14Test01;

    @Autowired
    private ResultTransformer jpylyzerMetadataTransformer;

    Logger logger = LogManager.getLogger(JpylyzerUnitTest.class.getName());

    @Test
    public void testImage14() throws Exception {
        byte[] stdout = readFile("../docs/examples/images_01/14/output-jpylyzer.raw");
        transformedData = jpylyzerMetadataTransformer.transform(stdout,null);
        assertNotNull(transformedData);

        /**
         * Test all properties are mapped.
         * Compare transformedData with list of
         * manual input of significant properties in image14Test01RecognizedProperties
         * Fails if a property is transformed but is yet not mapped.
         */

        ArrayList ignoredProperties = (ArrayList) image14Test01.get("image14Test01IgnoredProperties");
        ArrayList recognizedProperties = (ArrayList) image14Test01.get("image14Test01RecognizedProperties");
        assertNotNull(recognizedProperties);
        for(Entry e: transformedData){
            assertTrue("Testing that transformed property is recognized: "+ e.getKey(),recognizedProperties.contains(e.getKey())||ignoredProperties.contains(e.getKey()));
        }


        /**
         * Test all properties that are not ignored.
         * Go through each entry in transformedData,
         * Look for the key in:
         * identificationProperties/validationProperties/characterizationProperties,
         * Assert that the value is identical.
         */

        LinkedHashMap l = (LinkedHashMap) image14Test01.get("image14SignificantProperties");
        LinkedHashMap lh1 = (LinkedHashMap) l.get("identificationProperties");
        LinkedHashMap lh2 = (LinkedHashMap) l.get("validationProperties");
        LinkedHashMap lh3 = (LinkedHashMap) l.get("characterizationProperties");

        String s;

        for(Entry e: transformedData){
            // Make sure it is not ignored first
            if(recognizedProperties.contains(e.getKey())){
                s= (String)lh1.get(e.getKey());
                if(s==null) {
                    s= (String)lh2.get(e.getKey());
                    if(s==null){
                        s= (String)lh3.get(e.getKey());
                    }
                }  // If s is null here, then the entry is missing in manual data

                assertNotNull("Testing: "+e.getKey()+ " with: "+ s, s);
                assertEquals("Testing equality: "+e.getKey(), e.getValue(), s);
                s=null;
            }
        }
        /**
         * Last: 1.Check conversely that the recognized properties in test context
         * match the transformed data exactly (no extra entries in list).
         * 2. Ignored properties should also be in the transformed list.
         */

        for(int i=0; i<recognizedProperties.size();i++){
            assertTrue("Testing that manual recognized property was transformed: "+ recognizedProperties.get(i), lookFor((String)recognizedProperties.get(i)));
        }
        for(int j=0; j<ignoredProperties.size();j++){
            assertTrue("Testing that manual ignored property was transformed: "+ ignoredProperties.get(j),lookFor((String)ignoredProperties.get(j)));
        }
    }
    private boolean lookFor(String key){
        for(Entry e: transformedData){
            if(key.equals(e.getKey())) return true;
        }
        return false;
    }

    private byte[] readFile(String string) throws IOException {
        RandomAccessFile f = new RandomAccessFile(new File(string), "r");

        try {
            long longlength = f.length();
            int length = (int) longlength;
            if (length != longlength)
                throw new IOException("File size >= 2 GB");
            byte[] data = new byte[length];
            f.readFully(data);
            return data;
        }catch(IOException e){
            e.printStackTrace();
        }
        finally {
            f.close();
        }
        return null;
    }
}
