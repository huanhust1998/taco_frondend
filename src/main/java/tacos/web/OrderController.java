package tacos.web;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import tacos.Order;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {
	private RestTemplate rest = new RestTemplate();
	@GetMapping("/current")
	public String orderForm(Model model) {
		model.addAttribute("order", new Order());
		return "orderForm";
	}
	
	@PostMapping
    public String processOrder(@RequestParam("name")String name, @RequestParam("street") String street,
							   @RequestParam("city") String city,@RequestParam("state") String state,
							   @RequestParam("zip")String zip,@RequestParam("ccNumber")String ccNumber,
							   @RequestParam("ccExpiration")String ccExpiration,@RequestParam("ccCVV")String ccCVV) {
	    Order order = new Order();
	    order.setName(name);
		order.setStreet(street);
		order.setCity(city);
		order.setState(state);
		order.setZip(zip);
		order.setCcNumber(ccNumber);
		order.setCcExpiration(ccExpiration);
		order.setCcCVV(ccCVV);
		rest.postForObject("http://localhost:8443/order",order,Order.class);
	    log.info("Order submitted: " + order);
        return "redirect:/";
    }
}

