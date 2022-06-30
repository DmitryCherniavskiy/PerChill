package info.stepanoff.trsis.samples.rest;

import info.stepanoff.trsis.samples.db.model.Message;
import info.stepanoff.trsis.samples.db.model.Order;
import info.stepanoff.trsis.samples.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Controller
public class RedirectRestController {

    @Autowired
    private TransportOperatorService toService;

    @Autowired
    private TransportService transportService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/redirect/{toId}", method = RequestMethod.GET)
    public ModelAndView getTo(@PathVariable("toId") Integer toId) {
        ModelAndView mav = new ModelAndView("toPage");
        mav.addObject("info", toService.getById(toId));
        mav.addObject("transports", transportService.listAllByTo(toId));
        return mav;
    }

    @GetMapping("/addOrder/{toId}/{transportId}")
    public String addOrderForm(@PathVariable("toId") Integer toId, @PathVariable("transportId") Integer transport, Model model) {
        Order newOrder = new Order();
        model.addAttribute("order", newOrder);
        model.addAttribute("to",toId);
        model.addAttribute("transport",transport);
        return "addOrder";
    }

    @PostMapping("/addOrder/{toTelephone}/{transportId}")
    public String addOrderSubmit(@PathVariable("toTelephone") Integer toId, @PathVariable("transportId") Integer transport,@ModelAttribute Order order) {
        order.setClient(clientService.findByUsername(securityService.findLoggedTelephone()));
        order.setTo(toService.getById(toId));
        order.setTransport(transport);
        order.setStatus("Ожидает подтверждения");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        order.setDate(formatter.format(Calendar.getInstance().getTime()));
        orderService.add(order);
        return "redirect:/";
    }

    @GetMapping("/chat/{toTelephone}/{clientTelephone}")
    public String getMessage(@PathVariable("toTelephone") String toTelephone, @PathVariable("clientTelephone") String clientTelephone, Model model) {
        List<Message> mesList = messageService.listAll(toService.getByTelephone(toTelephone),clientService.findByUsername(clientTelephone));
        Collections.sort(mesList);
        Message mes = new Message("",toService.getByTelephone(toTelephone),clientService.findByUsername(clientTelephone),"");
        model.addAttribute("mes", mes);
        model.addAttribute("messages", mesList);
        model.addAttribute("to",toTelephone);
        model.addAttribute("client",clientTelephone);
        return "chat";
    }

    @PostMapping("/chat/clientSend/{toTelephone}/{clientTelephone}")
    public String sendFromClient( , @ModelAttribute Message message) {
        message.setFromMessage("client");
        message.setClientMessage(clientService.findByUsername(model.getAttribute("client").toString()));
        message.setToMessage(toService.getByTelephone(model.getAttribute("to").toString()));
        messageService.add(message);
        return "redirect:/chat/"+message.getToMessage().getTelephone()+"/"+message.getClientMessage().getTelephone();
    }

    @PostMapping("chat/toSend/{toTelephone}/{clientTelephone}")
    public String sendFromTo(Model model, @ModelAttribute Message message) {
        message.setFromMessage("to");
        message.setClientMessage(clientService.findByUsername(model.getAttribute("client").toString()));
        message.setToMessage(toService.getByTelephone(model.getAttribute("to").toString()));
        messageService.add(message);
        return "redirect:/chat/"+message.getToMessage().getTelephone()+"/"+message.getClientMessage().getTelephone();
    }
}
