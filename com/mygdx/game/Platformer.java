package com.mygdx.game;

import java.util.Random;
import java.math.MathContext;
import java.util.Date;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Platformer extends ApplicationAdapter {
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private OrthographicCamera camera;

	private Texture img;
	Character perso;
	
	private Animator animator;
	private Animator[] walkAnimation;

	Rectangle rec;
	
	protected int[][] map;
	protected float widthTile = 8;
	private float heightTile = widthTile;
	private int widthMap;
	private int heightMap;	
	private Date time;	
	private long lastClickTime;
	
	private Random randGen;
	private int randomPourcentage = 50;	
	private boolean randomSeed = false;

	
	static final private int WIDTHSCREEN = 1600;
	static final private int  HEIGHTSCREEN = 900;
	static private float GRAVITY = 20f;
	
	@Override
	public void create () {
		
		lastClickTime = 0;
		time = new Date();
		randGen = new java.util.Random( 	randomSeed?  time.getTime() : 1 );
		
		
		
		widthMap = WIDTHSCREEN / (int)widthTile;
		heightMap = HEIGHTSCREEN / (int)heightTile;
		map = new int[widthMap][heightMap];
		generateMap();
	
		
		
		
		perso = new Character(50, 1, 400, 1.2f, 300, 1, 400);

		img = new Texture("badlogic.jpg");	
		batch = new SpriteBatch();		
		
		
		shapeRenderer = new ShapeRenderer();	
		camera = new OrthographicCamera();
	    camera.setToOrtho(false, WIDTHSCREEN, HEIGHTSCREEN);
		
		rec = new Rectangle( WIDTHSCREEN / 2, HEIGHTSCREEN / 2, 64, 64 );
		
		animator = new Animator (9, 4, "character.png", 1f/9f);
		walkAnimation = animator.createAnimations(4);
				
	}
	

	
	public void displayConsoleMap(){
		for (int i = 0; i < widthMap; i++){
			for (int j = 0; j <heightMap; j++){				
					System.out.print( (map[i][j] == 1)? "1" : "0" );		
			}
			System.out.println("");
		}
	}
	
	
	public void displayGraphicMap(){
		
		shapeRenderer.begin(ShapeType.Filled);		
		for (int i = 0; i < widthMap; i++){
			for (int j = 0; j <heightMap; j++){
				
				if ( map[i][j] == 0 ) 
					shapeRenderer.setColor(1, 1, 1, 1);
				else
					shapeRenderer.setColor(.35f, .2f, 0, 1);
			 
				 shapeRenderer.rect( i * widthTile, j * heightTile, widthTile, heightTile);
			}
		}
		shapeRenderer.end();
	}
	
	public void generateMap(){

		fillRandomMap();
	
		for( int i = 0 ; i < 7 ; i ++){
			smoothMap();
		}
	}
	
	public void fillRandomMap(){

		
		for (int i = 0; i < widthMap; i++){
			for (int j = 0; j <heightMap; j++){
				if (i  == 0 || i == widthMap -1 || j == 0 || j == heightMap -1 )
					map[i][j] = 1;
				else
					map[i][j] = ( randGen.nextInt(100) < randomPourcentage) ? 1 : 0;
				
			}
		}
	}
	
	public void smoothMap(){
		int surroundingWallCount = 0;
		for (int x = 1; x < widthMap -1; x++){
			for (int y = 1; y <heightMap -1; y++){
				
					surroundingWallCount = getSurroundigWallCount(x, y);
					
					if( surroundingWallCount < 4 ){
						map[x][y] = 0;
						
					}
					else if ( surroundingWallCount > 4)
						map[x][y] = 1;
			
//					else
//						map[x][y] = 1;
			}
		}
		
	}
	
	public int getSurroundigWallCount(int xBlock, int yBlock){
		int count = 0;

			
		for (int xNeighbour = xBlock -1; xNeighbour <= xBlock +1; xNeighbour++){
			for (int yNeighbour = yBlock -1; yNeighbour <= yBlock +1; yNeighbour++){
				
				if(xNeighbour >= 0 && xNeighbour <= widthMap - 1 && yNeighbour >= 0 && yNeighbour <= heightMap - 1)
				
					if (xNeighbour != xBlock || yNeighbour != yBlock)
						count += map[xNeighbour][yNeighbour];
				
			}
		}
		return count;
		
	}

	@Override	public void render () {
		int centerX, centerY;
		time = new Date();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
	
		
		if(Gdx.input.isTouched() &&  (time.getTime() - lastClickTime > 100 ) ) {
			lastClickTime = time.getTime();
			generateMap();		    
		}
		
		displayGraphicMap();
		
		
		
		camera.update();
		 
		shapeRenderer.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);

		
		 centerX = (int) (perso.getX() + 32);
		 centerY = (int) (perso.getY() + 32);
	 
	 



		 batch.begin();
		 
		 if( perso.isOnAir() )
			 perso.affectGravity (GRAVITY);
		 
		 
		 if(Gdx.input.isKeyPressed(Keys.Q) ) {
			 	perso.moveRight();
		 }
		 
		 if(Gdx.input.isKeyPressed(Keys.D) ) { 
			 perso.moveLeft();
		 }
		 
		 if(Gdx.input.isKeyPressed(Keys.Z)  ) {
			 	perso.jump();
		 }
		 
		 if(Gdx.input.isKeyPressed(Keys.S) ) 
			 perso.affectGravity (GRAVITY);
	
		 
		 perso.moveCharacter();
		
		 
		 
		 
		 
		 if ( perso.getVectDir().x != 0f || perso.getVectDir().y != 0f) {
			 batch.draw(walkAnimation[ perso.getDirection() ].nextFrame(), perso.getX(), perso.getY());
		 }
		 else {
			 batch.draw(walkAnimation[ perso.getDirection() ].firstFrame(), perso.getX(), perso.getY());
		 }
			batch.end();
		 
			 shapeRenderer.begin(ShapeType.Filled);
			 shapeRenderer.line(centerX, centerY , centerX + (perso.getVectDir().x ) ,  centerY + (perso.getVectDir().y  ) );
			 shapeRenderer.end();
	
		 
		 if( perso.getY() <= HEIGHTSCREEN/2 ){
			 perso.setOnFloor() ;
			 perso.resetJump();
			
			 perso.setY( HEIGHTSCREEN / 2);
			 
			 perso.immobilizeCharacter();
			 
		 }
		
		 else{
			perso.setOnAir();
		 	perso.blockHorizontalMove();
		 }
		 
	 	

	}

}