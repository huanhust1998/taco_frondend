package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tacos.Ingredient;
import tacos.Taco;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/ingredient")
public class IngredientController {

    private RestTemplate rest = new RestTemplate();
    @GetMapping()
    public String InformationIngredient(Model model){
        //Sử dụng phương thức getForObject(url, class) để gọi API.
        // Phương thức sẽ tự động gọi API, lấy kết quả, chuyển đổi thành mảng các đối tượng
        List<Ingredient> ingredients = Arrays.asList(rest.getForObject("http://localhost:8443/ingredients",Ingredient[].class));
        System.out.println(ingredients);
        model.addAttribute("ingredients",ingredients);
        return "informationIngredient";
    }
    @GetMapping("/add")
    public String addIngredient(Model model){
        model.addAttribute("ingredient", new Ingredient());
        return "formAddIngredient";
    }

    @PostMapping()
    public String processDesign(@RequestParam("id") String id ,@RequestParam("name") String name,@RequestParam("type") Ingredient.Type type) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(id);
        ingredient.setName(name);
        ingredient.setType(type);
        log.info("New "+ ingredient);
        //Đối tượng Taco được tạo ra sẽ dùng cho postForObject()
        //nhằm thêm vào CSDL để gửi tới POST API
        rest.postForObject("http://localhost:8443/ingredients", ingredient, Ingredient.class);
        return "redirect:/ingredient";
    }

    @GetMapping("/delete/{id}")
    public String deleteIngredient(@PathVariable("id")String id, Model model) {
        rest.delete("http://localhost:8443/ingredients/delete/{id}",id);
        List<Ingredient> ingredients = Arrays.asList(rest.getForObject("http://localhost:8443/ingredients",Ingredient[].class));
        System.out.println(ingredients);
        model.addAttribute("ingredients",ingredients);
        return "informationIngredient";
    }

    @GetMapping("/edit/{id}")
    public String editIngredient(@PathVariable("id")String id,Model model){
        Ingredient ingredient = rest.getForObject("http://localhost:8443/ingredients/{id}",Ingredient.class,id);
        model.addAttribute("ingredient",ingredient);
        return "formAddIngredient";
    }
    @GetMapping("search")
    public String search(@RequestParam("id") String id,Model model){
        Ingredient ingredient = rest.getForObject("http://localhost:8443/ingredients/{id}",Ingredient.class,id);
        model.addAttribute("ingredients",ingredient);
        return "informationIngredient";
    }

}
