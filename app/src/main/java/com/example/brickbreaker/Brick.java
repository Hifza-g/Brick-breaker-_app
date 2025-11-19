package com.example.brickbreaker;

public class Brick {
    private boolean isvisible;
    public int row, column, width,height;
    public Brick(int row,int column,int width,int height)
    {
        isvisible =true;
        this.row=row;
        this.column=column;
        this.width=width;
        this.height=height;
    }
    public void setInvisible()
    {
      isvisible=false;
    }
    public boolean getvisible()
    {
        return isvisible;
    }

}
