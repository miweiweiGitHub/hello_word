package com.huolongguo.study.display;

import lombok.Data;

@Data
public class Control {

    private String type;
    private String name;
    private boolean enabled;
    private boolean selected;

    private Control(Builder builder) {
        this.type = builder.type;
        this.name = builder.name;
        this.enabled = builder.enabled;
        this.selected = builder.selected;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String type;
        private String name;
        private boolean enabled;
        private boolean selected;

        private Builder() {
        }

        public Builder withType(String val) {
            type = val;
            return this;
        }

        public Builder withName(String val) {
            name = val;
            return this;
        }

        public Builder withEnabled(boolean val) {
            enabled = val;
            return this;
        }

        public Builder withSelected(boolean val) {
            selected = val;
            return this;
        }

        public Control build() {
            return new Control(this);
        }
    }
}
