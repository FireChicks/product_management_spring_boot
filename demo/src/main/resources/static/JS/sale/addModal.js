function movePageSize() {
        var pageSizeValue = document.getElementById("pageSize").value;
        var searchCategoryValue = document.getElementById("searchCategory").value;
        var searchTextValue = document.getElementById("searchText").value;

        var modalContentUrl = '/salePerfomance/productSearch' + '?pageSize=' + pageSizeValue + '&searchCategory=' + searchCategoryValue + '&searchText=' + searchTextValue;
        fetch(modalContentUrl)
      .then(response => response.text())
      .then(data => {
        document.getElementById('modal-body').innerHTML = data;

        addSelectedProductToDOM();
        document.getElementById("pageSize").addEventListener("change", function () {
        movePageSize();
  });
      })
      .catch(error => console.error('Error:', error));
    }



  function movePage(page) {
        var pageSizeValue = document.getElementById("pageSize").value;
        var searchCategoryValue = document.getElementById("searchCategory").value;
        var searchTextValue = document.getElementById("searchText").value;

        var modalContentUrl = '/salePerfomance/productSearch' + '?pageSize=' + pageSizeValue + '&searchCategory=' + searchCategoryValue + '&searchText=' + searchTextValue +'&page=' + page;
        fetch(modalContentUrl)
      .then(response => response.text())
      .then(data => {
        document.getElementById('modal-body').innerHTML = data;

        addSelectedProductToDOM();
        document.getElementById("pageSize").addEventListener("change", function () {
        movePageSize();
  });
      })
      .catch(error => console.error('Error:', error));
    }

    function moveNextPage(page) {
        var pageSizeValue = document.getElementById("pageSize").value;
        var searchCategoryValue = document.getElementById("searchCategory").value;
        var searchTextValue = document.getElementById("searchText").value;

        var modalContentUrl = '/salePerfomance/productSearch' + '?pageSize=' + pageSizeValue + '&searchCategory=' + searchCategoryValue + '&searchText=' + searchTextValue +'&page=' + page + '&isGoNext=' + true;
        fetch(modalContentUrl)
      .then(response => response.text())
      .then(data => {
        document.getElementById('modal-body').innerHTML = data;

        addSelectedProductToDOM();
        document.getElementById("pageSize").addEventListener("change", function () {
        movePageSize();
  });
      })
      .catch(error => console.error('Error:', error));
    }

    function movePrevPage(page) {
        var pageSizeValue = document.getElementById("pageSize").value;
        var searchCategoryValue = document.getElementById("searchCategory").value;
        var searchTextValue = document.getElementById("searchText").value;

        var modalContentUrl = '/salePerfomance/productSearch' + '?pageSize=' + pageSizeValue + '&searchCategory=' + searchCategoryValue + '&searchText=' + searchTextValue +'&page=' + page + '&isGoBack=' + true;
        fetch(modalContentUrl)
      .then(response => response.text())
      .then(data => {
        document.getElementById('modal-body').innerHTML = data;

        addSelectedProductToDOM();
        document.getElementById("pageSize").addEventListener("change", function () {
        movePageSize();
  });
      })
      .catch(error => console.error('Error:', error));
    }

  function openModal() {
    var modalContentUrl = '/salePerfomance/productSearch';

    fetch(modalContentUrl)
      .then(response => response.text())
      .then(data => {
        document.getElementById('modal-body').innerHTML = data;
        $('#exampleModal').modal('show');

        addSelectedProductToDOM();
        document.getElementById("pageSize").addEventListener("change", function () {
       movePageSize();
  });
      })
      .catch(error => console.error('Error:', error));
  }

  var productData = [];

  //　もダル内部の左側に表示する商品情報の追加関数
  function addSelectedProductToDOM() {
      var selectedProductsContainer = document.getElementById('selectedProducts');

      selectedProductsContainer.innerHTML = '<div style="text-align:center">追加商品リスト</div>';

      productData.forEach(function(product) {
          var row = document.createElement('div');
          row.classList.add('row');

          var col = document.createElement('div');
          col.classList.add('col', 'border', 'd-flex', 'justify-content-between');

          var link = document.createElement('a');
          link.classList.add('me-2', 'align-self-center');
          link.innerHTML = '<div style="text-align: center;">' + product.prodID + '<br>' + product.prodName + '<br>(' + product.fromDate + ')</div>';
          col.appendChild(link);

          var closeButton = document.createElement('button');
          closeButton.setAttribute('type', 'button');
          closeButton.classList.add('btn-close');
          closeButton.setAttribute('aria-label', 'Close');
          col.appendChild(closeButton);

          closeButton.addEventListener('click', function() {
              var linkContent = link.innerText;
              var linkParts = linkContent.split('\n');
              var prodID = linkParts[0].trim();
              var fromDate = linkParts[2].trim().replace(/[()]/g, '');

              for (var i = 0; i < productData.length; i++) {
                  if (productData[i].prodID === prodID && productData[i].fromDate === fromDate) {
                      productData.splice(i, 1);
                      break;
                  }
              }

              addSelectedProductToDOM();
          });


          row.appendChild(col);

          selectedProductsContainer.appendChild(row);
      });

  }


  function addProduct(button) {
      var row = button.closest('tr');

      var product = {
          prodID : row.querySelector('td:nth-child(1)').textContent.trim(),
          prodName : row.querySelector('td:nth-child(2)').textContent.trim(),
          prodPrice : row.querySelector('td:nth-child(3)').textContent.trim(),
          prodStock : row.querySelector('td:nth-child(4)').textContent.trim(),
          fromDate : row.querySelector('td:nth-child(5)').textContent.trim()
      };

      var isDuplicate = productData.some(function(existingProduct) {
              return existingProduct.prodID === product.prodID && existingProduct.fromDate === product.fromDate;
      });

      if (!isDuplicate) {
        productData.push(product);
      }
      addSelectedProductToDOM();

      console.log(productData);

  }

  function removeProduct(prodID, fromDate) {
      for (var i = 0; i < productData.length; i++) {
          if (productData[i].prodID === prodID && productData[i].fromDate === fromDate) {
              productData.splice(i, 1);
              return;
          }
      }
  }