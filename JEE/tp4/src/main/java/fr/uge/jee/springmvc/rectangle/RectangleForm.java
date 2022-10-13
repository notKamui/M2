package fr.uge.jee.springmvc.rectangle;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class RectangleForm {

    @NotNull(message = "Width is required")
    @PositiveOrZero(message = "Width must be positive or zero")
    private int width;

    @NotNull(message = "Height is required")
    @PositiveOrZero(message = "Height must be positive or zero")
    private int height;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getArea() {
        return width * height;
    }
}
