function $$(objectId) {
     if(document.getElementById && document.getElementById(objectId)) {
     	return document.getElementById(objectId);// W3C DOM
     } else if (document.all && document.all(objectId)) {
         return document.all(objectId);// MSIE 4 DOM
     } else if (document.layers && document.layers[objectId]) {
         return document.layers[objectId];// NN 4 DOM.. note: this won't find nested layers
     } else {
         return false;
     }
}

//滑动门js
//设置样式,传入参数：div_name,total_num,now_index,isCustom(表示是否是自定义)
function gl_setTab(div_name,total_num,now_index,isCustom) { //v6.0
  var i=0;
  var caption_name=div_name+"_caption_";
  var content_name=div_name+"_content_";
  var caption_css_not="";
  var caption_css_now="";
  
  for (i=0; i<total_num; i++){	     
      caption_css_not=div_name+"_"+i+"_caption_normal";
      caption_css_now=div_name+"_"+i+"_caption_now";
	  if ($$(caption_name+i)!=false) $$(caption_name+i).className=caption_css_not;
	  if ($$(content_name+i)!=false) $$(content_name+i).style.display="none";
	  
	  if ($$(caption_name+i+"_select_old")!=false && $$(caption_name+i+"_select_old").innerHTML!=""){
		  $$(caption_name+i).innerHTML=$$(caption_name+i+"_select_old").innerHTML;
	  }
  }
  
  caption_css_not=div_name+"_"+now_index+"_caption_normal";
  caption_css_now=div_name+"_"+now_index+"_caption_now";
	  
  if ($$(caption_name+now_index)!=false) $$(caption_name+now_index).className=caption_css_now;
  if ($$(content_name+now_index)!=false) $$(content_name+now_index).style.display="";

  //设置标题选中内容
  if ($$(caption_name+now_index+"_select")!=false&&$$(caption_name+now_index+"_select").innerHTML!=""){
      $$(caption_name+now_index+"_select_old").innerHTML=$$(caption_name+now_index).innerHTML;
	  $$(caption_name+now_index).innerHTML=$$(caption_name+now_index+"_select").innerHTML;	  
  }
}

//保存当前选中项
function gl_setTabSelect(div_name,index){
  cookie.set(div_name,index);
}

//根据当前网址选中
function gl_setTabNowUrl(div_name,total_num){
  var i=0;
  var caption_name=div_name+"_caption_";
  var content_name=div_name+"_content_";
  var caption_css_not="";
  var caption_css_now="";
  
  //全部置为不选中
  for (i=0; i<total_num; i++){	     
      caption_css_not=div_name+"_"+i+"_caption_normal";
      caption_css_now=div_name+"_"+i+"_caption_now";
	  if ($$(caption_name+i)!=false) $$(caption_name+i).className=caption_css_not;
	  if ($$(content_name+i)!=false) $$(content_name+i).style.display="none";
	  	  
	  if ($$(caption_name+i+"_select_old")!=false && $$(caption_name+i+"_select_old").innerHTML!=""){
		  $$(caption_name+i).innerHTML=$$(caption_name+i+"_select_old").innerHTML;
	  }
  }
  

  //优先判断网址，如果网址和参数一致,则采用网址的

 //得到当前网址
   var url=window.location.href;
   
  //查找所有的选项页面，如果有符合网址的且有链接的
    var find_flag=false;
	for (i=0; i<total_num; i++){	
	    if($$(caption_name+i).innerHTML.indexOf("a")!=-1){
			
			 for(var j=0;j<$$(caption_name+i).childNodes.length;j++){
			   if($$(caption_name+i).childNodes[j].tagName == "A"){
				   
				   //先不管完全没有链接的
				   if($$(caption_name+i).childNodes[j].href==""){
					   continue;
				   }
				   
					var me_url=$$(caption_name+i).childNodes[j].href;
					if(url==me_url){
						caption_css_not=div_name+"_"+i+"_caption_normal";
						caption_css_now=div_name+"_"+i+"_caption_now";
						  
						if ($$(caption_name+i)!=false) $$(caption_name+i).className=caption_css_now;
						if ($$(content_name+i)!=false) $$(content_name+i).style.display="";
						
						//设置标题选中内容
						if ($$(caption_name+i+"_select")!=false&&$$(caption_name+i+"_select").innerHTML!=""){
							$$(caption_name+i+"_select_old").innerHTML=$$(caption_name+i).innerHTML;
							$$(caption_name+i).innerHTML=$$(caption_name+i+"_select").innerHTML;	  
						}
						  
						find_flag=true;
						return;  
					}	
				}
			 }

		}
  	}	
	
	//如果没有找到,则从历史中调
	if(find_flag==false){
		  var index=cookie.get(div_name);

		  if(index==null || index=='undefined'){
			 index=0; 
		  }

		  caption_css_not=div_name+"_"+index+"_caption_normal";
		  caption_css_now=div_name+"_"+index+"_caption_now";
							  
		  if ($$(caption_name+index)!=false) $$(caption_name+index).className=caption_css_now;
		  if ($$(content_name+index)!=false) $$(content_name+index).style.display="";
		 
		  //设置标题选中内容
		if ($$(caption_name+index+"_select")!=false&&$$(caption_name+index+"_select").innerHTML!=""){
			$$(caption_name+index+"_select_old").innerHTML=$$(caption_name+index).innerHTML;
			$$(caption_name+index).innerHTML=$$(caption_name+index+"_select").innerHTML;	  
		}
	}
 
}

