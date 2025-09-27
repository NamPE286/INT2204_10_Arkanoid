package org.arkanoid.factory;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class LabelFactory {
    private Font font;

    public Font getGlobalFont() {
        return font;
    }

    public void setGlobalFont(Font font) {
        this.font = font;
    }

    public Label createLabel(String content) {
        Label label = new Label(content);
        label.setFont(font);

        return label;
    }

    public Label createLabel(String content, Font font) {
        Label label = new Label(content);
        label.setFont(font);

        return label;
    }

    public LabelFactory() {
    }

    public LabelFactory(String fontPath, int size) {
        font = javafx.scene.text.Font.loadFont(
                getClass().getResourceAsStream(fontPath),
                size
        );
    }
}
