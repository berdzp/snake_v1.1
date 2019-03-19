package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    private List<GameObject> snakeParts = new ArrayList<>();
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;

    public Snake(int x, int y){
        GameObject cell1 = new GameObject(x, y);
        GameObject cell2 = new GameObject(x+1, y);
        GameObject cell3 = new GameObject(x+2, y);
        snakeParts.add(cell1);
        snakeParts.add(cell2);
        snakeParts.add(cell3);
    }

    public void draw(Game game){
        for (int i = 0; i<snakeParts.size(); i++)
            if (i==0){
                if (this.isAlive)
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, HEAD_SIGN, Color.BLACK, 75);
                else game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, HEAD_SIGN, Color.RED, 75);
            }
            else {
                if (this.isAlive)
                    game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.BLACK, 75);
                else game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.RED, 75);
            }
    }

    public void move(Apple apple){
        GameObject newH = createNewHead();
        if (checkCollision(newH)) {
            isAlive = false;
            return;
        }
        if (newH.x == apple.x && newH.y == apple.y){
            apple.isAlive = false;
            snakeParts.add(0, newH);
        }
        else {
            if (newH.x>=SnakeGame.WIDTH || newH.y>=SnakeGame.HEIGHT || newH.x<0 || newH.y<0){
                isAlive = false;
            }
            else {
                snakeParts.add(0, newH);
                removeTail();
            }
        }
    }

    public int getLength(){
        return snakeParts.size();
    }

    public GameObject createNewHead(){
        GameObject newHead = null;
        if (this.direction==Direction.LEFT){
            newHead = new GameObject(this.snakeParts.get(0).x-1, this.snakeParts.get(0).y);
        }
        if (this.direction==Direction.RIGHT){
            newHead = new GameObject(this.snakeParts.get(0).x+1, this.snakeParts.get(0).y);
        }
        if (this.direction==Direction.UP){
            newHead = new GameObject(this.snakeParts.get(0).x, this.snakeParts.get(0).y-1);
        }
        if (this.direction==Direction.DOWN){
            newHead = new GameObject(this.snakeParts.get(0).x, this.snakeParts.get(0).y+1);
        }
        return newHead;
    }

    public void removeTail(){
        snakeParts.remove(snakeParts.size()-1);
    }

    public void setDirection(Direction direction){
        if (this.direction == Direction.LEFT && snakeParts.get(0).x == snakeParts.get(1).x){return;}
        if (this.direction == Direction.RIGHT && snakeParts.get(0).x == snakeParts.get(1).x){return;}
        if (this.direction == Direction.UP && snakeParts.get(0).y == snakeParts.get(1).y){return;}
        if (this.direction == Direction.DOWN && snakeParts.get(0).y == snakeParts.get(1).y){return;}
        if (direction == Direction.DOWN && this.direction == Direction.UP){return;}
        if (direction == Direction.UP && this.direction == Direction.DOWN){return;}
        if (direction == Direction.LEFT && this.direction == Direction.RIGHT){return;}
        if (direction == Direction.RIGHT && this.direction == Direction.LEFT){return;}
        this.direction = direction;
    }

    public boolean checkCollision(GameObject head){
        for (GameObject bodyPart: snakeParts) {
            if (bodyPart.x == head.x && bodyPart.y == head.y)
                return true;
        }
        return false;
    }
}
