package info.stepanoff.trsis.samples.rest;


//import info.stepanoff.trsis.samples.db.dao.ConfirmationTokenRepository;
import info.stepanoff.trsis.samples.db.model.*;
import info.stepanoff.trsis.samples.service.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/public/rest/client")
public class UserDataRestService {

    private TransportOperator curTO = new TransportOperator();

    private Client curClient = new Client();

    @Autowired
    private ClientService clientService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransportOperatorService toService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TransportService transportService;

    //@Autowired
    //private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    /////// Client page /////////

    @RequestMapping(value = "/mypage/order", method = RequestMethod.GET)
    public ResponseEntity<Object> myPageOrder() {
        List<Order> orderList = orderService.listAllByClient(clientService.findByUsername(securityService.findLoggedTelephone()));
        Collections.sort(orderList);
        return ResponseEntity.ok(orderList);
    }

    @RequestMapping(value = "/mypage/info", method = RequestMethod.GET)
    public ResponseEntity<Object> myPageInfo() {
        Client client = clientService.findByUsername(securityService.findLoggedTelephone());
        return ResponseEntity.ok(client);
    }

    @RequestMapping(value = "/mypage/addGrade/{id}/{grade}", method = RequestMethod.GET)
    public ResponseEntity<Object> myPageInfo(@PathVariable("id") Integer id, @PathVariable("grade") Double grade) {
        Order order = orderService.getById(id);
        order.setGrade(grade);
        order = orderService.add(order);
        toService.setGrade(order.getTo().getTelephone());
        toService.setRecomendation(order.getTo().getTelephone());
        return ResponseEntity.ok(order);
    }

    @RequestMapping(value = "/mypage/editInfo/", method = RequestMethod.POST)
    public ResponseEntity<Object> clientPageEditInfo(HttpEntity<String> oEntity) {
        {
            Client oNewClient = null;
            try
            {
                String sJson = oEntity.getBody();
                oNewClient = Client.parseFromJson(new JSONObject(sJson));
                curClient = clientService.findByUsername(securityService.findLoggedTelephone());
                curClient.setName(oNewClient.getName());
                curClient.setSname(oNewClient.getSname());
                curClient.setPname(oNewClient.getPname());
                curClient.setEmail(oNewClient.getEmail());
                curClient.setImage(oNewClient.getImage());
                clientService.save(curClient);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.unprocessableEntity().build();
            }
        }
    }


    //////// Transport operator page //////////

    @RequestMapping(value = "/topage/order", method = RequestMethod.GET)
    public ResponseEntity<Object> toPageOrder() {
        List<Order> orderList = orderService.listAllByTo(toService.getByTelephone(securityService.findLoggedTelephone()));
        Collections.sort(orderList);
        return ResponseEntity.ok(orderList);
    }

    @RequestMapping(value = "/topage/cancelOrder/{id}", method = RequestMethod.POST)
    public ResponseEntity<Object> deleteOrder(@PathVariable("id") Integer orderId) {
        orderService.getById(orderId).setStatus("Отменен");
        orderService.add(orderService.getById(orderId));
        List<Order> orderList = orderService.listAllByTo(toService.getByTelephone(securityService.findLoggedTelephone()));
        Collections.sort(orderList);
        return ResponseEntity.ok(orderList);
    }

    @RequestMapping(value = "/topage/confirmOrder/{id}", method = RequestMethod.POST)
    public ResponseEntity<Object> submitOrder(@PathVariable("id") Integer orderId) {
        orderService.getById(orderId).setStatus("Подтвержден");
        orderService.add(orderService.getById(orderId));
        List<Order> orderList = orderService.listAllByTo(toService.getByTelephone(securityService.findLoggedTelephone()));
        Collections.sort(orderList);
        return ResponseEntity.ok(orderList);
    }

    @RequestMapping(value = "/topage/info", method = RequestMethod.GET)
    public ResponseEntity<Object> toPageInfo() {
        TransportOperator to = toService.getByTelephone(securityService.findLoggedTelephone());
        return ResponseEntity.ok(to);
    }

