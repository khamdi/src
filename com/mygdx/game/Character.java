package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Character {
	private float x, y;
	private final float speed;
	
	
	private final int healthMax;
	private int health;
	private final float weight;
	private float jumpCapacity;

	private Vector2 vectDir;
	
	private boolean moving;

	private int nbJumpMax;
	private int nbJumpLeft;
	
	
	private int direction;
	private boolean onFloor;
	
	
	private boolean faceRight;
	
	public Character (float x, float y, int healthMax, float weight, float speed, int nbJumpMax, float jumpCapacity) {
		
		this.x = isValidCoord(x);
		this.y = isValidCoord(y);
		this.healthMax = isValidHealth(healthMax);
		this.health =this.healthMax;
		this.weight = isValidWeight(weight);
		this.moving = false;
		
		this.speed = isValidSpeed(speed);
		this.onFloor = false;
		this.faceRight = true;
		
		this.nbJumpMax = isValidJump(nbJumpMax);
		
		this.nbJumpLeft = this.nbJumpMax;
		this.jumpCapacity = jumpCapacity;
		vectDir = new Vector2();
		
		this.direction = 3;

	}
	
	public void moveRight(){
		vectDir.add(-speed, 0);
	 	direction = 1;
	}
	
	public void moveLeft(){
		vectDir.add(speed, 0);
	 	direction = 3;
	}
	public void setOnFloor(){
		this.onFloor = true;
	}
	
	public void setOnAir() {
		this.onFloor = false;
	}
	
	public void setFaceRight(){
		this.faceRight = true;
	}
	
	public void setFaceLeft(){
		this.faceRight = false;
	}
	
	
	public void setPosition (float x, float y) {
		this.x = x;
		this.y = y;
	}
	public void setX(float x){
		this.x = x;
	}
	
	public void setY(float y){
		this.y = y;
	}
	
	public void setMoving (boolean moving){
		this.moving = moving;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() { 
		return y;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public int getHealthMax() {
		return healthMax;
	}
	
	public int getHealth() {
		return health;
	}
	
	public float getWeight() {
		return weight;
	}
	
	public Vector2 getVectDir() {
		return vectDir;
	}
	
	public int getNbJumpLeft() {
		return nbJumpLeft;
	}
	
	public void resetJump() {
		nbJumpLeft = 1;
	}
	
	
	public boolean isMoving () {
		return moving;
	}
	
	public boolean isFacingRight() {
		return this.faceRight;
	}
	
	public boolean isOnFloor() {
		return this.onFloor;
	}
	
	public boolean isOnAir() {
		return (!this.onFloor);
	}
	
	public boolean isDead(){
		return (this.health <= 0);
	}
	
	
	
	public void jump() {
		if(nbJumpLeft > 0){
			this.nbJumpLeft --;
			this.setOnAir();
		 	vectDir.add(0, jumpCapacity );
		}
	}
	
	public void addForce (float x, float y) {
		vectDir.add (x, y);
	}
	
	public void affectGravity (float gravity) {
		vectDir.add (0, -gravity);
	}
	
	public void immobilizeCharacter () {
		vectDir.x = 0;
		vectDir.y = 0;
	}
	
	public void blockHorizontalMove(){
		vectDir.x = 0;
	}
	
	public void blockVerticalMove(){
		vectDir.y = 0;
	}
	
	public void checkCollision(){
		
	}
	
	public void moveCharacter () {
		float futurX = 0;
		float futurY = 0;
		
		futurX = vectDir.x * Gdx.graphics.getDeltaTime();
		futurY = vectDir.y * Gdx.graphics.getDeltaTime();
		
		
		 this.x += futurX;
		 this.y += futurY;
		
//		if (moving == true) {
//			this.x += move.getX();
//			this.y -= move.getY();
//			return true;
//		}
//		return false;
	}
	
	
	public float isValidCoord(float z){
		if (z < 0)
			throw new IllegalArgumentException("A coordonate can't be negative.");
		return z;
	}
	
	public int isValidHealth (int health) {
		if (health <= 0)
			throw new IllegalArgumentException("Health can't be null or negative.");
		return health;
	}
	
	public float isValidWeight(float weight){
		if (weight <= 0)
			throw new IllegalArgumentException("A weight can't be null or negative.");
		return weight;
	}
	
	public float isValidSpeed(float speed){
		if (speed < 0)
				throw new IllegalArgumentException("The speed can't be negative.");
		return speed;
	}
	
	public int isValidJump(int jump){
		if (jump <= 0 )
			throw new IllegalArgumentException("The number of jumps can't be negative or null.");
		return jump;
	}
	
	
/*	
	public boolean isOutOfBounds (float x, float y) {
		return (this.x < 0 || this.x > x || this.y < 0 || this.y > y);
	}
*/
}
