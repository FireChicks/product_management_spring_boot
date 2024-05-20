package com.example.estimate.demo.controller;

import com.example.estimate.demo.dto.Product;
import com.example.estimate.demo.dto.ProductD_DTO;
import com.example.estimate.demo.dto.ProductH_DTO;
import com.example.estimate.demo.page.PageInfo;
import com.example.estimate.demo.page.Searchable;
import com.example.estimate.demo.service.ProductD_Service;
import com.example.estimate.demo.service.ProductH_Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/product")
@Controller
public class ProductController {
    private Logger logger = LoggerFactory.getLogger(MainController.class);
    private String productDirectory = "product/";

    @Autowired
    ProductH_Service productHService;
    @Autowired
    ProductD_Service productDService;

    @GetMapping("/search")
    public String productSearch(Model model,
                                @RequestParam(defaultValue = "0")    int page,
                                @RequestParam(defaultValue = "10")    int pageSize,
                                @RequestParam(defaultValue = "prodID")String searchCategory,
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

        return productDirectory + "productSearch";
    }

    @GetMapping("/add")
    public String productAdd(){
        return productDirectory + "productAdd";
    }

    @PostMapping("/addAction")
    public String addAction(RedirectAttributes rttr, @RequestParam(required = true) String prodID,
                            @RequestParam(required = true) String prodName,
                            @RequestParam(required = true) int prodPrice,
                            @RequestParam(required = true) String fromDate,
                            @RequestParam(required = true) int prodStock){
        Date alterDate = alterDate(fromDate);
        ProductH_DTO productH_dto = new ProductH_DTO(prodID, prodName);
        ProductD_DTO productD_dto = new ProductD_DTO(prodID, prodPrice, alterDate, null, prodStock);


        //プライマルキーが同じ場合
        if(productHService.isDuplicate(productH_dto)){
            rttr.addFlashAttribute("msg", "既に存在している商品IDです。修正してください。");
            rttr.addFlashAttribute("prodID", prodID);
            rttr.addFlashAttribute("prodName", prodName);
            rttr.addFlashAttribute("prodPrice", prodPrice);
            rttr.addFlashAttribute("fromDate", fromDate);
            rttr.addFlashAttribute("prodStock", prodStock);
            return "redirect:add";
        }

        productHService.addProductH(productH_dto);
        productDService.addProductD(productD_dto);

        return "redirect:search";
    }
    @GetMapping("/plusAdd")
    public String productPlusAdd(RedirectAttributes rttr, Model model,
                             @RequestParam(required = true) String prodID,
                             @RequestParam(required = true) String fromDate) {
        Date alterDate = alterDateDetail(fromDate);

        Product product = productHService.getProduct(prodID,alterDate);

        //商品照会失敗のばあい
        if(product == null) {
            rttr.addFlashAttribute("msg", "商品照会過程から問題が発生しました。");
            return "redirect:search";
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        model.addAttribute("prodID", product.getProdID());
        model.addAttribute("prodName", product.getProdName());
        return productDirectory + "productPlusAdd";
    }

    @PostMapping("/plusAddAction")
    public String plusAddAction(Model model, RedirectAttributes rttr, @RequestParam(required = true) String prodID,
                                                            @RequestParam(required = true) String prodName,
                                                            @RequestParam(required = true) int prodPrice,
                                                            @RequestParam(required = true) String fromDate,
                                                            @RequestParam(required = true) int prodStock){
        Date alterDate = alterDate(fromDate);
        ProductD_DTO productD_dto = new ProductD_DTO(prodID, prodPrice, alterDate, null, prodStock);

        //プライマルキーが同じ場合
        if(productDService.isDuplicated(prodID, alterDate)){
            model.addAttribute("msg", "既に存在している販売入庫日です。修正してください。");

            model.addAttribute("prodID", prodID);
            model.addAttribute("prodName", prodName);
            model.addAttribute("prodPrice", prodPrice);
            model.addAttribute("fromDate", fromDate);
            model.addAttribute("prodStock", prodStock);
            return productDirectory + "productPlusAdd";
        }

        productDService.editProductD(productD_dto);
        rttr.addFlashAttribute("msg", "成功できに追加登録を完了しました");

        return "redirect:search";
    }

    @GetMapping("/edit")
    public String productAdd(RedirectAttributes rttr, Model model,
                             @RequestParam(required = true) Long ID,
                             @RequestParam(required = true) String prodID,
                                @RequestParam(required = true) String fromDate) {
        Date alterDate = alterDateDetail(fromDate);

        Product product = productHService.getProduct(prodID,alterDate);

        //商品照会失敗のばあい
        if(product == null) {
            rttr.addFlashAttribute("msg", "商品照会過程から問題が発生しました。");
            return "redirect:search";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        model.addAttribute("ID", ID);
        model.addAttribute("prodID", product.getProdID());
        model.addAttribute("prodName", product.getProdName());
        model.addAttribute("prodPrice", product.getPrice());
        model.addAttribute("fromDate", format.format(product.getFromDate()));
        model.addAttribute("prodStock", product.getProdStock());
        if(product.getToDate() != null){
            model.addAttribute("toDate", format.format(product.getToDate()));
        }
        return productDirectory + "productEdit";
    }

    @PostMapping("/editAction")
    public String editAction(RedirectAttributes rttr, @RequestParam(required = true) Long ID,
                                                        @RequestParam(required = true) String prodID,
                                                        @RequestParam(required = true) String prodName,
                                                        @RequestParam(required = true) int prodPrice,
                                                        @RequestParam(required = true) String fromDate,
                                                        @RequestParam(required = true) int prodStock,
                                                        @RequestParam String toDate){

            Date alterFromDate = alterDate(fromDate);
            Date alterToDate = null;
            if(!toDate.equals("")) {
                alterToDate = alterDate(toDate);
                rttr.addFlashAttribute("msg","成功的に商品売り切れ日を追加しました。");
            }

            ProductD_DTO productD_dto = new ProductD_DTO(ID, prodID, prodPrice, alterFromDate, alterToDate, prodStock);
            productDService.addProductD(productD_dto);
            rttr.addFlashAttribute("msg","成功的に商品情報を修正しました。");

        return "redirect:search";
    }

    @GetMapping("/remove")
    public String productRemove(RedirectAttributes rttr, Model model,
                                @RequestParam(required = true) Long ID,
                             @RequestParam(required = true) String prodID,
                             @RequestParam(required = true) String fromDate) {
        Date alterDate = alterDateDetail(fromDate);

        Product product = productHService.getProduct(prodID,alterDate);

        //商品照会失敗のばあい
        if(product == null) {
            rttr.addFlashAttribute("msg", "商品照会過程から問題が発生しました。");
            return "redirect:search";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        model.addAttribute("ID", ID);
        model.addAttribute("prodID", product.getProdID());
        model.addAttribute("prodName", product.getProdName());
        model.addAttribute("prodPrice", product.getPrice());
        model.addAttribute("fromDate", format.format(product.getFromDate()));
        model.addAttribute("prodStock", product.getProdStock());
        if(product.getToDate() != null){
            model.addAttribute("toDate", format.format(product.getToDate()));
        }
        return productDirectory + "productRemove";
    }

    @PostMapping("/removeAction")
    public String removeAction(RedirectAttributes rttr,
                               @RequestParam(required = true) Long ID,
                               @RequestParam(required = true) String prodID,
                             @RequestParam(required = true) String prodName,
                             @RequestParam(required = true) int prodPrice,
                             @RequestParam(required = true) String fromDate,
                             @RequestParam(required = true) int prodStock,
                             @RequestParam String toDate){

        Date alterFromDate = alterDate(fromDate);
        Date alterToDate = null;
        if(!toDate.equals("")) {
            alterToDate = alterDate(toDate);
        }

        ProductD_DTO productD_dto = new ProductD_DTO(ID, prodID, prodPrice, alterFromDate, alterToDate, prodStock);
        ProductH_DTO productH_Dto = new ProductH_DTO(prodID,prodName);

        productDService.removeProductD(productD_dto);
        if(!productDService.isDuplicated(prodID)) {
            productHService.removeProductH(productH_Dto);
        }
        rttr.addFlashAttribute("msg","成功的に商品情報 "+prodID+"をを削除しました。");
        return "redirect:search";
    }

    public Date alterDateDetail(String oriDate) {
        Date date = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = formatter.parse(oriDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Date alterDate(String oriDate) {
        Date date = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            date = formatter.parse(oriDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
