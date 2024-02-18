package com.quantcast.app;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MainTest {
    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();
    @Rule
    public final SystemErrRule systemErr = new SystemErrRule().enableLog().mute();
    @Rule
    public final SystemOutRule systemOut = new SystemOutRule().enableLog().mute();

    @Test
    public void testBuilder() {
        final CommandLine.Builder builder = new CommandLine.Builder();
        builder.addArg("foo").addArg("bar");
        builder.addOption(Option.builder("T").build());
        final CommandLine cmd = builder.build();

        assertEquals("foo", cmd.getArgs()[0]);
        assertEquals("bar", cmd.getArgList().get(1));
        assertEquals("T", cmd.getOptions()[0].getOpt());
    }

    @Ignore
    @Test
    public void whenInvalidOption_returnErrorMessage() {
        String[] args = new String[2];

        args[0] = "-x";
        args[1] = "-y";

        exit.checkAssertionAfterwards(() -> assertNotEquals("", systemOut.getLog()));
        Main.main(args);
    }

    @Ignore
    @Test
    public void invalidDateFormat_returnMessage() throws Exception {
        String[] args = new String[4];

        args[0] = "-f";
        args[1] = "";
        args[2] = "-d";
        args[3] = "01-22-2023";

        exit.checkAssertionAfterwards(() -> assertNotEquals("Invalid date format, it should be YYYY-MM-DD\n", systemOut.getLog()));

        Main.main(args);
    }

    @Test
    public void shouldReturnOneCookie() {
        String[] args = new String[4];

        args[0] = "-f";
        args[1] = "src/test/resources/cookie_log.csv";
        args[2] = "-d";
        args[3] = "2018-12-09";

        Main.main(args);

        assertEquals("Most active cookies for 2018-12-09:\n" +
                "AtY0laUfhglK3lC7\n",
                systemOut.getLog());
    }

    @Test
    public void shouldReturnTwoCookies() {
        String[] args = new String[4];

        args[0] = "-f";
        args[1] = "src/test/resources/cookie_log_multiple.csv";
        args[2] = "-d";
        args[3] = "2018-12-09";

        Main.main(args);

        assertEquals("Most active cookies for 2018-12-09:\n" +
                        "AtY0laUfhglK3lC7\n" +
                "5UAVanZf6UtGyKVS\n",
                systemOut.getLog());
    }

    @Ignore
    @Test
    public void checkInvalidFile() {
        String[] args = new String[4];

        args[0] = "-f";
        args[1] = "src/test/resources/cookie_log.pdf";
        args[2] = "-d";
        args[3] = "2018-12-09";

        Main.main(args);

        assertEquals("Most active cookies for 2018-12-09:\n" +
                        "AtY0laUfhglK3lC7\n" +
                        "5UAVanZf6UtGyKVS\n",
                systemOut.getLog());
    }

}