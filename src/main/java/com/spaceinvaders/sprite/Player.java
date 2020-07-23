package com.spaceinvaders.sprite;

import com.spaceinvaders.Commons;

import javax.swing.ImageIcon;
import java.awt.event.KeyEvent;

public class Player extends Sprite {

  private int width;

  public Player() {
    initPlayer();
  }

  private void initPlayer() {
    var ii = new ImageIcon(Commons.PLAYER_IMG);

    width = ii.getImage().getWidth(null);
    setImage(ii.getImage());

    int startX = Commons.BOARD_WIDTH / 2;
    setX(startX);

    int startY = 280;
    setY(startY);
  }

  public void act() {
    x += dx;

    if (x <= 2) {
      x = 2;
    }

    if (x >= Commons.BOARD_WIDTH - 2 * width) {
      x = Commons.BOARD_WIDTH - 2 * width;
    }
  }

  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_LEFT -> dx = -2;
      case KeyEvent.VK_RIGHT -> dx = 2;
    }
  }

  public void keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT -> dx = 0;
    }
  }
}
