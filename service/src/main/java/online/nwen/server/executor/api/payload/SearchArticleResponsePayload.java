package online.nwen.server.executor.api.payload;

import org.springframework.data.domain.Page;

public class SearchArticleResponsePayload {
    public static class SearchArticleRecord {
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
