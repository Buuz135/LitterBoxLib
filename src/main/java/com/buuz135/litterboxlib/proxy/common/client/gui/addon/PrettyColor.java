package com.buuz135.litterboxlib.proxy.common.client.gui.addon;

import lombok.Getter;

import java.awt.*;

public enum PrettyColor {
    BLUE(new Color(10, 118, 208)),
    RED(new Color(208, 35, 10)),
    YELLOW(new Color(208, 166, 10));

    @Getter
    private Color color;

    PrettyColor(Color color) {
        this.color = color;
    }
}
