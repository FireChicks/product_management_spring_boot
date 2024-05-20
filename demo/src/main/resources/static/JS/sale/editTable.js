// 各行の合計を計算する関数
  function updateRowTotal(row) {
      var price = parseFloat(row.cells[3].textContent.substring(1).replaceAll(',','')); // ￥ 9.99에서 숫자만 추출
      var quantity = parseInt(row.cells[4].querySelector('input').value);

      var rowTotal = price * quantity;

      row.cells[5].textContent = formatToJapaneseCurrency(rowTotal);

      calculateTotalPrice();
  }

  // テーブルの全ての行の合計を合わせる関数
  function updateAllRowTotal() {
      var rows = document.querySelectorAll('tbody tr');
      rows.forEach(function(row) {
          updateRowTotal(row);
      });
  }

  // 数量入力フィールドが変更される場合その行の合計を更新する関数
  var quantityInputs = document.querySelectorAll('tbody input[type="number"]');
  quantityInputs.forEach(function(input) {
      input.addEventListener('change', function() {
          var row = this.closest('tr');
          updateRowTotal(row);
      });
  });

  // 最終価格を計算して表示する関数
  function calculateTotalPrice() {
      var totalPrice = 0;
      var rowTotalCells = document.querySelectorAll('tbody td:nth-child(6)');

      rowTotalCells.forEach(function(cell) {
          var rowTotal = parseInt(cell.textContent.substring(1).replaceAll(',','')); // ￥ 부호 제거
          if(!isNaN(rowTotal)) {
            totalPrice += rowTotal;
          }
      });

      var totalPriceCell = document.querySelector('tfoot td:nth-child(6)');
      totalPriceCell.textContent = formatToJapaneseCurrency(totalPrice);
  }

  // 商品データが無い時にメッセージを表示する関数
  function checkTableContent() {
    var tbody = document.querySelector('tbody');
    if (tbody.children.length === 0) {
      var messageRow = document.createElement('tr');
      var messageCell = document.createElement('td');
      messageCell.setAttribute('colspan', '7');
      messageCell.textContent = '商品検索ボタンを押して商品を追加してください。';
      messageRow.appendChild(messageCell);
      tbody.appendChild(messageRow);
    }
  }

  function deleteRow(button) {
  var row = button.parentNode.parentNode;
  row.parentNode.removeChild(row);

  checkTableContent();
  calculateTotalPrice();
}

  //もダルで商品追加を確定する時にテーブルにそのデータを追加する関数
  function addButtonClicked() {
     var tbody = document.querySelector('.table-responsive table tbody');
     tbody.innerHTML = '';

      productData.forEach(function(product) {
          var tr = document.createElement('tr');

          var td1 = document.createElement('td');
          td1.textContent = product.prodID;
          tr.appendChild(td1);

          var td2 = document.createElement('td');
          td2.textContent = product.prodName;
          tr.appendChild(td2);

          var td3 = document.createElement('td');
          td3.textContent = product.fromDate;
          tr.appendChild(td3);

          var td4 = document.createElement('td');
          td4.textContent = product.prodPrice;
          tr.appendChild(td4);

          var td5 = document.createElement('td');
          var input = document.createElement('input');
          input.classList.add('form-control', 'w-100', 'border-0');
          input.setAttribute('type', 'number');
          input.setAttribute('value', '1');
          input.setAttribute('min', '1');
          input.setAttribute('max', product.prodStock);
          td5.appendChild(input);
          tr.appendChild(td5);

          input.addEventListener('input', function() {
            var min = parseInt(input.getAttribute('min'));
            var max = parseInt(input.getAttribute('max'));
            var value = parseInt(input.value);

            if (value < min) {
              input.value = min;
            } else if (value > max) {
              input.value = max;
            }
          });

          var td6 = document.createElement('td');
          td6.textContent = ''; // 판매가격은 비워둠
          tr.appendChild(td6);

          var td7 = document.createElement('td');
          var deleteButton = document.createElement('button');
          deleteButton.classList.add('btn', 'btn-danger');
          deleteButton.textContent = '削除';
          deleteButton.setAttribute('onclick', 'deleteRow(this)');
          td7.appendChild(deleteButton);
          tr.appendChild(td7);

          tbody.appendChild(tr);
      });
       var quantityInputs = document.querySelectorAll('tbody input[type="number"]');
              quantityInputs.forEach(function(input) {
                  input.addEventListener('change', function() {
                      var row = this.closest('tr');
                      updateRowTotal(row);
                  });
        });
      updateAllRowTotal();
  }
  //　数字を通貨形式に変更させる関数
  function formatToJapaneseCurrency(number) {
    var formatter = new Intl.NumberFormat('ja-JP', {
        style: 'currency',
        currency: 'JPY'
    });
    return formatter.format(number);
}