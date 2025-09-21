package se233.chapter5part1.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import se233.chapter5part1.model.GameCharacter;
import se233.chapter5part1.model.Keys;
import java.util.ArrayList;
import java.util.List;

public class GameStage extends Pane {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;
    public final static int GROUND = 300;
    private List<GameCharacter> gameCharacterList;
    private Keys keys;
    private List<Score> scoreList;

    public GameStage() {
        keys = new Keys();
        scoreList = new ArrayList<>();
        gameCharacterList = new ArrayList<>();
        Image gameStageImg = new Image(getClass().getResourceAsStream("/se233/chapter5part1/assets/Background.png"));
        ImageView backgroundImg = new ImageView(gameStageImg);
        backgroundImg.setFitHeight(HEIGHT);
        backgroundImg.setFitWidth(WIDTH);

        int xMaxVelocity = 7;
        int yMaxVelocity = 17;

        gameCharacterList.add(new GameCharacter(0, 30, 30, KeyCode.A, KeyCode.D, KeyCode.W, "/se233/chapter5part1/assets/Character1.png", 4, 3, 2, 111, 97, 128, 128, xMaxVelocity, yMaxVelocity, 30));
        gameCharacterList.add(new GameCharacter(1, GameStage.WIDTH - 60, 30, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP, "/se233/chapter5part1/assets/Character2.png", 4, 4, 1, 129, 66, 160, 160, xMaxVelocity, yMaxVelocity, 80));

        scoreList.add(new Score(30, GROUND + 30));
        scoreList.add(new Score(WIDTH - 100, GROUND + 30));

        getChildren().add(backgroundImg);
        getChildren().addAll(gameCharacterList);
        getChildren().addAll(scoreList);
    }

    public List<GameCharacter> getGameCharacterList() { return gameCharacterList; }
    public Keys getKeys() { return keys; }
    public List<Score> getScoreList() { return scoreList; }
}