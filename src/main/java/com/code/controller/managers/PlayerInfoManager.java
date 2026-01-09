package com.code.controller.managers;

import com.code.config.GameConstants;
import com.code.model.entity.Player;
import com.code.model.game.OAnQuanGame;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class PlayerInfoManager {
    private final Label lblScoreP1;
    private final Label lblScoreP2;
    private final HBox boxPlayer1;
    private final HBox boxPlayer2;
    private final Label lblPlayerName1;
    private final Label lblPlayerName2;

    public PlayerInfoManager(Label lblScoreP1, Label lblScoreP2,
            HBox boxPlayer1, HBox boxPlayer2,
            Label lblPlayerName1, Label lblPlayerName2) {
        this.lblScoreP1 = lblScoreP1;
        this.lblScoreP2 = lblScoreP2;
        this.boxPlayer1 = boxPlayer1;
        this.boxPlayer2 = boxPlayer2;
        this.lblPlayerName1 = lblPlayerName1;
        this.lblPlayerName2 = lblPlayerName2;
    }

    public void updateScores(Player p1, Player p2) {
        lblScoreP1.setText(String.valueOf(p1.getScore()));
        lblScoreP2.setText(String.valueOf(p2.getScore()));
    }

    public void updateActivePlayerHighlight(OAnQuanGame gameModel) {
        boolean p1Turn = gameModel.getCurrentPlayer() == gameModel.getPlayer1();

        highlightPlayer(boxPlayer1, lblPlayerName1, p1Turn);
        highlightPlayer(boxPlayer2, lblPlayerName2, !p1Turn);
    }

    private void highlightPlayer(HBox box, Label nameLabel, boolean isActive) {
        if (isActive) {
            box.setOpacity(GameConstants.ACTIVE_PLAYER_OPACITY);
            box.setStyle(GameConstants.EFFECT_ACTIVE_PLAYER);
            nameLabel.setStyle(GameConstants.EFFECT_ACTIVE_PLAYER);
        } else {
            box.setOpacity(GameConstants.INACTIVE_PLAYER_OPACITY);
            box.setStyle("");
            nameLabel.setStyle("");
        }
    }
}
