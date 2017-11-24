package yz.svnotify;

import java.io.IOException;
import java.util.logging.Logger;

import static yz.svnotify.Utils.getHeadRevision;
import static yz.svnotify.Utils.getLog;
import static yz.svnotify.Utils.update;

/**
 * Main class
 *
 * @author yziyz
 * 2017/11/23 16:56
 */
public final class Main {

    /**
     * Logger instance
     */
    private static Logger log = Logger.getLogger(Main.class.getName());

    /**
     * Main method
     *
     * @param args commandline arguments list
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        update();
        log.info("Updated");
        final String revision = getHeadRevision();
        log.info("Revision: ".concat(revision));
        final SvnLog svnLog = getLog(revision);
        log.info(svnLog.toString());
        final String response = Utils.post(Constants.DING_TALK_URL, String.format(Constants.MESSAGE_TEMPLATE, svnLog.toString()));
        log.info("Response: \n".concat(response));
    }
}
