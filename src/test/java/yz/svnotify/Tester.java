package yz.svnotify;


import org.junit.Test;

import java.nio.file.Paths;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 袁臻
 * 2017/11/23 22:45
 */
public class Tester {

    final String log = "------------------------------------------------------------------------\n" +
            "r1276 | yuanzhen | 2017-11-28 18:15:11 +0800 (二, 28 11月 2017) | 1 line\n" +
            "Changed paths:\n" +
            "   M /trunk/SourceCode/JAVA/omts/pom.xml\n" +
            "   A /trunk/SourceCode/JAVA/omts/src/main/java/cn/com/vdin/cato/dto/admin/BizPushTaskDTO.java\n" +
            "   M /trunk/SourceCode/JAVA/omts/src/main/java/cn/com/vdin/cato/service/AppPushService.java\n" +
            "   M /trunk/SourceCode/JAVA/omts/src/main/java/cn/com/vdin/cato/web/admin/AppPushController.java\n" +
            "\n" +
            "添加“APP推送控制器”，测试升级Pavilion至3.0.7\n" +
            "------------------------------------------------------------------------\n";

    final String pattern = "^-{72}\\n(?<revision>r.+?) \\| (?<author>.+?) \\| (?<time>.+?) \\| (.+?)\\n(Changed paths:\\n(?<changedPaths>(   M .+?)\\n)+?)\\n(?<commitMessage>.+?)\\n\\-{72}\n$";

    final Pattern compile = Pattern.compile(pattern);

    @Test
    public void match() {
        final Matcher matcher = compile.matcher(log);
        System.out.println(matcher.find());
        final MatchResult matchResult = matcher.toMatchResult();
        for (int i = 1; i <= matchResult.groupCount(); i++) {
            System.out.println(i + ":\n" + matchResult.group(i));
        }
    }

    @Test
    public void getWorkingPath() {
        //System.out.println(System.getProperty("user.dir"));
        System.out.println(Paths.get(".").toAbsolutePath().normalize().toString());
    }

    @Test
    public void info() {
    }
}
