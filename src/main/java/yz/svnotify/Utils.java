package yz.svnotify;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

/**
 * 工具类
 *
 * @author 袁臻
 * 2017/11/23 23:31
 */
final class Utils {

    private static final Logger log = Logger.getLogger(Utils.class.getName());

    /**
     * HTTP的POST方法
     *
     * @param url  统一资源定位符
     * @param json JSON字符串
     * @return 响应字符串
     */
    static String post(String url, String json) {
        if (url == null) {
            throw new IllegalArgumentException("Dingtalk URL not found, now exit.");
        }
        final RequestBody body = RequestBody.create(Constants.JSON, json);
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        final ResponseBody responseBody;
        try {
            responseBody = new OkHttpClient().newCall(request).execute().body();
            return responseBody != null ? responseBody.string() : null;
        } catch (IOException e) {
            log.warning("POST消息异常");
            throw new IllegalStateException(e);
        }
    }

    static void update() throws IOException, InterruptedException {
        Runtime.getRuntime().exec(
                Constants.COMMAND_SVN_UPDATE
        ).waitFor();
    }

    /**
     * Getting HEAD revision
     *
     * @return HEAD revision
     * @throws IOException          If an I/O error occurs
     * @throws InterruptedException if the current thread is
     *                              {@linkplain Thread#interrupt() interrupted} by another
     *                              thread while it is waiting, then the wait is ended and
     *                              an {@link InterruptedException} is thrown.
     */
    static String getHeadRevision() throws IOException, InterruptedException {
        //Create a process to execute "svn info --show-item=revision" command
        final Process process = Runtime.getRuntime().exec(
                Constants.COMMAND_SVN_REVISION,
                null,
                Constants.WORKING_DIRECTORY
        );
        //Get STDOUT lines
        final List<String> lines = new BufferedReader(new InputStreamReader(process.getInputStream()))
                .lines()
                .collect(Collectors.toList());
        //Block until process terminated
        process.waitFor();
        if (process.exitValue() == 0) {
            //Return first string
            return lines.get(0);
        } else {
            //Exception occurred
            throw new IllegalStateException("Exception occurred while getting HEAD revision, now Exit.");
        }
    }

    static SvnLog getLog(final String revision) throws IOException {
        //查看日志
        final String logCommand = String.format(Constants.COMMAND_SVN_LOG, revision);
        log.info("路径:" + Constants.WORKING_DIRECTORY.getAbsolutePath() + "，命令:" + logCommand);
        final Process logProcess = Runtime.getRuntime().exec(
                logCommand,
                null,
                Constants.WORKING_DIRECTORY
        );
        final SvnLog svnLog = SvnLog.builder().build();
        //获取进程输出字符串列表
        final List<String> logLines = new BufferedReader(new InputStreamReader(logProcess.getInputStream()))
                .lines()
                .collect(Collectors.toList());
        //若行数小于5，抛出异常
        if (logLines.size() < Constants.MIN_LINES_COUNT) {
            throw new IllegalStateException("日志信息异常: " + logLines);
        }
        //解析日志
        final Matcher logMatcher = Constants.LOG_PATTERN.matcher(logLines.get(1));
        if (!logMatcher.find()) {
            throw new IllegalStateException("解析日志信息出错");
        } else {
            svnLog.setRevision(logMatcher.group("revision").substring(1));
            svnLog.setAuthor(logMatcher.group("author"));
            svnLog.setTime(logMatcher.group("time").substring(0, 19));
        }
        //设置提交消息
        svnLog.setCommitMessage(logLines.get(logLines.size() - 2));

        //项目名称
        log.info("路径:" + Constants.WORKING_DIRECTORY.getAbsolutePath() + "，命令:" + Constants.COMMAND_SVN_URL);
        final Process projectNameProcess = Runtime.getRuntime().exec(
                Constants.COMMAND_SVN_URL,
                null,
                Constants.WORKING_DIRECTORY
        );
        final List<String> projectNameLines = new BufferedReader(new InputStreamReader(projectNameProcess.getInputStream()))
                .lines()
                .collect(Collectors.toList());
        if (!projectNameLines.isEmpty()) {
            final Matcher projectNameMatcher = Constants.PROJECT_NAME_PATTERN.matcher(projectNameLines.get(0));
            if (projectNameMatcher.find()) {
                final String projectName = projectNameMatcher.group(1);
                svnLog.setProjectName(projectName);
            }
        }
        return svnLog;
    }
}
