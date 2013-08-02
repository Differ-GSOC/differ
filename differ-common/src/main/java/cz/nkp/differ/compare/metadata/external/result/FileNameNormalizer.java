package cz.nkp.differ.compare.metadata.external.result;

import cz.nkp.differ.compare.metadata.external.ResultEntryValueTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: stavel
 * Date: 13.2.13
 * Time: 20:09
 */
public class FileNameNormalizer implements ResultEntryValueTransformer {
    Logger logger = LogManager.getLogger(FilePathNormalizer.class.getName());

    @Override
    public String transform(String value) {
        logger.debug("Entering FileNameNormalizer with value: "+ value);

        File file = new File(value);
        String fileName = file.getName();
        logger.debug("Returning: "+ fileName);
        return fileName;
    }
}
