package ghostnet.gncasestudy;

import java.util.ArrayList;

import jakarta.enterprise.context.*;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("mySalvagec")
@RequestScoped
public class MySalvageController {

    @Inject
    private SessionData data;

    private ArrayList<GhostNet> myNets;
    private ArrayList<GhostNet> mySalvagedNets;
    private boolean init;

    public MySalvageController() {
        myNets = new ArrayList<>();
        init = true;
    }

    public ArrayList<GhostNet> getMyNets() {
        if (init) {
            load();
            init = false;
        }
        return myNets;
    }

    public void setMyNets(ArrayList<GhostNet> myNets) {
        this.myNets = myNets;
    }

    public ArrayList<GhostNet> getMySalvagedNets() {
        return mySalvagedNets;
    }

    public void setMySalvagedNets(ArrayList<GhostNet> mySalvagedNets) {
        this.mySalvagedNets = mySalvagedNets;
    }

    public void setAsSalvaged(GhostNet inp) {
        inp.setStatus(Status.SALVAGED);
        data.saveNet(inp);
        load();
    }

    public void load() {
        var totalNets = data.getMyNets();
        this.myNets = new ArrayList<GhostNet>();
        this.mySalvagedNets = new ArrayList<GhostNet>();
        for (GhostNet net : totalNets) {
            if (net.getStatus() == Status.UPCOMING_SALVAGE) {
                myNets.add(net);
            }
            else {
                mySalvagedNets.add(net);
            }
        }
    }
}
