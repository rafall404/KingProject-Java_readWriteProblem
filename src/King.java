import Logger.Logger;
import ValuableFactory.Valuable;

import java.util.ArrayList;
import java.util.Random;

public class King implements Runnable{
    private Logger logger;
    private TreasureRoom treasureRoom;
    Random random = new Random();

    public King(Logger logger, TreasureRoom treasureRoom){
        this.logger=logger;
        this.treasureRoom=treasureRoom;
    }

    @Override
    public void run() {
        while(true){
            ArrayList<Valuable> kingsPocket = new ArrayList<Valuable>();

            int costOfParty = random.nextInt(150-50)+50;
            logger.log("King is making new party");

            int pocketInMoney = 0;
            treasureRoom.acquireWrite();

            while( pocketInMoney < costOfParty) {

                Valuable valuable = treasureRoom.removeValuable();
                if(valuable!=null) {
                    logger.log("The king removed " +valuable.getName());
                    kingsPocket.add(valuable);
                    pocketInMoney+= valuable.getValue();

                }
                else {
                    for (int y = 0; y < kingsPocket.size(); y++) {
                        treasureRoom.addValuable(kingsPocket.get(y));
                    }
                    logger.log("the king cancels the party");
                    break;
                }
            }

            try
            {
                Thread.sleep(1000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            treasureRoom.releaseWrite();
        }
    }
}
