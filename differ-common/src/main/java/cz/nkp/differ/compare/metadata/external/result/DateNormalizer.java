package cz.nkp.differ.compare.metadata.external.result;

import cz.nkp.differ.compare.metadata.external.ResultEntryValueTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Normalize date format to given pattern
 * [2013-01-11 14:47:36+01:00]
 *
 * @author Jonatan Svensson
 * @version 15-07-2013
 */
public class DateNormalizer implements ResultEntryValueTransformer {

    Logger logger = LogManager.getLogger(DateNormalizer.class.getName());
    @Override
    public String transform(String input) {

        logger.debug("Entering DateNormalizer transformer with input: " + input);
        // Normalize date but not time with "-"
        String firstPart= input.substring(0,10);
        firstPart=firstPart.replaceAll(":","-");
        input=firstPart+input.substring(10,input.length());
        // Delimiter is whitespace and don't replace T in Tue
        String tempString = input.substring(1,input.length());
        tempString= tempString.replace("T"," ");
        input=input.substring(0,1)+tempString;
        // If pattern is correct [2013-01-11 14:47:36+01:00], just return the value
        if(!Pattern.compile("\\d\\d\\d\\d-\\d\\d-\\d\\d\\s\\d\\d:\\d\\d:\\d\\d(\\+||\\-)\\d\\d:\\d\\d").matcher(input).matches()){
            String result="";
            SimpleDateFormat sdfSource = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy",Locale.ENGLISH);
            try {
                // Convert from  [Fri Jan 11 14:47:36 2013] to desired pattern
                Date sourceDate = sdfSource.parse(input);
                SimpleDateFormat sdfDestination = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ", Locale.ENGLISH);
                result=sdfDestination.format(sourceDate);
            // Normalize standard dddd to dd:dd in SimpleDate locale
            result = new StringBuilder(result).insert(result.length()-2, ":").toString();
            logger.debug("Returning: "+result);
            }  catch (ParseException e) {
                logger.error("Failed to parse date in DateNormalizer. Please revise input: " +input);
            }
            return result;
        }
        logger.debug("Returning: "+input);
        return input;
    }
}

