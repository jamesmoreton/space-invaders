package com.spaceinvaders.views;

import com.spaceinvaders.Commons;

import javax.swing.JPanel;
import java.awt.Graphics;

public class InGameView {

  public static void draw(JPanel jPanel, Graphics g, int score, int level) {
    g.drawLine(0, Commons.GROUND, Commons.BOARD_WIDTH, Commons.GROUND);

    var bannerFontMetrics = jPanel.getFontMetrics(Commons.SMALL_FONT);
    g.setFont(Commons.SMALL_FONT);

    String scoreMsg = Commons.SCORE_PREFIX + score;
    g.drawString(scoreMsg, 5, 15);
    String levelMsg = Commons.LEVEL_PREFIX + level;
    g.drawString(levelMsg, (Commons.BOARD_WIDTH - bannerFontMetrics.stringWidth(levelMsg)) / 2, 15);
  }
}
