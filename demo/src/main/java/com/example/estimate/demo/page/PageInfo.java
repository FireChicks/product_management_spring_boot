package com.example.estimate.demo.page;

public class PageInfo {
    private int page;
    private int pageIndexInterval;

    private int maxPageSize;
    private int startPageNum;
    private int endPageNum;

    public PageInfo() {
    }

    public PageInfo(int page, int maxPageSize) {
        this.page = page;
        this.pageIndexInterval = 10;              //ページ内に表示出来るページの数量
        this.maxPageSize = maxPageSize;

        if(this.page == 0) {
            this.startPageNum = 0;
        } else {
            if(startPageNum >= maxPageSize) {  //最大ページ数より高い場合最大ページ数に設定
                this.startPageNum = maxPageSize;
            } else {
                this.startPageNum = (page / pageIndexInterval);
                if(this.startPageNum >= 1) {
                    this.startPageNum = calcStartPageNum(this.startPageNum);
                }
            }
        }
        setEndPageNum(0);
    }

    public int getPage() {
        return page;
    }

    public int calcStartPageNum(int startPageNum) { //再帰メソッドを利用して10個ずつ増加
        if( startPageNum == 0) {
            return 0;
        } else {
            return pageIndexInterval + calcStartPageNum(startPageNum - 1);
        }
    }

    public void setPage(int page) {
        if(page == 0) {
            this.page = 0;
        } else {
            this.page = page;
        }
    }

    public int getPageSize() {
        return pageIndexInterval;
    }

    public void setPageSize(int pageSize) {
        this.pageIndexInterval = pageSize;
    }

    public int getStartPageNum() {
        return startPageNum;
    }

    public void setStartPageNum(int startPageNum) {
        this.startPageNum = startPageNum;
    }

    public int getEndPageNum() {
        return endPageNum;
    }

    public void setEndPageNum(int endPageNum) { //指定したインタバルだけ最大ページが増加
        this.endPageNum = this.startPageNum + pageIndexInterval -  1;
        if(this.endPageNum >= maxPageSize) {
            this.endPageNum = maxPageSize;
        }
    }

    public int getMaxPageSize() {
        return maxPageSize;
    }

    public void setMaxPageSize(int maxPageSize) {
        this.maxPageSize = maxPageSize;
    }

    public boolean canGoToNext(int nowPage){
        if((nowPage / 10) < (this.maxPageSize / 10)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean canGoToBack(int nowPage){
        if((nowPage / 10) >= 1) {
            return true;
        } else {
            return false;
        }
    }
}
