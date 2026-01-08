package med.vol.api.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    //La direccion a donde se mandara ese controller
    @RequestMapping("/hello")
    public String hello(){
        return "hello word";
    }
}
