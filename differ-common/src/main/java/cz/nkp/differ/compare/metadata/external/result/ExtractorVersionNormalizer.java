package cz.nkp.differ.compare.metadata.external.result;

import cz.nkp.differ.compare.metadata.external.ResultEntryValueTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Normalize version of extractor to format 1.113.15.1 etc

 * @author Jonatan Svensson
 * @version 06-08-2013
 */
public class ExtractorVersionNormalizer implements ResultEntryValueTransformer {

    Logger logger = LogManager.getLogger(ExtractorVersionNormalizer.class.getName());
    @Override
    public String transform(String input) {

        logger.debug("Entering ExtractorVersionNormalizer transformer with input: " + input);
        Pattern p = Pattern.compile("ImageMagick\\s([0-9\\.-]+)\\s");
        Matcher m = p.matcher(input);
        // ImageMagick
        if(m.find()){
            String result=m.group(1);
            result=result.replace("-",".");
            logger.debug("Returning: "+result);
            return result;
        }
        // Jpylyzer
        p=Pattern.compile("jpylyzer\\s([0-9\\.]+)");
        m = p.matcher(input);
        if(m.find()){
            String result=m.group(1);
            logger.debug("Returning: "+result);
            return result;
        }
        // Fits
        p=Pattern.compile("FITS\\s([0-9\\.]+)");
        m = p.matcher(input);
        if(m.find()){
            String result=m.group(1);
            logger.debug("Returning: "+result);
            return result;
        }
        // Daitss
        p=Pattern.compile("info:fda/daitss/description/v([0-9\\.]+)");
        m = p.matcher(input);
        if(m.find()){
            String result=m.group(1);
            logger.debug("Returning: "+result);
            return result;
        }
        logger.debug("Returning: "+input);
        return input;
    }
}

