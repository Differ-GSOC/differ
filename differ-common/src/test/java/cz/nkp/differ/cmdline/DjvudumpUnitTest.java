package cz.nkp.differ.cmdline;

import cz.nkp.differ.compare.metadata.external.ResultTransformer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.*;

import static org.junit.Assert.*;


/**
 * Test class for Djvudump transformer
 * User: Jonatan Svensson <jonatansve@gmail.com>
 * Date: 2013-07-19
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:djvudumpTestsCtx.xml"})
public class DjvudumpUnitTest {
    String thisExtractor = this.getClass().getSimpleName();
    TestRunner t = new TestRunner();
    @Resource
    private Map<String, Object> image05Test01;

    @Resource
    private LinkedHashMap<String, Object> extractorSignificantProperties;

    @Autowired
    private ResultTransformer djvudumpMetadataTransformer;

    @Test
    public void testImage05() throws Exception {

        byte[] stdout = TestHelper.readFile("../docs/examples/images_03/05/output-djvudump.raw");
        List<ResultTransformer.Entry> transformedData = djvudumpMetadataTransformer.transform(stdout, null);
        ArrayList ignoredProperties = (ArrayList) image05Test01.get("ignoredSignificantProperties");
        ArrayList recognizedProperties = (ArrayList) image05Test01.get("recognizedSignificantProperties");
        LinkedHashMap<String, Object> specialProperties = (LinkedHashMap<String, Object>) image05Test01.get("specialSignificantProperties");
        LinkedHashMap significantProperties = (LinkedHashMap) image05Test01.get("significantProperties");

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

