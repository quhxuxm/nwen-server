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
