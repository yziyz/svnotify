package yz.svnotify;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * 主类
 *
 * @author 袁臻
 * 2017/11/23 16:56
 */
public class Main {

    private static final String COMMAND = "svn %s";

    /**
     * 主方法
     *
     * @param args 命令行参数数组
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        //获取返回值
        final String argument = arrayToString(args);
        log(argument, "/home/user/log");
        final int exitCode = exec(String.format(COMMAND, argument));
        System.exit(exitCode);
    }

    /**
     * 将字符串数组转为以空格分隔的字符串
     *
     * @param array 字符串数组
     * @return 以空格分隔的字符串
     */
    private static String arrayToString(String[] array) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (String s : array) {
            stringBuilder.append(s).append(' ');
        }
        return stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
    }

    /**
     * 执行命令获取其返回值
     *
     * @param argumentString 命令行参数
     * @return 返回值
     * @throws IOException 如果IO异常发生
     */
    private static int exec(final String argumentString) throws IOException, InterruptedException {
        //创建执行提交命令的进程
        final Process process = Runtime.getRuntime().exec(argumentString);
        //打印输出流
        final Thread out = new Thread(() -> new BufferedReader(new InputStreamReader(process.getInputStream()))
                .lines().forEach(line -> {
                    System.out.println(line);
                    try {
                        log(line, "/home/user/out");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }));
        out.start();
        //打印错误流
        final Thread err = new Thread(() -> new BufferedReader(new InputStreamReader(process.getErrorStream()))
                .lines().forEach(System.err::println));
        err.start();
        //等待线程退出
        out.join();
        err.join();
        //等待进程退出
        process.waitFor();
        //返回退出值
        return process.exitValue();
    }

    private static void log(String string, String path) throws FileNotFoundException {
        final PrintWriter printWriter = new PrintWriter(path);
        printWriter.write(string);
        printWriter.close();
    }
}
