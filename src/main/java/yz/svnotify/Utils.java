package yz.svnotify;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import static yz.svnotify.Constants.JSON;

/**
 * 工具类
 *
 * @author 袁臻
 * 2017/11/23 23:31
 */
final class Utils {

    private static final OkHttpClient client = new OkHttpClient();

    static String post(String url, String json) throws IOException {
        if (url == null) {
            throw new IllegalArgumentException("Dingtalk URL not found, noew exit.");
        }
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
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
                new File("/home/user/project/omts")
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
        final Process process = Runtime.getRuntime().exec(String.format(Constants.COMMAND_SVN_LOG, revision));
        final StringBuilder stringBuilder = new StringBuilder();
        new BufferedReader(new InputStreamReader(process.getInputStream())).lines().forEach(s -> stringBuilder.append(s).append('\n'));
        return SvnLog.of(stringBuilder.toString());
    }
}
