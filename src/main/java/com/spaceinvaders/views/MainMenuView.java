package com.spaceinvaders.views;

import com.spaceinvaders.Commons;

import javax.swing.JPanel;
import java.awt.Graphics;

public class MainMenuView {

  public static void draw(JPanel jPanel, Graphics g) {
    displayTitle(jPanel, g);
    displaySubHeadings(jPanel, g);
  }

  private static void displayTitle(JPanel jPanel, Graphics g) {
    var fontMetrics = jPanel.getFontMetrics(Commons.TITLE_FONT);
    g.setFont(Commons.TITLE_FONT);
    g.drawString(Commons.TITLE_1, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(Commons.TITLE_1)) / 2, Commons.BOARD_HEIGHT / 2 - 60);
    g.drawString(Commons.TITLE_2, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(Commons.TITLE_2)) / 2, Commons.BOARD_HEIGHT / 2 - 15);
  }

  private static void displaySubHeadings(JPanel jPanel, Graphics g) {
    var fontMetrics = jPanel.getFontMetrics(Commons.SMALL_FONT);
    g.setFont(Commons.SMALL_FONT);
    g.drawString(Commons.TITLE_PLAY_MSG, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(Commons.TITLE_PLAY_MSG)) / 2, Commons.BOARD_HEIGHT / 2 + 10);
    g.drawString(Commons.TITLE_HIGH_SCORES_MSG, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(Commons.TITLE_HIGH_SCORES_MSG)) / 2, Commons.BOARD_HEIGHT - 45);
    g.drawString(Commons.TITLE_INSTRUCTIONS_MSG, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(Commons.TITLE_INSTRUCTIONS_MSG)) / 2, Commons.BOARD_HEIGHT - 30);
  }
}
