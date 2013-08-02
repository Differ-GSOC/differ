package cz.nkp.differ.cmdline;

import cz.nkp.differ.compare.metadata.external.ResultTransformer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


/**
 * Test class for Djvudump transformer
 * User: Jonatan Svensson <jonatansve@gmail.com>
 * Date: 2013-07-19
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:djvudumpTestsCtx.xml"})
public class DjvudumpUnitTest {
    List<ResultTransformer.Entry> transformedData;
    @Autowired
    private Map<String, Object> image05Test01;

    @Autowired
    private ResultTransformer djvudumpMetadataTransformer;

    @Test
    public void testImage05() throws Exception {

        byte[] stdout = TestHelper.readFile("../docs/examples/images_03/05/output-djvudump.raw");
        transformedData = djvudumpMetadataTransformer.transform(stdout, null);
        assertNotNull(transformedData);

        ArrayList ignoredProperties = (ArrayList) image05Test01.get("image05Test01IgnoredProperties");
        ArrayList recognizedProperties = (ArrayList) image05Test01.get("image05Test01RecognizedProperties");
        assertNotNull(recognizedProperties);


        LinkedHashMap l = (LinkedHashMap) image05Test01.get("image05SignificantProperties");
        String s;

        for (ResultTransformer.Entry e : transformedData) {
            assertTrue("Testing that transformed property is recognized: " + e.getKey(), recognizedProperties.contains(e.getKey()) || ignoredProperties.contains(e.getKey()));
            // Make sure it is not ignored first
            if (recognizedProperties.contains(e.getKey())) {
                s = TestHelper.lookForManualValue(e.getKey(), l);
                // If s is null here, then the entry is missing in manual data
                assertNotNull("Testing: " + e.getKey() + " with: " + s, s);
                assertEquals("Testing equality: " + e.getKey(), e.getValue(), s);
            }
        }

        for (int i = 0; i < recognizedProperties.size(); i++) {
            assertTrue("Testing that manual recognized property was transformed: " + recognizedProperties.get(i), TestHelper.lookFor((String) recognizedProperties.get(i), transformedData));
        }
        for (int j = 0; j < ignoredProperties.size(); j++) {
            assertTrue("Testing that manual ignored property was transformed: " + ignoredProperties.get(j), TestHelper.lookFor((String) ignoredProperties.get(j), transformedData));
        }
    }
}

