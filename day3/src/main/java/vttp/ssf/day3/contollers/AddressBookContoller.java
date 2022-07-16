package vttp.ssf.day3.contollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp.ssf.day3.models.Contact;
import vttp.ssf.day3.utils.Contacts;

@Controller
@RequestMapping(path="/address-book")
public class AddressBookContoller {

    @Autowired
    Contacts ctcs;

    @Autowired
    ApplicationArguments appArgs;

    @Value("${test.data.dir}")
    private String dataDir;
    
    @GetMapping
    public String showAddressBookForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "address-book";
    }

    @GetMapping("{contactId}")
    public String getContactById(Model model, @PathVariable String contactId) {
        System.out.println("Requested for contact id " + contactId);
        ctcs.getContactById(contactId, model, appArgs, dataDir);
        return "contact-info";
    }

    @PostMapping
    public String saveContact(@ModelAttribute Contact contact, Model model) {
        ctcs.saveContact(contact, model, appArgs, dataDir);
        return "contact-info";
    }
    
}
