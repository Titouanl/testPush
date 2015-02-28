package lesson1;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class ObjectsGame extends BasicGame  {

	public ObjectsGame(String title) {
		super("test jeu");
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws SlickException {
	new AppGameContainer(new ObjectsGame("test"), 640, 480, false).start();
}


	private GameContainer container;
	private Map map = new Map();
	private Player player = new Player(map);
	private Camera camera = new Camera(player);
	  private TriggerController triggers = new TriggerController(map, player);
	private float xCamera = player.getX(), yCamera = player.getY();
	private boolean onStair;

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		this.camera.place(container, g);
		// placement de camera (leçon 4)
		this.map.renderBackground();
		this.player.render(g);
		this.map.renderForeground();
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		this.container = container;
		this.map.init();
		this.player.init();	
		PlayerController controller = new PlayerController(this.player);
		container.getInput().addKeyListener(controller);
	}

	public void update(GameContainer container, int delta)
			throws SlickException {
		
		this.triggers.update();
		// [...] test de trigger (leçon 6)

		this.player.update(delta);
		// [...] mise à jour de la camera (leçon 4)
		
		this.camera.update(container);
	}


	
	
}
