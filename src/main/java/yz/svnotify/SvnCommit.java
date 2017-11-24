package yz.svnotify;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 提交类
 *
 * @author 袁臻
 * 2017/11/23 22:49
 */
@Setter
@Getter
@Builder
@Data
public class SvnCommit {

  /**
   * 模式
   */
  public static final Pattern PATTERN = Pattern.compile("^--config-dir (.+?) " +
          "commit " +
          "--depth empty " +
          "--non-interactive " +
          "-F(.+?commit-message.txt) " +
          "--config-option config:miscellany:log-encoding=UTF-8 " +
          "(.+?)$");

  private Integer revision;

  private String lastChangedAuthor;

  private String lastChangeDate;

  private String message;

  private String fileList;

  /**
   * 静态工厂
   *
   * @param argument 参数字符串
   * @return 若参数字符串匹配，则返回提交实例，否则返回空
   */
  static SvnCommit of(final String argument) {
    final Matcher matcher = PATTERN.matcher(argument);
    final boolean isMatch = matcher.find();
    if (isMatch) {
      final SvnCommit svnCommit = SvnCommit.builder()
              .message(Utils.readFile(matcher.group(2)))
              .fileList(Utils.getFileListString(matcher.group(3)))
              .build();
      return setInfo(svnCommit);
    } else {
      return null;
    }
  }

  private static SvnCommit setInfo(final SvnCommit svnCommit) {
    try {
      final Process process = Runtime.getRuntime().exec("svn info");
      new BufferedReader(new InputStreamReader(process.getInputStream()))
              .lines()
              .forEach(line -> {
                //设置最后修订版本
                if (line.startsWith("Last Changed Rev")) {
                  svnCommit.setRevision(Integer.parseInt(line.substring(18)));
                }
                //设置最后修改作者
                if (line.startsWith("Last Changed Author")) {
                  svnCommit.setLastChangedAuthor(line.substring(21));
                }
                //设置最后修改时间
                if (line.startsWith("Last Changed Date")) {
                  svnCommit.setLastChangeDate(line.substring(19, 38));
                }
              });
      process.waitFor();
      return svnCommit;
    } catch (Exception e) {
      e.printStackTrace();
      return svnCommit;
    }
  }
}
