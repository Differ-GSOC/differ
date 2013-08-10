package cz.nkp.differ.cmdline;

import cz.nkp.differ.cmdline.ValueTester.ValueTester;
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
 * Test class for kdu_expand transformer
 * User: Jonatan Svensson <jonatansve@gmail.com>
 * Date: 2013-07-19
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:kduexpandTestsCtx.xml"})
public class KduExpandUnitTest {
    TestRunner t = new TestRunner();
    String thisExtractor = this.getClass().getSimpleName();

    @Resource
    private Map<String, Object> image14Test01;
    @Resource
    private LinkedHashMap<String, Object> extractorSignificantProperties;
    @Autowired
    private ResultTransformer kakaduMetadataTransformer;

    @Test
    public void testImage14() throws Exception {

        byte[] stdout = TestHelper.readFile("../docs/examples/images_01/14/output-kakadu.raw");
        List<ResultTransformer.Entry> transformedData = kakaduMetadataTransformer.transform(stdout, null);
        ArrayList ignoredProperties = (ArrayList) image14Test01.get("ignoredSignificantProperties");
        ArrayList recognizedProperties = (ArrayList) image14Test01.get("recognizedSignificantProperties");
        LinkedHashMap<String, Object> specialProperties = (LinkedHashMap) image14Test01.get("specialSignificantProperties");
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

