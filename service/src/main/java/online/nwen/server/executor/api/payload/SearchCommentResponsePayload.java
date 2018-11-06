package online.nwen.server.executor.api.payload;

import org.springframework.data.domain.Page;

import java.util.Date;

public class SearchCommentResponsePayload {
    public static class CommentSearchRecord {
        private String commentId;
        private String content;
        private Date createDate;
        private long subCommentNumber;
        private String authorId;
        private String authorNickname;
        private String authorUsername;
        private String authorIconImageId;

        public String getCommentId() {
            return commentId;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Date getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Date createDate) {
            this.createDate = createDate;
        }

        public long getSubCommentNumber() {
            return subCommentNumber;
        }

        public void setSubCommentNumber(long subCommentNumber) {
            this.subCommentNumber = subCommentNumber;
        }

        public String getAuthorId() {
            return authorId;
        }

        public void setAuthorId(String authorId) {
            this.authorId = authorId;
        }

        public String getAuthorNickname() {
            return authorNickname;
        }

        public void setAuthorNickname(String authorNickname) {
            this.authorNickname = authorNickname;
        }

        public String getAuthorUsername() {
            return authorUsername;
        }

        public void setAuthorUsername(String authorUsername) {
            this.authorUsername = authorUsername;
        }

        public String getAuthorIconImageId() {
            return authorIconImageId;
        }

        public void setAuthorIconImageId(String authorIconImageId) {
            this.authorIconImageId = authorIconImageId;
        }
    }

    private Page<CommentSearchRecord> records;

    public Page<CommentSearchRecord> getRecords() {
        return records;
    }

    public void setRecords(
            Page<CommentSearchRecord> records) {
        this.records = records;
    }
}
