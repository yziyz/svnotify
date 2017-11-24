package yz.svnotify;

import okhttp3.MediaType;

import java.util.regex.Pattern;

/**
 * 常量类s
 *
 * @author 袁臻
 * 2017/11/24 10:38
 */
final class Constants {

    static final String COMMAND_SVN_UPDATE = "svn -q update";

    static final String COMMAND_SVN_REVISION = "svn info --show-item=revision";

    static final String COMMAND_SVN_LOG = "svn -v -r%s log";

    static final Pattern PATTERN = Pattern.compile("^\\-{72}\\n(?<revision>r.+?) \\| (?<author>.+?) \\| (?<time>.+?) " +
            "\\| (.+?)\\n(Changed paths:\\n(?<changedPaths>(   M .+?)\\n)+?)\\n(?<commitMessage>.+?)\\n\\-{72}\n$");

    static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    static final String DING_TALK_URL = System.getProperty("dingTalkUrl");

    static final String MESSAGE_TEMPLATE = "{\"msgtype\":\"text\",\"text\":{\"content\":\"%s\"}}";
}
