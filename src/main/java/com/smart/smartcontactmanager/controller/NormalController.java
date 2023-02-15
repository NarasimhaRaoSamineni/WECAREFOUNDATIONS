package com.smart.smartcontactmanager.controller;


import com.smart.smartcontactmanager.dao.ContactusRepository;
import com.smart.smartcontactmanager.dao.DonateRepository;
import com.smart.smartcontactmanager.dao.UserRepository;
import com.smart.smartcontactmanager.dao.VolunteerRepository;
import com.smart.smartcontactmanager.entities.Contactus;
import com.smart.smartcontactmanager.entities.Donate;
import com.smart.smartcontactmanager.entities.User;
import com.smart.smartcontactmanager.entities.Volunteer;
import com.smart.smartcontactmanager.helper.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class NormalController {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DonateRepository donateRepository;

    @Autowired
    private ContactusRepository contactusRepository;

    @ModelAttribute
    public void addCommonData(Model model,Principal principal)
    {
        String userName=principal.getName();
        System.out.println("USERNAME "+userName);

        User user=userRepository.getUserByUserName(userName);

        System.out.println("USER "+user);

        model.addAttribute("user",user);
    }

    @GetMapping("/home")
    public String dashboard(Model model, Principal principal)
    {
        model.addAttribute("title","User dashboard");
        return "normal/user_dashboard";
    }

    @GetMapping("/donate")
    public String openAddContactForm(Model model)
    {
        model.addAttribute("title","Donate");
        model.addAttribute("donate",new Donate());
        return "normal/donate";
    }

    @RequestMapping("/volunteer")
    public String signup(Model model)
    {
        model.addAttribute("title","Become-Volunteer");
        model.addAttribute("volunteer",new Volunteer());
        return "normal/volunteer";
    }

    @RequestMapping("/homedonater")
    public String donatehome(Model model)
    {
        return "normal/homedonate";
    }

    //handler for registering volunteer
    @RequestMapping(value="/do_volunteerregister",method= RequestMethod.POST)
    public String registerVolunteer(@Valid @ModelAttribute("volunteer") Volunteer volunteer,
                                    @RequestParam("profileImage") MultipartFile file,
                                    BindingResult result1, Model model,Principal principal, HttpSession session)
    {
        try {

            if(file.isEmpty())
            {
                //if the file is empty then try our message
                System.out.println("File is Empty");
                volunteer.setImage("contact.png");
            }
            else {
                //file the file to folder and update the name to contact
                volunteer.setImage(file.getOriginalFilename());

                File savefile=new ClassPathResource("static/img").getFile();
                Path path= Paths.get(savefile.getAbsolutePath()+File.separator+file.getOriginalFilename());
                Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image is Uploaded");
            }


            if(result1.hasErrors())
            {
                System.out.println("ERROR "+result1.toString());
                model.addAttribute("volunteer",volunteer);
                return "normal/volunteer";
            }
            System.out.println("Volunteer " + volunteer);
            Volunteer result = this.volunteerRepository.save(volunteer);
            model.addAttribute("volunteer", result);

            session.setAttribute("message",new Message("Successfully Registered (:","alert-success"));
            return "normal/volunteer";
        }
        catch(Exception e)
        {
            e.printStackTrace();
            model.addAttribute("volunteer",volunteer);
            session.setAttribute("message",new Message("Something Went Wrong !!"+e.getMessage(),"alert-danger"));
            return "normal/volunteer";
        }

    }

    @RequestMapping(value="/do_donateregister",method= RequestMethod.POST)
    public String registerDonate(@Valid @ModelAttribute("donate") Donate donate,
                                    BindingResult result2, Model model,Principal principal)
    {
        try {

            if(result2.hasErrors())
            {
                System.out.println("ERROR "+result2.toString());
                model.addAttribute("donate",donate);
                return "normal/donate";
            }
            System.out.println("Donate " + donate);
            Donate result3 = this.donateRepository.save(donate);
            model.addAttribute("donate", result3);

            return "normal/terms";
        }
        catch(Exception e)
        {
            e.printStackTrace();
            model.addAttribute("donate",donate);
            return "normal/donate";
        }

    }

    @RequestMapping("/about")
    public String userabout(Model m)
    {
        m.addAttribute("title","about");
        return "normal/about";

    }

    @RequestMapping("/campaigns")
    public String usercampaigns(Model m)
    {
        m.addAttribute("title","Campaigns");
        return "normal/campaigns";
    }

    @RequestMapping("/termsandconditions")
    public String terms(Model m)
    {
        m.addAttribute("title","terms and conditions");
        return "normal/terms";
    }

    @RequestMapping("/contactus")
    public String contact(Model m)
    {
        m.addAttribute("title","Contact us");
        m.addAttribute("contactus",new Contactus());
        return "normal/contact";
    }

    @RequestMapping(value="/do_contactus",method= RequestMethod.POST)
    public String registercontactus(@Valid @ModelAttribute("contactus") Contactus contactus,
                                    BindingResult result2, Model model, Principal principal)
    {
        try {

            if(result2.hasErrors())
            {
                System.out.println("ERROR "+result2.toString());
                model.addAttribute("contactus",contactus);
                return "normal/contact";
            }
            System.out.println("Contactus " + contactus);
            Contactus result3 = this.contactusRepository.save(contactus);
            model.addAttribute("contactus", result3);

            return "normal/contact";
        }
        catch(Exception e)
        {
            e.printStackTrace();
            model.addAttribute("contactus",contactus);
            return "normal/contact";
        }

    }
}
