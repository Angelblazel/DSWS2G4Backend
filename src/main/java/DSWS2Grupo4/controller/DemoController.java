
package DSWS2Grupo4.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
@RequiredArgsConstructor

public class DemoController {
    @PostMapping(value="demo")
    public String Welcome(){
        return "Welcome from secure enspoint";
    }
}
 