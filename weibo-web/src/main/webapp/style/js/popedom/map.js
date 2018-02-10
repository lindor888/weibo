function Map(){
	this._values = new Array();
	this._a = new Array();
}

Map.prototype.put = function (key, obj){
	var len = this._a[key];
	if(len == null){
        len = this._values.length;
		this._a[key] = len;
	}
	this._values[len] = obj;
}

Map.prototype._getIndex = function (key_index){
	var index = null;
	if((typeof key_index) == "string"){
		index = this._a[key_index];
	}else if((typeof key_index) == "number"){
		index = key_index;
	}else{
		alert("����Ĳ������Ͳ��ԣ���������������ַ�!");
	}
	return index;
}

Map.prototype.get = function (key_index){
	var index = this._getIndex(key_index);
	var obj = null;
	if(index != null && index < this._values.length && index>=0){
		var obj = this._values[index];
		if(obj instanceof RemovedObj){
			obj = null;
		}
	}
	return obj;
}

Map.prototype.remove = function(key_index){	
	var obj = null;
	var index = this._getIndex(key_index);
	if(index != null && index < this._values.length && index>=0){
       obj = this._values[index];
	   this._values[index] = new RemovedObj();
	}
	return obj;
}

Map.prototype.size = function(){
	return this._values.length;
}

Map.prototype.values = function(){
	return getValues();
}

Map.prototype.getValues = function(){
	var a = new Array();
	for(var i=0; i<this._values.length; i++){
		if(!(this._values[i] instanceof RemovedObj)){
		   a[a.length] = this._values[i];
		}
	}
	return a;
}

//*********************************
function RemovedObj(){
   this.label = "[��ɾ��Ķ���]";
}

RemovedObj.prototype.toString = function(){
	return this.label;
}