var oEditer;
function CustomValidate(source, arguments)
{
    var value = oEditer.GetXHTML(true);
    if(value=="")
    {
       arguments.IsValid = false;
    }
   else
   { 
       arguments.IsValid = true; 
    } 
}

function FCKeditor_OnComplete( editorInstance )
{ 
    oEditer = editorInstance;
}

function openWindow(url)
{
   window.open(url,'anyname','height=400, width=600, top=244, left=500, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

//改变行背景色
function mouseovertr(o)
{
	o.style.backgroundColor="#f3f3f3";
}
function mouseouttr(o)
{
	o.style.backgroundColor="";
}

//提示是否执行大量操作
function cRun(){
if (confirm("执行生成静态页面会消耗服务器大量资源,确定那样做吗?")){
	    return true;
	}else{
		return false;
    }
}

//分页列表
function cdel(){
	if (confirm("你真的要删除这些记录吗？不可恢复！")){
	document.delnews.submit();;
	}
}

function SelectCheckBox()
{
	for(i=0;i<document.delnews.elements.length;i++)
	{
		if(document.all("selectCheck").checked == true)
		{
			document.delnews.elements[i].checked = true;					
		}
		else
		{
			document.delnews.elements[i].checked = false;
		}
	}
}

//隐藏层
function HideLayers(){
	MM_showHideLayers('rightAD','','hide');
	MM_showHideLayers('leftAD','','hide');
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_showHideLayers() { //v6.0
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v=='hide')?'hidden':v; }
    obj.visibility=v; }
}


function checkEmail(object)
{
	if(!ismail(object.value)&&object.value!=""){ 
		alert("请输入正确的邮箱格式！"); 
		object.focus();
		return false; 
	} 
}
function checkPhone(object)
{
	if(!isPhone(object.value)&&object.value!=""){ 
		alert("请输入正确的电话号码！"); 
		object.focus();
		return false; 
	} 
}

//是否含有中文（也包含日文和韩文）   
function isChineseChar(str){      
   var reg = /[\u4E00-\u9FA5\uF900-\uFA2D]/;   
   return reg.test(str);   
}   

//是否只含有中文 
function isOnlyChinese(str){      
    var t = str;
	if (t != str.replace(/[^\u4E00-\u9FA5]/g,'')){
	   return false;
	}else{
		return true;
	}
}

//同理，是否含有全角符号的函数   
function isFullwidthChar(str){   
   var reg = /[\uFF00-\uFFEF]/;   
   return reg.test(str);   
} 

//other
function ismail(mail) 
{ 
	return(new RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(mail)); 
} 
function isInteger(src) {
	reg = /^(-|\+)?\d+$/;
    return (reg.test(src));
}

function isIntegerPlus(src) {
	reg = /^\d+$/;
    return (reg.test(src));
}

