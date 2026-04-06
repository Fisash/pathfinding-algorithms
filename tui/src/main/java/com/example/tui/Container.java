package com.example.tui;

import com.googlecode.lanterna.input.KeyStroke;

import java.util.ArrayList;
import java.util.List;

public abstract class Container extends Component {
    protected List<Component> children = new ArrayList<>();
    protected Component focusedChild;

    protected int width;
    protected int height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    protected Container(int x, int y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    public void add(Component child, int x, int y) {
        child.setParent(this);
        int newX = this.x + x;
        int newY = this.y + y;
        int deltaX = newX - child.x;
        int deltaY = newY - child.y;
        child.x = newX;
        child.y = newY;
        children.add(child);
        if (focusedChild == null) focusedChild = child;
    
        if (child instanceof Container)
            updateChildPositions((Container) child, deltaX, deltaY);
    }

    private void updateChildPositions(Container container, int deltaX, int deltaY) {
        for (Component child : container.children) {
            child.x += deltaX;
            child.y += deltaY;
            if (child instanceof Container)
                updateChildPositions((Container) child, deltaX, deltaY);
        }
    }

    public void setFocusedChild(Component child) {
        if (child != null && child.getParent() == this) {
            if(focusedChild != null && focusedChild != child)
                focusedChild.onFocusLost();
            focusedChild = child;
            focusedChild.onFocusGained();
        }
    }

    public Component getFocusedChild() {
        return focusedChild;
    }

    @Override
    public boolean handleInput(KeyStroke key) {
        if (focusedChild != null && focusedChild.handleInput(key))
            return true;
        return super.handleInput(key);
    }
}

