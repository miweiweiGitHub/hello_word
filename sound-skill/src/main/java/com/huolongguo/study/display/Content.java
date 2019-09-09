package com.huolongguo.study.display;

import com.amazon.ask.model.interfaces.display.Image;
import lombok.Data;

@Data
public class Content {

    private String title;
    private String titleSubtext1;
    private String titleSubtext2;
    private String header;
    private String headerSubtext1;
    private String mediaLengthInMilliseconds;
    private Image art;
    private Provider provider;

    private Content(Builder builder) {
        this.title = builder.title;
        this.titleSubtext1 = builder.titleSubtext1;
        this.titleSubtext2 = builder.titleSubtext2;
        this.header = builder.header;
        this.headerSubtext1 = builder.headerSubtext1;
        this.mediaLengthInMilliseconds = builder.mediaLengthInMilliseconds;
        this.art = builder.art;
        this.provider = builder.provider;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String title;
        private String titleSubtext1;
        private String titleSubtext2;
        private String header;
        private String headerSubtext1;
        private String mediaLengthInMilliseconds;
        private Image art;
        private Provider provider;

        private Builder() {
        }

        public Builder withTitle(String val) {
            title = val;
            return this;
        }

        public Builder withTitleSubtext1(String val) {
            titleSubtext1 = val;
            return this;
        }

        public Builder withTitleSubtext2(String val) {
            titleSubtext2 = val;
            return this;
        }

        public Builder withHeader(String val) {
            header = val;
            return this;
        }

        public Builder withHeaderSubtext1(String val) {
            headerSubtext1 = val;
            return this;
        }

        public Builder withMediaLengthInMilliseconds(String val) {
            mediaLengthInMilliseconds = val;
            return this;
        }

        public Builder withArt(Image val) {
            art = val;
            return this;
        }

        public Builder withProvider(Provider val) {
            provider = val;
            return this;
        }

        public Content build() {
            return new Content(this);
        }
    }
}
