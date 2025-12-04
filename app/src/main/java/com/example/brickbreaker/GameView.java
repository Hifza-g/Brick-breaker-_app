package com.example.brickbreaker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;
import java.util.logging.Handler;
import android.content.Context;



public class GameView extends View {
    Context context;
    float ballx, bally;
    velocity velocity = new velocity(25, 32);
    Handler handler;
    final long UPDATE_MILLS = 30;
    Runnable runnable;
    Paint textPaint = new Paint();
    Paint healthPaint = new Paint();
    Paint brickPaint = new Paint();
    float TEXT_SIZE = 120;
    float paddlex, paddley;
    float oldx, oldpaddlex;
    int points = 0;
    int life = 3;
    Bitmap ball, paddle;
    int dWidth, dHeight;
    int ballWidth, ballHeight;
    MediaPlayer mpHit, mpMiss, mpBreak;
    Random random;
    Brick[] bricks = new Brick[30];
    int numBricks = 0;
    int brokenBricks = 0;
    boolean gameover = false;

    public GameView(Context context) {
        super(context);
        this.context = context;
        ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        paddle = BitmapFactory.decodeResource(getResources(), R.drawable.paddle);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        mpHit = MediaPlayer.create(context, R.raw.hit);
        mpMiss = MediaPlayer.create(context, R.raw.miss);
        mpBreak = MediaPlayer.create(context, R.raw.breaking);
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        healthPaint.setColor(Color.GREEN);
        brickPaint.setColor(Color.argb(255, 249, 129, 0));
        Display display = ((Activity).getWindowManager().getDefaultDisplay);
        Point Size = new Point();
        display.getSize(Size);
        dWidth = Size.x;
        dHeight = Size.y;
        random = new Random();
        ballx = random.nextInt(dWidth - 50);
        bally = dHeight / 3;
        paddley = (dHeight * 4) / 5;
        paddlex = dWidth / 2 - paddle.getWidth() / 2;
        ballWidth = ball.getWidth();
        ballHeight = ball.getHeight();
        createBricks();
    }

    private void createBricks() {
        int brickWidth = dWidth / 8;
        int brickHeight = dHeight / 16;
        for (int column = 0; column < 8; column++) {
            for (int row = 0; row < 3; row++) {
                bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight);
                numBricks++;

            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        ballx += velocity.getX();
        bally += velocity.getY();
        if ((ballx >= dWidth - ball.getWidth()) || ballx <= 0) {
            velocity.setX(velocity.getX() * -1);
        }
        if (bally <= 0) {
            velocity.setY(velocity.getY() * -1);
        }
        if (bally > paddley + paddle.getHeight()) {
            ballx = 1 + random.nextInt(dWidth - ball.getWidth() - 1);
            bally = dHeight / 3;
            if (mpMiss != null) {
                mpMiss.start();
            }
            velocity.setX(xvelocity);
            velocity.setY(32);
            life--;
            if (life == 0) {
                gameover = true;
                launchGameover();
            }
            if (((ballx + ball.getWidth()) >= paddlex) && ((ballx + ball.getWidth()) <= paddlex + paddle.getWidth()) && ((bally + ball.getHeight()) >= paddley)) {

                if (mpHit != null) {
                    mpHit.start();
                }
                velocity.setX(velocity.getX() + 1);
                velocity.setY((velocity.getY() + 1) * -1);
            }
            canvas.drawBitmap(ball, ballx, bally, null);
            canvas.drawBitmap(paddle, paddlex, paddley, null);
            for (int i = 0; i < numBricks; i++) {
                if (bricks[i].getvisible()) {
                    canvas.drawRect(bricks[i].column * bricks[i].width + 1, bricks[i].row * bricks[i].height + 1, bricks[i].column * bricks[i].width + bricks[i].width - 1, bricks[i].row * bricks[i].height + bricks[i].height - 1, brickPaint);

                }
            }
            canvas.drawText("" + points, 20, TEXT_SIZE, textPaint);

            if (life == 2) {
                healthPaint.setColor(Color.YELLOW);
            } else if (life == 1) {
                healthPaint.setColor(Color.RED);
            }
            canvas.drawRect(dWidth - 200, 30, dWidth - 200 + 60 * life, 80, healthPaint);
            for (int i = 0; i < numBricks; i++) {
                if (bricks[i].getvisible()) {
                    if ((ballx + ballWidth >= bricks[i].column * bricks[i].width) && (ballx <= bricks[i].column * bricks[i].width + bricks[i].width) && (bally <= bricks[i].height + bricks[i].height) && (bally >= bricks[i].row * bricks[i].height)) {
                        if (mpBreak != null) {
                            mpBreak.start();
                        }
                        velocity.setY((velocity.getY() + 1) * -1);
                        bricks[i].setInvisible();
                        points += 10;
                        brokenBricks++;
                        if (brokenBricks == 24) {
                            launchGameover();
                        }
                    }
                }

            }
            if (brokenBricks == numBricks) {
                gameover = true;
            }
            if (!gameover) {
                handler.postDelayed(runnable, UPDATE_MILLS);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
 float touchY = event.getY();
if (touchY>=paddley)
        {
   int action = event.getAction();
  if(action==MotionEvent.ACTION_DOWN)
  {
      oldx= event.getX();
      oldpaddlex=paddlex;
  }
  if(action==MotionEvent.ACTION_MOVE)
  {
      float shift = oldx-touchX;
      float newpaddlex=oldpaddlex+shift;
if (newpaddlex<=0)
    paddlex=0;
else if (newpaddlex>=dWidth-paddle.getWidth()) {
    paddlex=dWidth-paddle.getWidth();
}
else {
    paddlex=newpaddlex;
}

}
  }
return  true;

    }

    private void launchGameover(){
        handler.removeCallbacksAndMessages(null);
        Intent intent=new Intent(context,GameOver.class);
        intent.putExtra("points",points);
        context.startActivity(intent);
        ((Activity)context).finish();

    }
    private int xvelocity(){
        int[] values ={-35,-30,-25,25,30,35};
        int index = random.nextInt(6);
        return values[index];
    }}