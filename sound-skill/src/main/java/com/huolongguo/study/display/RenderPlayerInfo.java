package com.huolongguo.study.display;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RenderPlayerInfo extends com.amazon.ask.model.interfaces.display.Template {

    private String audioItemId;
    private Content content;
    private List<Control> controls;

    private RenderPlayerInfo(Builder builder) {
        this.audioItemId = builder.audioItemId;
        this.content = builder.content;
        this.controls = builder.controls;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String audioItemId;
        private Content content;
        private List<Control> controls;

        private Builder() {
        }

        public Builder withAudioItemId(String val) {
            audioItemId = val;
            return this;
        }

        public Builder withContent(Content val) {
            content = val;
            return this;
        }

        public Builder withControls(List<Control> val) {
            controls = val;
            return this;
        }

        public Builder addControls(Control val) {
            if (this.controls == null) {
                this.controls = new ArrayList<>();
            }
            controls.add(controls.size(), val);
            return this;
        }

        public Builder setControl(String name, Boolean enabled, Boolean selected) {
            if (this.controls == null) {
                return this;
            }
            for (int j = 0; j <= controls.size(); j++) {
                if (controls.get(j).getName().equals(name)) {
                    controls.get(j).setEnabled(enabled);
                    controls.get(j).setSelected(selected);
                    return this;
                }
            }
            return this;
        }

        public RenderPlayerInfo build() {
            return new RenderPlayerInfo(this);
        }
    }
}
