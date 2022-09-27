package spring.cookbookweb.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import spring.cookbookweb.Entity.Recipe;
import spring.cookbookweb.Repository.RecipeRepository;


@Controller
public class RecipeController {
    
    private final RecipeRepository repository;

    RecipeController(RecipeRepository repository){
        this.repository = repository;
    }

    @GetMapping("/recipies")
    public String showRecipies(Model model){
        model.addAttribute("recipes", repository.findAll());
        return "list-recipies";
    }

    @GetMapping("/addrecipepage")
    public String addEmployeeForm(Model model){
        model.addAttribute("recipe", new Recipe());
        return "add-recipe";
    }

    @PostMapping("/saverecipe")
    public String saveRecipe(@ModelAttribute Recipe recipe){
        repository.save(recipe);
        return "redirect:/recipies";
    }

    @PostMapping("/saverecipedemo")
    public String getDataForm(@RequestParam("ingrName") String[] ingrNames, 
        @RequestParam("ingrAmount") int[]ingrAmounts,
        @RequestParam("recipeName") String recipeName,
        @RequestParam("recipeDescription") String recipeDesc,
        @RequestParam("recipeDiff") String difficulty,
        @RequestParam("recipeTime") int cookTime){
        Recipe newRecipe = new Recipe(recipeName, recipeDesc, cookTime, difficulty);

        repository.save(newRecipe);
        return "redirect:/recipies";
    }

    @GetMapping("/updaterecipe")
    public String updateRecipe(@RequestParam long id, Model model){
        Recipe changeRecipe = repository.findById(id).get();
        model.addAttribute("recipe", changeRecipe);
        return "add-recipe";
    }

    @GetMapping("/deleterecipe")
    public String deleteRecipe(@RequestParam long id){
        repository.deleteById(id);
        return "redirect:/recipies";
    }
}
