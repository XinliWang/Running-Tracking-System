package demo.rest;

import demo.domain.Location;
import demo.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RunningBulkUploadRestController {
    private LocationService locationService;

    @Autowired
    public RunningBulkUploadRestController(LocationService locationService){
        this.locationService = locationService;
    }

    @RequestMapping(value="/running", method = RequestMethod.POST)
    public void upload(@RequestBody List<Location> locations) {
        this.locationService.saveRunningLocations(locations);
    }

    @RequestMapping(value = "/running/{movementType}", method = RequestMethod.GET)
    public Page<Location> findByMovementType(@PathVariable String movementType, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
        return this.locationService.findByRunnerMovementType(movementType, new PageRequest(page, size));
    }

    @RequestMapping(value = "/running/runningId/{runningId}", method = RequestMethod.GET)
    public Page<Location> findByRunningId(@PathVariable String runningId, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
        return this.locationService.findByRunningId(runningId, new PageRequest(page, size));
    }
}
