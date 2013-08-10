package cz.nkp.differ.cmdline;

import cz.nkp.differ.cmdline.ValueTester.ValueTester;
import cz.nkp.differ.compare.metadata.external.ResultTransformer;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
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
    TestRunner t = new TestRunner();
    String thisExtractor = this.getClass().getSimpleName();


    @Resource
    private Map<String, Object> image14Test01;
    @Resource
    private LinkedHashMap<String, Object> extractorSignificantProperties;
    @Autowired
    private ResultTransformer jpylyzerMetadataTransformer;

    Logger logger = LogManager.getLogger(JpylyzerUnitTest.class.getName());

    @Test
    public void testImage14() throws Exception {
        byte[] stdout = TestHelper.readFile("../docs/examples/images_01/14/output-jpylyzer.raw");
        List<ResultTransformer.Entry> transformedData = jpylyzerMetadataTransformer.transform(stdout, null);
        ArrayList ignoredProperties = (ArrayList) image14Test01.get("ignoredSignificantProperties");
        ArrayList recognizedProperties = (ArrayList) image14Test01.get("recognizedSignificantProperties");
        LinkedHashMap<String, Object> specialProperties = (LinkedHashMap) image14Test01.get("specialSignificantProperties");
        assertNotNull(recognizedProperties);
        LinkedHashMap significantProperties = (LinkedHashMap) image14Test01.get("significantProperties");

        t.runStandardTests(transformedData,
                recognizedProperties,
                ignoredProperties,
                specialProperties,
                extractorSignificantProperties,
                significantProperties,
                thisExtractor
        );
    }
}
