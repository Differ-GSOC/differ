package cz.nkp.differ.cmdline;

import cz.nkp.differ.cmdline.ValueTester.ValueTester;
import cz.nkp.differ.compare.metadata.external.ResultTransformer;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * User: Jonatan Svensson <jonatansve@gmail.com>
 * Date: 2013-08-05
 * Time: 15:15
 */
public class TestRunner {

    public void runStandardTests(List<ResultTransformer.Entry> transformedData,
                                 ArrayList recognizedProperties,
                                 ArrayList ignoredProperties,
                                 LinkedHashMap<String, Object> specialProperties,
                                 LinkedHashMap<String, Object> extractorProperties,
                                 LinkedHashMap significantProperties,
                                 String thisExtractor) {
        /**
         * Test context validity
         */
        assertNotNull(transformedData);
        assertNotNull(recognizedProperties);
        assertNotNull(ignoredProperties);
        assertNotNull(specialProperties);
        assertNotNull(extractorProperties);
        assertNotNull(significantProperties);
        assertNotNull(thisExtractor);

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
         * in the ignoredProperties list for tests to pass.
         * Assert that the value is identical if found.
         */
        String s;
        String imageName = (String) significantProperties.get("filePath");
        for (ResultTransformer.Entry e : transformedData) {
            // Property is definitely handled
            assertTrue(thisExtractor + ">>" + imageName + ">> " + "Testing that transformed property is recognized: " + e.getKey(), recognizedProperties.contains(e.getKey())
                    || ignoredProperties.contains(e.getKey())
                    || specialProperties.containsKey(e.getKey())
                    || extractorProperties.containsKey(e.getKey()));
            // Make sure it is not ignored or special
            if (recognizedProperties.contains(e.getKey())) {
                s = TestHelper.lookForManualValue(e.getKey(), significantProperties);
                // If s is null here, then the value is missing in manual data
                assertNotNull(thisExtractor + ">>" + imageName + ">> " + "Testing: " + e.getKey() + " with: " + s, s);
                assertEquals(thisExtractor + ">>" + imageName + ">> " + "Testing equality: " + e.getKey(), e.getValue(), s);
                s = null;
            }
            // Some values are not exact and are handled especially, so we test them in their own group
            else if (specialProperties.containsKey(e.getKey())) {
                ValueTester tester = (ValueTester) specialProperties.get(e.getKey());
                assertTrue(thisExtractor + ">>" + imageName + ">> " + "Testing equality for special property: " + e.getKey(), tester.test(e.getValue(), TestHelper.lookForManualValue(e.getKey(), significantProperties)));
            } else if (extractorProperties.containsKey(e.getKey())) {
                ValueTester tester = (ValueTester) extractorProperties.get(e.getKey());
                assertTrue(thisExtractor + ">>" + imageName + ">> " + "Testing equality for extractor " + thisExtractor + " property: " + e.getKey(), tester.test(e.getValue(), thisExtractor));
            }
        }
        /**
         * Last: 1.Check conversely that the recognized properties in test context
         * match the transformed data exactly (no extra entries in list).
         * 2. Ignored properties should also be in the transformed list.
         */

        for (int i = 0; i < recognizedProperties.size(); i++) {
            assertTrue(thisExtractor + ">>" + imageName + ">> " + "Testing that manual recognized property was transformed: " + recognizedProperties.get(i),
                    TestHelper.lookFor((String) recognizedProperties.get(i), transformedData));
        }

        for (String key : specialProperties.keySet()) {
            assertTrue(thisExtractor + ">>" + imageName + ">> " + "Testing that special recognized property was transformed: " + key,
                    TestHelper.lookFor(key, transformedData));
        }
        for (int i = 0; i < ignoredProperties.size(); i++) {
            assertTrue(thisExtractor + ">>" + imageName + ">> " + "Testing that manual ignored property was transformed: " + ignoredProperties.get(i),
                    TestHelper.lookFor((String) ignoredProperties.get(i), transformedData));
        }

    }
}
