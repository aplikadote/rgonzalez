//
// Util sobre cada anho del esta pagina
// https://valoruf.cl/valor-uf-anual.html
// 
// Por ejemplo,
// https://valoruf.cl/valores_anuales_uf_1982.html
//

var yyyy = $('.container .row h1').text().split(' ').slice(-1)[0]
var result = [];
for(var month=0;month<12;month++){
  var trs = $($('table')[month]).find('tr')
  var n  = trs.length;
  for(var i=1;i<n;i++){
    var aux = month + 1;
    var mm = aux < 10 ? '0' + aux : ''+aux+'';

    var tr = trs[i];
    var tds = $(tr).find('td');
    var sKey = $(tds[0]).text();
    var sVal = $(tds[1]).text().trim();

    aux = sKey.split(' ')[1];
    var dd = aux < 10 ? '0' + aux : ''+aux+'';
    var sDate = dd + '/' + mm + '/'+ yyyy;

    var pair = [sDate, sVal];
    result.push(pair);
    //console.log(sDate +'   ' + sVal);
  }
}
copy(result);