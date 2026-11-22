package com.hx.materail.ui.tutorial;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hx.materail.ui.R;


public class CurtainView extends View {
    private Bitmap mbitmap;
    private static int WIDTH = 30;
    private static int HEIGHT = 30;
    //鏈€澶ф按骞崇殑娉㈠舰楂樺害
    private float H_MAX_WAVE_HEIGHT = 50;
    //鏈€澶у瀭鐩寸殑娉㈠舰楂樺害
    private float V_MAX_WAVE_HEIGHT = 500;

    //灏忔牸鐩镐氦鐨勬€荤殑鐐规暟
    private int COUNT = (WIDTH + 1) * (HEIGHT + 1);
    private float[] verts = new float[COUNT * 2];
    private float[] origs = new float[COUNT * 2];
    private int[] colors = new int[COUNT * 2];
    private float k;
    private float progress;

    public CurtainView(Context context) {
        super(context);
        init();
    }

    public CurtainView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CurtainView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setProgress(float progress){
        this.progress = progress;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int index = 0;
        for (int i = 0; i < HEIGHT + 1; i++) {
            for (int j = 0; j < WIDTH + 1; j++) {

                //鎶婃瘡涓€涓按骞冲儚绱犻€氳繃姝ｅ鸡鍏紡杞崲鎴愭寮︽洸绾?                //H_MAX_WAVE_HEIGHT琛ㄧず娉㈠嘲璺熸尝浣庣殑鍨傜洿璺濈锛岀毐瑜跺悗浼氱帇妗戣秴杩囨按骞崇嚎锛屾墍浠ュ線涓嬪亸绉籛AVE_HEIGHT / 2
                //5琛ㄧず娉㈡氮鐨勫瘑闆嗗害锛岃〃绀烘尝宄版尝璋锋€诲叡鏈変簲涓?瀵瑰簲涓婇潰宸﹀浘鐨?,2,3,4,5
                //j灏辨槸姘村钩鍍忕殑X杞村潗鏍?                //K鍐冲畾姝ｅ鸡鏇茬嚎璧峰鐐?x=0)鐐圭殑Y鍧愭爣锛宬=0灏辨槸浠庢尝宄版尝璋风殑涓棿寮€濮嬪乏->鍙崇粯鍒舵洸绾?                float yOffset = H_MAX_WAVE_HEIGHT / 2 * progress + H_MAX_WAVE_HEIGHT / 2 * progress * (float) Math.sin((float)j/WIDTH*5*Math.PI+k);

                //鍨傜洿鏂瑰悜绔栫洿鍘嬬缉鏃剁殑鍧愭爣
                float xPostion = origs[(i*(WIDTH+1)+j)*2+0] + (bitmapwidth - origs[(i*(WIDTH+1)+j)*2+0]) * progress;
                //鍨傜洿鏂瑰悜姝ｅ鸡鏇茬嚎浼樺寲鍚庣殑鍧愭爣,1.1->涓尝宄版尝璋?                float vXSinPostion = V_MAX_WAVE_HEIGHT / 2 * progress * (float) Math.sin((float)i/WIDTH*1.1*Math.PI + k);
                //姣忎釜鍍忕礌鎵洸鍚庣殑x鍧愭爣
                //origs[(i*(WIDTH+1)+j)*2+0] 鍘熷浘x鍧愭爣
                verts[(i*(WIDTH+1)+j)*2+0]= vXSinPostion *((bitmapwidth - xPostion) / bitmapwidth) + xPostion;
                //姣忎釜鍍忕礌鎵洸鍚庣殑Y鍧愭爣
                //origs[(i*(WIDTH+1)+j)*2+1] 鍘熷浘y鍧愭爣
                verts[(i * (WIDTH + 1) + j) * 2 + 1] = origs[(i * (WIDTH + 1) + j) * 2 + 1] + yOffset;//

                int channel = 255 - (int)(yOffset * 3);
                channel = channel < 0 ? 0 : channel;
                channel = channel > 255 ? 255 : channel;
                colors[index] = 0xFF000000 | channel << 16 | channel << 8 | channel;
                index += 1;
            }
        }
        canvas.drawBitmapMesh(mbitmap, WIDTH, HEIGHT, verts, 0, colors, 0, null);
    }

    int bitmapwidth;
    int bitmapheight;

    public void init() {
        mbitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.timg);
        bitmapwidth = mbitmap.getWidth();
        bitmapheight = mbitmap.getHeight();

        COUNT = (WIDTH + 1) * (HEIGHT + 1);
        verts = new float[COUNT * 2];
        origs = new float[COUNT * 2];

        int index = 0;
        for (int i = 0; i < HEIGHT + 1; i++) {
            float fy = bitmapheight / (float) HEIGHT * i;
            for (int j = 0; j < WIDTH + 1; j++) {
                float fx = bitmapwidth / (float) WIDTH * j;
                //鍋舵暟浣嶈褰晉鍧愭爣  濂囨暟浣嶈褰昚鍧愭爣
                origs[index * 2 + 0] = verts[index * 2 + 0] = fx;
                origs[index * 2 + 1] = verts[index * 2 + 1] = fy;
                index++;
            }
        }
    }
}

