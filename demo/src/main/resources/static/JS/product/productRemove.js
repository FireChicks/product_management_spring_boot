document.getElementById("removeBtn").addEventListener("click", function(event) {
          event.preventDefault();

          if(window.confirm("本当に商品の削除をしますか？")){
            document.getElementById("prodID").disabled = false;
                      document.getElementById("prodName").disabled = false;
                      document.getElementById("prodPrice").disabled = false;
                      document.getElementById("fromDate").disabled = false;
                      document.getElementById("toDate").disabled = false;
                      document.getElementById("prodStock").disabled = false;

                      document.getElementById("productForm").submit();
          } else {
            return;
          }


});