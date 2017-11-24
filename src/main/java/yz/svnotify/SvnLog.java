package yz.svnotify;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.regex.Matcher;

/**
 * SVN日志
 *
 * @author 袁臻
 * 2017/11/24 10:40
 */
@Setter
@Getter
@Builder
final class SvnLog {

    private String revision;

    private String commitMessage;

    private String author;

    private String time;

    private String changedPaths;

    /**
     * 静态工厂
     *
     * @param log 日志字符串
     * @return 实例
     */
    static SvnLog of(String log) {
        final Matcher matcher = Constants.PATTERN.matcher(log);
        if (matcher.find()) {
            return SvnLog.builder()
                    .revision(matcher.group("revision").substring(1))
                    .author(matcher.group("author"))
                    .time(matcher.group("time").substring(0, 19))
                    .changedPaths(matcher.group(5).substring(15))
                    .commitMessage(matcher.group("commitMessage"))
                    .build();
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "版本:" + revision +
                "\n作者:" + author +
                "\n信息:" + commitMessage +
                "\n时间:" + time +
                "\n改动:\n" + changedPaths;
    }
}
