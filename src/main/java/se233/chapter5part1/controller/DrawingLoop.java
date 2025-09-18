package se233.chapter5part1.controller;
import javafx.application.Platform;
import se233.chapter5part1.model.GameCharacter;
import se233.chapter5part1.view.GameStage;
import java.util.List;

public class DrawingLoop implements Runnable {
    private GameStage gameStage;
    private List<GameCharacter> gameCharacterList;
    private int frameRate;
    private float interval;
    private boolean running;

    public DrawingLoop(GameStage gameStage, List<GameCharacter> gameCharacterList) {
        this.gameStage = gameStage;
        this.gameCharacterList = gameCharacterList;
        frameRate = 60;
        interval = 1000.0f / frameRate;
        running = true;
    }

    private void checkDrawCollisions(List<GameCharacter> characterList) {
        for (GameCharacter character : characterList) {
            character.checkReachGameWall();
            character.checkReachHighest();
            character.checkReachFloor();
        }


        if (characterList.size() == 2) {
            GameCharacter cA = characterList.get(0);
            GameCharacter cB = characterList.get(1);
            if (cA.getBoundsInParent().intersects(cB.getBoundsInParent())) {
                if (!cA.collided(cB)) {
                    cB.collided(cA);
                }
            }
        }
    }

    private void paint(List<GameCharacter> characterList) {
        for (GameCharacter gameCharacter : characterList) {
            gameCharacter.repaint();
            // Control animation playback
            if (gameCharacter.isMovingLeft() || gameCharacter.isMovingRight()) {
                gameCharacter.getImageView().play();
            } else {
                gameCharacter.getImageView().stop();
            }
        }
    }

    @Override
    public void run() {
        while (running) {
            float time = System.currentTimeMillis();
            Platform.runLater(() -> {
                checkDrawCollisions(gameCharacterList);
                paint(gameCharacterList);
            });
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