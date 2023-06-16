
package com.person.chenpt.swagger;

/**
 * highlight data style
 * 
 * @author Sayi
 *
 */
public class HighlightStyle {

    private boolean showLine;
    private String theme;
    private String fontFamily;
    private double fontSize;

    public HighlightStyle() {
    }

    private HighlightStyle(Builder builder) {
        this.showLine = builder.showLine;
        this.theme = builder.theme;
        this.fontFamily = builder.fontFamily;
        this.fontSize = builder.fontSize;
    }

    public boolean isShowLine() {
        return showLine;
    }

    public void setShowLine(boolean showLine) {
        this.showLine = showLine;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public double getFontSize() {
        return fontSize;
    }

    public void setFontSize(double fontSize) {
        this.fontSize = fontSize;
    }

    /**
     * Creates builder to build {@link HighlightStyle}.
     * 
     * @return created builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder to build {@link HighlightStyle}.
     */
    public static final class Builder {
        private boolean showLine;
        private String theme;
        private String fontFamily;
        private double fontSize;

        private Builder() {
        }

        public Builder withShowLine(boolean showLine) {
            this.showLine = showLine;
            return this;
        }

        public Builder withTheme(String theme) {
            this.theme = theme;
            return this;
        }

        public Builder withFontFamily(String fontFamily) {
            this.fontFamily = fontFamily;
            return this;
        }

        public Builder withFontSize(double fontSize) {
            this.fontSize = fontSize;
            return this;
        }

        public HighlightStyle build() {
            return new HighlightStyle(this);
        }
    }

}
