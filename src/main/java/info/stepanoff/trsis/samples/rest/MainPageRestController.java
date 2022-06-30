package info.stepanoff.trsis.samples.rest;

import info.stepanoff.trsis.samples.db.model.Client;
import info.stepanoff.trsis.samples.db.model.Order;
import info.stepanoff.trsis.samples.db.model.TransportOperator;
import info.stepanoff.trsis.samples.service.OrderService;
import info.stepanoff.trsis.samples.service.SecurityService;
import info.stepanoff.trsis.samples.service.TransportOperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/public/rest/rec")
public class MainPageRestController {

    @Autowired
    private TransportOperatorService transportOperatorService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/into/{page}", method = RequestMethod.GET)
    public ResponseEntity<Object> browse(@PathVariable("page") Integer page) {
        return ResponseEntity.ok(transportOperatorService.listAll(page));
    }

    @RequestMapping(value = "/into/abroad", method = RequestMethod.GET)
    public ResponseEntity<Object> browseAbroad() {
        return ResponseEntity.ok(transportOperatorService.listAbroad());
    }

    @RequestMapping(value = "/into/children", method = RequestMethod.GET)
    public ResponseEntity<Object> browseChildren() {
        return ResponseEntity.ok(transportOperatorService.listChildren());
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        transportOperatorService.delete(id);
    }

    @RequestMapping(value = "/count", method = RequestMethod.POST)
    public ResponseEntity<Object> count() {
        return ResponseEntity.ok(transportOperatorService.count());
    }

}
