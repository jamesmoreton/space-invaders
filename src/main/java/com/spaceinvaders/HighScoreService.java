package com.spaceinvaders;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HighScoreService {

  private static final Map<String, Integer> highScores = new LinkedHashMap<>();

  static void accept(JPanel jPanel, Graphics g, boolean gameOver, boolean allLevelsComplete, int score) {
    if (!gameOver && !allLevelsComplete) {
      return;
    }

    boolean isHighScore = isHighScore(score);
    displayHeadingAndPutHighScore(jPanel, g, isHighScore, score);
    removeLowScoreIfRequired();
    displayHighScores(g, true);
  }

  static void display(JPanel jPanel, Graphics g) {
    var fontMetrics = jPanel.getFontMetrics(Commons.SMALL_FONT);
    g.setFont(Commons.SMALL_FONT);
    g.drawString(Commons.ESCAPE_MSG, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(Commons.ESCAPE_MSG)) / 2, Commons.BOARD_HEIGHT - 30);

    if (highScores.isEmpty()) {
      g.drawString(Commons.NO_HIGH_SCORES, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(Commons.NO_HIGH_SCORES)) / 2, 30);
      return;
    }

    g.drawString(Commons.HIGH_SCORES, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(Commons.HIGH_SCORES)) / 2, 30);
    displayHighScores(g, false);
  }

  private static void displayHeadingAndPutHighScore(JPanel jPanel, Graphics g, boolean isHighScore, int score) {
    var fontMetrics = jPanel.getFontMetrics(Commons.SMALL_FONT);
    g.setFont(Commons.SMALL_FONT);

    if (isHighScore) {
      String userName = ZonedDateTime.now().toLocalTime().truncatedTo(ChronoUnit.SECONDS).toString();
      highScores.put(userName, score);
      g.drawString(Commons.NEW_HIGH_SCORE, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(Commons.NEW_HIGH_SCORE)) / 2, 15);
    }

    if (!highScores.isEmpty()) {
      g.drawString(Commons.HIGH_SCORES, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(Commons.HIGH_SCORES)) / 2, 30);
    }
  }

  private static boolean isHighScore(int score) {
    if (score > 0 && highScores.size() < 10) {
      return true;
    } else {
      return highScores.values()
          .stream()
          .anyMatch(hs -> hs < score);
    }
  }

  private static void removeLowScoreIfRequired() {
    if (highScores.size() > 10) {
      String keyToRemove = highScores.entrySet()
          .stream()
          .min(Map.Entry.comparingByValue())
          .orElseThrow(() -> new RuntimeException("Unable to find min high score to remove"))
          .getKey();
      highScores.remove(keyToRemove);
    }
  }

  private static void displayHighScores(Graphics g, boolean shouldCondense) {
    final Map<String, Integer> sortedHighScores = highScores.entrySet()
        .stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

    int positionY = 45;
    int scorePosition = 1;

    for (Map.Entry<String, Integer> entry : sortedHighScores.entrySet()) {
      String k = entry.getKey();
      Integer v = entry.getValue();

      if (sortedHighScores.size() > 5 && shouldCondense) {
        int positionX;
        int keyX;
        int valueX;
        if (scorePosition <= 5) {
          positionX = Commons.BOARD_WIDTH / 2 - 100;
          keyX = Commons.BOARD_WIDTH / 2 - 80;
          valueX = Commons.BOARD_WIDTH / 2 - 10;
          positionY += 15;
        } else {
          if (scorePosition == 6) {
            positionY -= 15 * 4;
          } else {
            positionY += 15;
          }
          positionX = Commons.BOARD_WIDTH / 2 + 10;
          keyX = Commons.BOARD_WIDTH / 2 + 30;
          valueX = Commons.BOARD_WIDTH / 2 + 100;
        }

        g.drawString(scorePosition + ".", positionX, positionY);
        g.drawString(k, keyX, positionY);
        g.drawString(v.toString(), valueX, positionY);
      } else {
        g.drawString(scorePosition + ".", Commons.BOARD_WIDTH / 2 - 55, positionY);
        g.drawString(k, Commons.BOARD_WIDTH / 2 - 35, positionY);
        g.drawString(v.toString(), Commons.BOARD_WIDTH / 2 + 35, positionY);
        positionY += 15;
      }
      scorePosition++;
    }
  }
}
