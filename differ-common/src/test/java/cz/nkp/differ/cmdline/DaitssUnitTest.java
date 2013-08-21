package cz.nkp.differ.cmdline;

import cz.nkp.differ.compare.metadata.external.ResultTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daitssTestsCtx.xml"})
public class DaitssUnitTest {
    TestRunner t = new TestRunner();
    String thisExtractor = this.getClass().getSimpleName();


    @Resource
    private Map<String, Object> image14Test01;
    @Resource
    private LinkedHashMap<String, Object> extractorSignificantProperties;
    @Autowired
    private ResultTransformer daitssMetadataTransformer;

    Logger logger = LogManager.getLogger(DaitssUnitTest.class.getName());

    @Test
    public void testImage14() throws Exception {
        byte[] stdout = TestHelper.readFile("../docs/examples/images_01/14/output-daitss.raw");
        List<ResultTransformer.Entry> transformedData = daitssMetadataTransformer.transform(stdout, null);
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
