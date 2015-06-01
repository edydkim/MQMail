package org.mail.config;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;


public class MailCommandLineParser {
    private static final Logger log = LoggerFactory.getLogger(MailCommandLineParser.class);

    private static Options options;

    private final long HOOKING_INTERVAL = 1000L;

    static {
        options = new Options();
        options.addOption(OptionBuilder.withLongOpt("help").withDescription("show this help").create("h"));
        options.addOption(OptionBuilder.withLongOpt("startup").withDescription("startup with mail hooker").create("s"));
        options.addOption(OptionBuilder.withLongOpt("test-mode").withDescription("startup with test mode and ignore option").create("t"));
        options.addOption(OptionBuilder.withLongOpt("interval").hasArg().isRequired(false).withDescription("hooking interval (milliseconds) for OracleAQ").create("i"));
    }

    public MailCommandLine parse(String[] args) throws Exception {
        MailCommandLine line = new MailCommandLine();
        CommandLineParser parser = new PosixParser();
        CommandLine cl = parser.parse(options, args);

        line.setHelp(cl.hasOption("h"));
        line.setStartup(cl.hasOption("s"));
        line.setIsTestMode(cl.hasOption("t"));
        if (cl.hasOption("i"))
            line.setHookingInterval(Integer.parseInt(cl.getOptionValue("i", String.valueOf(HOOKING_INTERVAL))));
        else
            line.setHookingInterval(HOOKING_INTERVAL);

        return line;
    }

    public void help() {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out, Charset.forName("US-ASCII")))) {
            HelpFormatter formatter = new HelpFormatter();
            StringWriter tmpBuf = new StringWriter();
            formatter.printUsage(new PrintWriter(tmpBuf), formatter.getWidth(),
                    "MQMail", options);
            pw.write(tmpBuf.getBuffer().toString().trim() + "\n");
            formatter.printOptions(pw, formatter.getWidth(), options,
                    formatter.getLeftPadding(), formatter.getDescPadding());
        }
    }
}
