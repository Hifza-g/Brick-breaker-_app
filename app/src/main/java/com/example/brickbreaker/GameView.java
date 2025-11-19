package com.example.brickbreaker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.View;

import java.util.Random;
import java.util.logging.Handler;
import android.content.Context;


public class GameView extends View
{
    Context context;
    float ballx, bally;
    velocity velocity= new velocity(25,32);
Handler handler;
final long UPDATE_MILLS=30;
Runnable runnable;
Paint textPaint = new Paint();
Paint healthPaint = new Paint();
Paint brickPaint = new Paint();
float TEXT_SIZE=120;
float paddlex,paddely;
float oldx,oldpaddelx;
int points =0;
int life =3;
Bitmap ball,paddle;
int dWidth,dHeight;
int ballWidth,ballHeight;
MediaPlayer mpHit,mpMiss,mpBreak;
Random random;
Brick[] bricks = new Brick[30];
int numBricks =0;
int brokenBricks = 0;
boolean gameover= false;
       public GameView(Context context)
       {
        super(context);
this.context=context;
ball=BitmapFactory.decodeResource(getResources(),R.drawable.ball);
paddle= BitmapFactory.decodeResource(getResources(),R.drawable.paddle);
handler=new Handler();
runnable=new Runnable() {
}
       }
}
