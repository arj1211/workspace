package frumsnl.gravityplatformer.game.level;

import java.util.ArrayList;

import frumsnl.gravityplatformer.game.Game;
import frumsnl.gravityplatformer.game.character.Character;
import frumsnl.gravityplatformer.game.character.Player;
import frumsnl.gravityplatformer.game.level.tile.AirTile;
import frumsnl.gravityplatformer.game.level.tile.SolidTile;
import frumsnl.gravityplatformer.game.level.tile.Tile;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Level {
    
    private TiledMap map;
    
    //a list of all characters present somewhere on this map
    private ArrayList<Character> characters;
    
    private Tile[][] tiles;
    
    private Player player;
    
    public Level(String level, Player player) throws SlickException{
        map = new TiledMap("lib/res/FrumsNL/" + level + ".tmx","lib/res/FrumsNL");
        characters = new ArrayList<Character>();
        this.player=player;
        addCharacter(player);
        loadTileMap();
    }
    
    private void loadTileMap(){
        //create an array to hold all the tiles in the map
        tiles = new Tile[map.getWidth()][map.getHeight()];
        
        int layerIndex = map.getLayerIndex("CollisionLayer");
        
        if(layerIndex == -1){
            //TODO we can clean this up later with an exception if we want, but because we make the maps ourselfs this will suffice for now
            System.err.println("Map does not have the layer \"CollisionLayer\"");
            System.exit(0);
        }
        
        //loop through the whole map
        for(int x = 0; x < map.getWidth(); x++){
            for(int y = 0; y < map.getHeight(); y++){
                
                //get the tile
                int tileID = map.getTileId(x, y, layerIndex);
                
                Tile tile = null;
                
                //and check what kind of tile it is (
                switch(map.getTileProperty(tileID, "tileType", "solid")){
                    case "air":
                        tile = new AirTile(x,y);
                        break;
                    default:
                        tile = new SolidTile(x,y);
                        break;
                }
                tiles[x][y] = tile;
            }
        }
    }
    
    public void addCharacter(Character c){
        characters.add(c);
    }
    
    public ArrayList<Character> getCharacters(){
        return characters;
    }
    
    public Tile[][] getTiles(){
        return tiles;
    }
    
    public void render(){
    	 
        int offset_x = getXOffset();
        int offset_y = getYOffset();
 
        //render the map first
        map.render(-(offset_x%32), -(offset_y%32), offset_x/32, offset_y/32, 32, 20);
 
        //and then render the characters on top of the map
        for(Character c : characters){
            c.render(offset_x,offset_y);
        }
 
    }
    
    
    public int getXOffset(){
        int offset_x = 0;
 
        //the first thing we are going to need is the half-width of the screen, to calculate if the player is in the middle of our screen
        int half_width = (int) (Game.WINDOW_WIDTH/Game.SCALE/2);
 
        //next up is the maximum offset, this is the most right side of the map, minus half of the screen offcourse
        int maxX = (int) (map.getWidth()*32)-half_width;
 
        //now we have 3 cases here
        if(player.getX() < half_width){
            //the player is between the most left side of the map, which is zero and half a screen size which is 0+half_screen
            offset_x = 0;
        }else if(player.getX() > maxX){
            //the player is between the maximum point of scrolling and the maximum width of the map
            //the reason why we substract half the screen again is because we need to set our offset to the topleft position of our screen
            offset_x = maxX-half_width;
        }else{
            //the player is in between the 2 spots, so we set the offset to the player, minus the half-width of the screen
            offset_x = (int) (player.getX()-half_width);
        }
 
        return offset_x;
    }
    
    public int getYOffset(){
        int offset_y = 0;
 
        int half_heigth = (int) (Game.WINDOW_HEIGTH/Game.SCALE/2);
 
        int maxY = (int) (map.getHeight()*32)-half_heigth;
 
        if(player.getY() < half_heigth){
            offset_y = 0;
        }else if(player.getY() > maxY){
            offset_y = maxY-half_heigth;
        }else{
            offset_y = (int) (player.getY()-half_heigth);
        }
 
        return offset_y;
    }
    
    
    
    

}