    @RequestMapping(value = "/topage/editInfo/", method = RequestMethod.POST)
    public ResponseEntity<Object> toPageEditInfo(HttpEntity<String> oEntity) {
        {
            TransportOperator oNewTO = null;
            try
            {
                String sJson = oEntity.getBody();
                oNewTO = TransportOperator.parseFromJson(new JSONObject(sJson));
                curTO = toService.getByTelephone(securityService.findLoggedTelephone());
                curTO.setName(oNewTO.getName());
                curTO.setSname(oNewTO.getSname());
                curTO.setPname(oNewTO.getPname());
                curTO.setEmail(oNewTO.getEmail());
                curTO.setDescription(oNewTO.getDescription());
                toService.save(curTO);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.unprocessableEntity().build();
            }
        }
    }

    @RequestMapping(value = "/topage/editTariff/{children}/{abroad}", method = RequestMethod.GET)
    public ResponseEntity<Object> submitOrder(@PathVariable("children") String children, @PathVariable("abroad") String abroad) {
        curTO = toService.getByTelephone(securityService.findLoggedTelephone());
        curTO.setAbroad(abroad);
        curTO.setChildren(children);
        toService.save(curTO);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/topage/transport", method = RequestMethod.GET)
    public ResponseEntity<Object> toPageTransport() {
        Iterable<Transport> transport = transportService.listAllByTo(toService.getByTelephone(securityService.findLoggedTelephone()).getId());
        return ResponseEntity.ok(transport);
    }

    @RequestMapping(value = "/topage/transport/edit/{transport}/{model}/{class}/{description}/{year}", method = RequestMethod.GET)
    public void editToTransport(@PathVariable("transport") Integer transportId, @PathVariable("model") String model,
                                @PathVariable("class") String trClass, @PathVariable("description") String desc,
                                @PathVariable("year") Integer year) {
        Transport transport = transportService.getById(transportId);
        transport.setModel(model);
        transport.setTransportClass(trClass);
        transport.setDescription(desc);
        transport.setYear(year);
    }

    //add transport
    @RequestMapping(value = "/topage/addTransport/", method = RequestMethod.POST)
    public ResponseEntity<Object> addTransport(HttpEntity<String> oEntity) {
        Transport oNewTransport = null;
        try
        {
            String sJson = oEntity.getBody();
            oNewTransport = Transport.parseFromJson(new JSONObject(sJson));
            oNewTransport.setToTransport(toService.getByTelephone(securityService.findLoggedTelephone()));
            transportService.add(oNewTransport);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().build();
        }
    }


    ///////// chosen transport operator ////////////////

    // information about to
    @RequestMapping(value = "/topage/info/{to}", method = RequestMethod.GET)
    public ResponseEntity<Object> getToPage(@PathVariable("to") String telephone) {
        TransportOperator to = toService.getByTelephone(telephone);
        return ResponseEntity.ok(to);
    }

    // information about to`s transports
    @RequestMapping(value = "/topage/transport/{to}", method = RequestMethod.GET)
    public ResponseEntity<Object> getToTransport(@PathVariable("to") Integer toId) {
        Iterable<Transport> transport = transportService.listAllByTo(toId);
        return ResponseEntity.ok(transport);
    }

    //information about to`s order
    @RequestMapping(value = "/topage/order/{to}/", method = RequestMethod.GET)
    public ResponseEntity<Object> makeOrder(@PathVariable("to") Integer toId) {
        Iterable<Transport> transport = transportService.listAllByTo(toId);
        return ResponseEntity.ok(transport);
    }

    //edit order
    @RequestMapping(value = "/topage/order/{to}/{edit}/", method = RequestMethod.GET)
    public ResponseEntity<Object> editOrder(@PathVariable("to") Integer toId) {
        Iterable<Transport> transport = transportService.listAllByTo(toId);
        return ResponseEntity.ok(transport);
    }

}
