package com.bla.laa.client.comp;

import com.google.gwt.user.client.ui.ScrollPanel;

public class ScrollPanelUpDown extends ScrollPanel{

    public enum Direction {
        UP (1),
        DOWN (-1),
        NO_CHANGE(0);

        private int val;
        Direction(int val){
            this.val = val;
        }
    }

    int currentPosition = 0;

    public Direction getDirection(){
        int currentPoz = super.getScrollPosition();
        if (this.currentPosition == currentPoz )
            return Direction.NO_CHANGE;

        if (this.currentPosition > currentPoz ){
            this.currentPosition = currentPoz;
            return Direction.UP;
        }

        if (this.currentPosition < currentPoz ){
            this.currentPosition = currentPoz;
            return Direction.DOWN;
        }
        return Direction.NO_CHANGE;
    }
}
