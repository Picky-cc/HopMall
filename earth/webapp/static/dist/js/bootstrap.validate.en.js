/**
 * Bootstrap validate
 * English lang module
 */

$.bt_validate.fn = {
  'required' : function(value) { return (value  != null) && (value != '')},
  'email' : function(value) { return /^[a-z0-9-_\.]+@[a-z0-9-_\.]+\.[a-z]{2,4}$/.test(value) },
  'www' : function(value) { return /^(http:\/\/)|(https:\/\/)[a-z0-9\/\.-_]+\.[a-z]{2,4}$/.test(value) },
  'date' : function(value) { return /^[\d]{2}\/[\d]{2}\/[\d]{4}$/.test(value) },
  'phone': function(value) { return /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/.test(value) },
  'time' : function(value) { return /^[\d]{2}:[\d]{2}(:{0,1}[\d]{0,2})$/.test(value) },
  'datetime' : function(value) { return /^[\d]{2}\/[\d]{2}\/[\d]{4} [\d]{2}:[\d]{2}:{0,1}[\d]{0,2}$/.test(value) },
  'number' : function(value) { return /^[\d]+$/.test(value) },
  'float' : function(value) { return /^([\d]+)|(\d]+\.[\d]+)$/.test(value) },
  'equal' : function(value, eq_value) { return (value == eq_value); },
  'match' : function(value, input) { return (value == $(input).val());},
  'min' : function(value, min) { return Number(value) >= min },
  'max' : function(value, max) { return Number(value) <= max },
  'between' : function(value, min, max) { return (Number(value) >= min) && (Number(value) <= max)},
  'length_min' : function(value, min) { return value.length >= min },
  'length_max' : function(value, max) { return value.length <= max },
  'length_between' : function(value, min, max) { return (value.length >= min) && (value.length <= max)}
}

$.bt_validate.text = {
  'required' : '输入不能为空！',
  'email' : '请输入正确的email格式！',
  'www' : '请输入正确的网址格式',
  'date' : 'The value is not valid date',
  'time' : 'The value is not valid time',
  'datetime' : 'The value is not valid datetime',
  'phone' : '请输入正确的手机格式！',
  'number' : '请输入数字格式！',
  'float' : '请输入数字格式！',
  'equal' : '输入值与 "%1"不匹配！',
  'match' : '输入值与"%1"不匹配！',
  'min' : '输入值必须大于 %1',
  'max' : '输入值必须小于 %1',
  'between' : 'The value must be between %1 and %2',
  'length_min' : 'The length of the value must be equal or greater than %1',
  'length_max' : 'The length of the value must be equal or less than %1',
  'length_between' : 'The length of the value must be between %1 and %2'
}