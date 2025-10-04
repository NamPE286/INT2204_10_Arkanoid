package org.arkanoid.factory;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * Factory class for creating JavaFX {@link Label} objects with a
 * configurable global font.
 * <p>
 * Allows setting a global font to be applied to all labels created
 * without specifying a font or creating labels with a custom font.
 */
public class LabelFactory {
    private Font globalFont;

    /**
     * Returns the currently set global font.
     *
     * @return the global {@link Font}, or null if none is set
     */
    public Font getGlobalFont() {
        return globalFont;
    }

    /**
     * Sets the global font to be used for labels.
     *
     * @param font the {@link Font} to set as global
     */
    public void setGlobalFont(Font font) {
        this.globalFont = font;
    }

    /**
     * Loads and sets a global font from the specified resource path.
     *
     * @param fontPath the path to the font resource
     * @param size the font size
     */
    public void setGlobalFont(String fontPath, int size) {
        this.globalFont = Font.loadFont(
                getClass().getResourceAsStream(fontPath),
                size
        );
    }

    /**
     * Creates a new {@link Label} with the specified text content
     * using the currently set global font.
     *
     * @param content the text content for the label
     * @return a new {@link Label} with the global font applied
     */
    public Label createLabel(String content) {
        Label label = new Label(content);
        label.setFont(globalFont);

        return label;
    }

    /**
     * Creates a new {@link Label} with the specified text content
     * and a custom font.
     *
     * @param content the text content for the label
     * @param font the {@link Font} to apply to the label
     * @return a new {@link Label} with the specified font applied
     */
    public Label createLabel(String content, Font font) {
        Label label = new Label(content);
        label.setFont(font);

        return label;
    }

    /**
     * Constructs a {@link LabelFactory} without a default global font.
     */
    public LabelFactory() {
    }

    /**
     * Constructs a {@link LabelFactory} and sets the global font
     * from the specified resource path.
     *
     * @param fontPath the path to the font resource
     * @param size the font size
     */
    public LabelFactory(String fontPath, int size) {
        globalFont = Font.loadFont(
                getClass().getResourceAsStream(fontPath),
                size
        );
    }
}
