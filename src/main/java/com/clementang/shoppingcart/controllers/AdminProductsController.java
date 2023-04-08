package com.clementang.shoppingcart.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import com.clementang.shoppingcart.model.Category;
import com.clementang.shoppingcart.model.Product;
import com.clementang.shoppingcart.service.*;

import com.clementang.shoppingcart.repository.CategoryRepository;
import com.clementang.shoppingcart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/admin/products")
public class AdminProductsController {

    @Autowired
    private ProductService _productService;

    @Autowired
    private CategoryService _categoryService;
    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\cmsshoppingcart\\src\\main\\resources\\static\\media\\";
    //public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/media";  // "file:/C:/Users/kuosh/Documents/cmsshoppingcart/cmsshoppingcart/src/main/resources/static/media/";


    @GetMapping
    public String index(Model model, @RequestParam(value="page", required = false) Integer p) {
        int perPage = 7;
        int page = (p != null) ? p: 0;

        Pageable pageable = PageRequest.of(page, perPage);

        //Page<Product> products = productRepo.findAll(pageable);
        List<Category> categories = _categoryService.getAllCategories();
        List<Product> products = _productService.getAllProducts();


        Map<UUID, String> cats = new HashMap<>();
        for (Category cat : categories) {
            cats.put(cat.getId(), cat.getCategoryName());
        }
        model.addAttribute("products", products);
        model.addAttribute("cats", cats);

       /* long count = productRepo.count();
        double pageCount = Math.ceil((double) count / (double) perPage);

        model.addAttribute("pageCount", (int) pageCount);
        model.addAttribute("perPage", perPage);
        model.addAttribute("count", count);
        model.addAttribute("page", page);*/

        /*return "admin/products/index";*/
        return findPaginated(1,  "categoryId" , "asc", model);
    }

    @GetMapping("/add")
    public String add(Product product, Model model) {

        List<Category> categories = _categoryService.getAllCategories();
        List<Product> products = productRepo.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        System.out.println(UPLOAD_DIRECTORY);
        return "admin/products/add";
    }

    @PostMapping("/add")
    public String add(@Valid Product product,
                            BindingResult bindingResult,
                            MultipartFile file,
                            RedirectAttributes redirectAttributes,
                            Model model) throws IOException{
        List<Category> categories = _categoryService.getAllCategories();
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categories);
            return "admin/products/add";
        }

        boolean fileOK = false;
        byte[] bytes = file.getBytes();
        String filename = file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIRECTORY , filename);
        System.out.println(file.getContentType());
        System.out.println(filename);
        System.out.println(filePath);
        if (filename.endsWith("jpg") || filename.endsWith("png") || filename.endsWith("jpeg")) {
            fileOK = true;
        }

        redirectAttributes.addFlashAttribute("message", "Product added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        
        String newSlug = product.getSlug().toLowerCase().replace(" ", "-");
        Product productExists = productRepo.findBySlug(newSlug);

        if (!fileOK) {
            redirectAttributes.addFlashAttribute("message", "Image must be a jpg, jpeg or a png.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

        } else if (file == null) {
            redirectAttributes.addFlashAttribute("message", "No image file is detected for upload.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        } else if (productExists != null) {
            redirectAttributes.addFlashAttribute("message", "Product exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("product", product);
        } else {
            product.setSlug(newSlug);
            product.setProductImage(filename);
            productRepo.saveAndFlush(product);
            Files.write(filePath, bytes);
        }
        //product.setSlug(slug);
        //product.setImage(filename);
       // Files.write(filePath, bytes);
       // productRepo.saveAndFlush(product);
        return "redirect:/admin/products/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable UUID id, Model model) {
        Product product = productRepo.findById(id);
        List<Category> categories = _categoryService.getAllCategories();

        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "admin/products/edit";

    }

    /**
     * For Administrator to update/edit Product Records
     * File upload validation - Ensure not empty/null and contains image format - jpg, png & jpeg
     */

    @PostMapping("/edit")
    public String edit(@Valid Product product,  BindingResult bindingResult, RedirectAttributes redirectAttributes,
                       @RequestParam("file") MultipartFile imageFile,
                       Model model) throws IOException  {

        Product currentProduct = productRepo.findById(product.getId());
        Category category = categoryRepo.findById(currentProduct.getId());
        List<Category> categories = _categoryService.getAllCategories();
        if (bindingResult.hasErrors()) {   // problematic issue
            model.addAttribute("category", category.getCategoryName());
            model.addAttribute("categories", categories);
            model.addAttribute("productName", currentProduct.getName());
            return "admin/products/edit";
        }

        boolean fileOK = true;
        //byte[] bytes = imageFile.getBytes();
        /*StringBuilder fileImage = new StringBuilder();*/
        //fileImage.append(imageFile.getOriginalFilename());
        Path newFileNameAndPath = Paths.get(UPLOAD_DIRECTORY , imageFile.getOriginalFilename());
        String fileName = imageFile.getOriginalFilename(); // works
        System.out.println(newFileNameAndPath);
        //String fileName = fileImage.toString();


        if (!imageFile.isEmpty()) {
            if (fileName.endsWith("jpg") || fileName.endsWith("png") || fileName.endsWith("jpeg")) {
                fileOK = true;
            }
        }else if (imageFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "No image is uploaded");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }
        else {
            fileOK = false;
        }
        redirectAttributes.addFlashAttribute("message", "Product updated");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        /**
         *  remove any slug with empty space and replace it with '-'
         */
        String newSlug = product.getSlug().toLowerCase().replace(" ", "-");
        Product productExists = productRepo.findById(product.getId());

        if (!fileOK) {
            redirectAttributes.addFlashAttribute("message", "Image must be a jpg, jpeg or a png.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("product", product);
        } else if (productExists != null) {
            redirectAttributes.addFlashAttribute("message", "Product exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("product", product);
        }
        else {

            if (!imageFile.isEmpty()) {

                if (currentProduct.getProductImage()!= null) {
                    Path currentImagePath = Paths.get(UPLOAD_DIRECTORY, currentProduct.getProductImage());
                    Files.delete(currentImagePath);
                }
                product.setProductImage(fileName);
                Files.write(newFileNameAndPath, imageFile.getBytes());

            } else {
                product.setProductImage(currentProduct.getProductImage());
            }
            model.addAttribute("msg", "Uploaded images: " + fileName);
            product.setSlug(newSlug);
            productRepo.saveAndFlush(product);
        }
        return "redirect:/admin/products/edit/" + product.getId();


    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable UUID id, RedirectAttributes redirectAttributes, Model model) throws IOException {

        Product product = _productService.findByProductId(id);
        Path currentImagePath= Paths.get(UPLOAD_DIRECTORY, product.getProductImage());
        Files.delete(currentImagePath);

        productRepo.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Product deleted");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/admin/products";
    }



    @GetMapping("/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 6;

        Page<Product> page = _productService.findProductsPaginated(pageNo, pageSize, sortField, sortDir);

        List<Product> listProducts = page.getContent();
        System.out.println(listProducts);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listProducts", listProducts);
        return "admin/products/index";
    }
}