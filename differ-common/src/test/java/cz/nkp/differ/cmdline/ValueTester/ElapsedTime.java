package cz.nkp.differ.cmdline.ValueTester;

import cz.nkp.differ.cmdline.ValueTester.ValueTester;

/**
 * User: Jonatan Svensson <jonatansve@gmail.com>
 * Date: 2013-08-05
 * Time: 12:22
 */
public class ElapsedTime implements ValueTester {
    @Override
    public boolean test(String value) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean test(String value, String reference) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setDescription(String value) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getDescription() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
