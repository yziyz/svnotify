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
                if (line.startsWith("Revision")) {
                  svnCommit.setRevision(Integer.parseInt(line.substring(10)));
                }
                if (line.startsWith("Last Changed Author")) {
                  svnCommit.setLastChangedAuthor(line.substring(21));
                }
                if (line.startsWith("Last Changed Date")) {
                  svnCommit.setLastChangeDate(line.substring(20));
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
