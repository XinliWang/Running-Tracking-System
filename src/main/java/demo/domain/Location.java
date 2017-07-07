package demo.domain;




import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "LOCATION")
@Data
public class Location {
    enum GpsStatus {
        EXCELENT, OK, UNRELIABLE, BAD, NOFIX, UNKNOWN;
    }

    public enum RunnerMovementType {
        STOPPED, IN_MOTION;

        public boolean isMoving() {
            return this != STOPPED;
        }
    }

    @Id
    @GeneratedValue
    private long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="fmi", column = @Column(name = "medical_fmi")),
            @AttributeOverride(name="bfr", column = @Column(name = "medical_bfr"))
    })
    //fmi is called medical_fmi and bfr is called medical_bfr in DATABASE
    private MedicalInfo medicalInfo;

    @Embedded
    @AttributeOverride(name = "bandMake", column = @Column(name = "unit_band_make"))
    //bandMake is called unit_band_make in DATABASE
    private UnitInfo unitInfo;

    private double latitude;
    private double longitude;
    private String heading;
    private double gpsSpeed;
    private GpsStatus gpsStatus;
    private double odometer;
    private double totalRunningTime;
    private double totalIdleTime;
    private double totalCalorieBurnt;
    private String address;
    private Date timestamp = new Date();
    private String gearProvider;
    private RunnerMovementType runnerMovementType = RunnerMovementType.STOPPED;
    private String serviceType;

    private int heartRate = 60;

    public Location() {
        this.unitInfo = null;
    }

    public Location(UnitInfo unitInfo) {
        this.unitInfo = unitInfo;
    }

    @JsonCreator
    //from a Json object to constructor
    public Location(@JsonProperty("runningId") String runningId) {
        this.unitInfo = new UnitInfo(runningId);
    }

    public String getRunningId() {
        return this.unitInfo == null ? null : this.unitInfo.getRunningId();
    }

}
