package se233.chapter5part1;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se233.chapter5part1.controller.GameLoop;
import se233.chapter5part1.model.GameCharacter;
import se233.chapter5part1.model.Keys;
import se233.chapter5part1.view.GameStage;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class Keysclass {

    @Test
    public void testSingleKeyPress() {
        Keys keys = new Keys();

        GameCharacter character = new GameCharacter(
                2,
                50, 50,
                KeyCode.A, KeyCode.D, KeyCode.W,
                "/se233/chapter5part1/assets/MarioSheet.png",
                4, 4, 1,
                16, 32,
                32, 64,
                7, 16,
                15
        );
        keys.add(KeyCode.A);
        keys.isPressed(KeyCode.A);
        character.setMoveLeft(keys.isPressed(character.getLeftKey()));
        boolean a = character.isMoveLeft();


        assertTrue(a, "Character should move left when LEFT key is pressed");
    }
    @Test
    public void testSingreKeyPress() {
        Keys keys = new Keys();

        GameCharacter character = new GameCharacter(
                2,
                50, 50,
                KeyCode.A, KeyCode.D, KeyCode.W,
                "/se233/chapter5part1/assets/MarioSheet.png",
                4, 4, 1,
                16, 32,
                32, 64,
                7, 16,
                15
        );
        keys.add(KeyCode.D);
        keys.isPressed(KeyCode.D);
        character.setMoveRight(keys.isPressed(character.getRightKey()));
        boolean a = character.isMoveRight();


        assertTrue(a, "Character should move Right when Right key is pressed");
    }
    @Test
    public void testMultiple() {
        Keys keys = new Keys();

        GameCharacter character = new GameCharacter(
                2,
                50, 50,
                KeyCode.A, KeyCode.D, KeyCode.W,
                "/se233/chapter5part1/assets/MarioSheet.png",
                4, 4, 1,
                16, 32,
                32, 64,
                7, 16,
                15
        );
        keys.add(KeyCode.D);
        keys.add(KeyCode.A);
        character.setMoveRight(keys.isPressed(character.getRightKey()));
        character.setMoveLeft(keys.isPressed(character.getLeftKey()));
        boolean isPressingRight = keys.isPressed(character.getRightKey());
        boolean isPressingLeft = keys.isPressed(character.getLeftKey());
        if (isPressingRight && isPressingLeft) {
            character.stop();
            isPressingRight = character.isMoveRight();
            isPressingLeft = character.isMoveLeft();
        } else if (isPressingRight) {
            character.moveRight();
        } else if (isPressingLeft) {
            character.moveLeft();
        }
        assertFalse(isPressingRight, "Character should move Right when Right key is pressed");
        assertFalse(isPressingLeft, "Character should move Right when Right key is pressed");
    }
}