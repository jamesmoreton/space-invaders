package com.spaceinvaders.sprite;

import com.spaceinvaders.Commons;

import javax.swing.ImageIcon;

public class Alien extends Sprite {

  private Bomb bomb;

  public Alien(int x, int y) {
    initAlien(x, y);
  }

  private void initAlien(int x, int y) {
    this.x = x;
    this.y = y;

    bomb = new Bomb(x, y);

    var ii = new ImageIcon(Commons.ALIEN_IMG);
    setImage(ii.getImage());
  }

  public void act(int direction) {
    this.x += direction;
  }

  public Bomb getBomb() {
    return bomb;
  }

  public static class Bomb extends Sprite {

    private boolean destroyed;

    public Bomb(int x, int y) {
      initBomb(x, y);
    }

    private void initBomb(int x, int y) {
      setDestroyed(true);

      this.x = x;
      this.y = y;

      var ii = new ImageIcon(Commons.BOMB_IMG);
      setImage(ii.getImage());
    }

    public void setDestroyed(boolean destroyed) {
      this.destroyed = destroyed;
    }

    public boolean isDestroyed() {
      return destroyed;
    }
  }
}
