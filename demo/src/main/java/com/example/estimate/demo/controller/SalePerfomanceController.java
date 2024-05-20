package com.example.estimate.demo.controller;

import com.example.estimate.demo.dto.Estimate;
import com.example.estimate.demo.dto.Product;
import com.example.estimate.demo.dto.SalePerfomanceDTO;
import com.example.estimate.demo.page.PageInfo;
import com.example.estimate.demo.page.Searchable;
import com.example.estimate.demo.service.ProductH_Service;
import com.example.estimate.demo.service.SalePerfomance_Service;
import com.example.estimate.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/salePerfomance")
@Controller
public class SalePerfomanceController {
    private Logger logger = LoggerFactory.getLogger(MainController.class);
    private String salesDirectory = "salePerfomance/";

    @Autowired
    private SalePerfomance_Service salePerfomanceService;
    @Autowired
    ProductH_Service productHService;
    @Autowired
    UserService userService;

    @GetMapping("/search")
    public String searchSales(Model model,
                              @RequestParam(defaultValue = "0")    int page,
                              @RequestParam(defaultValue = "10")    int pageSize,
                              @RequestParam(defaultValue = "saleID")String searchCategory,
                              @RequestParam(defaultValue = "")     String searchText,
                              @RequestParam(defaultValue = "false") boolean isGoNext,
                              @RequestParam(defaultValue = "false") boolean isGoBack){

        int PagesSize = 10;

        if(isGoNext){
            page = ((page / PagesSize) + 1) * PagesSize;
        }
        if(isGoBack){
            page = ((page / PagesSize) - 1) * PagesSize;
        }

        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by("saleDate").descending());
        Searchable searchable  = new Searchable(searchCategory, searchText);

        Page<SalePerfomanceDTO> dtos = salePerfomanceService.getAllPerfomanceBySearch(pageRequest, searchable);
        List<Estimate> estimates = new ArrayList<>();

        for(SalePerfomanceDTO dto : dtos) {
            Product product = productHService.getProduct(dto.getProdID(), dto.getProdInpDate());
            String userName = userService.getUserName(dto.getUserID());

            Estimate estimate = new Estimate(dto,product.getProdName(), product.getPrice(), userName);
            estimates.add(estimate);
        }

        PageInfo pageInfo = new PageInfo(page, dtos.getTotalPages() - 1);

        boolean canGoToNext = false;
        if(pageInfo.canGoToNext(dtos.getNumber())) {
            canGoToNext = true;
        }
        boolean canGoToBack = false;
        if(pageInfo.canGoToBack(dtos.getNumber())) {
            canGoToBack = true;
        }

        if(estimates.size() == 0) {
            estimates = null;
        }

        model.addAttribute("sales", estimates);
        model.addAttribute("searchable", searchable);
        model.addAttribute("page", page);
        model.addAttribute("pageInfo", pageInfo); //페이지 개수 나누기 위한 정보 전송
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("canGoToNext", canGoToNext);
        model.addAttribute("canGoToBack", canGoToBack);

