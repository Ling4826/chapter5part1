package se233.chapter5part1.controller;

import javafx.application.Platform;
import se233.chapter5part1.model.GameCharacter;
import se233.chapter5part1.view.GameStage;
import se233.chapter5part1.view.Score;
import java.util.List;

public class GameLoop implements Runnable {
    private GameStage gameStage;
    private List<GameCharacter> gameCharacterList;
    private int frameRate;
    private float interval;
    private boolean running;

    public GameLoop(GameStage gameStage, List<GameCharacter> gameCharacterList) {
        this.gameStage = gameStage;
        this.gameCharacterList = gameCharacterList;
        frameRate = 60;
        interval = 1000.0f / frameRate;
        running = true;
    }

    private void update(List<GameCharacter> characterList) {
        for (GameCharacter gameCharacter : characterList) {
            boolean leftPressed = gameStage.getKeys().isPressed(gameCharacter.getLeftKey());
            boolean rightPressed = gameStage.getKeys().isPressed(gameCharacter.getRightKey());
            boolean upPressed = gameStage.getKeys().isPressed(gameCharacter.getUpKey());
            if (leftPressed && rightPressed) { gameCharacter.stop(); }
            else if (leftPressed) { gameCharacter.moveLeft(); }
            else if (rightPressed) { gameCharacter.moveRight(); }
            else { gameCharacter.stop(); }
            if (upPressed) { gameCharacter.jump(); }
            gameCharacter.moveX();
            gameCharacter.moveY();
        }
    }

    // เพิ่มเมธอดสำหรับอัปเดตคะแนน
    private void updateScore(List<Score> scoreList, List<GameCharacter> characterList) {
        Platform.runLater(() -> {
            for (int i = 0; i < scoreList.size(); i++) {
                scoreList.get(i).setPoint(characterList.get(i).getScore());
            }
        });
    }

    @Override
    public void run() {
        while (running) {
            float time = System.currentTimeMillis();
            update(gameCharacterList);
            updateScore(gameStage.getScoreList(), gameStage.getGameCharacterList());

            time = System.currentTimeMillis() - time;
            if (time < interval) {
                try {
                    Thread.sleep((long) (interval - time));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}