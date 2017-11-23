package yz.svnotify;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;

/**
 * @author 袁臻
 * 2017/11/23 22:45
 */
public class Tester {

  private final String argument = "--config-dir /home/user/.subversion " +
          "commit " +
          "--depth empty " +
          "--non-interactive " +
          "-F /home/user/commit-message.txt " +
          "--config-option config:miscellany:log-encoding=UTF-8 " +
          "/home/user/project/svnotify/Hello.java " +
          "/home/user/project/svnotify/World.java";


  @Test
  public void parse() {
    Assert.assertTrue(SvnCommit.PATTERN.matcher(argument).find());
  }

  @Test
  public void getWorkingPath() {
    //System.out.println(System.getProperty("user.dir"));
    System.out.println(Paths.get(".").toAbsolutePath().normalize().toString());
  }

  @Test
  public void staticFactory() {
    final SvnCommit svnCommit = SvnCommit.of(argument);
    System.out.println(svnCommit.getMessage());
    System.out.println(svnCommit.getFileList());
  }
}
