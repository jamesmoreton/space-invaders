package com.spaceinvaders;

import java.awt.Font;

public interface Commons {

  int BOARD_WIDTH = 358;
  int BOARD_HEIGHT = 350;
  int BORDER_RIGHT = 30;
  int BORDER_LEFT = 5;

  int GROUND = 290;
  int BOMB_HEIGHT = 5;

  int ALIEN_HEIGHT = 12;
  int ALIEN_WIDTH = 12;
  int ALIEN_INIT_X = 150;
  int ALIEN_INIT_Y = 20;

  int GO_DOWN = 15;
  int CHANCE = 5;
  int DELAY = 17;
  int PLAYER_WIDTH = 15;
  int PLAYER_HEIGHT = 10;

  int INIT_ALIENS_PER_ROW = 6;
  int LEVEL_TOTAL = 5;
  int LEVEL_INIT = 1;

  // Main menu view
  String TITLE_1 = "SPACE";
  String TITLE_2 = "INVADERS";
  String TITLE_PLAY_MSG = "Press ENTER to play!";
  String TITLE_INSTRUCTIONS_MSG = "Instructions: Arrows to move, SPACE to shoot";
  String TITLE_HIGH_SCORES_MSG = "Press H to view high scores";

  // End game view
  String DEFAULT_END_MSG = "Game Over";
  String SCORE_PREFIX = "Score: ";
  String LEVEL_PREFIX = "Level ";
  String GAME_OVER_MSG = "Bad luck, try again";
  String MOVE_TO_NEXT_LEVEL_MSG = "Press ENTER to move to the next level";
  String ALL_LEVELS_COMPLETE_MSG = "Congratulations, all levels complete!";
  String RESTART_MSG = "Press R to restart or ESC for menu";
  String LEVEL_COMPLETE_MSG = "Level %s complete!";
  String INVASION_MSG = "Invasion!";

  // High score view
  String NEW_HIGH_SCORE = "New high score!";
  String HIGH_SCORES = "High scores: ";
  String NO_HIGH_SCORES = "No high scores to display :(";
  String ESCAPE_MSG = "Press ESC to return to menu";

  // Image resources
  String ALIEN_IMG = "resources/alien.png";
  String BOMB_IMG = "resources/bomb.png";
  String EXPLOSION_IMG = "resources/explosion.png";
  String PLAYER_IMG = "resources/player.png";
  String SHOT_IMG = "resources/shot.png";

  // Fonts
  Font SMALL_FONT = new Font(Font.DIALOG, Font.PLAIN, 12);
  Font LARGE_FONT = new Font(Font.DIALOG, Font.BOLD, 14);
  Font TITLE_FONT = new Font(Font.DIALOG, Font.BOLD, 45);
}
