package se233.chapter5part1.model;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class AnimatedSprite extends ImageView {
    private final int count;
    private final int columns;
    private final int offsetX;
    private final int offsetY;
    private final int width;
    private final int height;
    private final Animation animation;

    public AnimatedSprite(String imgPath, int count, int columns, int rows, int offsetX, int offsetY, int width, int height) {
        Image image = new Image(getClass().getResourceAsStream(imgPath));
        this.setImage(image);
        this.count = count;
        this.columns = columns;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        this.setPreserveRatio(true);
        setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        this.animation = new SpriteAnimation();
        this.animation.setCycleCount(Animation.INDEFINITE);
    }

    public void play() {
        if (animation.getStatus() != Animation.Status.RUNNING) {
            animation.playFromStart();
        }
    }

    public void stop() {
        animation.stop();
        setViewport(new Rectangle2D(offsetX, offsetY, width, height));
    }

    private class SpriteAnimation extends Transition {
        public SpriteAnimation() {
            setCycleDuration(Duration.millis(count * 50));
            setInterpolator(Interpolator.LINEAR);
        }

        @Override
        protected void interpolate(double frac) {
            final int index = Math.min((int) Math.floor(frac * count), count - 1);
            final int x = (index % columns) * width + offsetX;
            final int y = (index / columns) * height + offsetY;
            setViewport(new Rectangle2D(x, y, width, height));
        }
    }
}