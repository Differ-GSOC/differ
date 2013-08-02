package cz.nkp.differ.cmdline;

/**
 * User: Jonatan Svensson <jonatansve@gmail.com>
 * Date: 2013-08-01
 * Time: 10:11
 */
public interface ValueTester {
    boolean test(String value, String reference);

    void setDescription(String value);

    String getDescription();
}
