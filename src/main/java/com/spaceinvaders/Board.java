package com.spaceinvaders;

import com.spaceinvaders.sprite.Alien;
import com.spaceinvaders.sprite.Player;
import com.spaceinvaders.sprite.Shot;
import com.spaceinvaders.sprite.Sprite;
import com.spaceinvaders.views.EndGameView;
import com.spaceinvaders.views.InGameView;
import com.spaceinvaders.views.MainMenuView;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board extends JPanel {

  private Dimension d;
  private List<Alien> aliens;
  private Player player;
  private Shot shot;

  private int direction = -1;
  private int deaths = 0;
  private int score = 0;
  private int numberOfAliensToDestroy = 0;
  private int level = Commons.LEVEL_INIT;

  private boolean inMainMenu = true;
  private boolean inGame = false;
  private boolean inHighScores = false;
  private boolean gameOver = false;
  private boolean levelComplete = false;
  private boolean allLevelsComplete = false;
  private String endMsg = Commons.DEFAULT_END_MSG;

  private Timer timer;

  public Board() {
    initBoard();
  }

  private void initBoard() {
    addKeyListener(new TAdapter());
    setFocusable(true);
    d = new Dimension(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
    setBackground(Color.black);

    timer = new Timer(Commons.DELAY, new GameCycle());
    timer.start();

    gameInit();
  }

  private void gameInit() {
    aliens = new ArrayList<>();

    int alienRows = level;
    int aliensPerRow = Commons.INIT_ALIENS_PER_ROW + level;
    numberOfAliensToDestroy = alienRows * aliensPerRow;

    for (int i = 0; i < alienRows; i++) {
      for (int j = 0; j < aliensPerRow; j++) {
        var alien = new Alien(
            Commons.ALIEN_INIT_X + 18 * j,
            Commons.ALIEN_INIT_Y + 18 * i
        );
        aliens.add(alien);
      }
    }

    player = new Player();
    shot = new Shot();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    doDrawing(g);
  }

  private void doDrawing(Graphics g) {
    g.setColor(Color.black);
    g.fillRect(0, 0, d.width, d.height);
    g.setColor(Color.green);

    if (inMainMenu) {
      if (timer.isRunning()) {
        timer.stop();
      }

      MainMenuView.draw(this, g);
    } else if (inGame) {
      InGameView.draw(this, g, score, level);

      drawAliens(g);
      drawPlayer(g);
      drawShot(g);
      drawBombing(g);
    } else if (inHighScores) {
      if (timer.isRunning()) {
        timer.stop();
      }

      HighScoreService.display(this, g);
    } else {
      if (timer.isRunning()) {
        timer.stop();
      }

      EndGameView.draw(this, g, endMsg, gameOver, allLevelsComplete, levelComplete, score);
      HighScoreService.accept(this, g, gameOver, allLevelsComplete, score);
    }

    Toolkit.getDefaultToolkit().sync();
  }

  private void drawAliens(Graphics g) {
    aliens.forEach(alien -> {
      if (alien.isVisible()) {
        g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
      }

      if (alien.isDying()) {
        alien.die();
      }
    });
  }

  private void drawPlayer(Graphics g) {
    if (player.isVisible()) {
      g.drawImage(player.getImage(), player.getX(), player.getY(), this);
    }

    if (player.isDying()) {
      player.die();
      inGame = false;
      gameOver = true;
    }
  }

  private void drawShot(Graphics g) {
    if (shot.isVisible()) {
      g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
    }
  }

  private void drawBombing(Graphics g) {
    aliens.stream()
        .map(Alien::getBomb)
        .filter(b -> !b.isDestroyed())
        .forEach(b -> g.drawImage(b.getImage(), b.getX(), b.getY(), this));
  }

  private class GameCycle implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      doGameCycle();
    }
  }

  private void doGameCycle() {
    update();
    repaint();
  }

  private void update() {
    if (deaths == numberOfAliensToDestroy) {
      inGame = false;
      timer.stop();
      endMsg = String.format(Commons.LEVEL_COMPLETE_MSG, level);
      levelComplete = true;
      if (level == Commons.LEVEL_TOTAL) {
        allLevelsComplete = true;
      }
    }

    player.act();

    updateShot();
    updateAlienDirection();
    checkForInvasion();
    updateAlienBombs();
  }

  private void updateShot() {
    if (shot.isVisible()) {
      int shotX = shot.getX();
      int shotY = shot.getY();

      aliens.forEach(alien -> {
        int alienX = alien.getX();
        int alienY = alien.getY();

        if (alien.isVisible() && shot.isVisible()) {
          if (isCollision(shotX, shotY, alienX, alienY, Commons.ALIEN_WIDTH, Commons.ALIEN_HEIGHT)) {
            var ii = new ImageIcon(Commons.EXPLOSION_IMG);
            alien.setImage(ii.getImage());
            alien.setDying(true);
            deaths++;
            score++;
            shot.die();
          }
        }
      });

      int y = shot.getY();
      y -= 4;

      if (y < 0) {
        shot.die();
      } else {
        shot.setY(y);
      }
    }
  }

  private void updateAlienDirection() {
    aliens.forEach(alien -> {
      int x = alien.getX();

      if (x >= Commons.BOARD_WIDTH - Commons.BORDER_RIGHT && direction != -1) {
        direction = -1;
        aliens.forEach(
            a -> a.setY(a.getY() + Commons.GO_DOWN)
        );
      }

      if (x <= Commons.BORDER_LEFT && direction != 1) {
        direction = 1;
        aliens.forEach(
            a -> a.setY(a.getY() + Commons.GO_DOWN)
        );
      }
    });
  }

  private void checkForInvasion() {
    aliens.stream()
        .filter(Sprite::isVisible)
        .forEach(alien -> {
          int y = alien.getY();

          if (y > Commons.GROUND - Commons.ALIEN_HEIGHT) {
            inGame = false;
            gameOver = true;
            player.setDying(true);
            endMsg = Commons.INVASION_MSG;
          }

          alien.act(direction);
        });
  }

  private void updateAlienBombs() {
    var generator = new Random();

    aliens.forEach(alien -> {
      int shot = generator.nextInt(15);
      Alien.Bomb bomb = alien.getBomb();

      if (shot == Commons.CHANCE && alien.isVisible() && bomb.isDestroyed()) {
        bomb.setDestroyed(false);
        bomb.setX(alien.getX());
        bomb.setY(alien.getY());
      }

      int bombX = bomb.getX();
      int bombY = bomb.getY();
      int playerX = player.getX();
      int playerY = player.getY();

      if (player.isVisible() && !bomb.isDestroyed()) {
        if (isCollision(bombX, bombY, playerX, playerY, Commons.PLAYER_WIDTH, Commons.PLAYER_HEIGHT)) {
          var ii = new ImageIcon(Commons.EXPLOSION_IMG);
          player.setImage(ii.getImage());
          inGame = false;
          gameOver = true;
          player.setDying(true);
          bomb.setDestroyed(true);
          endMsg = Commons.DEFAULT_END_MSG;
        }
      }

      if (!bomb.isDestroyed()) {
        bomb.setY(bomb.getY() + 1);

        if (bomb.getY() >= Commons.GROUND - Commons.BOMB_HEIGHT) {
          bomb.setDestroyed(true);
        }
      }
    });
  }

  private boolean isCollision(int obj1X, int obj1Y, int obj2X, int obj2Y, int obj2Width, int obj2Height) {
    return obj1X >= (obj2X) &&
        obj1X <= (obj2X + obj2Width) &&
        obj1Y >= (obj2Y) &&
        obj1Y <= (obj2Y + obj2Height);
  }

  private class TAdapter extends KeyAdapter {
    @Override
    public void keyReleased(KeyEvent e) {
      player.keyReleased(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
      player.keyPressed(e);

      switch (e.getKeyCode()) {
        case KeyEvent.VK_ESCAPE: // Go to main menu
          resetGame(true);
          break;

        case KeyEvent.VK_H:
          if (inMainMenu) {
            viewHighScores();
          }
          break;

        case KeyEvent.VK_ENTER: // Start game from main menu or go to next level
          if (inMainMenu) {
            resetGame(false);
            break;
          }
          if (!inHighScores && !inGame && !gameOver && level < Commons.LEVEL_TOTAL) {
            goToNextLevel();
          }
          break;

        case KeyEvent.VK_R: // Restart game
          if (!inMainMenu && !inHighScores) {
            resetGame(false);
          }
          break;

        case KeyEvent.VK_SPACE: // Shoot
          if (inGame && !shot.isVisible()) {
            int x = player.getX();
            int y = player.getY();
            shot = new Shot(x, y);
          }
          break;
      }
    }
  }

  private void resetGame(boolean toMainMenu) {
    if (!timer.isRunning()) {
      timer.start();
    }

    // Reset params
    inMainMenu = toMainMenu;
    inHighScores = false;
    inGame = !toMainMenu;
    gameOver = false;
    levelComplete = false;
    allLevelsComplete = false;
    direction = -1;
    deaths = 0;
    score = 0;
    numberOfAliensToDestroy = 0;
    level = Commons.LEVEL_INIT;
    endMsg = Commons.DEFAULT_END_MSG;

    gameInit();
  }

  private void viewHighScores() {
    if (!timer.isRunning()) {
      timer.start();
    }

    inHighScores = true;
    inMainMenu = false;
    gameInit();
  }

  private void goToNextLevel() {
    if (!timer.isRunning()) {
      timer.start();
    }

    // Reset params
    inGame = true;
    levelComplete = false;
    direction = -1;
    deaths = 0;
    numberOfAliensToDestroy = 0;
    level++;

    gameInit();
  }
}
