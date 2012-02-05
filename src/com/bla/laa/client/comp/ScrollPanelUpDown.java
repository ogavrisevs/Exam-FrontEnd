package com.bla.laa.client.comp;

import com.google.gwt.user.client.ui.ScrollPanel;

import java.util.logging.Logger;

public class ScrollPanelUpDown extends ScrollPanel{
    private static final Logger logger = Logger.getLogger(ScrollPanelUpDown.class.getName());
    private final static Integer SCROLL_STEP = 10;

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

    public void setVertScroll(){
        Integer curPoz = getVerticalScrollPosition();
        Integer minPoz = getMinimumVerticalScrollPosition();
        Integer maxPoz = getMaximumVerticalScrollPosition();

        if ((curPoz == 0) && (maxPoz == 0) && (minPoz == 0))
            setVerticalScrollPosition(10);

    }

    public void setScrollPosBack(){
        Integer curPoz = getVerticalScrollPosition();
        Integer minPoz = getMinimumVerticalScrollPosition();
        Integer maxPoz = getMaximumVerticalScrollPosition();

        if ((curPoz == minPoz) || (curPoz == maxPoz)){
            logger.info("((curPoz == minPoz) || (curPoz == maxPoz))");
            if (curPoz == minPoz)
                setVerticalScrollPosition(minPoz + SCROLL_STEP);
            else if (curPoz == maxPoz)
                setVerticalScrollPosition(maxPoz - SCROLL_STEP);
        }
    }


    public void printPoz(){
        logger.info("VerticalScrollPosition() : " +String.valueOf(getVerticalScrollPosition()));
        logger.info("getMinimumVerticalScrollPosition() " +String.valueOf(getMinimumVerticalScrollPosition() ));
        logger.info("getMaximumVerticalScrollPosition() : " +String.valueOf(getMaximumVerticalScrollPosition() ));

    }


}
