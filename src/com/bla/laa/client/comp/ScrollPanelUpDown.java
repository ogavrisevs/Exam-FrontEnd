package com.bla.laa.client.comp;

import com.bla.laa.client.Main;
import com.google.gwt.user.client.ui.ScrollPanel;

import java.util.logging.Logger;

public class ScrollPanelUpDown extends ScrollPanel{
    private static final Logger logger = Logger.getLogger(ScrollPanelUpDown.class.getName());
    private final static Integer SCROLL_STEP = 10;
    Main main;

    public ScrollPanelUpDown(Main main ) {
        super();
        this.main = main;
    }

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
        logger.info(ScrollPanelUpDown.class.getName() + ".setScrollPosBack()");

        Integer curPoz = getVerticalScrollPosition();
        Integer minPoz = getMinimumVerticalScrollPosition();
        Integer maxPoz = getMaximumVerticalScrollPosition();
        printPoz();

        if ((curPoz.equals(minPoz)) || (curPoz.equals(maxPoz))){
            if (curPoz.equals(minPoz))
                setVerticalScrollPosition(minPoz + SCROLL_STEP);
            else if (curPoz.equals(maxPoz))
                setVerticalScrollPosition(maxPoz - SCROLL_STEP);
        }
    }


    public void printPoz(){
        Integer curPoz = getVerticalScrollPosition();
        Integer minPoz = getMinimumVerticalScrollPosition();
        Integer maxPoz = getMaximumVerticalScrollPosition();
        logger.info("VerticalScrollPosition( cur [min-max]) : "+ curPoz + " [ "+ minPoz +" - "+maxPoz + " ] " );
   }
}
