package online.nwen.server.executor.api.payload;

import org.springframework.data.domain.Page;

public class SearchArticleResponsePayload {
    public static class SearchArticleRecord {
        private String id;
        private boolean publish;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isPublish() {
            return publish;
        }

        public void setPublish(boolean publish) {
            this.publish = publish;
        }
    }

    private Page<SearchArticleRecord> records;

    public SearchArticleResponsePayload() {
    }

    public Page<SearchArticleRecord> getRecords() {
        return records;
    }

    public void setRecords(
            Page<SearchArticleRecord> records) {
        this.records = records;
    }
}
