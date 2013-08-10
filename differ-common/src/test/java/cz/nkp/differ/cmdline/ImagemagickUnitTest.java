package cz.nkp.differ.cmdline;

import cz.nkp.differ.cmdline.ValueTester.ValueTester;
import cz.nkp.differ.compare.metadata.external.ResultTransformer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


/**
 * Test class for Imagemagick transformer
 * User: Jonatan Svensson <jonatansve@gmail.com>
 * Date: 2013-07-19
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:imagemagickTestsCtx.xml"})
public class ImagemagickUnitTest {
    TestRunner t = new TestRunner();
    String thisExtractor = this.getClass().getSimpleName();

    @Resource
    private Map<String, Object> image01Test01;
    @Resource
    private ResultTransformer imagemagickMetadataTransformer;
    @Resource
    private LinkedHashMap<String, Object> extractorSignificantProperties;

    @Test
    public void testImage01() throws Exception {

        byte[] stdout = TestHelper.readFile("../docs/examples/images_01/01/output-imagemagick.raw");
        List<ResultTransformer.Entry> transformedData = imagemagickMetadataTransformer.transform(stdout, null);
        ArrayList ignoredProperties = (ArrayList) image01Test01.get("ignoredSignificantProperties");
        ArrayList recognizedProperties = (ArrayList) image01Test01.get("recognizedSignificantProperties");
        LinkedHashMap<String, Object> specialProperties = (LinkedHashMap) image01Test01.get("specialSignificantProperties");
        LinkedHashMap significantProperties = (LinkedHashMap) image01Test01.get("significantProperties");

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

