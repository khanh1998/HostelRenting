package capstone.tdhostel.controller;

import capstone.tdhostel.dto.RenterDTO;
import capstone.tdhostel.dto.RenterNoIdDTO;
import capstone.tdhostel.exception.ResourceNotFoundException;
import capstone.tdhostel.model.HostelGroup;
import capstone.tdhostel.model.Renter;
import capstone.tdhostel.service.RenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RenterController {
    @Autowired
    private RenterService renterService;

    @GetMapping("/renters")
    public List<Renter> getAllRenters() {
        return renterService.findAllRenter();
    }

    @GetMapping("/renters/{renterId}")
    public Renter getRenterById(@PathVariable int renterId) {
        return renterService.findRenterById(renterId).get();
    }

    @PostMapping("/renters")
    public ResponseEntity<?> createRenter(@RequestBody RenterDTO renterDTO) {
        Renter renter = new Renter();
        renter.setRenterName(renterDTO.getRenterName());
        renter.setPassword(renterDTO.getPassword());
        renter.setAvatar(renterDTO.getAvatar());
        renter.setEmail(renterDTO.getEmail());
        renter.setPhoneNumber(renterDTO.getPhoneNumber());
        renterService.saveRenter(renter);
        return  ResponseEntity.ok(renterDTO);
    }

    @PutMapping("/renters/{renterId}")
    public RenterNoIdDTO updateRenter(@PathVariable int renterId, @RequestBody RenterNoIdDTO renterRequest) {
        return renterService.findRenterById(renterId).map(renter -> {
                    renter.setRenterName(renterRequest.getRenterName());
                    renter.setPhoneNumber(renterRequest.getPhoneNumber());
                    renter.setEmail(renterRequest.getEmail());
                    renter.setAvatar(renterRequest.getAvatar());
                    renter.setPassword(renterRequest.getPassword());
                    renterService.saveRenter(renter);
                    return renterRequest;
                }).orElseThrow(() -> new ResourceNotFoundException("Renter not found with id " + renterId));
    }

    @DeleteMapping("/renters/{renterId}")
    public ResponseEntity<?> removeRenter(@PathVariable int renterId) {
        return renterService.findRenterById(renterId).map(renter -> {
                    renterService.removeRenter(renter);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Renter not found with id " + renterId));
    }
}
