package com.example.estimate.demo.page;

public class Searchable {
    private String searchCategory;
    private String searchText;

    public Searchable() {
    }

    public Searchable(String searchCategory, String searchText) {
        setSearchCategory(searchCategory);
        setSearchText(searchText);
    }

    public String getSearchCategory() {
        return searchCategory;
    }

    public void setSearchCategory(String searchCategory) { //sqlインジェクション防止のためにカテゴリーを再検査
        this.searchCategory = (searchCategory.equals("prodID") |
                searchCategory.equals("prodName")) |
                searchCategory.equals("saleID") |
                searchCategory.equals("saleTarget") | searchCategory.equals("userName") ? searchCategory : "prodID"; //他の特定な文字が入力された場合はそれをprodIDに変更
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        String regex = "[^a-zA-Z0-9ー!@#$-ぁ-んァ-ン一-龯]";
        searchText = searchText.replaceAll(regex, "");
        this.searchText = searchText;
    }
}
