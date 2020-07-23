package com.spaceinvaders.views;

import com.spaceinvaders.Commons;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class EndGameView {

  public static void draw(JPanel jPanel, Graphics g, String endMsg, boolean gameOver, boolean allLevelsComplete, boolean levelComplete, int score) {
    setBackground(g);

    displayEndMessage(jPanel, g, endMsg);

    var fontMetrics = jPanel.getFontMetrics(Commons.SMALL_FONT);
    displaySubHeading(g, fontMetrics, gameOver, allLevelsComplete, levelComplete);
    displayScore(g, fontMetrics, score);
    displayRestartMsg(g, fontMetrics);
  }

  private static void setBackground(Graphics g) {
    g.setColor(Color.black);
    g.fillRect(0, 0, Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
  }

  private static void displayEndMessage(JPanel jPanel, Graphics g, String endMsg) {
    g.setColor(Color.green);
    g.setFont(Commons.LARGE_FONT);
    var fontMetrics = jPanel.getFontMetrics(Commons.LARGE_FONT);

    g.drawString(endMsg, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(endMsg)) / 2, Commons.BOARD_HEIGHT / 2 - 15);
  }

  private static void displaySubHeading(Graphics g, FontMetrics fontMetrics, boolean gameOver, boolean allLevelsComplete, boolean levelComplete) {
    g.setFont(Commons.SMALL_FONT);

    String subHeadingMsg = "";
    if (gameOver) {
      subHeadingMsg = Commons.GAME_OVER_MSG;
    } else if (allLevelsComplete) {
      subHeadingMsg = Commons.ALL_LEVELS_COMPLETE_MSG;
    } else if (levelComplete) {
      subHeadingMsg = Commons.MOVE_TO_NEXT_LEVEL_MSG;
    }
    g.drawString(subHeadingMsg, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(subHeadingMsg)) / 2, Commons.BOARD_HEIGHT / 2);
  }

  private static void displayScore(Graphics g, FontMetrics fontMetrics, int score) {
    String scoreMsg = Commons.SCORE_PREFIX + score;
    g.drawString(scoreMsg, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(scoreMsg)) / 2, Commons.BOARD_HEIGHT / 2 + 15);
  }

  private static void displayRestartMsg(Graphics g, FontMetrics fontMetrics) {
    g.drawString(Commons.RESTART_MSG, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(Commons.RESTART_MSG)) / 2, Commons.BOARD_HEIGHT - 30);
  }
}
