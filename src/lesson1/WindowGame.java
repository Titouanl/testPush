//package lesson1;
//
//import org.newdawn.slick.Animation;
//import org.newdawn.slick.AppGameContainer;
//import org.newdawn.slick.BasicGame;
//import org.newdawn.slick.Color;
//import org.newdawn.slick.GameContainer;
//import org.newdawn.slick.Graphics;
//import org.newdawn.slick.Image;
//import org.newdawn.slick.Input;
//import org.newdawn.slick.SlickException;
//import org.newdawn.slick.SpriteSheet;
//import org.newdawn.slick.tiled.TiledMap;
//
//public class WindowGame extends BasicGame {
//
//	public static void main(String[] args) throws SlickException {
//		new AppGameContainer(new WindowGame(), 640, 480, false).start();
//	}
//
//	private GameContainer container;
//	private TiledMap map;
//
//	private float x = 300, y = 300;
//	private int direction = 0;
//	private boolean moving = false;
//	private Animation[] animations = new Animation[8];
//	
//	private float xCamera = x, yCamera = y;
//	private boolean onStair;
//
//	public WindowGame() {
//		super("Lesson 1 :: WindowGame");
//	}
//
//	@Override
//	public void update(GameContainer container, int delta)
//			throws SlickException {
//
//		updateTrigger();
//		
//	    if (this.moving) {
//	        float futurX = getFuturX(delta);
//	        float futurY = getFuturY(delta);
//	        boolean collision = isCollision(futurX, futurY);
//	        if (collision) {
//	            this.moving = false;
//	        } else {
//	            this.x = futurX;
//	            this.y = futurY;
//	        }
//	    }
//	    int w = container.getWidth() / 4;
//	    if (this.x > this.xCamera + w) this.xCamera = this.x - w;
//	    if (this.x < this.xCamera - w) this.xCamera = this.x + w;
//	    int h = container.getHeight() / 4;
//	    if (this.y > this.yCamera + h) this.yCamera = this.y - h;
//	    if (this.y < this.yCamera - h) this.yCamera = this.y + h;
//	}
//	
//	private void updateTrigger() throws SlickException {
//		this.onStair = false;
//	    for (int objectID = 0; objectID < this.map.getObjectCount(0); objectID++) {
//	        if (isInTrigger(objectID)) {
//	            String type = this.map.getObjectType(0, objectID);
//	            if ("teleport".equals(type)) {
//	                teleport(objectID);
//	            } else if ("stair".equals(type)) {
//	                this.onStair = true;
//	            } else if ("change-map".equals(type)) {
//	                changeMap(objectID);
//	            }
//	        }
//	    }
//	}
//	
//	private boolean isInTrigger(int id) {
//	    return x > map.getObjectX(0, id)
//	            && x < map.getObjectX(0, id) + map.getObjectWidth(0, id)
//	            && y > map.getObjectY(0, id)
//	            && y < map.getObjectY(0, id) + map.getObjectHeight(0, id);
//	}
//
//	private void teleport(int id) {
//	    this.x = Float.parseFloat(this.map.getObjectProperty(0, id, "dest-x", Float.toString(this.x)));
//	    this.y = Float.parseFloat(this.map.getObjectProperty(0, id, "dest-y", Float.toString(this.y)));
//	}
//	
//	private void changeMap(int objectID) throws SlickException {
//	    teleport(objectID);
//	    String newMap = this.map.getObjectProperty(0, objectID, "dest-map", "undefined");
//	    if (!"undefined".equals(newMap)) {
//	        this.map = new TiledMap("src/main/ressources/map/" + newMap);
//	    }
//	}
//	
//	private boolean isCollision(float x, float y) {
//	    int tileW = this.map.getTileWidth();
//	    int tileH = this.map.getTileHeight();
//	    int logicLayer = this.map.getLayerIndex("logic");
//	    Image tile = this.map.getTileImage((int) x / tileW, (int) y / tileH, logicLayer);
//	    boolean collision = tile != null;
//	    if (collision) {
//	        Color color = tile.getColor((int) x % tileW, (int) y % tileH);
//	        collision = color.getAlpha() > 0;
//	    }
//	    return collision;
//	}
//	
//	private float getFuturX(int delta) {
//	    float futurX = this.x;
//	    switch (this.direction) {
//	    case 1: futurX = this.x - .1f * delta; break;
//	    case 3: futurX = this.x + .1f * delta; break;
//	    }
//	    return futurX;
//	}
//	
//	private float getFuturY(int delta) {
//	    float futurY = this.y;
//	    switch (this.direction) {
//	    case 0: futurY = this.y - .1f * delta; break;
//	    case 2: futurY = this.y + .1f * delta; break;
//	    }
//	    return futurY;
//	}
//
//	@Override
//	public void init(GameContainer container) throws SlickException {
//		this.container = container;
//		this.map = new TiledMap("src/main/ressources/map/testMap.tmx");
//		SpriteSheet spriteSheet = new SpriteSheet(
//				"src/main/ressources/sprites/character.png", 64, 64);
//		this.animations[0] = loadAnimation(spriteSheet, 0, 1, 0);
//		this.animations[1] = loadAnimation(spriteSheet, 0, 1, 1);
//		this.animations[2] = loadAnimation(spriteSheet, 0, 1, 2);
//		this.animations[3] = loadAnimation(spriteSheet, 0, 1, 3);
//		this.animations[4] = loadAnimation(spriteSheet, 1, 9, 0);
//		this.animations[5] = loadAnimation(spriteSheet, 1, 9, 1);
//		this.animations[6] = loadAnimation(spriteSheet, 1, 9, 2);
//		this.animations[7] = loadAnimation(spriteSheet, 1, 9, 3);
//	}
//
//	private Animation loadAnimation(SpriteSheet spriteSheet, int startX,
//			int endX, int y) {
//		Animation animation = new Animation();
//		for (int x = startX; x < endX; x++) {
//			animation.addFrame(spriteSheet.getSprite(x, y), 100);
//		}
//		return animation;
//	}
//
//	@Override
//	public void render(GameContainer container, Graphics g)
//			throws SlickException {
//	    g.translate(container.getWidth() / 2 - (int) this.xCamera, 
//	            container.getHeight() / 2 - (int) this.yCamera);
//	    
//	    this.map.render(0, 0, 0);
//	    this.map.render(0, 0, 1);
//
//		g.setColor(new Color(0, 0, 0, .5f));
//		g.fillOval(x - 16, y - 8, 32, 16);
//		g.drawAnimation(animations[direction + (moving ? 4 : 0)], x - 32,
//				y - 60);
//
//	    this.map.render(0, 0, 2);
//	}
//
//	@Override
//	public void keyPressed(int key, char c) {
//		switch (key) {
//		case Input.KEY_UP:
//			this.direction = 0;
//			this.moving = true;
//			break;
//		case Input.KEY_LEFT:
//			this.direction = 1;
//			this.moving = true;
//			break;
//		case Input.KEY_DOWN:
//			this.direction = 2;
//			this.moving = true;
//			break;
//		case Input.KEY_RIGHT:
//			this.direction = 3;
//			this.moving = true;
//			break;
//		}
//	}
//
//	@Override
//	public void keyReleased(int key, char c) {
//		if (Input.KEY_ESCAPE == key) {
//			container.exit();
//		}
//		this.moving = false;
//	}
//
//}