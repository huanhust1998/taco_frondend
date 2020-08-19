package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Taco;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {
	//RestTemplate cung cấp các phương thức để gọi RestAPI
	private RestTemplate rest = new RestTemplate();

	@GetMapping
	public String showDesignForm(Model model) {
		//Sử dụng phương thức getForObject(url, class) để gọi API.
		// Phương thức sẽ tự động gọi API, lấy kết quả, chuyển đổi thành mảng các đối tượng
		List<Ingredient> ingredients = Arrays.asList(rest.getForObject("http://localhost:8443/ingredients",Ingredient[].class));
		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}
		model.addAttribute("design", new Taco());
		return "design";
	}
	
	@PostMapping
	public String processDesign(@RequestParam("ingredients") String ingredientIds, @RequestParam("name") String name) {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		for (String ingredientId : ingredientIds.split(",")) {
			Ingredient ingredient = rest.getForObject("http://localhost:8443/ingredients/{id}",Ingredient.class, ingredientId);
			ingredients.add(ingredient);
		}
		Taco taco = new Taco();
		taco.setName(name);
		taco.setIngredients(ingredients);
		System.out.println(taco);
		//Đối tượng Taco được tạo ra sẽ dùng cho postForObject()
		//nhằm thêm vào CSDL để gửi tới POST API
		rest.postForObject("http://localhost:8443/design", taco, Taco.class);
		return "redirect:/orders/current";
	}

	
	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		List<Ingredient> ingrList = new ArrayList<Ingredient>();
		for (Ingredient ingredient: ingredients) {
			if (ingredient.getType().equals(type)) ingrList.add(ingredient);
		}
		return ingrList;
			
	}
}