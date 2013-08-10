package cz.nkp.differ.cmdline.ValueTester;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Jonatan Svensson <jonatansve@gmail.com>
 * Date: 2013-08-01
 * Time: 10:13
 */
public class ValueInRange implements ValueTester {

    private String description;
    private int range;

    @Override
    public boolean test(String value) {
        return false;
    }

    /**
     * Verify that value is within: reference+-range
     *
     * @param value     input value
     * @param reference reference to compare with
     * @return true if in the range
     *         false if not
     */
    @Override
    public boolean test(String value, String reference) {
        int i = 0;
        int j = 0;
        Pattern r = Pattern.compile("[0-9]+(?=,)");
        Matcher m = r.matcher(value);
        if (m.find()) {
            i = Integer.valueOf(m.group(0));

        }
        Matcher m1 = r.matcher(reference);
        if (m1.find()) {
            j = Integer.valueOf(m1.group(0));
        }

        if (Math.abs(i - j) < range) {
            return true;
        }
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

    public void setRange(int range) {
        this.range = range;
    }

    public int getRange() {
        return range;
    }

}
