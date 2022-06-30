package info.stepanoff.trsis.samples.rest;


//import info.stepanoff.trsis.samples.db.dao.ConfirmationTokenRepository;
//import info.stepanoff.trsis.samples.db.model.ConfirmationToken;
import info.stepanoff.trsis.samples.db.model.Client;
import info.stepanoff.trsis.samples.db.model.Order;
import info.stepanoff.trsis.samples.db.model.TransportOperator;
import info.stepanoff.trsis.samples.db.model.User;
import info.stepanoff.trsis.samples.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationRestController {

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

    /*@RequestMapping(value = "/registrationto/{telephone}/{name}/{sname}/{pname}/{email}/{password}", method = RequestMethod.POST)
    public ResponseEntity<Object> registrationto(@PathVariable("telephone") String telephone, @PathVariable("name") String name,
                                                 @PathVariable("sname") String sname, @PathVariable("pname") String pname,
                                                 @PathVariable("email") String email, @PathVariable("password") String password) {

        try {
            if (toService.findByEmail(email) != null) {
                return new ResponseEntity(new Error("Почта уже зарегистрирована"), HttpStatus.BAD_REQUEST);
            }
            if (toService.getByTelephone(telephone) != null)
                return new ResponseEntity(new Error("Телефон уже зарегистрирован"), HttpStatus.BAD_REQUEST);
            User user = new User(telephone, password);
            userService.save(user, 2);

        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("chand312902@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
                +"http://localhost:8082/confirm-account/"+telephone+"/"+name+"/"+sname+"/"+pname+"/"+email+"/?token="+confirmationToken.getConfirmationToken());

        emailSenderService.sendEmail(mailMessage);


            TransportOperator to = new TransportOperator(name, sname, email, pname, telephone, 1);
            toService.save(to);
            securityService.autoLogin(user.getTelephone(), user.getPasshash());
        }catch (Exception ex){
            return new ResponseEntity(new Error("Встречены недопустимые символы в имени"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(securityService.findLoggedTelephone());
    }

    @RequestMapping(value="/confirm-account/{telephone}/{name}/{sname}/{pname}/{email}", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Object> confirmUserAccount(@RequestParam("token")String confirmationToken,
                                           @PathVariable("telephone") String telephone, @PathVariable("name") String name,
                                           @PathVariable("sname") String sname, @PathVariable("pname") String pname,
                                           @PathVariable("email") String email)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            User user = userService.findByUsername(telephone);
            user.setEnabled(true);
            toService.save(new TransportOperator(name,sname,pname,email,telephone,1));
        }
        else
        {
            return new ResponseEntity(new Error("Телефон уже зарегестрирован"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(toService.getByTelephone(telephone));
    }
}*/

    @GetMapping("/registrationto")
    public String regToForm(Model model) {
        TransportOperator newTo = new TransportOperator();
        User newUser = new User();
        model.addAttribute("to", newTo);
        model.addAttribute("user", newUser);
        return "registrationto";
    }


    @PostMapping("/registrationto")
    public String regToSubmit(@ModelAttribute TransportOperator to, @ModelAttribute User user, Model model) {
        if (toService.findByEmail(to.getEmail()) != null)
        {
            TransportOperator newTo = new TransportOperator();
            User newUser = new User();
            model.addAttribute("to", newTo);
            model.addAttribute("user", newUser);
            model.addAttribute("error", "Пользователь с такой почтой уже существует!");
            return "registrationto";
        }
        if (toService.getByTelephone(to.getTelephone()) != null) {
            TransportOperator newTo = new TransportOperator();
            User newUser = new User();
            model.addAttribute("to", newTo);
            model.addAttribute("user", newUser);
            model.addAttribute("error", "Пользователь с таким телефоном уже существует!");
            return "registrationto";
        }
        if (user.getPasshash() != user.getConfirmPasshash() || user.getPasshash().length()<8 || user.getPasshash().length()>20){
            TransportOperator newTo = new TransportOperator();
            User newUser = new User();
            model.addAttribute("to", newTo);
            model.addAttribute("user", newUser);
            model.addAttribute("error", "Пароль подтверждения должен совпадать с введенным паролем, а также быть больше 8 и меньше 20 символов");
            return "registrationto";
        }
        to.setFk_city_id(1);
        user.setTelephone(to.getTelephone());
        userService.save(user, 2);
        to.setId(userService.findByUsername(user.getTelephone()).getId());
        toService.save(to);
        securityService.autoLogin(user.getTelephone(), user.getConfirmPasshash());
        return "redirect:/";
    }

    ///////////// registration Client /////////////////////

    @GetMapping("/registration")
    public String regClientForm(Model model) {
        Client newClient = new Client();
        User newUser = new User();
        model.addAttribute("client", newClient);
        model.addAttribute("user", newUser);
        return "registration";
    }



    @PostMapping("/registration")
    public String regClientSubmit(@ModelAttribute Client client, @ModelAttribute User user, Model model) {
        if (toService.findByEmail(client.getEmail()) != null)
        {
            Client newClient = new Client();
            User newUser = new User();
            model.addAttribute("to", newClient);
            model.addAttribute("user", newUser);
            model.addAttribute("error", "Пользователь с такой почтой уже существует!");
            return "registration";
        }
        if (toService.getByTelephone(client.getTelephone()) != null) {
            Client newClient = new Client();
            User newUser = new User();
            model.addAttribute("to", newClient);
            model.addAttribute("user", newUser);
            model.addAttribute("error", "Пользователь с таким телефоном уже существует!");
            return "registration";
        }
        if (!user.getPasshash().equals(user.getConfirmPasshash()) || user.getPasshash().length()<8 || user.getPasshash().length()>20){
            Client newClient = new Client();
            User newUser = new User();
            model.addAttribute("to", newClient);
            model.addAttribute("user", newUser);
            model.addAttribute("error", "Пароль подтверждения должен совпадать с введенным паролем, а также быть больше 8 и меньше 20 символов");
            return "registration";
        }
        client.setFk_city_id(1);
        user.setTelephone(client.getTelephone());
        userService.save(user, 1);
        client.setId(userService.findByUsername(user.getTelephone()).getId());
        clientService.save(client);
        securityService.autoLogin(user.getTelephone(), user.getConfirmPasshash());
        return "redirect:/";
    }
}
