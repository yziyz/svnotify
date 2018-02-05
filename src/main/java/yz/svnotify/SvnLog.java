package yz.svnotify;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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

    private String projectName;

    private String revision;

    private String commitMessage;

    private String author;

    private String time;

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        if (projectName != null) {
            stringBuilder.append("项目:").append(projectName).append("\n");
        }
        stringBuilder.append("版本:").append(revision)
                .append("\n作者:").append(author)
                .append("\n时间:").append(time)
                .append("\n信息:").append(commitMessage);
        return stringBuilder.toString();
    }
}
