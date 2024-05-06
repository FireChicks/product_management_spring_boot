const input = document.getElementById('inputID');
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
     var inputID = document.getElementById("inputID").value;
     var inputPW = document.getElementById("inputPW").value;

     if (inputID.length >= 8 && inputPW.length >= 8) {
         document.getElementById("loginBtn").removeAttribute("disabled");
     } else {
         document.getElementById("loginBtn").setAttribute("disabled", "disabled");
     }
 }

 document.getElementById("inputID").addEventListener("input", checkInputs);
 document.getElementById("inputPW").addEventListener("input", checkInputs);