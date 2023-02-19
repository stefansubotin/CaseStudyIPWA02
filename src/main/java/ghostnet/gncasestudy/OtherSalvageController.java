package ghostnet.gncasestudy;

import java.util.ArrayList;

import jakarta.enterprise.context.*;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("otherSalvagec")
@RequestScoped
public class OtherSalvageController {

    @Inject
    private SessionData data;

    private ArrayList<GhostNet> otherNets;
    private boolean init;

    public OtherSalvageController() {
        otherNets = new ArrayList<GhostNet>();
        init = true;
    }

    public ArrayList<GhostNet> getOtherNets() {
        if (init) {
            load();
            init = false;
        }
        return otherNets;
    }

    public void setOtherNets(ArrayList<GhostNet> otherNets) {
        this.otherNets = otherNets;
    }

    public void load() {
        otherNets = data.getOtherClaimedNets();
    }
}