function isIntegerMiner(src) {
	reg = /^-\d+$/;
    return (reg.test(src));
}

function isMoney(src) {
	reg = /^\d+\.\d{2}$/;
    return (reg.test(src));
}

function isAge(src) {
	reg  = /^(1[0-2]\d|\d{1,2})$/;  
    return (reg.test(src));
}

function isPhone(src) {
	reg = /^(\+\d+ )?(\(\d+\) )?[\d ]+$/; 
    return (reg.test(src));
}
function isCNPhone(src){
	reg = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/;
	return (reg.test(src));
}

function isName(src) {
	reg = /^[A-Za-z\-]+$/;  
    return (reg.test(src));
}

function isPsw(src) {
	 var sxf,regex;
	 sxf='^[\\w]{6,12}$';
  	 regex=new RegExp(sxf);
  	 return regex.test(src);
}

function isZipCode(src) {
	 var ZipCode,regex;
  	 ZipCode="^[\\d]{6}$";
 	 regex=new RegExp(ZipCode);
 	 return regex.test(src);
}

function isZip(str)
{
	 var reg = /^\d{6}$/;
	 return reg.test(str);
}

function isMobile(str)
{
	 var reg = /^\d{11,12}$/;
	 return  reg.test(str);
}

function isNum15(str)
{
	  var reg=/^\d{15}$/;
	  return reg.test(str);
}

function isNum18(str)
{
  var reg=/^\d{17}(?:\d|x)$/;
  return reg.test(str);
}

//判断两位小数
function isNumber2Small(obj)
{
	var   tempstr = obj.match(/^(?:-?(?:(?:[1-9]\d{0,2}(?:,\d{3})*)|[1-9]\d*|0))(?:\.\d{1,2})?$/);//不需要负数把 "-? "取掉 
	if(tempstr   !=   null) 
		return   true; 
	else 
		return   false; 
}

//判断1位小数
function isNumber1Small(obj)
{
	var   tempstr = obj.match(/^(?:-?(?:(?:[1-9]\d{0,2}(?:,\d{3})*)|[1-9]\d*|0))(?:\.\d{1,1})?$/);//不需要负数把 "-? "取掉 
	if(tempstr   !=   null) 
		return   true; 
	else 
		return   false; 
}

function isIdentity(src) {
   	isIdCorrect1=/^\d{15}$/;
  	isIdCorrect2=/^\d{18}$/;
	if(isIdCorrect1.test(src)||isIdCorrect2.test(src))
       return true;
	 false;
}

//检查是否有特殊符号
function isHaveSpecialSymbols(character)
{
    var txt=new RegExp("[ ,\\`,\\~,\\!,\\@,\#,\\$,\\%,\\^,\\+,\\*,\\&,\\\\,\\/,\\?,\\|,\\:,\\.,\\<,\\>,\\{,\\},\\(,\\),\\',\\;,\\=,\"]");
    //特殊字符正则表达式
    if (txt.test(character))
    {
       return false;
    }else{
		return true;
	}
}

function isTime(str)
{
    var a = str.match(/^(\d{1,2})(:)?(\d{1,2})\2(\d{1,2})$/);
    if (a == null) {alert("Invalid Time Format"); return false;}
    if (a[1]>24 || a[3]>60 || a[4]>60)
    {
          alert("Invalid time format");
          return false
    }
    return true;
}

function isDateTime(str)
{
 	var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
	if(r==null)return false; 
	var d= new Date(r[1], r[3]-1, r[4]); 
	return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
}

function isDateLongTime(str)
{
    var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/; 
    var r = str.match(reg); 
    if(r==null)return false; 
    var d= new Date(r[1], r[3]-1,r[4],r[5],r[6],r[7]); 
    return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]&&d.getHours()==r[5]&&d.getMinutes()==r[6]&&d.getSeconds()==r[7]);
}

function isChar(src) {
	reg = /[^a-zA-Z]/;
    return (reg.test(src));
}

function isCharNum(src) {
	reg = /[^0-9a-zA-Z]/;
    return (reg.test(src));
}

function isCharVar(src) {
	reg = /^([a-zA-z_]{1})([\w]*)$/;
    return (reg.test(src));
}

