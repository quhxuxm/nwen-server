package online.nwen.server.executor.api.payload;

import java.util.HashMap;
import java.util.Map;

public class SearchCommentRequestPayload {
    public static class Condition {
        public enum Type {
            ARTICLE, ANTHOLOGY, COMMENT
        }

        private Type type;
        private Map<String, String> params;
        private boolean asc;

        public Condition() {
            this.params = new HashMap<>();
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public Map<String, String> getParams() {
            return params;
        }

        public boolean isAsc() {
            return asc;
        }

        public void setAsc(boolean asc) {
            this.asc = asc;
        }
    }

    private Condition condition;
    private int pageIndex;
    private int pageSize;

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
