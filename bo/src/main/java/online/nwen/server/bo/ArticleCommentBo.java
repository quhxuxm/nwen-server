package online.nwen.server.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleCommentBo {
    private Long articleId;
    private Long commentId;
    private Long replyToCommentId;
    private String content;
    private Date createTime;
    private Integer replyCommentsNumber;
    private List<ArticleCommentBo> replyComments;
    private UserSummaryBo commenter;

    public ArticleCommentBo() {
        this.replyComments = new ArrayList<>();
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getReplyToCommentId() {
        return replyToCommentId;
    }

    public void setReplyToCommentId(Long replyToCommentId) {
        this.replyToCommentId = replyToCommentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<ArticleCommentBo> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<ArticleCommentBo> replyComments) {
        this.replyComments = replyComments;
    }

    public void setCommenter(UserSummaryBo commenter) {
        this.commenter = commenter;
    }

    public UserSummaryBo getCommenter() {
        return commenter;
    }

    public void setReplyCommentsNumber(Integer replyCommentsNumber) {
        this.replyCommentsNumber = replyCommentsNumber;
    }

    public Integer getReplyCommentsNumber() {
        return replyCommentsNumber;
    }
}