function isPic(filePath){
 var temp;
 var ExtList = ".jpg.gif.bmp.png";
 var the_ext = filePath.substr(filePath.lastIndexOf(".")+1).toLowerCase();
 if (ExtList.indexOf(the_ext)==-1){
  return false;
 }
 return true;
}

function isWebsites(strEmail) { 
  var myReg = /^(http:\/\/[a-z0-9]{1,5}\.)+([-\/a-z0-9]+\.)+[a-z0-9]{2,4}$/;
  if(myReg.test(strEmail)) return true; 
  return false; 
}

function IsURL(str_url){
  var strRegex = "^((https|http|ftp|rtsp|mms)?://)" 
  + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@ 
        + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184 
        + "|" // 允许IP和DOMAIN（域名）
        + "([0-9a-z_!~*'()-]+\.)*" // 域名- www. 
        + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名 
        + "[a-z]{2,6})" // first level domain- .com or .museum 
        + "(:[0-9]{1,4})?" // 端口- :80 
        + "((/?)|" // a slash isn't required if there is no file name 
        + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$"; 
        var re=new RegExp(strRegex); 
  //re.test()
        if (re.test(str_url)){
            return (true); 
        }else{ 
            return (false); 
        }
    }
	
//银行账号验证
function isBank(banknum)
{
var regex=/^(\d{4}[\s\-]?){4,5}\d{3}$/g;
var str = banknum;
var result = regex.test(str);
return result;
}

//--身份证号码验证-支持新的带x身份证
function isIdCardNo(num) 
{
    var factorArr = new Array(7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1);
    var error;
    var varArray = new Array();
    var intValue;
    var lngProduct = 0;
    var intCheckDigit;
    var intStrLen = num.length;
    var idNumber = num;    
    // initialize
    if ((intStrLen != 15) && (intStrLen != 18)) {
        //error = "输入身份证号码长度不对！";
        //alert(error);
        //frmAddUser.txtIDCard.focus();
        return false;
    }    
    // check and set value
    for(i=0;i<intStrLen;i++) {
        varArray[i] = idNumber.charAt(i);
        if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
            //error = "错误的身份证号码！.";
            //alert(error);
            //frmAddUser.txtIDCard.focus();
            return false;
        } else if (i < 17) {
            varArray[i] = varArray[i]*factorArr[i];
        }
    }
    if (intStrLen == 18) {
        //check date
        var date8 = idNumber.substring(6,14);
        if (checkDate(date8) == false) {
            //error = "身份证中日期信息不正确！.";
            //alert(error);
            return false;
        }        
        // calculate the sum of the products
        for(i=0;i<17;i++) {
            lngProduct = lngProduct + varArray[i];
        }        
        // calculate the check digit
        intCheckDigit = 12 - lngProduct % 11;
        switch (intCheckDigit) {
            case 10:
                intCheckDigit = 'X';
                break;
            case 11:
                intCheckDigit = 0;
                break;
            case 12:
                intCheckDigit = 1;
                break;
        }        
        // check last digit
        if (varArray[17].toUpperCase() != intCheckDigit) {
            //error = "身份证效验位错误!...正确为： " + intCheckDigit + ".";
            //alert(error);
            return false;
        }
    } 
    else{        //length is 15
        //check date
        var date6 = idNumber.substring(6,12);
        if (checkDate(date6) == false) {
            //alert("身份证日期信息有误！.");
            return false;
        }
    }
    //alert ("Correct.");
    return true;
}

//字符串长度（包括中文）
function strLen(s) {
 var l = 0;
 var a = s.split("");
 for (var i=0;i<a.length;i++) {
  if (a[i].charCodeAt(0)<299) {
   l++;
  } else {
   l+=2;
  }
 }
 return l;
}

function autoSize(ImgD,w,h)
{
	var  image=new  Image();  
     image.src=ImgD.src;  
     if(image.width>0  &&  image.height>0){  
       flag=true;  
       if(image.width/image.height>=  w/h){  
         if(image.width>w){      
         ImgD.width=w;  
         ImgD.height=(image.height*h)/image.width;  
         }else{  
         ImgD.width=image.width;      
         ImgD.height=image.height;  
         }  
         }  
       else{  
         if(image.height>h){      
         ImgD.height=h;  
         ImgD.width=(image.width*w)/image.height;            
         }else{  
         ImgD.width=image.width;      
         ImgD.height=image.height;  
         }  
         }  
       }  
}

