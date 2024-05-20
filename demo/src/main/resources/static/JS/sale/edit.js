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
      var table = document.getElementById('products');
      var rows = table.querySelectorAll('tbody tr');

      if (rows.length > 0) {
           document.getElementById("edtBtn").removeAttribute("disabled");
      } else {
          document.getElementById("edtBtn").setAttribute("disabled", "disabled");
      }
  }

  setInterval(checkInputs, 1000);

  document.getElementById("edtBtn").addEventListener("click", function(event) {

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
            recordID    : cells[6].querySelector('input').value,
            saleTarget    : document.getElementById("sales-target").value,
            userID        : document.getElementById("user-id").value,
            saleDate      : document.getElementById("sales-date").value,
            saleID        : document.getElementById("sales-id").value
          };
          products.push(product);
        });

        fetch('/salePerfomance/editAction', {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json'
              },
              body: JSON.stringify(products)
            })
            .then(response => {
                  if (response.ok) {
                    window.alert('成功できにデータを入力しました。');
                    window.location.href = '/salePerfomance/search'; // 원하는 페이지로 이동
                  } else {
                    console.error('Error:', response.statusText);
                  }
                })
                .catch((error) => {
                  console.error('Error:', error);
                });
   });

   document.getElementById("delBtn").addEventListener("click", function(event) {

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
               recordID    : cells[6].querySelector('input').value,
               saleTarget    : document.getElementById("sales-target").value,
               userID        : document.getElementById("user-id").value,
               saleDate      : document.getElementById("sales-date").value,
               saleID        : document.getElementById("sales-id").value
             };
             products.push(product);
           });

           fetch('/salePerfomance/delAction', {
                 method: 'POST',
                 headers: {
                   'Content-Type': 'application/json'
                 },
                 body: JSON.stringify(products)
               })
               .then(response => {
                     if (response.ok) {
                       window.alert('成功できにデータを削除しました。');
                       window.location.href = '/salePerfomance/search'; // 원하는 페이지로 이동
                     } else {
                       console.error('Error:', response.statusText);
                     }
                   })
                   .catch((error) => {
                     console.error('Error:', error);
                   });
      });
