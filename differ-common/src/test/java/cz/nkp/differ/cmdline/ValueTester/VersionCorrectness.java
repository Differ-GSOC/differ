package cz.nkp.differ.cmdline.ValueTester;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Jonatan Svensson <jonatansve@gmail.com>
 * Date: 2013-08-02
 * Time: 11:50
 */
public class VersionCorrectness implements ValueTester {
    Logger logger = LogManager.getLogger(VersionCorrectness.class.getName());

    private String description;
    private Map<String, List> extractorVersions;

    @Override
    public boolean test(String value, String extractorName) {
        logger.debug("Testing: " + value + " for extractor: " + extractorName);

        List<String> versionList = extractorVersions.get(extractorName);
        // Format of version should be: one or more digits, followed by dot, an unlimited amount of times. Cannot end with dot.
        // e.g. 1.14.133
        Pattern r = Pattern.compile("^[0-9]*([.]{1}||[0-9]*)*[^.](\\(latest known\\)||(?!))$");
        Matcher m = r.matcher(value);
        if (m.find()) {
            logger.debug("Pattern matched");
            if (versionList.contains(value)) {
                logger.debug("Version found in accepted list");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean test(String value) {
        return false;
    }

    @Override
    public void setDescription(String value) {
        description = value;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setExtractorVersions(Map<String, List> l) {
        extractorVersions = l;
    }

    public Map<String, List> getExtractorVersions() {
        return extractorVersions;
    }
}