        return salesDirectory + "search";
    }

    @GetMapping("/add")
    public String addSales(HttpServletRequest request, Model model, RedirectAttributes rttr){

        HttpSession session = request.getSession();

        if(session.getAttribute("userID") == null) {
            rttr.addFlashAttribute("msg", "先にログインしてください。");
            return "redirect:/login";
        }
        String userID = (String) session.getAttribute("userID");

        String userName = userService.getUserName(userID);

        model.addAttribute("userName", userName);

        return salesDirectory + "add";
    }

    @GetMapping("/edit")
    public String editSales(HttpServletRequest request, Model model, RedirectAttributes rttr,
                            @RequestParam String saleID){

        HttpSession session = request.getSession();

        if(session.getAttribute("userID") == null) {
            rttr.addFlashAttribute("msg", "先にログインしてください。");
            return "redirect:/login";
        }

        String loginUserID = (String) session.getAttribute("userID");
        String loginUserName = userService.getUserName(loginUserID);
        Date saleDate = null;
        String saleTarget = null;



        List<SalePerfomanceDTO> sales = salePerfomanceService.getSalesBySaleID(saleID);
        List<Estimate> estimates = new ArrayList<>();

        for(SalePerfomanceDTO dto : sales) {
            Product product = productHService.getProduct(dto.getProdID(), dto.getProdInpDate());
            String userName = userService.getUserName(dto.getUserID());

            if(!userName.equals(loginUserName)){
                rttr.addFlashAttribute("msg", "作成したIDと違うIDです。");
                return "redirect:/salePerfomance/search";
            }

            Estimate estimate = new Estimate(dto,product.getProdName(), product.getPrice(), userName, product.getProdStock());
            estimates.add(estimate);
            saleTarget = dto.getSaleTarget();
            saleDate = dto.getSaleDate();
        }


        model.addAttribute("estimates", estimates);
        model.addAttribute("userName", loginUserName);
        model.addAttribute("saleDate", saleDate);
        model.addAttribute("saleTarget", saleTarget);
        model.addAttribute("saleID", saleID);

        return salesDirectory + "edit";
    }

    @PostMapping("/addAction")
    public ResponseEntity<String> addSalesAction(@RequestBody List<SalePerfomanceDTO> sales){

        if(!salePerfomanceService.checkIsExistSaleID(sales.get(0).getSaleID())) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Is Exist SaleID");
        }

        if(salePerfomanceService.saveAllSales(sales) == 1) {
            return ResponseEntity.ok("Success");
        } else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving sales data");
        }
    }

    @PostMapping("/editAction")
    public ResponseEntity<String> editSalesAction(@RequestBody List<SalePerfomanceDTO> sales){

        if(salePerfomanceService.updateAllSales(sales) == 1) {
            return ResponseEntity.ok("Success");
        } else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving sales data");
        }
    }

    @PostMapping("/delAction")
    public ResponseEntity<String> delSalesAction(@RequestBody List<SalePerfomanceDTO> sales){

        if(salePerfomanceService.deleteSales(sales) == 1) {
            return ResponseEntity.ok("Success");
        } else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving sales data");
        }
    }

    @GetMapping("/productSearch")
    public String searchProduct(Model model,
                                @RequestParam(defaultValue = "0")    int page,
                                @RequestParam(defaultValue = "3")    int pageSize,
                                @RequestParam(defaultValue = "prodID")String searchCategory,
                                @RequestParam(defaultValue = "")     String searchText,
                                @RequestParam(defaultValue = "false") boolean isGoNext,
                                @RequestParam(defaultValue = "false") boolean isGoBack){

        if(isGoNext){
            page = ((page / 10) + 1) * 10;
        }
        if(isGoBack){
            page = ((page / 10) - 1) * 10;
        }

        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by("fromDate").descending());
        Searchable searchable  = new Searchable(searchCategory, searchText);

        Map<String, Object> resultMap = productHService.getAllProduct(pageRequest, searchable);
        List<Product> products = (List<Product>)resultMap.get("products");
        PageInfo pageInfo = new PageInfo(page, (int)resultMap.get("pages")); //페이징 정보 저장

        boolean canGoToNext = false;
        if(pageInfo.canGoToNext((int)resultMap.get("nowPage"))) {
            canGoToNext = true;
        }
        boolean canGoToBack = false;
        if(pageInfo.canGoToBack((int)resultMap.get("nowPage"))) {
            canGoToBack = true;
        }

        if(products.size() == 0) {
            products = null;
        }

        model.addAttribute("products", products);
        model.addAttribute("searchable", searchable);
        model.addAttribute("page", page);
        model.addAttribute("pageInfo", pageInfo); //페이지 개수 나누기 위한 정보 전송
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("canGoToNext", canGoToNext);
        model.addAttribute("canGoToBack", canGoToBack);

        return salesDirectory + "productSearch";
    }

    @PostMapping("/saleInfo")
    @ResponseBody
    public List<Estimate> getProductInfo(@RequestBody String saleID) {

        List<SalePerfomanceDTO> sales = salePerfomanceService.getSalesBySaleID(saleID);
        List<Estimate> estimates = new ArrayList<>();

        for(SalePerfomanceDTO dto : sales) {
            Product product = productHService.getProduct(dto.getProdID(), dto.getProdInpDate());
            String userName = userService.getUserName(dto.getUserID());

            Estimate estimate = new Estimate(dto,product.getProdName(), product.getPrice(), userName);
            estimates.add(estimate);
        }

        return estimates;
    }
}