function resizeimg(ImgD,iwidth,iheight) { 
	var image=new Image(); 
	image.src=ImgD.src; 
	if(image.width>0 && image.height>0){ 
	  if(image.width/image.height>= iwidth/iheight){ 
		if(image.width>iwidth){ 
		ImgD.width=iwidth; 
		ImgD.height=(image.height*iwidth)/image.width; 
		}else{ 
		ImgD.width=image.width; 
		ImgD.height=image.height; 
		} 
		//ImgD.alt=image.width+"×"+image.height; 
	  }else{ 
		if(image.height>iheight){ 
		  ImgD.height=iheight; 
		  ImgD.width=(image.width*iheight)/image.height; 
		}else{ 
		  ImgD.width=image.width; 
		  ImgD.height=image.height; 
		} 
		//ImgD.alt=image.width+"×"+image.height; 
	  } 
	  }
}

// JavaScript Document
function getContentValue(obj)
{
	var oEditor = FCKeditorAPI.GetInstance(obj);
	var acontent = oEditor.GetXHTML();
	return acontent;
}

// JavaScript Document
function getCkContentValue(obj)
{
	var editor=CKEDITOR.replace(obj);
    var acontent = editor.document.getBody().getHtml(); //取得html文本
	return acontent;
}

function open_Dialog(url,title,Width,Height)
{
    var return_Value;
    return_Value = window.showModalDialog(url,window,"dialogWidth:" + Width + "px;dialogHeight:" + Height + "px;center:yes;status:no;scroll:yes;help:no;");
	if(return_Value=="ok"){
		location.reload(true);
	}
	return return_Value;
    //alert(return_Value);
}

function refresh_code(ctl)
{
    var myDate = new Date(); 
	$$(ctl).src='/include/validitCodePicture.php?t='+myDate.toLocaleString();
}

//全选，全不选，反选
function chk_selected(obj,flag)
{
	 var tree=document.getElementsByName(obj); 
     var i; 
     //alert(tree.length); 
     for(i=0;i<tree.length;i++){ 
	    if(flag=="all"){
			 tree[i].checked=true; 
		}
        if(flag=="no"){
			 tree[i].checked=false; 
		}
		if(flag=="reverse"){
			if(tree[i].checked){ 
				tree[i].checked=false; 
			}else{
				tree[i].checked=true; 
			}
		}
     } 
}

function get_chk_value(obj)
{
	 var tree=document.getElementsByName(obj); 
     var i; 
     var values="";
	 
     for(i=0;i<tree.length;i++){ 
	    if(tree[i].checked){
			values+=tree[i].value+","; 
		}
        
     } 
	 return values;
}

function get_radio_value(obj)
{
	 var tree=document.getElementsByName(obj); 
     var i; 
     var values="";
	 
     for(i=0;i<tree.length;i++){ 
	    if(tree[i].checked){
		  values=tree[i].value; 
		  break;
		}
        
     } 
	 return values;
}

function SetIframeSize(iframeName) 
{ 
	var iframe = document.getElementById(iframeName); 
	try
   { 
		var bHeight = iframe.contentWindow.document.body.scrollHeight; 
		var dHeight = iframe.contentWindow.document.documentElement.scrollHeight; 
		//据说这两个高度可能不一样
		var height = Math.max(bHeight, dHeight); 
		 iframe.height =height; 
	   }catch (ex){}
}

//+---------------------------------------------------  
//| 字符串转成日期类型   
//| 输入：yyyy-mm-dd hh:mm:ss
//| 格式 MM/dd/YYYY hh:mm:ss  
//+---------------------------------------------------  
function StringToDateTime(DateStr)  
{     
    var arys= DateStr.split('-');
	var arys1=arys[2].split(' ');
	var arys2=arys1[1].split(':');
	
        myDate = new Date(arys[0],arys[1]-1,arys1[0],arys2[0],arys2[1],arys2[2]); 

    return myDate;  
}  

