package se233.chapter5part1;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se233.chapter5part1.model.GameCharacter;
import se233.chapter5part1.view.GameStage;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class GameCharacterTest {
    private GameCharacter gameCharacter;
    Field xVelocityField, yVelocityField, yAccelerationField;
    @BeforeAll
    public static void initJfxRuntime() {
        javafx.application.Platform.startup(() -> {});
    }

    @BeforeEach
    public void setup() throws NoSuchFieldException {
        // This call now uses the correct 17-argument constructor and an absolute path for the image.
        gameCharacter = new GameCharacter(
                1,
                30, 30,
                KeyCode.A, KeyCode.D, KeyCode.W,
                "/se233/chapter5part1/assets/MarioSheet.png",
                4, 4, 1,
                16, 32,
                32, 64,
                7, 16,
                15
        );
        xVelocityField = gameCharacter.getClass().getDeclaredField("xVelocity");
        yVelocityField = gameCharacter.getClass().getDeclaredField("yVelocity");
        yAccelerationField = gameCharacter.getClass().getDeclaredField("yAcceleration");
        xVelocityField.setAccessible(true);
        yVelocityField.setAccessible(true);
        yAccelerationField.setAccessible(true);
    }
    @Test
    public void moveX_givenMoveRightOnce_thenXCoordinateIncreasedByXVelocity() throws
            IllegalAccessException {
        gameCharacter.respawn();
        gameCharacter.moveRight();
        gameCharacter.moveX();
        assertEquals(30 + xVelocityField.getInt(gameCharacter), gameCharacter.getX(), "Move right x");
    }
    @Test
    public void moveY_givenTwoConsecutiveCalls_thenYVelocityIncreases() throws
            IllegalAccessException {
        gameCharacter.respawn();
        gameCharacter.moveY();
        int yVelocity1 = yVelocityField.getInt(gameCharacter);
        gameCharacter.moveY();
        int yVelocity2 = yVelocityField.getInt(gameCharacter);
        assertTrue(yVelocity2 > yVelocity1, "Velocity is increasing");
    }
    @Test
    public void moveY_givenTwoConsecutiveCalls_thenYAccelerationUnchanged() throws
            IllegalAccessException {
        gameCharacter.respawn();
        gameCharacter.moveY();
        int yAcceleration1 = yAccelerationField.getInt(gameCharacter);
        gameCharacter.moveY();
        int yAcceleration2 = yAccelerationField.getInt(gameCharacter);
        assertTrue(yAcceleration1 == yAcceleration2, "Acceleration is not change");
    }

    @Test
    public void respawn_givenNewGameCharacter_thenCoordinatesAre30_30() {
        gameCharacter.respawn();
        assertEquals(30, gameCharacter.getX(), "Initial x");
        assertEquals(30, gameCharacter.getY(), "Initial y");
    }

    @Test
    public void respawn_givenNewGameCharacter_thenScoreIs0() {
        gameCharacter.respawn();
        assertEquals(0, gameCharacter.getScore(), "Initial score");
    }

    @Test
    public void ReachGameWalllef()  {
        gameCharacter.respawn();
        gameCharacter.moveLeft();
        gameCharacter.moveX();
        gameCharacter.moveX();
        gameCharacter.moveX();
        gameCharacter.moveX();
        gameCharacter.moveX();
        gameCharacter.moveX();
        gameCharacter.moveX();
        gameCharacter.moveX();
        gameCharacter.checkReachGameWall();
        assertEquals(0, gameCharacter.getX(), "gogogogo");
    }

    @Test
    public void ReachGameWallright()  {
        gameCharacter.respawn();
        gameCharacter.moveRight();
        for (int i = 0 ; i < 150 ; i++)
        {
            gameCharacter.moveX();
        }

        gameCharacter.checkReachGameWall();
        assertEquals(GameStage.WIDTH - gameCharacter.getDisplayWidth(),
                gameCharacter.getX(),
                "gogogogo");

    }

    @Test
    public void jump()  {
        gameCharacter.respawn();

        gameCharacter.checkReachFloor();
        gameCharacter.jump();
        gameCharacter.moveY();
        int initialY = gameCharacter.getY();
        gameCharacter.jump();

        assertFalse(gameCharacter.isCanJump(), "isCanJump?");
        assertTrue(initialY == gameCharacter.getY(),"gogogogo");
    }

    @Test
    public void collidedA(){
        GameCharacter A = gameCharacter;
        GameCharacter B = new GameCharacter(
                2,
                30, 30,
                KeyCode.A, KeyCode.D, KeyCode.W,
                "/se233/chapter5part1/assets/MarioSheet.png",
                4, 4, 1,
                16, 32,
                32, 64,
                7, 16,
                15
        );
        A.setY(B.getY() - 10);
        A.moveY();
        boolean collisionOccurred = A.collided(B);
        assertTrue(collisionOccurred, "isCanJump?");
        assertTrue(1 == A.getScore(),"gogogogo");
    }

    @Test
    public void collidedB(){
        GameCharacter A = gameCharacter;
        GameCharacter B = new GameCharacter(
                2,
                30, 20,
                KeyCode.A, KeyCode.D, KeyCode.W,
                "/se233/chapter5part1/assets/MarioSheet.png",
                4, 4, 1,
                16, 32,
                32, 64,
                7, 16,
                15
        );
        B.setY(A.getY() -10);
        B.moveY();
        boolean collisionOccurred = B.collided(A);


        assertTrue(collisionOccurred, "isCanJump?");
        assertTrue(1 == B.getScore(),"gogogogo");
    }

    @Test
    public void collidedAX(){
        GameCharacter A = gameCharacter;
        GameCharacter B = new GameCharacter(
                2,
                20, 30,
                KeyCode.A, KeyCode.D, KeyCode.W,
                "/se233/chapter5part1/assets/MarioSheet.png",
                4, 4, 1,
                16, 32,
                32, 64,
                7, 16,
                15
        );
        A.setMoveLeft(true);
        A.moveX();
        A.collided(B);
        boolean collisionOccurred = A.isMoveLeft();

        assertFalse(collisionOccurred,"gogogogo");
    }

    @Test
    public void collidedBX(){
        GameCharacter A = gameCharacter;
        GameCharacter B = new GameCharacter(
                2,
                20, 30,
                KeyCode.A, KeyCode.D, KeyCode.W,
                "/se233/chapter5part1/assets/MarioSheet.png",
                4, 4, 1,
                16, 32,
                32, 64,
                7, 16,
                15
        );
        B.setMoveRight(true);
        B.moveX();
        B.collided(A);
        boolean collisionOccurred = B.isMoveRight();

        assertFalse(collisionOccurred,"gogogogo");
    }

}