package com.huolongguo.study.display;

import com.amazon.ask.model.interfaces.display.Image;
import lombok.Data;

@Data
public class Provider {

    private String name;
    private Image logo;

    private Provider(Builder builder) {
        this.name = builder.name;
        this.logo = builder.logo;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private Image logo;

        private Builder() {
        }

        public Builder withName(String val) {
            name = val;
            return this;
        }

        public Builder withLogo(Image val) {
            logo = val;
            return this;
        }

        public Provider build() {
            return new Provider(this);
        }
    }
}
