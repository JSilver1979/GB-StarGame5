package ru.gb.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import ru.gb.stargame.game.GameController;
import ru.gb.stargame.game.WorldRenderer;
import ru.gb.stargame.screen.utils.Assets;


public class GameScreen extends AbstractScreen{

    private GameController gc;
    private WorldRenderer worldRenderer;
    private BitmapFont font24;
    private Stage stage;

    public GameScreen(SpriteBatch batch) {
        super(batch);
    }

    @Override
    public void show() {
        Assets.getInstance().loadAssets(ScreenManager.ScreenType.GAME);
        this.gc = new GameController();
        this.worldRenderer = new WorldRenderer(gc, batch);
        this.font24 = Assets.getInstance().getAssetManager().get("fonts/font24.ttf");
        this.stage = new Stage(ScreenManager.getInstance().getViewport(), batch);

        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin();
        skin.addRegions(Assets.getInstance().getAtlas());

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up= skin.getDrawable("smButton");
        textButtonStyle.font = font24;
        skin.add("simpleSkin", textButtonStyle);

        Button btnNewGame = new TextButton("Pause", textButtonStyle);
        btnNewGame.setPosition(ScreenManager.SCREEN_WIDTH - 120, ScreenManager.SCREEN_HEIGHT - 32);

        btnNewGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gc.setPaused(!gc.isPaused());
            }
        });

        stage.addActor(btnNewGame);
        skin.dispose();
    }

    @Override
    public void render(float delta) {
        gc.update(delta);
        worldRenderer.render();
        stage.draw();
    }
}
