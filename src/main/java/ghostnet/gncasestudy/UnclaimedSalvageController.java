package ghostnet.gncasestudy;

import java.util.ArrayList;

import jakarta.enterprise.context.*;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("unclaimedSalvagec")
@RequestScoped
public class UnclaimedSalvageController {

    @Inject
    private SessionData data;

    private ArrayList<GhostNet> unclaimedNets;
    private ArrayList<GhostNet> missingNets;
    private boolean init;

    public UnclaimedSalvageController() {
        unclaimedNets = new ArrayList<GhostNet>();
        missingNets = new ArrayList<GhostNet>();
        init = true;
    }

    public ArrayList<GhostNet> getUnclaimedNets() {
        if (init) {
            load();
            init = false;
        }
        return unclaimedNets;
    }

    public void setUnclaimedNets(ArrayList<GhostNet> unclaimedNets) {
        this.unclaimedNets = unclaimedNets;
    }

    public ArrayList<GhostNet> getMissingNets() {
        if (init) {
            load();
            init = false;
        }
        return missingNets;
    }

    public void setMissingNets(ArrayList<GhostNet> missingNets) {
        this.missingNets = missingNets;
    }

    public void claim(GhostNet inp) {
        inp.setStatus(Status.UPCOMING_SALVAGE);
        inp.setSalvager(data.getLoggedInUser());
        data.saveNet(inp);
        load();
    }

    public void found(GhostNet inp) {
        inp.setStatus(Status.FOUND);
        inp.setSalvager(data.getLoggedInUser());
        data.saveNet(inp);
        load();
    }

    public void load() {
        this.unclaimedNets = data.getUnclaimedNets();
        this.missingNets = data.getMissingNets();
    }
}
