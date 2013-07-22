package cz.nkp.differ.compare.metadata.external.result;

import cz.nkp.differ.compare.metadata.external.ResultEntryValueTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: stavel
 * Date: 13.2.13
 * Time: 20:09
 */
public class FilePathNormalizer implements ResultEntryValueTransformer {
    Logger logger = LogManager.getLogger(FilePathNormalizer.class.getName());


    @Override
    public String transform(String value) {
        logger.debug("Entering FilePathNormalizer with value: "+ value);
        Matcher m = Pattern.compile("/(?=([^/]+\\.[^/]+)$)").matcher(value);
        if(m.find()){
            logger.debug("Found "+ m.group(1));

            value=m.group(1);
        }
        logger.debug("Converted to: "+ value);

        String filePath = null;

        File file = new File(value);
        try {
            filePath = file.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.debug("Returning: "+ filePath);

        return filePath;
    }
}