/*字符转日期*/

//多个倒计时
function GetRTime(datevule,id){ 
    //alert(datevule);
	//datevule="07/22/2012 12:22:33";
	/*var str = datevule.split('-');
	var EndTime = new Date(); 
	EndTime.setUTCFullYear(str[0], str[1] - 1, str[2]); 
	EndTime.setUTCHours(0, 0, 0, 0); */
	
	var EndTime= new Date(StringToDateTime(datevule)); //截止时间 年 月 日 时 分 秒)
	//alert(EndTime)
	var NowTime = new Date(); 
	var nMS =EndTime.getTime() - NowTime.getTime(); 
	var nD =Math.floor(nMS/(1000 * 60 * 60 * 24)); 
	var nH=Math.floor(nMS/(1000*60*60)) % 24; 
	var nM=Math.floor(nMS/(1000*60)) % 60; 
	var nS=Math.floor(nMS/1000) % 60; 
	if(nD>= 0){ 
		document.getElementById(id).innerHTML="剩余"+nD+"天"+nH+"小"+nM+"分"+nS+"秒"; 
	} 
	else { 
		document.getElementById(id).innerHTML="已结束！"; 
	} 
} 


//-----------------------------------web.20--------------------------------
// JScript 文件
function keyDown(){ 
	if (typeof (window.event) == 'undefined'){
	}else {
	   if(window.event.keyCode==13){ 
		window.event.keyCode=9; 
	   }   
	}    
} 

//输入控件表现
function setFocus(obj)
{
	if ($$("lbl_"+obj)!=false){
		 $$(obj).className="txtfocus";   
         $$("lbl_"+obj).className="lblfocus"; 
	}
   
}

//设置错误提示
function setCssErr(obj,tip)
{
	if ($$("lbl_"+obj)!=false){
		$$("lbl_"+obj).innerHTML=tip;
  		$$("lbl_"+obj).className="lblerror";
	}else{
        //alert(tip);
	}	
}

//原型验证
String.prototype.Trim = function() {
var m = this.match(/^\s*(\S+(\s+\S+)*)\s*$/);
return (m == null) ? "" : m[1];
}

String.prototype.isMobile = function() {
return (/^(?:13\d|15[89])-?\d{5}(\d{3}|\*{3})$/.test(this.Trim()));
}

String.prototype.isTel = function()
{
    //"兼容格式: 国家代码(2到3位)-区号(2到3位)-电话号码(7到8位)-分机号(3位)"
    //return (/^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/.test(this.Trim()));
    return (/^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/.test(this.Trim()));
}


//设置当前链接的样式
function set_style_now_link(_obj,_classname){
	
        var url=window.location.pathname+""+window.location.search;
		var me_url=document.getElementById(_obj).pathname+document.getElementById(_obj).search;
		var find_flag=true;
			
		if(window.location.pathname==document.getElementById(_obj).pathname){
			//对比所有的网址参数(如果外部的网址参数和当前的相同)
			
			var paraArray1=window.location.search.replace("?","").split('&');//把参数分隔到数组中
            var paraArray2=document.getElementById(_obj).search.replace("?","").split('&');//把参数分隔到数组中
			
				
			//2的所有参数应该在1中都有
			for(var i=0;i<paraArray2.length;i++){
				
				var find_flag1=false;
				
				for(var j=0;j<paraArray1.length;j++){
					
				   if(paraArray2[i]==paraArray1[j]){
					   find_flag1=true;
					   break;
				   }
				   
				}
				
				//没有找到则跳出
				if(find_flag1==false){
					find_flag=false;
					break;
				}
			}
			
            if(find_flag){
               document.getElementById(_obj).className=_classname;
			}
	}
}

var cookie = {};
cookie.set = function(n, v, t){
	var exp  = new Date();
	exp.setTime(exp.getTime() + (t||24)*60*60*1000);
	document.cookie = n + "="+ escape(v) + ";expires=" + exp.toGMTString()+';path=/';
}
cookie.get = function(n){
	var arr = document.cookie.match(new RegExp("(^| )"+ n +"=([^;]*)(;|$)"));
	if(arr != null) return unescape(arr[2]);
	return null;
}