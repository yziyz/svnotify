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

    @Override
    public String toString() {
        return "版本:" + revision +
                "\n作者:" + author +
                "\n信息:" + commitMessage +
                "\n时间:" + time +
                "\n改动:\n" + changedPaths;
    }
}
