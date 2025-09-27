package org.arkanoid;


import javafx.embed.swing.JFXPanel;
import javafx.scene.text.Font;
import org.junit.jupiter.api.*;
import org.arkanoid.factory.LabelFactory;

import static org.junit.jupiter.api.Assertions.*;

public class LabelFactoryTest {
    private final LabelFactory labelFactory = new LabelFactory();

    @Test
    @DisplayName("Global font set by font object")
    public void globalFontCreatedByFontObject() {
        var font = Font.loadFont(
                getClass().getResourceAsStream("/fonts/nes.otf"),
                20
        );
        labelFactory.setGlobalFont(font);

        assertEquals("Nintendo NES Font Regular", font.getName());
        assertEquals(20.0, font.getSize());

        labelFactory.setGlobalFont(null);
    }

    @Test
    @DisplayName("Global font set by font path")
    public void globalFontCreatedByFontPath() {
        labelFactory.setGlobalFont("/fonts/nes.otf", 20);
        var font = labelFactory.getGlobalFont();

        assertEquals("Nintendo NES Font Regular", font.getName());
        assertEquals(20.0, font.getSize());

        labelFactory.setGlobalFont(null);
    }

    @Test
    @DisplayName("Label created")
    public void labelCreated() {
        JFXPanel fxPanel = new JFXPanel();

        labelFactory.setGlobalFont("/fonts/nes.otf", 20);
        var label = labelFactory.createLabel("ABC");

        assertEquals("ABC", label.getText());

        labelFactory.setGlobalFont(null);
    }

    @Test
    @DisplayName("Label created with custom font")
    public void labelCreatedWithCustomFont() {
        JFXPanel fxPanel = new JFXPanel();
        var font = Font.loadFont(
                getClass().getResourceAsStream("/fonts/nes.otf"),
                20
        );
        var label = labelFactory.createLabel("ABC", font);

        assertEquals("ABC", label.getText());
        assertEquals("Nintendo NES Font Regular", label.getFont().getName());

        labelFactory.setGlobalFont(null);
    }
}
