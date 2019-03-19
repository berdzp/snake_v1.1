package com.javarush.games.snake;

import com.javarush.engine.cell.*;

public class SnakeGame extends Game {
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private Snake snake;
    private int turnDelay;
    private Apple apple;
    private boolean isGameStopped;
    private static final int GOAL  = 28;
    private int score;

    public void initialize(){
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void createGame(){
        isGameStopped = false;
        score = 0;
        setScore(score);
        turnDelay = 300;
        setTurnTimer(turnDelay);
        snake = new Snake(WIDTH/2, HEIGHT/2);
        createNewApple();
        drawScene();

    }

    private void gameOver(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.BLACK, "GAME OVER", Color.RED, 36 );
    }

    private void win(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.BLACK, "YOU WIN", Color.RED, 36 );
    }

    private void drawScene(){
        for (int i=0; i<WIDTH; i++){
            for (int j=0; j<HEIGHT; j++){
                setCellValueEx(i, j, Color.DARKSEAGREEN, "");
            }
        }
        snake.draw(this);
        apple.draw(this);

    }

    public void onTurn(int t){
        snake.move(apple);
        if (apple.isAlive==false){createNewApple(); score =+ 5; setScore(score); turnDelay = turnDelay - 10; setTurnTimer(turnDelay);}
        if (snake.isAlive==false) {gameOver();}
        if (snake.getLength()>GOAL) {win();}
        drawScene();
    }

    public void onKeyPress(Key button){
        if (button == Key.SPACE && isGameStopped == true){
            createGame();
        }
        if (button == Key.DOWN){
            snake.setDirection(Direction.DOWN);
        }
        if (button == Key.UP){
            snake.setDirection(Direction.UP);
        }
        if (button == Key.RIGHT){
            snake.setDirection(Direction.RIGHT);
        }
        if (button == Key.LEFT){
            snake.setDirection(Direction.LEFT);
        }
    }

    private void createNewApple(){
        apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        while (snake.checkCollision(apple)){
            apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        }
    }
}
