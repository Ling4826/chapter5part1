
package se233.chapter5part1.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import se233.chapter5part1.view.GameStage;
import java.util.concurrent.TimeUnit;


public class GameCharacter extends Pane {
    private static final Logger logger = LogManager.getLogger(GameCharacter.class);
    private final int yOffset;
    private int x;

    public void setY(int y) {
        this.y = y;
    }

    private int y;
    private int startX;
    private int startY;

    public void setScore(int score) {
        this.score = score;
    }

    private int score = 0;
    private AnimatedSprite imageView;
    private KeyCode leftKey, rightKey, upKey;

    public boolean isMoveLeft() {
        return isMoveLeft;
    }

    public void setMoveLeft(boolean moveLeft) {
        isMoveLeft = moveLeft;
    }

    private boolean isMoveLeft = false;

    public boolean isMoveRight() {
        return isMoveRight;
    }

    public void setMoveRight(boolean moveRight) {
        isMoveRight = moveRight;
    }

    private boolean isMoveRight = false;
    private boolean isFalling = true;

    public boolean isCanJump() {
        return canJump;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    private boolean canJump = false;
    private boolean isJumping = false;
    private int xVelocity = 0, yVelocity = 0;
    private final int xAcceleration = 1, yAcceleration = 1;
    private final int xMaxVelocity, yMaxVelocity;
    private final int displayWidth, displayHeight;
    public GameCharacter(int id, int x, int y, KeyCode leftKey, KeyCode rightKey, KeyCode upKey, String spritePath, int spriteCount, int spriteColumns, int spriteRows, int frameWidth, int frameHeight, int displayWidth, int displayHeight, int xMaxVelocity, int yMaxVelocity, int yOffset) {
        this.x = x; this.y = y;
        this.startX = x; this.startY = y;
        this.leftKey = leftKey; this.rightKey = rightKey; this.upKey = upKey;
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
        this.xMaxVelocity = xMaxVelocity;
        this.yMaxVelocity = yMaxVelocity;
        this.yOffset = yOffset;

        this.imageView = new AnimatedSprite(spritePath, spriteCount, spriteColumns, spriteRows, 0, 0, frameWidth, frameHeight);
        this.imageView.setFitWidth(displayWidth);
        this.imageView.setFitHeight(displayHeight);
        this.setTranslateX(x); this.setTranslateY(y);
        this.getChildren().add(this.imageView);
    }

    public boolean collided(GameCharacter c) {
        if (this.isMoveLeft && this.x > c.getX() && this.getBoundsInParent().intersects(c.getBoundsInParent())) {
            this.x = c.getX() + c.getDisplayWidth();
            this.stop();
        } else if (this.isMoveRight && this.x < c.getX() && this.getBoundsInParent().intersects(c.getBoundsInParent())) {
            this.x = c.getX() - this.displayWidth;
            this.stop();
        }

        if (this.isFalling && this.y < c.getY() && this.getBoundsInParent().intersects(c.getBoundsInParent())) {
            score++;
            c.collapsed();
            c.respawn();
            return true;
        }
        return false;
    }

    public void collapsed() {
        this.imageView.setFitHeight(5);
        this.y = GameStage.GROUND - 5;
        this.repaint();
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void respawn() {
        this.x = this.startX;
        this.y = this.startY;
        this.isFalling = true;
        this.canJump = false;
        this.isJumping = false;
        this.imageView.setFitHeight(this.displayHeight);
    }


    public int getX() { return x; }
    public int getY() { return y; }
    public int getScore() { return score; }
    public int getDisplayWidth() { return displayWidth; }

    public void checkReachGameWall() {
        if (x <= 0) {
            x = 0;
            logger.debug("Character hit left wall.");
        } else if (x + displayWidth >= GameStage.WIDTH) {
            x = GameStage.WIDTH - displayWidth;
            logger.debug("Character hit right wall.");
        }
    }
    public void checkReachFloor() {
        if (isFalling && y >= GameStage.GROUND - displayHeight) {
            isFalling = false;
            canJump = true;
            yVelocity = 0;

            y = GameStage.GROUND - displayHeight + yOffset;
        }
    }
    public void moveX() {
        if (isMoveLeft) {
            xVelocity = Math.min(xVelocity + xAcceleration, xMaxVelocity);
            x -= xVelocity;

            logger.info("Character is moving LEFT. Current X: {}", x);
        }
        if (isMoveRight) {
            xVelocity = Math.min(xVelocity + xAcceleration, xMaxVelocity);
            x += xVelocity;
            logger.info("Character is moving RIGHT. Current X: {}", x);
        }
    }

    public void moveY() { if (isFalling) { yVelocity = Math.min(yVelocity + yAcceleration, yMaxVelocity); y += yVelocity; } else if (isJumping) { yVelocity = Math.max(yVelocity - yAcceleration, 0); y -= yVelocity; } }
    public void jump() { if (canJump) { yVelocity = yMaxVelocity; canJump = false; isJumping = true; isFalling = false; } }
    public void stop() { isMoveLeft = false; isMoveRight = false; xVelocity = 0; }

    public void moveLeft() {
        setScaleX(1);
        isMoveLeft = true;
        isMoveRight = false;
    }
    public void moveRight() {
        setScaleX(-1);
        isMoveLeft = false;
        isMoveRight = true;
    }
    public void repaint() { setTranslateX(x); setTranslateY(y); }
    public void checkReachHighest() { if (isJumping && yVelocity <= 0) { isJumping = false; isFalling = true; yVelocity = 0; } }
    public KeyCode getLeftKey() { return leftKey; }
    public KeyCode getRightKey() { return rightKey; }
    public KeyCode getUpKey() { return upKey; }
    public AnimatedSprite getImageView() { return imageView; }
    public boolean isMovingLeft() { return isMoveLeft; }
    public boolean isMovingRight() { return isMoveRight; }
}