package agh.ics.oop.my_package;

import project.backend.model.models.Vector2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

public class RandomPositionGenerator implements Iterable<Vector2d> {
    /** IMPORTANT NOTE!
     * works only for topLeft = (0,0) !!!
     * HUGE memory consumption! -> O ( w*h + w*h/CHUNK_SIZE^2 )
     * block == field (! block because it's harder to get confused)
     * top left point of ( example {0,1}x{0,1} -> (0,0) ) is a indentificator
     *   for blocks (fields) , chunks
     * */

    /** DZIALANIE
     * dziele obszar mapy na chunki po 16 pol,
     * przypisuje pola kazdemu chunkowi (dalej nazywam blokami aby nie mylic z field)
     * losuje tylko chunki te posrod dostepnych a nastepnie we wnatrz chunku blok
     *
     * */

    private static final int CHUNK_SIZE = 5;
    private int nToGenerate;

    RandomInt rr = new RandomInt();

    private final Vector2d topLeft; // (0,0) but might be something else in the future...
    private final Vector2d bottomRight;

    private final List<Vector2d> availableChunks = new ArrayList<Vector2d>();
    private final Map<Vector2d , List<Vector2d>> chunksToblocksDict = new HashMap<Vector2d , List<Vector2d>>();




    public RandomPositionGenerator(Vector2d bottomRight , int nToGenerate) {
        if (nToGenerate<0){
            throw new IllegalArgumentException("negative n ints to Generate passed into RandomPositionGenerator");
        }
        this.topLeft = new Vector2d(0,0);
        this.bottomRight = bottomRight;
        this.nToGenerate = nToGenerate;
        setChunks();
        setBlocks();
    }

    private void setChunks(){ // written for topLeft = (0,0)
        //used only in constructor as helper
        // complexity O( w*h/CHUNK_SIZE^2 )
        for (int y=0; y<bottomRight.getY() ; y += CHUNK_SIZE){
            for (int x=0; x<bottomRight.getX() ; x += CHUNK_SIZE){
                Vector2d chunk = new Vector2d(x,y);
//                Vector2d chunk = new Vector2d( x/CHUNK_SIZE , y/CHUNK_SIZE );
                availableChunks.add(chunk);
                chunksToblocksDict.put(chunk , new ArrayList<>());
            }
        }
    }

    private void setBlocks(){// written for topLeft = (0,0)
        //used only in constructor as helper
        // complexity O( w*h )
        //iterate thought all point -> map them to chunks
        Vector2d chunkID;
        List<Vector2d> blocksList;
        for (int y=0; y<bottomRight.getY() ; y++){
            for (int x=0; x<bottomRight.getX() ; x++){
                chunkID = new Vector2d( (x/CHUNK_SIZE)*CHUNK_SIZE , (y/CHUNK_SIZE)*CHUNK_SIZE );
                blocksList = chunksToblocksDict.get(chunkID);

                blocksList.add( new Vector2d(x,y) );
            }
        }
    }

    public Vector2d pickRandomBlock(){
        if (availableChunks.size() == 0){
            throw new ArrayIndexOutOfBoundsException("no chunks and blocks left -> nothing to pick in pickRandomBlock");
        }
        int chunkIDX = rr.randInt(0,availableChunks.size()-1);
        Vector2d chunk = availableChunks.get(chunkIDX);

        List<Vector2d> chunkContainsBlocksList = chunksToblocksDict.get(chunk);

        int blockIDX = rr.randInt(0,chunkContainsBlocksList.size() - 1);
        Vector2d block = chunkContainsBlocksList.get(blockIDX);

        //cleaning
        chunkContainsBlocksList.remove(blockIDX);
        if (chunkContainsBlocksList.size() == 0){ //all blocks from chunk used
            availableChunks.remove(chunkIDX); // this chunk is no needed anymore
        }

        return block;
    }

    public void freeMyMemoryNOW(){
        availableChunks.clear();
        chunksToblocksDict.clear();
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return new RandomPositionIterator();
    }

    private class RandomPositionIterator implements Iterator<Vector2d> {

        @Override
        public boolean hasNext() {
            return nToGenerate > 0;
        }

        @Override
        public Vector2d next() {
            if (!hasNext()) {
                freeMyMemoryNOW();
//                throw new IllegalStateException("No more positions to generate.");
            }
            nToGenerate--;
            return pickRandomBlock();
        }
    }


}
