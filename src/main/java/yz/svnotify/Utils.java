package yz.svnotify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * 工具类
 *
 * @author 袁臻
 * 2017/11/23 23:31
 */
public class Utils {

  /**
   * 读取文件
   *
   * @param path 文件路径
   * @return 文件内容
   */
  static String readFile(String path) {
    try {
      return new String(Files.readAllBytes(Paths.get(path)));
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  static String getFileListString(String filePathListString) {
    //获取工作路径
    final String workingDirectory = Paths.get(".").toAbsolutePath().normalize().toString();
    //字符串构建器实例
    final StringBuilder stringBuilder = new StringBuilder();
    //将文件路径字符串删除工作路径后加入字符串构建器
    Arrays.stream(filePathListString.split(" "))
            .map(filePath -> filePath.substring(workingDirectory.length() + 1))
            .forEach(filePath -> stringBuilder.append(", ").append(filePath));
    //删除字符串构建器实例中第一个", "
    return stringBuilder.delete(0, 2).toString();
  }
}
