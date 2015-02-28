package lesson1;

import org.newdawn.slick.SlickException;

public class TriggerController {
	  private Map map;
	  private Player player;

	  public TriggerController(Map map, Player player) {
	     this.map = map;
	     this.player = player;
	  }
	  
	  public void update() throws SlickException {
		  player.setOnStair(false);
		  for (int objectID = 0; objectID < map.getObjectCount(); objectID++) {
		    if (isInTrigger(objectID)) {
		      if ("teleport".equals(map.getObjectType(objectID))) {
		        teleport(objectID);
		      } else if ("stair".equals(map.getObjectType(objectID))) {
		        player.setOnStair(true);
		      } else if ("change-map".equals(map.getObjectType(objectID))) {
		        changeMap(objectID);
		      }
		    }
		  }
		}

		private boolean isInTrigger(int id) {
		  return player.getX() > map.getObjectX(id)
		    && player.getX() < map.getObjectX(id) + map.getObjectWidth(id)
		    && player.getY() > map.getObjectY(id)
		    && player.getY() < map.getObjectY(id) + map.getObjectHeight(id);
		}

		private void teleport(int objectID) {
		  player.setX(Float.parseFloat(map.getObjectProperty(objectID, "dest-x", Float.toString(player.getX()))));
		  player.setY(Float.parseFloat(map.getObjectProperty(objectID, "dest-y", Float.toString(player.getY()))));
		}

		private void changeMap(int objectID) throws SlickException {
		  teleport(objectID);
		  String newMap = map.getObjectProperty(objectID, "dest-map", "undefined");
		  if (!"undefined".equals(newMap)) {
		    map.changeMap("src/main/ressources/map/" + newMap);
		  }
		}

}
