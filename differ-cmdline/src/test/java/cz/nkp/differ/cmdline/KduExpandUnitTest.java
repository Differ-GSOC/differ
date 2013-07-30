package cz.nkp.differ.cmdline;

import cz.nkp.differ.compare.metadata.external.ResultTransformer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


/**
 * Test class for kdu_expand transformer
 * User: Jonatan Svensson <jonatansve@gmail.com>
 * Date: 2013-07-19
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:kduexpandTestsCtx.xml"})
public class KduExpandUnitTest {
    List<ResultTransformer.Entry> transformedData;
    @Autowired
    private Map<String,Object> image14Test01;

    @Autowired
    private ResultTransformer kakaduMetadataTransformer;

    @Test
    public void testImage14() throws Exception {

        byte[] stdout = TestHelper.readFile("../docs/examples/images_01/14/output-kakadu.raw");
        transformedData = kakaduMetadataTransformer.transform(stdout,null);
        assertNotNull(transformedData);

        ArrayList ignoredProperties = (ArrayList) image14Test01.get("image14Test01IgnoredProperties");
        ArrayList recognizedProperties = (ArrayList) image14Test01.get("image14Test01RecognizedProperties");
        assertNotNull(recognizedProperties);

        for(ResultTransformer.Entry e: transformedData){
           assertTrue("Testing that transformed property is recognized: "+ e.getKey(),recognizedProperties.contains(e.getKey())||ignoredProperties.contains(e.getKey()));
        }

        LinkedHashMap l = (LinkedHashMap) image14Test01.get("image14SignificantProperties");
        String s;

        for(ResultTransformer.Entry e: transformedData){
            // Make sure it is not ignored first
            if(recognizedProperties.contains(e.getKey())){
                s = TestHelper.lookForManualValue(e.getKey(),l);
                // If s is null here, then the entry is missing in manual data
               assertNotNull("Testing: "+e.getKey()+ " with: "+ s, s);
               assertEquals("Testing equality: "+e.getKey(), e.getValue(), s);
                s=null;
            }
        }

        for(int i=0; i<recognizedProperties.size();i++){
            assertTrue("Testing that manual recognized property was transformed: "+ recognizedProperties.get(i), TestHelper.lookFor((String)recognizedProperties.get(i), transformedData));
        }
        for(int j=0; j<ignoredProperties.size();j++){
            assertTrue("Testing that manual ignored property was transformed: "+ ignoredProperties.get(j),TestHelper.lookFor((String)ignoredProperties.get(j), transformedData));
        }
    }
}

