package yz.svnotify;


import org.junit.Assert;
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
            "r1218 | yanglei | 2017-11-24 16:13:51 +0800 (五, 24 11月 2017) | 1 line\n" +
            "Changed paths:\n" +
            "   M /trunk/SourceCode/JAVA/omts/src/main/java/cn/com/vdin/cato/common/constant/table/HrForeignPersonAccountEnum.java\n" +
            "   M /trunk/SourceCode/JAVA/omts/src/main/java/cn/com/vdin/cato/common/constant/table/SysDictType.java\n" +
            "   M /trunk/SourceCode/JAVA/omts/src/main/java/cn/com/vdin/cato/dao/HrForeignPersonAccountDAO.java\n" +
            "   M /trunk/SourceCode/JAVA/omts/src/main/java/cn/com/vdin/cato/domain/HrForeignPersonAccount.java\n" +
            "   M /trunk/SourceCode/JAVA/omts/src/main/java/cn/com/vdin/cato/domain/SysUser.java\n" +
            "   M /trunk/SourceCode/JAVA/omts/src/main/java/cn/com/vdin/cato/dto/admin/HrForeignPersonAccountIndexDTO.java\n" +
            "   M /trunk/SourceCode/JAVA/omts/src/main/java/cn/com/vdin/cato/dto/app/AppHrForeignPersonAccountCreateDTO.java\n" +
            "   M /trunk/SourceCode/JAVA/omts/src/main/java/cn/com/vdin/cato/service/admin/HrForeignPersonAccountService.java\n" +
            "   M /trunk/SourceCode/JAVA/omts/src/main/java/cn/com/vdin/cato/service/app/AppHrForeignPersonAccountService.java\n" +
            "   M /trunk/SourceCode/JAVA/omts/src/main/java/cn/com/vdin/cato/vo/admin/HrForeignPersonHaveCheckedShowVO.java\n" +
            "   M /trunk/SourceCode/JAVA/omts/src/main/java/cn/com/vdin/cato/vo/admin/HrForeignPersonNotCheckedShowVO.java\n" +
            "\n" +
            "修改外来人员管理\n" +
            "------------------------------------------------------------------------\n";

    final String pattern = "^\\-{72}\\n(?<revision>r.+?) \\| (?<author>.+?) \\| (?<time>.+?) \\| (.+?)\\n(Changed paths:\\n(?<changedPaths>(   M .+?)\\n)+?)\\n(?<commitMessage>.+?)\\n\\-{72}\n$";

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
    public void parse() {
        final SvnLog svnLog = SvnLog.of(log);
        System.out.println(svnLog.toString());
        System.out.println("\nBU");
    }

    @Test
    public void getWorkingPath() {
        //System.out.println(System.getProperty("user.dir"));
        System.out.println(Paths.get(".").toAbsolutePath().normalize().toString());
    }

    @Test
    public void staticFactory() {
        Assert.assertNotNull(SvnLog.of(log));
    }

    @Test
    public void info() {
    }
}
