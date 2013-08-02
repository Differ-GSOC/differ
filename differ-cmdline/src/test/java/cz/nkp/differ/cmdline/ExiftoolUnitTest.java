package cz.nkp.differ.cmdline;

import cz.nkp.differ.compare.metadata.external.ResultTransformer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


/**
 * Test class for Exiftool transformer
 * User: Jonatan Svensson <jonatansve@gmail.com>
 * Date: 2013-07-19
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:exiftoolTestsCtx.xml"})
public class ExiftoolUnitTest {
    List<ResultTransformer.Entry> transformedData;
    @Resource
    private Map<String, Object> image01Test01;
    @Resource
    private Map<String, Object> image14Test02;


    @Autowired
    private ResultTransformer exiftoolMetadataTransformer;

    @Test
    public void testImage01() throws Exception {

        byte[] stdout = TestHelper.readFile("../docs/examples/images_02/01/output-exiftool.raw");
        transformedData = exiftoolMetadataTransformer.transform(stdout, null);
        assertNotNull(transformedData);

        ArrayList ignoredProperties = (ArrayList) image01Test01.get("ignoredSignificantProperties");
        ArrayList recognizedProperties = (ArrayList) image01Test01.get("recognizedSignificantProperties");
        LinkedHashMap<String, Object> specialProperties = (LinkedHashMap) image01Test01.get("specialSignificantProperties");

        assertNotNull(recognizedProperties);

        LinkedHashMap l = (LinkedHashMap) image01Test01.get("significantProperties");
        String s;
        for (ResultTransformer.Entry e : transformedData) {
            // Property is definitely handled
            assertTrue("Testing that transformed property is recognized: " + e.getKey(), recognizedProperties.contains(e.getKey())
                    || ignoredProperties.contains(e.getKey())
                    || specialProperties.containsKey(e.getKey()));
            // Make sure it is not ignored or special
            if (recognizedProperties.contains(e.getKey())) {
                s = TestHelper.lookForManualValue(e.getKey(), l);
                // If s is null here, then the value is missing in manual data
                assertNotNull("Testing: " + e.getKey() + " with: " + s, s);
                assertEquals("Testing equality: " + e.getKey(), e.getValue(), s);
                s = null;
            }
            // Some values are not exact and are handled especially, so we test them in their own group
            else if (specialProperties.containsKey(e.getKey())) {
                ValueTester tester = (ValueTester)specialProperties.get(e.getKey());
                assertTrue("Testing equality for special property: " + e.getKey(), tester.test(e.getValue(), TestHelper.lookForManualValue(e.getKey(), l)));
            }
        }

        for (int i = 0; i < recognizedProperties.size(); i++) {
            assertTrue("Testing that manual recognized property was transformed: " + recognizedProperties.get(i),
                    TestHelper.lookFor((String) recognizedProperties.get(i), transformedData));
        }

        for(String key: specialProperties.keySet()){
            assertTrue("Testing that special recognized property was transformed: " + key,
                    TestHelper.lookFor(key, transformedData));
        }
        for (int i = 0; i < ignoredProperties.size(); i++) {
            assertTrue("Testing that manual ignored property was transformed: " + ignoredProperties.get(i),
                    TestHelper.lookFor((String) ignoredProperties.get(i), transformedData));
        }
    }

    @Test
    public void testImage14() throws Exception {
        byte[] stdout = TestHelper.readFile("../docs/examples/images_01/14/output-exiftool.raw");
        transformedData = exiftoolMetadataTransformer.transform(stdout, null);
        assertNotNull(transformedData);

        ArrayList ignoredProperties = (ArrayList) image14Test02.get("ignoredSignificantProperties");
        ArrayList recognizedProperties = (ArrayList) image14Test02.get("recognizedSignificantProperties");
        LinkedHashMap<String, Object> specialProperties = (LinkedHashMap) image14Test02.get("specialSignificantProperties");

        assertNotNull(recognizedProperties);

        LinkedHashMap l = (LinkedHashMap) image14Test02.get("significantProperties");
        String s;
        for (ResultTransformer.Entry e : transformedData) {
            // Property is definitely handled
            assertTrue("Testing that transformed property is recognized: " + e.getKey(), recognizedProperties.contains(e.getKey())
                    || ignoredProperties.contains(e.getKey())
                    || specialProperties.containsKey(e.getKey()));
            // Make sure it is not ignored or special
            if (recognizedProperties.contains(e.getKey())) {
                s = TestHelper.lookForManualValue(e.getKey(), l);
                // If s is null here, then the value is missing in manual data
                assertNotNull("Testing: " + e.getKey() + " with: " + s, s);
                assertEquals("Testing equality: " + e.getKey(), e.getValue(), s);
            }
            // Some values are not exact and are handled especially, so we test them in their own group
            else if (specialProperties.containsKey(e.getKey())) {
                ValueTester tester = (ValueTester)specialProperties.get(e.getKey());
                assertTrue("Testing equality for special property: " + e.getKey(), tester.test(e.getValue(), TestHelper.lookForManualValue(e.getKey(), l)));
            }
        }

        for (int i = 0; i < recognizedProperties.size(); i++) {
            assertTrue("Testing that manual recognized property was transformed: " + recognizedProperties.get(i),
                    TestHelper.lookFor((String) recognizedProperties.get(i), transformedData));
        }
        for(String key: specialProperties.keySet()){
            assertTrue("Testing that special recognized property was transformed: " + key,
                    TestHelper.lookFor(key, transformedData));
        }
        for (int i = 0; i < ignoredProperties.size(); i++) {
            assertTrue("Testing that manual ignored property was transformed: " + ignoredProperties.get(i),
                    TestHelper.lookFor((String) ignoredProperties.get(i), transformedData));
        }
    }
}


