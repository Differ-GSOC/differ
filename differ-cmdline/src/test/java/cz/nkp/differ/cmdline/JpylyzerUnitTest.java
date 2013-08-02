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
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:jpylyzerTestsCtx.xml"})
public class JpylyzerUnitTest {

    List<ResultTransformer.Entry> transformedData;
    @Autowired
    private Map<String, Object> image14Test01;

    @Autowired
    private ResultTransformer jpylyzerMetadataTransformer;

    Logger logger = LogManager.getLogger(JpylyzerUnitTest.class.getName());

    @Test
    public void testImage14() throws Exception {
        byte[] stdout = TestHelper.readFile("../docs/examples/images_01/14/output-jpylyzer.raw");
        transformedData = jpylyzerMetadataTransformer.transform(stdout, null);
        assertNotNull(transformedData);

        /**
         * Test all properties are mapped.
         * Compare transformedData with list of
         * manual input of significant properties in image14Test01RecognizedProperties
         * Fails if a property is transformed but is yet not mapped.
         *
         *
         * Test all properties that are not ignored.
         * Go through each entry in transformedData,
         * Look for the key in:
         * identificationProperties/validationProperties/characterizationProperties,
         * Properties not in this significant properties list yield null and should be put
         * in the ignoredProperties list for the tests to pass.
         * Assert that the value is identical.
         */

        ArrayList ignoredProperties = (ArrayList) image14Test01.get("image14Test01IgnoredProperties");
        ArrayList recognizedProperties = (ArrayList) image14Test01.get("image14Test01RecognizedProperties");
        assertNotNull(recognizedProperties);

        LinkedHashMap l = (LinkedHashMap) image14Test01.get("image14SignificantProperties");
        String s;
        for (Entry e : transformedData) {
            assertTrue("Testing that transformed property is recognized: " + e.getKey(), recognizedProperties.contains(e.getKey()) || ignoredProperties.contains(e.getKey()));
            // Make sure it is not ignored first
            if (recognizedProperties.contains(e.getKey())) {
                s = TestHelper.lookForManualValue(e.getKey(), l);
                // If s is null here, then the entry is missing in manual data
                assertNotNull("Testing: " + e.getKey() + " with: " + s, s);
                assertEquals("Testing equality: " + e.getKey(), e.getValue(), s);
            }
        }
        /**
         * Last: 1.Check conversely that the recognized properties in test context
         * match the transformed data exactly (no extra entries in list).
         * 2. Ignored properties should also be in the transformed list.
         */

        for (int i = 0; i < recognizedProperties.size(); i++) {
            assertTrue("Testing that manual recognized property was transformed: " + recognizedProperties.get(i), TestHelper.lookFor((String) recognizedProperties.get(i), transformedData));
        }
        for (int j = 0; j < ignoredProperties.size(); j++) {
            assertTrue("Testing that manual ignored property was transformed: " + ignoredProperties.get(j), TestHelper.lookFor((String) ignoredProperties.get(j), transformedData));
        }
    }
}

