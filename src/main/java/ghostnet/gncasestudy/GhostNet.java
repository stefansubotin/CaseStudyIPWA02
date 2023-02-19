package ghostnet.gncasestudy;

import jakarta.enterprise.context.*;
import java.io.Serializable;

/**
 * Klasse, die ein GhostNetobjekt definiert
 *
 * @author ssu
 *
 */
@SessionScoped
public class GhostNet implements Serializable {

    private int id;
    private String name = "unknown";
    private Status status = Status.REPORTED;
    private double latitude;
    private double longitude;
    private int estimatedSize;
    private String reportedBy = "anonym";
    private String reportersPhone = "";
    private Salvager salvager;

    public GhostNet() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) return;
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        if (status == null) return;
        this.status = status;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        if (reportedBy == null) reportedBy = "anonym";
        this.reportedBy = reportedBy;
    }

    public String getReportersPhone() {
        return reportersPhone;
    }

    public void setReportersPhone(String reportersPhone) {
        this.reportersPhone = reportersPhone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Salvager getSalvager() {
        return salvager;
    }

    public void setSalvager(Salvager salvager) {
        this.salvager = salvager;
    }

    public int getEstimatedSize() {
        return estimatedSize;
    }

    public void setEstimatedSize(int estimatedSize) {
        this.estimatedSize = estimatedSize;
    }
}
