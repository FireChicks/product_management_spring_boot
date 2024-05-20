const input = document.getElementById('prodID');
  preIdLength = 0;

  input.addEventListener('input', function() {

      const regex = /[^0-9-]/g;
      value = input.value;
      //入力値で数字と特殊文字’-'を除き全て外す
      value = value.replace(regex, '');
      input.value = value;


      idLength = value.length;

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

 //IDとPWの文字すが8以上の時にdisable状態解除
  function checkInputs() {
      var prodID = document.getElementById("prodID").value;
      var prodName = document.getElementById("prodName").value;
      var fromDate = document.getElementById("fromDate").value;
      var prodStock= document.getElementById("prodStock").value;
      var prodPrice = document.getElementById("prodPrice").value;

      if (prodID.length == 9 &&
              prodName.length > 0 &&
              prodPrice.length > 0 &&
              prodStock >= 0) {
          document.getElementById("addBtn").removeAttribute("disabled");
      } else {
          document.getElementById("addBtn").setAttribute("disabled", "disabled");
      }
  }

  document.getElementById("prodID").addEventListener("input", checkInputs);
  document.getElementById("prodName").addEventListener("input", checkInputs);
  document.getElementById("fromDate").addEventListener("input", checkInputs);
  document.getElementById("prodStock").addEventListener("input", checkInputs);
  document.getElementById("prodPrice").addEventListener("input", checkInputs);

  document.getElementById("addBtn").addEventListener("click", function(event) {
          event.preventDefault();

          document.getElementById("prodID").disabled = false;
          document.getElementById("prodName").disabled = false;

          document.getElementById("productForm").submit();
      });