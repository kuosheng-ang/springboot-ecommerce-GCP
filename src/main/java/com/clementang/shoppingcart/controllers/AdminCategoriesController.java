package com.clementang.shoppingcart.controllers;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;


import com.clementang.shoppingcart.model.Category;
import com.clementang.shoppingcart.repository.CategoryRepository;
import com.clementang.shoppingcart.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoriesController {

    @Autowired
    private CategoryService _categoryService;

    @GetMapping
    public String index(Model model) {
        List<Category> categories = _categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return findPaginated(1,  "name" , "asc", model);

    }

    @GetMapping("/add")
    public String add(Category category) {
        return "admin/categories/add";
    }

    @PostMapping("/add")
    public String add(@Valid Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/categories/add";
        }
        redirectAttributes.addFlashAttribute("message", "Category added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        
        String slug = category.getCategoryName().toLowerCase().replace(" ", "-");
        Category categoryExists = _categoryService.findByCategory(category.getCategoryName());

        if (categoryExists != null) {
            redirectAttributes.addFlashAttribute("message", "Category exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("categoryInfo", category);
        } else {
            category.setSlug(slug);
            category.setSorting(100);
            _categoryService.saveCategory(category);
        }
        return "admin/categories/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable UUID id, Model model) {
        Category category = _categoryService.findCategoryById(id);
        model.addAttribute("category", category);
        return "/admin/categories/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid Category category,  BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        Category currentCategory = _categoryService.findCategoryById(category.getId());

        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryName", currentCategory);
            return "admin/categories/edit";
        }
        redirectAttributes.addFlashAttribute("message", "Category updated");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        
        String slug = category.getCategoryName().toLowerCase().replace(" ", "-");
        Category categoryExists = _categoryService.findByCategory(category.getCategoryName());

        if (categoryExists != null) {
            redirectAttributes.addFlashAttribute("message", "Category exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        } else {
            category.setSlug(slug);
            _categoryService.saveCategory(category);
        }
        return "redirect:/admin/categories/edit/" + category.getId();
        //return "admin/categories/edit/" + category.getId();

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes, Model model) {
        _categoryService.deleteCategoryById(id);
        redirectAttributes.addFlashAttribute("message", "Category deleted");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/admin/categories";
    }

    @PostMapping("/reorder")
    public @ResponseBody String reorder(@RequestParam("id[]") UUID[] id) {
        int count = 1;
        Category category;

        for (UUID categoryId : id) {
            category = _categoryService.findCategoryById(categoryId);
            category.setSorting(count);
            _categoryService.saveCategory(category);
            count++;
        }
        return "ok";
    }

    @GetMapping("/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 6;

        Page<Category> page = _categoryService.findCategoriesPaginated(pageNo, pageSize, sortField, sortDir);
        List<Category> listCategories = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listCategories", listCategories );
        return "admin/categories/index";
    }
}
