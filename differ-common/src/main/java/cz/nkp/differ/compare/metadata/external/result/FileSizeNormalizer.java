package cz.nkp.differ.compare.metadata.external.result;

import cz.nkp.differ.compare.metadata.external.ResultEntryValueTransformer;
import sun.misc.FloatingDecimal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: stavel
 * Date: 13.2.13
 * Time: 20:38
 */
public class FileSizeNormalizer implements ResultEntryValueTransformer {
    @Override
    public String transform(String value) {
        Matcher unitMatcher = Pattern.compile("^([0-9\\.]+)[ ]*([a-zA-Z]+)").matcher(value);
        if( unitMatcher.find() ) {
            //FloatingDecimal size = FloatingDecimal.readJavaFormatString(unitMatcher.group(1));
            String unit = unitMatcher.group(2);
            if( unit.contains("kB") ){
            }
        }
        return value;
    }
}
