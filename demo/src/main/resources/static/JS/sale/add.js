const input = document.getElementById('sales-id');
  preIdLength = 0;

  input.addEventListener('input', function() {
      value = input.value;
      idLength = value.length;


      if(idLength <= 2){
        //入力値で大文字と数字と特殊文字’-'を除き全て外す
        var　regex = /[^A-Z0-9-]/g;

        value = value.replace(regex, '');
        input.value = value;
      } else {
        var　preRegex = /[^A-Z0-9-]/g;
        var postRegex = /[^0-9-]/g;
        preValue = value.substr(0,3);
        postValue = value.substr(3);

        value = preValue.replace(preRegex, '') + postValue.replace(postRegex, '');;
        input.value = value;
      }

      //入力値で数字と特殊文字’-'を除き全て外す
      value = value.replace(regex, '');
      input.value = value;


      //ID入力値が2以下に削られる時'-'を削除
      if(preIdLength == 4 && idLength == 3){
        return;
      }
      if(preIdLength == 3 && idLength == 3){
        return;
      }

      //ID入力値が３以上に増加される時'-'を追加
      if(idLength > 2 && idLength <= 3){
        changedValue = value[0] + value[1] + "-";
        for(var i = 2; i < value.length; i++){
          changedValue += value[i];
        }
        input.value = changedValue;
      }
      preIdLength = idLength;
  });

  function checkInputs() {
      var saleTarget = document.getElementById("sales-target").value;
      var userID = document.getElementById("user-id").value;
      var saleDate = document.getElementById("sales-date").value;
      var saleID = document.getElementById("sales-id").value;
      var table = document.getElementById('products');
       var rows = table.querySelectorAll('tbody tr');

      if (saleTarget.length >= 1 &&
              userID.length > 0 &&
              saleDate.length > 0 &&
              saleID.length == 9 && rows.length > 0) {
          document.getElementById("addBtn").removeAttribute("disabled");
      } else {
          document.getElementById("addBtn").setAttribute("disabled", "disabled");
      }
  }
  document.getElementById("sales-target").addEventListener("input", checkInputs);
  document.getElementById("user-id").addEventListener("input", checkInputs);
  document.getElementById("sales-date").addEventListener("input", checkInputs);
  document.getElementById("sales-id").addEventListener("input", checkInputs);

  document.getElementById("addBtn").addEventListener("click", function(event) {
    event.preventDefault();

    var table = document.getElementById('products');
    var rows = table.querySelectorAll('tbody tr');
    products = []

    rows.forEach(function(row) {
          var cells = row.getElementsByTagName('td');
          var product = {
            prodID        : cells[0].innerText,
            prodInpDate : cells[2].innerText,
            saleCount    : cells[4].querySelector('input').value,
            saleTarget    : document.getElementById("sales-target").value,
            userID        : document.getElementById("user-id").value,
            saleDate      : document.getElementById("sales-date").value,
            saleID        : document.getElementById("sales-id").value
          };
          products.push(product);
        });

        fetch('/salePerfomance/addAction', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(products)
        })
        .then(response => {
            if (response.ok) {
                window.alert('成功できにデータを入力しました。');
                window.location.href = '/salePerfomance/search';
            } else {
                return response.text().then(errorMessage => {
                    if (response.status === 500 && errorMessage === "Is Exist SaleID") {
                        window.alert('存在している実績番号です。');
                    } else {
                        console.error('Error:', response.statusText);
                        window.alert('エラーが発生しました。');
                    }
                });
            }
        })
        .catch((error) => {
            console.error('Error:', error);
            window.alert('エラーが発生しました。');
        });

   });
